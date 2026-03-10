
/**
 *
 * @author Alonso
 */
public class Jugador {
    
    String turno, Nombre;
    int puntos;
    
    public Jugador(String turno, String Nombre, int puntos) {
        this.turno = turno;
        this.Nombre = Nombre;
        this.puntos = puntos;
    }
    
    public Jugador(){
    
    }

    public String getTurno() {
        return turno;
    }

    public String getNombre() {
        return Nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}
