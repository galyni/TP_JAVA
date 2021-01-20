package formation.java.tp;

import formation.java.tp.fileClasses.DBSerializer;
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
//Args 0 : path \ Args 1 : filename \ Args 2 : 1
    public static void main(String[] args) {
        int operation;
        if (args.length != 4){
            Usage();
            return;
        }
        else{

            try {
                operation = Integer.parseInt(args[0]);
            }catch (NumberFormatException e){
                Usage();
                return;
            }
            if(operation < 1 || operation >= 5){ //Todo : Adapter les tests en fonction des arguments
                Usage();
                return;

            }
            if(!args[1].endsWith(".properties")){
                Usage();
                return;
            }
            if(args[2] == null){
                Usage();
                return;
            }
            //Todo : Il faudra checker toutes les extensions à vérifier
            if(!args[2].endsWith(".json") ){
                Usage();
                return;
            }

        }
        String url = null;
        String filenameProperties = args[1];
        String filenameDatabaseJSON = args[2];
        BufferedReader br = null;

        try{
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filenameProperties)));
            JSONObject jso = new JSONObject(br.readLine());
            url = jso.getString("connectionString");

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (br != null) {
                    br.close();
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }

        Connection connexion = null;
        Statement state = null;
        PreparedStatement ps =  null;
        ResultSet result = null;

        switch (operation) {
            case 4:
                try {
                    connexion = DriverManager.getConnection(url);
                    state = connexion.createStatement();
                    result = state.executeQuery("select * from Editors");


                    // Sérialisation de la table Editeurs
                    DBSerializer dbSerializer = new DBSerializer();
                    JSONArray jsonArray = dbSerializer.SerialiseDatabase( result);


                    // Ecriture du JSON dans un fichier
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filenameDatabaseJSON)));
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
                break;
//            case eOperation.jsonFileToDatabase.value:
//                break;
        }


//        try {
//            // Lecture fichier vers un objet
////            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
////            Editor e1 = (Editor)in.readObject();
////            System.out.println(e1.Stringify());
//
//            //Lecture fichier vers base
//            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
//
//            StringBuilder sb = new StringBuilder();
//            while(br.ready()){
//                sb.append(br.readLine());
//            }
//            JSONArray editors = new JSONArray(sb.toString());
//            JSONObject editor;
//
//            connexion = DriverManager.getConnection(url);
//            state = connexion.createStatement();
//
//            String Name = null;
//            String SIRET = null;
//            String Country = null;
//            String Street = null;
//            String Zipcode = null;
//            String City = null;
//
//            for(int i = 0; i < editors.length(); i++){
//                editor = editors.getJSONObject(i);
//
//                // TODO : check if null for nullable fields and only those
//                Name = editor.getString("Name");
//                if(editor.has("SIRET"))
//                    SIRET = editor.getString("SIRET");
//                else
//                    SIRET = null;
//                if(editor.has("Country"))
//                    Country = editor.getString("Country");
//                else
//                    Country = null;
//                Street = editor.getString("Street");
//                Zipcode = editor.getString("Zipcode");
//                City = editor.getString("City");
//
//                ps = connexion.prepareStatement(
//                        "INSERT INTO EDITORS(SIRET, NAME, STREET, ZIPCODE, CITY, COUNTRY)" +
//                                "VALUES(?, ?, ?, ?, ?, ?)");
//                ps.setString(1, SIRET);
//                ps.setString(2, Name);
//                ps.setString(3, Street);
//                ps.setString(4, Zipcode);
//                ps.setString(5, City);
//                ps.setString(6, Country);
//
//                int nbRows = ps.executeUpdate();
//                System.out.println("Nombre de lignes insérées : " + nbRows);
//
//            }
//
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }  finally {
//        try {
//            if (connexion != null)
//                connexion.close();
//            if (ps != null)
//                ps.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//        Library lLibrary = new Library();
//        new LibraryInitializer().initializeCollection(lLibrary);
//        System.out.println( lLibrary.Stringify() );
    }
    //TODO A changer lorsque l'on modifiera les arguments
    private static void Usage()
    {
        System.out.println( "Usage : \n" +
                "AppName need 4 args : \n" +
                "\targ1 : \n" +
                "\t\t[1] to import file with serialized Objects into database \n" +
                "\t\t[2] to serialize database into file\n" +
                "\t\t[3] to import JSON file into database\n" +
                "\t\t[4] to serialize database into JSON file\n" +
                "\targ2 : path to file to read/write\n" +
                "\targ3 : path to properties file for database connection\n" +
                "\targ4 : path to logs file\n" +
                "\texample : \"AppName [1/2/3/4] serializeFile.txt propertiesFile.json logFile.txt\"");
    }

//    private enum eOperation{ //Todo vérifier si on peut s'en servir
//        objectFileToDatabase(1),
//        databaseToObjectFile(2),
//        jsonFileToDatabase(3),
//        databaseToJsonFile(4);
//
//        private int value;
//        private  eOperation(int value){
//        this.value = value;
//        }
//    }

}
