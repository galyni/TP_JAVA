package formation.java.tp;

import formation.java.tp.model.Editor;
import formation.java.tp.model.Library;
import formation.java.tp.utils.LibraryInitializer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONWriter;


import java.io.*;
import java.sql.*;

public class Main {

    public static void main(String[] args) {
        //TODO : connection string dans un fichier

        // Pour Nico : ajouter \SQLEXPRESS
        String url = "jdbc:sqlserver://localhost;database=Librairie;integratedSecurity=true;authenticationScheme=nativeAuthentication;";
        // SQL Server avec instance et port d'écoute par défaut
        //String url = "jdbc:sqlserver://localhost;databaseName=structures";
//        String user = "user";
//        String mdp = "";

        String fileName = "test.txt";

        Connection connexion = null;
        Statement state = null;
        PreparedStatement ps =  null;
        ResultSet result = null;

//        JsonArray jArray = new JsonArray();
//        while (result.next())
//        {
//            String  type_json=result.getString("type");
//            String name_json=result.getString("name");
//            String id_json=result.getString("demo");
//            JsonObject jObj = new JsonObject();
//            jobj.put("id", id_json);
//            jobj.put("type", type_json);
//            jobj.put("name", name_json);
//            jArray.put(jObj);
//        }
//
//        JsonObject jObjDevice = new JsonObject();
//        jObjDevice.put("device", jArray);
//        JsonObject jObjDeviceList = new JsonObject();
//        jObjDevice.put("devicelist", jObjDevice );
        JSONObject jsonObject = new JSONObject();

        try {
            connexion = DriverManager.getConnection(url);
            state = connexion.createStatement();
            result = state.executeQuery("select * from Editors");
            while(result.next()){
                String Nom = result.getString("Name");
                String City = result.getString("City");
                //System.out.println("Nom : " + Nom);

                // Sérialisation

                jsonObject.put("Name", Nom);
                jsonObject.put("City", City);

            }

            Editor ed = new Editor();
            ed.setEditorCity(jsonObject.getString("City"));
            ed.setEditorName(jsonObject.getString("Name"));

            // Stockage fichier
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(ed);
            out.close();

            result.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (result != null)
                    result.close();
                if (state != null)
                    state.close();
                if (connexion != null)
                    connexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            // Lecture fichier
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            Editor e1 = (Editor)in.readObject();
            System.out.println(e1.Stringify());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
//        Library lLibrary = new Library();
//        new LibraryInitializer().initializeCollection(lLibrary);
//        System.out.println( lLibrary.Stringify() );
    }
}
