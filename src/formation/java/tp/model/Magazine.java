package formation.java.tp.model;

import formation.java.tp.abstracts.ACollectible;
import formation.java.tp.utils.eMagazineType;
import formation.java.tp.utils.ePublishmentFrequency;

public class Magazine extends ACollectible
{
    public int                   mNumberOfPage;
    public String                mAuthor;
    public eMagazineType         mMagType;
    public ePublishmentFrequency mFrequency;

    public Magazine (){}
}
