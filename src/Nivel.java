package minesweeper.niveis;

public class Nivel {
	protected int tamanhoTabuleiro;
    protected int numMinas;

    public Nivel(int tamanhoTabuleiro, int numMinas) {
        this.tamanhoTabuleiro = tamanhoTabuleiro;
        this.numMinas = numMinas;
    }

    public int getTamanhoTabuleiro() {
        return tamanhoTabuleiro;
    }

    public int getNumMinas() {
        return numMinas;
    }

}
