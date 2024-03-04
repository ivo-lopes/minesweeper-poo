package minesweeper.tabuleiro;

@SuppressWarnings("serial")
public class ValorInvalidoException extends RuntimeException {
	public ValorInvalidoException(String mensagem) {
        super(mensagem);
    }
}
