package minesweeper.interfacegrafica;

import javax.swing.JOptionPane;

public class Recorde implements Comparable<Recorde> {
    private int index;
    private String nome;
    private int tempo;

    public Recorde(String nome, int tempo) {
        this.nome = nome;
        this.tempo = tempo;
    }

    // Getter para o nome
    public String getNome() {
        return nome;
    }

    // Setter para o nome
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getter para o tempo
    public int getTempo() {
        return tempo;
    }

    // Setter para o tempo
    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    // Método para solicitar o nome do usuário
    public void solicitarNome() {
        String nome = JOptionPane.showInputDialog(null, "Parabéns! Você venceu o jogo. Por favor, insira seu nome:", "Registro de Recorde", JOptionPane.PLAIN_MESSAGE);
        if (nome != null && !nome.isEmpty()) {
            setNome(nome);
        }
    }

    @Override
    public int compareTo(Recorde outroRecorde) {
        // Comparar os recordes com base no tempo
        return Integer.compare(this.tempo, outroRecorde.tempo);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() { // Método para obter o índice do recorde na lista
        return index;
    }

    @Override
    public String toString() {
        return String.format("%d. %s: %d segundos", index, nome, tempo);
    }
}
