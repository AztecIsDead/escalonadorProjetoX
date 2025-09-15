import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

    public class CsvProcessosInterativo {
        public static void main(String[] args) {
            String csvFile = "processos.csv";
            Scanner scanner = new Scanner(System.in);
            int idCounter = 1; // Contador para gerar IDs automaticamente


            System.out.println("Digite os dados (Nome, Prioridade, Ciclos_Necessarios, Recurso_Necessario). Digite 'sair' no Nome para finalizar:");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile, StandardCharsets.UTF_8))) {
                writer.write("Id,Nome,Prioridade,Ciclos_Necessarios,Recurso_Necessario");
                writer.newLine();

                while (true) {
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine().trim();
                    if (nome.equalsIgnoreCase("sair")) {
                        break;
                    }
                    if (nome.isEmpty()) {
                        System.out.println("Nome não pode ser vazio!");
                        continue;
                    }
                    if (nome.length() > 100) {
                        System.out.println("Nome muito longo! Máximo de 100 caracteres.");
                        continue;
                    }

                    System.out.print("Prioridade (Alta, Média, Baixa): ");
                    String prioridade = scanner.nextLine().trim();
                    if (!prioridade.equalsIgnoreCase("Alta") &&
                            !prioridade.equalsIgnoreCase("Média") &&
                            !prioridade.equalsIgnoreCase("Baixa")) {
                        System.out.println("Prioridade inválida! Use Alta, Média ou Baixa.");
                        continue;
                    }

                    System.out.print("Ciclos Necessários (inteiro > 0): ");
                    String ciclosStr = scanner.nextLine().trim();
                    int ciclos;
                    try {
                        ciclos = Integer.parseInt(ciclosStr);
                        if (ciclos <= 0) {
                            System.out.println("Ciclos Necessários inválido! Deve ser maior que 0.");
                            continue;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Ciclos Necessários inválido! Digite um número inteiro.");
                        continue;
                    }

                    System.out.print("Recurso Necessário (Nulo, Disco): ");
                    String recurso = scanner.nextLine().trim();
                    if (!recurso.equalsIgnoreCase("Nulo") && !recurso.equalsIgnoreCase("Disco")) {
                        System.out.println("Recurso Necessário inválido! Use Nulo ou Disco.");
                        continue;
                    }


                    String nomeEscapado = nome.contains(",") || nome.contains("\"")
                            ? "\"" + nome.replace("\"", "\"\"") + "\""
                            : nome;
                    String prioridadeEscapada = prioridade.contains(",") || prioridade.contains("\"")
                            ? "\"" + prioridade.replace("\"", "\"\"") + "\""
                            : prioridade;
                    String recursoEscapado = recurso.contains(",") || recurso.contains("\"")
                            ? "\"" + recurso.replace("\"", "\"\"") + "\""
                            : recurso;
                    writer.write(idCounter + "," + nomeEscapado + "," + prioridadeEscapada + "," + ciclos + "," + recursoEscapado);
                    writer.newLine();
                    idCounter++;
                }
                System.out.println("Arquivo CSV escrito com sucesso em " + csvFile + "!");
            } catch (IOException e) {
                System.err.println("Erro ao escrever o arquivo CSV. Verifique permissões ou espaço em disco: " + e.getMessage());
            }


            lerCsv(csvFile);
        }

        public static void lerCsv(String filePath) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
                String linha;
                boolean isHeader = true;
                System.out.println("\nConteúdo do arquivo CSV:");
                while ((linha = reader.readLine()) != null) {
                    if (isHeader) {
                        isHeader = false;
                        continue; // Pulaaaaar o cabeçalho
                    }
                    String[] dados = linha.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                    for (int i = 0; i < dados.length; i++) {
                        dados[i] = dados[i].replaceAll("^\"|\"$", "").replace("\"\"", "\"").trim();
                    }
                    if (dados.length == 5) {
                        System.out.println("Id: " + dados[0] + ", Nome: " + dados[1] +
                                ", Prioridade: " + dados[2] + ", Ciclos Necessários: " + dados[3] +
                                ", Recurso Necessário: " + dados[4]);
                    } else {
                        System.out.println("Linha inválida (esperado 5 campos, encontrado " + dados.length + "): " + linha);
                    }
                }
            } catch (IOException e) {
                System.err.println("Erro ao ler o arquivo CSV. Verifique se o arquivo existe e as permissões: " + e.getMessage());
            }
        }
    }

