package PortasLogicas;

import java.util.Scanner;

public class TesteDaTabela {

    public static void main(String[] args) {
        Scanner print = new Scanner(System.in);

        int opcao;

        do {
            System.out.println("Menu:");
            System.out.println("==========================");
            System.out.println("1 - Gerar Tabela Verdade");
            System.out.println("0 - Sair");
            System.out.println("==========================");
            System.out.print("Escolha: ");

            opcao = print.nextInt();
            print.nextLine();

            if (opcao == 1) {
                System.out.println("\n=== Gerador de Tabela Verdade ===\n");

                System.out.print("Digite a expressão (ex: A.(B + C') ): ");
                String expressao = print.nextLine();

                System.out.println("\nTabela Verdade:\n");

                TabelaVerdade.gerarTabela(expressao);

                System.out.println();
            }

        } while (opcao != 0);

        System.out.println("\nFim.");
        print.close();
    }
}