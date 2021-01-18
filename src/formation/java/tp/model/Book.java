package formation.java.tp.model;

import formation.java.tp.abstracts.ACollectible;
import formation.java.tp.interfaces.IDisplayable;
import formation.java.tp.utils.eBookType;

public class Book extends ACollectible implements IDisplayable
{
    public int       mNumberOfPages;
    public String    mAuthor;
    public eBookType mBookType;
    public boolean   mIsTraduct;

    public Book(){}
    public Book(int pNumberOfPage, String pAuthor, eBookType pBookType, boolean pIsTraduct)
    {
        mNumberOfPages = pNumberOfPage ;
        mAuthor        = pAuthor ;
        mBookType      = pBookType ;
        mIsTraduct     = pIsTraduct ;
    }

    @Override
    public String Stringify()
    {
        String lToReturn = ( super.Stringify() +
                             "cette article est un livre de " + this.mBookType.name() + " de " + this.mNumberOfPages + " pages, " + " écrit par " + this.mAuthor +
                             "\n\t" + ( this.mIsTraduct ? "traduit en français" : "non traduit en français" ) ) ;
        return lToReturn ;
    }
}
