package minesweeper.logica;

public interface Tabuleiro {
    void inicializar();
    void colocarMinas();
    void calcularDicas();
    char[][] getCampo();
    boolean[][] getRevelado();
    boolean[][] getBandeiras();
    int getTamanhoDoCampo();
    int getNumeroDeMinas();
    void revelarCampo(int linha, int coluna);
    void adicionarOuRemoverBandeira(int linha, int coluna);
    boolean verificarVitoria();
}
