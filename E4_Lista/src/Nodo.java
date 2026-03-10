/**
 *
 * @author Alonso
 */
public class Nodo {
    
    int info; //Atributo para guardadar datos enteros en la lista
    Nodo Siguiente; 


    public Nodo(int info, Nodo Siguiente) {
        this.info = info;
        this.Siguiente = Siguiente;
    }
    
    public int getInfo() {
        return info;
    }

    public Nodo getSiguiente() {
        return Siguiente;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public void setApuntador(Nodo Siguiente) {
        this.Siguiente = Siguiente;
    }

    public void setSiguiente(Nodo Siguiente) {
        this.Siguiente = Siguiente;
    }
    
    
}
