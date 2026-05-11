package p14_arbolbinariodebusquedaavl;

/**
 * Clase que representa un nodo en el Árbol Binario de Búsqueda AVL.
 * Cada nodo contiene un dato entero y referencias a sus nodos hijo izquierdo,
 * hijo derecho y nodo padre. Esta estructura permite la organización jerárquica
 * de los datos en el árbol.
 * @author Alonso y Cesar
 * @version 1.0
 * @since 2026
 */
public class Nodo {
    private int dato;      // Valor almacenado en el nodo
    private Nodo izdo;      // Referencia al nodo hijo izquierdo
    private Nodo der;       // Referencia al nodo hijo derecho
    private Nodo raiz;      // Referencia al nodo padre
    
    /**
     * Constructor por defecto que crea un nodo vacío.
     */
    Nodo(){}

    /**
     * Constructor que inicializa un nodo con todos sus atributos.
     * @param dato El valor entero a almacenar en el nodo
     * @param izdo Referencia al nodo hijo izquierdo (puede ser null)
     * @param der Referencia al nodo hijo derecho (puede ser null)
     * @param raiz Referencia al nodo padre/raíz (puede ser null para la raíz del árbol)
     */
    public Nodo(int dato, Nodo izdo, Nodo der, Nodo raiz) {
        this.dato = dato;
        this.izdo = izdo;
        this.der = der;
        this.raiz = raiz;
    }

    /**
     * Obtiene el valor almacenado en el nodo.
     * @return El valor entero del nodo
     */
    public int getDato() {
        return dato;
    }

    /**
     * Establece el valor del nodo.
     * @param dato El nuevo valor entero a almacenar
     */
    public void setDato(int dato) {
        this.dato = dato;
    }

    /**
     * Obtiene el nodo hijo izquierdo.
     * @return Referencia al nodo hijo izquierdo, o null si no existe
     */
    public Nodo getIzdo() {
        return izdo;
    }

    /**
     * Establece el nodo hijo izquierdo.
     * @param izdo Referencia al nuevo nodo hijo izquierdo
     */
    public void setIzdo(Nodo izdo) {
        this.izdo = izdo;
    }

    /**
     * Obtiene el nodo hijo derecho.
     * @return Referencia al nodo hijo derecho, o null si no existe
     */
    public Nodo getDer() {
        return der;
    }

    /**
     * Establece el nodo hijo derecho.
     * @param der Referencia al nuevo nodo hijo derecho
     */
    public void setDer(Nodo der) {
        this.der = der;
    }

    /**
     * Obtiene el nodo padre/raíz.
     * @return Referencia al nodo padre, o null si es la raíz del árbol
     */
    public Nodo getRaiz() {
        return raiz;
    }

    /**
     * Establece el nodo padre/raíz.
     * @param raiz Referencia al nuevo nodo padre
     */
    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }
}
