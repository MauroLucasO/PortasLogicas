package PortasLogicas;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class PainelTabela extends JPanel {

    private List<String[]> dados;
    private String entrada;

    public PainelTabela(List<String[]> dados) {
        this.dados = dados;
    }

    public void setDados(List<String[]> dados, String entrada) {
        this.dados = dados;
        this.entrada = entrada;
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (dados != null) {
            desenharTabela(g);
        }

        desenharCircuitos(g);
    }

    private void desenharTabela(Graphics g) {

        int larguraCelula = 50;
        int alturaCelula = 30;

        int x = 50;
        int y = 50;

        for (int i = 0; i < dados.size(); i++) {

            String[] linha = dados.get(i);

            for (int j = 0; j < linha.length; j++) {

                int px = x + (j * larguraCelula);
                int py = y + (i * alturaCelula);

                if ("1".equals(linha[j])) {
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

    private void desenharCircuitos(Graphics g) {

        if (entrada == null || entrada.isBlank()) {
            return;
        }

        String[] expressoes = entrada.split(",");

        int yBase = 150;

        for (int i = 0; i < expressoes.length; i++) {

            String expr = expressoes[i].trim();

            desenharCircuito(g, expr, yBase);

            yBase += 180;
        }
    }

    private void desenharCircuito(Graphics g, String expressao, int baseY) {

        String expr = expressao.toUpperCase();

        String nomeSaida = "SAIDA";

        if (expr.contains("=")) {
            String[] partes = expr.split("=");
            nomeSaida = partes[0].trim();
            expr = partes[1].trim();
        }

        Set<Character> variaveis = new LinkedHashSet<>();

        for (char c : expr.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                variaveis.add(c);
            }
        }

        int xVar = 450;
        int xPorta = 650;
        int xSaida = 850;

        int espacamento = 50;

        int indice = 0;
        int centroY = baseY;

        g.setColor(Color.BLACK);

        g.drawString("Circuito: " + expressao, xVar, baseY - 40);

        for (char var : variaveis) {

            int y = baseY + (indice * espacamento);

            g.drawString(String.valueOf(var), xVar - 20, y + 5);

            g.drawLine(xVar, y, xPorta, y);

            if (expr.contains(var + "'")) {

                int[] xTri = {
                        xVar + 40,
                        xVar + 80,
                        xVar + 40
                };

                int[] yTri = {
                        y - 10,
                        y,
                        y + 10
                };

                g.drawPolygon(xTri, yTri, 3);
                g.drawOval(xVar + 80, y - 3, 6, 6);

                g.drawString("NOT", xVar + 45, y + 25);

                g.drawLine(xVar + 86, y, xPorta, y);
            }

            centroY = y;
            indice++;
        }

        if (variaveis.size() > 1) {
            centroY = baseY + ((variaveis.size() - 1) * espacamento) / 2;
        }

        if (expr.contains(".")) {

            g.drawRect(xPorta, centroY - 25, 70, 50);
            g.drawString("AND", xPorta + 15, centroY + 5);

            g.drawLine(xPorta + 70, centroY, xSaida, centroY);
        }

        if (expr.contains("+")) {

            g.drawOval(xPorta, centroY - 25, 70, 50);
            g.drawString("OR", xPorta + 22, centroY + 5);

            g.drawLine(xPorta + 70, centroY, xSaida, centroY);
        }

        if (!expr.contains(".") && !expr.contains("+")) {
            g.drawLine(xPorta - 100, centroY, xSaida, centroY);
        }

        g.drawString(nomeSaida, xSaida + 10, centroY + 5);
    }

    @Override
    public Dimension getPreferredSize() {

        return new Dimension(1200, 1000);
    }
}