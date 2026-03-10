package pista_3;

class NodoDoble {
    int valor;
    NodoDoble siguiente;
    NodoDoble anterior;

    public NodoDoble(int valor) {
        this.valor = valor;
        this.siguiente = null;
        this.anterior = null;
    }
}

class ListaDoble {
    NodoDoble cabeza;
    NodoDoble cola;

    public void insertar(int valor) {
        NodoDoble nuevoNodo = new NodoDoble(valor);

        if (cabeza == null) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            cola.siguiente = nuevoNodo;
            nuevoNodo.anterior = cola;
            cola = nuevoNodo;
        }
    }

    public void eliminar(int valor) {
        if (cabeza == null) {
            return;
        }

        NodoDoble actual = cabeza;

        while (actual != null && actual.valor != valor) {
            actual = actual.siguiente;
        }

        if (actual == null) {
            return;
        }

        if (actual.anterior != null) {
            actual.anterior.siguiente = actual.siguiente;
        } else {
            cabeza = actual.siguiente;
        }

        if (actual.siguiente != null) {
            actual.siguiente.anterior = actual.anterior;
        } else {
            cola = actual.anterior;
        }
    }

    public void mostrar() {
        NodoDoble actual = cabeza;
        while (actual != null) {
            System.out.print(actual.valor);
            if (actual.siguiente != null) {
                System.out.print(" "); 
            }
            actual = actual.siguiente;
        }
        System.out.println();
    }
}

public class Pista_4 {
    public static void main(String[] args) {
        ListaDoble lista = new ListaDoble();
        
        lista.insertar(10);
        lista.insertar(20);
        lista.insertar(30);
        lista.insertar(40);

        System.out.println("Lista original:");
        lista.mostrar();

        System.out.println("\nEliminando el valor 30...");
        lista.eliminar(30);
        
        System.out.println("Lista actualizada:");
        lista.mostrar();
    }
}