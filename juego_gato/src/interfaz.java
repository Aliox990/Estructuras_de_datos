/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import javax.swing.ImageIcon;
/**
 *
 * @author Alonso
 */
public class interfaz extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(interfaz.class.getName());

    JuegoGato juego = new JuegoGato();
    javax.swing.JButton[][] botones; // Matriz visual de botones
    
    /**
     * Creates new form interfaz
     */
    public interfaz() {
        initComponents();
        configurarBotones();
        this.setIconImage(new ImageIcon(getClass().getResource("/icons/icono_gato.png")).getImage());
        actualizarEtiquetaTurno();
        bloquearTablero();
    }
    
    private void configurarBotones() {
        botones = new javax.swing.JButton[][] {
            {Boton1, Boton2, Boton3},
            {Boton4, Boton5, Boton6},
            {Boton7, Boton8, Boton9}
        };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int f = i;
                final int c = j;
                botones[i][j].addActionListener(e -> realizarJugada(f, c));
            }
        }
    }
    
    private void realizarJugada(int fila, int col) {
        
        int valorFicha = (juego.jugadorActual == juego.jugadorX) ? 1 : 2;
        juego.tablero[fila][col] = valorFicha;

        botones[fila][col].setText(juego.jugadorActual.getTurno()); // Pone "X" u "O"
        botones[fila][col].setEnabled(false);
        
        if (juego.jugadorActual == juego.jugadorX) {
            botones[fila][col].setForeground(java.awt.Color.BLUE);
        } else {
            botones[fila][col].setForeground(java.awt.Color.RED);
        }

        int resultado = juego.verificarGanador();
        
        if (resultado != 0) {
            
            terminarPartida(resultado); 
        } else if (esEmpate()) {
            terminarPartida(0); 
        } else {
            juego.cambiarTurno();
            actualizarEtiquetaTurno(); 
        }
    }
    
    private boolean esEmpate() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (juego.tablero[i][j] == 0) {
                    return false; // Todavía hay al menos una casilla vacía
                }
            }
        }
        return true; // Tablero lleno
    }
    
    private void terminarPartida(int resultado) {
        String mensaje = "";
        switch (resultado) {
            case 1 -> {
                mensaje = "¡Ganó " + juego.jugadorX.getNombre() + "!";
                juego.jugadorX.setPuntos(juego.jugadorX.getPuntos() + 1);
            }
            case 2 -> {
                mensaje = "¡Ganó " + juego.jugadorO.getNombre() + "!";
                juego.jugadorO.setPuntos(juego.jugadorO.getPuntos() + 1);
            }
            default -> mensaje = "¡Es un empate!";
        }
        
        javax.swing.JOptionPane.showMessageDialog(this, mensaje);
        
        Puntos_1.setText("Puntos: " + juego.jugadorX.getPuntos());
        Puntos_2.setText("Puntos: " + juego.jugadorO.getPuntos());
        
        juego.partidasJugadas++; 
        
        if (juego.partidasJugadas >= 5) {
            finalizarSesion(); 
        } else {
            reiniciarTableroParaSiguientePartida();
        }
    }
    
    private void reiniciarTableroParaSiguientePartida() {
        // Limpiar lógica
        juego.inicializarTablero();
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botones[i][j].setText("");
                botones[i][j].setEnabled(true);
                botones[i][j].setForeground(java.awt.Color.BLACK);
            }
        }
    }
    
    private void finalizarSesion() {
        Jugador ganadorSesion = null;
        String mensaje = "--- FIN DE LA Partida ---\n\n";

        if (juego.jugadorX.getPuntos() > juego.jugadorO.getPuntos()) {
            ganadorSesion = juego.jugadorX;
            mensaje += "¡El GANADOR DEFINITIVO es " + ganadorSesion.getNombre() + "!\nPuntos totales: " + ganadorSesion.getPuntos();
        } else if (juego.jugadorO.getPuntos() > juego.jugadorX.getPuntos()) {
            ganadorSesion = juego.jugadorO;
            mensaje += "¡El GANADOR DEFINITIVO es " + ganadorSesion.getNombre() + "!\nPuntos totales: " + ganadorSesion.getPuntos();
        } else {
            mensaje += "¡Ha sido un EMPATE GLOBAL! No hay ganador para el ranking.";
        }

        if (ganadorSesion != null) {
            juego.actualizarRanking(ganadorSesion);
            mensaje += "\n\n(Ranking actualizado si el puntaje fue suficiente)";
        }

        javax.swing.JOptionPane.showMessageDialog(this, mensaje);

        bloquearTablero(); 
        BtnIniciar.setEnabled(true); 
        Jugador_1.setText("Esperando...");
        Jugador_2.setText("Esperando...");
    }
    
    private void reiniciarSesionCompleta() {
        // Reiniciar puntos
        juego.jugadorX.setPuntos(0);
        juego.jugadorO.setPuntos(0);

        // Reiniciar contador de partidas
        juego.partidasJugadas = 0; 

        // Reiniciar etiquetas visuales
        Puntos_1.setText("Puntos: 0");
        Puntos_2.setText("Puntos: 0");

        // Limpiar el tablero visual y reactivar botones
        reiniciarTableroParaSiguientePartida();
    }
    
    private void actualizarEtiquetaTurno() {
        if (juego.jugadorActual == juego.jugadorX) {
            Jugador_1.setForeground(java.awt.Color.BLUE);
            Jugador_2.setForeground(java.awt.Color.BLACK); 
            
        } else {
            Jugador_1.setForeground(java.awt.Color.BLACK);
            Jugador_2.setForeground(java.awt.Color.RED);
        }
    }
    
    private void bloquearTablero() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botones[i][j].setEnabled(false);
            }
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

        jPanel1 = new javax.swing.JPanel();
        Boton1 = new javax.swing.JButton();
        Boton2 = new javax.swing.JButton();
        Boton3 = new javax.swing.JButton();
        Boton4 = new javax.swing.JButton();
        Boton5 = new javax.swing.JButton();
        Boton6 = new javax.swing.JButton();
        Boton7 = new javax.swing.JButton();
        Boton8 = new javax.swing.JButton();
        Boton9 = new javax.swing.JButton();
        Jugador_1 = new javax.swing.JLabel();
        Jugador_2 = new javax.swing.JLabel();
        Puntos_1 = new javax.swing.JLabel();
        Puntos_2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        BtnIniciar = new javax.swing.JButton();
        BtnReinicio = new javax.swing.JButton();
        BtnRanking = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(400, 450));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setLayout(new java.awt.GridLayout(3, 3, 5, 5));

        Boton1.setFont(new java.awt.Font("Segoe UI", 1, 78)); // NOI18N
        jPanel1.add(Boton1);

        Boton2.setFont(new java.awt.Font("Segoe UI", 1, 78)); // NOI18N
        Boton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Boton2ActionPerformed(evt);
            }
        });
        jPanel1.add(Boton2);

        Boton3.setFont(new java.awt.Font("Segoe UI", 1, 78)); // NOI18N
        jPanel1.add(Boton3);

        Boton4.setFont(new java.awt.Font("Segoe UI", 1, 78)); // NOI18N
        jPanel1.add(Boton4);

        Boton5.setFont(new java.awt.Font("Segoe UI", 1, 78)); // NOI18N
        jPanel1.add(Boton5);

        Boton6.setFont(new java.awt.Font("Segoe UI", 1, 78)); // NOI18N
        jPanel1.add(Boton6);

        Boton7.setFont(new java.awt.Font("Segoe UI", 1, 78)); // NOI18N
        jPanel1.add(Boton7);

        Boton8.setFont(new java.awt.Font("Segoe UI", 1, 78)); // NOI18N
        jPanel1.add(Boton8);

        Boton9.setFont(new java.awt.Font("Segoe UI", 1, 78)); // NOI18N
        jPanel1.add(Boton9);

        Jugador_1.setFont(new java.awt.Font("Segoe UI Historic", 1, 14)); // NOI18N
        Jugador_1.setText("Jugador 1");
        Jugador_1.setToolTipText("");

        Jugador_2.setFont(new java.awt.Font("Segoe UI Historic", 1, 14)); // NOI18N
        Jugador_2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Jugador_2.setText("Jugador 2");
        Jugador_2.setToolTipText("");
        Jugador_2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        Puntos_1.setText("Puntos 1");

        Puntos_2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Puntos_2.setText("Puntos 2");

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        BtnIniciar.setBackground(new java.awt.Color(204, 255, 204));
        BtnIniciar.setText("Iniciar partida");
        BtnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnIniciarActionPerformed(evt);
            }
        });

        BtnReinicio.setBackground(new java.awt.Color(255, 102, 102));
        BtnReinicio.setText("Reiniciar");
        BtnReinicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnReinicioActionPerformed(evt);
            }
        });

        BtnRanking.setBackground(new java.awt.Color(204, 255, 255));
        BtnRanking.setText("Ranking");
        BtnRanking.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRankingActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtnIniciar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BtnReinicio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                    .addComponent(BtnRanking, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BtnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnReinicio, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnRanking, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Puntos_1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Jugador_1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(132, 132, 132)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Jugador_2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(Puntos_2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Jugador_1)
                    .addComponent(Jugador_2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Puntos_1)
                    .addComponent(Puntos_2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Boton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Boton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Boton2ActionPerformed

    private void BtnRankingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRankingActionPerformed
        StringBuilder sb = new StringBuilder();
        sb.append("=== SALÓN DE LA FAMA (TOP 5) ===\n\n");

        for (int i = 0; i < 5; i++) {
            Jugador j = juego.ranking[i]; 
            if (j != null) {
                sb.append(i+1).append(". ").append(j.getNombre()).append("  -  ").append(j.getPuntos()).append(" pts\n");
            } else {
                sb.append(i+1).append(". (Vacío)\n");
            }
        }
    
    javax.swing.JOptionPane.showMessageDialog(this, sb.toString());
    }//GEN-LAST:event_BtnRankingActionPerformed

    private void BtnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnIniciarActionPerformed
        String nombre1 = javax.swing.JOptionPane.showInputDialog(this, "Escribe el nombre del Jugador 1 (X):");

        if (nombre1 == null || nombre1.trim().isEmpty()) {
            nombre1 = "Jugador 1";
        }

        String nombre2 = javax.swing.JOptionPane.showInputDialog(this, "Escribe el nombre del Jugador 2 (O):");
        if (nombre2 == null || nombre2.trim().isEmpty()) {
            nombre2 = "Jugador 2";
        }

        juego.jugadorX.setNombre(nombre1);
        juego.jugadorO.setNombre(nombre2);

        Jugador_1.setText(nombre1);
        Jugador_2.setText(nombre2);

        reiniciarSesionCompleta(); 

        BtnIniciar.setEnabled(false);

    }//GEN-LAST:event_BtnIniciarActionPerformed

    private void BtnReinicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnReinicioActionPerformed
        int respuesta = javax.swing.JOptionPane.showConfirmDialog(
            this, 
            "¿Estás seguro de que quieres reiniciar el juego?\nSe perderán los puntos y el progreso actual.", 
            "Reiniciar Juego", 
            javax.swing.JOptionPane.YES_NO_OPTION
        );

        if (respuesta == javax.swing.JOptionPane.YES_OPTION) {

            reiniciarSesionCompleta();

            bloquearTablero();

            BtnIniciar.setEnabled(true);

            Jugador_1.setText("Jugador 1");
            Jugador_2.setText("Jugador 2");

            juego.jugadorX.setNombre("Jugador 1");
            juego.jugadorO.setNombre("Jugador 2");
        }

    }//GEN-LAST:event_BtnReinicioActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new interfaz().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Boton1;
    private javax.swing.JButton Boton2;
    private javax.swing.JButton Boton3;
    private javax.swing.JButton Boton4;
    private javax.swing.JButton Boton5;
    private javax.swing.JButton Boton6;
    private javax.swing.JButton Boton7;
    private javax.swing.JButton Boton8;
    private javax.swing.JButton Boton9;
    private javax.swing.JButton BtnIniciar;
    private javax.swing.JButton BtnRanking;
    private javax.swing.JButton BtnReinicio;
    private javax.swing.JLabel Jugador_1;
    private javax.swing.JLabel Jugador_2;
    private javax.swing.JLabel Puntos_1;
    private javax.swing.JLabel Puntos_2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
