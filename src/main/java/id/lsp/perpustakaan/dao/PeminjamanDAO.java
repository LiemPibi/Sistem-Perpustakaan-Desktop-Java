package id.lsp.perpustakaan.dao;

import id.lsp.perpustakaan.model.Anggota;
import id.lsp.perpustakaan.model.Buku;
import id.lsp.perpustakaan.model.Peminjaman;
import id.lsp.perpustakaan.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO untuk transaksi peminjaman, pengembalian, dan laporan.
 */
public class PeminjamanDAO {
    public int tambah(Peminjaman peminjaman) throws SQLException {
        String sql = "INSERT INTO peminjaman (anggota_id, buku_id, tanggal_pinjam, tanggal_kembali) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, peminjaman.getAnggota().getId());
            statement.setInt(2, peminjaman.getBuku().getId());
            statement.setDate(3, Date.valueOf(peminjaman.getTanggalPinjam()));
            if (peminjaman.getTanggalKembali() == null) {
                statement.setDate(4, null);
            } else {
                statement.setDate(4, Date.valueOf(peminjaman.getTanggalKembali()));
            }
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        }
        return 0;
    }

    public List<Peminjaman> cariSemua() throws SQLException {
        List<Peminjaman> laporan = new ArrayList<>();
        String sql = """
                SELECT p.id, p.tanggal_pinjam, p.tanggal_kembali,
                       a.id AS anggota_id, a.nama, a.alamat, a.telepon,
                       b.id AS buku_id, b.judul, b.penulis, b.stok
                FROM peminjaman p
                JOIN anggota a ON p.anggota_id = a.id
                JOIN buku b ON p.buku_id = b.id
                ORDER BY p.tanggal_pinjam DESC, p.id DESC
                """;
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                laporan.add(mapResultSet(resultSet));
            }
        }
        return laporan;
    }

    public void kembalikanBuku(int peminjamanId, LocalDate tanggalKembali) throws SQLException {
        String sql = "UPDATE peminjaman SET tanggal_kembali = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(tanggalKembali));
            statement.setInt(2, peminjamanId);
            statement.executeUpdate();
        }
    }

    private Peminjaman mapResultSet(ResultSet resultSet) throws SQLException {
        Date tanggalKembali = resultSet.getDate("tanggal_kembali");
        Anggota anggota = new Anggota(
                resultSet.getInt("anggota_id"),
                resultSet.getString("nama"),
                resultSet.getString("alamat"),
                resultSet.getString("telepon")
        );
        Buku buku = new Buku(
                resultSet.getInt("buku_id"),
                resultSet.getString("judul"),
                resultSet.getString("penulis"),
                resultSet.getInt("stok")
        );
        return new Peminjaman(
                resultSet.getInt("id"),
                anggota,
                buku,
                resultSet.getDate("tanggal_pinjam").toLocalDate(),
                tanggalKembali == null ? null : tanggalKembali.toLocalDate()
        );
    }
}
