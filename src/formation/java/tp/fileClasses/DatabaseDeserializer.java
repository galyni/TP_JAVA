package formation.java.tp.fileClasses;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseDeserializer {

    public void DeserializeDatabase(PreparedStatement ps, JSONArray jsonArray){


    }

    public void DeserializeEditorsTable(Connection co, JSONArray editors) throws SQLException {
        int nbRows = 0;
        JSONObject editor;
        PreparedStatement ps;

        String[] columns = {"Name", "SIRET", "Country", "Street", "Zipcode", "City"};
        ArrayList<String> data = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i < columns.length; i++ ){
            sb.append("?, ");
        }
        sb.append("?");

        for(int i = 0; i < editors.length(); i++){
            editor = editors.getJSONObject(i);

            for (String column: columns
                 ) {
                if(editor.has(column))
                    data.add(editor.getString(column));
                else
                    data.add(null);
            }


            ps = co.prepareStatement("INSERT INTO EDITORS("+ String.join(", ", columns)  + ") VALUES(" + sb + ")");

            for(int j = 0; j < columns.length; j++){
                ps.setString(j + 1, data.get(j));
            }

            nbRows += ps.executeUpdate();

        }
        System.out.println("Nombre de lignes insérées dans la table Editors : " + nbRows);
    }
}
