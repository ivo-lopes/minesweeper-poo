package minesweeper.interfacegrafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class MenuPrincipal extends JFrame {
    private JPanel panel;
    private JLabel tituloLabel;
    private JButton jogarButton;
    private JButton opcoesButton;
    private JButton sairButton;
    @SuppressWarnings("unused")
    private List<Recorde> recordes;
    private JButton recordesButton;

    public MenuPrincipal() {
        setTitle("Menu Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        tituloLabel = new JLabel("Campo Minado");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(tituloLabel);

        jogarButton = new JButton("Jogar");
        jogarButton.setPreferredSize(new Dimension(200, 50)); 
        jogarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exibirDificuldades(); 
            }
        });
        panel.add(jogarButton);

        opcoesButton = new JButton("Opções");
        opcoesButton.setPreferredSize(new Dimension(200, 50)); 
        opcoesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exibirOpcoes();
            }
        });
        panel.add(opcoesButton);

        recordesButton = new JButton("Recordes");
        recordesButton.setPreferredSize(new Dimension(200, 50));
        recordesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exibirRecordes();
            }
        });
        panel.add(recordesButton);

        sairButton = new JButton("Sair");
        sairButton.setPreferredSize(new Dimension(200, 50)); 
        sairButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(sairButton);

        recordes = new ArrayList<>();

        add(panel);
        setVisible(true);
    }


    private void exibirRecordes() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/minesweeper/recordlist.txt"));
            String linha;
            StringBuilder conteudo = new StringBuilder();
            while ((linha = reader.readLine()) != null) {
                conteudo.append(linha).append("\n");
            }
            reader.close();
            JOptionPane.showMessageDialog(this, conteudo.toString(), "Recordes", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo de recordes.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exibirOpcoes() {
        setTitle("Opções");

        panel.removeAll();
        panel.setLayout(new GridLayout(3, 1)); 

        JButton modoButton = new JButton("Modo");
        modoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implementar ação para escolher o modo de jogo entre normal e maluco
            }
        });
        panel.add(modoButton);

        JButton sobreButton = new JButton("Sobre");
        sobreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exibirSobre();
            }
        });
        panel.add(sobreButton);

        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                panel.setLayout(new GridLayout(5, 1));
                panel.add(tituloLabel);
                panel.add(jogarButton);
                panel.add(recordesButton);
                panel.add(opcoesButton);
                panel.add(sairButton);
                setTitle("Menu Principal");
                revalidate();
                repaint();
            }
        });
        panel.add(voltarButton);

        revalidate();
        repaint();
    }

    private void exibirDificuldades() {
        setTitle("Dificuldade");

        panel.removeAll();
        panel.setLayout(new GridLayout(4, 1));

        JButton facilButton = new JButton("Fácil");
        facilButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CampoMinadoGUI(9, 10); 
            }
        });
        panel.add(facilButton);

        JButton medioButton = new JButton("Médio");
        medioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CampoMinadoGUI(13, 20);
            }
        });
        panel.add(medioButton);

        JButton dificilButton = new JButton("Difícil");
        dificilButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CampoMinadoGUI(17, 30);
            }
        });
        panel.add(dificilButton);

        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.removeAll();
                panel.setLayout(new GridLayout(5, 1));
                panel.add(tituloLabel);
                panel.add(jogarButton);
                panel.add(recordesButton);
                panel.add(opcoesButton);
                panel.add(sairButton);
                setTitle("Menu Principal");
                revalidate();
                repaint();
            }
        });
        panel.add(voltarButton);

        revalidate();
        repaint();
    }

    private void exibirSobre() {
        JOptionPane.showMessageDialog(this, "Projeto de POO.\nFeito por Ivo Lopes.", "Sobre", JOptionPane.INFORMATION_MESSAGE);
    }
}
