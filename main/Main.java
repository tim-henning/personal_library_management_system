package main;

import models.Book;
import services.DatabaseManager;
import services.Library;

import java.util.ArrayList;

public class Main {

    public static void main (String[] args) {
        Library myLibrary = new Library();

        myLibrary.removeBook("978-0261102217");
        myLibrary.removeBook("978-0451524935");
    }
}
