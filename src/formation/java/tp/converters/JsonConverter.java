package formation.java.tp.converters;

import formation.java.tp.model.Library;
import jdk.jshell.spi.ExecutionControl;
import org.json.JSONObject;

public class JsonConverter<T>
{
    public JsonConverter(){}

    public String ConvertIntoJson(T pObjectToConvert) throws ExecutionControl.NotImplementedException
    {
        String lJsonifiedObject = "" ;
        if(pObjectToConvert instanceof Library)
        {
            JSONObject lJsonRoot    = new JSONObject() ;
            JSONObject lLibraryRoot = new JSONObject() ;

            lLibraryRoot.put("Magazines", ( (Library) pObjectToConvert ).mMagazineLibrary) ;
            lLibraryRoot.put("Books", ( (Library) pObjectToConvert ).mBookLibrary) ;
            lLibraryRoot.put("CDs", ( (Library) pObjectToConvert ).mCDLibrary) ;
            lLibraryRoot.put("DVDs", ( (Library) pObjectToConvert ).mDVDLibrary) ;
            lJsonRoot.put("Library", lLibraryRoot) ;

            lJsonifiedObject = lJsonRoot.toString() ;
        }
        else
        {
            throw new ExecutionControl.NotImplementedException("Method can only Jsonify Library objects for now") ;
        }
        return lJsonifiedObject ;
    }
}
