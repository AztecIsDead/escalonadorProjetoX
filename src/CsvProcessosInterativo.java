import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class CsvProcessosInterativo {
    public static void rodarEntrada() {
        String csvFile = "processos.csv";
        Scanner scanner = new Scanner(System.in);
        int idCounter = 1;

        System.out.println("Digite os dados (Nome, Prioridade, Ciclos_Necessarios, Recurso_Necessario).");
        System.out.println("Digite 'sair' no Nome para finalizar.");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile, StandardCharsets.UTF_8))) {
            writer.write("Id,Nome,Prioridade,Ciclos_Necessarios,Recurso_Necessario");
            writer.newLine();

            while (true) {
                System.out.print("Nome: ");
                String nome = scanner.nextLine().trim();
                if (nome.equalsIgnoreCase("sair")) break;

                System.out.print("Prioridade (Alta, Média, Baixa): ");
                String prioridade = scanner.nextLine().trim();

                System.out.print("Ciclos Necessários (>0): ");
                int ciclos = Integer.parseInt(scanner.nextLine().trim());

                System.out.print("Recurso Necessário (Nulo, Disco): ");
                String recurso = scanner.nextLine().trim();

                writer.write(idCounter + "," + nome + "," + prioridade + "," + ciclos + "," + recurso);
                writer.newLine();
                idCounter++;
            }
            System.out.println("Arquivo CSV salvo em " + csvFile);
        } catch (IOException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
