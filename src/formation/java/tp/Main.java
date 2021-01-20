package formation.java.tp;

import formation.java.tp.fileClasses.DBSerializer;
import formation.java.tp.fileClasses.Deserializer;
import formation.java.tp.fileClasses.Serializer;
import formation.java.tp.model.Book;
import formation.java.tp.model.Editor;
import formation.java.tp.model.Library;
import formation.java.tp.utils.LibraryInitializer;
import formation.java.tp.utils.eBookType;
import org.json.JSONArray;
import org.json.JSONObject;
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



/*
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

        /* ----- JSONify ----- */
/*
        Library lLibrary = new Library() ;
        new LibraryInitializer().initializeCollection(lLibrary) ;
        JSONObject lJsonRoot = new JSONObject( ) ;

        JSONArray lJsonArray = new JSONArray(lLibrary.mBookLibrary.toArray());
        lJsonArray.put(lLibrary.mMagazineLibrary);
        lJsonArray.put(lLibrary.mCDLibrary);
        lJsonArray.put(lLibrary.mDVDLibrary);
        lJsonRoot.put("Magazines", lLibrary.mMagazineLibrary) ;
        lJsonRoot.put("Books", lLibrary.mBookLibrary) ;
        lJsonRoot.put("CDs", lLibrary.mCDLibrary) ;
        lJsonRoot.put("DVDs", lLibrary.mDVDLibrary) ;

        System.out.println(lJsonRoot.toString());
*/      //TODO David, finish this, create library property to contains all arrays, write it into file, and create an object to contains this logic
        /* ----- end JSONify ----- */

        /* ----- object serialization/deserialization example ----- */
/*
        String lObjFilePath                 = "resources/serializedObj.txt" ;
        Library lLibrary                    = new Library() ;
        Deserializer<Library> lDeserializer = new Deserializer<Library>(lObjFilePath) ;
        Library lLibrary2 ;

        new LibraryInitializer().initializeCollection(lLibrary) ;

        Serializer<Library> lSerializer = new Serializer<Library>(lObjFilePath, lLibrary ) ;
        lSerializer.Serialize() ;

        lLibrary2 = lDeserializer.Deserialize() ;
        System.out.println( lLibrary2.Stringify() ) ;
*/
        /* ----- end serialization/deserialization example ----- */
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
            if( !pArgs[1].endsWith(".txt") )
            {
                System.out.println("invalid file extensions...");
                Usage() ;
                return -1 ;
            }
        }
        else
        {
            if( !pArgs[1].endsWith(".json") )
            {
                System.out.println("invalid file extensions...");
                Usage() ;
                return -1 ;
            }
        }//at this point args[1] seem to be ok
        if( !pArgs[2].endsWith(".properties") )
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
