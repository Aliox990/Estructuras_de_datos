
import java.awt.HeadlessException;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author Alonso
 */
public class ListaEnlazada extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ListaEnlazada.class.getName());
    
    Nodo frente=null, Final=null, aux=null;
    Nodo nuevo, actual; //actual etendra el valor que se esta mostrando ahora en pantalla
    String temporalImagen;
    
    void insertar(){
        String melodia = JOptionPane.showInputDialog("Dame el elemento a insertar en la lista: ");
        cargarImagen();
        nuevo = new Nodo(melodia, temporalImagen, null); //Crea una nueva instancia de un nodo
        
        if (Final!=null){
            //Cuando ya hay elementos en la lista
            Final.setSiguiente(nuevo);
            Final=nuevo;
            Final.setSiguiente(frente);
            actual=Final;
            
        } else{//Cuando es el primer elemento que se va a inserta
            Final=nuevo; 
            frente=nuevo;
            nuevo.setSiguiente(nuevo);
            actual=Final;
            //Como solo hay un elemento, el final y el frente quedan en ese elemento
        }
        labDato.setText("");
        setImageLabel(labDato, temporalImagen);
        labMelodia.setText(nuevo.melodia); //Muestra el puntero
    }
    
    void cargarImagen(){
        try{
            JFileChooser explorador = new JFileChooser();
            explorador.addChoosableFileFilter(new FileNameExtensionFilter("imagen","jpg","png"));
            explorador.showOpenDialog(null);
            File auxFile = explorador.getSelectedFile();
            temporalImagen = auxFile.getAbsolutePath();
            setImageLabel(labDato, temporalImagen);
        } catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "Error al cargar el archivo");
        }
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

    void MostrarLista(){
        aux=frente;
        int cont=1;
        if(Final==null){
            JOptionPane.showConfirmDialog(null, "Lista vacia");
        } else{
            while(aux!=null){
                System.out.println(cont+"_"+aux.getMelodia()+" Direccion: "+ aux.getSiguiente());
                aux=aux.getSiguiente();
                cont++;
            }
        } 
    }
    
    void eliminarFrente(){
        if(frente==null && Final==null){ //Verificar si la lista tiene elementos
            JOptionPane.showMessageDialog(null,"Lista vacia");
        } else {
            if(Final.Siguiente==Final){
                JOptionPane.showMessageDialog(null,"Elemento borrado:"+frente.melodia);
                frente=null;
                actual=null;
                Final=null;
                labDato.setText(null);
                setImageLabel(labDato,null);
                labMelodia.setText(null);
            } else {
                JOptionPane.showMessageDialog(null,"Elemento borrado:"+frente.melodia);
                Final.setSiguiente(frente.Siguiente);
                frente=frente.Siguiente;
                if(actual==frente){
                    actual=frente.Siguiente;
                    labMelodia.setText(actual.melodia);
                    setImageLabel(labDato, actual.imagen);
                }
            }
        }
    }
    
    
    void eliminarFinal(){
        aux=frente;
        if(frente==null){
            JOptionPane.showMessageDialog(null,"Lista vacia");
        } else if(frente==Final){
            JOptionPane.showMessageDialog(null,"Elemento Eliminado:"+aux.melodia);
            Final=null;
            frente=null;
            actual=null;
            labDato.setText(null);
            setImageLabel(labDato,null);
            labMelodia.setText(null);
        }else{
            while(aux.getSiguiente()!=Final ){
                aux=aux.getSiguiente();
            }
            JOptionPane.showMessageDialog(null,"Elemento Eliminado:"+aux.Siguiente.melodia);
            if(actual==Final){
                actual=aux;
                labMelodia.setText(actual.melodia);
                setImageLabel(labDato, actual.imagen);
            }
            Final=aux;
            Final.setSiguiente(frente);
            
        }
    }
    
    void EliminarX(String x){
        aux=frente;
        Nodo aux2;
        if(frente==null && Final==null){
            JOptionPane.showMessageDialog(null,"Lista vacia");
        }else{
            if(frente==Final){
                if(!frente.melodia.equals(x)){
                    JOptionPane.showMessageDialog(null,"Elemento '"+x+"' no existe");
                }else{
                    JOptionPane.showMessageDialog(null,"Elemento Eliminado:"+aux.melodia);
                    Final.setSiguiente(null);
                    frente.setSiguiente(null);
                }
            }else{
                while(!aux.Siguiente.melodia.equals(x)){
                    aux=aux.getSiguiente();
                }
                aux2=aux.Siguiente.Siguiente;
                JOptionPane.showMessageDialog(null,"Elemento Eliminado:"+aux.Siguiente.melodia);
                aux.Siguiente.setSiguiente(null);
                aux.setSiguiente(aux2);
            }
        }
    }
    
    void eliminarActual(){
        aux = frente;
        if(Final == null){
        JOptionPane.showMessageDialog(null, "Lista vacia");
        }
        else {
            if(Final.Siguiente!=Final){
              JOptionPane.showMessageDialog(null, "El elemento a borrar es: " + actual.melodia);
              while(aux.Siguiente!=actual){
                  aux=aux.Siguiente;
              }
              aux.Siguiente=aux.Siguiente.Siguiente;
              actual = aux.Siguiente; //actual ahora es el siguiente
              labMelodia.setText(actual.melodia);
              setImageLabel(labDato,actual.imagen);
              JOptionPane.showMessageDialog(null, "Elemento eliminado");
            }
            else{
              JOptionPane.showMessageDialog(null, "Elemento eliminado" + actual.melodia);
              Final=null;
              frente=null;
              actual=null;
              labMelodia.setText("NULL");
              labDato.setText("NUll");
              JOptionPane.showMessageDialog(null, "Elemento eliminado");
            }
        }
    }
    
    boolean Vacia(){
        return frente==null;
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
        btnBorrarActual = new javax.swing.JButton();
        btnMostrar = new javax.swing.JButton();
        labDato = new javax.swing.JLabel();
        labMelodia = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnInicio = new javax.swing.JButton();
        btnAtras = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        btnAdelante = new javax.swing.JButton();
        btnFinal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

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

        btnBorrarActual.setText("Borrar Actual");
        btnBorrarActual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActualActionPerformed(evt);
            }
        });
        jPanel1.add(btnBorrarActual);

        btnMostrar.setBackground(new java.awt.Color(204, 255, 255));
        btnMostrar.setText("Mostrar todo");
        btnMostrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnMostrar);

        labDato.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labDato.setText("NULL");

        labMelodia.setText("Melodia");

        jPanel3.setLayout(new java.awt.GridLayout(1, 2, 140, 0));

        jPanel2.setLayout(new java.awt.GridLayout(1, 2, 20, 0));

        btnInicio.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnInicio.setText("<-");
        btnInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioActionPerformed(evt);
            }
        });
        jPanel2.add(btnInicio);

        btnAtras.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnAtras.setText("<");
        jPanel2.add(btnAtras);

        jPanel3.add(jPanel2);

        jPanel4.setLayout(new java.awt.GridLayout(1, 2, 20, 0));

        btnAdelante.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnAdelante.setText(">");
        btnAdelante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdelanteActionPerformed(evt);
            }
        });
        jPanel4.add(btnAdelante);

        btnFinal.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        btnFinal.setText("->");
        btnFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalActionPerformed(evt);
            }
        });
        jPanel4.add(btnFinal);

        jPanel3.add(jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labDato, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labMelodia, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(layout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 65, Short.MAX_VALUE))
                    .addComponent(labDato, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(labMelodia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarActionPerformed
        try{
            insertar();
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

    private void btnBorrarActualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActualActionPerformed
        try{
            eliminarActual();
        } catch(HeadlessException | NumberFormatException e){
            JOptionPane.showConfirmDialog(null, "Error en el valor proporcionado");
        }
    }//GEN-LAST:event_btnBorrarActualActionPerformed

    private void btnAdelanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdelanteActionPerformed
        // Ir al proximo elemento
        if(Vacia()){
            labDato.setText(null);
            labMelodia.setText("No hay elementos");
        } else{
            actual=actual.getSiguiente();
            setImageLabel(labDato,actual.imagen);
            labMelodia.setText(actual.melodia);
        }
    }//GEN-LAST:event_btnAdelanteActionPerformed

    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioActionPerformed
        //Ir al inicio de la lista
        if(Vacia()){
            labDato.setText(null);
            labMelodia.setText("No hay elementos");
        } else{
            actual=frente;
            setImageLabel(labDato,actual.imagen);
            labMelodia.setText(actual.melodia);
        }
    }//GEN-LAST:event_btnInicioActionPerformed

    private void btnFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalActionPerformed
        // ir al final de la lista
        if(Vacia()){
            labDato.setText(null);
            labMelodia.setText("No hay elementos");
        } else{
            actual=Final;
            setImageLabel(labDato,actual.imagen);
            labMelodia.setText(actual.melodia);
        }
    }//GEN-LAST:event_btnFinalActionPerformed

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
    private javax.swing.JButton btnAdelante;
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnBorrarActual;
    private javax.swing.JButton btnBorrarFinal;
    private javax.swing.JButton btnBorrarFrente;
    private javax.swing.JButton btnFinal;
    private javax.swing.JButton btnInicio;
    private javax.swing.JButton btnInsertar;
    private javax.swing.JButton btnMostrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel labDato;
    private javax.swing.JLabel labMelodia;
    // End of variables declaration//GEN-END:variables
}
