package id.lsp.perpustakaan.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface generik untuk operasi CRUD pada database.
 *
 * @param <T> tipe model yang dikelola DAO
 */
public interface Crudable<T> {
    void tambah(T data) throws SQLException;

    List<T> cariSemua() throws SQLException;

    void ubah(T data) throws SQLException;

    void hapus(int id) throws SQLException;
}
