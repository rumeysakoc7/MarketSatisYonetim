import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class Kategoriler extends JFrame {
    private JTable tablekategori;
    private JTextField textaciklama;
    private JTextField textadi;
    private JButton buttonekle;
    private JButton buttonsil;
    private JButton buttonguncelle;
    private JLabel kategoriad;
    private JLabel Aciklama;
    private JPanel Kategoripanel;
    private JLabel kategorisayfa;
    private JButton buttontemizle;


    public Kategoriler() {
        setTitle("KATEGORİ YÖNETİM");
        setSize(1000,700);
        setContentPane(Kategoripanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        buttontemizle.addActionListener(e -> temizle());
        setVisible(true);
        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID", "Kategori Adı", "Açıklama", "Aktif"}, 0);


        tablekategori.setModel(tableModel);
        kategoriListele();

        tablekategori.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablekategori.getSelectedRow() != -1) {
                int selectedRow = tablekategori.getSelectedRow();
                textadi.setText(tableModel.getValueAt(selectedRow, 1).toString());
                textaciklama.setText(tableModel.getValueAt(selectedRow, 2).toString());
            }
        });

        buttonekle.addActionListener(e -> kategoriEkle());
        buttonguncelle.addActionListener(e -> kategoriGuncelle());
        buttonsil.addActionListener(e -> kategoriSil());
    }
    private void kategoriListele() {
        DefaultTableModel tableModel = (DefaultTableModel) tablekategori.getModel();
        tableModel.setRowCount(0);
        List<Kategori> kategoriler = KategoriDAO.kategoriListele();

        for (Kategori kategori : kategoriler) {
            tableModel.addRow(new Object[]{
                    kategori.getKategoriId(),
                    kategori.getKategoriAdi(),
                    kategori.getKategoriAciklama(),
                    kategori.isAktif() ? "Aktif" : "Pasif"
            });
        }
    }

    private void kategoriEkle() {
        String adi = textadi.getText();
        String aciklama = textaciklama.getText();

        if (!adi.isEmpty()  && !aciklama.isEmpty()) {
            Kategori yeniKategori = new Kategori();
            yeniKategori.setKategoriAdi(adi);
            yeniKategori.setKategoriAciklama(aciklama);
            yeniKategori.setAktif(true);

            if (KategoriDAO.kategoriEkle(yeniKategori)) {
                JOptionPane.showMessageDialog(this, "Kategori başarıyla eklendi!");
                kategoriListele();
            } else {
                JOptionPane.showMessageDialog(this, "Kategori eklenemedi!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Tüm alanları doldurun!", "Uyarı", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void kategoriGuncelle() {
        int selectedRow = tablekategori.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir kategori seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tablekategori.getValueAt(selectedRow, 0);
        String adi = textadi.getText().trim();
        String aciklama = textaciklama.getText().trim();
        boolean aktif = true;

        if (!adi.isEmpty() && !aciklama.isEmpty()) {

            Kategori kategori = new Kategori();
            kategori.setKategoriId(id);
            kategori.setKategoriAdi(adi);
            kategori.setKategoriAciklama(aciklama);
            kategori.setAktif(aktif);

            if (KategoriDAO.kategoriGuncelle(kategori)) {
                JOptionPane.showMessageDialog(this, "Kategori başarıyla güncellendi!");
                kategoriListele();
            } else {
                JOptionPane.showMessageDialog(this, "Kategori güncellenemedi!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Tüm alanları doldurun!", "Uyarı", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void kategoriSil() {
        int selectedRow = tablekategori.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir kategori seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tablekategori.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Bu kategoriyi silmek istediğinize emin misiniz?", "Onay", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (KategoriDAO.kategoriSil(id)) {
                JOptionPane.showMessageDialog(this, "Kategori başarıyla silindi!");
                kategoriListele();
            } else {
                JOptionPane.showMessageDialog(this, "Kategori silinemedi!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void temizle() {
        textadi.setText("");
        textaciklama.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Kategoriler::new);
    }
}
