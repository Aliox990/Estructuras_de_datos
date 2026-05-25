import java.awt.CardLayout;

/**
 *
 * @author Alonso
 */
public class ReproductorDeMusica extends javax.swing.JFrame {
    
    private ListaPlaylist miLista = new ListaPlaylist();
    private ColaReproduccion miCola = new ColaReproduccion(); // Bicola (Deque)
    private PilaHistorial miHistorial = new PilaHistorial();  // Pila (Stack)
    
    private ReproductorWAV motorAudio = new ReproductorWAV();
    private Cancion cancionActual = null; // Monitorea qué nodo está sonando ahora
    
    // Banderas para bucles
    private boolean bucleColaActivo = false;
    private boolean bucleCancionActivo = false;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ReproductorDeMusica.class.getName());
    private javax.swing.Timer cronometroAudio;
    
    /**
     * Creates new form ReproductorDeMusica
     */
    public ReproductorDeMusica() {
        initComponents();
        
        cargarPlaylistDesdeArchivo();
        
        inicializarCronometro();
        
        panelPlaylist vistaPlaylist = new panelPlaylist();
        vistaPlaylist.configurarEstructuras(miLista, miCola);
        
        miLista.cargarEnTabla(vistaPlaylist);
        panelCentral.add(vistaPlaylist, java.awt.BorderLayout.CENTER);
    }
    
    private void cargarPlaylistDesdeArchivo() {
        java.io.File archivoPlaylist = new java.io.File("PlaylistCanciones.txt");
        
        // Si el archivo no existe aún (primera ejecución), no hay nada que cargar
        if (!archivoPlaylist.exists()) {
            return;
        }
        
        try {
            java.io.RandomAccessFile fichero = new java.io.RandomAccessFile(archivoPlaylist, "r");
            String registro;
            
            // Limpia la estructura en memoria antes de cargar para evitar duplicados
            miLista = new ListaPlaylist();
            
            // Lectura secuencial línea por línea
            while ((registro = fichero.readLine()) != null) {
                String[] campos = registro.split(";");
                
                // Asegura que la línea tenga los 4 campos obligatorios del nodo
                if (campos.length == 4) {
                    String titulo = campos[0];
                    String artista = campos[1];
                    String rutaAudio = campos[2];
                    String rutaImagen = campos[3];
                    
                    // Inserta el nodo en la Lista Doblemente Enlazada
                    miLista.insertarAlFinal(titulo, artista, rutaAudio, rutaImagen);
                }
            }
            fichero.close();
            
        } catch (java.io.IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error al sincronizar la base de datos musical", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Convierte los microsegundos nativos de Java a un formato visual clásico MM:SS
     */
    private String formatearTiempo(long microsegundos) {
        long segundosTotales = microsegundos / 1000000;
        long minutos = segundosTotales / 60;
        long segundos = segundosTotales % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }

    /**
     * Inicializa el cronometro que actualizará la barra gráfica cada 100 milisegundos
     */
    private void inicializarCronometro() {
        cronometroAudio = new javax.swing.Timer(100, new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                // Solo trabaja si el motor reporta que hay música sonando
                if (motorAudio.isEstaReproduciendo()) {
                    long actualStr = motorAudio.getPosicionActual();
                    long totalStr = motorAudio.getDuracionTotal();
                    
                    // Actualizar la barra de progreso
                    if (totalStr > 0) {
                        int progreso = (int) ((actualStr * 100) / totalStr);
                        jProgressBar1.setValue(progreso);
                    }
                    
                    // Actualizar las etiquetas de texto
                    lblTiempoActual.setText(formatearTiempo(actualStr));
                    lblTiempoTotal.setText(formatearTiempo(totalStr));
                    
                    // Si la canción llega a su final, el sistema simula un clic 
                    // en el botón "Siguiente", activando toda la lógica de la Cola o la Playlist.
                    if (motorAudio.alcanzoElFinal()) {
                        btnSiguiente.doClick(); 
                    }
                }
            }
        });
        
        // Encender el cronómetro de forma indefinida
        cronometroAudio.start();
    }
    
    /**
     * Actualiza la portada lateral y los textos de la barra inferior
     */
    private void actualizarInterfazReproductor() {
        if (cancionActual != null) {
            // Actualiza los textos inferiores
            lblDetalleCancion.setText(cancionActual.getTitulo() + " - " + cancionActual.getArtista());
            
            // Actualiza la portada lateral
            java.io.File archivoImagen = new java.io.File(cancionActual.getRutaImagen());
            if (archivoImagen.exists()) {
                javax.swing.ImageIcon iconoOriginal = new javax.swing.ImageIcon(cancionActual.getRutaImagen());
                java.awt.Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(130, 130, java.awt.Image.SCALE_SMOOTH);
                lblPortada.setIcon(new javax.swing.ImageIcon(imagenEscalada));
                lblPortada.setText("");
            } else {
                lblPortada.setIcon(null);
                lblPortada.setText("[Sin Portada]");
            }
        } else {
            lblDetalleCancion.setText("");
            lblPortada.setIcon(null);
            lblPortada.setText("");
        }
    }
    
    /**
     * Detecta si el usuario está viendo la pantalla de la cola y la actualiza en tiempo real
     */
    private void refrescarPantallaColaSiEstaActiva() {
        if (panelCentral.getComponentCount() > 0 && panelCentral.getComponent(0) instanceof panelCola) {
            panelCola vistaCola = (panelCola) panelCentral.getComponent(0);
            vistaCola.actualizarPanel(miCola);
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

        panelContenedor = new javax.swing.JPanel();
        panelLogin = new javax.swing.JPanel();
        panelAcomodoLogin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        txtContra = new javax.swing.JPasswordField();
        btnIngresar = new javax.swing.JButton();
        btnCrearUsuario = new javax.swing.JButton();
        btnMostrarUsuarios = new javax.swing.JButton();
        panelReproductor = new javax.swing.JPanel();
        panelMenu = new javax.swing.JPanel();
        btnPlaylist = new javax.swing.JButton();
        btnCola = new javax.swing.JButton();
        btnAdmin = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        lblPortada = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();
        lblTiempoActual = new javax.swing.JLabel();
        lblTiempoTotal = new javax.swing.JLabel();
        btnBucleCola = new javax.swing.JToggleButton();
        btnBucleCancion = new javax.swing.JToggleButton();
        btnPlayPausa = new javax.swing.JToggleButton();
        btnSiguiente = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        lblDetalleCancion = new javax.swing.JLabel();
        panelCentral = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));

        panelContenedor.setLayout(new java.awt.CardLayout());

        panelLogin.setBackground(new java.awt.Color(0, 51, 102));
        panelLogin.setLayout(new java.awt.GridBagLayout());

        panelAcomodoLogin.setBackground(new java.awt.Color(30, 30, 30));
        panelAcomodoLogin.setPreferredSize(new java.awt.Dimension(500, 375));
        panelAcomodoLogin.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Inicio de sesión");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panelAcomodoLogin.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel1.setBackground(new java.awt.Color(30, 30, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Usuario:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Contraseña:");

        txtUsuario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        txtContra.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        btnIngresar.setBackground(new java.awt.Color(0, 153, 153));
        btnIngresar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnIngresar.setForeground(new java.awt.Color(255, 255, 255));
        btnIngresar.setText("Ingresar");
        btnIngresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });

        btnCrearUsuario.setBackground(new java.awt.Color(0, 153, 153));
        btnCrearUsuario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCrearUsuario.setForeground(new java.awt.Color(255, 255, 255));
        btnCrearUsuario.setText("Crear Usuario");
        btnCrearUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCrearUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearUsuarioActionPerformed(evt);
            }
        });

        btnMostrarUsuarios.setBackground(new java.awt.Color(0, 153, 51));
        btnMostrarUsuarios.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnMostrarUsuarios.setForeground(new java.awt.Color(255, 255, 255));
        btnMostrarUsuarios.setText("Mostrar Usuarios");
        btnMostrarUsuarios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMostrarUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarUsuariosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnMostrarUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCrearUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGap(19, 19, 19)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(11, 11, 11)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtContra, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtContra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addComponent(btnIngresar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCrearUsuario)
                .addGap(37, 37, 37)
                .addComponent(btnMostrarUsuarios)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelAcomodoLogin.add(jPanel1, java.awt.BorderLayout.CENTER);

        panelLogin.add(panelAcomodoLogin, new java.awt.GridBagConstraints());

        panelContenedor.add(panelLogin, "pantalla_login");

        panelReproductor.setLayout(new java.awt.BorderLayout());

        panelMenu.setBackground(new java.awt.Color(30, 30, 30));
        panelMenu.setPreferredSize(new java.awt.Dimension(160, 600));

        btnPlaylist.setBackground(new java.awt.Color(0, 153, 153));
        btnPlaylist.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnPlaylist.setForeground(new java.awt.Color(255, 255, 255));
        btnPlaylist.setText("Playlist");
        btnPlaylist.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPlaylist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlaylistActionPerformed(evt);
            }
        });

        btnCola.setBackground(new java.awt.Color(0, 153, 153));
        btnCola.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCola.setForeground(new java.awt.Color(255, 255, 255));
        btnCola.setText("Cola");
        btnCola.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCola.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnColaActionPerformed(evt);
            }
        });

        btnAdmin.setBackground(new java.awt.Color(0, 153, 153));
        btnAdmin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAdmin.setForeground(new java.awt.Color(255, 255, 255));
        btnAdmin.setText("Admin");
        btnAdmin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdminActionPerformed(evt);
            }
        });

        btnCerrarSesion.setBackground(new java.awt.Color(153, 0, 51));
        btnCerrarSesion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrarSesion.setText("Cerrar sesión");
        btnCerrarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });

        lblPortada.setForeground(new java.awt.Color(255, 255, 255));
        lblPortada.setMaximumSize(new java.awt.Dimension(200, 200));
        lblPortada.setMinimumSize(new java.awt.Dimension(130, 130));
        lblPortada.setPreferredSize(new java.awt.Dimension(130, 130));

        javax.swing.GroupLayout panelMenuLayout = new javax.swing.GroupLayout(panelMenu);
        panelMenu.setLayout(panelMenuLayout);
        panelMenuLayout.setHorizontalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPlaylist, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCola, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addComponent(btnAdmin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addComponent(btnCerrarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addGroup(panelMenuLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(lblPortada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelMenuLayout.setVerticalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnPlaylist)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCola)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAdmin)
                .addGap(18, 18, 18)
                .addComponent(btnCerrarSesion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 294, Short.MAX_VALUE)
                .addComponent(lblPortada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelReproductor.add(panelMenu, java.awt.BorderLayout.LINE_START);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(25, 51, 80));
        jPanel3.setPreferredSize(new java.awt.Dimension(381, 135));

        jProgressBar1.setBackground(new java.awt.Color(204, 204, 204));
        jProgressBar1.setForeground(new java.awt.Color(255, 255, 255));
        jProgressBar1.setPreferredSize(new java.awt.Dimension(300, 10));

        lblTiempoActual.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTiempoActual.setForeground(new java.awt.Color(255, 255, 255));
        lblTiempoActual.setText("00:00");

        lblTiempoTotal.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTiempoTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblTiempoTotal.setText("00:00");

        btnBucleCola.setBackground(new java.awt.Color(25, 51, 80));
        btnBucleCola.setForeground(new java.awt.Color(255, 255, 255));
        btnBucleCola.setText("🔁");
        btnBucleCola.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBucleCola.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBucleColaActionPerformed(evt);
            }
        });

        btnBucleCancion.setBackground(new java.awt.Color(25, 51, 80));
        btnBucleCancion.setForeground(new java.awt.Color(255, 255, 255));
        btnBucleCancion.setText("🔂");
        btnBucleCancion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBucleCancion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBucleCancionActionPerformed(evt);
            }
        });

        btnPlayPausa.setBackground(new java.awt.Color(25, 51, 80));
        btnPlayPausa.setForeground(new java.awt.Color(255, 255, 255));
        btnPlayPausa.setText("⏯");
        btnPlayPausa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPlayPausa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayPausaActionPerformed(evt);
            }
        });

        btnSiguiente.setBackground(new java.awt.Color(25, 51, 80));
        btnSiguiente.setForeground(new java.awt.Color(255, 255, 255));
        btnSiguiente.setText("⏭");
        btnSiguiente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });

        btnAnterior.setBackground(new java.awt.Color(25, 51, 80));
        btnAnterior.setForeground(new java.awt.Color(255, 255, 255));
        btnAnterior.setText("⏮");
        btnAnterior.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorActionPerformed(evt);
            }
        });

        lblDetalleCancion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDetalleCancion.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTiempoActual)
                            .addComponent(btnBucleCola))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                        .addComponent(btnAnterior)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPlayPausa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSiguiente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 107, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBucleCancion)
                            .addComponent(lblTiempoTotal)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(lblDetalleCancion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(9, 9, 9)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(lblDetalleCancion, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBucleCola)
                    .addComponent(btnBucleCancion)
                    .addComponent(btnPlayPausa)
                    .addComponent(btnSiguiente)
                    .addComponent(btnAnterior))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTiempoActual)
                    .addComponent(lblTiempoTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        panelCentral.setLayout(new java.awt.BorderLayout());
        jPanel2.add(panelCentral, java.awt.BorderLayout.CENTER);

        panelReproductor.add(jPanel2, java.awt.BorderLayout.CENTER);

        panelContenedor.add(panelReproductor, "pantalla_reproductor");

        getContentPane().add(panelContenedor, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        String usuario = txtUsuario.getText();
        String contra = new String(txtContra.getPassword());

        Ingresar(usuario, contra);
    }//GEN-LAST:event_btnIngresarActionPerformed

    private void btnCrearUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearUsuarioActionPerformed
        String usuario = txtUsuario.getText();
        String contra = new String(txtContra.getPassword());
        
        registrarNuevoUsuario(usuario, contra, "usuario");
        
        //Reiniciar campos de texto
        txtUsuario.setText("");
        txtContra.setText("");
    }//GEN-LAST:event_btnCrearUsuarioActionPerformed

    private void btnMostrarUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarUsuariosActionPerformed
        mostrarUsuariosRegistrados();
    }//GEN-LAST:event_btnMostrarUsuariosActionPerformed

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        // Detenr cancion si se esta reproduciendo
        motorAudio.detener();
        cancionActual = null;

        // Regresar a la pantalla de login de forma instantánea
        CardLayout cl = (CardLayout) panelContenedor.getLayout();
        cl.show(panelContenedor, "pantalla_login");

        // Limpiar los campos del login por seguridad
        txtUsuario.setText("");
        txtContra.setText("");
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void btnAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdminActionPerformed
        panelAdmin vistaAdmin = new panelAdmin();
        vistaAdmin.configurarEstructura(miLista);
    
        // Removemr lo que este en el panel central y agrega el modulo admin
        panelCentral.removeAll();
        panelCentral.add(vistaAdmin, java.awt.BorderLayout.CENTER);
        panelCentral.revalidate();
        panelCentral.repaint();
    }//GEN-LAST:event_btnAdminActionPerformed

    private void btnPlaylistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlaylistActionPerformed
        cargarPlaylistDesdeArchivo();

        // Instanciar el panel de la playlist
        panelPlaylist vistaPlaylist = new panelPlaylist();
        vistaPlaylist.configurarEstructuras(miLista, miCola);
        
        miLista.cargarEnTabla(vistaPlaylist);
        
        // Limpia el centro y agrega la playlist
        panelCentral.removeAll();
        panelCentral.add(vistaPlaylist, java.awt.BorderLayout.CENTER);
        panelCentral.revalidate();
        panelCentral.repaint();
    }//GEN-LAST:event_btnPlaylistActionPerformed

    private void btnBucleColaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBucleColaActionPerformed
        bucleColaActivo = btnBucleCola.isSelected();
    }//GEN-LAST:event_btnBucleColaActionPerformed

    private void btnBucleCancionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBucleCancionActionPerformed
        bucleCancionActivo = btnBucleCancion.isSelected();
    }//GEN-LAST:event_btnBucleCancionActionPerformed

    private void btnPlayPausaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayPausaActionPerformed
        if (miLista.getInicio() == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "La playlist está vacía. Agrega canciones en el módulo de Admin.", "Aviso", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            btnPlayPausa.setSelected(false); // Descomprime el botón visualmente ya que no hay música
            return;
        }

        // Es la primera vez que se da Play o el reproductor estaba totalmente detenido
        if (cancionActual == null) {
            // Toma el primer nodo de la Lista Doblemente Enlazada
            cancionActual = miLista.getInicio();

            // Encender el reproductor con la ruta del archivo WAV
            motorAudio.reproducirDesdeCero(cancionActual.getRutaAudio());
            actualizarInterfazReproductor();
            btnPlayPausa.setText("⏸");
            return;
        }

        // Ya hay una canción en el flujo, alterna el estado del motor
        if (motorAudio.isEstaReproduciendo()) {
            motorAudio.pausar();
            btnPlayPausa.setText("▶");
        } else {
            motorAudio.reanudar();
            btnPlayPausa.setText("⏸");
        }
    }//GEN-LAST:event_btnPlayPausaActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        if (cancionActual == null) return; // Si no hay nada sonando, no hacemos nada

        // Pioridad maxima: Bucle de canción única
        if (bucleCancionActivo) {
            motorAudio.reproducirDesdeCero(cancionActual.getRutaAudio());
            refrescarPantallaColaSiEstaActiva();
            return;
        }

        // PUSH: Guardar la canción actual en el Historial (Pila) antes de cambiar
        miHistorial.apilar(cancionActual.getTitulo(), cancionActual.getArtista(), 
                           cancionActual.getRutaAudio(), cancionActual.getRutaImagen());

        // Prioridad dos: Revisar si hay música en espera en la Cola (Queue)
        if (!miCola.estaVacia()) {
            // DEQUEUE: Saca la primera en la fila
            Cancion siguienteCola = miCola.desencolar();

            // Si el bucle de cola está activo, la volvemos a formar al final (ENQUEUE)
            if (bucleColaActivo) {
                miCola.encolar(siguienteCola.getTitulo(), siguienteCola.getArtista(), 
                               siguienteCola.getRutaAudio(), siguienteCola.getRutaImagen());
            }

            cancionActual = siguienteCola;
            motorAudio.reproducirDesdeCero(cancionActual.getRutaAudio());
            actualizarInterfazReproductor();
            btnPlayPausa.setText("⏸");
            btnPlayPausa.setSelected(true);
            refrescarPantallaColaSiEstaActiva();
            return;
        }

        // Prioridad tres: Avanza normalmente en la Playlist (Lista Doble)
        if (cancionActual.getSiguiente() != null) {
            cancionActual = cancionActual.getSiguiente();
            motorAudio.reproducirDesdeCero(cancionActual.getRutaAudio());
            actualizarInterfazReproductor();
            btnPlayPausa.setText("⏸");
            btnPlayPausa.setSelected(true);
        } else {
            // Se acabó la playlist entera
            motorAudio.detener();
            btnPlayPausa.setText("▶");
            btnPlayPausa.setSelected(false);
            cancionActual = null;
        }
        refrescarPantallaColaSiEstaActiva();
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        if (cancionActual == null) return;

        // Revisar si hay algo en el historial (Pila)
        if (!miHistorial.estaVacia()) {

            // Usa la Bicola para guardar la canción actual enviándola al inicio de la espera
            miCola.devolverAlFrente(cancionActual.getTitulo(), cancionActual.getArtista(), 
                                    cancionActual.getRutaAudio(), cancionActual.getRutaImagen());

            // POP: Saca la última canción que escuchamos del historial
            Cancion cancionAnterior = miHistorial.desapilar();

            // La reproduce 
            cancionActual = cancionAnterior;
            motorAudio.reproducirDesdeCero(cancionActual.getRutaAudio());
            actualizarInterfazReproductor();

            btnPlayPausa.setText("⏸");
            btnPlayPausa.setSelected(true);

        } else {
            // Si la Pila está vacía (es la primera canción que suena), simplemente la reinicia
            motorAudio.reproducirDesdeCero(cancionActual.getRutaAudio());
        }
        refrescarPantallaColaSiEstaActiva();
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnColaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnColaActionPerformed
        
        panelCola vistaCola = new panelCola();

        vistaCola.actualizarPanel(miCola);

        panelCentral.removeAll();
        panelCentral.add(vistaCola, java.awt.BorderLayout.CENTER);
        panelCentral.revalidate();
        panelCentral.repaint();
    }//GEN-LAST:event_btnColaActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new ReproductorDeMusica().setVisible(true));
    }
    
    private void Ingresar(String usuario, String contra){
        // Archivo de texto secuencial
        java.io.File archivo = new java.io.File("BaseDatos.txt");

        try {
            // Crear el archivo si no existe e inserta accesos de prueba
            if (!archivo.exists()) {
                archivo.createNewFile();
                java.io.RandomAccessFile f = new java.io.RandomAccessFile(archivo, "rw");
                f.writeBytes("admin;1234;administrador\n");
                f.writeBytes("usuario1;0000;usuario\n");
                f.close();
            }

            java.io.RandomAccessFile fichero = new java.io.RandomAccessFile(archivo, "r");
            String registro;
            boolean accesoConcedido = false;
            String rolObtenido = "";

            while ((registro = fichero.readLine()) != null) {
                String[] campos = registro.split(";");
                if (campos.length == 3) {
                    if (usuario.equals(campos[0]) && contra.equals(campos[1])) {
                        accesoConcedido = true;
                        rolObtenido = campos[2]; // "administrador" o "usuario"
                        break;
                    }
                }
            }
            fichero.close();

            if (accesoConcedido) {
                // --- CONTROL DE ROLES ---
                if (rolObtenido.equalsIgnoreCase("usuario")) {
                    // Si es un usuario normal oculta el botón de administración
                    btnAdmin.setVisible(false);
                } else {
                    btnAdmin.setVisible(true);
                }

                // --- CAMBIO DE PANTALLA ---
                // Recuperar el manejador de la baraja y le pedimos mostrar la carta del reproductor
                CardLayout cl = (CardLayout) panelContenedor.getLayout();
                cl.show(panelContenedor, "pantalla_reproductor");

            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }

        } catch (java.io.IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error de lectura de archivo", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void registrarNuevoUsuario(String usuario, String contra, String rol) {
        // Validacion primaria de campos vacios
        if (usuario.trim().isEmpty() || contra.trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "No se permiten campos vacios", "Advertencia", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        java.io.File archivo = new java.io.File("BaseDatos.txt");
        try {
            java.io.RandomAccessFile fichero = new java.io.RandomAccessFile(archivo, "rw");

            // Posicionar el puntero al final para insercion secuencial
            fichero.seek(fichero.length());

            // Escribir la linea con el formato establecido
            fichero.writeBytes(usuario + ";" + contra + ";" + rol + "\n");
            fichero.close();

            javax.swing.JOptionPane.showMessageDialog(this, "Usuario creado exitosamente con rol: " + rol);

        } catch (java.io.IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error al escribir en la base de datos", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void mostrarUsuariosRegistrados() {
        java.io.File archivo = new java.io.File("BaseDatos.txt");

        if (!archivo.exists()) {
            javax.swing.JOptionPane.showMessageDialog(this, "No hay usuarios registrados aun");
            return;
        }

        try {
            java.io.RandomAccessFile fichero = new java.io.RandomAccessFile(archivo, "r");
            String registro;
            StringBuilder listaCompleta = new StringBuilder("=== USUARIOS EN EL SISTEMA ===\n\n");

            while ((registro = fichero.readLine()) != null) {
                String[] campos = registro.split(";");
                if (campos.length == 3) {
                    listaCompleta.append("User: ").append(campos[0])
                                 .append(" | Pass: ").append(campos[1])
                                 .append(" | Rol: ").append(campos[2]).append("\n");
                }
            }
            fichero.close();

            javax.swing.JOptionPane.showMessageDialog(this, listaCompleta.toString(), "Registros Persistentes", javax.swing.JOptionPane.INFORMATION_MESSAGE);

        } catch (java.io.IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error al leer la base de datos", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdmin;
    private javax.swing.JButton btnAnterior;
    private javax.swing.JToggleButton btnBucleCancion;
    private javax.swing.JToggleButton btnBucleCola;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnCola;
    private javax.swing.JButton btnCrearUsuario;
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnMostrarUsuarios;
    private javax.swing.JToggleButton btnPlayPausa;
    private javax.swing.JButton btnPlaylist;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JLabel lblDetalleCancion;
    private javax.swing.JLabel lblPortada;
    private javax.swing.JLabel lblTiempoActual;
    private javax.swing.JLabel lblTiempoTotal;
    private javax.swing.JPanel panelAcomodoLogin;
    private javax.swing.JPanel panelCentral;
    private javax.swing.JPanel panelContenedor;
    private javax.swing.JPanel panelLogin;
    private javax.swing.JPanel panelMenu;
    private javax.swing.JPanel panelReproductor;
    private javax.swing.JPasswordField txtContra;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}

