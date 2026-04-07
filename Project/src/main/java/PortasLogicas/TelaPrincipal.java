package PortasLogicas;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaPrincipal extends JFrame {

    private JTextField campoExpressao;
    private PainelTabela painelTabela;
    private JLabel labelPortas;

    public TelaPrincipal() {
        setTitle("Gerador de Tabela Verdade");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        criarMenu();

        JPanel painelTop = new JPanel();

        JLabel label = new JLabel("Expressão: ");
        campoExpressao = new JTextField(20);

        JButton botaoGerar = new JButton("Gerar Tabela");

        labelPortas = new JLabel(" ");

        painelTop.add(label);
        painelTop.add(campoExpressao);
        painelTop.add(botaoGerar);
        painelTop.add(labelPortas);

        add(painelTop, BorderLayout.NORTH);

        painelTabela = new PainelTabela(null);
        JScrollPane scroll = new JScrollPane(painelTabela);

        add(scroll, BorderLayout.CENTER);

        botaoGerar.addActionListener(e -> gerarTabela());
    }

    private void criarMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuArquivo = new JMenu("Arquivo");

        JMenuItem gerar = new JMenuItem("Gerar Tabela");
        JMenuItem sair = new JMenuItem("Sair");

        menuArquivo.add(gerar);
        menuArquivo.addSeparator();
        menuArquivo.add(sair);

        menuBar.add(menuArquivo);
        setJMenuBar(menuBar);

        gerar.addActionListener(e -> gerarTabela());
        sair.addActionListener(e -> System.exit(0));
    }

    private void gerarTabela() {
        String expressao = campoExpressao.getText();

        if (expressao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite uma expressão!");
            return;
        }

        List<String[]> dados = TabelaVerdade.gerarTabelaDados(expressao);

        painelTabela.setDados(dados, expressao);

        labelPortas.setText(
                TabelaVerdade.identificarPortas(expressao) + " | " +
                        TabelaVerdade.traduzirExpressao(expressao)
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }
}