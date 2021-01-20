package formation.java.tp.fileClasses;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBSerializer {

    Table[] tables = { new Table("Editors", new String[] {"ID", "Name", "SIRET", "Country", "Street", "Zipcode", "City"}),
            new Table("Books", new String[] {"ID", "Title", "EditorsID", "PublishDate", "Borrowed", "Borrowable", "NumberOfPages", "Type", "Translated", "Author" }),
            new Table("CDs", new String[] {"ID", "Title", "EditorsID", "PublishDate", "Borrowed", "Borrowable", "Length", "Type", "NumberOfTracks"}),
            new Table("DVDs", new String[] {"ID", "Title", "EditorsID", "PublishDate", "Borrowed", "Borrowable", "Length", "Type", "AudioDescription" }),
            new Table("Magazines", new String[] {"ID", "Title", "EditorsID", "PublishDate", "Borrowed", "Borrowable", "NumberOfPages", "Type", "Frequency", "Author" }),

    };

    // TODO : gestion d'exceptions
    public JSONArray SerialiseDatabase(ResultSet result) throws SQLException {
        JSONArray dbSerialized = new JSONArray();
        for (Table table : tables
             ) {
            JSONObject tableJson = new JSONObject();
            tableJson.put(table.Name, SerialiseTable(table, result));
//            dbSerialized.put(SerialiseTable(table, result));
            dbSerialized.put(tableJson);
        }
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

            tableSerialized.put(jsonObject);
        }
        return tableSerialized;
    }

    class Table{
        String Name;
        String[] Columns;

        Table (String name, String[] columns){
            Name = name;
            Columns = columns;
        }
    }

}
