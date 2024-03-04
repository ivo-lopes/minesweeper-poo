package minesweeper.tabuleiro;

import minesweeper.celulas.Celula;

public interface Tabuleiro {
	 Celula[][] getTabuleiro();
	    void reiniciarJogo();
	    void fecharCelulas();
	    void marcarCelula(int linha, int coluna);
	    boolean verificarVitoria();
	    void revelarTabuleiro();
	    boolean verificarDerrota();
	    void abrirCelula(int linha, int coluna);
	    void notificarMudancaVizinhos(int linha, int coluna);
		void distribuirMinas(int linhaClicada, int colunaClicada);
}
