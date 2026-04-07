package PortasLogicas;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PainelTabela extends JPanel {

    private List<String[]> dados;
    private String expressao;

    public PainelTabela(List<String[]> dados) {
        this.dados = dados;
    }

    public void setDados(List<String[]> dados, String expressao) {
        this.dados = dados;
        this.expressao = expressao;
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (dados == null) return;

        desenharTabela(g);
        desenharCircuito(g);
    }

    private void desenharTabela(Graphics g) {
        int larguraCelula = 50;
        int alturaCelula = 30;

        int x = 50;
        int y = 50;

        for (int i = 0; i < dados.size(); i++) {
            String[] linha = dados.get(i);

            for (int j = 0; j < linha.length; j++) {
                int px = x + j * larguraCelula;
                int py = y + i * alturaCelula;

                if (linha[j].equals("1")) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.RED);
                }

                g.fillRect(px, py, larguraCelula, alturaCelula);

                g.setColor(Color.BLACK);
                g.drawRect(px, py, larguraCelula, alturaCelula);
                g.drawString(linha[j], px + 20, py + 20);
            }
        }
    }

    private void desenharCircuito(Graphics g) {
        if (expressao == null) return;

        g.setColor(Color.BLACK);

        int baseX = 450;
        int baseY = 120;

        int xVar = baseX;
        int xNot = baseX + 80;
        int xAnd = baseX + 180;
        int xOr = baseX + 300;
        int xOut = baseX + 420;

        int yA = baseY;
        int yB = baseY + 60;
        int yC = baseY + 120;

        g.drawString("A", xVar - 20, yA + 5);
        g.drawString("B", xVar - 20, yB + 5);
        g.drawString("C", xVar - 20, yC + 5);

        g.drawLine(xVar, yA, xNot, yA);

        if (expressao.contains("'")) {
            g.drawPolygon(
                    new int[]{xNot, xNot + 40, xNot},
                    new int[]{yA - 10, yA, yA + 10},
                    3
            );
            g.drawString("NOT", xNot + 5, yA + 5);
            g.drawLine(xNot + 40, yA, xAnd, yA);
        } else {
            g.drawLine(xNot, yA, xAnd, yA);
        }

        g.drawLine(xVar, yB, xOr, yB);
        g.drawLine(xVar, yC, xOr, yC);

        if (expressao.contains("+")) {
            g.drawOval(xOr, yB - 20, 60, 60);
            g.drawString("OR", xOr + 20, yB + 5);

            g.drawLine(xOr + 60, yB, xAnd, yB);
        }

        if (expressao.contains(".")) {
            g.drawRect(xAnd, yA - 20, 60, 60);
            g.drawString("AND", xAnd + 10, yA + 5);

            g.drawLine(xAnd + 60, yA, xOut, yA);
        }

        g.drawString("Saída", xOut + 10, yA + 5);
    }

    @Override
    public Dimension getPreferredSize() {
        if (dados == null) return new Dimension(900, 600);
        return new Dimension(900, dados.size() * 30 + 300);
    }
}