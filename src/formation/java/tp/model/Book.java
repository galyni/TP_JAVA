package formation.java.tp.model;

import formation.java.tp.abstracts.ACollectible;
import formation.java.tp.utils.eBookType;

public class Book extends ACollectible
{
    public int       mNumberOfPages;
    public String    mAuthor;
    public eBookType mBookType;
    public boolean   mIsTraduct;

    public Book(){}
}
