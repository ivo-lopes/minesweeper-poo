package minesweeper.interfacegrafica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListaRecordes {
    private List<Recorde> recordes;
    private final int MAX_RECORDS = 10;

    public ListaRecordes() {
        this.recordes = new ArrayList<>();
        inicializarRecordes();
    }

    private void inicializarRecordes() {
        for (int i = 0; i < MAX_RECORDS; i++) {
            recordes.add(new Recorde("--", Integer.MAX_VALUE));
        }
    }

    public List<Recorde> getRecordes() {
        return recordes;
    }

    public void adicionarRecorde(String nome, int tempo) {
        recordes.add(new Recorde(nome, tempo));
        Collections.sort(recordes);
        if (recordes.size() > MAX_RECORDS) {
            recordes.remove(recordes.size() - 1);
        }
    }
}
