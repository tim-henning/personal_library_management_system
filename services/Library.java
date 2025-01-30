package services;

import models.Book;

import java.sql.*;
import java.util.ArrayList;

public class Library {

    public void addBook(Book book) {
        //Get a connection to the database
        Connection conn = DatabaseManager.connect();
        if (conn == null) {
            System.out.println("Database connection failed!");
            return;
        }

        //Prepare the SQL statement to insert a new book
        String sql = "INSERT INTO books (title, author, isbn) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            //Replace the placeholders (?) with actual values
            pstmt.setString(1, book.getTitle()); // Set title
            pstmt.setString(2, book.getAuthor()); // Set author
            pstmt.setString(3, book.getIsbn()); // Set ISBN

            //Execute the SQL statement to insert the book
            pstmt.executeUpdate();
            System.out.println("Book added!");

        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE constraint failed")) {
                System.out.println("This book is already in the library.");
            } else {
                System.out.println("Error adding book: " + e.getMessage());
            }
        }
    }

    public void removeBook(String isbn) {
        Connection conn = DatabaseManager.connect();
        if (conn == null) {
            System.out.println("Database connection failed!");
            return;
        }

        String sql = "DELETE FROM books WHERE isbn = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, isbn);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Book deleted!");
            } else {
                System.out.println("Book not found!");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting book: " + e.getMessage());
        }
    }

    public ArrayList<Book> searchByTitle(String title) {
        Connection conn = DatabaseManager.connect();
        if (conn == null) {
            System.out.println("Database connection failed!");
            return new ArrayList<>();
        }

        String sql = "SELECT * FROM books WHERE title LIKE ?";

        ArrayList<Book> searchResults = new ArrayList<>();

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + title + "%");

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                String bookTitle = rs.getString("title");
                String author = rs.getString("author");
                String isbn = rs.getString("isbn");

                searchResults.add(new Book(bookTitle, author, isbn));
            }
        } catch (SQLException e){
            System.out.println("Error searching for book: " + e.getMessage());
        }
        return searchResults;
    }

    public String displayBooks() {
        Connection conn = DatabaseManager.connect();
        if (conn == null) {
            System.out.println("Database connection failed!");
            return null;
        }

        String sql = "SELECT * FROM books";
        StringBuilder bookList = new StringBuilder();
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            boolean hasBooks = false;

            while (rs.next()) {
                hasBooks = true;
                String title = rs.getString("title");
                String author = rs.getString("author");
                String isbn = rs.getString("isbn");

                bookList.append("Title: ").append(title)
                        .append(", Author: ").append(author)
                        .append(", ISBN: ").append(isbn)
                        .append("\n");
            }

            if (!hasBooks) {
                System.out.println("No books in the library!");
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving books: " + e.getMessage());
        }

        return bookList.toString();
    }
}
