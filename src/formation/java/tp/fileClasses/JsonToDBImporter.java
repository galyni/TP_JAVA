package formation.java.tp.fileClasses;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class JsonToDBImporter {

    Table[] tables = { new Table("Editors", new String[] {"Name", "SIRET", "Country", "Street", "Zipcode", "City"}),
            new Table("Books", new String[] {"Title", "EditorsID", "PublishDate", "Borrowed", "Borrowable", "NumberOfPages", "Type", "Translated", "Author" }),
            new Table("CDs", new String[] {"Title", "EditorsID", "PublishDate", "Borrowed", "Borrowable", "Length", "Type", "NumberOfTracks"}),
            new Table("DVDs", new String[] {"Title", "EditorsID", "PublishDate", "Borrowed", "Borrowable", "Length", "Type", "AudioDescription" }),
            new Table("Magazines", new String[] {"Title", "EditorsID", "PublishDate", "Borrowed", "Borrowable", "NumberOfPages", "Type", "Frequency", "Author" }),
    };

    public void DeserializeDatabase(Connection co, JSONObject databaseJson) throws SQLException {

        for (String tableName: getTableNames()
             ) {
            JSONArray tableData = databaseJson.getJSONArray(tableName);
            if(tableData.length() > 0)
                DeserializeTable(co, tableData, tableName);
        }
    }

    public void DeserializeTable(Connection co, JSONArray tableData, String tableName) throws SQLException {
        int nbRows = 0;
        JSONObject jsonObject;
        PreparedStatement ps;


        var columns = getColumns(tableName);

        ArrayList<String> rowData = null;
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i < columns.length; i++ ){
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


            ps = co.prepareStatement("INSERT INTO "+ tableName + "("+ String.join(", ", columns)  + ") VALUES(" + sb + ")");

            for(int j = 0; j < columns.length; j++){
                ps.setString(j + 1, rowData.get(j));
            }

            nbRows += ps.executeUpdate();

        }
        System.out.println("Nombre de lignes insérées dans la table " + tableName + " : " + nbRows);
    }

    private String[] getColumns(String tableName){
        for (Table table: tables
             ) {
            if(table.Name == tableName)
                return table.Columns;
        }
        return null;
    }

    private ArrayList<String> getTableNames(){
        ArrayList<String> tableNames = new ArrayList<String>();
        for (Table table: tables
             ) {
            tableNames.add(table.Name);
        }
        return tableNames;
    }
}
