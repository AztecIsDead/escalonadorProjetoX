public class Main {
    public static void main(String[] args) {
        Escalonador scheduler = new Escalonador();

   scheduler.carregarDeCsv("processos.csv");

scheduler.executarCiclos();
    }
}
