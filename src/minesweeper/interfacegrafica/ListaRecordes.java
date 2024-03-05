package minesweeper.interfacegrafica;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListaRecordes {
    private static final int MAX_RECORDS = 10;
    private List<Recorde> recordes;
    @SuppressWarnings("unused")
    private int indice;
    private static final String FILE_PATH = "src/minesweeper/recordlist.txt"; 

    public ListaRecordes() {
        this.recordes = new ArrayList<>();
        this.indice = 1;
        carregarRecordes();
    }

    public void adicionarRecorde(Recorde recorde) {
        recordes.add(recorde);
        ordenarRecordes();
        limitarRecordes();
        atribuirIndices();
        salvarRecordes();
    }

    private void ordenarRecordes() {
        Collections.sort(recordes);
    }

    private void limitarRecordes() {
        if (recordes.size() > MAX_RECORDS) {
            recordes = recordes.subList(0, MAX_RECORDS);
        }
    }

    private void atribuirIndices() {
        for (int i = 0; i < recordes.size(); i++) {
            recordes.get(i).setIndex(i + 1);
        }
    }

    public List<Recorde> getRecordes() {
        return recordes;
    }

    private void carregarRecordes() {
        recordes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length >= 3) {
                    String nome = parts[1];
                    int tempo = Integer.parseInt(parts[2]);
                    recordes.add(new Recorde(nome, tempo));
                }
            }
            Collections.sort(recordes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void salvarRecordes() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Recorde recorde : recordes) {
                writer.println(recorde.getIndex() + " " + recorde.getNome() + " " + recorde.getTempo());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lista de Recordes:\n");
        for (Recorde recorde : recordes) {
            sb.append(recorde.getIndex()).append(". ").append(recorde.getNome()).append(": ").append(recorde.getTempo()).append(" segundos\n");
        }
        return sb.toString();
    }   
}
