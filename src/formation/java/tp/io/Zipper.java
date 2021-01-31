package formation.java.tp.io;

import java.io.*;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper
{
    private DataReader     mDataReader ;
    private Vector<String> mFilesPath ;
    private String         mOutputFilePath ;
    private LogWriter      mLogWriter ;

    public Zipper(){}
    public Zipper(String pOutputFilePath, LogWriter pLogWriter)
    {
        this.mOutputFilePath = pOutputFilePath ;
        this.mLogWriter      = pLogWriter ;
        mFilesPath           = new Vector<String>() ;
    }
    public Zipper(String pOutputFilePath, LogWriter pLogWriter, String... pFilesPath)
    {
        this.mOutputFilePath = pOutputFilePath ;
        this.mLogWriter      = pLogWriter ;
        mFilesPath           = new Vector<String>(pFilesPath.length) ;
        for(String filePath : pFilesPath)
        {
            mFilesPath.add(filePath) ;
        }
    }
    public void AddNewFiles(String... pFilesPath)
    {
        for(String filePath : pFilesPath)
        {
            mFilesPath.add(filePath) ;
        }
    }
    public void RemoveFiles(String... pFilesPath)
    {
        for(String filePath : pFilesPath)
        {
            if( mFilesPath.contains(filePath) )
            {
                mFilesPath.remove(filePath) ;
            }
        }
    }

    /**
     * Create a zip with all previously file specified into constructor or throught "AddNewFiles" method, at path specified with c'tor
     */
    public void ZipFiles()
    {
        try
        {
            BufferedOutputStream lBufferedOutputStream = null ;
            ZipOutputStream lZipOutputStream           = new ZipOutputStream(new FileOutputStream(this.mOutputFilePath)); ;

            for (int i = 0; i < this.mFilesPath.size(); ++i)
            {
                //this.mDataReader = new DataReader( this.mFilesPath.get(i) ) ;
                //byte[] lByteFileContent = this.mDataReader.ReadAsByte() ;

                //File lFile                 = new File( this.mFilesPath.get(i) ) ;
                FileInputStream lInputFile = new FileInputStream(this.mFilesPath.get(i));
                ZipEntry zipEntry          = new ZipEntry( this.mFilesPath.get(i) ) ;
                lZipOutputStream.putNextEntry(zipEntry);

                int lContentSize = lInputFile.available() ;
                byte[] lByteFileContent = new byte[lContentSize] ;
                int length ;
                while ((length = lInputFile.read(lByteFileContent)) > 0)
                {
                    lZipOutputStream.write(lByteFileContent, 0, length);
                }
                lZipOutputStream.closeEntry() ;
                lInputFile.close() ;
            }
            lZipOutputStream.close();
            if( mLogWriter != null ) this.mLogWriter.FileWritingLog(this.getClass().getName() + "Successfully zip files" ); ;
        }
        catch( FileNotFoundException e )
        {
            if( mLogWriter != null ) this.mLogWriter.ErrorLog(this.getClass().getName() + "Unable to open file to zip", e ) ;
            e.printStackTrace();
            System.out.println("Unable to open file to zip");
        }
        catch( IOException e )
        {
            if( mLogWriter != null ) this.mLogWriter.ErrorLog(this.getClass().getName() + "Error during zipping", e ) ;
            e.printStackTrace();
            System.out.println("Unable to open file");
        }
    }
}
