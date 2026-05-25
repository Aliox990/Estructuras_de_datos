import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Clase experta para gestionar la reproducción de archivos WAV de forma nativa
 */
public class ReproductorWAV {
    
    // Objeto nativo de Java que almacena y reproduce el audio
    private Clip clipActivo;
    
    // Variable para recordar en qué microsegundo se pausó la canción
    private long tiempoPausa;
    
    // Bandera lógica para saber el estado del motor
    private boolean estaReproduciendo;

    public ReproductorWAV() {
        this.estaReproduciendo = false;
        this.tiempoPausa = 0;
    }

    /**
     * Carga un archivo de audio nuevo y lo reproduce desde el inicio
     */
    public void reproducirDesdeCero(String rutaArchivo) {
        try {
            // Si ya hay una canción sonando, la detiene y cierra para liberar memoria
            detener();

            File archivoAudio = new File(rutaArchivo);
            
            // Valida físicamente que el archivo esté ahí por seguridad
            if (!archivoAudio.exists()) {
                javax.swing.JOptionPane.showMessageDialog(null, "No se encontró el archivo de audio: " + rutaArchivo);
                return;
            }

            // Abre el canal de audio nativo
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(archivoAudio);
            clipActivo = AudioSystem.getClip();
            clipActivo.open(audioStream);
            
            // Comienza a reproducir
            clipActivo.start();
            estaReproduciendo = true;
            tiempoPausa = 0;
            
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error al procesar el archivo WAV.", "Error de Audio", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Pausa la canción actual guardando la posición exacta
     */
    public void pausar() {
        if (clipActivo != null && clipActivo.isRunning()) {
            tiempoPausa = clipActivo.getMicrosecondPosition(); // Guarda el tiempo exacto
            clipActivo.stop();
            estaReproduciendo = false;
        }
    }

    /**
     * Reanuda la canción desde el punto donde se quedó
     */
    public void reanudar() {
        if (clipActivo != null && !estaReproduciendo) {
            clipActivo.setMicrosecondPosition(tiempoPausa); // Se mueve al punto de pausa
            clipActivo.start();
            estaReproduciendo = true;
        }
    }

    /**
     * Detiene la canción por completo y reinicia el tiempo
     */
    public void detener() {
        if (clipActivo != null) {
            clipActivo.stop();
            clipActivo.close();
            estaReproduciendo = false;
            tiempoPausa = 0;
        }
    }

    // Getter para que la interfaz sepa el estado de la cancion
    public boolean isEstaReproduciendo() {
        return estaReproduciendo;
    }
    
    /**
     * Obtiene el momento exacto actual de la canción en microsegundos
     */
    public long getPosicionActual() {
        if (clipActivo != null) {
            return clipActivo.getMicrosecondPosition();
        }
        return 0;
    }

    /**
     * Obtiene la duración total del archivo de audio en microsegundos
     */
    public long getDuracionTotal() {
        if (clipActivo != null) {
            return clipActivo.getMicrosecondLength();
        }
        return 0;
    }

    /**
     * Verifica lógicamente si la canción ya terminó su reproducción
     */
    public boolean alcanzoElFinal() {
        if (clipActivo != null) {
            // Si la posición actual es igual o mayor a la duración total, ya terminó
            return clipActivo.getMicrosecondPosition() >= clipActivo.getMicrosecondLength();
        }
        return false;
    }
}