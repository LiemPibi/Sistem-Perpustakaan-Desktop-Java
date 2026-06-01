package id.lsp.perpustakaan.dao;

import id.lsp.perpustakaan.model.Buku;
import id.lsp.perpustakaan.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO untuk CRUD data buku dan katalog.
 */
public class BukuDAO implements Crudable<Buku> {
    @Override
    public void tambah(Buku buku) throws SQLException {
        String sql = "INSERT INTO buku (judul, penulis, stok) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, buku.getJudul());
            statement.setString(2, buku.getPenulis());
            statement.setInt(3, buku.getStok());
            statement.executeUpdate();
        }
    }

    @Override
    public List<Buku> cariSemua() throws SQLException {
        return cariKatalog("");
    }

    public List<Buku> cariKatalog(String kataKunci) throws SQLException {
        List<Buku> daftarBuku = new ArrayList<>();
        String sql = "SELECT id, judul, penulis, stok FROM buku WHERE judul LIKE ? OR penulis LIKE ? ORDER BY judul";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            String pencarian = "%" + kataKunci + "%";
            statement.setString(1, pencarian);
            statement.setString(2, pencarian);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    daftarBuku.add(mapResultSet(resultSet));
                }
            }
        }
        return daftarBuku;
    }

    @Override
    public void ubah(Buku buku) throws SQLException {
        String sql = "UPDATE buku SET judul = ?, penulis = ?, stok = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, buku.getJudul());
            statement.setString(2, buku.getPenulis());
            statement.setInt(3, buku.getStok());
            statement.setInt(4, buku.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void hapus(int id) throws SQLException {
        String sql = "DELETE FROM buku WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public void updateStok(int bukuId, int stok) throws SQLException {
        String sql = "UPDATE buku SET stok = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, stok);
            statement.setInt(2, bukuId);
            statement.executeUpdate();
        }
    }

    private Buku mapResultSet(ResultSet resultSet) throws SQLException {
        return new Buku(
                resultSet.getInt("id"),
                resultSet.getString("judul"),
                resultSet.getString("penulis"),
                resultSet.getInt("stok")
        );
    }
}
