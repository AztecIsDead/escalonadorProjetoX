import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;

public class Escalonador {
    private ListaDeProcessos alta = new ListaDeProcessos();
    private ListaDeProcessos media = new ListaDeProcessos();
    private ListaDeProcessos baixa = new ListaDeProcessos();
    private ListaDeProcessos bloqueados = new ListaDeProcessos();

    private int contadorAlta = 0;

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

                Processo p = new Processo(id, nome, prioridade, ciclos, recurso);

                if (prioridade == 1) alta.adicionar(p);
                else if (prioridade == 2) media.adicionar(p);
                else baixa.adicionar(p);
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
            }

            Processo atual = null;

            if (contadorAlta >= 5) {
                if (!media.isEmpty()) {
                    atual = media.remover();
                } else if (!baixa.isEmpty()) {
                    atual = baixa.remover();
                }
                contadorAlta = 0;
            }

            if (atual == null) {
                if (!alta.isEmpty()) {
                    atual = alta.remover();
                    contadorAlta++;
                } else if (!media.isEmpty()) {
                    atual = media.remover();
                } else if (!baixa.isEmpty()) {
                    atual = baixa.remover();
                }
            }

            if (atual == null) continue;

            if (atual.getRecurso_necessario().equalsIgnoreCase("Disco") && !atual.isJaBloqueado()) {
                System.out.println("[BLOQUEADO] " + atual.getNome() + " precisa de Disco.");
                System.out.println(atual);
                atual.setJaBloqueado(true);
                bloqueados.adicionar(atual);
                continue;
            }

            atual.reduzirCiclo();
            System.out.println("[EXECUTANDO] " + atual);

            if (atual.getCiclos_necessarios() > 0) {
                if (atual.getPrioridade() == 1) alta.adicionar(atual);
                else if (atual.getPrioridade() == 2) media.adicionar(atual);
                else baixa.adicionar(atual);
            } else {
                System.out.println("[FINALIZADO] " + atual.getNome());
                System.out.println(atual);
            }
        }

        System.out.println("\n--- Simulação Encerrada ---");
    }
}
