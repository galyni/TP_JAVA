package formation.java.tp.IOClasses;

import formation.java.tp.abstracts.AFileReader;
import formation.java.tp.utils.LogWriter;

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
        return null ;
    }
    //TODO logger to both next functions
    public String ReadLastEntry() throws FileNotFoundException, NullPointerException
    {
        this.mFile = new File(this.mFilePath) ;
        if( this.mFile.exists() )
        {
            if( this.mFile.canRead() )
            {
                String lData                       = "" ;
                RandomAccessFile lRandomAccessFile = new RandomAccessFile(this.mFile, "r") ;
                long lFileLength                   = this.mFile.length() ;

                try
                {
                    lRandomAccessFile.seek(lFileLength - 1) ;
                } catch (IOException e)
                {
                    e.printStackTrace();
                    throw new NullPointerException("File pointer out of bounds") ;
                }
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
                return lData ;
            }
        }
        else
        {
            throw new FileNotFoundException("Unable to open file to read") ;
        }
        return null ;
    }
    public byte[] ReadAsByte() throws IOException
    {
        FileInputStream lFileInputStream = new FileInputStream(this.mFilePath) ;
        DataInputStream lDataInputStream = new DataInputStream(lFileInputStream) ;

        int lContentSize = 0 ;
        try
        {
            lContentSize = lDataInputStream.available() ;
        } catch (IOException e)
        {
            e.printStackTrace() ;
            throw new IOException("unable to find input file to zip") ;
        }
        byte lFileData [] = new byte[lContentSize] ;
        lDataInputStream.read(lFileData) ;

        lFileInputStream.close() ;
        lDataInputStream.close() ;

        return lFileData ;
    }
}
