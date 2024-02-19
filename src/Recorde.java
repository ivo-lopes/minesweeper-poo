package minesweeper.tabuleiro;

import java.util.ArrayList;
import java.util.List;

public class Recorde {
	private static final int MAX_PONTUACOES = 10;
    private List<Pontuacao> pontuacoes;

    public Recorde() {
        pontuacoes = new ArrayList<>();
    }

    public void adicionarPontuacao(String nomeJogador, long tempo, boolean venceu) {
        pontuacoes.add(new Pontuacao(nomeJogador, tempo, venceu));
        pontuacoes.sort((p1, p2) -> Long.compare(p1.getTempo(), p2.getTempo()));
        if (pontuacoes.size() > MAX_PONTUACOES) {
            pontuacoes.remove(MAX_PONTUACOES);
        }
    }

    public List<Pontuacao> getPontuacoes() {
        return pontuacoes;
    }
}

class Pontuacao {
    private String nomeJogador;
    private long tempo;
    private boolean venceu;

    public Pontuacao(String nomeJogador, long tempo, boolean venceu) {
        this.nomeJogador = nomeJogador;
        this.tempo = tempo;
        this.venceu = venceu;
    }

    public String getNomeJogador() {
        return nomeJogador;
    }

    public long getTempo() {
        return tempo;
    }
    
    public boolean isVenceu() {
        return venceu;
    }

}
