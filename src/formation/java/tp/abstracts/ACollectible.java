package formation.java.tp.abstracts;

import formation.java.tp.interfaces.IDisplayable;
import formation.java.tp.model.Editor;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class ACollectible implements IDisplayable, Serializable
{
    protected boolean       mBorrowable ;
    protected boolean       mBorrowed ;
    protected String        mTitle ;
    protected Editor        mEditor ;
    protected LocalDate mPublishDate ;

    @Override
    public String Stringify()
    {
        return (this.mTitle +
                " : \n\t édité par : " + this.mEditor.Stringify() + this.mEditor.getEditorName() + ", le " + this.mPublishDate.toString() +
                ".\n\t" + (this.mBorrowable ? "non empruntable, " : "empruntable, " ) + ( ( this.mBorrowable ? ( this.mBorrowed ? " déjà sorti ": "disponible à l'emprunt " ) : " " ) ) +
                "\n\t") ;
    }

    public void setEditorID(int pEditorID){
        this.mEditor.setEditorID(pEditorID);
    }
}
