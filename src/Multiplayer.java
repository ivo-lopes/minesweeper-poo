package minesweeper.tabuleiro;

import javax.swing.JOptionPane;

public class Multiplayer extends CampoMinado {
	private long startPlayer1;
    private long endPlayer1;
    private long startPlayer2;
    private long endPlayer2;
    private boolean turnoJogador1; // Variável para controlar o turno dos jogadores
    private long inicioTurnoAtual; 

	public Multiplayer(int tamanhoTabuleiro, int numMinas) {
		super(tamanhoTabuleiro, numMinas);
		turnoJogador1 = true;
	}
	
	public void startPlayer1() {
    	startPlayer1 = System.currentTimeMillis();
    }
    
    public void endPlayer1() {
        endPlayer1 = System.currentTimeMillis();
    }
    
    public void startPlayer2() {
    	startPlayer2 = System.currentTimeMillis();
    }

    public void endPlayer2() {
        endPlayer2 = System.currentTimeMillis();
        vencedor();
    }
    
    public void iniciarTurno() {
        inicioTurnoAtual = System.currentTimeMillis();
    }

    public void encerrarTurno() {
        long fimTurnoAtual = System.currentTimeMillis();
        long tempoDoTurno = fimTurnoAtual - inicioTurnoAtual; 

        if (turnoJogador1) {
            endPlayer1 = fimTurnoAtual; // Atualiza o tempo do jogador 1
            JOptionPane.showMessageDialog(null, "Jogador 1 terminou em " + tempoDoTurno/1000 + " segundos!");
        } else {
            endPlayer2 = fimTurnoAtual; // Atualiza o tempo do jogador 2
            JOptionPane.showMessageDialog(null, "Jogador 2 terminou em " + tempoDoTurno/1000 + " segundos!");
            vencedor(); // Após o término do turno do jogador 2, verifica o vencedor
        }

        turnoJogador1 = !turnoJogador1; // Alterna o turno para o próximo jogador
    }
    
    public void vencedor() {
        if (endPlayer1 == 0 || endPlayer2 == 0) {
            // Não faz a verificação se um dos jogadores ainda não terminou
            return;
        }

        long tempoPlayer1 = endPlayer1 - startPlayer1;
        long tempoPlayer2 = endPlayer2 - startPlayer2;

        if (tempoPlayer1 < tempoPlayer2) {
            JOptionPane.showMessageDialog(null, "Jogador 1 venceu com " + tempoPlayer1/1000 + " segundos!");
        } else if (tempoPlayer1 > tempoPlayer2) {
            JOptionPane.showMessageDialog(null, "Jogador 2 venceu com " + tempoPlayer2/1000 + " segundos!");
        } else {
            JOptionPane.showMessageDialog(null, "Empate!");
        }
    }
    
    public boolean verificarVitoria() {
    	revelarTabuleiro();
    	endPlayer1();
		return true;
    }


}
