package view;

import model.Tarea;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TareasTableModel extends AbstractTableModel {
    private enum TareasTableModelColumns {

        Nombre("Nombre"),
        Descripcion("Descripcion"),
        Fecha("Fecha"),
        Completado("Completado"),
        Prioridad("Prioridad");


        final String header;

        TareasTableModelColumns(String header){this.header = header;}

    }

    private List<Tarea> tareas;

    public TareasTableModel(List<Tarea> tareas){
        super();
        this.tareas = tareas;
    }


    @Override
    public int getRowCount() {
        return tareas.size();
    }

    @Override
    public int getColumnCount() {
        return TareasTableModelColumns.values().length;
    }

    @Override
    public String getColumnName (int column){
        return TareasTableModelColumns.values()[column].header;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tarea tarea = tareas.get(rowIndex);
        switch (TareasTableModelColumns.values()[columnIndex]){
            case Nombre:
                return tarea.getNombre();
            case Descripcion:
                return tarea.getDescripcion();
            case Fecha:
                return tarea.getFecha();
            case Prioridad:
                return tarea.getPrioridad();
            case Completado:
                return tarea.getRealizada();
            default:
                throw new RuntimeException("No existe la columna " + columnIndex);
        }
    }
}
