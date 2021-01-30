package formation.java.tp.fileClasses;

import formation.java.tp.model.Book;
import formation.java.tp.model.Editor;
import formation.java.tp.model.Library;
import formation.java.tp.utils.eBookType;

import java.sql.*;
import java.util.Vector;

public class DBToObjectExporter {
    // TODO: 30/01/2021 méthodes qui renvoient 1 objet ? ou filtrage ?
    Connection connexion = null;

    public DBToObjectExporter(String connectionString) throws SQLException {
        connexion = DriverManager.getConnection(connectionString);
    }

    public void CloseConnection() throws SQLException {
        connexion.close();
    }

    public Library ExportDatabase() throws SQLException {
        Library library = new Library();
        library.mBookLibrary= GetBooksFromTable();

        return library;
    }


    public Vector<Book> GetBooksFromTable() throws SQLException {
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
                    resultSet.getBoolean("Translated")
            );
            int editorID = resultSet.getInt("EditorsID");
            Editor editor = GetEditorFromTable(editorID);
            book.setEditor(editor);
            bookLibrary.add(book);
        }

        return bookLibrary;
    }




    public Editor GetEditorFromTable(int editorID) throws SQLException {
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
