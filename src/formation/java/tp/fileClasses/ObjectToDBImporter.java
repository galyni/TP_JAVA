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
        var borrowed = magazine.isBorrowed() ? 1 : 0;
        var borrowable = magazine.isBorrowable() ? 1 : 0;

        Editor editeur = magazine.getEditor();
        int editorID = GetEditorID(editeur);

        Statement statement = connexion.createStatement();
        String query = "INSERT INTO Magazines(Title, EditorsID, PublishDate, Borrowed, Borrowable, NumberOfPages, Type, Frequency, Author) VALUES('" +
                magazine.getTitle() + "', " +
                editorID + ", '" +
                magazine.getPublishDate().format(DateTimeFormatter.ISO_LOCAL_DATE) + "', " +
                borrowed + ", " +
                borrowable + ", " +
                magazine.getNumberOfPages() + ", " +
                magazine.getMagazineTypeAsInt() + ", " +
                magazine.getMagazineFrequencyAsInt() + ", '" +
                magazine.getAuthor() + "')";
        statement.executeUpdate(query);

        int i =0;

    }

    public void InsertIntoBooksTable(Book book) throws SQLException {

        var borrowed = book.isBorrowed() ? 1 : 0;
        var borrowable = book.isBorrowable() ? 1 : 0;
        var traduct = book.isTraduct() ? 1 : 0;

        Editor editeur = book.getEditor();
        int editorID = GetEditorID(editeur);

        Statement statement = connexion.createStatement();
        String query = "INSERT INTO Books(Title, EditorsID, PublishDate, Borrowed, Borrowable, NumberOfPages, Type, Translated, Author) VALUES('" +
                book.getTitle() + "', " +
                editorID + ", '" +
                book.getPublishDate().format(DateTimeFormatter.ISO_LOCAL_DATE) + "', " +
                borrowed + ", " +
                borrowable + ", " +
                book.getNumberOfPages() + ", " +
                book.getBookTypeAsInt() + ", " +
                traduct + ", '" +
                book.getAuthor() + "')";
        statement.executeUpdate(query);

        int i =0;


    }

    public void InsertIntoCDTable(CD cd) throws SQLException {

        var borrowed = cd.isBorrowed() ? 1 : 0;
        var borrowable = cd.isBorrowable() ? 1 : 0;

        Editor editeur = cd.getEditor();
        int editorID = GetEditorID(editeur);

        Statement statement = connexion.createStatement();
        String query = "INSERT INTO CDs(Title, EditorsID, PublishDate, Borrowed, Borrowable, Length, Type, NumberOfTracks) VALUES('" +
                cd.getTitle() + "', " +
                editorID + ", '" +
                cd.getPublishDate().format(DateTimeFormatter.ISO_LOCAL_DATE) + "', " +
                borrowed + ", " +
                borrowable + ", '" +
                cd.getDVDLength() + "', " +
                cd.getCDTypeAsInt() + ", " +
                cd.getCDNumberOfTracks() + ")";
        statement.executeUpdate(query);
    }

    public void InsertIntoDVDTable(DVD dvd) throws SQLException {

        var borrowed = dvd.isBorrowed() ? 1 : 0;
        var borrowable = dvd.isBorrowable() ? 1 : 0;
        var AudioDescripted = dvd.isAudioDescriptible() ? 1 : 0;

        Editor editeur = dvd.getEditor();
        int editorID = GetEditorID(editeur);

        Statement statement = connexion.createStatement();
        String query = "INSERT INTO DVDs(Title, EditorsID, PublishDate, Borrowed, Borrowable, Length, Type, AudioDescription) VALUES('" +
                dvd.getTitle() + "', " +
                editorID + ", '" +
                dvd.getPublishDate().format(DateTimeFormatter.ISO_LOCAL_DATE) + "', " +
                borrowed + ", " +
                borrowable + ", '" +
                dvd.getDVDLength() + "', " +
                dvd.getDVDTypeAsInt() + ", " +
                AudioDescripted + ")";
        statement.executeUpdate(query);
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
