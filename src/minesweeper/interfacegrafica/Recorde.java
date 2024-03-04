package minesweeper.interfacegrafica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Recorde implements Comparable<Recorde> {
    private String jogador;
    private int tempo;

    public Recorde(String jogador, int tempo) {
        this.jogador = jogador;
        this.tempo = tempo;
    }

    public String getJogador() {
        return jogador;
    }

    public int getTempo() {
        return tempo;
    }

    @Override
    public int compareTo(Recorde outro) {
        return Integer.compare(tempo, outro.tempo);
    }

    @Override
    public String toString() {
        return jogador + " - " + tempo + "s";
    }

    // Carrega os recordes salvos
    public static List<Recorde> carregarRecordes() {
        // Carregue os recordes de algum armazenamento, como um arquivo
        // Por enquanto, vamos retornar uma lista fictícia de recordes
        List<Recorde> recordes = new ArrayList<>();
        recordes.add(new Recorde("Jogador1", 100));
        recordes.add(new Recorde("Jogador2", 120));
        recordes.add(new Recorde("Jogador3", 150));
        Collections.sort(recordes); // Ordena os recordes
        return recordes;
    }

    // Método para salvar os recordes atualizados
    public static void salvarRecordes(List<Recorde> recordes) {
        // Implemente a lógica para salvar os recordes em algum armazenamento, como um arquivo
    }
}
