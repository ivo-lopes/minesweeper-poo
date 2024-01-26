package minesweeper.tabuleiro;
import java.util.Random;

import minesweeper.celulas.Celula;
import minesweeper.celulas.CelulaMaluca;
import minesweeper.celulas.CelulaVazia;
import minesweeper.celulas.CelulaBomba;

public class CampoMinadoMaluco extends CampoMinado {
	

	public CampoMinadoMaluco(int tamanhoTabuleiro, int numMinas) {
		super(tamanhoTabuleiro, numMinas);
	}
	
	public void iniciarJogoMaluco() {
		    // Inicializa o tabuleiro com células vazias e sem bombas
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

		        if (!(tabuleiro[linha][coluna].isBomba()) && celulasMalucasRestantes > 0) {
		            tabuleiro[linha][coluna] = new CelulaMaluca();
		            celulasMalucasRestantes--;
		        } else if (!(tabuleiro[linha][coluna].isBomba())) {
		            tabuleiro[linha][coluna] = new CelulaBomba();
		            minasRestantes--;
		        }
		    }

		    // Agora, calcula os valores das células vizinhas
		    for (int linha = 0; linha < tamanhoTabuleiro; linha++) {
		        for (int coluna = 0; coluna < tamanhoTabuleiro; coluna++) {
		            if (tabuleiro[linha][coluna] instanceof CelulaVazia) {
		                int valor = calcularValorVizinhos(linha, coluna);
		                ((CelulaVazia) tabuleiro[linha][coluna]).setValor(valor);
		            }
		        }
		    }
		}

		// Método auxiliar para calcular o valor das células vizinhas
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
            return;
        }

        if (celula instanceof CelulaMaluca) {
            CelulaMaluca celulaMaluca = (CelulaMaluca) celula;
            
            if (celulaMaluca.isBomba()) {
                // Se a célula maluca for uma bomba e o usuário a marcar, ela se torna uma célula vazia
                celulaMaluca.setBomba(false);
                celulaMaluca.setAberta(true);
                atualizarVizinhos(linha, coluna);
            } else {
                // Se a célula maluca não for uma bomba, comportamento padrão de marcação
                celulaMaluca.alterarEstado();
                alterarVizinhos(linha, coluna);
            }
        } else {
            // Se não for uma célula maluca, comportamento padrão de marcação
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
    
    // Método para notificar os vizinhos sobre a alteração na célula maluca
    private void atualizarVizinhos(int linha, int coluna) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int novaLinha = linha + i;
                int novaColuna = coluna + j;

                if (novaLinha >= 0 && novaLinha < tamanhoTabuleiro && novaColuna >= 0 && novaColuna < tamanhoTabuleiro) {
                    Celula vizinho = tabuleiro[novaLinha][novaColuna];

                    if (vizinho instanceof CelulaVazia) {
                        CelulaVazia celulaVazia = (CelulaVazia) vizinho;

                        // Adicione a lógica para calcular o novo valor aqui
                        int novoValor = calcularValorVizinhos(novaLinha, novaColuna);

                        // Atualize o valor na célula vazia vizinha
                        celulaVazia.atualizarValor(novoValor);
                    }
                }
            }
        }
    }

    @Override
    public void reiniciarJogo() {
        super.reiniciarJogo();
    }
}

