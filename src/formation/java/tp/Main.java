package formation.java.tp;

import formation.java.tp.io.*;
import formation.java.tp.db.DBToJsonExporter;
import formation.java.tp.db.DBToObjectExporter;
import formation.java.tp.db.JsonToDBImporter;
import formation.java.tp.db.ObjectToDBImporter;
import formation.java.tp.model.*;
import formation.java.tp.io.LogWriter;
import formation.java.tp.utils.LibraryInitializer;
import org.json.JSONObject;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        if(!CheckArgs(args))
        {
            Usage() ;
            return ;
        }

        LogWriter      lLogWriter           = new LogWriter(args[3]) ;
        String         connectionString     = null;
        String         filenameProperties   = args[1];
        String         filenameDatabaseJSON = args[2];
        BufferedReader br                   = null;
        eOperation     lOperation           = eOperation.values()[ Integer.parseInt(args[0]) - 1 ] ;

        try{
            br = new BufferedReader(new InputStreamReader(new FileInputStream(filenameProperties)));
            JSONObject jso = new JSONObject(br.readLine());
            connectionString = jso.getString("connectionString");

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

        BufferedWriter bw;


        switch (lOperation) {
            case objectFileToDatabase :
                Deserializer<Library> deserializer = new Deserializer<>(args[2], lLogWriter);
                Library deserializedLibrary = deserializer.Deserialize();
                ObjectToDBImporter importer = new ObjectToDBImporter(connectionString, lLogWriter);

                importer.ImportLibrary(deserializedLibrary);
                lLogWriter.FileReadingLog("Successfully deserialize bin file, and insert it into database");
                break;
            case databaseToObjectFile :
                DBToObjectExporter exporter = new DBToObjectExporter(connectionString, lLogWriter);

                Library serializedLibrary = exporter.ExportDatabase();
                if(serializedLibrary != null) {
                    Serializer serializer = new Serializer(args[2], lLogWriter);
                    serializer.Serialize(serializedLibrary);
                }
                lLogWriter.FileWritingLog("Successfully serialize database into bin file");

                MakeZip(lLogWriter, args) ;
                break;
            case jsonFileToDatabase :
                try {
                    //Lecture fichier vers base
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(filenameDatabaseJSON)));

                    StringBuilder sb = new StringBuilder();
                    while (br.ready()) {
                        sb.append(br.readLine());
                    }
                    br.close();
                    JSONObject databaseObject = new JSONObject(sb.toString());

                    JsonToDBImporter databaseDeserializer = new JsonToDBImporter(connectionString, lLogWriter);
                    databaseDeserializer.DeserializeDatabase(databaseObject);
                }catch (IOException e){
                    e.printStackTrace();
                }
                lLogWriter.FileReadingLog("Successfully deserialize json file, and insert it into database");
                break;

            case databaseToJsonFile :
                try {
                    // Sérialisation de la base
                    DBToJsonExporter dbSerializer = new DBToJsonExporter(connectionString, lLogWriter);
                    JSONObject serializedDB = dbSerializer.SerialiseDatabase();

                    // Ecriture du JSON dans un fichier
                    bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filenameDatabaseJSON)));
                    bw.write(serializedDB.toString());
                    bw.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
                lLogWriter.FileWritingLog("Successfully serialize database into json file");

                MakeZip(lLogWriter, args) ;
                break;
            default :
                lLogWriter.ErrorLog("Unknown error from unhandled case") ;
                return ;
        }



    }
    private static void Usage()
    {
        System.out.println("""
                Usage :\s
                AppName need 4 args :\s
                \targ1 :\s
                \t\t[1] to import file with serialized Objects into database\s
                \t\t[2] to serialize database into file
                \t\t[3] to import JSON file into database
                \t\t[4] to serialize database into JSON file
                \targ2 : ".txt", ".bin" or ".json" path to file to read/write
                \targ3 : ".properties" path to properties file for database connection
                \targ4 : ".txt" path to logs file
                \texample : "AppName [1/2/3/4] serializeFile.txt propertiesFile.json logFile.txt\"""");
    }

    private static boolean CheckArgs(String[] pArgs)
    {
        if (pArgs.length != 4){
            return false;
        }
        int lCheckArg0 ;
        try
        {
            lCheckArg0 = Integer.parseInt( pArgs[0] ) ;
        }
        catch (NumberFormatException e)
        {
            System.out.println("invalid operation format...") ;
            return false ;
        }
        if(lCheckArg0 < 1 || lCheckArg0 >= 5)
        {
            System.out.println("Unknown operation requested...") ;
            return false ;
        }//at this point args[0] seem to be ok
        if( lCheckArg0 == eOperation.objectFileToDatabase.getValue() || lCheckArg0 == eOperation.databaseToObjectFile.getValue() )
        {
            if( !(pArgs[2].endsWith(".txt") || pArgs[2].endsWith(".bin")) )
            {
                System.out.println("invalid file extensions...");
                return false ;
            }
        }
        else
        {
            if( !pArgs[2].endsWith(".json") )
            {
                System.out.println("invalid file extensions...");
                return false ;
            }
        }//at this point args[2] seem to be ok
        if( !pArgs[1].endsWith(".properties") )
        {
            System.out.println("invalid propertie file...") ;
            return false ;
        }//at this point args[1] seem to be ok
        if( !pArgs[3].endsWith(".txt") )
        {
            System.out.println("invalid log file extensions...");
            return false ;
        }//at this point all args are ok
        return true;
    }

    private static void MakeZip(LogWriter pLogWriter, String[] pArgs)
    {
        Scanner lScanner = new Scanner(System.in) ;
        Zipper  lZipper ;

        String  lInput ;

        do
        {
            System.out.println("Do you want to create a ZIP? (Y/N)") ;
            lInput = lScanner.nextLine() ;
        }while( !(lInput.equalsIgnoreCase("Y") || lInput.equalsIgnoreCase("N"))) ;

        if(lInput.equalsIgnoreCase("Y") )
        {
            lZipper  = new Zipper("results/zip.zip", pLogWriter, pArgs[2]) ;
            System.out.println("\nDeserialized file will be into zip") ;

            do
            {
                System.out.println("\ndo you want to add logs file in the ZIP? (Y/N)") ;
                lInput = lScanner.nextLine() ;
            }while( !(lInput.equalsIgnoreCase("Y") || lInput.equalsIgnoreCase("N"))) ;
            if( lInput.equalsIgnoreCase("Y")) lZipper.AddNewFiles(pArgs[3]) ;

            do
            {
                System.out.println("\ndo you want to add properties file into the ZIP? (Y/N)") ;
                lInput = lScanner.nextLine() ;
            }while( !(lInput.equalsIgnoreCase("Y") || lInput.equalsIgnoreCase("N"))) ;
            if( lInput.equalsIgnoreCase("Y") ) lZipper.AddNewFiles(pArgs[1]) ;
            lZipper.ZipFiles();
        }
    }

    private enum eOperation
    {
        objectFileToDatabase(1),
        databaseToObjectFile(2),
        jsonFileToDatabase(3),
        databaseToJsonFile(4);

        private int        value ;

        eOperation(int value)
        {
            this.value = value ;
        }

        public int getValue()
        {
            return value ;
        }
    }
}
