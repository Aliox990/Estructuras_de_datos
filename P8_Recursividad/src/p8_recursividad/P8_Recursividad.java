package p8_recursividad;

import java.util.Scanner;

/**
 *
 * @author Alonso
 */
public class P8_Recursividad {
    static int suma=0, valor;
    static Scanner sc = new Scanner(System.in);
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        valor = pedirNumeroInt(sc, "Ingresa un valor entero:");
        
        impTxtLn(suma1HastaN(valor));
    }
    
    static int suma1HastaN(int n){
    
        if (n==0){ // Si el valor es 0, ya no hay nada que sumar, por lo que ahora regresa el resultado
            return suma;
        } else { // Si el valor ingresado aun no es 0, vuelve a llamar la funcion 
            suma=suma+n; // 
            return suma1HastaN(n-1); //Se le resta 1 a n para en la proxima vuelta sumar el valor anterior, o si es 0 que termine
        }
    }
    
    public static int pedirNumeroInt(Scanner sc, String mensaje) {
        while (true) {
            impTxt(mensaje);
            try {
                int num = Integer.parseInt(sc.nextLine());
                return num;
            } catch (NumberFormatException e) {
                impTxtLn("Valor invalido, ingrese un numero.");
            }
        }
    }
    
    /**
     * 
     * @param texto permite escribir texto en pantalla
     */
    public static void impTxt(String texto)
    { // Imprimir texto en pantalla
        System.out.print(texto);
    } // Fin de impTxt
    
    /**
     * 
     * @param texto permite escribir texto en pantalla con salto de linea
     */
    public static void impTxtLn(String texto)
    { // Imprimir texto en pantalla
        System.out.println(texto);
    } // Fin de impTxtLn
    
    /**
     * 
     * @param num permite escribir numeros en pantalla
     */
    public static void impTxt(float num)
    { // Imprimir texto en pantalla
        System.out.print(num);
    } // Fin de impTxt
    
    /**
     * 
     * @param num permite escribir numeros en pantalla con salto de linea
     */
    public static void impTxtLn(float num)
    { // Imprimir texto en pantalla
        System.out.println(num);
    } // Fin de impTxtLn
    
}
