/**
 *
 * @author Alonso
 */
public class Nodo {
    Nodo Anterior, Siguiente;
    String URL;

    public Nodo(Nodo Anterior, Nodo Siguiente, String URL) {
        this.Anterior = Anterior;
        this.Siguiente = Siguiente;
        this.URL = URL;
    }

    public Nodo(String URL) {
        this.URL = URL;
    }

    public Nodo getAnterior() {
        return Anterior;
    }

    public void setAnterior(Nodo Anterior) {
        this.Anterior = Anterior;
    }

    public Nodo getSiguiente() {
        return Siguiente;
    }

    public void setSiguiente(Nodo Siguiente) {
        this.Siguiente = Siguiente;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
    
    
}
