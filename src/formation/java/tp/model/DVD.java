package formation.java.tp.model;

import formation.java.tp.abstracts.ACollectible;
import formation.java.tp.interfaces.IDisplayable;
import formation.java.tp.utils.eDVDType;

public class DVD extends ACollectible implements IDisplayable
{
    public float    mLength;
    public eDVDType mDVDType;
    public boolean  mIsAudioDescriptible;

    public DVD (){}
    public DVD(float pLength, eDVDType pDVDType, boolean pIsAudioDescriptible)
    {
        mLength              = pLength ;
        mDVDType             = pDVDType ;
        mIsAudioDescriptible = pIsAudioDescriptible ;
    }

    @Override
    public String Stringify()
    {
        String lToReturn = ( super.Stringify() +
                             "cette article est un CD : " + this.mDVDType.name() +
                             "\n\td'une durée de " + this.mLength + "min" +
                             "\n\t" + ( this.mIsAudioDescriptible ? "disponible pour les malentendants" : "indisponible pour les malentendants" ) ) ;
        return super.Stringify();
    }
}
