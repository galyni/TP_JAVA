package formation.java.tp.db;

import formation.java.tp.io.LogWriter;
import formation.java.tp.model.*;
import formation.java.tp.utils.*;

import java.sql.*;
import java.util.Vector;

/**
 * Exporte les données de la base Librairie en un objet Library, qui peut ensuite être sérialisé dans un fichier.
 */
public class DBToObjectExporter {
    private Connection connexion = null;
    private String connectionString;
    private LogWriter logWriter;

    /**
     *
     * @param connectionString La chaine de connexion à la base de donnée Librairie
     */
    public DBToObjectExporter(String connectionString) {
        this.connectionString = connectionString;
    }

    /**
     *
     * @param connectionString La chaine de connexion à la base de donnée Librairie
     * @param logWriter L'objet LogWriter pour l'écriture des logs (opérations réussies, erreurs)
     */
    public DBToObjectExporter(String connectionString, LogWriter logWriter) {
        this.connectionString = connectionString;
        this.logWriter = logWriter;
    }

    /**
     * Transforme l'intégralité de la base Librairie en un objet Library contenant toutes ses données.
     *
     * Chaque ligne devient un objet (Book, Magazine, DVD, CD, Editor.
     * Chaque table devient un Vector<> contenant l'ensemble des objet du même type, sauf les Editor qui sont inclus dans les objets médias.
     *
     * Les liens représentés par des clés étrangères dans la base se traduisent par l'inclusion de l'objet lié.
     * @return Un objet Library contenant toutes les données de la bas
     */
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
            }
        }
        if(logWriter != null ) this.logWriter.CRUDOperationLog("Export of database succeeded.");

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
        resultSet.close();
        statement.close();

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
        resultSet.close();
        statement.close();

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
        resultSet.close();
        statement.close();

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
        resultSet.close();
        statement.close();

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
        resultSet.close();
        statement.close();

        return editor;
    }
}
