/**
 *
 * @author Alonso
 */
public class JuegoGato {
    // Jugadores actuales
    Jugador jugadorActual; // Para saber a quién le toca
    Jugador jugadorX = new Jugador("X", "Jugador 1", 0);
    Jugador jugadorO = new Jugador("O", "Jugador 2", 0);
    
    Jugador[] ranking = new Jugador[5]; 
    
    // Para manejar el tablero
    int[][] tablero = new int[3][3];
    
    // Control de la sesión de 5 partidas
    int partidasJugadas = 0;
    boolean juegoTerminado = false;

    public JuegoGato() {
        jugadorActual = jugadorX; // Empieza X
        inicializarTablero();
    }

    /**
     * Pone todos los valores del tablero en 0
     */
    public final void inicializarTablero() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = 0;
            }
        }
        juegoTerminado = false;
    }
    
    /**
     * Funcion para cambiar el turno actual
     */
    public void cambiarTurno() {
        if (jugadorActual == jugadorX) {
            jugadorActual = jugadorO;
        } else {
            jugadorActual = jugadorX;
        }
    }
    
    /**
     * Comprueba si alguno de los jugadores ha ganado
     * @return Retorna 1 si ganax, 2 si gana O, o cero si nadie ha ganado
     */
    public int verificarGanador() {
        // Filas
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0] != 0 && 
                tablero[i][0] == tablero[i][1] && 
                tablero[i][1] == tablero[i][2]) {
                return tablero[i][0]; // Devuelve el ganador (1 o 2)
            }
        }

        // Columnas
        for (int j = 0; j < 3; j++) {
            if (tablero[0][j] != 0 && 
                tablero[0][j] == tablero[1][j] && 
                tablero[1][j] == tablero[2][j]) {
                return tablero[0][j];
            }
        }

        // Diagonal (\)
        if (tablero[0][0] != 0 && 
            tablero[0][0] == tablero[1][1] && 
            tablero[1][1] == tablero[2][2]) {
            return tablero[0][0];
        }

        // Diagonal inversa (/)
        if (tablero[0][2] != 0 && 
            tablero[0][2] == tablero[1][1] && 
            tablero[1][1] == tablero[2][0]) {
            return tablero[0][2];
        }

        return 0;
    }
    
    public void actualizarRanking(Jugador ganador) {
        boolean agregado = false;

        // Busca espacio vacío
        for (int i = 0; i < ranking.length; i++) {
            if (ranking[i] == null) {
                // Crea una copia del jugador para guardarlo en el ranking
                ranking[i] = new Jugador(ganador.getTurno(), ganador.getNombre(), ganador.getPuntos());
                agregado = true;
                break;
            }
        }

        if (!agregado) {
            int indiceMenor = 0;
            int menorPuntaje = ranking[0].getPuntos();

            // Busca quién tiene menos puntos en el ranking actual
            for (int i = 1; i < ranking.length; i++) {
                if (ranking[i].getPuntos() < menorPuntaje) {
                    menorPuntaje = ranking[i].getPuntos();
                    indiceMenor = i;
                }
            }

            // Si el ganador tiene más puntos que el peor del ranking, lo reemplaza
            if (ganador.getPuntos() > menorPuntaje) {
                ranking[indiceMenor] = new Jugador(ganador.getTurno(), ganador.getNombre(), ganador.getPuntos());
            }
        }

        ordenarRanking();
    }

    // Método auxiliar para ordenar
    private void ordenarRanking() {
        Jugador aux;
        for (int i = 0; i < ranking.length - 1; i++) {
            for (int j = 0; j < ranking.length - 1; j++) {
                if (ranking[j] != null && ranking[j+1] != null) {
                    if (ranking[j].getPuntos() < ranking[j+1].getPuntos()) {
                        // Intercambio
                        aux = ranking[j];
                        ranking[j] = ranking[j+1];
                        ranking[j+1] = aux;
                    }
                } else if (ranking[j] == null && ranking[j+1] != null) {
                    aux = ranking[j];
                    ranking[j] = ranking[j+1];
                    ranking[j+1] = aux;
                }
            }
        }
    }
}
