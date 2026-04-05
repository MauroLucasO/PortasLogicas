package PortasLogicas;

import java.util.*;

public class TabelaVerdade {

    public static boolean avaliarExpressao(String expressao, Map<Character, Boolean> valores) {

        String expr = expressao.toUpperCase();

        // CONVERTE BARRADO (A̅ → A')
        expr = normalizarBarrado(expr);

        // Substituir variáveis
        for (Map.Entry<Character, Boolean> entry : valores.entrySet()) {
            expr = expr.replaceAll("\\b" + entry.getKey() + "\\b", entry.getValue().toString());
        }

        return resolver(expr);
    }

    private static boolean resolver(String expr) {
        expr = expr.trim();

        // Retira o Espaço
        expr = expr.replaceAll(" ", "");

        // Para aceitar []
        expr = expr.replace("[", "(");
        expr = expr.replace("]", ")");

        // Parênteses
        while (expr.contains("(")) {
            int inicio = expr.lastIndexOf("(");
            int fim = expr.indexOf(")", inicio);

            String dentro = expr.substring(inicio + 1, fim);
            boolean valor = resolver(dentro);

            expr = expr.substring(0, inicio) + valor + expr.substring(fim + 1);
        }

        // Porta NOT (')
        while (expr.contains("'")) {
            expr = expr.replaceAll("true'", "false");
            expr = expr.replaceAll("false'", "true");
        }

        // Porta AND (.)

        while (expr.contains(".")) {
            expr = expr.replaceAll("true\\.true", "true");
            expr = expr.replaceAll("true\\.false", "false");
            expr = expr.replaceAll("false\\.true", "false");
            expr = expr.replaceAll("false\\.false", "false");
        }

        // Porta OR (+)

        while (expr.contains("+")) {
            expr = expr.replaceAll("true\\+true", "true");
            expr = expr.replaceAll("true\\+false", "true");
            expr = expr.replaceAll("false\\+true", "true");
            expr = expr.replaceAll("false\\+false", "false");
        }

        return Boolean.parseBoolean(expr.trim());
    }

    // Uma forma para declarar o barrado (A̅ → A')
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

    // Criar tabela verdade
    public static void gerarTabela(String expressao) {
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

        for (char v : variaveis) {
            System.out.print(v + " ");
        }
        System.out.println("| Resultado");

        for (int i = 0; i < linhas; i++) {
            Map<Character, Boolean> valores = new HashMap<>();

            for (int j = 0; j < n; j++) {
                boolean valor = (i / (int) Math.pow(2, n - j - 1)) % 2 == 1;
                valores.put(variaveis.get(j), valor);
                System.out.print((valor ? 1 : 0) + " ");
            }

            boolean resultado = avaliarExpressao(expressao, valores);
            System.out.println("|     " + (resultado ? 1 : 0));
        }
    }
}