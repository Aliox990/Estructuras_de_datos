
import java.util.Date;

/**
 * Representa un nodo en una lista doblemente enlazada que almacena información de imágenes.
 * Cada nodo contiene referencias a los nodos anterior y siguiente, así como información
 * de la imagen (nombre, descripción, fecha y ruta).
 * 
 * @author Cesar de Jesus Becerra Vera
 * @author Christian Alonso Arevalos Gonzalez
 * @version 1.0
 * @since 2026-03-10
 */
public class Nodo {

    /** Referencia al nodo anterior en la lista */
    Nodo anterior;
    
    /** Referencia al nodo siguiente en la lista */
    Nodo siguiente;
    
    /** Nombre de la imagen */
    String nombre;
    
    /** Descripción de la imagen */
    String descripcion;
    
    /** Fecha de registro de la imagen */
    String fecha;
    
    /** Ruta del archivo de la imagen */
    String imagen;
    
    /**
     * Constructor que crea un nuevo nodo con la información de una imagen.
     * 
     * @param anterior Referencia al nodo anterior en la lista
     * @param siguiente Referencia al nodo siguiente en la lista
     * @param nombre Nombre de la imagen
     * @param descripcion Descripción de la imagen
     * @param fecha Fecha de registro
     * @param imagen Ruta del archivo de la imagen
     */
    public Nodo(Nodo anterior, Nodo siguiente, String nombre, String descripcion, String fecha, String imagen) {
        this.anterior = anterior;
        this.siguiente = siguiente;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.imagen = imagen;
    }

    /**
     * Obtiene la ruta del archivo de la imagen.
     * 
     * @return La ruta del archivo de la imagen
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Establece la ruta del archivo de la imagen.
     * 
     * @param imagen La nueva ruta del archivo de la imagen
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * Obtiene la referencia al nodo anterior en la lista.
     * 
     * @return El nodo anterior
     */
    public Nodo getAnterior() {
        return anterior;
    }

    /**
     * Establece la referencia al nodo anterior en la lista.
     * 
     * @param anterior El nuevo nodo anterior
     */
    public void setAnterior(Nodo anterior) {
        this.anterior = anterior;
    }

    /**
     * Obtiene la referencia al nodo siguiente en la lista.
     * 
     * @return El nodo siguiente
     */
    public Nodo getSiguiente() {
        return siguiente;
    }

    /**
     * Establece la referencia al nodo siguiente en la lista.
     * 
     * @param siguiente El nuevo nodo siguiente
     */
    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }

    /**
     * Obtiene el nombre de la imagen.
     * 
     * @return El nombre de la imagen
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la imagen.
     * 
     * @param nombre El nuevo nombre de la imagen
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción de la imagen.
     * 
     * @return La descripción de la imagen
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la imagen.
     * 
     * @param descripcion La nueva descripción de la imagen
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la fecha de registro de la imagen.
     * 
     * @return La fecha de registro
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * Establece la fecha de registro de la imagen.
     * 
     * @param fecha La nueva fecha de registro
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
