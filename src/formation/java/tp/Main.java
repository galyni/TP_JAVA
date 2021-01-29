package formation.java.tp;

import formation.java.tp.converters.JsonConverter;
import formation.java.tp.fileClasses.*;
import formation.java.tp.model.*;
import jdk.jshell.spi.ExecutionControl;
import org.json.JSONArray;
import org.json.JSONObject;
import formation.java.tp.utils.LibraryInitializer;
import formation.java.tp.utils.eBookType;
import org.json.JSONStringer;
import org.json.JSONWriter;

import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
//Args 0 :  (int)operation \ Args 1 : path to file to read/write \ Args 2 : path to properties file \ Arg 3 : path to Log file
    public static void main(String[] args) throws ExecutionControl.NotImplementedException, ParseException {
        //TODO don't forget to add LogWriter to every logic classes, to allow them to log their operations and errors into logFile


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
            case "4":
                try {
                    connexion = DriverManager.getConnection(connectionString);

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

                    connexion = DriverManager.getConnection(connectionString);

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
//                try {
//                    String database = "resources/database.json";
//                    String nomFichier = "patate.zip";
//                    Zipper zipper = new Zipper(nomFichier, database, "resources/Log.txt");
//                    zipper.ZipFiles();
//                    break;
//                } catch (IOException e){
//                    e.printStackTrace();
//                }
                try {
                    Library librairie = new Library();
                    new LibraryInitializer().initializeCollection(librairie);
                    JsonConverter jo = new JsonConverter();
                    String test = jo.ConvertIntoJson(librairie);
                    System.out.println(test);

                    DataWriter martine = new DataWriter("LibrairieToJSON.json");
                    martine.WriteFile(test, false);

                    DataReader martin = new DataReader("LibrairieToJSON.json");
                    test = martin.ReadWholeFile();
                    System.out.println(librairie.Stringify());
                    Library librairie2 = new Library();
                    librairie2 = jo.InstanciateLibraryFromJson(test);

                    int i = 0;
                }catch(ExecutionControl.NotImplementedException e){
                    e.printStackTrace();
                }catch(FileNotFoundException e){
                    e.printStackTrace();
                }
                break;
            case "1":
                try {
                    Library librairie = new Library();
                    new LibraryInitializer().initializeCollection(librairie);

                    Editor editeur = librairie.mBookLibrary.firstElement().getEditor();
                    Book livre = librairie.mBookLibrary.firstElement();
                    Magazine magazine = librairie.mMagazineLibrary.firstElement();
                    CD cd = librairie.mCDLibrary.firstElement();
                    DVD dvd = librairie.mDVDLibrary.firstElement();

                    DatabaseImporter importer = new DatabaseImporter(connectionString);
                    importer.InsertIntoEditors(editeur);

                    importer.InsertIntoBooks(livre, 2);
                    importer.InsertIntoDVD(dvd, 2);
                    importer.InsertIntoCD(cd, 1);
                    importer.InsertIntoMagazines(magazine, 2);
                } catch (Exception e) {
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
