package formation.java.tp.fileClasses;

import formation.java.tp.model.*;
import formation.java.tp.utils.*;

import java.sql.*;
import java.util.Vector;

public class DBToObjectExporter {
    // TODO: 30/01/2021 méthodes qui renvoient 1 objet ? ou filtrage ?
    private Connection connexion = null;
    private String connectionString;
    private LogWriter logWriter;

    public DBToObjectExporter(String connectionString) {
        this.connectionString = connectionString;
    }

    public DBToObjectExporter(String connectionString, LogWriter logWriter) {
        this.connectionString = connectionString;
        this.logWriter = logWriter;
    }

    public Library ExportDatabase() {
        Library library = new Library();
        try {
            connexion = DriverManager.getConnection(connectionString);
            library.mBookLibrary= GetBooksFromTable();
            library.mMagazineLibrary = GetMagazinesFromTable();
            library.mCDLibrary= GetCDFromTable();
            library.mDVDLibrary= GetDVDFromTable();

            connexion.close();
        } catch (SQLException e) {
            if( logWriter != null ) this.logWriter.ErrorLog(this.getClass().getName() + "Failed to export database ", e) ;
            System.out.println("error during database export... " + e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if (connexion != null)
                    connexion.close();
            } catch (SQLException e) {
                if (logWriter != null)
                    this.logWriter.ErrorLog(this.getClass().getName() + "Failed to close connexion \"" + connectionString + "\"", e);
                System.out.println("error during database update... " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
        if(logWriter != null ) this.logWriter.CRUDOperationLog("Export of database succeeded."); ;

        return library;
    }

    private Vector<Book> GetBooksFromTable() throws SQLException {
        Vector<Book> bookLibrary = new Vector<>();

        Statement statement = connexion.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM BOOKS");
        while (resultSet.next()){
            Book book = new Book(
                    resultSet.getString("Title"),
                    resultSet.getDate("PublishDate").toLocalDate(),
                    resultSet.getInt("NumberOfPages"),
                    resultSet.getString("Author"),
                    eBookType.values()[resultSet.getInt("Type")],
                    resultSet.getBoolean("Translated"),
                    resultSet.getBoolean("Borrowed")
            );
            int editorID = resultSet.getInt("EditorsID");
            Editor editor = GetEditorByID(editorID);
            book.setEditor(editor);
            bookLibrary.add(book);
        }

        return bookLibrary;
    }

    private Vector<Magazine> GetMagazinesFromTable() throws SQLException {
        Vector<Magazine> magazinesLibrary = new Vector<>();

        Statement statement = connexion.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM MAGAZINES");
        while (resultSet.next()){
            Magazine magazine = new Magazine(
                    resultSet.getString("Title"),
                    resultSet.getDate("PublishDate").toLocalDate(),
                    resultSet.getInt("NumberOfPages"),
                    resultSet.getString("Author"),
                    eMagazineType.values()[resultSet.getInt("Type")],
                    ePublishmentFrequency.values()[resultSet.getInt("Frequency")]
            );
            int editorID = resultSet.getInt("EditorsID");
            Editor editor = GetEditorByID(editorID);
            magazine.setEditor(editor);
            magazinesLibrary.add(magazine);
        }

        return magazinesLibrary;
    }

    private Vector<CD> GetCDFromTable() throws SQLException {
        Vector<CD> CDLibrary = new Vector<>();

        Statement statement = connexion.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM CDs");
        while (resultSet.next()){
            CD cd = new CD(
                    resultSet.getString("Title"),
                    resultSet.getDate("PublishDate").toLocalDate(),
                    resultSet.getString("Length"),
                    eCDType.values()[resultSet.getInt("Type")],
                    resultSet.getInt("NumberOfTracks")
            );
            int editorID = resultSet.getInt("EditorsID");
            Editor editor = GetEditorByID(editorID);
            cd.setEditor(editor);
            CDLibrary.add(cd);
        }

        return CDLibrary;
    }

    private Vector<DVD> GetDVDFromTable() throws SQLException {
        Vector<DVD> DVDLibrary = new Vector<>();

        Statement statement = connexion.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM DVDs");
        while (resultSet.next()){
            DVD dvd = new DVD(
                    resultSet.getString("Title"),
                    resultSet.getDate("PublishDate").toLocalDate(),
                    resultSet.getString("Length"),
                    eDVDType.values()[resultSet.getInt("Type")],
                    resultSet.getBoolean("AudioDescription")
            );
            int editorID = resultSet.getInt("EditorsID");
            Editor editor = GetEditorByID(editorID);
            dvd.setEditor(editor);
            DVDLibrary.add(dvd);
        }

        return DVDLibrary;
    }

    private Editor GetEditorByID(int editorID) throws SQLException {
        Statement statement = connexion.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM EDITORS WHERE ID=" + editorID);
        resultSet.next();
        Editor editor = new Editor(
                resultSet.getString("Street"),
                resultSet.getString("City"),
                resultSet.getString("Country"),
                resultSet.getString("Name"),
                resultSet.getString("SIRET"),
                resultSet.getString("ZipCode")
        );

        return editor;
    }
}
