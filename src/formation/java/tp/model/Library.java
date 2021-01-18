package formation.java.tp.model;

import formation.java.tp.interfaces.ICollection;

import java.util.Vector;

public class Library implements ICollection<Library>
{
    public Vector<CD>       mCDLibrary ;
    public Vector<DVD>      mDVDLibrary ;
    public Vector<Book>     mBookLibrary ;
    public Vector<Magazine> mMagazineLibrary ;

    public Library(){}

    @Override
    public void Borrow()
    {

    }

    @Override
    public void Return(Library pToReturn)
    {

    }
}
