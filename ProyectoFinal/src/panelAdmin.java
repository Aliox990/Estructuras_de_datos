/**
 *
 * @author Alonso
 */
public class panelAdmin extends javax.swing.JPanel {
    
    private ListaPlaylist listaGlobal;

    /**
     * Creates new form panelAdmin
     */
    public panelAdmin() {
        initComponents();
    }
    
    public void configurarEstructura(ListaPlaylist lista) {
        this.listaGlobal = lista;
    }
    
    private void reescribirArchivoPersistente() {
        java.io.File baseDatosPlaylist = new java.io.File("PlaylistCanciones.txt");
        try {
            // Al usar el modo "rw" y aplicar setLength(0), trunca el archivo para limpiarlo por completo
            java.io.RandomAccessFile escritor = new java.io.RandomAccessFile(baseDatosPlaylist, "rw");
            escritor.setLength(0);

            // Recorre la estructura de datos para guardar los nodos que aun existen
            Cancion actual = listaGlobal.getInicio();
            while (actual != null) {
                escritor.writeBytes(actual.getTitulo() + ";" + actual.getArtista() + ";" + 
                                   actual.getRutaAudio() + ";" + actual.getRutaImagen() + "\n");
                actual = actual.getSiguiente();
            }
            escritor.close();
        } catch (java.io.IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error al actualizar el archivo de almacenamiento.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buscarAudio(){
        javax.swing.JFileChooser selector = new javax.swing.JFileChooser();
        
        // Abrir el explorador directamente en la carpeta del proyecto para mayor comodidad
        selector.setCurrentDirectory(new java.io.File(System.getProperty("user.dir")));
    
        javax.swing.filechooser.FileNameExtensionFilter filtro = 
            new javax.swing.filechooser.FileNameExtensionFilter("Archivos de Audio WAV" , "wav");
        selector.setFileFilter(filtro);

        int resultado = selector.showOpenDialog(this);

        if (resultado == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File archivoSeleccionado = selector.getSelectedFile();
            
            String rutaAbsoluta = archivoSeleccionado.getAbsolutePath();
            String rutaProyecto = System.getProperty("user.dir");
            
            // Si el archivo está dentro de la carpeta del proyecto, calcula la ruta relativa
            if (rutaAbsoluta.startsWith(rutaProyecto)) {
                // El +1 sirve para quitar la barra separadora (ej. '\' en Windows)
                String rutaRelativa = rutaAbsoluta.substring(rutaProyecto.length() + 1);
                txtRutaAudio.setText(rutaRelativa);
            } else {
                // Si escoge un archivo de otro lado, guarda ruta absolta
                txtRutaAudio.setText(rutaAbsoluta);
            }
        }
    }
    
    private void buscarImagen(){
        javax.swing.JFileChooser selector = new javax.swing.JFileChooser();
        selector.setCurrentDirectory(new java.io.File(System.getProperty("user.dir")));
        
        javax.swing.filechooser.FileNameExtensionFilter filtro = 
            new javax.swing.filechooser.FileNameExtensionFilter("Imágenes del álbum JPG o PNG" , "jpg" , "png");
        selector.setFileFilter(filtro);

        int resultado = selector.showOpenDialog(this);

        if (resultado == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File archivoSeleccionado = selector.getSelectedFile();
            
            String rutaAbsoluta = archivoSeleccionado.getAbsolutePath();
            String rutaProyecto = System.getProperty("user.dir");
            
            if (rutaAbsoluta.startsWith(rutaProyecto)) {
                String rutaRelativa = rutaAbsoluta.substring(rutaProyecto.length() + 1);
                txtRutaImagen.setText(rutaRelativa);
            } else {
                txtRutaImagen.setText(rutaAbsoluta);
            }
        }
    }
    
    private void guardarCancion(){
        String titulo = txtTitulo.getText().trim();
        String artista = txtArtista.getText().trim();
        String rutaAudio = txtRutaAudio.getText().trim();
        String rutaImagen = txtRutaImagen.getText().trim();

        // Validacion de campos vacios
        if (titulo.isEmpty() || artista.isEmpty() || rutaAudio.isEmpty() || rutaImagen.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error todos los campos deben ser llenados" , "Advertencia" , javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validacion de existencia fisica de archivos multimedia
        java.io.File archivoMusica = new java.io.File(rutaAudio);
        java.io.File archivoFoto = new java.io.File(rutaImagen);

        if (!archivoMusica.exists() || !archivoFoto.exists()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error las rutas de archivos especificadas no existen" , "Archivo No Encontrado" , javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Archivo donde se centralizan las canciones de la playlist
        java.io.File baseDatosPlaylist = new java.io.File("PlaylistCanciones.txt");

        try {
            // Asegurar que el archivo de datos exista
            if (!baseDatosPlaylist.exists()) {
                baseDatosPlaylist.createNewFile();
            }

            // Validacion de Canciones Duplicadas
            java.io.RandomAccessFile lector = new java.io.RandomAccessFile(baseDatosPlaylist, "r");
            String registroExistente;
            boolean cancionDuplicada = false;

            while ((registroExistente = lector.readLine()) != null) {
                String[] campos = registroExistente.split(";");
                if (campos.length >= 1) {
                    // Si el titulo ya existe dentro de la playlist activa
                    if (campos[0].equalsIgnoreCase(titulo)) {
                        cancionDuplicada = true;
                        break;
                    }
                }
            }
            lector.close();

            if (cancionDuplicada) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error ya existe una cancion registrada con ese mismo titulo" , "Registro Duplicado" , javax.swing.JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Guardado Secuencial Persistente
            java.io.RandomAccessFile escritor = new java.io.RandomAccessFile(baseDatosPlaylist, "rw");
            escritor.seek(escritor.length()); // Se mueve al final absoluto del archivo

            // Escribe los cuatro atributos del nodo separados por punto y coma
            escritor.writeBytes(titulo + ";" + artista + ";" + rutaAudio + ";" + rutaImagen + "\n");
            escritor.close();

            javax.swing.JOptionPane.showMessageDialog(this, "Cancion añadida exitosamente a la base de datos");

            // Limpia el formulario para una nueva captura
            txtTitulo.setText("");
            txtArtista.setText("");
            txtRutaAudio.setText("");
            txtRutaImagen.setText("");

        } catch (java.io.IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Ocurrio un error al gestionar el archivo de datos" , "Error" , javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtTitulo = new javax.swing.JTextField();
        txtArtista = new javax.swing.JTextField();
        txtRutaAudio = new javax.swing.JTextField();
        txtRutaImagen = new javax.swing.JTextField();
        btnBuscarAudio = new javax.swing.JButton();
        btnBuscarImagen = new javax.swing.JButton();
        btnGuardarCancion = new javax.swing.JButton();
        btnEliminarCancion = new javax.swing.JButton();
        btnEliminarPlaylist = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 51, 102));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Admin");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Titulo:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Artista:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Archivo Audio:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Imagen:");

        txtTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        txtArtista.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        txtRutaAudio.setEditable(false);
        txtRutaAudio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtRutaAudio.setFocusable(false);
        txtRutaAudio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRutaAudioActionPerformed(evt);
            }
        });

