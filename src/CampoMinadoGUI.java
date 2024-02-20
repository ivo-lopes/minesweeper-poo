package minesweeper.tabuleiro;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;
//import java.util.PriorityQueue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import minesweeper.celulas.Celula;
import minesweeper.celulas.CelulaVazia;
import minesweeper.niveis.*;
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
	private JComboBox<String> comboBoxDificuldade;
	private String nomeJogador;
	private Recorde recorde;


    public CampoMinadoGUI(int tamanhoTabuleiro, int numMinas, String nomeJogador) {
    	this.nomeJogador = nomeJogador;
        this.tamanhoTabuleiro = tamanhoTabuleiro;
        this.campoMinado = new CampoMinado(tamanhoTabuleiro, numMinas);
        this.botoes = new JButton[tamanhoTabuleiro][tamanhoTabuleiro];
        this.recorde = new Recorde();
        
        String[] niveis = {"Fácil", "Intermediário", "Difícil"};
        comboBoxDificuldade = new JComboBox<>(niveis);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(comboBoxDificuldade, BorderLayout.WEST);

        comboBoxDificuldade.addActionListener(e ->  configurarJogo());
      
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
    
    void configurarJogo() {
        String selectedNivel = (String) comboBoxDificuldade.getSelectedItem();
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
    }
    
    public void iniciarTimer() {
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
       System.out.println("Valor inválido");
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
            salvarPontuacao(nomeJogador, gameTimer.getTempoDecorrido(), false);
            exibirRecorde();
        } else if (campoMinado.verificarVitoria()) {
            jogoEncerrado = true;
            gameTimer.pararTimer();
            JOptionPane.showMessageDialog(null, "Você venceu!");
            salvarPontuacao(nomeJogador, gameTimer.getTempoDecorrido(), true);
            exibirRecorde();
        }
    }
	
	private void salvarPontuacao(String nomeJogador, long tempoDecorrido, boolean venceu) {
	    recorde.adicionarPontuacao(nomeJogador, tempoDecorrido, venceu);
    }
	
	public class RecordeFrame extends JFrame {

	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public RecordeFrame(String mensagem) {
	        setTitle("Recorde");
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setSize(400, 300); 
	        setLocationRelativeTo(null); 

	        JTextArea textArea = new JTextArea();
	        textArea.setEditable(false); 

	        JScrollPane scrollPane = new JScrollPane(textArea);
	        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	        

	        textArea.setText(mensagem);
	        getContentPane().add(scrollPane, BorderLayout.CENTER);

	        setVisible(true);
	        
	    }
	}
	private void exibirRecorde() {
		List<Pontuacao> top10 = recorde.getTop10Pontuacoes();
		  
		  StringBuilder message = new StringBuilder("Top 10 Pontuações:\n");

		  for(int i = 0; i < top10.size(); i++) {
		    Pontuacao pontuacao = top10.get(i);
		    message.append(i+1).append(". ").append(pontuacao.getNomeJogador())
		      .append(": ").append(pontuacao.getTempo()).append(" segundos\n");
		  }

		  new RecordeFrame(message.toString());
		  
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
                        botao.setText("X"); 
                    } else if (celula instanceof CelulaVazia) {
                        int valor = ((CelulaVazia) celula).getValor();
                        if (valor > 0) {
                            botao.setText(String.valueOf(valor));
                        } else {
                            botao.setText(""); 
                        }
                    }
                } else if (celula.isMarcada()) {
                	botao.setText("M");
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
            new CampoMinadoGUI(tamanhoTabuleiro, numMinas, "");
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
		 campoMinado.marcarCelula(linha, coluna);
		 atualizarTabuleiro();
		
	}

	@Override
	public boolean verificarVitoria() {
		return campoMinado.verificarVitoria();
	}

	@Override
	public void revelarTabuleiro() {
		campoMinado.revelarTabuleiro();
	    atualizarTabuleiro();
		
	}

	@Override
	public boolean verificarDerrota() {
		return campoMinado.verificarDerrota();
	}

	@Override
	public void abrirCelula(int linha, int coluna) {
		campoMinado.abrirCelula(linha, coluna);
	    atualizarTabuleiro();
		
	}

	@Override
	public void notificarMudancaVizinhos(int linha, int coluna) {
		campoMinado.notificarMudancaVizinhos(linha, coluna);
	    atualizarTabuleiro();
		
	}

	@Override
	public void distribuirMinas(int linhaClicada, int colunaClicada) {
		campoMinado.distribuirMinas(linhaClicada, colunaClicada);
		
	}

}

