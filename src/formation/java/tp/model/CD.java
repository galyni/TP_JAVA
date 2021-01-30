package formation.java.tp.model;

import formation.java.tp.abstracts.ACollectible;
import formation.java.tp.interfaces.IDisplayable;
import formation.java.tp.utils.eCDType;

import java.time.LocalDate;

public class CD extends ACollectible implements IDisplayable
{
    private String  mLength;
    private eCDType mCDType;
    private int mNumberOfTracks;

    public CD(){}
    public CD(String pTitle, Editor pEditor, LocalDate pPublishDate, String pLength, eCDType pCDType, int pNumberOfTracks )
    {
        this.mPublishDate = pPublishDate ;
        this.mBorrowed    = false ;
        this.mBorrowable  = true ;
        this.mEditor      = pEditor ;
        this.mTitle       = pTitle ;
        this.mLength      = pLength ;
        this.mCDType      = pCDType ;
        this.mNumberOfTracks = pNumberOfTracks;
    }

    public LocalDate getPublishDate() {return this.mPublishDate ;}
    public boolean isBorrowed()           {return this.mBorrowed ;}
    public boolean isBorrowable()           {return this.mBorrowable ;}
    public void borrowDVD()               {this.mBorrowed = true ;}
    public Editor getEditor()             {return this.mEditor ;}
    public String getTitle()              {return this.mTitle ;}
    public String getDVDLength()          {return this.mLength ;}
    public eCDType getCDType()            {return this.mCDType ;}
    public int getCDNumberOfTracks() {
        return this.mNumberOfTracks;
    }
    public int getCDTypeAsInt() {
        return this.mCDType.ordinal();
    }


    @Override
    public String Stringify()
    {
        return ( "\n" + super.Stringify() +
                 "cette article est un CD : " + this.mCDType.name() +
                 "\n\td'une durée de " + this.mLength + "min") ;
    }
}
