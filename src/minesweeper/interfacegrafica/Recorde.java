package minesweeper.interfacegrafica;

import javax.swing.JOptionPane;

public class Recorde implements Comparable<Recorde> {
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
    public int compareTo(Recorde o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
    }
}
