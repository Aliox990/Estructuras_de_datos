import java.awt.Image;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Alonso
 */
public class Lista_doble_circular extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Lista_doble_circular.class.getName());
    Nodo Frente=null, Final=null, aux=null;
    Nodo nuevo, actual;
    
    void limpiarCampos() {
        txtValor.setText("");
    }
    
    boolean validarEntradas() {
        if (txtValor.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, "Todos los campos de texto son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    void agregarValorEnX() {
        // Validar que la caja de texto no esté vacía
        if (!validarEntradas()) return;

        // Pedir la posición al usuario mediante una ventana emergente
        String posStr = JOptionPane.showInputDialog(this, 
                "Ingrese la posición donde desea insertar (1 para el inicio):", 
                "Posición", JOptionPane.QUESTION_MESSAGE);
                
        if (posStr == null || posStr.trim().isEmpty()) return; // por si el usuario cancela la ventana

        int posicion;
        try {
            posicion = Integer.parseInt(posStr);
            if (posicion <= 0) {
                JOptionPane.showMessageDialog(this, "La posición debe ser un número mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String Valor = txtValor.getText().trim();

        // Calcular el tamaño actual de la lista para evitar ciclos infinitos
        int tamaño = 0;
        if (Frente != null) {
            tamaño = 1;
            Nodo temp = Frente;
            while (temp != Final) {
                tamaño++;
                temp = temp.getSiguiente();
            }
        }

        // Si piden una posición mayor al tamaño de la lista, lo ajustamos para que se agregue al final
        if (posicion > tamaño + 1) {
            JOptionPane.showMessageDialog(this, "La posición excede el tamaño. Se agregará al final (Posición " + (tamaño + 1) + ").", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            posicion = tamaño + 1;
        }

        // Lógica de inserción según la posición seleccionada
        if (tamaño == 0) { 
            // Lista vacía
            nuevo = new Nodo(null, null, Valor);
            nuevo.setSiguiente(nuevo);
            nuevo.setAnterior(nuevo);
            Frente = nuevo;
            Final = nuevo;
        } else if (posicion == 1) { 
            // Insertar al inicio (Frente)
            nuevo = new Nodo(Final, Frente, Valor);
            Final.setSiguiente(nuevo);
            Frente.setAnterior(nuevo);
            Frente = nuevo; // Actualizar quién es el nuevo Frente
        } else if (posicion == tamaño + 1) { 
            // Insertar exactamente al final
            nuevo = new Nodo(Final, Frente, Valor);
            Final.setSiguiente(nuevo);
            Frente.setAnterior(nuevo);
            Final = nuevo; // Actualizamos quién es el nuevo Final
        } else { 
            // Insertar en medio
            aux = Frente;
            // Avanzar hasta el nodo que está justo antes de la posición deseada
            for (int i = 1; i < posicion - 1; i++) {
                aux = aux.getSiguiente();
            }
            
            Nodo sig = aux.getSiguiente(); // El nodo que actualmente ocupa esa posición
            
            // Crear el nuevo nodo metiéndolo en medio de 'aux' y 'sig'
            nuevo = new Nodo(aux, sig, Valor);
            aux.setSiguiente(nuevo);
            sig.setAnterior(nuevo);
        }
        
        actual = nuevo;
        actualizarVistaActual(); 
        limpiarCampos();         
        
        JOptionPane.showMessageDialog(this, "Valor agregado en la posición " + posicion + " con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    
    void eliminarEstudiante() {
        if (actual == null) {
            JOptionPane.showMessageDialog(this, "La lista está vacía o no hay estudiante seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Seguro que desea eliminar a " + actual.getValor() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
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
            labValor.setText("Valor: " + actual.getValor());
        } else {
            // Limpiar si no hay nada
            labValor.setText("Valor:");
        }
    }

    /**
     * Creates new form Control_escolar
     */
    public Lista_doble_circular() {
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
        labValor = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnPrimero = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        btnUltimo = new javax.swing.JButton();
        panelDerecha = new javax.swing.JPanel();
        PanelDatos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(760, 140));
        setResizable(false);

        panelVisualizacion.setBackground(new java.awt.Color(0, 51, 102));
        panelVisualizacion.setPreferredSize(new java.awt.Dimension(300, 504));

        labValor.setForeground(new java.awt.Color(255, 255, 255));
        labValor.setText("Valor:");

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
                    .addComponent(labValor, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 288, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelVisualizacionLayout.setVerticalGroup(
            panelVisualizacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVisualizacionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labValor)
                .addGap(29, 29, 29)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        getContentPane().add(panelVisualizacion, java.awt.BorderLayout.LINE_START);

        panelDerecha.setLayout(new java.awt.BorderLayout());

        PanelDatos.setBackground(new java.awt.Color(102, 102, 102));
        PanelDatos.setPreferredSize(new java.awt.Dimension(460, 300));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Valor:");

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
                        .addComponent(jLabel1)
                        .addGap(75, 75, 75)
                        .addComponent(txtValor, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE))
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
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        panelDerecha.add(PanelDatos, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelDerecha, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        agregarValorEnX();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoActionPerformed
        irUltimo();
    }//GEN-LAST:event_btnUltimoActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        avanzarUno();
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        eliminarEstudiante();
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        retrocederUno();
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnPrimeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimeroActionPerformed
        irPrimero();
    }//GEN-LAST:event_btnPrimeroActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new Lista_doble_circular().setVisible(true));
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labValor;
    private javax.swing.JPanel panelDerecha;
    private javax.swing.JPanel panelVisualizacion;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
