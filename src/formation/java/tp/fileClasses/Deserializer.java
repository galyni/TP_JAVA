package formation.java.tp.fileClasses;

import formation.java.tp.abstracts.AFileReader;
import formation.java.tp.model.Library;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Vector;

public class Deserializer<T> extends AFileReader
{
    private T                 mWaitedObject ;
    private ObjectInputStream inputStream ;
    private Vector<T>         mObjects ;

    public Deserializer() {}
    public Deserializer(String pFilepath, T pWaitedObject)
    {
        this.mFilePath     = pFilepath ;
        this.mWaitedObject = pWaitedObject ;
    }

    public void Deserialize()
    {
        try
        {
            inputStream = new ObjectInputStream( Files.newInputStream( Paths.get( this.mFilePath ), StandardOpenOption.READ ) );
        }
        catch (IOException e)
        {
            System.out.println("Unable to open file to read... " + e.getMessage());
            e.printStackTrace();
        }
        try
        {
            this.mObjects = (Vector)inputStream.readObject() ;
        }
        catch (IOException e)
        {
            System.out.println("Problem during file reading... " + e.getMessage());
            e.printStackTrace();
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Unable to found corresponding Class... " + e.getMessage());
            e.printStackTrace();
        }
        try
        {
            inputStream.close();
        }
        catch (IOException e)
        {
            System.out.println("Unable to close file... " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void DisplayContent()
    {
        if( this.mWaitedObject instanceof Library)
        {
            for (T library : this.mObjects)
            {
                System.out.println( ( (Library)library ).Stringify() );
            }
        }
        else
        {
            System.out.println("Unable to know if stringify() method exist");
        }
    }
}
