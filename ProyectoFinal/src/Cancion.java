/**
 * Representa un nodo que contiene los datos de una canción.
 * <p>Se usa como elemento de la {@link ListaPlaylist}, {@link ColaReproduccion}
 * y {@link PilaHistorial} para almacenar metadatos básicos de la pista.
 * @author Christian Alonso Arevalos Gonzalez y Cesar de Jesus Becerra Vera
 * @version 1.0
 * @since 1.0
 */

public class Cancion {
    private String titulo;
    private String artista;
    private String rutaAudio;
    private String rutaImagen;
    // Apuntadores
    private Cancion siguiente;
    private Cancion anterior;

    /**
     * Constructor para inicializar una nueva cancion.
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

    /**
     * Obtiene el título de la canción.
     * @return título de la canción
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título de la canción.
     * @param titulo nuevo título
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene el artista de la canción.
     * @return nombre del artista
     */
    public String getArtista() {
        return artista;
    }

    /**
     * Establece el artista de la canción.
     * @param artista nombre del artista
     */
    public void setArtista(String artista) {
        this.artista = artista;
    }

    /**
     * Obtiene la ruta al archivo de audio.
     * @return ruta del audio
     */
    public String getRutaAudio() {
        return rutaAudio;
    }

    /**
     * Establece la ruta del archivo de audio.
     * @param rutaAudio ruta del archivo
     */
    public void setRutaAudio(String rutaAudio) {
        this.rutaAudio = rutaAudio;
    }

    /**
     * Obtiene la ruta de la imagen de portada.
     * @return ruta de la imagen
     */
    public String getRutaImagen() {
        return rutaImagen;
    }

    /**
     * Establece la ruta de la imagen de portada.
     * @param rutaImagen ruta de la imagen
     */
    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    /**
     * Obtiene la canción siguiente en la estructura (si existe).
     * @return referencia al siguiente nodo o {@code null}
     */
    public Cancion getSiguiente() {
        return siguiente;
    }

    /**
     * Establece la referencia al siguiente nodo.
     * @param siguiente nodo siguiente
     */
    public void setSiguiente(Cancion siguiente) {
        this.siguiente = siguiente;
    }

    /**
     * Obtiene la canción anterior en la estructura (si existe).
     * @return referencia al nodo anterior o {@code null}
     */
    public Cancion getAnterior() {
        return anterior;
    }

    /**
     * Establece la referencia al nodo anterior.
     * @param anterior nodo anterior
     */
    public void setAnterior(Cancion anterior) {
        this.anterior = anterior;
    }
}