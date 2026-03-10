package pista_4;

import java.util.Scanner;

class Nodo {
    int valor;
    Nodo siguiente;

    public Nodo(int valor) {
        this.valor = valor;
        this.siguiente = null;
    }
}

class ListaEnlazada {
    Nodo cabeza;

    public void insertarOrdenado(int valor) {
        Nodo nuevoNodo = new Nodo(valor);

        if (cabeza == null || cabeza.valor >= nuevoNodo.valor) {
            nuevoNodo.siguiente = cabeza;
            cabeza = nuevoNodo;
        } else {
            Nodo actual = cabeza;
            
            while (actual.siguiente != null && actual.siguiente.valor < nuevoNodo.valor) {
                actual = actual.siguiente;
            }
            
            nuevoNodo.siguiente = actual.siguiente;
            actual.siguiente = nuevoNodo;
        }
    }

    public void mostrar() {
        Nodo actual = cabeza;
        while (actual != null) {
            System.out.print(actual.valor + " -> ");
            actual = actual.siguiente;
        }
        System.out.println("null");
    }
}

public class Pista_3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ListaEnlazada lista = new ListaEnlazada();
        int valor;

        System.out.println("Ingresa números para agregar a la lista (escribe -1 para terminar):");

        while (true) {
            System.out.print("Valor: ");
            valor = scanner.nextInt();

            if (valor == -1) {
                System.out.println("Programa terminado.");
                break;
            }

            lista.insertarOrdenado(valor);
            
            System.out.print("Lista actual: ");
            lista.mostrar();
            System.out.println("-------------------------");
        }

        scanner.close();
    }
}