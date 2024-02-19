package minesweeper.tabuleiro;
import javax.swing.*;

import minesweeper.niveis.Dificil;
import minesweeper.niveis.Facil;
import minesweeper.niveis.Intermediario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaInicial extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TelaInicial() {
        setTitle("Campo Minado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1));

        JLabel label = new JLabel("Selecione o nível de dificuldade:");
        panel.add(label);

        String[] niveis = {"Fácil", "Intermediário", "Difícil"};
        JComboBox<String> comboBoxNiveis = new JComboBox<>(niveis);
        panel.add(comboBoxNiveis);
        
        JLabel labelNome = new JLabel("Digite seu nome:");
        panel.add(labelNome);

        JTextField textFieldNome = new JTextField();
        panel.add(textFieldNome);

        JButton iniciarJogoButton = new JButton("Iniciar Jogo");
        panel.add(iniciarJogoButton);

        iniciarJogoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedNivel = (String) comboBoxNiveis.getSelectedItem();
                int tamanhoTabuleiro = 0;
                int numMinas = 0;
                String nomeJogador = textFieldNome.getText();
                switch (selectedNivel) {
                    case "Fácil":
                        tamanhoTabuleiro = new Facil().getTamanhoTabuleiro();
                        numMinas = new Facil().getNumMinas();
                        break;
                    case "Intermediário":
                        tamanhoTabuleiro = new Intermediario().getTamanhoTabuleiro();
                        numMinas = new Intermediario().getNumMinas();
                        break;
                    case "Difícil":
                        tamanhoTabuleiro = new Dificil().getTamanhoTabuleiro();
                        numMinas = new Dificil().getNumMinas();
                        break;
                }
                dispose();
                iniciarJogo(tamanhoTabuleiro, numMinas, nomeJogador);
            }
        });

        add(panel);
        setVisible(true);
    }

    private void iniciarJogo(int tamanhoTabuleiro, int numMinas, String nomeJogador) {
    	SwingUtilities.invokeLater(() -> { 
            new CampoMinadoGUI(tamanhoTabuleiro, numMinas, nomeJogador); // Create GUI with the initialized campoMinado
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaInicial::new);
    }

}
