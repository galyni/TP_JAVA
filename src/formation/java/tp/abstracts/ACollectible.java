package formation.java.tp.abstracts;

import formation.java.tp.model.Editor;

import java.util.Date;

public abstract class ACollectible
{
    public boolean mBorrowable;
    public boolean mBorrowed;
    public String  mTitle;
    public Editor  mEditor;
    public Date    mPublishDate;
}
