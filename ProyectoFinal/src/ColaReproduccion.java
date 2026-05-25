/**
 * @author Alonso
 */
public class ColaReproduccion {
    // Apuntadores
    private Cancion frente;
    private Cancion fin;

    public ColaReproduccion() {
        this.frente = null;
        this.fin = null;
    }

    /**
     * Operación Enqueue: Inserta una canción al final de la cola
     */
    public void encolar(String titulo, String artista, String rutaAudio, String rutaImagen) {
        // Crea un nodo completamente nuevo e independiente para no romper la playlist
        Cancion nuevoNodo = new Cancion(titulo, artista, rutaAudio, rutaImagen);
        
        // Si la cola está vacía, el nuevo nodo es tanto el frente como el fin
        if (estaVacia()) {
            frente = nuevoNodo;
            fin = nuevoNodo;
        } 
        // Si ya hay elementos, se forma al final
        else {
            fin.setSiguiente(nuevoNodo);
            nuevoNodo.setAnterior(fin); // Mantener el enlace doble por comodidad
            fin = nuevoNodo;
        }
    }

    /**
     * Operación Dequeue: Saca y regresa la canción que está al frente de la cola
     * @return El nodo de la cancion que debe sonar a continuacion
     */
    public Cancion desencolar() {
        if (estaVacia()) {
            return null;
        }
        
        // Guarda una referencia a la cancion del frente para regresarla
        Cancion cancionSaliendo = frente;
        
        // Mover el frente a la siguiente cancion de la fila
        frente = frente.getSiguiente();
        
        // Si la cola se quedó vacía tras el movimiento, el fin también debe ser null
        if (frente == null) {
            fin = null;
        } else {
            frente.setAnterior(null); // Romper el enlace con la cancion que va saliendo
        }
        
        // Limpia los apuntadores del nodo que sale por seguridad
        cancionSaliendo.setSiguiente(null);
        cancionSaliendo.setAnterior(null);
        
        return cancionSaliendo;
    }
    
    /**
     * Operación especial de Bicola (Deque): Inserta una canción al frente de la fila.
     * Se usa para devolver la canción interrumpida cuando el usuario presiona "Anterior".
     */
    public void devolverAlFrente(String titulo, String artista, String rutaAudio, String rutaImagen) {
        // Creamos la instancia independiente
        Cancion nodoDevuelto = new Cancion(titulo, artista, rutaAudio, rutaImagen);
        
        // Si la cola estaba vacía, funciona igual que una inserción normal
        if (estaVacia()) {
            frente = nodoDevuelto;
            fin = nodoDevuelto;
        } 
        // Si hay fila, nos metemos abusivamente al primer lugar
        else {
            // El nuevo nodo apunta hacia adelante al que antes era el primero
            nodoDevuelto.setSiguiente(frente);
            // El que era primero ahora apunta hacia atrás al nuevo nodo
            frente.setAnterior(nodoDevuelto);
            // Actualizamos la referencia oficial del frente
            frente = nodoDevuelto;
        }
    }
    
    /**
     * Busca una canción por título en la cola de espera y la desconecta de la estructura
     */
    public boolean eliminarPorTitulo(String titulo) {
        Cancion actual = frente;
        
        while (actual != null) {
            if (actual.getTitulo().equalsIgnoreCase(titulo)) {
                // Es el primer elemento de la cola
                if (actual == frente) {
                    frente = actual.getSiguiente();
                    if (frente != null) {
                        frente.setAnterior(null);
                    } else {
                        fin = null; // La cola se quedó vacía
                    }
                } 
                // Es el último elemento de la cola
                else if (actual == fin) {
                    fin = actual.getAnterior();
                    if (fin != null) {
                        fin.setSiguiente(null);
                    } else {
                        frente = null;
                    }
                } 
                // Está en medio de otros dos nodos
                else {
                    actual.getAnterior().setSiguiente(actual.getSiguiente());
                    actual.getSiguiente().setAnterior(actual.getAnterior());
                }
                return true; // Se encontró y eliminó exitosamente
            }
            actual = actual.getSiguiente(); // Avanza en la fila
        }
        return false; // No se encontró coincidencia
    }

    /**
     * Verifica si no hay canciones en espera
     */
    public boolean estaVacia() {
        return frente == null;
    }

    // Getter para poder listar la cola en la interfaz grafica
    public Cancion getFrente() {
        return frente;
    }
}
