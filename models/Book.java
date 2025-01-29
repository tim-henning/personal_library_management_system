package models;

public class Book {

    private final String title;
    private final String author;
    private final String isbn;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return String.format("Book[Title='%s', Author='%s', ISBN='%s']", title, author, isbn);
    }
}
