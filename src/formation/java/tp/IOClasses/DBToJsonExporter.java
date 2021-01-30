package formation.java.tp.IOClasses;

import formation.java.tp.model.meta.Table;
import formation.java.tp.utils.LogWriter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

//TODO : stockage d'une Library dans un fichier en binaire
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

    public DBToJsonExporter(String connectionString) {
        this.connectionString = connectionString;
    }

    public DBToJsonExporter(String connectionString, LogWriter logWriter) {
        this.connectionString = connectionString;
        this.logWriter = logWriter;
    }

    public DBToJsonExporter(Table[] tables) {
        this.mediaTables = tables;
    }

    /**
     * Sérialise la table Library en un JSONObject contenant toutes ses données.
     * @return JSONObject
     */
    public JSONObject SerialiseDatabase(){

        JSONObject dbSerialized = new JSONObject();
        Statement state;
        ResultSet result;
        try {
            connexion = DriverManager.getConnection(connectionString);
            for (Table table : mediaTables
            ) {
                state = connexion.createStatement();
                result = state.executeQuery("select * from " + table.Name);
                JSONArray tableJson = SerialiseTable(table, result);
                dbSerialized.put(table.Name, tableJson);
            }

            connexion.close();
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
        if(logWriter != null ) this.logWriter.CRUDOperationLog("Export of database succeeded."); ;
        return dbSerialized;
    }


    private JSONArray SerialiseTable(Table table, ResultSet result) throws SQLException {

        JSONArray tableSerialized = new JSONArray();

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

        return editorJson;
    }
}
