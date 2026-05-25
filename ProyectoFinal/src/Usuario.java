/**
 * Representa un usuario del sistema con credenciales y rol.
 * @author Christian Alonso Arevalos Gonzalez y Cesar de Jesus Becerra Vera
 * @version 1.0
 * @since 1.0
 */
public class Usuario {
    private String usuario, contraseña, rol;

    /**
     * Crea un usuario vacío.
     */
    public Usuario() {
    }

    /**
     * Obtiene el nombre de usuario.
     * @return nombre de usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Establece el nombre de usuario.
     * @param usuario nombre de usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene la contraseña del usuario.
     * @return contraseña (texto plano)
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * Establece la contraseña del usuario.
     * @param contraseña contraseña en texto plano
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    /**
     * Obtiene el rol del usuario.
     * @return rol (ej. "administrador" o "usuario")
     */
    public String getRol() {
        return rol;
    }


    /**
     * Establece el rol del usuario.
     * @param rol rol del usuario
     */
    public void setRol(String rol) {
        this.rol = rol;
    }
}
