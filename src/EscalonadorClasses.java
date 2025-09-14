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
public String Unlist() {
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
    Line high = new Line();
    Line medium = new Line();
    Line low = new Line();
    public void exe() {
        int counter = 0;
        while (!high.isEmpty() || !medium.isEmpty() || !low.isEmpty()) {
            for(int i = 0; i < 5 && high.isEmpty(); i++){
                System.out.println("Alta prioridade em execução: " + high.Unlist());
            counter++;
            if(medium.isEmpty()){
                System.out.println(("Media prioridade em execução: " + medium.Unlist()));
            }
            if(high.isEmpty() || medium.isEmpty() || !low.isEmpty()){
                System.out.println("Baixa prioridade em execução: " +low.Unlist());
            }
            }
        }//while
    }//exe
    }//esc classes






