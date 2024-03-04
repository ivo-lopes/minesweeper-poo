package minesweeper.tabuleiro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaInicial extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton botaoIniciar;
    private JLabel labelTitulo;
    private JLabel labelInstrucoes;

    public TelaInicial() {
        setTitle("Campo Minado");
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(230, 230, 230)); 

        labelTitulo = new JLabel("Bem-vindo ao Campo Minado");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        labelTitulo.setHorizontalAlignment(JLabel.CENTER);
        panel.add(labelTitulo, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(4, 1));
        inputPanel.setBackground(new Color(230, 230, 230)); 
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JLabel labelDificuldade = new JLabel("Selecione o nível de dificuldade:");
        inputPanel.add(labelDificuldade);
        String[] niveis = {"Fácil", "Intermediário", "Difícil"};
        JComboBox<String> comboBoxNiveis = new JComboBox<>(niveis);
        gbc.gridy++;
        inputPanel.add(comboBoxNiveis, gbc);

        JLabel labelNome = new JLabel("Digite seu nome:");
        gbc.gridy++;
        inputPanel.add(labelNome, gbc);

        JTextField textFieldNome = new JTextField();
        gbc.gridy++;
        inputPanel.add(textFieldNome, gbc);

        labelInstrucoes = new JLabel("<html>Clique em 'Iniciar Jogo' para começar<br>Use o botão esquerdo do mouse para abrir células<br>Use o botão direito do mouse para marcar ou desmarcar células</html>");
        labelInstrucoes.setFont(new Font("Arial", Font.PLAIN, 13));
        gbc.gridy=4;
        gbc.gridwidth = 4; 
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(labelInstrucoes, gbc);

        panel.add(inputPanel, BorderLayout.CENTER);

        botaoIniciar = new JButton("Iniciar Jogo");
        botaoIniciar.setFont(new Font("Arial", Font.BOLD, 16));
        botaoIniciar.setBackground(Color.GREEN.darker());
        botaoIniciar.setForeground(Color.WHITE);
        botaoIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedNivel = (String) comboBoxNiveis.getSelectedItem();
                int tamanhoTabuleiro = 0;
                int numMinas = 0;
                String nomeJogador = textFieldNome.getText();
                switch (selectedNivel) {
                    case "Fácil":
                        tamanhoTabuleiro = 8;
                        numMinas = 10;
                        break;
                    case "Intermediário":
                        tamanhoTabuleiro = 12;
                        numMinas = 20;
                        break;
                    case "Difícil":
                        tamanhoTabuleiro = 16;
                        numMinas = 30;
                        break;
                }
                dispose(); 
                iniciarJogo(tamanhoTabuleiro, numMinas, nomeJogador);
            }
        });
        panel.add(botaoIniciar, BorderLayout.SOUTH);

        getContentPane().add(panel);
        setVisible(true);
    }

    private void iniciarJogo(int tamanhoTabuleiro, int numMinas, String nomeJogador) {
        SwingUtilities.invokeLater(() -> {
            new CampoMinadoGUI(tamanhoTabuleiro, numMinas, nomeJogador);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaInicial());
    }
}





}
