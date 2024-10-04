import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FrmEditor extends JFrame {
    private Dibujo dibujo;
    private Trazo trazoSeleccionado;
    private Point puntoInicial;

    private JButton btnAgregar, btnEliminar, btnGuardar, btnCargar;
    private JComboBox<String> comboTipos;

    public FrmEditor() {
        dibujo = new Dibujo();
        setTitle("Editor de Dibujos Vectoriales");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelBotones = new JPanel();
        String[] tipos = {"Linea", "Cuadrado", "Circulo", "Triangulo"};
        comboTipos = new JComboBox<>(tipos);
        btnAgregar = new JButton("Agregar Trazo");
        btnEliminar = new JButton("Eliminar Trazo");
        btnGuardar = new JButton("Guardar");
        btnCargar = new JButton("Cargar");

        panelBotones.add(comboTipos);
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCargar);
        add(panelBotones, BorderLayout.SOUTH);

        DibujoPanel panelDibujo = new DibujoPanel();
        add(panelDibujo, BorderLayout.CENTER);

        btnEliminar.addActionListener(e -> {
            if (trazoSeleccionado != null) {
                dibujo.eliminar(trazoSeleccionado.getX1(), trazoSeleccionado.getY1());
                repaint();
            }
            
        });

        btnGuardar.addActionListener(e -> btnGuardarClick(e));
        btnCargar.addActionListener(e -> {
            btnCargarClick(e);  
            panelDibujo.repaint();
        });
    }

    private void btnGuardarClick(ActionEvent evt) {
        String nombreArchivo = Archivo.elegirArchivo();
        String[] lineas = dibujo.aArchivo();
        if (Archivo.guardarArchivo(nombreArchivo, lineas)) {
            JOptionPane.showMessageDialog(this, "Dibujo guardado correctamente");
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el dibujo");
        }
    }

    private void btnCargarClick(ActionEvent evt){
        String nombreArchivo = Archivo.elegirArchivo();
        dibujo.desdeArchivo(nombreArchivo);
        
    }

    class DibujoPanel extends JPanel {
        public DibujoPanel() {
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    puntoInicial = e.getPoint(); // Guardar punto inicial
                }

                public void mouseReleased(MouseEvent e) {
                    Point puntoFinal = e.getPoint(); // Guardar punto final
                    try {
                        Tipotrazo tipoDeTrazo = Tipotrazo.valueOf(((String) comboTipos.getSelectedItem()).toUpperCase());
                        dibujo.agregar(new Trazo(tipoDeTrazo, puntoInicial.x, puntoInicial.y, puntoFinal.x, puntoFinal.y));
                        repaint();
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(null, "Tipo de trazo no válido");
                    }
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            dibujo.dibujar(g);
        }
    }
}
