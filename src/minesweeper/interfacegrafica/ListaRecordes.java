package minesweeper.interfacegrafica;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListaRecordes {
    private List<Recorde> recordes;

    public ListaRecordes() {
        this.recordes = new ArrayList<>();
        carregarRecordes();
    }

    public void adicionarRecorde(Recorde recorde) {
        recordes.add(recorde);
        Collections.sort(recordes); // Ordenar os recordes após adicionar um novo
        salvarRecordes();
    }

    public List<Recorde> getRecordes() {
        return recordes;
    }

    private void carregarRecordes() {
        try (BufferedReader reader = new BufferedReader(new FileReader("recordlist.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String nome = parts[0];
                int tempo = Integer.parseInt(parts[1]);
                recordes.add(new Recorde(nome, tempo));
            }
        } catch (IOException e) {
            // Se ocorrer algum erro ao ler o arquivo, apenas imprime a mensagem de erro
            e.printStackTrace();
        }
    }

    private void salvarRecordes() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("recordlist.txt"))) {
            for (Recorde recorde : recordes) {
                writer.write(recorde.getNome() + "," + recorde.getTempo());
                writer.newLine();
            }
        } catch (IOException e) {
            // Se ocorrer algum erro ao escrever no arquivo, apenas imprime a mensagem de erro
            e.printStackTrace();
        }
    }
}
