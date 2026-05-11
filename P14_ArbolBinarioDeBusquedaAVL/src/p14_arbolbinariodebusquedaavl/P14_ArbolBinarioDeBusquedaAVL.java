package p14_arbolbinariodebusquedaavl;

import java.util.Scanner;

/**
 * Clase principal que implementa un Arbol Binario de Busqueda Auto-Balanceado (AVL).
 * Un arbol AVL es una estructura de datos que mantiene sus nodos organizados de forma
 * balanceada mediante rotaciones automaticas. Las operaciones fundamentales 
 * (insercion, eliminacion y busqueda) se realizan en tiempo O(log n).
 * El balanceo se logra mediante el Factor de Equilibrio (FE), que es la diferencia
 * entre las alturas del subárbol derecho e izquierdo. El árbol se considera balanceado
 * cuando el FE de cada nodo está entre -1 y 1.
 * Características principales:
 * - Insercion con auto-balanceo
 * - Eliminacion con auto-balanceo
 * - Busqueda en tiempo logaritmico
 * - Multiples tipos de recorrido (preorden, inorden, postorden)
 * - Visualizacion jerárquica del árbol
 * @author Alonso y Cesar
 * @version 1.0
 * @since 2026
 * @see Nodo
 */
public class P14_ArbolBinarioDeBusquedaAVL {
    /** Raíz del árbol AVL */
    static Nodo raiz;

    /**
     * Calcula la altura de un nodo recursivamente.
     * La altura se define como el número de nodos desde el nodo actual hasta la hoja más lejana.
     * Un nodo null tiene altura 0.
     * @param nodo El nodo del que se va a calcular la altura
     * @return La altura del nodo, o 0 si el nodo es null
     */
    public static int obtenerAltura(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + Math.max(obtenerAltura(nodo.getIzdo()), obtenerAltura(nodo.getDer()));
    }
    
    /**
     * Calcula el Factor de Equilibrio (FE) de un nodo.
     * El FE se define como: FE = Altura(Subárbol Derecho) - Altura(Subárbol Izquierdo)
     * <ul>
     *   <li>Si FE &gt; 1: El árbol está desequilibrado hacia la derecha</li>
     *   <li>Si FE &lt; -1: El árbol está desequilibrado hacia la izquierda</li>
     *   <li>Si -1 ≤ FE ≤ 1: El árbol está balanceado</li>
     * </ul>
     * @param nodo El nodo del que se calculará el factor de equilibrio
     * @return El factor de equilibrio del nodo, o 0 si el nodo es null
     */
    public static int obtenerFE(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return obtenerAltura(nodo.getDer()) - obtenerAltura(nodo.getIzdo());
    }
    
    /**
     * Realiza una rotación simple a la izquierda.
     * Una rotación a la izquierda se aplica cuando el árbol está desequilibrado hacia la derecha.
     * <pre>
     *       x(x)                      y(y)
     *      /    \                    /    \
     *   A(A)   y(y)     --->    x(x)     C(C)
     *          /    \         /    \
     *       T2(T2)  C(C)   A(A)    T2(T2)
     * </pre>
     * @param x El nodo superiormente desequilibrado
     * @return La nueva raíz del subárbol después de la rotación
     */
    public static Nodo rotacionIzquierda(Nodo x) {
        Nodo y = x.getDer();
        Nodo T2 = y.getIzdo();
        // Realizar rotación
        y.setIzdo(x);
        x.setDer(T2);
        // Actualizar apuntadores al padre
        y.setRaiz(x.getRaiz());
        x.setRaiz(y);
        if (T2 != null) {
            T2.setRaiz(x);
        }
        return y; // y se convierte en la nueva raíz de este subárbol
    }

    /**
     * Realiza una rotación simple a la derecha.
     * Una rotación a la derecha se aplica cuando el árbol está desequilibrado hacia la izquierda.
     * <pre>
     *           y(y)                    x(x)
     *          /    \                  /    \
     *       x(x)     C(C)    --->   A(A)    y(y)
     *      /    \                        /    \
     *   A(A)  T2(T2)                  T2(T2)  C(C)
     * </pre>
     * @param y El nodo superiormente desequilibrado
     * @return La nueva raíz del subárbol después de la rotación
     */
    public static Nodo rotacionDerecha(Nodo y) {
        Nodo x = y.getIzdo();
        Nodo T2 = x.getDer();
        // Realizar rotación
        x.setDer(y);
        y.setIzdo(T2);
        // Actualizar apuntadores al padre
        x.setRaiz(y.getRaiz());
        y.setRaiz(x);
        if (T2 != null) {
            T2.setRaiz(y);
        }
        return x; // x se convierte en la nueva raíz de este subárbol
    }
    
