package id.lsp.perpustakaan;

import id.lsp.perpustakaan.model.Anggota;
import id.lsp.perpustakaan.model.Buku;
import id.lsp.perpustakaan.model.Petugas;
import id.lsp.perpustakaan.service.LibraryService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LibraryTest {
    private final LibraryService libraryService = new LibraryService();

    @Test
    void testLoginPetugasBerhasil() {
        Optional<Petugas> petugas = libraryService.login("admin", "admin123");

        assertTrue(petugas.isPresent());
        assertEquals("Administrator", petugas.get().getNama());
    }

    @Test
    void testLoginPetugasGagal() {
        Optional<Petugas> petugas = libraryService.login("admin", "salah");

        assertFalse(petugas.isPresent());
    }

    @Test
    void testTambahBukuValidasiJudulKosong() {
        Buku buku = new Buku(1, "", "Penulis", 1);

        assertThrows(IllegalArgumentException.class, () -> libraryService.validasiBuku(buku));
    }

    @Test
    void testTambahAnggotaValidasiTeleponKosong() {
        Anggota anggota = new Anggota(1, "Rina", "Jl. Mawar", "");

        assertThrows(IllegalArgumentException.class, () -> libraryService.validasiAnggota(anggota));
    }

    @Test
    void testPeminjamanMengurangiStokBuku() {
        Buku buku = new Buku(1, "Java", "Duke", 2);

        buku.kurangiStok();

        assertEquals(1, buku.getStok());
    }

    @Test
    void testTanggalJatuhTempo() {
        LocalDate pinjam = LocalDate.of(2026, 5, 30);
        LocalDate jatuhTempo = libraryService.hitungTanggalJatuhTempo(pinjam);

        assertEquals(pinjam.plusDays(7), jatuhTempo);
    }
}
