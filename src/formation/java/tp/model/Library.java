package formation.java.tp.model;

import formation.java.tp.interfaces.ICollection;
import formation.java.tp.interfaces.IDisplayable;

import java.io.Serializable;
import java.util.Vector;

public class Library implements ICollection<Library>, IDisplayable, Serializable
{
    public Vector<CD>       mCDLibrary ;
    public Vector<DVD>      mDVDLibrary ;
    public Vector<Book>     mBookLibrary ;
    public Vector<Magazine> mMagazineLibrary ;

    public Library()
    {
        this.mCDLibrary       = new Vector<CD>() ;
        this.mDVDLibrary      = new Vector<DVD>() ;
        this.mMagazineLibrary = new Vector<Magazine>() ;
        this.mBookLibrary     = new Vector<Book>() ;
    }

    @Override
    public void Borrow()
    {
        //TODO CRUD delete request
    }

    @Override
    public void Return(Library pToReturn)
    {
        //TODO CRUD insert request
    }

    @Override
    public String Stringify()
    {
        String lToReturn = "" ;
        for (byte i = 0; i < mCDLibrary.size(); ++i)
        {
            lToReturn += mCDLibrary.elementAt(i).Stringify() ;
        }
        for (byte i = 0; i < mDVDLibrary.size(); ++i)
        {
            lToReturn += mDVDLibrary.elementAt(i).Stringify() ;
        }
        for (byte i = 0; i < mBookLibrary.size(); ++i)
        {
            lToReturn += mBookLibrary.elementAt(i).Stringify() ;
        }
        for (byte i = 0; i < mMagazineLibrary.size(); ++i)
        {
            lToReturn += mMagazineLibrary.elementAt(i).Stringify() ;
        }
        return lToReturn;
    }
}
