package formation.java.tp.fileClasses;

import formation.java.tp.model.Editor;
import formation.java.tp.utils.LogWriter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;

public class JsonToDBImporter {

    Table[] mediaTables = { new Table("Books", new String[] {"Title", "PublishDate", "Borrowed", "Borrowable", "NumberOfPages", "Type", "Translated", "Author"}),
            new Table("CDs", new String[] {"Title", "PublishDate", "Borrowed", "Borrowable", "Length", "Type", "NumberOfTracks"}),
            new Table("DVDs", new String[] {"Title","PublishDate", "Borrowed", "Borrowable", "Length", "Type", "AudioDescription"}),
            new Table("Magazines", new String[] {"Title","PublishDate", "Borrowed", "Borrowable", "NumberOfPages", "Type", "Frequency", "Author"}),
    };
    Table editorsTable = new Table("Editors", new String[] {"Name", "SIRET", "Country", "Street", "Zipcode", "City"});

    private String connectionString;
    private Connection connexion = null;
    private LogWriter logWriter;
    private int totalNumberOfRows = 0;

    public JsonToDBImporter(String connectionString) {
        this.connectionString = connectionString;
    }

    public JsonToDBImporter(String connectionString, LogWriter logWriter) {
        this.connectionString = connectionString;
        this.logWriter = logWriter;
    }

    public void DeserializeDatabase(JSONObject databaseJson){

        try {

            for (String tableName : getTableNames()
            ) {
                JSONArray tableData = databaseJson.getJSONArray(tableName);
                if (tableData.length() > 0)
                    DeserializeTable(tableData, tableName);
            }

            if(logWriter != null ) this.logWriter.CRUDOperationLog("Import of database from Json succeeded."+ totalNumberOfRows + " lines inserted."); ;
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

    public void DeserializeTable(JSONArray tableData, String tableName) throws SQLException {

        connexion = DriverManager.getConnection(connectionString);
        int nbRows = 0;
        JSONObject jsonObject;
        PreparedStatement ps;


        var columns = getColumns(tableName);

        ArrayList<String> rowData = null;
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i < columns.length + 1; i++ ){
            sb.append("?, ");
        }
        sb.append("?");

        for(int i = 0; i < tableData.length(); i++){
            jsonObject = tableData.getJSONObject(i);
            rowData = new ArrayList<String>();
            for (String column: columns
                 ) {
                if(jsonObject.has(column))
                    rowData.add(jsonObject.getString(column));
                else
                    rowData.add(null);
            }


            ps = connexion.prepareStatement("INSERT INTO "+ tableName + "("+ String.join(", ", columns)  + ", EditorsID) VALUES(" + sb + ")");

            for(int j = 0; j < columns.length; j++){
                ps.setString(j + 1, rowData.get(j));
            }

            JSONObject editorJson = jsonObject.getJSONObject("editor");
            ps.setInt(columns.length + 1, GetEditorID(editorJson));

            nbRows += ps.executeUpdate();

        }
        System.out.println("Nombre de lignes insérées dans la table " + tableName + " : " + nbRows);
        totalNumberOfRows += nbRows;
        connexion.close();
    }

    // TODO: 30/01/2021 refactor le select top(1) en une méthode GetEditorIDByName et si -1(par exemple) appeler la méthode insert, qui retourne void

    private int GetEditorID(JSONObject editorJson) throws SQLException {
        Statement statement = connexion.createStatement();
        String query = "SELECT TOP(1) ID FROM Editors " +
                "WHERE NAME='" +
                editorJson.getString("Name") + "' " +
                "ORDER BY ID DESC";

        int editorID;

        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet.next()) {
            editorID = resultSet.getInt("ID");
            return editorID;
        }
        else
            return DeserializeEditor(editorJson);

    }

    public int DeserializeEditor(JSONObject editorJson) throws SQLException {
        Statement statement = connexion.createStatement();
        String query = "INSERT INTO EDITORS(SIRET, Name, Street, ZipCode, City, Country) VALUES('" +
                editorJson.getString("SIRET") + "', '" +
                editorJson.getString("Name") + "', '" +
                editorJson.getString("Street") + "', '" +
                editorJson.getString("ZipCode") + "', '" +
                editorJson.getString("City") + "', '" +
                editorJson.getString("Country") + "')";
        statement.executeUpdate(query);
        
        totalNumberOfRows += 1;
        
        statement = connexion.createStatement();
        query = "SELECT TOP(1) ID FROM Editors " +
                "WHERE NAME='" +
                editorJson.getString("Name") + "' " +
                "ORDER BY ID DESC";

        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        int editorID = resultSet.getInt("ID");

        return editorID;
    }

    private String[] getColumns(String tableName){
        for (Table table: mediaTables
             ) {
            if(table.Name == tableName)
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
