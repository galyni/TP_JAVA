package formation.java.tp.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;



public class LogWriter
{
    private String     mFilePath ;
    private Date       mDate ;
    private FileWriter mWriter ;

    private enum eLogType
    {
        error,
        fileWriting,
        fileReading,
        dbConnection,
        CRUDOperation
    }

    public LogWriter(){}
    public LogWriter(String pFilePath)
    {
        this.mFilePath = pFilePath ;
    }

    public void SetFilePath( String pFilePath ){this.mFilePath = pFilePath ;}
    public String GetFilePath()                {return this.mFilePath ;}

    private void WriteLog(String pLogLine)
    {
        try
        {
            this.mWriter = new FileWriter( this.mFilePath, true ) ;
                    //Files.newOutputStream( Paths.get( this.mFilePath ), StandardOpenOption.APPEND ) ;
        }
        catch (IOException e)
        {
            System.out.println("Unable to open file.." + e.getMessage());
            e.printStackTrace();
        }
        try
        {
            pLogLine += "\n" ;
            this.mWriter.write( pLogLine ) ;
            this.mWriter.close() ;
        }
        catch (IOException e)
        {
            System.out.println("Unable to write into file.." + e.getMessage());
            e.printStackTrace();
        }
    }
    private void FormatLog(String pLogMessage, eLogType pLogType)
    {
        this.mDate = new Date() ;
        String lFormatedLog ;

        lFormatedLog = this.mDate + " : " + pLogType.name() + " : " + pLogMessage ;

        WriteLog( lFormatedLog ) ;
    }
    public void ErrorLog        ( String pLogMessage ){this.FormatLog( pLogMessage, eLogType.error ) ;}
    public void DbConnectionLog ( String pLogMessage ){this.FormatLog( pLogMessage, eLogType.dbConnection ) ;}
    public void CRUDOperationLog( String pLogMessage ){this.FormatLog( pLogMessage, eLogType.CRUDOperation ) ;}
    public void FileReadingLog  ( String pLogMessage ){this.FormatLog( pLogMessage, eLogType.fileReading ) ;}
    public void FileWritingLog  ( String pLogMessage ){this.FormatLog( pLogMessage, eLogType.fileWriting) ;}
}
