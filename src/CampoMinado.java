package minesweeper.tabuleiro;

import java.util.Random;

import minesweeper.celulas.Celula;
import minesweeper.celulas.CelulaBomba;
import minesweeper.celulas.CelulaVazia;

public class CampoMinado implements Tabuleiro {
	protected int tamanhoTabuleiro;
    protected int numMinas;
    protected Celula[][] tabuleiro;
	private int startPlayer1;
	private int endPlayer1;
	private int startPlayer2;
	private int endPlayer2;
	
	public int getStartPlayer1() {
		return startPlayer1;
	}

	public void setStartPlayer1(int startPlayer1) {
		if (startPlayer1 < 0 || startPlayer1 >= tamanhoTabuleiro) {
	        throw new ValorInvalidoException("Invalid start position for Player 1.");
	    }
		this.startPlayer1 = startPlayer1;
	}

	public int getEndPlayer1() {
		return endPlayer1;
	}

	public void setEndPlayer1(int endPlayer1) {
		if (endPlayer1 < 0 || endPlayer1 >= tamanhoTabuleiro) {
	        throw new ValorInvalidoException("Posição inválida para Player 1.");
	    }
		this.endPlayer1 = endPlayer1;
	}

	public int getStartPlayer2() {
		return startPlayer2;
	}

	public void setStartPlayer2(int startPlayer2) {
		if (startPlayer2 < 0 || startPlayer2 >= tamanhoTabuleiro) {
	        throw new ValorInvalidoException("Posição inválida para Player 2.");
	    }
		this.startPlayer2 = startPlayer2;
	}

	public int getEndPlayer2() {
		return endPlayer2;
	}

	public void setEndPlayer2(int endPlayer2) {
		if (endPlayer2 < 0 || endPlayer2 >= tamanhoTabuleiro) {
	        throw new ValorInvalidoException("Posição inválida para Player 2.");
	    }
		this.endPlayer2 = endPlayer2;
	}

    public CampoMinado(int tamanhoTabuleiro, int numMinas) {
        this.tamanhoTabuleiro = tamanhoTabuleiro;
        this.numMinas = numMinas;
        this.tabuleiro = new Celula[tamanhoTabuleiro][tamanhoTabuleiro];
        iniciarJogo();
    }
    
    public int getNumMinas() {
        return numMinas;
    }


    public Celula[][] getTabuleiro() {
        return tabuleiro;
    }
    
    public void reiniciarJogo() {
        iniciarJogo();
        fecharCelulas();
    }
    
    public void fecharCelulas() {
        for (int linha = 0; linha < tamanhoTabuleiro; linha++) {
            for (int coluna = 0; coluna < tamanhoTabuleiro; coluna++) {
                tabuleiro[linha][coluna].setAberta(false);
                tabuleiro[linha][coluna].setMarcada(false);
            }
        }
   }

    
    public void iniciarJogo() {
        // Inicializa o tabuleiro com células vazias e sem minas
        for (int linha = 0; linha < tamanhoTabuleiro; linha++) {
            for (int coluna = 0; coluna < tamanhoTabuleiro; coluna++) {
                tabuleiro[linha][coluna] = new CelulaVazia();
            }
        }
        // Distribui as minas aleatoriamente
        Random random = new Random();
        int minasRestantes = numMinas;
        while (minasRestantes > 0) {
            int linha = random.nextInt(tamanhoTabuleiro);
            int coluna = random.nextInt(tamanhoTabuleiro);
            if (!(tabuleiro[linha][coluna].isBomba())) {
                    tabuleiro[linha][coluna] = new CelulaBomba();
                    minasRestantes--;
            }
        }

        // Calcula os valores das células (número de minas vizinhas)
        for (int linha = 0; linha < tamanhoTabuleiro; linha++) {
            for (int coluna = 0; coluna < tamanhoTabuleiro; coluna++) {
                if (!(tabuleiro[linha][coluna].isBomba())) {
                    int valor = contarMinasVizinhas(linha, coluna);
                    ((CelulaVazia) tabuleiro[linha][coluna]).setValor(valor);
                }
            }
        }
    }
    
