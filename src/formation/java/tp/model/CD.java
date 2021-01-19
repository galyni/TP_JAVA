package formation.java.tp.model;

import formation.java.tp.abstracts.ACollectible;
import formation.java.tp.interfaces.IDisplayable;
import formation.java.tp.utils.eCDType;

import java.util.Date;

public class CD extends ACollectible implements IDisplayable
{
    private double  mLength;
    private eCDType mCDType;

    public CD(){}
    public CD(String pTitle, Editor pEditor, Date pPublishDate, double pLength, eCDType pCDType )
    {
        this.mPublishDate = pPublishDate ;
        this.mBorrowed    = false ;
        this.mBorrowable  = true ;
        this.mEditor      = pEditor ;
        this.mTitle       = pTitle ;
        this.mLength      = pLength ;
        this.mCDType      = pCDType ;
    }

    public Date getPublishDate() {return this.mPublishDate ;}
    public boolean isBorrowed()  {return this.mBorrowed ;}
    public void borrowDVD()      {this.mBorrowed = true ;}
    public Editor getEditor()    {return this.mEditor ;}
    public String getTitle()     {return this.mTitle ;}
    public double getDVDLength() {return this.mLength ;}
    public eCDType getCDType()   {return this.mCDType ;}

    @Override
    public String Stringify()
    {
        return ( "\n" + super.Stringify() +
                 "cette article est un CD : " + this.mCDType.name() +
                 "\n\td'une durée de " + this.mLength + "min") ;
    }
}
