package minesweeper.interfacegrafica;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListaRecordes {
    private static final int MAX_RECORDS = 10;
    private List<Recorde> recordes;
    private int indice;
    private static final String FILE_PATH = "src/minesweeper/recordlist.txt"; // Alteração: Caminho do arquivo

    public ListaRecordes() {
        this.recordes = new ArrayList<>();
        this.indice = 1;
        carregarRecordes();
    }

    public void adicionarRecorde(Recorde recorde) {
        recorde.setIndex(indice++);
        recordes.add(recorde);
        Collections.sort(recordes);
        if (recordes.size() > MAX_RECORDS) {
            recordes = recordes.subList(0, MAX_RECORDS);
        }
        salvarRecordes();
    }

    public List<Recorde> getRecordes() {
        return recordes;
    }

    private void carregarRecordes() {
        recordes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+"); // Dividindo a linha por espaços em branco
                if (parts.length >= 2) {
                    String nome = parts[0];
                    int tempo = Integer.parseInt(parts[1]);
                    recordes.add(new Recorde(nome, tempo));
                }
            }
            // Ordena os recordes com base no tempo (menor tempo primeiro)
            Collections.sort(recordes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void salvarRecordes() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Recorde recorde : recordes) {
                writer.println(recorde.getNome() + " " + recorde.getTempo());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < recordes.size(); i++) {
            sb.append(recordes.get(i).toString());
            if (i < recordes.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
    
}
