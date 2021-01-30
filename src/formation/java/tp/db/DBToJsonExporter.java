package formation.java.tp.db;

import formation.java.tp.model.meta.Table;
import formation.java.tp.io.LogWriter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

/**
 * Permet l'exportation de la base Librairie en format Json
 *
 *
 */
public class DBToJsonExporter {

    Table[] mediaTables = {new Table("Books", new String[] {"Title", "PublishDate", "Borrowed", "Borrowable", "NumberOfPages", "Type", "Translated", "Author" }),
            new Table("CDs", new String[] {"Title", "PublishDate", "Borrowed", "Borrowable", "Length", "Type", "NumberOfTracks"}),
            new Table("DVDs", new String[] {"Title", "PublishDate", "Borrowed", "Borrowable", "Length", "Type", "AudioDescription" }),
            new Table("Magazines", new String[] {"Title", "PublishDate", "Borrowed", "Borrowable", "NumberOfPages", "Type", "Frequency", "Author" }),

    };
    Table editorsTable =  new Table("Editors", new String[] {"Name", "SIRET", "Country", "Street", "Zipcode", "City"});

    private Connection connexion = null;
    private String connectionString;
    private LogWriter logWriter;

    /**
     *
     * @param connectionString La chaine de connexion à la base de donnée Librairie
     */
    public DBToJsonExporter(String connectionString) {
        this.connectionString = connectionString;
    }

    /**
     *
     * @param connectionString La chaine de connexion à la base de donnée Librairie
     * @param logWriter L'objet LogWriter pour l'écriture des logs (opérations réussies, erreurs)
     */
    public DBToJsonExporter(String connectionString, LogWriter logWriter) {
        this.connectionString = connectionString;
        this.logWriter = logWriter;
    }

    /**
     * Sérialise la table Library en un JSONObject contenant toutes ses données.
     *
     * Chaque table est un JSONObject ayant un couple clé valeur : le nom de la table - l'ensemble de ses données sérialisées au sein d'un JSONArray
     * Chaque ligne est un JSONObject composé de couples clés(nom de colonne dans la table) valeur (donnée pour cette colonne).
     *
     * Les liens représentés par des clés étrangères dans la base se traduisent par l'inclusion de l'objet lié.
     * @return JSONObject
     */
    public JSONObject SerialiseDatabase(){
        JSONObject dbSerialized = new JSONObject();

        try {
            for (Table table : mediaTables) {
                JSONArray tableJson = SerialiseTable(table);
                dbSerialized.put(table.Name, tableJson);
            }
        } catch (SQLException e) {
            if( logWriter != null ) this.logWriter.ErrorLog(this.getClass().getName() + "Failed to export database ", e) ;
            System.out.println("error during database export... " + e.getMessage());
            e.printStackTrace();
        }  {
            try {
                if (connexion != null)
                    connexion.close();
            } catch (SQLException e) {
                if (logWriter != null)
                    this.logWriter.ErrorLog(this.getClass().getName() + "Failed to close connexion \"" + connectionString + "\"", e);
                System.out.println("error during database update... " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
        if(logWriter != null ) this.logWriter.CRUDOperationLog("Export of database succeeded.");
        return dbSerialized;
    }


    private JSONArray SerialiseTable(Table table) throws SQLException {
        Statement state;
        ResultSet result;
        JSONArray tableSerialized = new JSONArray();

        connexion = DriverManager.getConnection(connectionString);
        state = connexion.createStatement();
        result = state.executeQuery("select * from " + table.Name);

        // Tant que la requête nous renvoie des lignes, on alimente le JSONArray avec les données renvoyées
        // Chaque ligne est convertie en un JSONObject
        while(result.next()){
            JSONObject jsonObject = new JSONObject();
            String data;
            for (String column : table.Columns
                 ) {
                data = result.getString(column);
                jsonObject.put(column, data);
            }
            // On récupère l'éditeur associé grâce à la clé étrangère, et on le stocke dans l'objet Json
            jsonObject.put("editor", GetEditorObject(result.getInt("editorsID")));
            tableSerialized.put(jsonObject);
        }
        result.close();
        state.close();
        connexion.close();
        return tableSerialized;
    }

    private JSONObject GetEditorObject(int editorID) throws SQLException {
        Statement statement = connexion.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM EDITORS WHERE ID=" + editorID);
        resultSet.next();
        JSONObject editorJson = new JSONObject();
        String data;
        for (String column : editorsTable.Columns
        ) {
            data = resultSet.getString(column);
            editorJson.put(column, data);
        }
        resultSet.close();
        statement.close();

        return editorJson;
    }
}
