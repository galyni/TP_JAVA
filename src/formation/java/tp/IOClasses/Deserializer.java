package formation.java.tp.IOClasses;

import formation.java.tp.abstracts.AFileReader;
import formation.java.tp.utils.LogWriter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Deserializer<T> extends AFileReader
{
    private ObjectInputStream inputStream ;
    private T                 mObjects ;
    private LogWriter         mLogWriter ;

    public Deserializer() {}
    public Deserializer(String pFilepath)
    {
        this.mFilePath  = pFilepath ;
        this.mLogWriter = null ;
    }
    public Deserializer(String pFilepath, LogWriter pLogger)
    {
        this.mFilePath  = pFilepath ;
        this.mLogWriter = pLogger ;
    }


    public T Deserialize()
    {
        try
        {
            inputStream = new ObjectInputStream( Files.newInputStream( Paths.get( this.mFilePath ), StandardOpenOption.READ ) );
        }
        catch (IOException e)
        {
            if( mLogWriter != null ) this.mLogWriter.ErrorLog(this.getClass().getName() + "Failed to read \"" + this.mFilePath + "\" file", e) ;
            System.out.println("error during file opening... " + e.getMessage());
            e.printStackTrace();
        }
        try
        {
            this.mObjects = (T)inputStream.readObject() ;
        }
        catch (IOException e)
        {
            if( mLogWriter != null ) this.mLogWriter.ErrorLog(this.getClass().getName() + "Failed to convert object from \"" + this.mFilePath + "\" file", e) ;
            System.out.println("invalid cast conversion during reading... " + e.getMessage());
            e.printStackTrace();
        }
        catch(ClassNotFoundException e)
        {
            if( mLogWriter != null ) this.mLogWriter.ErrorLog(this.getClass().getName() + "Failed find corresponding Class when converting object from file", e) ;
            System.out.println("Unable to found corresponding Class... " + e.getMessage());
            e.printStackTrace();
        }
        try
        {
            inputStream.close();
        }
        catch (IOException e)
        {
            if( mLogWriter != null ) this.mLogWriter.ErrorLog(this.getClass().getName() + "Error during fileInputStream.close()", e) ;
            System.out.println("Unable to close file... " + e.getMessage());
            e.printStackTrace();
        }
        if( mLogWriter != null ) this.mLogWriter.FileReadingLog("file : \"" + this.mFilePath + "\" has been deserialized") ;
        return mObjects ;
    }
}
