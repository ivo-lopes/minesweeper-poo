package minesweeper.tabuleiro;
import java.util.Random;

import minesweeper.celulas.Celula;
import minesweeper.celulas.CelulaBomba;
import minesweeper.celulas.CelulaVazia;

public class CampoMinado {
    private int tamanhoTabuleiro;
    private int numMinas;
    private Celula[][] tabuleiro;

    public CampoMinado(int tamanhoTabuleiro, int numMinas) {
        this.tamanhoTabuleiro = tamanhoTabuleiro;
        this.numMinas = numMinas;
        this.tabuleiro = new Celula[tamanhoTabuleiro][tamanhoTabuleiro];
        inicializarJogo();
    }

    public Celula[][] getTabuleiro() {
        return tabuleiro;
    }

    private void inicializarJogo() {
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
                    int valor = calcularValorCelula(linha, coluna);
                    ((CelulaVazia) tabuleiro[linha][coluna]).setValor(valor);
                }
            }
        }
    }
    
    private int calcularValorCelula(int linha, int coluna) {
        if(!(tabuleiro[linha][coluna].isBomba())){
        	int valor = 0;
        	
        	for(int i = -1; i <= 1; i++) {
        		for(int j = -1; j <= 1; j++) {
        			int novaLinha = linha + i;
        			int novaColuna = coluna + j;
        			
        			if(novaLinha >= 0 && novaLinha < tamanhoTabuleiro && novaColuna >=0 && novaColuna < tamanhoTabuleiro) {
        				if(tabuleiro[novaLinha][novaColuna].isBomba()) {
        					valor++;
        				}
        			}
        		}
        	}
        	return valor;
        }
        return 0;
    }

    public void marcarCelula(int linha, int coluna) {
        Celula celula = tabuleiro[linha][coluna];
        if (celula.isAberta()) {
            return;
        }
        if(celula.isMarcada()) {
        	celula.mudarMarcacao();
        }
        else {
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
    	for(int linha = 0; linha < tamanhoTabuleiro; linha++) {
    		for(int coluna= 0; coluna < tamanhoTabuleiro; coluna++) {
    			tabuleiro[linha][coluna].setAberta(true);
    		}
    	}
    }
	
    public boolean verificarDerrota() {
    	for(int linha = 0; linha < tamanhoTabuleiro; linha++) {
    		for(int coluna = 0; coluna < tamanhoTabuleiro; coluna++) {
    			Celula celula = tabuleiro [linha][coluna];
    			if(celula.isAberta() && celula.isBomba) {
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
    			if(celula.isBomba) {
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
            verificarDerrota();
        } else {
            if (((CelulaVazia) celula).getValor() == 0) {
                abrirCelulasVizinhas(linha, coluna);
            }
        }
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
}
