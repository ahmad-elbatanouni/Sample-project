package com.mycompany.registration;

import static com.mycompany.registration.RegistrationFrame.DB_URL;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddSubject {

    int studentId;

    public AddSubject(int studentId) {
        this.studentId = studentId;

        JFrame addSubjectFrame = new JFrame("Add Subject");
        addSubjectFrame.setLocationRelativeTo(null);
        addSubjectFrame.setSize(300, 120);

        // Create components for adding a subject
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5)); // 2 rows, 2 columns, gap of 5 pixels
        JLabel subjectLabel = new JLabel("Subject title:");
        JTextField subjectField = new JTextField(15);
        JButton addButton = new JButton("Add");

        // Add action listener to the "Add" button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = subjectField.getText();

                try {
                    Connection conn = DriverManager.getConnection(DB_URL);
                    String sql = "INSERT INTO subjects (studentId, title) VALUES (?,?)";
                    System.out.println(title);
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, studentId);
                    pstmt.setString(2, title);
                    pstmt.executeUpdate();
                    pstmt.close();
                    conn.close();
                    JOptionPane.showMessageDialog(addSubjectFrame, "Subject stored successfully!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(addSubjectFrame, "Error storing subject: " + ex.getMessage());
                }
            }
        });

        panel.add(subjectLabel);
        panel.add(subjectField);
        panel.add(new JLabel());

        panel.add(addButton);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add the panel to the frame
        addSubjectFrame.add(panel);

        // Make the frame visible
        addSubjectFrame.setVisible(true);
    }

}
