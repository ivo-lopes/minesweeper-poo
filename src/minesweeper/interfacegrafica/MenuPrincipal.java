package minesweeper.interfacegrafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class MenuPrincipal extends JFrame {
    private JPanel panel;
    private JLabel tituloLabel;
    private JButton jogarButton;
    private JButton opcoesButton;
    private JButton sairButton;
    private List<Recorde> recordes;
    private JButton recordesButton;

    public MenuPrincipal() {
        setTitle("Menu Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1)); // Adicionando espaçamento entre os botões

        tituloLabel = new JLabel("Campo Minado");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Define a fonte em negrito e o tamanho grande
        tituloLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centraliza o texto horizontalmente
        panel.add(tituloLabel);

        jogarButton = new JButton("Jogar");
        jogarButton.setPreferredSize(new Dimension(200, 50)); // Reduzindo o tamanho do botão
        jogarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exibirDificuldades(); // Mudança: Chama a exibição das dificuldades ao invés de ir direto ao jogo
            }
        });
        panel.add(jogarButton);

        opcoesButton = new JButton("Opções");
        opcoesButton.setPreferredSize(new Dimension(200, 50)); // Reduzindo o tamanho do botão
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
        sairButton.setPreferredSize(new Dimension(200, 50)); // Reduzindo o tamanho do botão
        sairButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel.add(sairButton);

        recordes = new ArrayList<>();
        inicializarRecordes(); // Inicializa a lista de recordes

        add(panel);
        setVisible(true);
    }

    private void inicializarRecordes() {
        for (int i = 0; i < 10; i++) {
            recordes.add(new Recorde("--", 0)); // Adiciona 10 recordes vazios inicialmente
        }
    }

    private void exibirRecordes() {
        // Implementar a lógica para exibir os recordes em uma nova janela
        JOptionPane.showMessageDialog(this, "Lista de Recordes:\n" + formatarRecordes(), "Recordes", JOptionPane.INFORMATION_MESSAGE);
    }

    private String formatarRecordes() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < recordes.size(); i++) {
            builder.append((i + 1) + ". " + recordes.get(i).getNome() + ": " + recordes.get(i).getTempo() + " segundos\n");
        }
        return builder.toString();
    }

    private void exibirOpcoes() {
        setTitle("Opções");

        panel.removeAll();
        panel.setLayout(new GridLayout(3, 1)); // Alterado de 5 para 4

        JButton modoButton = new JButton("Modo");
        modoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implementar ação para escolher o modo de jogo
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
        panel.setLayout(new GridLayout(4, 1)); // Alterado de 5 para 4

        JButton facilButton = new JButton("Fácil");
        facilButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CampoMinadoGUI(9, 10); // Nível fácil
            }
        });
        panel.add(facilButton);

        JButton medioButton = new JButton("Médio");
        medioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CampoMinadoGUI(16, 40); // Nível médio
            }
        });
        panel.add(medioButton);

        JButton dificilButton = new JButton("Difícil");
        dificilButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CampoMinadoGUI(20, 80); // Nível difícil
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
        JOptionPane.showMessageDialog(this, "Texto genérico sobre lontras.", "Sobre", JOptionPane.INFORMATION_MESSAGE);
    }
}
