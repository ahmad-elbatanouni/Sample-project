package com.mycompany.registration;

import static com.mycompany.registration.RegistrationFrame.DB_URL;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddStudent {

    public AddStudent() {
        // Create a frame
        JFrame frame = new JFrame("New Student");
        frame.setSize(400, 300); // Set size
        frame.setLocationRelativeTo(null);

        // Create a panel with GridLayout
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10)); // 3 rows, 1 column, horizontal and vertical gap of 10 pixels

        // Create components
        JPanel namePanel = new JPanel(new FlowLayout()); // Panel for name components
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(15);
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        JPanel agePanel = new JPanel(new FlowLayout()); // Panel for age components
        JLabel ageLabel = new JLabel("year:");
        JTextField ageField = new JTextField(15);
        agePanel.add(ageLabel);
        agePanel.add(ageField);

        JPanel buttonPanel = new JPanel(new FlowLayout()); // Panel for button
        JButton submitButton = new JButton("Submit");

        // Add action listener to the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String year = ageField.getText();
                // Store data in the database
                try {

                    Connection conn = DriverManager.getConnection(DB_URL);
                    Statement stmt = conn.createStatement();

                    String sqlQuery = "Insert into students(name, year) values(\""+name+"\", \"" + year +  "\")";
                    stmt.executeUpdate(sqlQuery);

                    Connection conn = DriverManager.getConnection(DB_URL);
                    String sql = "INSERT INTO students (name, year) VALUES (?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, name);
                    pstmt.setString(2, year);
                    pstmt.executeUpdate();
                    pstmt.close();
                    conn.close();
                    JOptionPane.showMessageDialog(frame, "Data stored successfully!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error storing data: " + ex.getMessage());
                }
            }
        });

        buttonPanel.add(submitButton);

        // Add panels to the main panel
        panel.add(namePanel);
        panel.add(agePanel);
        panel.add(buttonPanel);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add panel to the frame
        frame.add(panel);

        // Make the frame visible
        frame.setVisible(true);
    }

}
