import java.awt.HeadlessException;
import javax.swing.JOptionPane;

/**
 *
 * @author Alonso
 */
public class ListaEnlazada extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ListaEnlazada.class.getName());
    
    Nodo frente=null, Final=null, aux=null;
    Nodo nuevo;
    
    private void mostrarCancion(Nodo x){
        labCancion.setText(String.valueOf(x.getNombreCancion())); //Muestra los datos del nodo
        labDuracion.setText(convertirSegundos(x.getDuracion()));
        labGenero.setText(String.valueOf(x.getGenero()));
        labAutor.setText(String.valueOf(x.getNombreArtista()));
        labPuntero.setText(String.valueOf(x)); //Muestra el puntero
    }
    
    private String convertirSegundos(int seg){
        int min=0;
        while(seg>=60){
            seg-=60;
            min++;
        }
        if(seg==0){
            return min+":00";
        }else if(seg<10){
            return min+":0"+seg;
        }else{
            return min+":"+seg;
        }
    }
    
    void insertar(String cancion, String autor, String genero, int duracion){
        
        nuevo = new Nodo(duracion, cancion, autor, genero, null); //Crea una nueva instancia de un nodo
        
        if (Final!=null){
            //Cuando ya hay elementos en la lista
            Final.setSiguiente(nuevo);
            Final=nuevo;
            
        } else{//Cuando es el primer elemento que se va a inserta
            Final=nuevo; 
            frente=nuevo;
            //Como solo hay un elemento, el final y el frente quedan en ese elemento
        }
        mostrarCancion(Final);
    }
    
    void MostrarLista() {
        //Verifica si la lista está vacía
        if (frente == null) {
            JOptionPane.showMessageDialog(null, "La playlist está vacía.", "Playlist", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //Usa StringBuilder para construir el texto que se va a mostrar
        StringBuilder listaVisual = new StringBuilder();
        listaVisual.append("--- TU PLAYLIST ---\n\n");
        
        Nodo actual = frente;
        int cont = 1;

        //Recorrer la lista nodo por nodo
        while (actual != null) {
            listaVisual.append(cont).append(". ")
                       .append(actual.getNombreCancion())
                       .append(" | ").append(actual.getNombreArtista())
                       .append(" | ").append(actual.getGenero())
                       .append(" | ").append(convertirSegundos(actual.getDuracion()))
                       .append("\n"); // Salto de línea para la siguiente canción
            
            actual = actual.getSiguiente();
            cont++;
        }
        JOptionPane.showMessageDialog(null, listaVisual.toString(), "Lista de Reproducción", JOptionPane.INFORMATION_MESSAGE);
    }
    
    void eliminarFrente(){
        if(frente==null && Final==null){ //Verificar si la lista tiene elementos
            JOptionPane.showMessageDialog(null,"Lista vacia");
        } else {
            JOptionPane.showMessageDialog(null,"Elemento a borrar:"+frente.nombreCancion);//Muestra informacion del nodo a eliminar
            frente=frente.getSiguiente(); //Mueve el apuntador frente al proximo elemento
            if(frente==null){
                Final=null;
            }
            JOptionPane.showMessageDialog(null,"Elemento eliminado");
        }
    }
    
    void eliminarFinal(){
        aux=frente;
        if(frente==null && Final==null){
            JOptionPane.showMessageDialog(null,"Lista vacia");
        } else{
            if(frente==Final){
                JOptionPane.showMessageDialog(null,"Elemento Eliminado:"+aux.nombreCancion);
                Final=null;
                frente=null;
            }else{
                while(aux.getSiguiente()!=Final){
                    aux=aux.getSiguiente();
                }
                JOptionPane.showMessageDialog(null,"Elemento Eliminado:"+aux.Siguiente.nombreCancion);
                Final=aux;
                Final.setSiguiente(null);
            }
        }
    }
    
void EliminarX(String x) {
        if (frente == null) { // Si la lista está vacía
            JOptionPane.showMessageDialog(null, "Lista vacía");
            return;
        }
        //El elemento a eliminar es el PRIMERO de la lista
        if (frente.nombreCancion.equals(x)) {
            JOptionPane.showMessageDialog(null, "Elemento Eliminado: " + frente.nombreCancion);
            frente = frente.getSiguiente(); // Mover el frente al segundo nodo
            if (frente == null) { 
                // Si la lista se quedó vacía, Final también debe ser null
                Final = null;
            }
            return;
        }
        // El elemento está en medio o es el último
        Nodo actual = frente;
        // Recorrer mientras haya un nodo siguiente Y ese siguiente no sea el que esta buscando
        while (actual.getSiguiente() != null && !actual.getSiguiente().nombreCancion.equals(x)) {
            actual = actual.getSiguiente();
        }
        // Si el ciclo terminó porque llega al final y no se encontro el elemento
        if (actual.getSiguiente() == null) {
            JOptionPane.showMessageDialog(null, "Elemento '" + x + "' no existe");
        } else {
            // Si lo encuentra, lo "salta" para eliminarlo
            JOptionPane.showMessageDialog(null, "Elemento Eliminado: " + actual.getSiguiente().nombreCancion);
            actual.setSiguiente(actual.getSiguiente().getSiguiente());
            
            // Si el elemento eliminado era el último, 
            // actualizar el puntero 'Final'
            if (actual.getSiguiente() == null) {
                Final = actual;
            }
        }
    }
    
    public ListaEnlazada() {
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

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnInsertar = new javax.swing.JButton();
        btnBorrarFrente = new javax.swing.JButton();
        btnBorrarFinal = new javax.swing.JButton();
        btnBorrarX = new javax.swing.JButton();
        labCancion = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        labPuntero = new javax.swing.JLabel();
        btnMostrar = new javax.swing.JButton();
        labAutor = new javax.swing.JLabel();
        labGenero = new javax.swing.JLabel();
        labDuracion = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Listas Enlazadas");

        jPanel1.setLayout(new java.awt.GridLayout(5, 1));

        btnInsertar.setBackground(new java.awt.Color(204, 255, 204));
        btnInsertar.setText("Insertar");
        btnInsertar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarActionPerformed(evt);
            }
        });
        jPanel1.add(btnInsertar);

        btnBorrarFrente.setBackground(new java.awt.Color(255, 204, 204));
        btnBorrarFrente.setText("Borrar Frente");
        btnBorrarFrente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBorrarFrente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarFrenteActionPerformed(evt);
            }
        });
        jPanel1.add(btnBorrarFrente);

        btnBorrarFinal.setBackground(new java.awt.Color(255, 204, 204));
        btnBorrarFinal.setText("Borrar Final");
        btnBorrarFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarFinalActionPerformed(evt);
            }
        });
        jPanel1.add(btnBorrarFinal);

        btnBorrarX.setBackground(new java.awt.Color(255, 204, 204));
        btnBorrarX.setText("Borrar X");
        btnBorrarX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarXActionPerformed(evt);
            }
        });
        jPanel1.add(btnBorrarX);

        labCancion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labCancion.setText("NULL");

        jPanel2.setLayout(new java.awt.GridLayout(1, 2, 200, 0));

        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton4.setText("<-");
        jButton4.setEnabled(false);
        jPanel2.add(jButton4);

        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jButton5.setText("->");
        jButton5.setEnabled(false);
        jPanel2.add(jButton5);

        labPuntero.setText("Puntero");

        btnMostrar.setBackground(new java.awt.Color(204, 255, 255));
        btnMostrar.setText("Mostrar todo");
        btnMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarActionPerformed(evt);
            }
        });

        labAutor.setText("Artista");

        labGenero.setText("Genero");

        labDuracion.setText("Duracion");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(148, 148, 148)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labCancion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(labPuntero, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(48, 48, 48)
                                        .addComponent(btnMostrar))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(labAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(labGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(labDuracion, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labCancion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(labDuracion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labAutor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labGenero)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(labPuntero)
                        .addGap(18, 18, 18)
                        .addComponent(btnMostrar)
                        .addGap(20, 20, 20))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarActionPerformed
        try{
            insertar(
                    JOptionPane.showInputDialog("Cancion a insertar en la lista: "),
                    JOptionPane.showInputDialog("Autor de la cancion: "),
                    JOptionPane.showInputDialog("Genero de la cancion: "),
                    Integer.parseInt(JOptionPane.showInputDialog("Duracion de la cancion(segundos): "))
            );
        }catch(HeadlessException | NumberFormatException e){
            JOptionPane.showConfirmDialog(null, "Error en el valor proporcionado");
        }
    }//GEN-LAST:event_btnInsertarActionPerformed

    private void btnMostrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarActionPerformed
        MostrarLista();
    }//GEN-LAST:event_btnMostrarActionPerformed

    private void btnBorrarFrenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarFrenteActionPerformed
        eliminarFrente();
    }//GEN-LAST:event_btnBorrarFrenteActionPerformed

    private void btnBorrarFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarFinalActionPerformed
        eliminarFinal();
    }//GEN-LAST:event_btnBorrarFinalActionPerformed

    private void btnBorrarXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarXActionPerformed
        try{
            EliminarX(JOptionPane.showInputDialog("Cancion a eliminar: "));
        } catch(HeadlessException | NumberFormatException e){
            JOptionPane.showConfirmDialog(null, "Error en el valor proporcionado");
        }
    }//GEN-LAST:event_btnBorrarXActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new ListaEnlazada().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBorrarFinal;
    private javax.swing.JButton btnBorrarFrente;
    private javax.swing.JButton btnBorrarX;
    private javax.swing.JButton btnInsertar;
    private javax.swing.JButton btnMostrar;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labAutor;
    private javax.swing.JLabel labCancion;
    private javax.swing.JLabel labDuracion;
    private javax.swing.JLabel labGenero;
    private javax.swing.JLabel labPuntero;
    // End of variables declaration//GEN-END:variables
}
