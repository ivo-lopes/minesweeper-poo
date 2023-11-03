import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CampoMinadoGUI extends JFrame {
    private CampoMinado campoMinado;
    private JButton[][] botoes;
    private int tamanhoTabuleiro;

    public CampoMinadoGUI(int tamanhoTabuleiro, int numMinas) {
        this.tamanhoTabuleiro = tamanhoTabuleiro;
        this.campoMinado = new CampoMinado(tamanhoTabuleiro, numMinas);
        this.botoes = new JButton[tamanhoTabuleiro][tamanhoTabuleiro];

        setTitle("Campo Minado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new GridLayout(tamanhoTabuleiro, tamanhoTabuleiro));

        criarBotoes();
        atualizarTabuleiro();

        setVisible(true);
    }

    private void criarBotoes() {
        for (int linha = 0; linha < tamanhoTabuleiro; linha++) {
            for (int coluna = 0; coluna < tamanhoTabuleiro; coluna++) {
                JButton botao = new JButton();
                botoes[linha][coluna] = botao;
                final int finalLinha = linha;
                final int finalColuna = coluna;

                botao.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!campoMinado.getTabuleiro()[finalLinha][finalColuna].isAberta()) {
                            campoMinado.abrirCelula(finalLinha, finalColuna);
                            atualizarTabuleiro();
                            if (campoMinado.verificarVitoria()) {
                                JOptionPane.showMessageDialog(null, "Você venceu!");
                            } else if (campoMinado.verificarDerrota()) {
                                JOptionPane.showMessageDialog(null, "Você perdeu!");
                            }
                        }
                    }
                });

                add(botao);
            }
        }
    }

    private void atualizarTabuleiro() {
        for (int linha = 0; linha < tamanhoTabuleiro; linha++) {
            for (int coluna = 0; coluna < tamanhoTabuleiro; coluna++) {
                Celula celula = campoMinado.getTabuleiro()[linha][coluna];
                JButton botao = botoes[linha][coluna];

                if (celula.isAberta()) {
                    botao.setEnabled(false);
                    if (celula instanceof CelulaBomba) {
                        botao.setText("X"); // Mostra uma mina
                    } else if (celula instanceof CelulaVazia) {
                        int valor = ((CelulaVazia) celula).getValor();
                        if (valor > 0) {
                            botao.setText(String.valueOf(valor)); // Mostra o valor das células vizinhas
                        } else {
                            botao.setText(""); // Célula vazia
                        }
                    }
                } else if (celula.isMarcada()) {
                    botao.setText("M"); // Marcação de possível mina
                } else {
                    botao.setText(""); // Célula fechada
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            int tamanhoTabuleiro = 8;
            int numMinas = 10;
            new CampoMinadoGUI(tamanhoTabuleiro, numMinas);
        });
    }
}
