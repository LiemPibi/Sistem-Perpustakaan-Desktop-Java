package id.lsp.perpustakaan.model;

/**
 * Representasi petugas perpustakaan yang dapat login ke aplikasi.
 */
public class Petugas extends User {
    private String role;

    public Petugas() {
    }

    public Petugas(int id, String username, String nama, String role) {
        super(id, username, nama);
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String displayData() {
        return getNama() + " - " + role;
    }
}
