package id.lsp.perpustakaan.view;

import id.lsp.perpustakaan.dao.BukuDAO;
import id.lsp.perpustakaan.model.Buku;

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

/**
 * Form CRUD buku sekaligus katalog pencarian buku.
 */
public class FormBuku extends JFrame {
    private final BukuDAO bukuDAO = new BukuDAO();
    private final JTextField idField = new JTextField();
    private final JTextField judulField = new JTextField();
    private final JTextField penulisField = new JTextField();
    private final JTextField stokField = new JTextField();
    private final JTextField cariField = new JTextField();
    private final DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID", "Judul", "Penulis", "Stok"}, 0);

    public FormBuku() {
        setTitle("Kelola Buku dan Katalog");
        setSize(760, 460);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        add(formPanel(), BorderLayout.NORTH);
        add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER);
        add(buttonPanel(), BorderLayout.SOUTH);
        loadData("");
    }

    private GridLayoutPanel formPanel() {
        GridLayoutPanel panel = new GridLayoutPanel(5, 2);
        panel.add(new JLabel("ID (isi untuk ubah/hapus)"));
        panel.add(idField);
        panel.add(new JLabel("Judul"));
        panel.add(judulField);
        panel.add(new JLabel("Penulis"));
        panel.add(penulisField);
        panel.add(new JLabel("Stok"));
        panel.add(stokField);
        panel.add(new JLabel("Cari katalog"));
        panel.add(cariField);
        return panel;
    }

    private GridLayoutPanel buttonPanel() {
        GridLayoutPanel panel = new GridLayoutPanel(1, 5);
        JButton tambah = new JButton("Tambah");
        JButton ubah = new JButton("Ubah");
        JButton hapus = new JButton("Hapus");
        JButton cari = new JButton("Cari");
        JButton refresh = new JButton("Refresh");
        tambah.addActionListener(event -> simpan(false));
        ubah.addActionListener(event -> simpan(true));
        hapus.addActionListener(event -> hapus());
        cari.addActionListener(event -> loadData(cariField.getText()));
        refresh.addActionListener(event -> loadData(""));
        panel.add(tambah);
        panel.add(ubah);
        panel.add(hapus);
        panel.add(cari);
        panel.add(refresh);
        return panel;
    }

    private void simpan(boolean update) {
        try {
            Buku buku = new Buku(parseId(), judulField.getText(), penulisField.getText(), Integer.parseInt(stokField.getText()));
            if (update) {
                bukuDAO.ubah(buku);
            } else {
                bukuDAO.tambah(buku);
            }
            loadData("");
        } catch (SQLException | NumberFormatException ex) {
            showError(ex);
        }
    }

    private void hapus() {
        try {
            bukuDAO.hapus(parseId());
            loadData("");
        } catch (SQLException | NumberFormatException ex) {
            showError(ex);
        }
    }

    private int parseId() {
        return idField.getText().isBlank() ? 0 : Integer.parseInt(idField.getText());
    }

    private void loadData(String kataKunci) {
        try {
            tableModel.setRowCount(0);
            for (Buku buku : bukuDAO.cariKatalog(kataKunci)) {
                tableModel.addRow(new Object[]{buku.getId(), buku.getJudul(), buku.getPenulis(), buku.getStok()});
            }
        } catch (SQLException ex) {
            showError(ex);
        }
    }

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
    }
}
