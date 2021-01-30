package formation.java.tp;

import formation.java.tp.IOClasses.*;
import formation.java.tp.model.*;
import formation.java.tp.utils.LogWriter;
import org.json.JSONObject;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

// TODO: 22/01/2021 Liste de points qui restent à voir :
/*
1 - Les objets ont sans doute besoin d'un champ EditeurID, car nous n'avons pas d'ORM pour gérer pour nous le lien entre les objets.
Pour les conversions Objets <-> Json, cela ne pose pas problème, mais pour les liens avec la base oui.
Possibilité : convertisseur sans stockage de l'objet editeur dans les objets, juste de son ID, et stockage des éditeurs à part, avec leurs ID.
Le problème se pose aussi pour le DatabaseSerialzer, qui perd les relations.
Une fois ces problème réglés, le format sera preque le même que pour le passage DB <-> Json, donc tous les sens de conversion seront possible.

Seule différence : la librairie sérialisée crée un objet qui a une couche supplémentaire : { "Library" : {"Books":[...], ...}}
alors que la Base donne tout simplement {"Books":[...], ...}

2 - Ajouter le logger dans toutes les classes


 */

public class Main {
    public static void main(String[] args) {

        if(!CheckArgs(args))
        {
            Usage() ;
            return ;
        }

        LogWriter lLogWriter = new LogWriter(args[3]) ;
        String connectionString = null;
        String filenameProperties = args[1];
        String filenameDatabaseJSON = args[2];
        String logsFilename = args[3];
        BufferedReader br = null;

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

        Connection connexion = null;
        Statement state = null;
        PreparedStatement ps =  null;
        ResultSet result = null;
        BufferedWriter bw = null;


        switch (args[0]) {
            case "1":
                Deserializer<Library> deserializer = new Deserializer<>(args[2], lLogWriter);
                Library librairie = deserializer.Deserialize();

                ObjectToDBImporter importer = new ObjectToDBImporter(connectionString, lLogWriter);

                importer.ImportLibrary(librairie);

                break;
            case "2":
                DBToObjectExporter exporter = new DBToObjectExporter(connectionString, lLogWriter);

                Library librairie2 = exporter.ExportDatabase();
                if(librairie2 != null) {
                    Serializer serializer = new Serializer(args[2], lLogWriter);
                    serializer.Serialize(librairie2);
                }
                break;
            case "3":
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

                break;
            // TODO: 21/01/2021 temporaire, pour test le zipper

            case "4":
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
                break;
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
            if( !pArgs[2].endsWith(".txt") )
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
        }//at this point args[1] seem to be ok
        if( !pArgs[1].endsWith(".properties") )
        {
            System.out.println("invalid propertie file...") ;
            return false ;
        }//at this point args[0] seem to be ok
        if( !pArgs[3].endsWith(".txt") )
        {
            System.out.println("invalid log file extensions...");
            return false ;
        }//at this point all args are ok
        return true;
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