    /**
     * Balancea un nodo realizando las rotaciones necesarias.
     * Este método verifica el Factor de Equilibrio del nodo y aplica rotaciones simples o dobles
     * según sea necesario para mantener la propiedad AVL.
     * @param nodo El nodo a balancear
     * @return El nodo (posiblemente rotado) después del balanceo
     */
    public static Nodo balancear(Nodo nodo) {
        int fe = obtenerFE(nodo);
        // Desequilibrio hacia la Derecha (FE > 1)
        if (fe > 1) {
            // Si el hijo derecho pesa hacia la izquierda, es una Rotación Doble
            if (obtenerFE(nodo.getDer()) < 0) {
                nodo.setDer(rotacionDerecha(nodo.getDer()));
            }
            // Rotación Simple a la Izquierda
            impTxtLn("-> Balanceando (Rotacion Izquierda) en el nodo: " + nodo.getDato());
            return rotacionIzquierda(nodo);
        }
        // Desequilibrio hacia la Izquierda (FE < -1)
        if (fe < -1) {
            // Si el hijo izquierdo pesa hacia la derecha, es una Rotación Doble
            if (obtenerFE(nodo.getIzdo()) > 0) {
                nodo.setIzdo(rotacionIzquierda(nodo.getIzdo()));
            }
            // Rotación Simple a la Derecha
            impTxtLn("-> Balanceando (Rotacion Derecha) en el nodo: " + nodo.getDato());
            return rotacionDerecha(nodo);
        }
        return nodo; // Si el nodo está balanceado, se regresa tal cual
    }
    
    /**
     * Inserta un nuevo valor en el árbol AVL.
     * Llamada pública que inicializa la inserción con la raíz del árbol.
     * @param valor El valor entero a insertar
     */
    public static void insertar(int valor) {
        raiz = insertarAVL(raiz, valor, null);
    }

    /**
     * Inserta recursivamente un valor en el árbol AVL.
     * Baja por el árbol siguiendo la propiedad de búsqueda binaria hasta encontrar
     * el lugar correcto. Luego, al subir por la recursividad, realiza el balanceo necesario.
     * @param nodo El nodo actual en la recursión
     * @param valor El valor a insertar
     * @param padre El nodo padre del nodo actual
     * @return El nodo (posiblemente rotado) después de la inserción y balanceo
     */
    private static Nodo insertarAVL(Nodo nodo, int valor, Nodo padre) {
        // Caso base: Encuentra un espacio vacío
        if (nodo == null) {
            impTxtLn("Nodo insertado con exito: " + valor);
            return new Nodo(valor, null, null, padre);
        }
        // Bajar por el árbol
        if (valor < nodo.getDato()) {
            nodo.setIzdo(insertarAVL(nodo.getIzdo(), valor, nodo));
        } else if (valor > nodo.getDato()) {
            nodo.setDer(insertarAVL(nodo.getDer(), valor, nodo));
        } else {
            impTxtLn("El valor " + valor + " ya existe en el arbol.");
            return nodo;
        }
        // Al subir por la recursividad, revisa si se desbalanceó y lo arregla
        return balancear(nodo);
    }
    
    /**
     * Elimina un valor del árbol AVL.
     * Llamada pública que inicializa la eliminación con la raíz del árbol.
     * @param valor El valor entero a eliminar
     */
    public static void eliminar(int valor) {
        raiz = eliminarAVL(raiz, valor);
    }

