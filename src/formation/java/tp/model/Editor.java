package formation.java.tp.model;

import formation.java.tp.interfaces.IDisplayable;

public class Editor implements IDisplayable
{
    private String mStreet;
    private String mCity;
    private String mCountry;
    private String mName;
    private String mSiret;

    public Editor(){}
    public Editor( String pStreet, String pCity, String pCountry, String pName, String pSiret)
    {
        this.mStreet  = pStreet ;
        this.mCity    = pCity ;
        this.mCountry = pCountry ;
        this.mName    = pName ;
        this. mSiret  = pSiret ;
    }

    public String getEditorName()    {return this.mName ;}
    public String getEditorCity()    {return this.mCity ;}
    public String getEditorCountry() {return this.mCountry ;}
    public String getEditorSiret()   {return this.mSiret ;}
    public String getSiegeStreet()   {return this.mStreet ;}

    public void setEditorCity(String pCity)       {this.mCity = pCity ;}
    public void setEditorCountry(String pCountry) {this.mCountry = pCountry ;}
    public void setSiegeStreet(String pStreet)    {this.mStreet = pStreet ;}

    @Override
    public String Stringify()
    {
        return ( "\n" + this.mName + " : " +
                 "\n\tVille : " + this.mCity +
                 "\n\tRue : " + this.mStreet +
                 "\n\tPays : " + this.mCountry +
                 "\n\tSiret : " + this.mSiret) ;
    }
}
