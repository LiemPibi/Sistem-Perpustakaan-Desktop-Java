package id.lsp.perpustakaan.model;

/**
 * Class induk untuk pengguna aplikasi.
 */
public class User implements Displayable {
    private int id;
    private String username;
    private String nama;

    public User() {
    }

    public User(int id, String username, String nama) {
        this.id = id;
        this.username = username;
        this.nama = nama;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    @Override
    public String displayData() {
        return nama + " (" + username + ")";
    }
}
