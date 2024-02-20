package minesweeper.tabuleiro;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Recorde {
	private static final int MAX_PONTUACOES = 10;
    private List<Pontuacao> pontuacoes;

    public Recorde() {
        this.pontuacoes = new ArrayList<>();
        carregarPontuacoes();
    }

    public void adicionarPontuacao(String nomeJogador, long tempo, boolean venceu) {
        pontuacoes.add(new Pontuacao(nomeJogador, tempo, venceu));
        pontuacoes.sort((p1, p2) -> Long.compare(p1.getTempo(), p2.getTempo()));
        if (pontuacoes.size() > MAX_PONTUACOES) {
            pontuacoes.remove(MAX_PONTUACOES);
        }
        salvarPontuacoes();
        
    }
    
    private void carregarPontuacoes() {
        try (BufferedReader reader = new BufferedReader(new FileReader("pontuacoes.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String nomeJogador = parts[0];
                    long tempo = Long.parseLong(parts[1]);
                    boolean venceu = Boolean.parseBoolean(parts[2]);
                    pontuacoes.add(new Pontuacao(nomeJogador, tempo, venceu));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void salvarPontuacoes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pontuacoes.txt"))) {
            for (Pontuacao pontuacao : pontuacoes) {
                writer.write(String.format("%s,%d,%b%n", pontuacao.getNomeJogador(), pontuacao.getTempo(), pontuacao.isVenceu()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Pontuacao> getPontuacoes() {
        return pontuacoes;
    }
    
    public List<Pontuacao> getTop10Pontuacoes() {
    	List<Pontuacao> pontuacoes = getPontuacoes();
    	List<Pontuacao> pontuacoesVencedoras = pontuacoes.stream()
    	.filter(Pontuacao::isVenceu).sorted(Comparator.comparing(Pontuacao::getTempo)).collect(Collectors.toList());

    	int tamanhoLista = Math.min(pontuacoesVencedoras.size(), MAX_PONTUACOES);
        return pontuacoesVencedoras.subList(0, tamanhoLista);

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
