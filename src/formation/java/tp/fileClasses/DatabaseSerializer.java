package formation.java.tp.fileClasses;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//TODO : stockage d'une Library dans un fichier en binaire
public class DatabaseSerializer {

    Table[] tables = { new Table("Editors", new String[] {"ID", "Name", "SIRET", "Country", "Street", "Zipcode", "City"}),
            new Table("Books", new String[] {"ID", "Title", "EditorsID", "PublishDate", "Borrowed", "Borrowable", "NumberOfPages", "Type", "Translated", "Author" }),
            new Table("CDs", new String[] {"ID", "Title", "EditorsID", "PublishDate", "Borrowed", "Borrowable", "Length", "Type", "NumberOfTracks"}),
            new Table("DVDs", new String[] {"ID", "Title", "EditorsID", "PublishDate", "Borrowed", "Borrowable", "Length", "Type", "AudioDescription" }),
            new Table("Magazines", new String[] {"ID", "Title", "EditorsID", "PublishDate", "Borrowed", "Borrowable", "NumberOfPages", "Type", "Frequency", "Author" }),

    };

    public DatabaseSerializer() {
    }

    public DatabaseSerializer(Table[] tables) {
        this.tables = tables;
    }

    // TODO : gestion d'exceptions
    public JSONObject SerialiseDatabase(Connection connection) throws SQLException {

        JSONObject dbSerialized = new JSONObject();
        Statement state;
        ResultSet result;
        for (Table table : tables
             ) {
            state = connection.createStatement();
            result = state.executeQuery("select * from " + table.Name);
            JSONArray tableJson = SerialiseTable(table, result);
            dbSerialized.put(table.Name, tableJson);
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
