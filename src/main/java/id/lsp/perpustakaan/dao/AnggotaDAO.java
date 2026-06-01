package id.lsp.perpustakaan.dao;

import id.lsp.perpustakaan.model.Anggota;
import id.lsp.perpustakaan.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO untuk CRUD data anggota.
 */
public class AnggotaDAO implements Crudable<Anggota> {
    @Override
    public void tambah(Anggota anggota) throws SQLException {
        String sql = "INSERT INTO anggota (nama, alamat, telepon) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, anggota.getNama());
            statement.setString(2, anggota.getAlamat());
            statement.setString(3, anggota.getTelepon());
            statement.executeUpdate();
        }
    }

    @Override
    public List<Anggota> cariSemua() throws SQLException {
        List<Anggota> daftarAnggota = new ArrayList<>();
        String sql = "SELECT id, nama, alamat, telepon FROM anggota ORDER BY nama";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                daftarAnggota.add(mapResultSet(resultSet));
            }
        }
        return daftarAnggota;
    }

    @Override
    public void ubah(Anggota anggota) throws SQLException {
        String sql = "UPDATE anggota SET nama = ?, alamat = ?, telepon = ? WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, anggota.getNama());
            statement.setString(2, anggota.getAlamat());
            statement.setString(3, anggota.getTelepon());
            statement.setInt(4, anggota.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void hapus(int id) throws SQLException {
        String sql = "DELETE FROM anggota WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    private Anggota mapResultSet(ResultSet resultSet) throws SQLException {
        return new Anggota(
                resultSet.getInt("id"),
                resultSet.getString("nama"),
                resultSet.getString("alamat"),
                resultSet.getString("telepon")
        );
    }
}
