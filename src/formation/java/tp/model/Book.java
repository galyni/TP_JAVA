package formation.java.tp.model;

import formation.java.tp.abstracts.ACollectible;
import formation.java.tp.interfaces.IDisplayable;
import formation.java.tp.utils.eBookType;

import java.time.LocalDate;

public class Book extends ACollectible implements IDisplayable
{
    private int       mNumberOfPages;
    private String    mAuthor;
    private eBookType mBookType;
    private boolean   mIsTraduct;

    public Book(){}
    public Book(String pTitle, Editor pEditor, LocalDate pPublishDate, int pNumberOfPage, String pAuthor, eBookType pBookType, boolean pIsTraduct)
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

    public Book(String pTitle, LocalDate pPublishDate, int pNumberOfPage, String pAuthor, eBookType pBookType, boolean pIsTraduct, boolean pBorrowed)
    {
        this.mPublishDate   = pPublishDate ;
        this.mBorrowed      = pBorrowed;
        this.mBorrowable    = true ;
        this.mTitle         = pTitle ;
        this.mNumberOfPages = pNumberOfPage ;
        this.mAuthor        = pAuthor ;
        this.mBookType      = pBookType ;
        this.mIsTraduct     = pIsTraduct ;
    }

    public LocalDate getPublishDate() {return this.mPublishDate ;}
    public boolean isBorrowed()           {return this.mBorrowed ;}
    public void borrowBook()              {this.mBorrowed = true ;}
    public Editor getEditor()             {return this.mEditor ;}
    public String getTitle()              {return this.mTitle ;}
    public int getNumberOfPages()         {return this.mNumberOfPages ;}
    public String getAuthor()             {return this.mAuthor ;}
    public eBookType getBookType()        {return this.mBookType ;}
    public boolean isTraduct()            {return this.mIsTraduct ;}
    public boolean isBorrowable()         {return this.mBorrowable ;}
    public int getBookTypeAsInt() {
        return mBookType.ordinal();
    }

    public void setEditor(Editor editeur){
        this.mEditor = editeur;
    }

    @Override
    public String Stringify()
    {
        return ( "\n" + super.Stringify() +
                 "cette article est un livre de " + this.mBookType.name() + " de " + this.mNumberOfPages + " pages, " + " écrit par " + this.mAuthor +
                 "\n\t" + ( this.mIsTraduct ? "traduit en français" : "non traduit en français" ) ) ;
    }
}