        txtRutaImagen.setEditable(false);
        txtRutaImagen.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtRutaImagen.setFocusable(false);

        btnBuscarAudio.setBackground(new java.awt.Color(102, 102, 102));
        btnBuscarAudio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBuscarAudio.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarAudio.setText("Examinar");
        btnBuscarAudio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscarAudio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarAudioActionPerformed(evt);
            }
        });

        btnBuscarImagen.setBackground(new java.awt.Color(102, 102, 102));
        btnBuscarImagen.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBuscarImagen.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscarImagen.setText("Examinar");
        btnBuscarImagen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarImagenActionPerformed(evt);
            }
        });

        btnGuardarCancion.setBackground(new java.awt.Color(0, 102, 51));
        btnGuardarCancion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGuardarCancion.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardarCancion.setText("Agregar canción");
        btnGuardarCancion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarCancion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCancionActionPerformed(evt);
            }
        });

        btnEliminarCancion.setBackground(new java.awt.Color(153, 0, 51));
        btnEliminarCancion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEliminarCancion.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarCancion.setText("Eliminar canción");
        btnEliminarCancion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarCancion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCancionActionPerformed(evt);
            }
        });

        btnEliminarPlaylist.setBackground(new java.awt.Color(153, 0, 51));
        btnEliminarPlaylist.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnEliminarPlaylist.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarPlaylist.setText("Eliminar Playlist");
        btnEliminarPlaylist.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarPlaylist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPlaylistActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTitulo))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtArtista))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRutaAudio, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscarAudio))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEliminarPlaylist, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminarCancion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtRutaImagen, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscarImagen))
                            .addComponent(btnGuardarCancion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtArtista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtRutaAudio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarAudio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtRutaImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscarImagen))
                .addGap(18, 18, 18)
                .addComponent(btnGuardarCancion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarCancion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEliminarPlaylist)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarAudioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarAudioActionPerformed
        buscarAudio();
    }//GEN-LAST:event_btnBuscarAudioActionPerformed

    private void btnBuscarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarImagenActionPerformed
        buscarImagen();
    }//GEN-LAST:event_btnBuscarImagenActionPerformed

    private void btnGuardarCancionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCancionActionPerformed
        guardarCancion();
    }//GEN-LAST:event_btnGuardarCancionActionPerformed

    private void btnEliminarCancionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCancionActionPerformed
        String tituloParaBorrar = txtTitulo.getText().trim();
    
        if (tituloParaBorrar.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, escribe el título exacto de la canción que deseas eliminar.", "Campos Vacíos", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Ejecuta la baja en la estructura de datos lineal
        boolean exito = listaGlobal.eliminarPorTitulo(tituloParaBorrar);

        if (exito) {
            // Si el nodo se desconectó con éxito en RAM, sincroniza el archivo TXT
            String rutaProyecto = System.getProperty("user.dir");
            reescribirArchivoPersistente();
            javax.swing.JOptionPane.showMessageDialog(this, "La canción ha sido eliminada correctamente de la base de datos.");
            txtTitulo.setText("");
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "No se encontró ninguna canción registrada con ese título.", "No Encontrado", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarCancionActionPerformed

    private void btnEliminarPlaylistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPlaylistActionPerformed
        int confirmacion = javax.swing.JOptionPane.showConfirmDialog(this, 
        "¿Estás completamente seguro de que deseas eliminar la playlist entera? Esta acción no se puede deshacer.", 
        "Advertencia Crítica", javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.WARNING_MESSAGE);

        if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
            // Vacia la estructura en memoria RAM
            listaGlobal.vaciarLista();

            // Sobrescribir el archivo físico dejándolo en cero bytes
            reescribirArchivoPersistente();

            javax.swing.JOptionPane.showMessageDialog(this, "La playlist completa ha sido eliminada.");
        }
    }//GEN-LAST:event_btnEliminarPlaylistActionPerformed

    private void txtRutaAudioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRutaAudioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRutaAudioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarAudio;
    private javax.swing.JButton btnBuscarImagen;
    private javax.swing.JButton btnEliminarCancion;
    private javax.swing.JButton btnEliminarPlaylist;
    private javax.swing.JButton btnGuardarCancion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField txtArtista;
    private javax.swing.JTextField txtRutaAudio;
    private javax.swing.JTextField txtRutaImagen;
    private javax.swing.JTextField txtTitulo;
    // End of variables declaration//GEN-END:variables
}
