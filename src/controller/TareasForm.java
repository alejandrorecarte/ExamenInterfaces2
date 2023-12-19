package controller;

import com.toedter.calendar.JCalendar;
import model.Tarea;
import view.TareasTableModel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Date;


public class TareasForm {
    ;
    private JLabel lNombre;
    private JLabel lDescripcion;
    private JLabel lFecha;
    private JLabel lHora;
    private JLabel lRealizada;
    private JLabel lPrioridad;
    private JTextField tfNombre;
    private JTextField tfDescripcion;
    private JTextField tfHora;
    private JComboBox cbRealizada;
    private JComboBox cbPrioridad;
    private JPanel p;
    private JButton bAnyadir;
    private JButton bModificar;
    private JButton bEliminar;
    private JScrollPane sp;
    private JTable tList;
    private JPanel panelBotones;
    private JButton bAyuda;
    private JLabel lFiltrar;
    private JComboBox cbFiltrar;
    private JButton bCambiarEstado;
    private JButton bDeseleccionar;
    private JButton bSeleccionarFecha;
    private JLabel lColon;
    private JTextField tfMinuto;
    private JLabel lColon2;
    private JTextField tfSegundo;
    private JButton bBuscar;
    private static ArrayList<Tarea> tareas;
    private static JFrame frame;
    private Date fecha = new Date();

