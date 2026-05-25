/**
 * Estructura de Datos: Lista Doblemente Enlazada para gestionar las canciones
 */
public class ListaPlaylist {
    
    // Apuntadores principales de la lista doble
    private Cancion inicio;
    private Cancion fin;

    public ListaPlaylist() {
        this.inicio = null;
        this.fin = null;
    }

    /**
     * Método para insertar una nueva canción al final de la lista
     */
    public void insertarAlFinal(String titulo, String artista, String rutaAudio, String rutaImagen) {
        Cancion nuevaCancion = new Cancion(titulo, artista, rutaAudio, rutaImagen);
        
        // Si la lista está vacía, el nuevo nodo es tanto el inicio como el fin
        if (inicio == null) {
            inicio = nuevaCancion;
            fin = nuevaCancion;
        } 
        // Si ya hay elementos, lo agrega al final
        else {
            // El fin actual se conecta hacia adelante con la nueva canción
            fin.setSiguiente(nuevaCancion);
            // La nueva canción se conecta hacia atrás con el viejo fin
            nuevaCancion.setAnterior(fin);
            // El apuntador 'fin' se mueve y ahora es la nueva canción
            fin = nuevaCancion;
        }
    }

    /**
     * Recorre la lista doble de principio a fin y actualiza la JTable visual
     */
    public void cargarEnTabla(panelPlaylist panelVisual) {
        // Limpia la tabla del panel para evitar filas duplicadas al refrescar
        panelVisual.limpiarTabla();
        
        Cancion actual = inicio;
        
        // Recorrido secuencial clásico de nodos
        while (actual != null) {
            // Envia los datos
            panelVisual.agregarCancionGraficamente(
                actual.getTitulo(), 
                actual.getArtista(), 
                actual.getRutaAudio()
            );
            // Avanza al siguiente nodo
            actual = actual.getSiguiente();
        }
    }
    
    /**
     * Recorre la lista buscando un nodo por su título exacto
     */
    public Cancion buscarPorTitulo(String titulo) {
        Cancion actual = inicio;
        
        while (actual != null) {
            if (actual.getTitulo().equalsIgnoreCase(titulo)) {
                return actual; // Retorna el nodo completo con todos sus datos
            }
            actual = actual.getSiguiente();
        }
        
        return null; // Si termina de buscar y no hay coincidencias
    }
    
    public boolean eliminarPorTitulo(String titulo) {
        Cancion actual = inicio;

        while (actual != null) {
            if (actual.getTitulo().equalsIgnoreCase(titulo)) {
                // Es el único elemento o el primero de la lista
                if (actual == inicio) {
                    inicio = actual.getSiguiente();
                    if (inicio != null) {
                        inicio.setAnterior(null);
                    } else {
                        fin = null; // La lista quedó vacía
                    }
                } 
                // Es el último elemento de la lista
                else if (actual == fin) {
                    fin = actual.getAnterior();
                    if (fin != null) {
                        fin.setSiguiente(null);
                    } else {
                        inicio = null;
                    }
                } 
                // El nodo se encuentra en medio de otros dos
                else {
                    actual.getAnterior().setSiguiente(actual.getSiguiente());
                    actual.getSiguiente().setAnterior(actual.getAnterior());
                }
                return true; // Eliminación exitosa en memoria
            }
            actual = actual.getSiguiente();
        }
        return false; // No se encontró la canción
    }

    public void vaciarLista() {
        inicio = null;
        fin = null;
    }

    // Métodos de acceso para que el reproductor sepa dónde empezar a sonar
    public Cancion getInicio() {
        return inicio;
    }

    public Cancion getFin() {
        return fin;
    }
}