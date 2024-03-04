package minesweeper.logica;

import java.util.Random;

import minesweeper.excecoes.ValorAtributoInvalidoException;

public abstract class CampoMinado implements Tabuleiro {
    protected char[][] campo;
    protected char[][] minas;
    protected boolean[][] revelado;
    protected boolean[][] bandeiras;
    protected int tamanhoDoCampo;
    protected int numeroDeMinas;
    protected int celulasNaoReveladas;

    public CampoMinado(int tamanhoDoCampo, int numeroDeMinas) throws ValorAtributoInvalidoException {
        if (tamanhoDoCampo <= 0 || numeroDeMinas < 0 || numeroDeMinas >= tamanhoDoCampo * tamanhoDoCampo) {
            throw new ValorAtributoInvalidoException("Tamanho do campo ou número de minas inválidos.");
        }

        this.tamanhoDoCampo = tamanhoDoCampo;
        this.numeroDeMinas = numeroDeMinas;
        this.campo = new char[tamanhoDoCampo][tamanhoDoCampo];
        this.minas = new char[tamanhoDoCampo][tamanhoDoCampo];
        this.revelado = new boolean[tamanhoDoCampo][tamanhoDoCampo];
        this.bandeiras = new boolean[tamanhoDoCampo][tamanhoDoCampo];
        this.celulasNaoReveladas = tamanhoDoCampo * tamanhoDoCampo;

        inicializar();
        colocarMinas();
        calcularDicas();
    }

    @Override
    public void inicializar() {
        for (int i = 0; i < tamanhoDoCampo; i++) {
            for (int j = 0; j < tamanhoDoCampo; j++) {
                campo[i][j] = ' ';
                revelado[i][j] = false;
                bandeiras[i][j] = false;
            }
        }
    }

    @Override
    public void colocarMinas() {
        Random random = new Random();

        for (int i = 0; i < numeroDeMinas; i++) {
            int linha, coluna;
            do {
                linha = random.nextInt(tamanhoDoCampo);
                coluna = random.nextInt(tamanhoDoCampo);
            } while (minas[linha][coluna] == '*');

            minas[linha][coluna] = '*';
        }
    }

    @Override
    public void calcularDicas() {
        for (int i = 0; i < tamanhoDoCampo; i++) {
            for (int j = 0; j < tamanhoDoCampo; j++) {
                if (minas[i][j] == '*') {
                    continue;
                }

                int count = 0;
                for (int row = i - 1; row <= i + 1; row++) {
                    for (int col = j - 1; col <= j + 1; col++) {
                        if (row >= 0 && row < tamanhoDoCampo && col >= 0 && col < tamanhoDoCampo && minas[row][col] == '*') {
                            count++;
                        }
                    }
                }
                if (count > 0) {
                    campo[i][j] = (char) (count + '0');
                }
            }
        }
    }
}
