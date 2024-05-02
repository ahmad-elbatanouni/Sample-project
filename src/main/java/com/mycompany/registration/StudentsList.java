package com.mycompany.registration;

import static com.mycompany.registration.RegistrationFrame.DB_URL;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class StudentsList {

    public StudentsList() {
        JFrame usersFrame = new JFrame("List of Users");
        usersFrame.setLocationRelativeTo(null);
        usersFrame.setSize(400, 300);

        // Retrieve data from the database and populate a DefaultTableModel
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Age");

        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM students";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String year = rs.getString("year");

                Object[] rowData = {id, name, year};
                model.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create JTable with the populated model
        JTable table = new JTable(model);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable target = (JTable) e.getSource();
                int row = target.getSelectedRow();
                int userId = (int) target.getValueAt(row, 0);

                int choice = JOptionPane.showOptionDialog(usersFrame, "Do you want to enter a subject or show a list of subjects?", "User Options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Enter Subject", "Show Subjects"}, "Enter Subject");

                if (choice == JOptionPane.YES_OPTION) {
                    new AddSubject(userId);
                } else if (choice == JOptionPane.NO_OPTION) {
                    new SubjectsList(userId);
                }
            }

        });

        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        usersFrame.add(scrollPane);

        usersFrame.setVisible(true);
    }

}
