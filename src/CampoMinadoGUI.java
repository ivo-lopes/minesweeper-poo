package minesweeper.tabuleiro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
	
	private static final Color COR_CELULA_FECHADA = new Color(200, 200, 200);
    //private static final Color COR_CELULA_ABERTA = new Color(160, 160, 160);
    private static final Color COR_MARGEM = new Color(140, 140, 140);

    ImageIcon bandeiraIcon = new ImageIcon(getClass().getResource("/imagens/red-flag.png"));
    Image imagemRedimensionada = bandeiraIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
    ImageIcon bandeiraIcon2 = new ImageIcon(imagemRedimensionada);
    ImageIcon bombaIcon = new ImageIcon(getClass().getResource("/imagens/icons8-mina-naval-48.png"));
   
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
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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
        	int numMinasMalucas = 0;
            switch (tamanhoTabuleiro) {
                case 8:
                    numMinasMalucas = 10;
                    break;
                case 16: 
                    numMinasMalucas = 20;
                    break;
                case 24: 
                    numMinasMalucas = 30;
                    break;
            }
        	CampoMinadoMaluco campoMinadoMaluco = new CampoMinadoMaluco(tamanhoTabuleiro, numMinas, numMinasMalucas);
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
                botao.setBorder(BorderFactory.createLineBorder(COR_MARGEM, 2)); 
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
                        		if (celulaClicada.isMarcada()) {
                                    botao.setIcon(bandeiraIcon);
                                } else {
                                    botao.setIcon(null);
                                }
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
            JOptionPane.showMessageDialog(null, "<html><center><font size='5' color='red'>Você perdeu! 😞</font></center></html>", "Fim de Jogo", JOptionPane.PLAIN_MESSAGE);
            salvarPontuacao(nomeJogador, gameTimer.getTempoDecorrido(), false);
            exibirRecorde();
        } else if (campoMinado.verificarVitoria()) {
            jogoEncerrado = true;
            gameTimer.pararTimer();
            JOptionPane.showMessageDialog(null, "<html><center><font size='5' color='green'>Parabéns, você venceu! 🎉</font></center></html>", "Fim de Jogo", JOptionPane.PLAIN_MESSAGE);
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
	        textArea.setFont(new Font("Arial", Font.BOLD, 14)); 

	        JScrollPane scrollPane = new JScrollPane(textArea);
	        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

	        textArea.setText(mensagem);
	        getContentPane().add(scrollPane, BorderLayout.CENTER);
	        
	        getContentPane().setBackground(new Color(240, 240, 240)); 
	        //textArea.setBackground(Color.LIGHT_GRAY); // Cor de fundo da área de texto
	        textArea.setForeground(new Color(159, 125, 74));

	        setVisible(true);
	    }
	}
	private void exibirRecorde() {
		List<Pontuacao> top10 = recorde.getTop10Pontuacoes();
		  
		  StringBuilder message = new StringBuilder("Top 10 Menores Tempos:\n");

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
                	botao.setBackground(null);
                    if (celula.isBomba()) {
                    	botao.setIcon(bombaIcon);
                    } else if (celula instanceof CelulaVazia) {
                        int valor = ((CelulaVazia) celula).getValor();
                        switch (valor) {
            			case 1:
            				botao.setForeground(Color.GREEN); break;
            			case 2:
            				botao.setForeground(Color.BLUE); break;
            			case 3:
            				botao.setForeground(Color.YELLOW); break;
            			case 4: case 5: case 6:
            				botao.setForeground(Color.RED); break;
            			default:
            				botao.setForeground(Color.PINK);
            		}
                        if (valor > 0) {
                            botao.setText(String.valueOf(valor));
                            botao.setFont(botao.getFont().deriveFont(20f));
                        } else {
                            botao.setText(""); 
                        }
                    }
                } else if (celula.isMarcada()) {
                	botao.setIcon(bandeiraIcon2);
                } else {
                    botao.setText("");
                    botao.setBackground(COR_CELULA_FECHADA);
                    
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
		boolean vitoria = campoMinado.verificarVitoria();
	    if (vitoria && campoMinadoMalucoAtivo) {
	        revelarTabuleiro();
	    }
	    return vitoria;
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

	    SwingUtilities.invokeLater(() -> {
	        atualizarTabuleiro();
	        verificarFimDeJogo();
	    });
		
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
