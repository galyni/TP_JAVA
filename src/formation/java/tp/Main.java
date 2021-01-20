package formation.java.tp;

import formation.java.tp.fileClasses.DatabaseDeserializer;
import formation.java.tp.fileClasses.DatabaseSerializer;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.*;
import java.sql.*;

public class Main {
//Args 0 : path \ Args 1 : filename \ Args 2 : 1
    public static void main(String[] args) {


        if(!ValidArgs(args)) {
            Usage();
            return;
        }

        String url = null;
        String filenameProperties = args[1];
        String filenameDatabaseJSON = args[2];
        String logsFilename = args[3];
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
        BufferedWriter bw = null;


        switch (args[0]) {
            case "4":
                try {
                    connexion = DriverManager.getConnection(url);

                    // Sérialisation de la base
                    DatabaseSerializer dbSerializer = new DatabaseSerializer();
                    JSONObject serializedDB = dbSerializer.SerialiseDatabase(connexion);

                    // Ecriture du JSON dans un fichier
                    bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filenameDatabaseJSON)));
                    bw.write(serializedDB.toString());
                    bw.close();
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
                        if (bw != null)
                            bw.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "3":
                try {

                    //Lecture fichier vers base
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(filenameDatabaseJSON)));

                    StringBuilder sb = new StringBuilder();
                    while(br.ready()){
                        sb.append(br.readLine());
                    }
                    br.close();
                    JSONObject databaseObject = new JSONObject(sb.toString());
                    JSONArray editors = databaseObject.getJSONArray("Editors");

                    connexion = DriverManager.getConnection(url);

                    DatabaseDeserializer databaseDeserializer = new DatabaseDeserializer();
                    databaseDeserializer.DeserializeEditorsTable(connexion, editors);


                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }  finally {
                    try {
                        if (connexion != null)
                            connexion.close();
                        if (ps != null)
                            ps.close();
                        if (br != null)
                            br.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }



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

    private static boolean ValidArgs(String args[]) {
        int operation;
        if (args.length != 4) {
            return false;
        } else {
            try {
                operation = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                return false;
            }
            if (operation < 1 || operation >= 5) { //Todo : Adapter les tests en fonction des arguments
                return false;
            }
            if (!args[1].endsWith(".properties")) {
                return false;
            }
            //Todo : Il faudra checker toutes les extensions à vérifier
            if (!(args[2].length() > 0) || !args[2].endsWith(".json")) {
                return false;
            }
            if (!(args[3].length() > 0) || !args[3].endsWith(".txt"))
                return false;
        }
        return true;
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
