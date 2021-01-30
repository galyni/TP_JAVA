package formation.java.tp.IOClasses;

import formation.java.tp.abstracts.AFileWriter;

import java.io.FileWriter;
import java.io.IOException;

public class DataWriter extends AFileWriter
{
    protected FileWriter mWriter ;

    public DataWriter(){}
    public DataWriter(String pFilePath)
    {
        this.mFilePath = pFilePath ;
    }

    public void SetFilePath( String pFilePath ){this.mFilePath = pFilePath ;}
    public String GetFilePath()                {return this.mFilePath ;}

    public void WriteFile(String pData, boolean pAppend)
    {
        try
        {
            this.mWriter = new FileWriter( this.mFilePath, pAppend ) ;
        }
        catch (IOException e)
        {
            System.out.println("Unable to open file.." + e.getMessage());
            e.printStackTrace();
        }
        try
        {
            if(pAppend)
            {
                pData += "\n" ;
            }
            this.mWriter.write( pData ) ;
            this.mWriter.close() ;
        }
        catch (IOException e)
        {
            System.out.println("Unable to write into file.." + e.getMessage());
            e.printStackTrace();
        }
    }
}
