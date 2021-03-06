package formation.java.tp.io;

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
    /**
     * Use any of those methods to write formated entry into your log file
     * @param pLogMessage is a string to specify details about logged operation
     */
    public void DbConnectionLog ( String pLogMessage ){this.FormatLog( pLogMessage, eLogType.dbConnection ) ;}
    public void CRUDOperationLog( String pLogMessage ){this.FormatLog( pLogMessage, eLogType.CRUDOperation ) ;}
    public void FileReadingLog  ( String pLogMessage ){this.FormatLog( pLogMessage, eLogType.fileReading ) ;}
    public void FileWritingLog  ( String pLogMessage ){this.FormatLog( pLogMessage, eLogType.fileWriting) ;}
    public void ErrorLog        ( String pLogMessage ){this.FormatLog( pLogMessage, eLogType.error ) ;}
    /**
     * this method take a second parameter to have more details about source of error, use it in a "catch" scope
     * @param pLogMessage is a string to specify details about logged operation
     * @param pException thrown by parent method
     */
    public void ErrorLog        ( String pLogMessage, Exception pException )
    {
        String lLogMessage = pLogMessage + "\nError message : " + pException.getMessage() + "\n\tStack Trace : \n" ;
        for ( StackTraceElement stackTrace : pException.getStackTrace() )
        {
            lLogMessage += ( "\t" + stackTrace.toString() + "\n" ) ;
        }
        this.FormatLog( lLogMessage, eLogType.error ) ;
    }
}
