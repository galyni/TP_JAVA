package formation.java.tp.converters;

import formation.java.tp.model.*;
import formation.java.tp.utils.*;
import jdk.jshell.spi.ExecutionControl;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

public class JsonConverter<T>
{
    public JsonConverter(){}

    public String ConvertIntoJson(T pObjectToConvert) throws ExecutionControl.NotImplementedException
    {
        String lJsonifiedObject = "" ;
        if(pObjectToConvert instanceof Library)
        {
            JSONObject lJsonRoot    = new JSONObject() ;
//            JSONObject lLibraryRoot = new JSONObject() ;

            lJsonRoot.put("Magazines", ( (Library) pObjectToConvert ).mMagazineLibrary) ;
            lJsonRoot.put("Books", ( (Library) pObjectToConvert ).mBookLibrary) ;
            lJsonRoot.put("CDs", ( (Library) pObjectToConvert ).mCDLibrary) ;
            lJsonRoot.put("DVDs", ( (Library) pObjectToConvert ).mDVDLibrary) ;
//            lJsonRoot.put("Library", lLibraryRoot) ;

            lJsonifiedObject = lJsonRoot.toString() ;
        }
        else
        {
            throw new ExecutionControl.NotImplementedException("Method can only convert Library objects into Json for now") ;
        }
        return lJsonifiedObject ;
    }
    public Library InstanciateLibraryFromJson(String pJsonLibrary)
    {
        Library lLibrary             = new Library() ;
        JSONObject lJsonRoot         = new JSONObject(pJsonLibrary) ;
//        JSONObject lJsonLibrary      = new JSONObject( lJsonRoot.get("Library").toString() ) ;
        JSONArray lJsonDVDArray      = new JSONArray( lJsonRoot.getJSONArray("DVDs").toString() ) ;
        JSONArray lJsonCDArray       = new JSONArray( lJsonRoot.getJSONArray("CDs").toString() ) ;
        JSONArray lJsonBookArray     = new JSONArray( lJsonRoot.getJSONArray("Books").toString() ) ;
        JSONArray lJsonMagazineArray = new JSONArray( lJsonRoot.getJSONArray("Magazines").toString() ) ;
        JSONObject lJsonEditor ;

        for(int i = 0; i < lJsonDVDArray.length(); ++i)
        {
            JSONObject lJsonDVD = lJsonDVDArray.getJSONObject(i) ;
            lJsonEditor         = lJsonDVD.getJSONObject("editor") ;

            DVD lDVD = new DVD(
                    lJsonDVD.get("title").toString(),
                    new Editor(
                    lJsonEditor.get("siegeStreet").toString(),
                    lJsonEditor.get("editorCity").toString(),
                    lJsonEditor.get("editorCountry").toString(),
                    lJsonEditor.get("editorName").toString(),
                    lJsonEditor.get("editorSiret").toString(),
                    lJsonEditor.get("editorZipcode").toString(),
                    (int)lJsonEditor.get("editorID")
                    ),
                    LocalDateTime.parse( lJsonDVD.get("publishDate").toString() ),
                    lJsonDVD.getDouble("DVDLength"),
                    lJsonDVD.getEnum( eDVDType.class,"DVDType"),
                    lJsonDVD.getBoolean("audioDescriptible")
            ) ;
            lDVD.setEditorID(lJsonEditor.getInt("editorID"));
            lLibrary.mDVDLibrary.add(lDVD) ;
        }
        for(int i = 0; i < lJsonCDArray.length(); ++i)
        {
            JSONObject lJsonCD = lJsonCDArray.getJSONObject(i);
            lJsonEditor = lJsonCD.getJSONObject("editor");

            CD lCD = new CD(
                    lJsonCD.get("title").toString(),
                    new Editor(
                            lJsonEditor.get("siegeStreet").toString(),
                            lJsonEditor.get("editorCity").toString(),
                            lJsonEditor.get("editorCountry").toString(),
                            lJsonEditor.get("editorName").toString(),
                            lJsonEditor.get("editorSiret").toString(),
                            lJsonEditor.get("editorZipcode").toString(),
                            (int)lJsonEditor.get("editorID")
                    ),
                    LocalDateTime.parse(lJsonCD.get("publishDate").toString()),
                    lJsonCD.get("DVDLength").toString(),
                    lJsonCD.getEnum(eCDType.class, "CDType"),
                    lJsonCD.getInt("CDNumberOfTracks")
            );
            lLibrary.mCDLibrary.add(lCD);
        }
        for(int i = 0; i < lJsonBookArray.length(); ++i)
        {
            JSONObject lJsonBook = lJsonBookArray.getJSONObject(i) ;
            lJsonEditor          = lJsonBook.getJSONObject("editor") ;

            Book lBook = new Book(
                    lJsonBook.get("title").toString(),
                    new Editor(
                            lJsonEditor.get("siegeStreet").toString(),
                            lJsonEditor.get("editorCity").toString(),
                            lJsonEditor.get("editorCountry").toString(),
                            lJsonEditor.get("editorName").toString(),
                            lJsonEditor.get("editorSiret").toString(),
                            lJsonEditor.get("editorZipcode").toString(),
                            (int)lJsonEditor.get("editorID")
                    ),
                    LocalDateTime.parse( lJsonBook.get("publishDate").toString() ),
                    lJsonBook.getInt("numberOfPages"),
                    lJsonBook.get("author").toString(),
                    lJsonBook.getEnum( eBookType.class,"bookType"),
                    lJsonBook.getBoolean( "borrowed")
            ) ;
            lLibrary.mBookLibrary.add(lBook) ;
        }
        for(int i = 0; i < lJsonMagazineArray.length(); ++i)
        {
            JSONObject lJsonMagazine = lJsonMagazineArray.getJSONObject(i) ;
            lJsonEditor              = lJsonMagazine.getJSONObject("editor") ;

            Magazine lMagazine = new Magazine(
                    lJsonMagazine.get("title").toString(),
                    new Editor(
                            lJsonEditor.get("siegeStreet").toString(),
                            lJsonEditor.get("editorCity").toString(),
                            lJsonEditor.get("editorCountry").toString(),
                            lJsonEditor.get("editorName").toString(),
                            lJsonEditor.get("editorSiret").toString(),
                            lJsonEditor.get("editorZipcode").toString(),
                            (int)lJsonEditor.get("editorID")
                    ),
                    LocalDateTime.parse( lJsonMagazine.get("publishDate").toString() ),
                    lJsonMagazine.getInt("numberOfPages"),
                    lJsonMagazine.get("author").toString(),
                    lJsonMagazine.getEnum( eMagazineType.class,"magazineType"),
                    ePublishmentFrequency.Daily
//                    lJsonMagazine.getEnum( ePublishmentFrequency.class,"magazineType")
            ) ;
            lLibrary.mMagazineLibrary.add(lMagazine) ;
        }
        return lLibrary ;
    }
}
