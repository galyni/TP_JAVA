package formation.java.tp.model;

import formation.java.tp.abstracts.ACollectible;
import formation.java.tp.interfaces.IDisplayable;
import formation.java.tp.utils.eCDType;

public class CD extends ACollectible implements IDisplayable
{
    public float   mLength;
    public eCDType mCDType;

    public CD(){}

    @Override
    public String Stringify()
    {
        String lToReturn = ( super.Stringify() +
                              "cette article est un CD : " + this.mCDType.name() +
                              "\n\td'une durée de " + this.mLength + "min") ;
        return lToReturn ;
    }
}
