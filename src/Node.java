public class Node {
    Processo processo;
    Node next;

    public Node(Processo processo){
        this.processo = processo;
        this.next = null;
    }
}
//classe de node para a lista encadeada de processos