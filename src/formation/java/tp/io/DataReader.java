package formation.java.tp.io;

import formation.java.tp.abstracts.AFileReader;

import java.io.*;
import java.util.Scanner;

public class DataReader extends AFileReader
{
    private File      mFile ;
    private LogWriter mLogWriter ;

    public DataReader(){}
    public DataReader(String pFilePath)
    {
        this.mFilePath = pFilePath ;
    }
    public DataReader( String pFilePath, LogWriter pLogWriter )
    {
        this.mFilePath  = pFilePath ;
        this.mLogWriter = pLogWriter ;
    }

    public String ReadWholeFile()
    {
        this.mFile = new File(this.mFilePath) ;
        if( this.mFile.exists() )
        {
            if( this.mFile.canRead() )
            {
                String lData = "" ;
                try
                {
                    Scanner lReader = new Scanner(this.mFile) ;
                    while( lReader.hasNextLine() )
                    {
                        lData += lReader.nextLine() ;
                    }
                }
                catch( FileNotFoundException e )
                {
                    if( mLogWriter != null ) this.mLogWriter.ErrorLog(this.getClass().getName() + "Failed to read \"" + this.mFilePath + "\" file", e) ;
                    System.out.println("Unable to open file...") ;
                    e.printStackTrace() ;
                }
                return lData ;
            }
        }
        if( mLogWriter != null ) this.mLogWriter.ErrorLog(this.getClass().getName() + "Failed to read \"" + this.mFilePath + "\" file" ) ;
        return null ;
    }
    public String ReadLastEntry()
    {
        this.mFile = new File(this.mFilePath) ;
        if( this.mFile.exists() )
        {
            if( this.mFile.canRead() )
            {

                try
                {
                    String lData                       = "" ;
                    RandomAccessFile lRandomAccessFile = new RandomAccessFile(this.mFile, "r") ;
                    long lFileLength                   = this.mFile.length() ;

                    lRandomAccessFile.seek(lFileLength - 1) ;

                    for (long i = lFileLength - 2; i >= 0; --i)
                    {
                        try
                        {
                            lRandomAccessFile.seek(i) ;
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                            throw new NullPointerException("File pointer out of bounds") ;
                        }
                        char lChar = ' ' ;
                        try
                        {
                            lChar = (char)lRandomAccessFile.read() ;
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                        if( lChar == '\n' )
                        {
                            break ;
                        }
                        lData += lChar ;
                    }
                    StringBuilder lStringBuilder = new StringBuilder(lData) ;
                    lData = lStringBuilder.reverse().toString() ;

                    if( mLogWriter != null ) this.mLogWriter.FileReadingLog(this.getClass().getName() + "Successfully read \"" + this.mFilePath + "\" file's last line" ); ;
                    return lData ;
                }
                catch(FileNotFoundException e)
                {
                    if( mLogWriter != null ) this.mLogWriter.ErrorLog(this.getClass().getName() + "Unable to open \"" + this.mFilePath + "\" file", e ) ;
                    e.printStackTrace();
                    System.out.println("Unable to open file");
                }
                catch (IOException e)
                {
                    if( mLogWriter != null ) this.mLogWriter.ErrorLog(this.getClass().getName() + "Error during \"" + this.mFilePath + "\" file reading", e ) ;
                    e.printStackTrace();
                    System.out.println("File pointer out of bounds");
                }
            }
        }
        if( mLogWriter != null ) this.mLogWriter.ErrorLog(this.getClass().getName() + "Failed to read \"" + this.mFilePath + "\" file" ) ;
        return null ;
    }
    public byte[] ReadAsByte()
    {
        try
        {
            FileInputStream lFileInputStream = new FileInputStream(this.mFilePath) ;
            DataInputStream lDataInputStream = new DataInputStream(lFileInputStream) ;

            int lContentSize = 0 ;

            lContentSize = lDataInputStream.available() ;

            byte lFileData [] = new byte[lContentSize] ;
            lDataInputStream.read(lFileData) ;

            lFileInputStream.close() ;
            lDataInputStream.close() ;

            if( mLogWriter != null ) this.mLogWriter.FileReadingLog(this.getClass().getName() + "Successfully read \"" + this.mFilePath + "\" file as byte" ); ;
            return lFileData ;
        }
        catch( FileNotFoundException e )
        {
            if( mLogWriter != null ) this.mLogWriter.ErrorLog(this.getClass().getName() + "Unable to open \"" + this.mFilePath + "\" file", e ) ;
            e.printStackTrace();
            System.out.println("Unable to open file");
        }
        catch( IOException e )
        {
            if( mLogWriter != null ) this.mLogWriter.ErrorLog(this.getClass().getName() + "Error during \"" + this.mFilePath + "\" file reading", e ) ;
            e.printStackTrace() ;
            System.out.println("Error during file reading");
        }
        if( mLogWriter != null ) this.mLogWriter.ErrorLog(this.getClass().getName() + "Unable to open \"" + this.mFilePath + "\" file" ) ;
        return null ;
    }
}
