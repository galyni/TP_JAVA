package formation.java.tp.fileClasses;

import formation.java.tp.model.Book;
import formation.java.tp.model.Editor;

import java.sql.*;
import java.time.format.DateTimeFormatter;


public class DatabaseImporter {

    Connection connexion = null;

    public DatabaseImporter(String connectionString) throws SQLException {
        connexion = DriverManager.getConnection(connectionString);
    }

    public void CloseConnection() throws SQLException {
        connexion.close();
    }


    public void InsertIntoBooks(Book book, int editorID) throws SQLException {

        var borrowed = book.isBorrowed() ? 1 : 0;
        var borrowable = book.isBorrowable() ? 1 : 0;
        var traduct = book.isTraduct() ? 1 : 0;

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


    public int InsertIntoEditors(Editor editeur) throws SQLException {
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
