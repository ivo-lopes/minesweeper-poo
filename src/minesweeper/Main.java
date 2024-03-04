package minesweeper;

import javax.swing.SwingUtilities;

import minesweeper.interfacegrafica.MenuPrincipal;

public class Main {
        public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuPrincipal::new);
    }
}
