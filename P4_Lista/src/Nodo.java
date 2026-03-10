/**
 *
 * @author Alonso
 */
public class Nodo {
    
    int duracion;
    String nombreCancion, nombreArtista, genero;
    Nodo Siguiente; 


    public Nodo(String nombreCancion, Nodo Siguiente) {
        this.Siguiente = Siguiente;
        this.nombreCancion = nombreCancion;
    }

    public Nodo(int duracion, String nombreCancion, String nombreArtista, String genero, Nodo Siguiente) {
        this.duracion = duracion;
        this.nombreCancion = nombreCancion;
        this.nombreArtista = nombreArtista;
        this.genero = genero;
        this.Siguiente = Siguiente;
    }

    public Nodo getSiguiente() {
        return Siguiente;
    }

    public void setApuntador(Nodo Siguiente) {
        this.Siguiente = Siguiente;
    }

    public void setSiguiente(Nodo Siguiente) {
        this.Siguiente = Siguiente;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getNombreCancion() {
        return nombreCancion;
    }

    public void setNombreCancion(String nombreCancion) {
        this.nombreCancion = nombreCancion;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    
}
