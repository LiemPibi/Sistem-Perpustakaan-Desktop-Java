package id.lsp.perpustakaan.view;

import id.lsp.perpustakaan.dao.AnggotaDAO;
import id.lsp.perpustakaan.model.Anggota;

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
 * Form CRUD anggota perpustakaan.
 */
public class FormAnggota extends JFrame {
    private final AnggotaDAO anggotaDAO = new AnggotaDAO();
    private final JTextField idField = new JTextField();
    private final JTextField namaField = new JTextField();
    private final JTextField alamatField = new JTextField();
    private final JTextField teleponField = new JTextField();
    private final DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Alamat", "Telepon"}, 0);

    public FormAnggota() {
        setTitle("Kelola Anggota");
        setSize(720, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(formPanel(), BorderLayout.NORTH);
        add(buttonPanel(), BorderLayout.SOUTH);
        loadData();
    }

    private GridLayoutPanel formPanel() {
        GridLayoutPanel panel = new GridLayoutPanel(4, 2);
        panel.add(new JLabel("ID (isi untuk ubah/hapus)"));
        panel.add(idField);
        panel.add(new JLabel("Nama"));
        panel.add(namaField);
        panel.add(new JLabel("Alamat"));
        panel.add(alamatField);
        panel.add(new JLabel("Telepon"));
        panel.add(teleponField);
        return panel;
    }

    private GridLayoutPanel buttonPanel() {
        GridLayoutPanel panel = new GridLayoutPanel(1, 4);
        JButton tambah = new JButton("Tambah");
        JButton ubah = new JButton("Ubah");
        JButton hapus = new JButton("Hapus");
        JButton refresh = new JButton("Refresh");
        tambah.addActionListener(event -> simpan(false));
        ubah.addActionListener(event -> simpan(true));
        hapus.addActionListener(event -> hapus());
        refresh.addActionListener(event -> loadData());
        panel.add(tambah);
        panel.add(ubah);
        panel.add(hapus);
        panel.add(refresh);
        return panel;
    }

    private void simpan(boolean update) {
        try {
            Anggota anggota = new Anggota(parseId(), namaField.getText(), alamatField.getText(), teleponField.getText());
            if (update) {
                anggotaDAO.ubah(anggota);
            } else {
                anggotaDAO.tambah(anggota);
            }
            loadData();
        } catch (SQLException | NumberFormatException ex) {
            showError(ex);
        }
    }

    private void hapus() {
        try {
            anggotaDAO.hapus(parseId());
            loadData();
        } catch (SQLException | NumberFormatException ex) {
            showError(ex);
        }
    }

    private int parseId() {
        return idField.getText().isBlank() ? 0 : Integer.parseInt(idField.getText());
    }

    private void loadData() {
        try {
            tableModel.setRowCount(0);
            for (Anggota anggota : anggotaDAO.cariSemua()) {
                tableModel.addRow(new Object[]{anggota.getId(), anggota.getNama(), anggota.getAlamat(), anggota.getTelepon()});
            }
        } catch (SQLException ex) {
            showError(ex);
        }
    }

    private void showError(Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
    }
}
