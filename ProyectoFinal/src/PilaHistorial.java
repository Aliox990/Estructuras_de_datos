
/**
 * Pila (LIFO) que almacena el historial de reproducción para poder volver a
 * la canción anterior.
 * @author Christian Alonso Arevalos Gonzalez y Cesar de Jesus Becerra Vera
 * @version 1.0
 * @since 1.0
 */
public class PilaHistorial {
    // Apuntador principal que siempre apunta el último elemento insertado
    private Cancion cima;

    /**
     * Crea una pila de historial vacía.
     */
    public PilaHistorial() {
        this.cima = null;
    }

    /**
     * Operación Push (Apilar): Guarda una canción que acaba de ser reproducida
     *
     * @param titulo título de la canción
     * @param artista artista de la canción
     * @param rutaAudio ruta al archivo de audio
     * @param rutaImagen ruta a la imagen de portada
     */
    public void apilar(String titulo, String artista, String rutaAudio, String rutaImagen) {
        // Crea un nodo independiente para proteger la Playlist original
        Cancion nuevaCancion = new Cancion(titulo, artista, rutaAudio, rutaImagen);
        // Si la pila está vacía, el nuevo nodo es directamente la cima
        if (estaVacia()) {
            cima = nuevaCancion;
        } 
        // Si ya hay historial, el nuevo nodo se coloca encima del anterior
        else {
            // El nuevo nodo apunta hacia abajo (al que antes era la cima)
            nuevaCancion.setSiguiente(cima);
            // Actualiza la cima para que sea el nuevo nodo
            cima = nuevaCancion;
        }
    }

    /**
     * Operación Pop (Desapilar): Saca la última canción del historial para volver a escucharla
     * @return El nodo de la canción anterior
     */
    public Cancion desapilar() {
        // Si no hay historial, no se puede regresar
        if (estaVacia()) {
            return null;
        }
        // Trae el nodo que está en la cima antes de borrarlo de la pila
        Cancion cancionSaliendo = cima;
        // La nueva cima baja un nivel (se convierte en el elemento que estaba justo debajo)
        cima = cima.getSiguiente();
        // Limpia los apuntadores del nodo que sale para dejarlo completamente aislado
        cancionSaliendo.setSiguiente(null);
        cancionSaliendo.setAnterior(null);
        return cancionSaliendo;
    }

    /**
     * Verifica si el historial está vacío
     * @return {@code true} si no hay elementos en la pila
     */
    public boolean estaVacia() {
        return cima == null;
    }
}