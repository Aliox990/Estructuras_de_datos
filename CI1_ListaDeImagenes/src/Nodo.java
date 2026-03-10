
import java.util.Date;

/**
 *
 * @author Alonso
 */
public class Nodo {

    Nodo anterior, siguiente;
    String nombre, descripcion, fecha, imagen;
    
    public Nodo(Nodo anterior, Nodo siguiente, String nombre, String descripcion, String fecha, String imagen) {
        this.anterior = anterior;
        this.siguiente = siguiente;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.imagen = imagen;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
