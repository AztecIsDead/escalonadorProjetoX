import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;

public class Escalonador {
    private ListaDeProcessos alta = new ListaDeProcessos();//cria uma lista de processos nova a partir da String: alta
    private ListaDeProcessos media = new ListaDeProcessos();//cria uma lista de processos nova a partir da String: media
    private ListaDeProcessos baixa = new ListaDeProcessos();//cria uma lista de processos nova a partir da String: baixa
    private ListaDeProcessos bloqueados = new ListaDeProcessos();//cria uma lista de processos nova a partir da String: bloqueados

    private int contadorAlta = 0; //cria o Integer contadorAlta para usar no IF para a cada 5 de alta, 1 de media.

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
                String recurso = (dados.length > 4 && dados[4] != null) ? dados[4].trim() : ""; // Isso impede que haja erro caso o campo de "Recurso" esteja nulo.

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
                        baixa.adicionar(p);//registrar como prioridade BAIXA caso nao identifique o input
                        break;
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar CSV: " + e.getMessage()); //mensagem de erro caso algo esteja errado com o arquivo
        }
    }

    //metodo de execucao dos processos
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

            Processo atual = null;  // cria uma variavel para armazenar o processo

            if (contadorAlta >= 5) {
                if (!media.isEmpty()) {
                    atual = media.remover();  // se a fila de prioridade media nao esta vazia, pega um processo da fila de prioridade 2
                } else if (!baixa.isEmpty()) {
                    atual = baixa.remover();// se a fila de prioridade baixa nao esta vazia, pega um processo da fila de prioridade 3
                }
                contadorAlta = 0; //reseta o contador
            }//enquanto o contador for maior ou igual a 5, executar os codigos ^acima^

            if (atual == null) { // se ainda nao foi selecionado nenhum processo para execucao, executar:
                if (!alta.isEmpty()) {
                    atual = alta.remover(); // se a fila de alta prioridade nao esta vazia, pega um processo da fila de prioridade 1
                    contadorAlta++; //adiciona ao contador +1
                } else if (!media.isEmpty()) {
                    atual = media.remover(); // pega um processo da fila de media
                } else if (!baixa.isEmpty()) {
                    atual = baixa.remover(); // pega um processo da fila de baixa
                }
            }

            if (atual == null) continue; // se nao encontrou nenhum processo, passa para a proxima iteracao

            if (atual.getRecurso_necessario().equalsIgnoreCase("Disco") && !atual.isJaBloqueado()) {
                System.out.println("[BLOQUEADO] " + atual.getNome() + " precisa de Disco.");
                System.out.println(atual);
                atual.setJaBloqueado(true);
                bloqueados.adicionar(atual);
                continue;
            } // verifica se o processo precisa de recurso (DISCO) e ainda nao foi bloqueado

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
            }//se nao, finaliza o codigo.
        }

        System.out.println("\n--- Simulação Encerrada ---");
    }
}
