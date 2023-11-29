package minesweeper.tabuleiro;

import javax.swing.*;
import minesweeper.celulas.Celula;
import minesweeper.celulas.CelulaVazia;

import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class CampoMinadoGUI extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

                botao.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                    	Celula celulaClicada = campoMinado.getTabuleiro()[finalLinha][finalColuna];
                        if (SwingUtilities.isRightMouseButton(e)) { 
                        	if(!celulaClicada.isAberta()) {
                        		celulaClicada.setMarcada(!celulaClicada.isMarcada());
                                atualizarTabuleiro();
                        	}
                        } else if (SwingUtilities.isLeftMouseButton(e) && !celulaClicada.isAberta()) {
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
                    if (celula.isBomba()) {
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
