public class EscalonadorClasses{
class Node{
String processo;
Node proximo;
int fila;
}
class Line{
    Node head;
    Node tail;

public void LiningList(String processo) {
    Node no = new Node();
    no.processo = processo;
    if (head == null){
        this.head = no;
        this.tail = no;
    }else{
        tail.proximo = no;
        tail = no;
    }
}//lininglist
public String Unlist(String processo) {
    if (head == null) {
        return null;
    }
    String x = head.processo;
    head = head.proximo;
    if (head == null) {
        tail = null;
    }
        return x;

}//unlist
public boolean isEmpty(){
    return head == null;
    }
    }//class line
    }//esc classes






