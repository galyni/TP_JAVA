package formation.java.tp;

import formation.java.tp.model.Editor;
import formation.java.tp.model.Library;
import formation.java.tp.utils.LibraryInitializer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONWriter;


import java.io.*;
import java.sql.*;

public class Main {

    public static void main(String[] args) {
        //TODO : connection string dans un fichier .properties
        // Pour Nico : ajouter \SQLEXPRESS
        String url = "jdbc:sqlserver://localhost;database=Librairie;integratedSecurity=true;authenticationScheme=nativeAuthentication;";

        String fileName = "test.txt";

        Connection connexion = null;
        Statement state = null;
        PreparedStatement ps =  null;
        ResultSet result = null;

        try {
            connexion = DriverManager.getConnection(url);
            state = connexion.createStatement();
            result = state.executeQuery("select * from Editors");

            JSONArray jsonArray = new JSONArray();


            while(result.next()){
                JSONObject jsonObject = new JSONObject();
                String Name = result.getString("Name");
                String SIRET = result.getString("SIRET");
                String Country = result.getString("Country");
                String Street = result.getString("Street");
                String Zipcode = result.getString("Zipcode");
                String City = result.getString("City");
                //System.out.println("Nom : " + Nom);

                // Sérialisation

                jsonObject.put("Name", Name);
                jsonObject.put("SIRET", SIRET);
                jsonObject.put("Country", Country);
                jsonObject.put("Street", Street);
                jsonObject.put("Zipcode", Zipcode);
                jsonObject.put("City", City);
                jsonArray.put(jsonObject);
            }

            // Stockage objet dans un fichier à partir du JSON
//            Editor ed = new Editor();
//            ed.setEditorCity(jsonObject.getString("City"));
//            ed.setEditorName(jsonObject.getString("Name"));


//            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
//            out.writeObject(ed);
//            out.close();

            // Ecriture du JSON dans un fichier
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
            bw.write(jsonArray.toString());
            bw.close();
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
            // Lecture fichier vers un objet
//            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
//            Editor e1 = (Editor)in.readObject();
//            System.out.println(e1.Stringify());

            //Lecture fichier vers base
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));

            StringBuilder sb = new StringBuilder();
            while(br.ready()){
                sb.append(br.readLine());
            }
            JSONArray editors = new JSONArray(sb.toString());
            JSONObject editor;

            connexion = DriverManager.getConnection(url);
            state = connexion.createStatement();

            String Name = null;
            String SIRET = null;
            String Country = null;
            String Street = null;
            String Zipcode = null;
            String City = null;

            for(int i = 0; i < editors.length(); i++){
                editor = editors.getJSONObject(i);

                // TODO : check if null for nullable fields and only those
                Name = editor.getString("Name");
                if(editor.has("SIRET"))
                    SIRET = editor.getString("SIRET");
                else
                    SIRET = null;
                if(editor.has("Country"))
                    Country = editor.getString("Country");
                else
                    Country = null;
                Street = editor.getString("Street");
                Zipcode = editor.getString("Zipcode");
                City = editor.getString("City");

                ps = connexion.prepareStatement(
                        "INSERT INTO EDITORS(SIRET, NAME, STREET, ZIPCODE, CITY, COUNTRY)" +
                                "VALUES(?, ?, ?, ?, ?, ?)");
                ps.setString(1, null);
                ps.setString(2, Name);
                ps.setString(3, Street);
                ps.setString(4, Zipcode);
                ps.setString(5, City);
                ps.setString(6, Country);

                int nbRows = ps.executeUpdate();
                System.out.println("Nombre de lignes insérées : " + nbRows);

            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }  finally {
        try {
            if (connexion != null)
                connexion.close();
            if (ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//        Library lLibrary = new Library();
//        new LibraryInitializer().initializeCollection(lLibrary);
//        System.out.println( lLibrary.Stringify() );
    }
}
