package id.lsp.perpustakaan.view;

import id.lsp.perpustakaan.model.Petugas;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.GridLayout;

/**
 * Menu utama aplikasi setelah petugas berhasil login.
 */
public class MainMenu extends JFrame {
    public MainMenu(Petugas petugas) {
        setTitle("Sistem Perpustakaan - " + petugas.displayData());
        setSize(420, 280);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 8, 8));

        JButton anggotaButton = new JButton("Kelola Anggota");
        JButton bukuButton = new JButton("Kelola Buku & Katalog");
        JButton peminjamanButton = new JButton("Peminjaman, Pengembalian & Laporan");
        JButton keluarButton = new JButton("Keluar");

        anggotaButton.addActionListener(event -> new FormAnggota().setVisible(true));
        bukuButton.addActionListener(event -> new FormBuku().setVisible(true));
        peminjamanButton.addActionListener(event -> new FormPeminjaman().setVisible(true));
        keluarButton.addActionListener(event -> System.exit(0));

        add(new JLabel("Selamat datang, " + petugas.getNama()));
        add(anggotaButton);
        add(bukuButton);
        add(peminjamanButton);
        add(keluarButton);
    }
}
