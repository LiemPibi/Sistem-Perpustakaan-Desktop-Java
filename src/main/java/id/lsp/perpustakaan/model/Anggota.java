package id.lsp.perpustakaan.model;

/**
 * Model data anggota perpustakaan.
 */
public class Anggota implements Displayable {
    private int id;
    private String nama;
    private String alamat;
    private String telepon;

    public Anggota() {
    }

    public Anggota(int id, String nama, String alamat, String telepon) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.telepon = telepon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    @Override
    public String displayData() {
        return id + " - " + nama + " (" + telepon + ")";
    }
}
