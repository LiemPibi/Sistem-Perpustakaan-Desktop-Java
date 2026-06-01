package id.lsp.perpustakaan.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utilitas koneksi database MySQL untuk aplikasi perpustakaan.
 */
public final class DatabaseConnection {

    private static final String URL = System.getProperty(
            "db.url",
            "jdbc:mysql://127.0.0.1:3306/perpustakaan"
                    + "?useSSL=false"
                    + "&allowPublicKeyRetrieval=true"
                    + "&serverTimezone=UTC"
    );
    private static final String USER = System.getProperty("db.user", "root");
    private static final String PASS = System.getProperty("db.password", "");

    private DatabaseConnection() {
        // utility class
    }

    /**
     * Membuka koneksi baru ke database MySQL.
     *
     * @return koneksi database
     * @throws SQLException jika koneksi gagal
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
