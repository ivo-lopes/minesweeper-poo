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
    public void mudarMarcacao() {
    	marcada = !marcada;
    }
    
    public abstract boolean isBomba();
}
