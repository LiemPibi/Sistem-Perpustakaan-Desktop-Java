package id.lsp.perpustakaan;

import id.lsp.perpustakaan.util.DatabaseConnection;
import id.lsp.perpustakaan.view.LoginForm;

import java.sql.Connection;
import javax.swing.SwingUtilities;

public class LibraryApplication {

    public static void main(String[] args) {

        try {
            Connection conn = DatabaseConnection.getConnection();
            System.out.println("Koneksi database berhasil!");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            LoginForm login = new LoginForm();
            login.setVisible(true);
        });
    }
}