package formation.java.tp.fileClasses;

import formation.java.tp.abstracts.AFileWriter;
import formation.java.tp.model.Library;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Serializer<T> extends AFileWriter
{//TODO check all class implementation
    // TODO: 30/01/2021 passer les paramètres dans Serialize au lieu du constructeur 
    ObjectOutputStream mOutput ;
    T                  mObjectToSerialize ;

    public Serializer(){}
    public Serializer(String pFilepath, T pObject)
    {
        this.mFilePath     = pFilepath ;
        mObjectToSerialize = pObject ;
    }

    public void Serialize()
    {
        try
        {
            this.mOutput = new ObjectOutputStream( Files.newOutputStream( Paths.get( this.mFilePath ), StandardOpenOption.CREATE ) ) ;
        }
        catch (IOException e)
        {
            System.out.println("Unable to open file.." + e.getMessage());
            e.printStackTrace();
        }
        try
        {
            this.mOutput.writeObject(this.mObjectToSerialize);
            this.mOutput.close();
        }
        catch (IOException e)
        {
            System.out.println("Unable to write into file.." + e.getMessage());
            e.printStackTrace();
        }
    }
}
