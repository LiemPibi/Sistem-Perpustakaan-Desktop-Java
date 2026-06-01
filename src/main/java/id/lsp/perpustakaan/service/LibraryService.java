package id.lsp.perpustakaan.service;

import id.lsp.perpustakaan.dao.AnggotaDAO;
import id.lsp.perpustakaan.dao.BukuDAO;
import id.lsp.perpustakaan.dao.PeminjamanDAO;
import id.lsp.perpustakaan.model.Anggota;
import id.lsp.perpustakaan.model.Buku;
import id.lsp.perpustakaan.model.Peminjaman;
import id.lsp.perpustakaan.model.Petugas;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Layer service yang berisi aturan bisnis utama aplikasi perpustakaan.
 */
public class LibraryService {
    private final AnggotaDAO anggotaDAO;
    private final BukuDAO bukuDAO;
    private final PeminjamanDAO peminjamanDAO;

    public LibraryService() {
        this(new AnggotaDAO(), new BukuDAO(), new PeminjamanDAO());
    }

    public LibraryService(AnggotaDAO anggotaDAO, BukuDAO bukuDAO, PeminjamanDAO peminjamanDAO) {
        this.anggotaDAO = anggotaDAO;
        this.bukuDAO = bukuDAO;
        this.peminjamanDAO = peminjamanDAO;
    }

    public Optional<Petugas> login(String username, String password) {
        if ("admin".equals(username) && "admin123".equals(password)) {
            return Optional.of(new Petugas(1, username, "Administrator", "Petugas"));
        }
        return Optional.empty();
    }

    public void validasiBuku(Buku buku) {
        if (buku.getJudul() == null || buku.getJudul().isBlank()) {
            throw new IllegalArgumentException("Judul buku wajib diisi");
        }
        if (buku.getStok() < 0) {
            throw new IllegalArgumentException("Stok buku tidak boleh negatif");
        }
    }

    public void validasiAnggota(Anggota anggota) {
        if (anggota.getNama() == null || anggota.getNama().isBlank()) {
            throw new IllegalArgumentException("Nama anggota wajib diisi");
        }
        if (anggota.getTelepon() == null || anggota.getTelepon().isBlank()) {
            throw new IllegalArgumentException("Telepon anggota wajib diisi");
        }
    }

    public LocalDate hitungTanggalJatuhTempo(LocalDate tanggalPinjam) {
        return tanggalPinjam.plusDays(7);
    }

    public Peminjaman pinjamBuku(Anggota anggota, Buku buku, LocalDate tanggalPinjam) throws SQLException {
        validasiAnggota(anggota);
        validasiBuku(buku);
        if (!buku.tersedia()) {
            throw new IllegalStateException("Buku sedang habis");
        }
        buku.kurangiStok();
        bukuDAO.updateStok(buku.getId(), buku.getStok());
        Peminjaman peminjaman = new Peminjaman(0, anggota, buku, tanggalPinjam, null);
        int id = peminjamanDAO.tambah(peminjaman);
        peminjaman.setId(id);
        return peminjaman;
    }

    public void kembalikanBuku(Peminjaman peminjaman, LocalDate tanggalKembali) throws SQLException {
        if (peminjaman.sudahDikembalikan()) {
            throw new IllegalStateException("Buku sudah dikembalikan");
        }
        peminjaman.setTanggalKembali(tanggalKembali);
        peminjaman.getBuku().tambahStok();
        peminjamanDAO.kembalikanBuku(peminjaman.getId(), tanggalKembali);
        bukuDAO.updateStok(peminjaman.getBuku().getId(), peminjaman.getBuku().getStok());
    }

    public void tambahAnggota(Anggota anggota) throws SQLException {
        validasiAnggota(anggota);
        anggotaDAO.tambah(anggota);
    }

    public void tambahBuku(Buku buku) throws SQLException {
        validasiBuku(buku);
        bukuDAO.tambah(buku);
    }

    public List<Anggota> daftarAnggota() throws SQLException {
        return anggotaDAO.cariSemua();
    }

    public List<Buku> katalogBuku(String kataKunci) throws SQLException {
        return bukuDAO.cariKatalog(kataKunci);
    }

    public List<Peminjaman> laporanPeminjaman() throws SQLException {
        return peminjamanDAO.cariSemua();
    }
}