    private int contarMinasVizinhas(int linha, int coluna) {
    	int minasVizinhas = 0;
        if(!(tabuleiro[linha][coluna].isBomba())){
        	
        	for(int i = -1; i <= 1; i++) {
        		for(int j = -1; j <= 1; j++) {
        			int novaLinha = linha + i;
        			int novaColuna = coluna + j;
        			
        			if (novaLinha >= 0 && novaLinha < tamanhoTabuleiro &&
                            novaColuna >= 0 && novaColuna < tamanhoTabuleiro) {
                            if (tabuleiro[novaLinha][novaColuna].isBomba()) {
                                minasVizinhas++;
                            }
        			}
        		}
        	}
        	return minasVizinhas;
        }
        return 0;
    }

    public void marcarCelula(int linha, int coluna) {
    	Celula celula = tabuleiro[linha][coluna];
        if (!celula.isAberta()) {
            celula.mudarMarcacao();
        }
    }

    public boolean verificarVitoria() {
    	for(int linha = 0; linha < tamanhoTabuleiro; linha++) {
    		for(int coluna = 0; coluna < tamanhoTabuleiro; coluna++) {
    			Celula celula = tabuleiro [linha][coluna];
    			if(!(celula.isAberta()) && !(celula.isBomba())){
    				return false;
    			}
    		}
    	}
    	revelarTabuleiro();
		return true;
    }
    
    public void revelarTabuleiro() {
    	for (int linha = 0; linha < tamanhoTabuleiro; linha++) {
            for (int coluna = 0; coluna < tamanhoTabuleiro; coluna++) {
                Celula celula = tabuleiro[linha][coluna];
                
                if (!celula.isAberta()) {
                    celula.setAberta(true);
                }
                
            }
        }
    }

    public boolean verificarDerrota() {
    	for(int linha = 0; linha < tamanhoTabuleiro; linha++) {
    		for(int coluna = 0; coluna < tamanhoTabuleiro; coluna++) {
    			Celula celula = tabuleiro [linha][coluna];
    			if(celula.isAberta() && celula.isBomba()) {
    				revelarBombas();
    				return true;
    			}
    		}
    	}
		return false;
    }
    
    public void revelarBombas() {
    	for(int linha = 0; linha < tamanhoTabuleiro; linha++) {
    		for(int coluna= 0; coluna < tamanhoTabuleiro; coluna++) {
    			Celula celula = tabuleiro[linha][coluna];
    			if(celula.isBomba()&& !celula.isAberta()) {
    			   celula.setAberta(true);
    			}
    		}
    	}
    }

    public void abrirCelula(int linha, int coluna) {
        Celula celula = tabuleiro[linha][coluna];

        if (celula.isAberta() || celula.isMarcada()) {
            return;
        }

        celula.setAberta(true);

        if (celula.isBomba()) {
            verificarDerrota();  // Certifique-se de que esse método seja chamado no momento certo
        } else {

             if (celula instanceof CelulaVazia &&((CelulaVazia) celula).getValor() == 0) {
                 abrirCelulasVizinhas(linha, coluna);
             }
        }
        notificarMudancaVizinhos(linha, coluna);
    }

    private void abrirCelulasVizinhas(int linha, int coluna) {
        for(int i = -1; i <= 1 ; i++) {
        	for(int j = -1; j <= 1; j++) {
        		int novaLinha = linha + i;
        		int novaColuna = coluna + j;
        		
        		if(novaLinha >= 0 && novaLinha < tamanhoTabuleiro && novaColuna >= 0 && novaColuna < tamanhoTabuleiro) {
        			Celula celula = tabuleiro[novaLinha][novaColuna];
        			
        			if(!celula.isAberta() && !(celula.isBomba())) {
        				celula.setAberta(true);
        			if(((CelulaVazia) celula).getValor() == 0) {
                        abrirCelulasVizinhas(novaLinha, novaColuna);
                    }
        		}
        	}
        }
      }
   }
    
    public void notificarMudancaVizinhos(int linha, int coluna) {
        // Lógica para notificar mudança nas células vizinhas
    	for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int novaLinha = linha + i;
                int novaColuna = coluna + j;

                if (novaLinha >= 0 && novaLinha < tamanhoTabuleiro &&
                    novaColuna >= 0 && novaColuna < tamanhoTabuleiro) {
                    Celula vizinha = tabuleiro[novaLinha][novaColuna];

                    // A lógica a seguir verifica se a vizinha é uma instância de CelulaVazia antes de realizar o cast
                    if (vizinha instanceof CelulaVazia) {
                        int novoValor = contarMinasVizinhas(novaLinha, novaColuna);
                        ((CelulaVazia) vizinha).atualizarValor(novoValor);
                    }
                }
            }
        }
    }
}
