package formation.java.tp.model;

import formation.java.tp.abstracts.ACollectible;
import formation.java.tp.interfaces.IDisplayable;
import formation.java.tp.utils.eDVDType;

import java.time.LocalDate;

public class DVD extends ACollectible implements IDisplayable
{
    private String mLength;
    private eDVDType mDVDType;
    private boolean  mIsAudioDescriptible;

    public DVD (){}
    public DVD(String pTitle, Editor pEditor, LocalDate pPublishDate, String pLength, eDVDType pDVDType, boolean pIsAudioDescriptible )
    {
        this.mPublishDate         = pPublishDate ;
        this.mBorrowed            = false ;
        this.mBorrowable          = true ;
        this.mEditor              = pEditor ;
        this.mTitle               = pTitle ;
        this.mLength              = pLength ;
        this.mDVDType             = pDVDType ;
        this.mIsAudioDescriptible = pIsAudioDescriptible ;
    }

    public DVD(String pTitle, LocalDate pPublishDate, String pLength, eDVDType pDVDType, boolean pIsAudioDescriptible )
    {
        this.mPublishDate         = pPublishDate ;
        this.mBorrowed            = false ;
        this.mBorrowable          = true ;
        this.mTitle               = pTitle ;
        this.mLength              = pLength ;
        this.mDVDType             = pDVDType ;
        this.mIsAudioDescriptible = pIsAudioDescriptible ;
    }

    public LocalDate getPublishDate() {return this.mPublishDate ;}
    public boolean isBorrowed()           {return this.mBorrowed ;}
    public boolean isBorrowable()         {return this.mBorrowable ;}
    public void borrowDVD()               {this.mBorrowed = true ;}
    public Editor getEditor()             {return this.mEditor ;}
    public String getTitle()              {return this.mTitle ;}
    public String getDVDLength()          {return this.mLength ;}
    public eDVDType getDVDType()          {return this.mDVDType ;}
    public boolean isAudioDescriptible()  {return this.mIsAudioDescriptible ;}
    public int getDVDTypeAsInt() {
        return this.mDVDType.ordinal();
}
    public void setEditor(Editor editeur){
        this.mEditor = editeur;
    }


    @Override
    public String Stringify()
    {
        String lToReturn = ( "\n" + super.Stringify() +
                             "cette article est un CD : " + this.mDVDType.name() +
                             "\n\td'une durée de " + this.mLength + "min" +
                             "\n\t" + ( this.mIsAudioDescriptible ? "disponible pour les malentendants" : "indisponible pour les malentendants" ) ) ;
        return super.Stringify();
    }
}
