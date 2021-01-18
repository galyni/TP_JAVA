package formation.java.tp.model;

import formation.java.tp.abstracts.ACollectible;
import formation.java.tp.interfaces.IDisplayable;
import formation.java.tp.utils.eBookType;

import java.util.Date;

public class Book extends ACollectible implements IDisplayable
{
    private int       mNumberOfPages;
    private String    mAuthor;
    private eBookType mBookType;
    private boolean   mIsTraduct;

    public Book(){}
    public Book(String pTitle, Editor pEditor, Date pPublishDate, int pNumberOfPage, String pAuthor, eBookType pBookType, boolean pIsTraduct)
    {
        this.mPublishDate   = pPublishDate ;
        this.mBorrowed      = false ;
        this.mBorrowable    = true ;
        this.mEditor        = pEditor ;
        this.mTitle         = pTitle ;
        this.mNumberOfPages = pNumberOfPage ;
        this.mAuthor        = pAuthor ;
        this.mBookType      = pBookType ;
        this.mIsTraduct     = pIsTraduct ;
    }

    public Date getPublishDate()   {return this.mPublishDate ;}
    public boolean isBorrowed()    {return this.mBorrowed ;}
    public void borrowBook()       {this.mBorrowed = true ;}
    public Editor getEditor()      {return this.mEditor ;}
    public String getTitle()       {return this.mTitle ;}
    public int getNumberOfPages()  {return this.mNumberOfPages ;}
    public String getAuthor()      {return this.mAuthor ;}
    public eBookType getBookType() {return this.mBookType ;}
    public boolean isTraduct()     {return this.mIsTraduct ;}

    @Override
    public String Stringify()
    {
        return ( super.Stringify() +
                 "cette article est un livre de " + this.mBookType.name() + " de " + this.mNumberOfPages + " pages, " + " écrit par " + this.mAuthor +
                 "\n\t" + ( this.mIsTraduct ? "traduit en français" : "non traduit en français" ) ) ;
    }
}
