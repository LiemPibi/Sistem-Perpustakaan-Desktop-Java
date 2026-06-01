package id.lsp.perpustakaan.view;

import id.lsp.perpustakaan.model.Petugas;
import id.lsp.perpustakaan.service.LibraryService;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.util.Optional;

/**
 * Form login petugas. Demo akun: admin / admin123.
 */
public class LoginForm extends JFrame {
    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final LibraryService libraryService = new LibraryService();

    public LoginForm() {
        setTitle("Login Petugas");
        setSize(360, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 8, 8));

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(event -> login());

        add(new JLabel("Username"));
        add(usernameField);
        add(new JLabel("Password"));
        add(passwordField);
        add(new JLabel("Demo: admin/admin123"));
        add(loginButton);
    }

    private void login() {
        String password = new String(passwordField.getPassword());
        Optional<Petugas> petugas = libraryService.login(usernameField.getText(), password);
        if (petugas.isPresent()) {
            new MainMenu(petugas.get()).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Username atau password salah", "Login gagal", JOptionPane.ERROR_MESSAGE);
        }
    }
}
