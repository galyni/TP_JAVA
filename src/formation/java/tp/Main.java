package formation.java.tp;

import formation.java.tp.fileClasses.DatabaseDeserializer;
import formation.java.tp.fileClasses.DatabaseSerializer;
import formation.java.tp.fileClasses.Zipper;
import org.json.JSONArray;
import org.json.JSONObject;
import formation.java.tp.model.Editor;
import formation.java.tp.model.Library;
import formation.java.tp.utils.LibraryInitializer;
import formation.java.tp.utils.eBookType;
import org.json.JSONStringer;
import org.json.JSONWriter;

import java.io.*;
import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Main {
//Args 0 :  (int)operation \ Args 1 : path to file to read/write \ Args 2 : path to properties file \ Arg 3 : path to Log file
    public static void main(String[] args) {
        //TODO don't forget to add LogWriter to every logic classes, to allow them to log their operations and errors into logFile
        int operation = 0;
        if (args.length != 4){
            Usage();
            return;
        }
        else
        {
            if( CheckArgs( args ) == -1 )
            {
                return ;
            }
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
                    // TODO : renvoyer un objet librairie à la place du JSON

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

                    connexion = DriverManager.getConnection(url);

                    DatabaseDeserializer databaseDeserializer = new DatabaseDeserializer();
                    databaseDeserializer.DeserializeDatabase(connexion, databaseObject);


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
            // TODO: 21/01/2021 temporaire, pour test le zipper
            case "2":
                try {
                    String database = "resources/database.json";
                    String nomFichier = "patate.zip";
                    Zipper zipper = new Zipper(nomFichier, database, "resources/Log.txt");
                    zipper.ZipFiles();
                    break;
                } catch (IOException e){
                    e.printStackTrace();
                }
        }



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
                "\targ2 : \".txt\" or \".json\" path to file to read/write\n" +
                "\targ3 : \".properties\" path to properties file for database connection\n" +
                "\targ4 : \".txt\" path to logs file\n" +
                "\texample : \"AppName [1/2/3/4] serializeFile.txt propertiesFile.json logFile.txt\"");
    }

    private static int CheckArgs(String[] pArgs)
    {
        int lCheckArg0 ;
        try
        {
            lCheckArg0 = Integer.parseInt( pArgs[0] ) ;
        }
        catch (NumberFormatException e)
        {
            System.out.println("invalid operation format...") ;
            Usage() ;
            return -1 ;
        }
        if(lCheckArg0 < 1 || lCheckArg0 >= 5)
        {
            System.out.println("Unknown operation requested...") ;
            Usage() ;
            return -1 ;
        }//at this point args[0] seem to be ok
        if( lCheckArg0 == eOperation.objectFileToDatabase.getValue() || lCheckArg0 == eOperation.databaseToObjectFile.getValue() )
        {
            if( !pArgs[2].endsWith(".txt") )
            {
                System.out.println("invalid file extensions...");
                Usage() ;
                return -1 ;
            }
        }
        else
        {
            if( !pArgs[2].endsWith(".json") )
            {
                System.out.println("invalid file extensions...");
                Usage() ;
                return -1 ;
            }
        }//at this point args[1] seem to be ok
        if( !pArgs[1].endsWith(".properties") )
        {
            System.out.println("invalid propertie file...") ;
            Usage();
            return -1 ;
        }//at this point args[0] seem to be ok
        if( !pArgs[3].endsWith(".txt") )
        {
            System.out.println("invalid log file extensions...");
            Usage() ;
            return -1 ;
        }//at this point all args are ok
        return 0 ;
    }

    private enum eOperation
    {
        objectFileToDatabase(1),
        databaseToObjectFile(2),
        jsonFileToDatabase(3),
        databaseToJsonFile(4);

        private int        value ;
        private static Map map = new HashMap<>() ;

        private eOperation(int value)
        {
            this.value = value ;
        }

        static
        {
            for ( eOperation pageType : eOperation.values() )
            {
                map.put(pageType.value, pageType) ;
            }
        }

        public static eOperation valueOf(int pageType)
        {
            return (eOperation) map.get(pageType) ;
        }

        public int getValue()
        {
            return value ;
        }
    }
}
