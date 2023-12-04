package minesweeper.tabuleiro;

import javax.swing.*;

public class GameTimer extends SwingWorker<Void, Integer> {
	
	private JLabel timerLabel;
	private int segundos;
	
	public GameTimer(JLabel timer) {
		this.timerLabel = timer;
		this.segundos = 0;
	}
	
	  @Override
	    protected Void doInBackground() throws Exception {
	        while (!isCancelled()) {
	            Thread.sleep(1000);
	            segundos++;
	            publish(segundos);
	        }
	        return null;
	    }

	    @Override
	    protected void process(java.util.List<Integer> chunks) {
	        int tempoFinal = chunks.get(chunks.size() - 1);
	        timerLabel.setText("Tempo: " + tempoFinal + " segundos");
	    }

}
