package formation.java.tp.io;

import formation.java.tp.abstracts.AFileWriter;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Serializer extends AFileWriter
{
    private ObjectOutputStream mOutput ;
    private LogWriter          mLogWriter ;

    public Serializer(){}
    public Serializer(String pFilepath)
    {
        this.mFilePath  = pFilepath ;
        this.mLogWriter = null ;
    }
    public Serializer(String pFilepath, LogWriter pLogWriter)
    {
        this.mFilePath  = pFilepath ;
        this.mLogWriter = pLogWriter ;
    }

    /**
     * Serialize any kind of object(s) into binary file
     * @param pObject in an instance of what you want to serialize
     */
    public <T>void Serialize( T pObject )
    {
        try
        {
            this.mOutput = new ObjectOutputStream( Files.newOutputStream( Paths.get( this.mFilePath ), StandardOpenOption.CREATE ) ) ;
        }
        catch (IOException e)
        {
            if( mLogWriter != null ) this.mLogWriter.ErrorLog(this.getClass().getName() + "Failed to read \"" + this.mFilePath + "\" file", e) ;
            System.out.println( "Unable to open file.." + e.getMessage() ) ;
            e.printStackTrace() ;
        }
        try
        {
            this.mOutput.writeObject(pObject) ;
        }
        catch (IOException e)
        {
            if( mLogWriter != null ) this.mLogWriter.ErrorLog(this.getClass().getName() + "Failed to serialized into \"" + this.mFilePath + "\" file", e) ;
            System.out.println( "Unable to write into file.." + e.getMessage() ) ;
            e.printStackTrace() ;
        }
        try
        {
            this.mOutput.close() ;
        }
        catch (IOException e)
        {
            if( mLogWriter != null ) this.mLogWriter.ErrorLog(this.getClass().getName() + "Error during fileInputStream.close()", e) ;
            System.out.println( "Unable to close file.." + e.getMessage() ) ;
            e.printStackTrace() ;
        }
        mLogWriter.FileWritingLog(pObject.getClass().getName() + " has been serialized into \"" + this.mFilePath + "\" file" ) ;
    }
}
