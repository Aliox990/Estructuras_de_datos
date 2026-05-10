package p14_arbolbinariodebusquedaavl;

/**
 *
 * @author Alonso
 */
public class Nodo {
    
    private int dato;
    private Nodo izdo, der, raiz;
    Nodo(){}

    /**
     * @param dato Dato de el nodo
     * @param izdo Hijo izquierdo
     * @param der Hijo derecho
     * @param raiz Raiz del nodo
     */
    public Nodo(int dato, Nodo izdo, Nodo der, Nodo raiz) {
        this.dato = dato;
        this.izdo = izdo;
        this.der = der;
        this.raiz = raiz;
    }

    public int getDato() {
        return dato;
    }

    public void setDato(int dato) {
        this.dato = dato;
    }

    public Nodo getIzdo() {
        return izdo;
    }

    public void setIzdo(Nodo izdo) {
        this.izdo = izdo;
    }

    public Nodo getDer() {
        return der;
    }

    public void setDer(Nodo der) {
        this.der = der;
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }
    
    
}
