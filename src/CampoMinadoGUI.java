package minesweeper.tabuleiro;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import minesweeper.celulas.Celula;
import minesweeper.celulas.CelulaVazia;

public class CampoMinadoGUI extends JFrame implements Tabuleiro{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CampoMinado campoMinado;
    private JButton[][] botoes;
    private int tamanhoTabuleiro;
    private JLabel timer;
    private GameTimer gameTimer;
    private boolean campoMinadoMalucoAtivo;
	private int numMinas;
	private boolean jogoEncerrado = false;


    public CampoMinadoGUI(int tamanhoTabuleiro, int numMinas) {
        this.tamanhoTabuleiro = tamanhoTabuleiro;
        this.campoMinado = new CampoMinado(tamanhoTabuleiro, numMinas);
        this.botoes = new JButton[tamanhoTabuleiro][tamanhoTabuleiro];
        
        JPanel panel = new JPanel(new BorderLayout());
        timer = new JLabel("Tempo: ");
        panel.add(timer, BorderLayout.NORTH);
        

        JButton alternarModo = new JButton("Alternar Modo");
        panel.add(alternarModo, BorderLayout.SOUTH);

        alternarModo.addActionListener(e -> {
            campoMinadoMalucoAtivo = !campoMinadoMalucoAtivo;
            reiniciarJogo();
            atualizarTitulo(); 
        }); 
        
        
        setTitle("Campo Minado");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        JPanel gamePanel = new JPanel(new GridLayout(tamanhoTabuleiro, tamanhoTabuleiro));
        panel.add(gamePanel, BorderLayout.CENTER);
        criarBotoes(gamePanel);
        
        atualizarTabuleiro();
        getContentPane().add(panel);
        setVisible(true);
        
        iniciarTimer();

    }
    
    private void iniciarTimer() {
        gameTimer = new GameTimer(timer);
        gameTimer.execute();
    }
    
    public void atualizarTitulo() {
    	setTitle(campoMinadoMalucoAtivo ? "Campo Minado Maluco" : "Campo Minado");
    }
    
    private void instanciaJogo() {
    try {
        if (campoMinadoMalucoAtivo) {
        	CampoMinadoMaluco campoMinadoMaluco = new CampoMinadoMaluco(tamanhoTabuleiro, numMinas);
            campoMinado = campoMinadoMaluco;
            campoMinadoMaluco.iniciarJogoMaluco();
           
        } else {
            campoMinado = new CampoMinado(tamanhoTabuleiro, numMinas);
            campoMinado.iniciarJogo();
        }
        
        atualizarTabuleiro();
    } catch (ValorInvalidoException e) {
        // Handle ValorInvalidoException (e.g., log it, show a message to the user)
    }
  }
    
	public void reiniciarJogo() {
		getContentPane().removeAll();
	    
	    JPanel panel = new JPanel(new BorderLayout());
	    timer = new JLabel("Tempo: ");
	    panel.add(timer, BorderLayout.NORTH);

	    JButton alternarModo = new JButton("Alternar Modo");
	    panel.add(alternarModo, BorderLayout.SOUTH);

	    alternarModo.addActionListener(e -> {
	        campoMinadoMalucoAtivo = !campoMinadoMalucoAtivo;
	        reiniciarJogo();
	        atualizarTitulo();
	        iniciarTimer();
	    });

	    setTitle("Campo Minado");
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    JPanel gamePanel = new JPanel(new GridLayout(tamanhoTabuleiro, tamanhoTabuleiro));
	    panel.add(gamePanel, BorderLayout.CENTER);

	    getContentPane().add(panel);
	    
		for (int linha = 0; linha < tamanhoTabuleiro; linha++) {
            for (int coluna = 0; coluna < tamanhoTabuleiro; coluna++) {
                botoes[linha][coluna].setEnabled(true);
            }
        }
        instanciaJogo();
        criarBotoes(gamePanel);
        atualizarTitulo();
        iniciarTimer();
        atualizarTabuleiro();
        
        revalidate();
        repaint();
  }


	public void criarBotoes(JPanel gamePanel) {
		gamePanel.removeAll();
		
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
                        		SwingUtilities.invokeLater(() -> atualizarTabuleiro());
                        	}
                        }else if (SwingUtilities.isLeftMouseButton(e) && !celulaClicada.isAberta()) {
                        	campoMinado.abrirCelula(finalLinha, finalColuna);
                            SwingUtilities.invokeLater(() -> {
                                atualizarTabuleiro();
                                verificarFimDeJogo();
                        });
                    }
                 }
              });
              gamePanel.add(botao);
            }
        }
        gamePanel.revalidate();
        gamePanel.repaint();
        
	}
	
	private void verificarFimDeJogo() {
        if (campoMinado.verificarDerrota()) {
            campoMinado.revelarTabuleiro();
            jogoEncerrado = true;
            gameTimer.pararTimer();
            JOptionPane.showMessageDialog(null, "Você perdeu!");
        } else if (campoMinado.verificarVitoria()) {
            jogoEncerrado = true;
            gameTimer.pararTimer();
            JOptionPane.showMessageDialog(null, "Você venceu!");
        }
    }
	
    private void atualizarTabuleiro() {
        for (int linha = 0; linha < tamanhoTabuleiro; linha++) {
            for (int coluna = 0; coluna < tamanhoTabuleiro; coluna++) {
                Celula celula = campoMinado.getTabuleiro()[linha][coluna];
                JButton botao = botoes[linha][coluna];

                if (jogoEncerrado) {
                    verificarFimDeJogo();
                } else if (celula.isAberta()) {
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
                	botao.setText("M");// Marcação de possível mina
                } else {
                    botao.setText("");
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

	@Override
	public Celula[][] getTabuleiro() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fecharCelulas() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void marcarCelula(int linha, int coluna) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean verificarVitoria() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void revelarTabuleiro() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean verificarDerrota() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void abrirCelula(int linha, int coluna) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notificarMudancaVizinhos(int linha, int coluna) {
		// TODO Auto-generated method stub
		
	}

}

