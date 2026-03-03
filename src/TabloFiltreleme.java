import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
public class TabloFiltreleme {
    public static void tabloyuFiltrele(JTable tablo, String filtreMetni, int kolon) {
        DefaultTableModel model = (DefaultTableModel) tablo.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tablo.setRowSorter(sorter);

        if (filtreMetni.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + filtreMetni, kolon));
        }
    }
}
