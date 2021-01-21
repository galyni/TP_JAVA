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
    private ObjectInputStream inputStream ;
    private T                 mObjects ;

    public Deserializer() {}
    public Deserializer(String pFilepath)
    {
        this.mFilePath = pFilepath ;
    }

    public T Deserialize()
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
            this.mObjects = (T)inputStream.readObject() ;
        }
        catch (IOException e)
        {
            System.out.println("invalid cast conversion during reading... " + e.getMessage());
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
        return mObjects ;
    }
}
