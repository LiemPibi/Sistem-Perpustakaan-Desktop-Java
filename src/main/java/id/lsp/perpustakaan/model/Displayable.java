package id.lsp.perpustakaan.model;

/**
 * Kontrak untuk objek yang dapat menampilkan ringkasan data.
 */
public interface Displayable {
    /**
     * Menghasilkan teks ringkas untuk ditampilkan di UI atau log.
     *
     * @return ringkasan data
     */
    String displayData();
}
