package id.lsp.perpustakaan.view;

import javax.swing.JPanel;
import java.awt.GridLayout;

/**
 * Panel kecil untuk menyederhanakan pembuatan form berbasis GridLayout.
 */
class GridLayoutPanel extends JPanel {
    GridLayoutPanel(int rows, int columns) {
        super(new GridLayout(rows, columns, 8, 8));
    }
}