    /**
     * Elimina recursivamente un valor del árbol AVL.
     * Busca el nodo con el valor especificado y lo elimina, manejando tres casos:
     * <ul>
     *   <li>Nodo sin hijos: Se elimina directamente</li>
     *   <li>Nodo con un hijo: El hijo toma su lugar</li>
     *   <li>Nodo con dos hijos: Se reemplaza con su sucesor inorden<li>
     * </ul>
     * Luego realiza el balanceo necesario.
     * @param nodo El nodo actual en la recursión
     * @param valor El valor a eliminar
     * @return El nodo (posiblemente rotado) después de la eliminación y balanceo
     */
    private static Nodo eliminarAVL(Nodo nodo, int valor) {
        if (nodo == null) {
            impTxtLn("Valor no encontrado en el arbol.");
            return nodo;
        }
        // Bajar por el árbol buscando el valor
        if (valor < nodo.getDato()) {
            nodo.setIzdo(eliminarAVL(nodo.getIzdo(), valor));
        } else if (valor > nodo.getDato()) {
            nodo.setDer(eliminarAVL(nodo.getDer(), valor));
        } else {
            // nodo encontrado
            impTxtLn("Nodo " + valor + " procesado para eliminacion.");
            // Un hijo o ninguno (Es una hoja)
            if (nodo.getIzdo() == null || nodo.getDer() == null) {
                Nodo temp = null;
                if (temp == nodo.getIzdo()) {
                    temp = nodo.getDer();
                } else {
                    temp = nodo.getIzdo();
                }
                // Sin hijos
                if (temp == null) {
                    nodo = null;
                } else { 
                    // Un solo hijo (el hijo toma el lugar del padre)
                    temp.setRaiz(nodo.getRaiz());
                    nodo = temp;
                }
            } else {
                // Dos hijos. Busca el sucesor (el más pequeño del lado derecho)
                Nodo temp = nodoMinimo(nodo.getDer());
                nodo.setDato(temp.getDato()); // Copiamos el dato del sucesor
                // Borra al sucesor de su posición original
                nodo.setDer(eliminarAVL(nodo.getDer(), temp.getDato()));
            }
        }
        // Si el árbol tenía solo 1 nodo y se borró, regresamos
        if (nodo == null) {
            return nodo;
        }
        // Al subir por la recursividad, balancea para no perder la estructura AVL
        return balancear(nodo);
    }
    
    /**
     * Encuentra el nodo con el valor más pequeño de un subárbol.
     * Recorre hacia la izquierda hasta encontrar una hoja. Este nodo representa
     * el menor valor del subárbol.
     * @param nodo La raíz del subárbol donde buscar el mínimo
     * @return El nodo con el valor mínimo del subárbol
     */
    private static Nodo nodoMinimo(Nodo nodo) {
        Nodo actual = nodo;
        while (actual.getIzdo() != null) {
            actual = actual.getIzdo();
        }
        return actual;
    }
    
    /**
     * Muestra el árbol de forma jerárquica (acostado) en consola.
     * Visualiza el árbol rotado 90 grados, útil para inspeccionar su estructura.
     * Los nodos más a la derecha son nodos superiores en el árbol.
     * @param nodo El nodo actual (se inicia con la raíz)
     * @param espaciado La cantidad de espacios para la indentación
     */
    public static void mostrarArbol(Nodo nodo, int espaciado) {
        if (nodo == null) {
            return; // Caso base
        }
        // Recorre primero el subárbol derecho para que quede en la parte superior
        mostrarArbol(nodo.getDer(), espaciado + 1);
        // Imprime la indentación según el nivel del nodo
        for (int i = 0; i < espaciado; i++) {
            System.out.print("      ");
        }
        // Imprime el dato
        System.out.println(nodo.getDato());
        // Recorre el subárbol izquierdo para que quede en la parte inferior
        mostrarArbol(nodo.getIzdo(), espaciado + 1);
    }
    
    /**
     * Muestra texto en consola sin salto de línea.
     * @param texto El texto que se va a mostrar
     */
    public static void impTxt(String texto) { 
        System.out.print(texto);
    } 
    
    /**
     * Muestra texto en consola y salta a la siguiente línea.
     * @param texto El texto que se va a mostrar
     */
    public static void impTxtLn(String texto) { 
        System.out.println(texto);
    } 
    
    /**
     * Muestra el recorrido preorden del árbol.
     * Preorden: Raíz -> Subárbol Izquierdo -> Subárbol Derecho
     * @param raiz Raíz del árbol a recorrer
     */
    public static void preorden(Nodo raiz){
        if(raiz!=null){
            impTxtLn("["+raiz.getDato()+"]");
            preorden(raiz.getIzdo());
            preorden(raiz.getDer());
        }
    }
    
    /**
     * Muestra el recorrido inorden del árbol.
     * Inorden: Subárbol Izquierdo -> Raíz -> Subárbol Derecho
     * En un ABB, esto proporciona los valores en orden ascendente.
     * @param raiz Raíz del árbol a recorrer
     */
    public static void inorden(Nodo raiz){
        if(raiz!=null){
            inorden(raiz.getIzdo());
            impTxtLn("["+raiz.getDato()+"]");
            inorden(raiz.getDer());
        }
    }
    
    /**
     * Muestra el recorrido postorden del árbol.
     * Postorden: Subárbol Izquierdo -> Subárbol Derecho -> Raíz
     * @param raiz Raíz del árbol a recorrer
     */
    public static void postorden(Nodo raiz){
        if(raiz!=null){
            postorden(raiz.getIzdo());
            postorden(raiz.getDer());
            impTxtLn("["+raiz.getDato()+"]");
        }
    }
    
