package formation.java.tp.db;

import formation.java.tp.model.meta.Table;
import formation.java.tp.io.LogWriter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;

/**
 *  Permet l'importation de données d'une base Librairie à partir d'un fichier Json
 */
public class JsonToDBImporter {

    Table[] mediaTables = { new Table("Books", new String[] {"Title", "PublishDate", "Borrowed", "Borrowable", "NumberOfPages", "Type", "Translated", "Author"}),
            new Table("CDs", new String[] {"Title", "PublishDate", "Borrowed", "Borrowable", "Length", "Type", "NumberOfTracks"}),
            new Table("DVDs", new String[] {"Title","PublishDate", "Borrowed", "Borrowable", "Length", "Type", "AudioDescription"}),
            new Table("Magazines", new String[] {"Title","PublishDate", "Borrowed", "Borrowable", "NumberOfPages", "Type", "Frequency", "Author"}),
    };

    private String connectionString;
    private Connection connexion = null;
    private LogWriter logWriter;
    private int totalNumberOfRows = 0;

    /**
     *
     * @param connectionString La chaine de connexion à la base de donnée Librairie
     */
    public JsonToDBImporter(String connectionString) {
        this.connectionString = connectionString;
    }

    /**
     *
     * @param connectionString La chaine de connexion à la base de donnée Librairie
     * @param logWriter L'objet LogWriter pour l'écriture des logs (opérations réussies, erreurs)
     */
    public JsonToDBImporter(String connectionString, LogWriter logWriter) {
        this.connectionString = connectionString;
        this.logWriter = logWriter;
    }

    /**
     * Insère les données d'un objet de type Library contenues dans JSONObject au sein de la base Librairie.
     * Affiche le nombre de lignes insérées dans chaque table.
     *
     * @param libraryJson LeJSONObject contenant les données à insérer dans la base
     */
    public void DeserializeDatabase(JSONObject libraryJson){

        try {
            for (String tableName : getTableNames()
            ) {
                JSONArray tableData = libraryJson.getJSONArray(tableName);
                if (tableData.length() > 0)
                    DeserializeTable(tableData, tableName);
            }

            if(logWriter != null ) this.logWriter.CRUDOperationLog("Import of database from Json succeeded."+ totalNumberOfRows + " lines inserted.");
        } catch (SQLException e) {
            if( logWriter != null ) this.logWriter.ErrorLog(this.getClass().getName() + "Failed to import database from Json ", e) ;
            System.out.println("error during database import... " + e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if (connexion != null)
                    connexion.close();
            } catch (SQLException e) {
                if (logWriter != null)
                    this.logWriter.ErrorLog(this.getClass().getName() + "Failed to close connexion \"" + connectionString + "\"", e);
                System.out.println("error during database update... " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void DeserializeTable(JSONArray tableData, String tableName) throws SQLException {

        int nbRows = 0;
        JSONObject jsonObject;
        PreparedStatement ps;
        ArrayList<String> rowData;

        var columns = getColumnNames(tableName);

        // On construit la suite de '?' nécessaire  à la construction du PreparedStatement (le nombre varie en fonction du nombre de colonnes de la table)
        StringBuilder sb = new StringBuilder();
        sb.append("?, ".repeat(columns.length));
        sb.append("?");

        // Boucle d'insertion, chaque JSONObject donne une requête d'update de la base
        for(int i = 0; i < tableData.length(); i++){

            // On remplit rowData avec les données sérialisées, de façon à pouvoir les insérer dans le PreparedStatement, en tenant compte des null
            jsonObject = tableData.getJSONObject(i);
            rowData = new ArrayList<String>();
            for (String column: columns
                 ) {
                if(jsonObject.has(column))
                    rowData.add(jsonObject.getString(column));
                else
                    rowData.add(null);
            }

            connexion = DriverManager.getConnection(connectionString);

            ps = connexion.prepareStatement("INSERT INTO "+ tableName + "("+ String.join(", ", columns)  + ", EditorsID) VALUES(" + sb + ")");
            for(int j = 0; j < columns.length; j++){
                ps.setString(j + 1, rowData.get(j));
            }
            JSONObject editorJson = jsonObject.getJSONObject("editor");
            // On récupère l'ID de l'éditeur, qui est une foreign key. Si l'éditeur n'existe pas, il est créé avant l'objet qui le requiert.
            ps.setInt(columns.length + 1, GetEditorID(editorJson));

            nbRows += ps.executeUpdate();

            ps.close();
        }
        System.out.println("Nombre de lignes insérées dans la table " + tableName + " : " + nbRows);
        totalNumberOfRows += nbRows;
        connexion.close();
    }


    /**
     * Cherche si l'éditeur correspondant au JSONObjet existe déjà dans la base.
     * @param editorJson l'éditeur à chercher dans la base
     * @return L'ID de l'éditeur récupéré ou créé
     * @throws SQLException
     */
    private int GetEditorID(JSONObject editorJson) throws SQLException {
        int editorID;
        Statement statement = connexion.createStatement();
        String query = "SELECT ID FROM Editors " +
                "WHERE NAME='" +
                editorJson.getString("Name") + "' ";

        // Si la requête a renvoyé un résultat, on retourne l'ID de l'éditeur existant. Sinon, on le crée et on retourne son ID
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet.next()) {
            editorID = resultSet.getInt("ID");
        } else {
            editorID = DeserializeEditor(editorJson);
        }

        resultSet.close();
        statement.close();

        return editorID;
    }

    private int DeserializeEditor(JSONObject editorJson) throws SQLException {
        Statement statement = connexion.createStatement();
        String editorName = editorJson.getString("Name");
        String query = "INSERT INTO EDITORS(SIRET, Name, Street, ZipCode, City, Country) VALUES('" +
                editorJson.getString("SIRET") + "', '" +
                editorName + "', '" +
                editorJson.getString("Street") + "', '" +
                editorJson.getString("ZipCode") + "', '" +
                editorJson.getString("City") + "', '" +
                editorJson.getString("Country") + "')";
        statement.executeUpdate(query);
        
        totalNumberOfRows += 1;

        // On récupère l'ID de l'éditeur créé. Si plusieurs ont le même nom, on prend celui dont l'ID est la plus élevée
        statement = connexion.createStatement();
        query = "SELECT TOP(1) ID FROM Editors " +
                "WHERE NAME='" +
                editorName + "' " +
                "ORDER BY ID DESC";

        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        int editorID = resultSet.getInt("ID");

        resultSet.close();
        statement.close();

        return editorID;
    }

    private String[] getColumnNames(String tableName){
        for (Table table: mediaTables) {
            if(table.Name.equals(tableName))
                return table.Columns;
        }
        return null;
    }

    private ArrayList<String> getTableNames(){
        ArrayList<String> tableNames = new ArrayList<String>();
        for (Table table: mediaTables
             ) {
            tableNames.add(table.Name);
        }
        return tableNames;
    }
}
