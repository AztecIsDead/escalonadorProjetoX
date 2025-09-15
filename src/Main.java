public class Main {
    public static void main(String[] args) {
        CsvProcessosInterativo.rodarEntrada();

      Escalonador scheduler = new Escalonador();

   scheduler.carregarDeCsv("processos.csv");

scheduler.executarCiclos();
    }
}
