public class ListaDeProcessos {
    private Node head;

    public ListaDeProcessos() {
        this.head = null;
    }

    public void adicionar(Processo processo) {
        Node node = new Node(processo);
        if (head == null) {
            node = head;
        } else {
            Node temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = node;
        }
    }

    public void remover(int id) {
        if (head == null) {
            System.out.println("A lista está vazia!");
            return;
        }
        else if (head.processo.getId() == id) {
            head = head.next;
            System.out.println("Processo Removido. id: " + id);
            return;
        }
        Node temp = head;
        while (temp.next != null) {
            if (temp.next.processo.getId() == id){
                temp.next = temp.next.next;
                System.out.println("Processo Removido. id: " + id);
                return;
            }
        }
        System.out.println("O Processo com id:" + id +" não foi encontrado e não pôde ser removido.");
    }

    public Processo busca(int id){
        Node temp = head;
        while (temp != null){
            if (temp.processo.getId() == id){
                return temp.processo;
            }
            temp = temp.next;
        }
        return null;
    }

    public void exibirProcessos(){
        Node temp = head;
        while (temp != null){
            System.out.println(temp.processo);
            temp = temp.next;
        }
    }
}
