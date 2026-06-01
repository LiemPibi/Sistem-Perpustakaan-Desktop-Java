package id.lsp.perpustakaan.view;

import id.lsp.perpustakaan.model.Anggota;
import id.lsp.perpustakaan.model.Buku;
import id.lsp.perpustakaan.model.Peminjaman;
import id.lsp.perpustakaan.service.LibraryService;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Form transaksi peminjaman, pengembalian, dan laporan peminjaman.
 */
public class FormPeminjaman extends JFrame {
    private final LibraryService libraryService = new LibraryService();
    private final JTextField anggotaIdField = new JTextField();
    private final JTextField bukuIdField = new JTextField();
    private final JTextField peminjamanIdField = new JTextField();
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"ID", "Anggota", "Buku", "Tanggal Pinjam", "Harus Kembali", "Tanggal Kembali"}, 0
    );
    private List<Anggota> daftarAnggota;
    private List<Buku> daftarBuku;
    private List<Peminjaman> laporan;

    public FormPeminjaman() {
        setTitle("Peminjaman, Pengembalian, dan Laporan");
        setSize(820, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        add(formPanel(), BorderLayout.NORTH);
        add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER);
        add(buttonPanel(), BorderLayout.SOUTH);
        refreshData();
    }

    private GridLayoutPanel formPanel() {
        GridLayoutPanel panel = new GridLayoutPanel(3, 2);
        panel.add(new JLabel("ID Anggota"));
        panel.add(anggotaIdField);
        panel.add(new JLabel("ID Buku"));
        panel.add(bukuIdField);
        panel.add(new JLabel("ID Peminjaman untuk pengembalian"));
        panel.add(peminjamanIdField);
        return panel;
    }

    private GridLayoutPanel buttonPanel() {
        GridLayoutPanel panel = new GridLayoutPanel(1, 3);
        JButton pinjam = new JButton("Pinjam Buku");
        JButton kembali = new JButton("Kembalikan Buku");
        JButton refresh = new JButton("Refresh Laporan");
        pinjam.addActionListener(event -> pinjamBuku());
        kembali.addActionListener(event -> kembalikanBuku());
        refresh.addActionListener(event -> refreshData());
        panel.add(pinjam);
        panel.add(kembali);
        panel.add(refresh);
        return panel;
    }

    private void pinjamBuku() {
        try {
            Anggota anggota = findAnggota(Integer.parseInt(anggotaIdField.getText()));
            Buku buku = findBuku(Integer.parseInt(bukuIdField.getText()));
            libraryService.pinjamBuku(anggota, buku, LocalDate.now());
            refreshData();
        } catch (SQLException | IllegalArgumentException | IllegalStateException ex) {
            showError(ex);
        }
    }

    private void kembalikanBuku() {
        try {
            int peminjamanId = Integer.parseInt(peminjamanIdField.getText());
            Peminjaman peminjaman = laporan.stream()
                    .filter(item -> item.getId() == peminjamanId)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("ID peminjaman tidak ditemukan"));
            libraryService.kembalikanBuku(peminjaman, LocalDate.now());
            refreshData();
        } catch (SQLException | IllegalArgumentException | IllegalStateException ex) {
            showError(ex);
        }
    }

    private void refreshData() {
        try {
            daftarAnggota = libraryService.daftarAnggota();
            daftarBuku = libraryService.katalogBuku("");
            laporan = libraryService.laporanPeminjaman();
            tableModel.setRowCount(0);
            for (Peminjaman peminjaman : laporan) {
                tableModel.addRow(new Object[]{
                        peminjaman.getId(),
                        peminjaman.getAnggota().displayData(),
                        peminjaman.getBuku().displayData(),
                        peminjaman.getTanggalPinjam(),
                        peminjaman.getTanggalHarusKembali(),
                        peminjaman.getTanggalKembali() == null ? "Belum kembali" : peminjaman.getTanggalKembali()
                });
            }
        } catch (SQLException ex) {
            showError(ex);
        }
    }

    private Anggota findAnggota(int id) {
        return daftarAnggota.stream()
                .filter(anggota -> anggota.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("ID anggota tidak ditemukan"));
    }

    private Buku findBuku(int id) {
        return daftarBuku.stream()
                .filter(buku -> buku.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("ID buku tidak ditemukan"));
    }

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
    }
}
