package formation.java.tp.model;

import formation.java.tp.abstracts.ACollectible;
import formation.java.tp.interfaces.IDisplayable;
import formation.java.tp.utils.eMagazineType;
import formation.java.tp.utils.ePublishmentFrequency;

public class Magazine extends ACollectible implements IDisplayable
{
    public int                   mNumberOfPage;
    public String                mAuthor;
    public eMagazineType         mMagType;
    public ePublishmentFrequency mFrequency;

    public Magazine (){}

    @Override
    public String Stringify()
    {
        String lToReturn = ( super.Stringify() +
                             "cette article est un magazine " + this.mFrequency +" sur " + this.mMagType.name() + " de " + this.mNumberOfPage + " pages, " + " écrit par " + this.mAuthor) ;
        return lToReturn;
    }
}
