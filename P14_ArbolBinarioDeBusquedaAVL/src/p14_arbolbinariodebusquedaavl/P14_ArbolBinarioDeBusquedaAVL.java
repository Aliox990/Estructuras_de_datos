package p14_arbolbinariodebusquedaavl;

import java.util.Scanner;

/**
 *
 * @author Alonso
 */
public class P14_ArbolBinarioDeBusquedaAVL {
    
    static Nodo raiz;

    
    
    /**
     * Calcula la altura de un nodo recursivamente.
     * @param nodo El nodo del que se va a calcular la altura
     * @return Regresa la altura del nodo
     */
    public static int obtenerAltura(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + Math.max(obtenerAltura(nodo.getIzdo()), obtenerAltura(nodo.getDer()));
    }
    
    /**
     * Calcula el factor de equilibrio (FE = Altura Derecha - Altura Izquierda)
     * @param nodo El nodo del que se obtendra su factor de equilibrio
     * @return Regresa el factor de equilibrio
     */
    public static int obtenerFE(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        return obtenerAltura(nodo.getDer()) - obtenerAltura(nodo.getIzdo());
    }
    
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
    
    public static void insertar(int valor) {
        raiz = insertarAVL(raiz, valor, null);
    }

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
    
    public static void eliminar(int valor) {
        raiz = eliminarAVL(raiz, valor);
    }

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
     * Encuentra el nodo con el valor más pequeño de un subárbol
     */
    private static Nodo nodoMinimo(Nodo nodo) {
        Nodo actual = nodo;
        while (actual.getIzdo() != null) {
            actual = actual.getIzdo();
        }
        return actual;
    }
    
    /**
     * Muestra el arbol de forma jerarquica (acostado) en consola
     * @param nodo El nodo actual (inicia con la raiz)
     * @param espaciado La cantidad de espacios para la indentacion
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
     * Muestra texto en consola
     * @param texto Es el texto que se va a mostrar
     */
    public static void impTxt(String texto) { 
        System.out.print(texto);
    } 
    
    /**
     * Muestra texto en consola y salta de lines
     * @param texto Es el texto que se va a mostrar
     */
    public static void impTxtLn(String texto) { 
        System.out.println(texto);
    } 
    
    /**
     * Muestra el recorrido preorden para un arbol
     * @param raiz Raiz del arbol que se va a recorrer
     */
    public static void preorden(Nodo raiz){
        if(raiz!=null){
            impTxtLn("["+raiz.getDato()+"]");
            preorden(raiz.getIzdo());
            preorden(raiz.getDer());
        }
    }
    
    /**
     * Muestra el recorrido inorden para un arbol
     * @param raiz Raiz del arbol que se va a recorrer
     */
    public static void inorden(Nodo raiz){
        if(raiz!=null){
            inorden(raiz.getIzdo());
            impTxtLn("["+raiz.getDato()+"]");
            inorden(raiz.getDer());
        }
    }
    
    /**
     * Muestra el recorrido postorden para un arbol
     * @param raiz Raiz del arbol que se va a recorrer
     */
    public static void postorden(Nodo raiz){
        if(raiz!=null){
            postorden(raiz.getIzdo());
            postorden(raiz.getDer());
            impTxtLn("["+raiz.getDato()+"]");
        }
    }
    
    /**
     * Buscar un dato en un arbol binario
     * @param raiz raiz del arbol o sub arbol en donde empieza la busqueda
     * @param valor Valor a buscar
     * @return Regresa el nodo con el valor que se esta buscando
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
     * Muestra un menu en consola
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
     * Pide y valida un numero entero
     * @param sc Instancia de scanner
     * @param mensaje Mensaje que se muestra al pedir el numero
     * @return Regresa un numero entero
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
     * @param args the command line arguments
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