    public TareasForm() {
        try{
            tareas = Streams.importarTareas("src/model/data/ArrayListTarea");
        }catch(Exception e){
            JOptionPane.showConfirmDialog(frame, "Error al importar las tareas.", "Mensaje de error", JOptionPane.WARNING_MESSAGE);
            tareas = new ArrayList<Tarea>();
            e.printStackTrace();
        }

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(JOptionPane.showConfirmDialog(frame, "¿Seguro que desea salir de la aplicación?", "Confirmación", JOptionPane.INFORMATION_MESSAGE) == 0){
                    try {
                        Streams.exportarTareas(tareas);
                    } catch (IOException ex) {
                        JOptionPane.showConfirmDialog(frame, "Error al guardar las tareas.", "Mensaje de error", JOptionPane.WARNING_MESSAGE);
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        actualizarTabla(tList, tareas);
        bAnyadir.setEnabled(true);
        bModificar.setEnabled(false);
        bEliminar.setEnabled(false);
        bCambiarEstado.setEnabled(false);
        bDeseleccionar.setEnabled(false);
        tfHora.setColumns(2);
        tfMinuto.setColumns(2);
        tfSegundo.setColumns(2);
        TableRowSorter<TareasTableModel> sorter = new TableRowSorter<>(new TareasTableModel(tareas));
        ArrayList list = new ArrayList();
        list.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
        sorter.setSortKeys(list);
        sorter.sort();
        tList.setRowSorter(sorter);

        bAnyadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tfNombre.getText().equals("") || fecha == null || tfHora.getText().equals("")) {
                    JOptionPane.showMessageDialog(frame, "No se puede añadir porque un campo obligatorio está vacío.", "Mensaje de error", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    fecha.setHours(Integer.parseInt(tfHora.getText()));
                    fecha.setMinutes(Integer.parseInt(tfMinuto.getText()));
                    fecha.setSeconds(Integer.parseInt(tfSegundo.getText()));
                    tareas.add(new Tarea(tfNombre.getText(),
                            fecha,
                            tfDescripcion.getText(),
                            (String) cbRealizada.getSelectedItem(),
                            Integer.parseInt((String) cbPrioridad.getSelectedItem())));
                    actualizarTabla(tList, tareas);
                    tList.repaint();
                    tList.revalidate();
                    tfNombre.setText("");
                    tfDescripcion.setText("");
                    fecha = null;
                    bSeleccionarFecha.setText("Seleccionar");
                    tfHora.setText("");
                    tfMinuto.setText("");
                    tfSegundo.setText("");
                    cbRealizada.setSelectedIndex(0);
                    cbPrioridad.setSelectedIndex(0);
                    bAnyadir.setEnabled(true);
                    bModificar.setEnabled(false);
                    bCambiarEstado.setEnabled(false);
                    JOptionPane.showConfirmDialog(frame, "Se ha introducido al usuario correctamente", "Confirmación", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        bSeleccionarFecha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frameFecha = new JFrame("Seleccionar fecha");
                JPanel pFecha = new JPanel();
                JCalendar calendar = new JCalendar();
                if(fecha != null) {
                    calendar.setDate(fecha);
                }
                JButton bAceptar = new JButton("Aceptar");

                frameFecha.add(pFecha);
                pFecha.add(calendar);
                pFecha.add(bAceptar);

                frameFecha.setVisible(true);
                frameFecha.setBounds(bSeleccionarFecha.getX() ,bSeleccionarFecha.getY(),250,250);

                bAceptar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        fecha = calendar.getDate();
                        frameFecha.dispose();
                        bSeleccionarFecha.setText(fecha.getDate() + " / " + fecha.getMonth() + " / 20" + (fecha.getYear()%100));
                    }
                });
            }
        });
        bDeseleccionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tList.repaint();
                tList.revalidate();
                tfNombre.setText("");
                tfDescripcion.setText("");
                fecha = null;
                bSeleccionarFecha.setText("Seleccionar");
                tfHora.setText("");
                tfMinuto.setText("");
                tfSegundo.setText("");
                cbRealizada.setSelectedIndex(0);
                cbPrioridad.setSelectedIndex(0);
                bAnyadir.setEnabled(true);
                bModificar.setEnabled(false);
                bCambiarEstado.setEnabled(false);
                bDeseleccionar.setEnabled(false);
                bEliminar.setEnabled(false);
            }
        });
        tList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(tList.getSelectedRow() != -1){
                    Tarea tarea = tareas.get(tList.getSelectedRow());
                    tfNombre.setEditable(true);
                    tfDescripcion.setEditable(true);
                    tfHora.setEditable(true);
                    tfMinuto.setEditable(true);
                    tfSegundo.setEditable(true);
                    cbRealizada.setEnabled(true);
                    cbPrioridad.setEnabled(true);
                    bAnyadir.setEnabled(true);
                    bModificar.setEnabled(false);
                    tfNombre.setText(String.valueOf(tarea.getNombre()));
                    tfDescripcion.setText(tarea.getDescripcion());
                    fecha = tarea.getFecha();
                    bSeleccionarFecha.setText(fecha.getDate() + " / " + fecha.getMonth() + " / 20" + (fecha.getYear()%100));
                    tfHora.setText(String.valueOf(tarea.getFecha().getHours()));
                    tfMinuto.setText(String.valueOf(tarea.getFecha().getMinutes()));
                    tfSegundo.setText(String.valueOf(tarea.getFecha().getSeconds()));
                    cbPrioridad.setSelectedItem(String.valueOf(tarea.getPrioridad()));
                    if(tarea.getRealizada().equals("Completado")){
                        cbRealizada.setSelectedItem("Completado");
                    }else{
                        cbRealizada.setSelectedItem("Pendiente");
                    }
                    bModificar.setEnabled(true);
                    bEliminar.setEnabled(true);
                    bAnyadir.setEnabled(false);
                    bCambiarEstado.setEnabled(true);
                    bDeseleccionar.setEnabled(true);
                    bSeleccionarFecha.setEnabled(true);
                }
            }
        });

        bEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(frame, "¿Seguro que desea eliminar la tarea seleccionada?", "Confirmación", JOptionPane.INFORMATION_MESSAGE) == 0){
                    tareas.remove(tList.getSelectedRow());
                    actualizarTabla(tList, tareas);
                    tList.revalidate();
                    tList.repaint();
                    tfNombre.setText("");
                    tfDescripcion.setText("");
                    fecha = null;
                    bSeleccionarFecha.setText("Seleccionar");
                    tfHora.setText("");
                    tfMinuto.setText("");
                    tfSegundo.setText("");
                    cbRealizada.setSelectedIndex(0);
                    cbPrioridad.setSelectedIndex(0);
                    tfNombre.setEditable(true);
                    tfDescripcion.setEditable(true);
                    bSeleccionarFecha.setEnabled(true);
                    tfHora.setEditable(true);
                    cbRealizada.setEnabled(true);
                    cbPrioridad.setEnabled(true);
                    bAnyadir.setEnabled(true);
                    bModificar.setEnabled(false);
                    bEliminar.setEnabled(false);
                    bCambiarEstado.setEnabled(false);
                    bDeseleccionar.setEnabled(false);
                }
            }
        });

        bModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tfNombre.getText().equals("") || fecha == null || tfHora.getText().equals("")) {
                    JOptionPane.showMessageDialog(frame, "No se puede añadir porque un campo obligatorio está vacío.", "Mensaje de error", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    if (JOptionPane.showConfirmDialog(frame, "¿Seguro que desea eliminar la tarea seleccionada?", "Confirmación", JOptionPane.INFORMATION_MESSAGE) == 0) {
                        Tarea tarea = tareas.get(tList.getSelectedRow());
                        tareas.remove(tList.getSelectedRow());
                        tarea.setNombre(tfNombre.getText());
                        tarea.setDescripcion(tfDescripcion.getText());
                        fecha.setHours(Integer.parseInt(tfHora.getText()));
                        fecha.setMinutes(Integer.parseInt(tfMinuto.getText()));
                        fecha.setSeconds(Integer.parseInt(tfSegundo.getText()));
                        tarea.setFecha(fecha);
                        tarea.setPrioridad(Integer.parseInt((String) cbPrioridad.getSelectedItem()));
                        if (cbRealizada.getSelectedItem().equals("Completado")) {
                            tarea.setRealizada("Completado");
                        } else {
                            tarea.setRealizada("Pendiente");
                        }

                        tareas.add(tarea);
                        actualizarTabla(tList, tareas);
                        tList.revalidate();
                        tList.repaint();
                        tfNombre.setText("");
                        tfDescripcion.setText("");
                        fecha = null;
                        bSeleccionarFecha.setText("Seleccionar");
                        tfHora.setText("");
                        tfMinuto.setText("");
                        tfSegundo.setText("");
                        cbRealizada.setSelectedIndex(0);
                        cbPrioridad.setSelectedIndex(0);
                        tfNombre.setEditable(true);
                        tfDescripcion.setEditable(true);
                        bSeleccionarFecha.setEnabled(true);
                        tfHora.setEditable(true);
                        tfMinuto.setEditable(true);
                        tfSegundo.setEditable(true);
                        cbRealizada.setEnabled(true);
                        cbPrioridad.setEnabled(true);
                        bAnyadir.setEnabled(true);
                        bModificar.setEnabled(false);
                        bEliminar.setEnabled(false);
                        bCambiarEstado.setEnabled(false);
                        bDeseleccionar.setEnabled(false);
                    }
                }
            }
        });

        bAyuda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Aplicación para el manejo de tareas:\n" +
                        "- Usa el botón 'Añadir' para crear una nueva tarea.\n" +
                        "- Selecciona una tarea de la lista para modificarla o eliminarla.\n" +
                        "- Usa el botón 'Modificar' para cambiar los datos de esa tarea.\n" +
                        "- " +
                        "" +
                        "" +
                        "Usa el botón 'Eliminar' para eliminar la tarea seleccionada.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        cbFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<Tarea> tareasFiltradas = new ArrayList<Tarea>();
                    for (int i = 0; i < tareas.size(); i++) {
                        if (cbFiltrar.getSelectedItem().equals("Todas")) {
                            tareasFiltradas.add(tareas.get(i));
                        }
                        if (cbFiltrar.getSelectedItem().equals("Pendientes") && tareas.get(i).getRealizada().equals("Pendiente")) {
                            tareasFiltradas.add(tareas.get(i));
                        }
                        if (cbFiltrar.getSelectedItem().equals("Completadas") && tareas.get(i).getRealizada().equals("Completado")) {
                            tareasFiltradas.add(tareas.get(i));
                        }
                    }
                    actualizarTabla(tList, tareasFiltradas);

                }catch (IndexOutOfBoundsException ex){}
                bAnyadir.setEnabled(true);
                bEliminar.setEnabled(false);
                bModificar.setEnabled(false);
                bCambiarEstado.setEnabled(false);
            }
        });

        bCambiarEstado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(frame, "¿Seguro que desea cambiar el estado de la tarea?", "Confirmación", JOptionPane.INFORMATION_MESSAGE) == 0){
                    if(tareas.get(tList.getSelectedRow()).getRealizada().equals("Pendiente")) {
                        tareas.get(tList.getSelectedRow()).setRealizada("Completado");
                    }else{
                        tareas.get(tList.getSelectedRow()).setRealizada("Pendiente");
                    }

                    actualizarTabla(tList, tareas);
                    tList.revalidate();
                    tList.repaint();
                    tfNombre.setText("");
                    tfDescripcion.setText("");
                    fecha = null;
                    bSeleccionarFecha.setText("Seleccionar");
                    tfHora.setText("");
                    tfMinuto.setText("");
                    tfSegundo.setText("");
                    cbRealizada.setSelectedIndex(0);
                    cbPrioridad.setSelectedIndex(0);
                    bAnyadir.setEnabled(true);
                    bModificar.setEnabled(false);
                    bEliminar.setEnabled(false);
                    bCambiarEstado.setEnabled(false);
                }
            }
        });
    }


    public static void GUI() {
        frame = new JFrame("Tareas");
        frame.setContentPane(new TareasForm().p);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setBounds(0,0,1000,300);
    }


    private static void actualizarTabla(JTable t, ArrayList<Tarea> tareas){
        t.setModel(new TareasTableModel(tareas));
        t.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
}
