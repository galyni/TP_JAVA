package formation.java.tp.io;

import formation.java.tp.abstracts.AFileWriter;

import java.io.FileWriter;
import java.io.IOException;

public class DataWriter extends AFileWriter
{
    private FileWriter mWriter ;
    private LogWriter  mLogWriter ;

    public DataWriter(){}
    public DataWriter(String pFilePath)
    {
        this.mFilePath = pFilePath ;
    }
    public DataWriter(String pFilePath, LogWriter pLogWriter)
    {
        this.mFilePath  = pFilePath ;
        this.mLogWriter = pLogWriter ;
    }

    public void SetFilePath( String pFilePath ){this.mFilePath = pFilePath ;}
    public String GetFilePath()                {return this.mFilePath ;}

    /**
     * Write into file
     * @param pData to write as string
     * @param pAppend if you to erase previous content or just add new
    */
    public void WriteFile(String pData, boolean pAppend)
    {
        try
        {
            this.mWriter = new FileWriter( this.mFilePath, pAppend ) ;
        }
        catch (IOException e)
        {
            if( mLogWriter != null ) this.mLogWriter.ErrorLog(this.getClass().getName() + "Unable to open \"" + this.mFilePath + "\" file", e ) ;
            e.printStackTrace();
            System.out.println("Unable to open file");
        }
        try
        {
            if(pAppend)
            {
                pData += "\n" ;
            }
            this.mWriter.write( pData ) ;
            this.mWriter.close() ;
            if( mLogWriter != null ) this.mLogWriter.FileWritingLog(this.getClass().getName() + "Successfully write into \"" + this.mFilePath + "\" file" ) ;
        }
        catch (IOException e)
        {
            if( mLogWriter != null ) this.mLogWriter.ErrorLog(this.getClass().getName() + "Unable to write into \"" + this.mFilePath + "\" file", e ) ;
            System.out.println("Unable to write into file.." + e.getMessage());
            e.printStackTrace();
        }
    }
}
