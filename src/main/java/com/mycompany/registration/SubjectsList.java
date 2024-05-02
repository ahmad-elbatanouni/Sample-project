package com.mycompany.registration;

import static com.mycompany.registration.RegistrationFrame.DB_URL;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SubjectsList {

    int studentId;

    public SubjectsList(int studentId) {
        this.studentId = studentId;
        JFrame usersFrame = new JFrame("List of Subjects");
        usersFrame.setLocationRelativeTo(null);
        usersFrame.setSize(400, 300);

        // Retrieve data from the database and populate a DefaultTableModel
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Title");

        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM subjects where studentId=" + this.studentId;
            
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");

                Object[] rowData = {id, title};
                model.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create JTable with the populated model
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        usersFrame.add(scrollPane);

        usersFrame.setVisible(true);
    }

}