    /**
     * Busca un valor en el árbol binario de búsqueda.
     * Utiliza la propiedad de búsqueda binaria para encontrar eficientemente el nodo.
     * La búsqueda se realiza en tiempo O(log n) en un árbol balanceado.
     * @param raiz La raíz del árbol o subárbol donde empieza la búsqueda
     * @param valor El valor a buscar
     * @return El nodo con el valor buscado, o null si no se encuentra
     */
    public static Nodo buscar(Nodo raiz, int valor){
        if(raiz!=null){
            if(raiz.getDato()==valor){
                // Caso base, regresa el nodo donde esta el valor buscado
                impTxtLn("Valor encontrado");
                return raiz;
            } else if(valor<raiz.getDato()){
                // Recorre el sub arbol izquierdo para seguir buscando el valor
                return buscar(raiz.getIzdo(), valor);
            } else if(valor>raiz.getDato()){
                // Recorre el sub arbol Derecho para seguir buscando el valor
                return buscar(raiz.getDer(), valor);
            }
        }
        return null;
    }
    
    /**
     * Muestra el menú interactivo en consola.
     * Presenta al usuario todas las operaciones disponibles del árbol AVL.
     */
    public static void menu() {
        impTxtLn("\n===================================");
        impTxtLn("      GESTOR DE ARBOL AVL (BALANCEADO)");
        impTxtLn("===================================");
        impTxtLn("1. Agregar elemento");
        impTxtLn("2. Mostrar arbol completo");
        impTxtLn("3. Ordenar-Preorden");
        impTxtLn("4. Ordenar-Inorden");
        impTxtLn("5. Ordenar-Postorden");
        impTxtLn("6. Buscar valor");
        impTxtLn("7. Eliminar");
        impTxtLn("8. Salir");
        impTxtLn("===================================");
    }
    
    /**
     * Pide y valida la entrada de un número entero desde el usuario.
     * Continúa pidiendo entrada hasta que el usuario proporcione un entero válido.
     * @param sc Instancia de Scanner para leer desde la consola
     * @param mensaje El mensaje a mostrar al pedir el número
     * @return Un número entero válido ingresado por el usuario
     */
    public static int pedirNumeroInt(Scanner sc, String mensaje) {
        while (true) {
            impTxt(mensaje);
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                impTxtLn("Valor invalido, ingrese un numero entero.");
            }
        }
    }

    /**
     * Método principal que inicia la aplicación.
     * Presenta un menú interactivo que permite al usuario:
     * <ul>
     *   <li>Insertar elementos en el árbol</li>
     *   <li>Eliminar elementos del árbol</li>
     *   <li>Buscar elementos en el árbol</li>
     *   <li>Realizar recorridos (preorden, inorden, postorden)</li>
     *   <li>Visualizar la estructura del árbol</li>
     *   <li>Salir de la aplicación</li>
     * </ul>
     * @param args Los argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Boolean ejecucion = true;
        int opcion;
        do {
            menu();
            opcion = pedirNumeroInt(sc, "Elige una opcion: ");
            switch(opcion){
                case 1:{
                    insertar(pedirNumeroInt(sc, "Ingrese un numero entero para agregar al arbol: "));
                    break;
                }
                case 2:{
                    if (raiz == null) {
                        impTxtLn("El arbol se encuentra vacio.");
                    } else {
                        impTxtLn("\n--- Estructura del Arbol (Grafica) ---");
                        // Se añadirá el factor de equilibrio en la visualización
                        mostrarArbol(raiz, 0); 
                        impTxtLn("--------------------------------------\n");
                    }
                    break;
                }
                case 3:{ preorden(raiz); break; }
                case 4:{ inorden(raiz); break; }
                case 5:{ postorden(raiz); break; }
                case 6:{
                    Nodo valorBusqueda = buscar(raiz, pedirNumeroInt(sc,"Ingresa el valor a buscar: "));
                    if (valorBusqueda == null){
                        impTxtLn("Valor no encontrado");
                    } else {
                        String padre = (valorBusqueda.getRaiz() != null) ? String.valueOf(valorBusqueda.getRaiz().getDato()) : "Ninguno (es la raiz)";
                        impTxtLn("Valor encontrado: " + valorBusqueda.getDato() + " | Su padre es: " + padre);
                    }
                    break;
                }
                case 7:{
                    eliminar(pedirNumeroInt(sc,"Ingrese el valor a eliminar: "));
                    break;
                }
                case 8:{
                    impTxtLn("Saliendo del programa...");
                    ejecucion = false;
                    break;
                }
                default:{
                    impTxtLn("Opcion no valida, ingresa otra opcion");
                    break;
                }
            }
        } while(ejecucion);
        sc.close();
    }
}
