/**
 * Representa un Nodo dentro de la Lista Enlazada y la Cola de Reproduccion
 */
public class Cancion {
    
    private String titulo;
    private String artista;
    private String rutaAudio;
    private String rutaImagen;
    
    //Apuntadores
    private Cancion siguiente;
    private Cancion anterior;

    /**
     * Constructor para inicializar una nueva cancion
     * @param titulo Nombre de la cancion
     * @param artista Nombre del artista de la cancion
     * @param rutaAudio Ruta del audio de la cancion
     * @param rutaImagen Ruta de la imagen de la cancion
     */
    public Cancion(String titulo, String artista, String rutaAudio, String rutaImagen) {
        this.titulo = titulo;
        this.artista = artista;
        this.rutaAudio = rutaAudio;
        this.rutaImagen = rutaImagen;
        this.siguiente = null; // Por defecto, una nueva cancion no apunta a nada
        this.anterior = null;
    }

    // GETTERS Y SETTERS

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getRutaAudio() {
        return rutaAudio;
    }

    public void setRutaAudio(String rutaAudio) {
        this.rutaAudio = rutaAudio;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public Cancion getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Cancion siguiente) {
        this.siguiente = siguiente;
    }

    public Cancion getAnterior() {
        return anterior;
    }

    public void setAnterior(Cancion anterior) {
        this.anterior = anterior;
    }
    
    
}