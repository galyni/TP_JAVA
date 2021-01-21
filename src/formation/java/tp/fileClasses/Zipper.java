package formation.java.tp.fileClasses;

import java.io.*;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper
{
    private DataReader     mDataReader ;
    private Vector<String> mFilesPath ;
    private String         mOutputFilePath ;

    public Zipper(){}
    public Zipper(String pOutputFilePath, String... pFilesPath)
    {
        this.mOutputFilePath = pOutputFilePath ;
        mFilesPath = new Vector<String>(pFilesPath.length) ;
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

    public void ZipFiles() throws IOException, FileNotFoundException
    {
        BufferedOutputStream lBufferedOutputStream = null ;
        FileOutputStream lFileOutputStream         = new FileOutputStream(this.mOutputFilePath);
        ZipOutputStream lZipOutputStream           = new ZipOutputStream(lFileOutputStream); ;

        for (int i = 0; i < this.mFilesPath.size(); ++i)
        {
            //this.mDataReader = new DataReader( this.mFilesPath.get(i) ) ;
            //byte[] lByteFileContent = this.mDataReader.ReadAsByte() ;

            File lFile                 = new File( this.mFilesPath.get(i) ) ;
            FileInputStream lInputFile = new FileInputStream(lFile);
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

        lFileOutputStream.close();
        lZipOutputStream.close();
    }
}