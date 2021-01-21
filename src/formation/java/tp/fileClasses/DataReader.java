package formation.java.tp.fileClasses;

import formation.java.tp.abstracts.AFileReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class DataReader extends AFileReader
{
    private File mFile ;

    public DataReader(){}
    public DataReader(String pFilePath)
    {
        this.mFilePath = pFilePath ;
    }

    public String ReadWholeFile() throws FileNotFoundException
    {
        this.mFile = new File(this.mFilePath) ;
        if( this.mFile.exists() )
        {
            if( this.mFile.canRead() )
            {
                String lData    = "" ;
                Scanner lReader = new Scanner(this.mFile) ;
                while( lReader.hasNextLine() )
                {
                    lData += lReader.nextLine() ;
                }
                return lData ;
            }
        }
        else
        {
            throw new FileNotFoundException("Unable to open file to read") ;
        }
        return null ;
    }
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
}
