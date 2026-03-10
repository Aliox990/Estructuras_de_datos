/**
 *
 * @author Alonso
 */
public class Nodo {
    String melodia, imagen;
    Nodo Siguiente; 

    public Nodo(String melodia, String imagen, Nodo Siguiente) {
        this.melodia = melodia;
        this.imagen = imagen;
        this.Siguiente = Siguiente;
    }
    
    public String getMelodia() {
        return melodia;
    }

    public Nodo getSiguiente() {
        return Siguiente;
    }

    public void setMelodia(String melodia) {
        this.melodia = melodia;
    }

    public void setApuntador(Nodo Siguiente) {
        this.Siguiente = Siguiente;
    }

    public void setSiguiente(Nodo Siguiente) {
        this.Siguiente = Siguiente;
    }
    
    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
