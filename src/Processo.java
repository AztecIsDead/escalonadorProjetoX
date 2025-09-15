public class Processo {
    private int id;
    private String nome;
    private int prioridade;
    private int ciclos_necessarios;
    private String recurso_necessario;
    private boolean jaBloqueado = false;

    public boolean isJaBloqueado() {
        return jaBloqueado;
    }

    public void setJaBloqueado(boolean valor) {
        this.jaBloqueado = valor;
    }


    public Processo(int id, String nome, int prioridade, int ciclos_necessarios, String recurso_necessario){
        this.id = id;
        this.nome = nome;
        this.prioridade = prioridade;
        this.ciclos_necessarios = ciclos_necessarios;
        this.recurso_necessario = recurso_necessario;
    }

    public int getId(){ return id; }
    public String getNome(){ return nome; }
    public int getPrioridade(){ return prioridade; }
    public int getCiclos_necessarios(){ return ciclos_necessarios; }
    public void reduzirCiclo() { this.ciclos_necessarios--; }
    public String getRecurso_necessario(){ return recurso_necessario; }

    @Override
    public String toString() {
        return "Processo {Id: " + id + ", Nome: " + nome +
                ", Prioridade: " + prioridade +
                ", Ciclos: " + ciclos_necessarios +
                ", Recurso: " + recurso_necessario + "}";
    }
}
