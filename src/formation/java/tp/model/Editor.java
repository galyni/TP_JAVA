package formation.java.tp.model;

import formation.java.tp.interfaces.IDisplayable;

import java.io.Serializable;



public class Editor implements IDisplayable, Serializable
{
    public static int sEditorsID = 0;

    private String mStreet;
    private String mCity;
    private String mCountry;
    private String mName;
    private String mSiret;
    private int EditorID;

    public Editor(){}
    public Editor( String pStreet, String pCity, String pCountry, String pName, String pSiret)
    {
        this.mStreet   = pStreet ;
        this.mCity     = pCity ;
        this.mCountry  = pCountry ;
        this.mName     = pName ;
        this. mSiret   = pSiret ;
        sEditorsID ++;
        this.EditorID = sEditorsID;
    }

    public String getEditorName()    {return this.mName ;}
    public String getEditorCity()    {return this.mCity ;}
    public String getEditorCountry() {return this.mCountry ;}
    public String getEditorSiret()   {return this.mSiret ;}
    public String getSiegeStreet()   {return this.mStreet ;}
    public int getEditorID()        {return this.EditorID;}

    public void setEditorCity(String pCity)       {this.mCity = pCity ;}
    public void setEditorCountry(String pCountry) {this.mCountry = pCountry ;}
    public void setSiegeStreet(String pStreet)    {this.mStreet = pStreet ;}
    public void setEditorName(String pName)       {this.mName = pName ;}
    public void setEditorID(int pEditorID)        {this.EditorID = pEditorID;}

    @Override
    public String Stringify()
    {
        return ( "\n" + this.mName + " : " +
                "\n\tEditorID : " + this.EditorID +
                 "\n\tVille : " + this.mCity +
                 "\n\tRue : " + this.mStreet +
                 "\n\tPays : " + this.mCountry +
                 "\n\tSiret : " + this.mSiret) ;
    }
}
