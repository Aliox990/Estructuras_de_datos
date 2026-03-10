
import java.awt.Image;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Alonso
 */
public class Control_escolar extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Control_escolar.class.getName());
    String temporalImagen;
    Nodo Frente=null, Final=null, aux=null;
    Nodo nuevo, actual;
    
    void limpiarCampos() {
        txtNombre.setText("");
        txtApellidoP.setText("");
        txtApellidoM.setText("");
        txtCarrera.setText("");
        txtCorreo.setText("");
        temporalImagen = null;
    }
    
    boolean validarEntradas() {
        if (txtNombre.getText().trim().isEmpty() || 
            txtApellidoP.getText().trim().isEmpty() || 
            txtApellidoM.getText().trim().isEmpty() || 
            txtCarrera.getText().trim().isEmpty() || 
            txtCorreo.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, "Todos los campos de texto son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    boolean cargarImagen() {
        JFileChooser explorador = new JFileChooser();
        explorador.addChoosableFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "png", "jpeg"));
        int seleccion = explorador.showOpenDialog(this);
        
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File auxFile = explorador.getSelectedFile();
            temporalImagen = auxFile.getAbsolutePath();
            return true;
        }
        return false;
    }
    
    void setImageLabel(JLabel label, String imagePath) { //redimensionar imagen
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image img = imageIcon.getImage();

        if (label.getWidth() > 0 && label.getHeight() > 0) {
            Image imagenEscalada =
                    img.getScaledInstance(label.getWidth(),
                            label.getHeight(), Image.SCALE_SMOOTH);  // Ajustar al tamaño del JLabel
            label.setIcon(new ImageIcon(imagenEscalada)); //establecer la imagen escalada en el JLabel
        } else {
            label.setIcon(imageIcon); //si no se puede redimensionar, mostrar la imagen sin escalado
        }
    }
    
    void agregarEstudiante() {
        // Validar que no haya campos vacíos
        if (!validarEntradas()) return;
        
        // Pedir la imagen. Si el usuario cancela se detiene el proceso
        if (!cargarImagen()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una fotografía para continuar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Obtener datos
        String nombre = txtNombre.getText().trim();
        String apellidoP = txtApellidoP.getText().trim();
        String apellidoM = txtApellidoM.getText().trim();
        String carrera = txtCarrera.getText().trim();
        String correo = txtCorreo.getText().trim();

        // Crear el nodo con los datos
        nuevo = new Nodo(null, null, nombre, apellidoP, apellidoM, carrera, correo, temporalImagen);

        // Insertar en la lista al final
        if (Final == null) { // Lista vacía
            Frente = nuevo;
            Final = nuevo;
        } else {
            Final.setSiguiente(nuevo);
            nuevo.setAnterior(Final);
            Final = nuevo;
        }
        
        actual = nuevo;
        actualizarVistaActual(); // Muestra el nodo recién agregado
        limpiarCampos();         // Deja el formulario listo para otro registro
        
        JOptionPane.showMessageDialog(this, "Estudiante agregado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    
    void eliminarEstudiante() {
        if (actual == null) {
            JOptionPane.showMessageDialog(this, "La lista está vacía o no hay estudiante seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar a " + actual.getNombre() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) return;

        // Si es el único elemento en la lista
        if (actual == Frente && actual == Final) {
            Frente = null;
            Final = null;
            actual = null;
        } 
        // Si es el primer elemento
        else if (actual == Frente) {
            Frente = Frente.getSiguiente();
            Frente.setAnterior(null);
            actual = Frente;
        } 
        // Si es el último elemento
        else if (actual == Final) {
            Final = Final.getAnterior();
            Final.setSiguiente(null);
            actual = Final;
        } 
        // Si está en medio de la lista
        else {
            Nodo ant = actual.getAnterior();
            Nodo sig = actual.getSiguiente();
            ant.setSiguiente(sig);
            sig.setAnterior(ant);
            actual = sig; 
        }
        
        actualizarVistaActual();
        JOptionPane.showMessageDialog(this, "Estudiante eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    
    void irPrimero() {
        if (Frente == null) {
            JOptionPane.showMessageDialog(this, "La lista está vacía.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        actual = Frente;
        actualizarVistaActual();
    }

    void irUltimo() {
        if (Final == null) {
            JOptionPane.showMessageDialog(this, "La lista está vacía.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        actual = Final;
        actualizarVistaActual();
    }

    void retrocederUno() {
        if (actual == null) {
            JOptionPane.showMessageDialog(this, "La lista está vacía.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (actual.getAnterior() != null) {
            actual = actual.getAnterior();
            actualizarVistaActual();
        } else {
            JOptionPane.showMessageDialog(this, "Ya estás en el primer registro.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    void avanzarUno(){
        if (actual == null) {
            JOptionPane.showMessageDialog(null, "La lista está vacía.");
            return;
        }
        if (actual.getSiguiente() != null) {
            actual = actual.getSiguiente(); // Mover el puntero
            actualizarVistaActual();        // Refrescar los datos en pantalla
        } else {
            JOptionPane.showMessageDialog(null, "Ya estás en el último registro.");
        }
    }
    
    void actualizarVistaActual() {
        if (actual != null) {
            labNombre.setText("Nombre: " + actual.getNombre());
            labApellidos.setText("Apellidos: " + actual.getApellidoP() + " " + actual.getApellidoM());
            labCarrera.setText("Carrera: " + actual.getCarrera());
            labCorreo.setText("Correo: " + actual.getCorreo());
            setImageLabel(labImagen, actual.imagen);
        } else {
            // Limpiar si no hay nada
            labNombre.setText("Nombre:");
            labApellidos.setText("Apellidos:");
            labCarrera.setText("Carrera:");
            labCorreo.setText("Correo:");
            labImagen.setIcon(null);
        }
    }

    /**
     * Creates new form Control_escolar
     */
    public Control_escolar() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelVisualizacion = new javax.swing.JPanel();
        labImagen = new javax.swing.JLabel();
        labNombre = new javax.swing.JLabel();
        labApellidos = new javax.swing.JLabel();
        labCarrera = new javax.swing.JLabel();
        labCorreo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnPrimero = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        btnUltimo = new javax.swing.JButton();
        panelDerecha = new javax.swing.JPanel();
        PanelDatos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtApellidoP = new javax.swing.JTextField();
        txtApellidoM = new javax.swing.JTextField();
        txtCarrera = new javax.swing.JTextField();
        txtCorreo = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        panelVisualizacion.setBackground(new java.awt.Color(0, 51, 102));
        panelVisualizacion.setPreferredSize(new java.awt.Dimension(300, 504));

        labImagen.setMaximumSize(new java.awt.Dimension(288, 288));
        labImagen.setMinimumSize(new java.awt.Dimension(38, 38));
        labImagen.setPreferredSize(new java.awt.Dimension(288, 288));

        labNombre.setForeground(new java.awt.Color(255, 255, 255));
        labNombre.setText("Nombre:");

        labApellidos.setForeground(new java.awt.Color(255, 255, 255));
        labApellidos.setText("Apellidos:");

        labCarrera.setForeground(new java.awt.Color(255, 255, 255));
        labCarrera.setText("Carrera:");

        labCorreo.setForeground(new java.awt.Color(255, 255, 255));
        labCorreo.setText("Correo:");

        jPanel2.setBackground(new java.awt.Color(0, 51, 102));
        jPanel2.setLayout(new java.awt.GridLayout(1, 5, 10, 0));

        btnPrimero.setBackground(new java.awt.Color(204, 204, 255));
        btnPrimero.setText("<<");
        btnPrimero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimeroActionPerformed(evt);
            }
        });
        jPanel2.add(btnPrimero);

        btnAnterior.setBackground(new java.awt.Color(204, 204, 255));
        btnAnterior.setText("<");
        btnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorActionPerformed(evt);
            }
        });
        jPanel2.add(btnAnterior);

        btnBorrar.setBackground(new java.awt.Color(255, 204, 204));
        btnBorrar.setText("X");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });
        jPanel2.add(btnBorrar);

        btnSiguiente.setBackground(new java.awt.Color(204, 204, 255));
        btnSiguiente.setText(">");
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });
        jPanel2.add(btnSiguiente);

        btnUltimo.setBackground(new java.awt.Color(204, 204, 255));
        btnUltimo.setText(">>");
        btnUltimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltimoActionPerformed(evt);
            }
        });
        jPanel2.add(btnUltimo);

        javax.swing.GroupLayout panelVisualizacionLayout = new javax.swing.GroupLayout(panelVisualizacion);
        panelVisualizacion.setLayout(panelVisualizacionLayout);
        panelVisualizacionLayout.setHorizontalGroup(
            panelVisualizacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVisualizacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelVisualizacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labApellidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labCarrera, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labCorreo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelVisualizacionLayout.setVerticalGroup(
            panelVisualizacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVisualizacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labNombre)
                .addGap(18, 18, 18)
                .addComponent(labApellidos)
                .addGap(18, 18, 18)
                .addComponent(labCarrera)
                .addGap(18, 18, 18)
                .addComponent(labCorreo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(panelVisualizacion, java.awt.BorderLayout.LINE_START);

        panelDerecha.setLayout(new java.awt.BorderLayout());

        PanelDatos.setBackground(new java.awt.Color(102, 102, 102));
        PanelDatos.setPreferredSize(new java.awt.Dimension(460, 300));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Nombre(s):");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Apellido paterno:");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Apellido materno:");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Carrera:");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Correo:");

        txtCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 153, 0));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Agregar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelDatosLayout = new javax.swing.GroupLayout(PanelDatos);
        PanelDatos.setLayout(PanelDatosLayout);
        PanelDatosLayout.setHorizontalGroup(
            PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelDatosLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(67, 67, 67)
                        .addComponent(txtCorreo, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE))
                    .addGroup(PanelDatosLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(9, 9, 9)
                        .addComponent(txtApellidoM, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE))
                    .addGroup(PanelDatosLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(64, 64, 64)
                        .addComponent(txtCarrera, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE))
                    .addGroup(PanelDatosLayout.createSequentialGroup()
                        .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre)
                            .addComponent(txtApellidoP, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDatosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        PanelDatosLayout.setVerticalGroup(
            PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtApellidoP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtApellidoM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(PanelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 280, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        panelDerecha.add(PanelDatos, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelDerecha, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCorreoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        agregarEstudiante();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        avanzarUno();
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void btnPrimeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimeroActionPerformed
        irPrimero();
    }//GEN-LAST:event_btnPrimeroActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        retrocederUno();
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        eliminarEstudiante();
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoActionPerformed
        irUltimo();
    }//GEN-LAST:event_btnUltimoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Control_escolar().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelDatos;
    private javax.swing.JButton btnAnterior;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnPrimero;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JButton btnUltimo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labApellidos;
    private javax.swing.JLabel labCarrera;
    private javax.swing.JLabel labCorreo;
    private javax.swing.JLabel labImagen;
    private javax.swing.JLabel labNombre;
    private javax.swing.JPanel panelDerecha;
    private javax.swing.JPanel panelVisualizacion;
    private javax.swing.JTextField txtApellidoM;
    private javax.swing.JTextField txtApellidoP;
    private javax.swing.JTextField txtCarrera;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
