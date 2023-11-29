package minesweeper.celulas;

public class CelulaVazia extends Celula {
	private int valor;  // Número de minas vizinhas

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
    
    public boolean isBomba() {
		return false;
	}


}
