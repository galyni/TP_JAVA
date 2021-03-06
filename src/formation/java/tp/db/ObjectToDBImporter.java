package formation.java.tp.db;

import formation.java.tp.model.*;
import formation.java.tp.io.LogWriter;

import java.sql.*;
import java.time.format.DateTimeFormatter;

/**
 * Permet l'importation de données dans une base Librairie à partir d'un objet Library.
 */
public class ObjectToDBImporter {
    private Connection connexion = null;
    private String connectionString;
    private LogWriter logWriter;
    private int numberOfRows = 0;

    /**
     *
     * @param connectionString La chaine de connexion à la base de donnée Librairie
     */
    public ObjectToDBImporter(String connectionString) {
        this.connectionString = connectionString;
    }

    /**
     *
     * @param connectionString La chaine de connexion à la base de donnée Librairie
     * @param logWriter L'objet LogWriter pour l'écriture des logs (opérations réussies, erreurs)
     */
    public ObjectToDBImporter(String connectionString, LogWriter logWriter) {
        this.connectionString = connectionString;
        this.logWriter = logWriter;
    }

    /**
     *
     * @param library L'objet Library dont les données doivent être insérées dans la base
     */
    public void ImportLibrary(Library library) {
        try {
            for (Book book : library.mBookLibrary) {
                InsertIntoBooksTable(book);
            }
            for (DVD dvd : library.mDVDLibrary) {
                InsertIntoDVDTable(dvd);
            }
            for (Magazine magazine : library.mMagazineLibrary) {
                InsertIntoMagazinesTable(magazine);
            }
            for (CD cd : library.mCDLibrary) {
                InsertIntoCDTable(cd);
            }
            if(logWriter != null ) this.logWriter.CRUDOperationLog("Import into database succeeded. " + numberOfRows + " lines inserted.");
        } catch (SQLException e) {
            if( logWriter != null ) this.logWriter.ErrorLog(this.getClass().getName() + "Failed to import into database ", e) ;
            System.out.println("error during database update... " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if(connexion != null)
                    connexion.close();
            } catch(SQLException e) {
                if( logWriter != null ) this.logWriter.ErrorLog(this.getClass().getName() + "Failed to close connexion \"" + connectionString + "\"", e) ;
                System.out.println("error during database update... " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void InsertIntoMagazinesTable(Magazine magazine) throws SQLException {
        Editor editeur = magazine.getEditor();
        int editorID = GetEditorID(editeur);
        String query = "INSERT INTO Magazines(Title, EditorsID, PublishDate, Borrowed, Borrowable, NumberOfPages, Type, Frequency, Author) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        connexion = DriverManager.getConnection(connectionString);
        PreparedStatement ps = connexion.prepareStatement(query);

        ps.setString(1, magazine.getTitle());
        ps.setInt(2, editorID);
        ps.setString(3, magazine.getPublishDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        ps.setBoolean(4, magazine.isBorrowed());
        ps.setBoolean(5, magazine.isBorrowable());
        ps.setInt(6, magazine.getNumberOfPages());
        ps.setInt(7, magazine.getMagazineTypeAsInt());
        ps.setInt(8, magazine.getMagazineFrequencyAsInt());
        ps.setString(9, magazine.getAuthor());

        ps.executeUpdate();
        numberOfRows += 1;

        ps.close();
        connexion.close();
    }

    private void InsertIntoBooksTable(Book book) throws SQLException {
        Editor editeur = book.getEditor();
        int editorID = GetEditorID(editeur);

        String query = "INSERT INTO Books(Title, EditorsID, PublishDate, Borrowed, Borrowable, NumberOfPages, Type, Translated, Author) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        connexion = DriverManager.getConnection(connectionString);

        PreparedStatement ps = connexion.prepareStatement(query);

        ps.setString(1, book.getTitle());
        ps.setInt(2, editorID);
        ps.setString(3, book.getPublishDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        ps.setBoolean(4, book.isBorrowed());
        ps.setBoolean(5, book.isBorrowable());
        ps.setInt(6, book.getNumberOfPages());
        ps.setInt(7, book.getBookTypeAsInt());
        ps.setBoolean(8, book.isTraduct());
        ps.setString(9, book.getAuthor());

        ps.executeUpdate();
        numberOfRows += 1;

        ps.close();
        connexion.close();
    }

    private void InsertIntoCDTable(CD cd) throws SQLException {
        Editor editeur = cd.getEditor();
        int editorID = GetEditorID(editeur);

        String query = "INSERT INTO CDs(Title, EditorsID, PublishDate, Borrowed, Borrowable, Length, Type, NumberOfTracks) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        connexion = DriverManager.getConnection(connectionString);

        PreparedStatement ps = connexion.prepareStatement(query);

        ps.setString(1, cd.getTitle());
        ps.setInt(2, editorID);
        ps.setString(3, cd.getPublishDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        ps.setBoolean(4, cd.isBorrowed());
        ps.setBoolean(5, cd.isBorrowable());
        ps.setString(6, cd.getCDLength());
        ps.setInt(7, cd.getCDTypeAsInt());
        ps.setInt(8, cd.getCDNumberOfTracks());

        ps.executeUpdate();
        numberOfRows += 1;

        ps.close();
        connexion.close();
    }

    private void InsertIntoDVDTable(DVD dvd) throws SQLException {
        Editor editeur = dvd.getEditor();
        int editorID = GetEditorID(editeur);

        String query = "INSERT INTO DVDs(Title, EditorsID, PublishDate, Borrowed, Borrowable, Length, Type, AudioDescription) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        connexion = DriverManager.getConnection(connectionString);

        PreparedStatement ps = connexion.prepareStatement(query);

        ps.setString(1, dvd.getTitle());
        ps.setInt(2, editorID);
        ps.setString(3, dvd.getPublishDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        ps.setBoolean(4, dvd.isBorrowed());
        ps.setBoolean(5, dvd.isBorrowable());
        ps.setString(6, dvd.getDVDLength());
        ps.setInt(7, dvd.getDVDTypeAsInt());
        ps.setBoolean(8, dvd.isAudioDescriptible());

        ps.executeUpdate();
        numberOfRows += 1;

        ps.close();
        connexion.close();
    }

    private int GetEditorID(Editor editor) throws SQLException {
        int editorID;

        connexion = DriverManager.getConnection(connectionString);
        Statement statement = connexion.createStatement();
        String query = "SELECT TOP(1) ID FROM Editors " +
                "WHERE NAME='" +
                editor.getEditorName() + "' " +
                "ORDER BY ID DESC";


        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet.next()) {
            editorID = resultSet.getInt("ID");
        }
        else
            editorID = InsertIntoEditorsTable(editor);

        statement.close();
        connexion.close();

        return editorID;
    }


    private int InsertIntoEditorsTable(Editor editeur) throws SQLException {
        Statement statement = connexion.createStatement();
        String query = "INSERT INTO EDITORS(SIRET, Name, Street, ZipCode, City, Country) VALUES('" +
                editeur.getEditorSiret() + "', '" +
                editeur.getEditorName() + "', '" +
                editeur.getSiegeStreet() + "', '" +
                editeur.getmZipcode() + "', '" +
                editeur.getEditorCity() + "', '" +
                editeur.getEditorCountry() + "')";
        statement.executeUpdate(query);
        numberOfRows += 1;

        statement = connexion.createStatement();
        query = "SELECT TOP(1) ID FROM Editors " +
                "WHERE NAME='" +
                editeur.getEditorName() + "' " +
                "ORDER BY ID DESC";

        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();

        return resultSet.getInt("ID");
    }


}
