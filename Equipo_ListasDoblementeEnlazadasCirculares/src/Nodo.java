/**
 *
 * @author Alonso
 */
public class Nodo {

    Nodo anterior, siguiente;
    String Valor;
    
    public Nodo(Nodo anterior, Nodo siguiente, String Valor) {
        this.anterior = anterior;
        this.siguiente = siguiente;
        this.Valor = Valor;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String imagen) {
        this.Valor = imagen;
    }

    public Nodo getAnterior() {
        return anterior;
    }

    public void setAnterior(Nodo anterior) {
        this.anterior = anterior;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
    
    
    
}
