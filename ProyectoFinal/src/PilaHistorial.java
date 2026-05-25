/**
 * Pila (Stack) para gestionar el historial de reproducción (LIFO)
 */
public class PilaHistorial {
    
    // Apuntador principal que siempre apunta el último elemento insertado
    private Cancion cima;

    public PilaHistorial() {
        this.cima = null;
    }

    /**
     * Operación Push (Apilar): Guarda una canción que acaba de ser reproducida
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
     */
    public boolean estaVacia() {
        return cima == null;
    }
}