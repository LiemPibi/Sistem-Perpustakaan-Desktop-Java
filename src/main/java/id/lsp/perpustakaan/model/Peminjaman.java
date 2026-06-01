package id.lsp.perpustakaan.model;

import java.time.LocalDate;

/**
 * Model transaksi peminjaman dan pengembalian buku.
 */
public class Peminjaman implements Displayable {
    private int id;
    private Anggota anggota;
    private Buku buku;
    private LocalDate tanggalPinjam;
    private LocalDate tanggalHarusKembali;
    private LocalDate tanggalKembali;

    public Peminjaman() {
    }

    public Peminjaman(int id, Anggota anggota, Buku buku, LocalDate tanggalPinjam, LocalDate tanggalKembali) {
        this(id, anggota, buku, tanggalPinjam, tanggalPinjam == null ? null : tanggalPinjam.plusDays(7), tanggalKembali);
    }

    public Peminjaman(int id, Anggota anggota, Buku buku, LocalDate tanggalPinjam, LocalDate tanggalHarusKembali, LocalDate tanggalKembali) {
        this.id = id;
        this.anggota = anggota;
        this.buku = buku;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalHarusKembali = tanggalHarusKembali;
        this.tanggalKembali = tanggalKembali;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Anggota getAnggota() {
        return anggota;
    }

    public void setAnggota(Anggota anggota) {
        this.anggota = anggota;
    }

    public Buku getBuku() {
        return buku;
    }

    public void setBuku(Buku buku) {
        this.buku = buku;
    }

    public LocalDate getTanggalPinjam() {
        return tanggalPinjam;
    }

    public void setTanggalPinjam(LocalDate tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    public LocalDate getTanggalHarusKembali() {
        return tanggalHarusKembali;
    }

    public void setTanggalHarusKembali(LocalDate tanggalHarusKembali) {
        this.tanggalHarusKembali = tanggalHarusKembali;
    }

    public LocalDate getTanggalKembali() {
        return tanggalKembali;
    }

    public void setTanggalKembali(LocalDate tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }

    public boolean sudahDikembalikan() {
        return tanggalKembali != null;
    }

    @Override
    public String displayData() {
        String status = sudahDikembalikan() ? "Kembali: " + tanggalKembali : "Harus kembali: " + tanggalHarusKembali;
        return id + " - " + anggota.getNama() + " meminjam " + buku.getJudul() + " (" + status + ")";
    }
}
