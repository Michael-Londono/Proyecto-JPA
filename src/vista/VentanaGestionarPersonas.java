package vista;

import modelo.PersonaDAO;
import modelo.PersonaVO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class VentanaGestionarPersonas extends JFrame {

    private JTextField txtNombre, txtDocumento, txtDireccion, txtTelefono;
    private JButton btnRegistrar;
    private JComboBox<String> comboPersonas;
    private JTable tablaPersonas;
    private DefaultTableModel modeloTabla;
    private JLabel lblMensaje;

    public VentanaGestionarPersonas() {
        setTitle("Registro de Persona");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblTitulo = new JLabel("GESTIONAR PERSONAS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBounds(280, 10, 300, 30);
        add(lblTitulo);

        addLabel("Documento", 30, 60);
        txtDocumento = addTextField(120, 60, 200);

        addLabel("Nombre", 360, 60);
        txtNombre = addTextField(460, 60, 200);

        addLabel("Dirección", 30, 100);
        txtDireccion = addTextField(120, 100, 200);

        addLabel("Teléfono", 360, 100);
        txtTelefono = addTextField(460, 100, 200);

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(330, 150, 120, 30);
        add(btnRegistrar);

        JSeparator separator = new JSeparator();
        separator.setBounds(30, 200, 720, 10);
        add(separator);

        addLabel("Combo Personas", 30, 220);
        comboPersonas = new JComboBox<>();
        comboPersonas.setBounds(150, 220, 300, 25);
        add(comboPersonas);

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Documento");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Dirección");
        modeloTabla.addColumn("Teléfono");

        tablaPersonas = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaPersonas);
        scrollPane.setBounds(30, 260, 720, 250);
        add(scrollPane);

        lblMensaje = new JLabel("");
        lblMensaje.setBounds(30, 520, 500, 25);
        lblMensaje.setForeground(Color.BLUE);
        add(lblMensaje);

        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String documentoStr = txtDocumento.getText().trim();
                String nombre = txtNombre.getText().trim();
                String direccion = txtDireccion.getText().trim();
                String telefono = txtTelefono.getText().trim();

                if (!documentoStr.isEmpty() && !nombre.isEmpty()) {
                    try {
                        int documento = Integer.parseInt(documentoStr);
                        PersonaDAO dao = new PersonaDAO();
                        String mensaje = dao.registrarPersona(nombre, documento, direccion, telefono);
                        lblMensaje.setText(mensaje);
                        cargarComboYTabla();
                        txtDocumento.setText("");
                        txtNombre.setText("");
                        txtDireccion.setText("");
                        txtTelefono.setText("");
                    } catch (NumberFormatException ex) {
                        lblMensaje.setText("Documento debe ser un número.");
                    }
                } else {
                    lblMensaje.setText("Debe ingresar al menos documento y nombre.");
                }
            }
        });

        cargarComboYTabla();
    }

    private void cargarComboYTabla() {
        PersonaDAO dao = new PersonaDAO();
        List<PersonaVO> lista = dao.consultarPersonas();

        comboPersonas.removeAllItems();
        modeloTabla.setRowCount(0);

        for (PersonaVO p : lista) {
            comboPersonas.addItem(p.getDocumento() + " - " + p.getNombre());
            modeloTabla.addRow(new Object[]{p.getDocumento(), p.getNombre(), p.getDireccion(), p.getTelefono()});
        }
    }

    private void addLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 100, 25);
        add(label);
    }

    private JTextField addTextField(int x, int y, int width) {
        JTextField txt = new JTextField();
        txt.setBounds(x, y, width, 25);
        add(txt);
        return txt;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaGestionarPersonas().setVisible(true));
    }
}
