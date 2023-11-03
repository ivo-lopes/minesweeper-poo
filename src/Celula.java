public abstract class Celula {
    private boolean aberta;
    private boolean marcada;

    public boolean isAberta() {
        return aberta;
    }

    public void setAberta(boolean aberta) {
        this.aberta = aberta;
    }

    public boolean isMarcada() {
        return marcada;
    }

    public void setMarcada(boolean marcada) {
        this.marcada = marcada;
    }
}

class CelulaVazia extends Celula {
    private int valor;  // Número de minas vizinhas

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}

class CelulaBomba extends Celula {
}

class CelulaVizinha extends Celula {
    private int valor;  // Número de minas vizinhas

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}