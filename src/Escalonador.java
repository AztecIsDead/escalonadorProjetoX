import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;

public class Escalonador {
    private ListaDeProcessos alta = new ListaDeProcessos();//cria uma lista de processos nova a partir da String: alta
    private ListaDeProcessos media = new ListaDeProcessos();//cria uma lista de processos nova a partir da String: media
    private ListaDeProcessos baixa = new ListaDeProcessos();//cria uma lista de processos nova a partir da String: baixa
    private ListaDeProcessos bloqueados = new ListaDeProcessos();//cria uma lista de processos nova a partir da String: bloqueados

    private int contadorAlta = 0; //cria o Integer contadorAlta para usar no IF para a cada 5 de alta, 1 de média.

    public void carregarDeCsv(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String linha;
            boolean cabecalho = true;
            while ((linha = reader.readLine()) != null) {
                if (cabecalho) { cabecalho = false; continue; }
                String[] dados = linha.split(",");
                int id = Integer.parseInt(dados[0].trim());
                String nome = dados[1].trim();
                int prioridade = Integer.parseInt(dados[2].trim());
                int ciclos = Integer.parseInt(dados[3].trim());
                String recurso = dados[4].trim();

                Processo p = new Processo(id, nome, prioridade, ciclos, recurso); //define "p" como um novo processo

                switch (prioridade){ //switch case para registro de prioridades
                    case 1:
                        alta.adicionar(p);//registrar como prioridade ALTA
                        break;

                    case 2:
                        media.adicionar(p);//registrar como prioridade MEDIA
                        break;

                    case 3:
                        baixa.adicionar(p);//registrar como prioridade BAIXA
                        break;

                    default:
                        baixa.adicionar(p);//registrar como prioridade BAIXA caso não identifique o input
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar CSV: " + e.getMessage());
        }
    }

    public void executarCiclos() {
        System.out.println("\n--- Iniciando Simulação ---");

        while (!alta.isEmpty() || !media.isEmpty() || !baixa.isEmpty() || !bloqueados.isEmpty()) {

            Processo desbloqueado = bloqueados.remover();
            if (desbloqueado != null) {
                System.out.println("[DESBLOQUEADO] " + desbloqueado.getNome());
                if (desbloqueado.getPrioridade() == 1) alta.adicionar(desbloqueado);
                else if (desbloqueado.getPrioridade() == 2) media.adicionar(desbloqueado);
                else baixa.adicionar(desbloqueado);
            }//enquanto nenhum dos processos estiver vazio: desbloquear processos Disco seguindo a ordem de prioridade 1 2 e 3;

            Processo atual = null;  // cria uma variável para armazenar o processo

            if (contadorAlta >= 5) {
                if (!media.isEmpty()) {
                    atual = media.remover();  // se a fila de prioridade média não está vazia, pega um processo da fila de prioridade 2
                } else if (!baixa.isEmpty()) {
                    atual = baixa.remover();// se a fila de prioridade baixa não está vazia, pega um processo da fila de prioridade 3
                }
                contadorAlta = 0; //reseta o contador
            }//enquanto o contador for maior ou igual a 5, executar os códigos ^acima^

            if (atual == null) { // se ainda não foi selecionado nenhum processo para execução, executar:
                if (!alta.isEmpty()) {
                    atual = alta.remover(); // se a fila de alta prioridade não está vazia, pega um processo da fila de prioridade 1
                    contadorAlta++; //adiciona ao contador +1
                } else if (!media.isEmpty()) {
                    atual = media.remover(); // pega um processo da fila de média
                } else if (!baixa.isEmpty()) {
                    atual = baixa.remover(); // pega um processo da fila de baixa
                }
            }

            if (atual == null) continue; // se não encontrou nenhum processo, passa para a próxima iteração

            if (atual.getRecurso_necessario().equalsIgnoreCase("Disco") && !atual.isJaBloqueado()) {
                System.out.println("[BLOQUEADO] " + atual.getNome() + " precisa de Disco.");
                System.out.println(atual);
                atual.setJaBloqueado(true);
                bloqueados.adicionar(atual);
                continue;
            } // verifica se o processo precisa de recurso (Disco) e ainda não foi bloqueado

            atual.reduzirCiclo(); // Executa 1 ciclo do processo
            System.out.println("[EXECUTANDO] " + atual);

            if (atual.getCiclos_necessarios() > 0) {
                if (atual.getPrioridade() == 1) alta.adicionar(atual);
                else if (atual.getPrioridade() == 2) media.adicionar(atual);
                else baixa.adicionar(atual);
            }// se ainda restam ciclos, reenvia o processo para a fila correspondente
            else {
                System.out.println("[FINALIZADO] " + atual.getNome());
                System.out.println(atual);
            }//se não, finaliza o código.
        }

        System.out.println("\n--- Simulação Encerrada ---");
    }
}
