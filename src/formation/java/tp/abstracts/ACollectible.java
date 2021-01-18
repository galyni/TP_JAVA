package formation.java.tp.abstracts;

import formation.java.tp.interfaces.IDisplayable;
import formation.java.tp.model.Editor;

import java.util.Date;

public abstract class ACollectible implements IDisplayable
{
    public boolean mBorrowable ;
    public boolean mBorrowed ;
    public String  mTitle ;
    public Editor  mEditor ;
    public Date    mPublishDate ;

    @Override
    public String Stringify()
    {
        String lToreturn = (this.mTitle +
                            " : \n\t édité par : " + this.mEditor + ", le " + this.mPublishDate.toString() +
                            ".\n\t" + (this.mBorrowable ? "non empruntable, " : "empruntable, " ) + ( ( this.mBorrowable ? ( this.mBorrowed ? " déjà sorti ": "disponible à l'emprunt " ) : " " ) ) +
                            "\n\t") ;
        return lToreturn ;
    }
}
