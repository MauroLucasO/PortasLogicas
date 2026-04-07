package PortasLogicas;

import java.util.*;

public class TabelaVerdade {

    public static boolean avaliarExpressao(String expressao, Map<Character, Boolean> valores) {
        String expr = expressao.toUpperCase();
        expr = normalizarBarrado(expr);

        for (Map.Entry<Character, Boolean> entry : valores.entrySet()) {
            expr = expr.replaceAll("\\b" + entry.getKey() + "\\b", entry.getValue().toString());
        }

        return resolver(expr);
    }

    private static boolean resolver(String expr) {
        expr = expr.trim();
        expr = expr.replaceAll(" ", "");
        expr = expr.replace("[", "(").replace("]", ")");

        while (expr.contains("(")) {
            int inicio = expr.lastIndexOf("(");
            int fim = expr.indexOf(")", inicio);
            String dentro = expr.substring(inicio + 1, fim);
            boolean valor = resolver(dentro);
            expr = expr.substring(0, inicio) + valor + expr.substring(fim + 1);
        }

        while (expr.contains("'")) {
            expr = expr.replaceAll("true'", "false");
            expr = expr.replaceAll("false'", "true");
        }

        while (expr.contains(".")) {
            expr = expr.replaceAll("true\\.true", "true");
            expr = expr.replaceAll("true\\.false", "false");
            expr = expr.replaceAll("false\\.true", "false");
            expr = expr.replaceAll("false\\.false", "false");
        }

        while (expr.contains("+")) {
            expr = expr.replaceAll("true\\+true", "true");
            expr = expr.replaceAll("true\\+false", "true");
            expr = expr.replaceAll("false\\+true", "true");
            expr = expr.replaceAll("false\\+false", "false");
        }

        return Boolean.parseBoolean(expr.trim());
    }

    private static String normalizarBarrado(String expr) {
        StringBuilder nova = new StringBuilder();

        for (int i = 0; i < expr.length(); i++) {
            char atual = expr.charAt(i);

            if (i + 1 < expr.length() && expr.charAt(i + 1) == '\u0305') {
                nova.append(atual).append("'");
                i++;
            } else {
                nova.append(atual);
            }
        }

        return nova.toString();
    }

    public static List<String[]> gerarTabelaDados(String expressao) {
        expressao = expressao.toUpperCase();

        Set<Character> variaveisSet = new LinkedHashSet<>();
        for (char c : expressao.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                variaveisSet.add(c);
            }
        }

        List<Character> variaveis = new ArrayList<>(variaveisSet);
        int n = variaveis.size();
        int linhas = (int) Math.pow(2, n);

        List<String[]> tabela = new ArrayList<>();

        for (int i = 0; i < linhas; i++) {
            Map<Character, Boolean> valores = new HashMap<>();
            String[] linha = new String[n + 1];

            for (int j = 0; j < n; j++) {
                boolean valor = (i / (int) Math.pow(2, n - j - 1)) % 2 == 1;
                valores.put(variaveis.get(j), valor);
                linha[j] = valor ? "1" : "0";
            }

            boolean resultado = avaliarExpressao(expressao, valores);
            linha[n] = resultado ? "1" : "0";

            tabela.add(linha);
        }

        return tabela;
    }

    public static String identificarPortas(String expressao) {
        expressao = expressao.toUpperCase();
        StringBuilder portas = new StringBuilder("Portas usadas: ");

        if (expressao.contains(".")) {
            portas.append("AND ");
        }
        if (expressao.contains("+")) {
            portas.append("OR ");
        }
        if (expressao.contains("'")) {
            portas.append("NOT ");
        }

        return portas.toString();
    }

    public static String traduzirExpressao(String expr) {
        expr = expr.toUpperCase();
        expr = expr.replace(".", " AND ");
        expr = expr.replace("+", " OR ");
        expr = expr.replace("'", " NOT ");
        return expr;
    }
}