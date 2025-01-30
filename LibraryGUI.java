import models.Book;
import services.Library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LibraryGUI extends JFrame {

    private JTextField titleField, authorField, isbnField;
    private JButton addButton, removeButton, searchButton, displayButton;
    private JTextArea textArea;
    private Library library;

    public LibraryGUI() {
        // Set up the window
        setTitle("Personal Library Management System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        library = new Library();

        JPanel topPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        titleField = new JTextField(); authorField = new JTextField(); isbnField = new JTextField();
        addButton = new JButton("Add"); removeButton = new JButton("Remove");
        searchButton = new JButton("Search"); displayButton = new JButton("Display");

        topPanel.add(new JLabel("Title:")); topPanel.add(titleField); topPanel.add(addButton);
        topPanel.add(new JLabel("Author:")); topPanel.add(authorField); topPanel.add(removeButton);
        topPanel.add(new JLabel("ISBN:")); topPanel.add(isbnField); topPanel.add(searchButton);
        topPanel.add(new JLabel("")); topPanel.add(displayButton); topPanel.add(new JLabel(""));

        add(topPanel, BorderLayout.NORTH);

        //Scrollable Area
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String author = authorField.getText();
                String isbn = isbnField.getText();

                if (title.isEmpty() || author.isEmpty() || isbn.isEmpty()) {
                    textArea.setText("All fields must be filled!");
                    return;
                }
                library.addBook(new Book(title, author, isbn));
                textArea.setText("Book added successfully.");
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = isbnField.getText();

                if(isbn.isEmpty()) {
                    textArea.setText("Please enter an ISBN.");
                    return;
                }

                library.removeBook(isbn);
                textArea.setText("Book removed.");
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                if(title.isEmpty()) {
                    textArea.setText("Please enter a title.");
                    return;
                }

                ArrayList<Book> results = library.searchByTitle(title);
                if(results.isEmpty()) {
                    textArea.setText("No books found.");
                } else {
                    textArea.setText("Search Results:\n");
                    for(Book book : results) {
                        textArea.append(book.toString() + "\n");
                    }
                }
            }
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(library.displayBooks());
            }
        });

        setVisible(true);
    }

    public static void  main(String[] args) {
        new LibraryGUI();
    }
}
