package formation.java.tp.model;

import formation.java.tp.abstracts.ACollectible;
import formation.java.tp.interfaces.IDisplayable;
import formation.java.tp.utils.eMagazineType;
import formation.java.tp.utils.ePublishmentFrequency;

import java.util.Date;

public class Magazine extends ACollectible implements IDisplayable
{
    private int                   mNumberOfPages;
    private String                mAuthor;
    private eMagazineType         mMagType;
    private ePublishmentFrequency mFrequency;

    public Magazine (){}
    public Magazine ( String pTitle, Editor pEditor, Date pPublishDate, int pNumberOfPages, String pAuthor, eMagazineType pMagazineType, ePublishmentFrequency pFrequency )
    {
        this.mPublishDate   = pPublishDate ;
        this.mBorrowed      = false ;
        this.mBorrowable    = false ;
        this.mEditor        = pEditor ;
        this.mTitle         = pTitle ;
        this.mNumberOfPages = pNumberOfPages ;
        this.mAuthor        = pAuthor ;
        this.mMagType       = pMagazineType ;
        this.mFrequency     = pFrequency ;
    }

    public Date getPublishDate()           {return this.mPublishDate ;}
    public boolean isBorrowed()            {return this.mBorrowed ;}
    public void borrowMagazine()           {this.mBorrowed = true ;}
    public Editor getEditor()              {return this.mEditor ;}
    public String getTitle()               {return this.mTitle ;}
    public int getNumberOfPages()          {return this.mNumberOfPages ;}
    public String getAuthor()              {return this.mAuthor ;}
    public eMagazineType getMagazineType() {return this.mMagType ;}

    @Override
    public String Stringify()
    {
        return ( "\n" + super.Stringify() +
                 "cette article est un magazine " + this.mFrequency +" sur " + this.mMagType.name() + " de " + this.mNumberOfPages + " pages, " + " écrit par " + this.mAuthor) ;
    }
}
