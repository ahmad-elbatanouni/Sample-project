package com.mycompany.registration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class RegistrationFrame {

    static final String DB_URL = "jdbc:sqlite:test.db";

    public RegistrationFrame() {
        createDatabaseTable();

        JFrame mainFrame = new JFrame("Main Screen");
        mainFrame.setSize(400, 150);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10)); // 1 row, 2 columns, gap of 10 pixels
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // top, right, bottom, left

        // Create "Add Student" button
        JButton addButton = new JButton("Add Student");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddStudent();
            }
        });

        JButton showButton = new JButton("Show Students");
        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudentsList();
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(showButton);

        mainFrame.add(buttonPanel);

        mainFrame.setVisible(true);
    }

    
    private static void createDatabaseTable() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement(); 

            String sql = "CREATE TABLE IF NOT EXISTS students (\n"
                    + "    id INTEGER PRIMARY KEY,\n"
                    + "    name TEXT,\n"
                    + "    year INTEGER\n"
                    + ");\n"
                    + "\n"
                    + "CREATE TABLE IF NOT EXISTS subjects (\n"
                    + "    id INTEGER PRIMARY KEY,\n"
                    + "    studentId INTEGER,\n"
                    + "    title TEXT,\n"
                    + "    FOREIGN KEY (studentId) REFERENCES students(id)\n"
                    + ");";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
