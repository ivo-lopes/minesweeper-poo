package minesweeper.interfacegrafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class CampoMinadoGUI {
    private JFrame frame;
    private JPanel topPanel;
    private JPanel gridPanel;
    private JButton[][] buttons;
    private char[][] campo;
    private char[][] minas;
    private boolean[][] revelado;
    private boolean[][] bandeiras;
    private int tamanhoDoCampo;
    private int numeroDeMinas;
    private int bandeirasRestantes;
    private int celulasNaoReveladas;
    private JLabel bandeirasLabel;
    private JButton reiniciarButton;
    private Timer timer;
    private int tempoPassado;
    private JLabel timerLabel;
    @SuppressWarnings("unused")
    private List<Recorde> recordes;
    private ListaRecordes listaRecordes = new ListaRecordes();


    public CampoMinadoGUI(int tamanhoDoCampo, int numeroDeMinas) {
        this.tamanhoDoCampo = tamanhoDoCampo;
        this.numeroDeMinas = numeroDeMinas;
        bandeirasRestantes = numeroDeMinas;
        celulasNaoReveladas = tamanhoDoCampo * tamanhoDoCampo;
        tempoPassado = 0;
        timer = new Timer();

        frame = new JFrame("Campo Minado");
        topPanel = new JPanel();
        gridPanel = new JPanel(new GridLayout(tamanhoDoCampo, tamanhoDoCampo));
        buttons = new JButton[tamanhoDoCampo][tamanhoDoCampo];
        campo = new char[tamanhoDoCampo][tamanhoDoCampo];
        minas = new char[tamanhoDoCampo][tamanhoDoCampo];
        revelado = new boolean[tamanhoDoCampo][tamanhoDoCampo];
        bandeiras = new boolean[tamanhoDoCampo][tamanhoDoCampo];
        recordes = new ArrayList<>();

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null); // Centraliza a janela

        bandeirasLabel = new JLabel("Bandeiras Restantes: " + bandeirasRestantes);
        bandeirasLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(bandeirasLabel);

        reiniciarButton = new JButton("Reiniciar");
        reiniciarButton.setFont(new Font("Arial", Font.BOLD, 16));
        reiniciarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reiniciarJogo();
            }
        });
        topPanel.add(reiniciarButton);

        timerLabel = new JLabel("Tempo: 0");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(timerLabel);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(gridPanel, BorderLayout.CENTER);

        inicializarCampo();
        colocarMinas();
        calcularDicas();

        for (int i = 0; i < tamanhoDoCampo; i++) {
            for (int j = 0; j < tamanhoDoCampo; j++) {
                buttons[i][j] = new JButton(" ");
                buttons[i][j].setMargin(new Insets(0, 0, 0, 0));
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 24));

                int finalI = i;
                int finalJ = j;
                buttons[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        revelarCampo(finalI, finalJ);
                    }
                });

                buttons[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            adicionarOuRemoverBandeira(finalI, finalJ);
                        }
                    }
                });

                gridPanel.add(buttons[i][j]);
            }
        }

        frame.setVisible(true);
        iniciarTemporizador();

        // Adicionando ação para fechar a janela
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                voltarMenuPrincipal();
            }
        });

        recordes = carregarRecordes();
    }

    private void voltarMenuPrincipal() {
        int confirm = JOptionPane.showOptionDialog(
                frame,
                "Tem certeza que deseja sair do jogo?",
                "Confirmar saída",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null);

        if (confirm == JOptionPane.YES_OPTION) {
            new MenuPrincipal().setVisible(true);
            frame.dispose();
        }
    }

    private void iniciarTemporizador() {
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                tempoPassado++;
                timerLabel.setText("Tempo: " + tempoPassado);
            }
        }, 1000, 1000);
    }

    private void pararTemporizador() {
        timer.cancel();
    }

    private void reiniciarJogo() {
        frame.dispose();
        SwingUtilities.invokeLater(() -> new CampoMinadoGUI(tamanhoDoCampo, numeroDeMinas));
    }

    private void inicializarCampo() {
        for (int i = 0; i < tamanhoDoCampo; i++) {
            for (int j = 0; j < tamanhoDoCampo; j++) {
                campo[i][j] = ' ';
                revelado[i][j] = false;
                bandeiras[i][j] = false;
            }
        }
    }

    private void colocarMinas() {
        Random random = new Random();

        for (int i = 0; i < numeroDeMinas; i++) {
            int linha, coluna;
            do {
                linha = random.nextInt(tamanhoDoCampo);
                coluna = random.nextInt(tamanhoDoCampo);
            } while (minas[linha][coluna] == '*');

            minas[linha][coluna] = '*';
        }
    }

    private void calcularDicas() {
        for (int i = 0; i < tamanhoDoCampo; i++) {
            for (int j = 0; j < tamanhoDoCampo; j++) {
                if (minas[i][j] == '*') {
                    continue;
                }

                int count = 0;
                for (int row = i - 1; row <= i + 1; row++) {
                    for (int col = j - 1; col <= j + 1; col++) {
                        if (row >= 0 && row < tamanhoDoCampo && col >= 0 && col < tamanhoDoCampo && minas[row][col] == '*') {
                            count++;
                        }
                    }
                }
                if (count > 0) {
                    campo[i][j] = (char) (count + '0');
                }
            }
        }
    }

    private void revelarCampo(int linha, int coluna) {
        if (revelado[linha][coluna] || bandeiras[linha][coluna] || !buttons[linha][coluna].isEnabled()) {
            return;
        }

        revelado[linha][coluna] = true;
        buttons[linha][coluna].setEnabled(false);
        celulasNaoReveladas--;

        if (minas[linha][coluna] == '*') {
            gameOver();
        } else {
            switch (campo[linha][coluna]) {
                case '1':
                    buttons[linha][coluna].setForeground(Color.BLUE);
                    break;
                case '2':
                    buttons[linha][coluna].setForeground(Color.GREEN);
                    break;
                case '3':
                    buttons[linha][coluna].setForeground(Color.RED);
                    break;
                case '4':
                    buttons[linha][coluna].setForeground(new Color(128, 0, 128));
                    break;
                case '5':
                    buttons[linha][coluna].setForeground(new Color(128, 0, 0));
                    break;
            }
            buttons[linha][coluna].setText(Character.toString(campo[linha][coluna]));

            if (campo[linha][coluna] == ' ') {
                for (int i = linha - 1; i <= linha + 1; i++) {
                    for (int j = coluna - 1; j <= coluna + 1; j++) {
                        if (i >= 0 && i < tamanhoDoCampo && j >= 0 && j < tamanhoDoCampo) {
                            revelarCampo(i, j);
                        }
                    }
                }
            }
        }

        if (celulasNaoReveladas == numeroDeMinas) {
            gameWon();
        }
    }

    private void adicionarOuRemoverBandeira(int linha, int coluna) {
        if (revelado[linha][coluna]) {
            return;
        }

        if (bandeiras[linha][coluna]) {
            bandeiras[linha][coluna] = false;
            buttons[linha][coluna].setText(" ");
            bandeirasRestantes++;
        } else {
            bandeiras[linha][coluna] = true;
            buttons[linha][coluna].setText("!!");
            bandeirasRestantes--;
        }
        bandeirasLabel.setText("Bandeiras Restantes: " + bandeirasRestantes);
    }

    private void gameOver() {
        pararTemporizador();
        for (int i = 0; i < tamanhoDoCampo; i++) {
            for (int j = 0; j < tamanhoDoCampo; j++) {
                if (minas[i][j] == '*') {
                    campo[i][j] = '#';
                    buttons[i][j].setText("#");
                }
                buttons[i][j].setEnabled(false);
            }
        }
        JOptionPane.showMessageDialog(frame, "Você perdeu! Acertou uma mina.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    private void gameWon() {
        pararTemporizador();
        JOptionPane.showMessageDialog(frame, "Parabéns! Você venceu o jogo.", "Vitória", JOptionPane.INFORMATION_MESSAGE);
        Recorde recorde = new Recorde("", tempoPassado);
        recorde.solicitarNome();
        listaRecordes.adicionarRecorde(recorde); // Alteração: Adiciona o novo recorde à lista de recordes
    }

    private List<Recorde> carregarRecordes() {
        // Lógica para carregar os recordes salvos de um arquivo ou outro meio de armazenamento
        // Retorna uma lista de recordes
        return new ArrayList<>(); // Temporário - substitua isso pela lógica real de carregamento
    }


    public void exibirRecordes() {
        List<Recorde> recordes = listaRecordes.getRecordes();

        StringBuilder sb = new StringBuilder();
        sb.append("Lista de Recordes:\n");
        for (Recorde recorde : recordes) {
            sb.append(recorde.getNome()).append(" - ").append(recorde.getTempo()).append(" segundos\n");
        }
    }
}
