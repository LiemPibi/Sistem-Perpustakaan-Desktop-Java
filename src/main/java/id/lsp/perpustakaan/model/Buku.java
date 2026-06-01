package id.lsp.perpustakaan.model;

/**
 * Model data buku perpustakaan.
 */
public class Buku implements Displayable {
    private int id;
    private String judul;
    private String penulis;
    private int stok;

    public Buku() {
    }

    public Buku(int id, String judul, String penulis, int stok) {
        this.id = id;
        this.judul = judul;
        this.penulis = penulis;
        this.stok = stok;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public boolean tersedia() {
        return stok > 0;
    }

    public void kurangiStok() {
        if (!tersedia()) {
            throw new IllegalStateException("Stok buku tidak tersedia");
        }
        stok--;
    }

    public void tambahStok() {
        stok++;
    }

    @Override
    public String displayData() {
        return id + " - " + judul + " oleh " + penulis + " (stok: " + stok + ")";
    }
}
