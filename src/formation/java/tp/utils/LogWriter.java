package formation.java.tp.utils;

import formation.java.tp.fileClasses.DataWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;



public class LogWriter extends DataWriter
{
    private Date mDate ;

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

    private void FormatLog(String pLogMessage, eLogType pLogType)
    {
        this.mDate = new Date() ;
        String lFormatedLog ;

        lFormatedLog = this.mDate + " : " + pLogType.name() + " : " + pLogMessage ;

        WriteFile( lFormatedLog, true ) ;
    }
    public void ErrorLog        ( String pLogMessage ){this.FormatLog( pLogMessage, eLogType.error ) ;}
    public void DbConnectionLog ( String pLogMessage ){this.FormatLog( pLogMessage, eLogType.dbConnection ) ;}
    public void CRUDOperationLog( String pLogMessage ){this.FormatLog( pLogMessage, eLogType.CRUDOperation ) ;}
    public void FileReadingLog  ( String pLogMessage ){this.FormatLog( pLogMessage, eLogType.fileReading ) ;}
    public void FileWritingLog  ( String pLogMessage ){this.FormatLog( pLogMessage, eLogType.fileWriting) ;}
}
