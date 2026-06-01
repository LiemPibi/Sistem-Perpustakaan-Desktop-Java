package id.lsp.perpustakaan.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

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

    public static void initializeSchema() throws SQLException {
        try (Connection connection = getConnection()) {
            ensureTanggalHarusKembaliColumn(connection);
        }
    }

    private static void ensureTanggalHarusKembaliColumn(Connection connection) throws SQLException {
        Set<String> columns = getColumns(connection, "peminjaman");
        if (columns.isEmpty() || columns.contains("tanggal_harus_kembali")) {
            return;
        }

        try (Statement statement = connection.createStatement()) {
            if (columns.contains("tanggal-harus-kembali")) {
                statement.executeUpdate("""
                        ALTER TABLE peminjaman
                        CHANGE COLUMN `tanggal-harus-kembali` tanggal_harus_kembali DATE NULL
                        """);
            } else {
                statement.executeUpdate("""
                        ALTER TABLE peminjaman
                        ADD COLUMN tanggal_harus_kembali DATE NULL AFTER tanggal_pinjam
                        """);
            }
            statement.executeUpdate("""
                    UPDATE peminjaman
                    SET tanggal_harus_kembali = DATE_ADD(tanggal_pinjam, INTERVAL 7 DAY)
                    WHERE tanggal_harus_kembali IS NULL
                    """);
            statement.executeUpdate("""
                    ALTER TABLE peminjaman
                    MODIFY tanggal_harus_kembali DATE NOT NULL
                    """);
        }
    }

    private static Set<String> getColumns(Connection connection, String tableName) throws SQLException {
        Set<String> columns = new HashSet<>();
        DatabaseMetaData metaData = connection.getMetaData();
        try (ResultSet resultSet = metaData.getColumns(connection.getCatalog(), null, tableName, null)) {
            while (resultSet.next()) {
                columns.add(resultSet.getString("COLUMN_NAME"));
            }
        }
        return columns;
    }
}
