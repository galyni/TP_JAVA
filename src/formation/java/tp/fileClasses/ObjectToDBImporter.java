package formation.java.tp.fileClasses;

import formation.java.tp.model.*;

import java.sql.*;
import java.time.format.DateTimeFormatter;


public class ObjectToDBImporter {
    // TODO: 30/01/2021 ajouter dans l'insertion des objets l'insertion de leur éditeur + transformer les requetes en requetes préparées
    Connection connexion = null;

    public ObjectToDBImporter(String connectionString) throws SQLException {
        connexion = DriverManager.getConnection(connectionString);
    }

    public void CloseConnection() throws SQLException {
        connexion.close();
    }

    public void ImportLibrary(Library library) throws SQLException {
        for(Book book : library.mBookLibrary){
            InsertIntoBooksTable(book);
        }
        for(DVD dvd : library.mDVDLibrary){
            InsertIntoDVDTable(dvd);
        }
        for(Magazine magazine : library.mMagazineLibrary){
            InsertIntoMagazinesTable(magazine);
        }
        for(CD cd : library.mCDLibrary){
            InsertIntoCDTable(cd);
        }
    }

    public void InsertIntoMagazinesTable(Magazine magazine) throws SQLException {

        Editor editeur = magazine.getEditor();
        int editorID = GetEditorID(editeur);
        String query = "INSERT INTO Magazines(Title, EditorsID, PublishDate, Borrowed, Borrowable, NumberOfPages, Type, Frequency, Author) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

    }

    public void InsertIntoBooksTable(Book book) throws SQLException {

        var borrowed = book.isBorrowed() ? 1 : 0;
        var borrowable = book.isBorrowable() ? 1 : 0;
        var traduct = book.isTraduct() ? 1 : 0;

        Editor editeur = book.getEditor();
        int editorID = GetEditorID(editeur);

        String query = "INSERT INTO Books(Title, EditorsID, PublishDate, Borrowed, Borrowable, NumberOfPages, Type, Translated, Author) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

        int i =0;


    }

    public void InsertIntoCDTable(CD cd) throws SQLException {


        Editor editeur = cd.getEditor();
        int editorID = GetEditorID(editeur);

        String query = "INSERT INTO CDs(Title, EditorsID, PublishDate, Borrowed, Borrowable, Length, Type, NumberOfTracks) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

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
    }

    public void InsertIntoDVDTable(DVD dvd) throws SQLException {

        var borrowed = dvd.isBorrowed() ? 1 : 0;
        var borrowable = dvd.isBorrowable() ? 1 : 0;
        var AudioDescripted = dvd.isAudioDescriptible() ? 1 : 0;

        Editor editeur = dvd.getEditor();
        int editorID = GetEditorID(editeur);

        String query = "INSERT INTO DVDs(Title, EditorsID, PublishDate, Borrowed, Borrowable, Length, Type, AudioDescription) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

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
    }


    private int GetEditorID(Editor editor) throws SQLException {
        Statement statement = connexion.createStatement();
        String query = "SELECT TOP(1) ID FROM Editors " +
                "WHERE NAME='" +
                editor.getEditorName() + "' " +
                "ORDER BY ID DESC";

        int editorID;

        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet.next()) {
            editorID = resultSet.getInt("ID");
            return editorID;
        }
        else
            return InsertIntoEditorsTable(editor);

    }


    public int InsertIntoEditorsTable(Editor editeur) throws SQLException {
        Statement statement = connexion.createStatement();
        String query = "INSERT INTO EDITORS(SIRET, Name, Street, ZipCode, City, Country) VALUES('" +
                editeur.getEditorSiret() + "', '" +
                editeur.getEditorName() + "', '" +
                editeur.getSiegeStreet() + "', '" +
                editeur.getmZipcode() + "', '" +
                editeur.getEditorCity() + "', '" +
                editeur.getEditorCountry() + "')";
        statement.executeUpdate(query);

        statement = connexion.createStatement();
        query = "SELECT TOP(1) ID FROM Editors " +
                "WHERE NAME='" +
                editeur.getEditorName() + "' " +
                "ORDER BY ID DESC";

        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();
        int editorID = resultSet.getInt("ID");

        return editorID;

    }


}
