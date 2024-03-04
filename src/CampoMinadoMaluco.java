package minesweeper.tabuleiro;
import java.util.Random;

import minesweeper.celulas.Celula;
import minesweeper.celulas.CelulaMaluca;
import minesweeper.celulas.CelulaVazia;
import minesweeper.celulas.CelulaBomba;

public class CampoMinadoMaluco extends CampoMinado {
	

	public CampoMinadoMaluco(int tamanhoTabuleiro, int numMinas, int numMinasMalucas) {
		super(tamanhoTabuleiro, numMinas);
		iniciarJogoMaluco();
	}
	
	public void iniciarJogoMaluco() {
		    for (int linha = 0; linha < tamanhoTabuleiro; linha++) {
		        for (int coluna = 0; coluna < tamanhoTabuleiro; coluna++) {
		            tabuleiro[linha][coluna] = new CelulaVazia();
		        }
		    }

		    Random random = new Random();
		    int minasRestantes = numMinas;
		    int celulasMalucas = 5;
		    int celulasMalucasRestantes = celulasMalucas; 

		    while (minasRestantes > 0 || celulasMalucasRestantes > 0) {
		        int linha = random.nextInt(tamanhoTabuleiro);
		        int coluna = random.nextInt(tamanhoTabuleiro);

		        if (!(tabuleiro[linha][coluna].isBomba())) {
		        	if (celulasMalucasRestantes > 0 && random.nextBoolean()) {
			            tabuleiro[linha][coluna] = new CelulaMaluca();
			            celulasMalucasRestantes--;
			        } else if (minasRestantes > 0) {
			            tabuleiro[linha][coluna] = new CelulaBomba();
			            minasRestantes--;
			        }
		        }
		    }

		    for (int linha = 0; linha < tamanhoTabuleiro; linha++) {
		        for (int coluna = 0; coluna < tamanhoTabuleiro; coluna++) {
		            if (tabuleiro[linha][coluna] instanceof CelulaVazia) {
		                int valor = calcularValorVizinhos(linha, coluna);
		                ((CelulaVazia) tabuleiro[linha][coluna]).setValor(valor);
		            }
		        }
		    }
		}

		public int calcularValorVizinhos(int linha, int coluna) {
		    int valor = 0;
		    for (int i = -1; i <= 1; i++) {
		        for (int j = -1; j <= 1; j++) {
		            int novaLinha = linha + i;
		            int novaColuna = coluna + j;

		            if (novaLinha >= 0 && novaLinha < tamanhoTabuleiro && novaColuna >= 0 && novaColuna < tamanhoTabuleiro) {
		                if (tabuleiro[novaLinha][novaColuna].isBomba()) {
		                    valor++;
		                }
		            }
		        }
		    }
		    return valor;
		}

    @Override
    public void marcarCelula(int linha, int coluna) {
        Celula celula = tabuleiro[linha][coluna];

        if (celula.isAberta()) {
        	if(celula instanceof CelulaMaluca) {
        		CelulaMaluca celulaMaluca = (CelulaMaluca) celula;
        		if(!(celulaMaluca.isBomba())) {
        			celulaMaluca.setBomba(true);
        			verificarDerrota();
        		}
        	}
            return;
        }

        if (celula instanceof CelulaMaluca) {
            CelulaMaluca celulaMaluca = (CelulaMaluca) celula;
            
            if (celulaMaluca.isBomba()) {
                celulaMaluca.setBomba(false);
                celulaMaluca.setAberta(true);
                atualizarVizinhos(linha, coluna);
            } else {
                celulaMaluca.alterarEstado();
                alterarVizinhos(linha, coluna);
            }
        } else {
            super.marcarCelula(linha, coluna);
        }
    }

    private void alterarVizinhos(int linha, int coluna) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int novaLinha = linha + i;
                int novaColuna = coluna + j;

                if (novaLinha >= 0 && novaLinha < tamanhoTabuleiro && novaColuna >= 0 && novaColuna < tamanhoTabuleiro) {
                    if (tabuleiro[novaLinha][novaColuna] instanceof CelulaMaluca) {
                        CelulaMaluca celulaMaluca = (CelulaMaluca) tabuleiro[novaLinha][novaColuna];
                        celulaMaluca.alterarEstado();
                    }
                }
            }
        }
    }
    
    private void atualizarVizinhos(int linha, int coluna) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int novaLinha = linha + i;
                int novaColuna = coluna + j;

                if (novaLinha >= 0 && novaLinha < tamanhoTabuleiro && novaColuna >= 0 && novaColuna < tamanhoTabuleiro) {
                    Celula vizinho = tabuleiro[novaLinha][novaColuna];

                    if (vizinho instanceof CelulaVazia) {
                        CelulaVazia celulaVazia = (CelulaVazia) vizinho;

                        int novoValor = calcularValorVizinhos(novaLinha, novaColuna);

                        celulaVazia.atualizarValor(novoValor);
                    }
                }
            }
        }
    }

    @Override
    public void reiniciarJogo() {
        super.reiniciarJogo();
        iniciarJogoMaluco();
    }
}

