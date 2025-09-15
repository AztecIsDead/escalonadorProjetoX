public class ListaDeProcessos {
    private Node head;
    private Node tail;

    public ListaDeProcessos() {
        this.head = null;
        this.tail = null;
    }

    public void adicionar(Processo processo) {
        Node node = new Node(processo);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
    }

    public Processo remover() {
        if (head == null) return null;
        Processo p = head.processo;
        head = head.next;
        if (head == null) tail = null;
        return p;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void exibir() {
        Node temp = head;
        while (temp != null) {
            System.out.println(temp.processo);
            temp = temp.next;
        }
    }
}

