import java.util.Random;
import java.util.Scanner;

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
            if (!(tabuleiro[linha][coluna] instanceof CelulaBomba)) {
                tabuleiro[linha][coluna] = new CelulaBomba();
                minasRestantes--;
            }
        }

        // Calcula os valores das células (número de minas vizinhas)
        for (int linha = 0; linha < tamanhoTabuleiro; linha++) {
            for (int coluna = 0; coluna < tamanhoTabuleiro; coluna++) {
                if (!(tabuleiro[linha][coluna] instanceof CelulaBomba)) {
                    int valor = calcularValorCelula(linha, coluna);
                    ((CelulaVazia) tabuleiro[linha][coluna]).setValor(valor);
                }
            }
        }
    }
    // Imprime a representação textual do jogo
    public String printTabuleiro() {
    	StringBuilder board = new StringBuilder();
    	
    	board.append("   ");
    	for( int col = 0; col < tamanhoTabuleiro; col++) {
    		board.append(String.format(" %02d", col));
    	}
    	board.append("\n");
    	
    	board.append("  ");
    	for(int col = 0; col < tamanhoTabuleiro; col++) {
    		board.append("----");
    	}
    	board.append("-\n");
    	
    	
    	for(int row = 0; row < tamanhoTabuleiro; row++) {
    		board.append(String.format("%02d | ", row));
    		for(int col = 0; col < tamanhoTabuleiro; col++) {
    			Celula celula = tabuleiro[row][col];
    			if(celula.isAberta()) {
    				if(celula instanceof CelulaBomba) {
    					board.append("X  ");
    				}else if(celula instanceof CelulaVazia) {
    					int valor = ((CelulaVazia) celula).getValor();
    					if(valor > 0) {
    						board.append(String.format("%02d ", valor));
    					}else {
    						board.append("   ");
    					}
    				}
    			}else if(celula.isMarcada()) {
    				board.append("M ");
    			}else {
    				board.append("#  ");
    			}
    		}
    		board.append("|\n");
    	}
    	
    	board.append("  ");
    	for(int col = 0; col < tamanhoTabuleiro; col++) {
    		board.append("----");
    	}
    	board.append("-\n");
    	return board.toString();
    }

    private int calcularValorCelula(int linha, int coluna) {
        if(!(tabuleiro[linha][coluna] instanceof CelulaBomba)){
        	int valor = 0;
        	
        	for(int i = -1; i <= 1; i++) {
        		for(int j = -1; j <= 1; j++) {
        			int novaLinha = linha + i;
        			int novaColuna = coluna + j;
        			
        			if(novaLinha >= 0 && novaLinha < tamanhoTabuleiro && novaColuna >=0 && novaColuna < tamanhoTabuleiro) {
        				if(tabuleiro[novaLinha][novaColuna] instanceof CelulaBomba) {
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
        if (!celula.isAberta()) {
            celula.setMarcada(!celula.isMarcada());
        }
    }

    public boolean verificarVitoria() {
    	for(int linha = 0; linha < tamanhoTabuleiro; linha++) {
    		for(int coluna = 0; coluna < tamanhoTabuleiro; coluna++) {
    			Celula celula = tabuleiro [linha][coluna];
    			if(!(celula.isAberta()) && !(celula instanceof CelulaBomba)){
    				return false;
    			}
    		}
    	}
	return true;
    }

    public boolean verificarDerrota() {
    	for(int linha = 0; linha < tamanhoTabuleiro; linha++) {
    		for(int coluna = 0; coluna < tamanhoTabuleiro; coluna++) {
    			Celula celula = tabuleiro [linha][coluna];
    			if(celula.isAberta() && celula instanceof CelulaBomba) {
    				return true;
    			}
    		}
    	}
		return false;
    }

    public void abrirCelula(int linha, int coluna) {
        Celula celula = tabuleiro[linha][coluna];

        if (celula.isAberta() || celula.isMarcada()) {
            return;
        }

        celula.setAberta(true);

        if (celula instanceof CelulaBomba) {
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
        			
        			if(!celula.isAberta() && !(celula instanceof CelulaBomba)) {
        				celula.setAberta(true);
        			if(((CelulaVazia) celula).getValor() == 0) {
                                   abrirCelulasVizinhas(novaLinha, novaColuna);
                    }
        		}
        	}
        }
      }
   }
    public static void main(String[] args) {
        int tamanhoTabuleiro = 8;
        int numMinas = 10;
        
        Scanner scan = new Scanner(System.in);
        
        boolean[][]celulasSelecionadas = new boolean [tamanhoTabuleiro][tamanhoTabuleiro];
        CampoMinado campoMinado = new CampoMinado(tamanhoTabuleiro, numMinas);
        String initialBoard = campoMinado.printTabuleiro();
        System.out.println(initialBoard);
        
        while(true) {
        	System.out.println("Digite a linha: (0-" + (tamanhoTabuleiro - 1) + ")");
        	int row = scan.nextInt();
        	System.out.println("Digite a coluna: (0-" + (tamanhoTabuleiro - 1) + ")");
        	int col = scan.nextInt();
        	
        	if(row < 0 || row >= tamanhoTabuleiro || col < 0 || col >= tamanhoTabuleiro) {
        		System.out.println();
        		System.out.println("Linha ou coluna inválida");
        		continue;
        	}
        	
        	if(celulasSelecionadas[row][col]) {
        		System.out.println();
        		System.out.println("Você já selecionou essa célula!");
        	}
        	celulasSelecionadas[row][col] = true;
        	campoMinado.abrirCelula(row, col);
        	String novoTabuleiro = campoMinado.printTabuleiro();
        	System.out.println();
        	System.out.println(novoTabuleiro);
        	
        	if(campoMinado.verificarDerrota()) {
        		System.out.println();
        		System.out.println("O jogo acabou! Você atingiu uma bomba!");
        		break;
        	}
        	if(campoMinado.verificarVitoria()) {
        		System.out.println();
        		System.out.println("Parabéns você venceu!");
        		break;
        	}
       }
        scan.close();    
    }
}
