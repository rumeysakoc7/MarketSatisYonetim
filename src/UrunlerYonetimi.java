import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class UrunlerYonetimi extends JFrame {

    private JTextField texturunad;
    private JComboBox comboBoxfirma;
    private JComboBox comboBoxkategori;
    private JSpinner spinnerstokmiktar;
    private JTextField textsatisfiyat;
    private JTextField textalisfiyat;
    private JTable tableUrun;
    private JButton buttonEkle;
    private JButton buttonSil;
    private JButton buttonGuncelle;
    private JLabel labelUrunAd;
    private JPanel UrunYonetimi;
    private JLabel kategorismi;
    private JLabel Firmaismi;
    private JLabel Stokmiktar;
    private JLabel SatisFiyat;
    private JLabel AlisFiyat;
    private JButton buttontemizle;
    private UrunDAO urunDAO; // DAO nesnesi
    private DefaultTableModel tableModel;

    public UrunlerYonetimi() {
        setTitle("Ürün Yönetimi");
        setSize(800,800);
        setContentPane(UrunYonetimi);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        urunDAO = new UrunDAO();
        tedarikciComboBoxDoldur();
        kategoriComboBoxDoldur();
        buttontemizle.addActionListener(e -> temizle());
        tableModel = new DefaultTableModel(new String[]{"ID", "Ürün Adı", "Alış Fiyatı", "Satış Fiyatı", "Stok", "Kategori", "Tedarikçi", "Durum"}, 0);
        tableUrun.setModel(tableModel);

        textsatisfiyat.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();

                if (!Character.isDigit(c) && c != '.') {
                    evt.consume();
                }
            }
        });

        textalisfiyat.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();

                if (!Character.isDigit(c) && c != '.') {
                    evt.consume();
                }
            }
        });

        urunleriListele();

        tableUrun.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tableUrun.getSelectedRow() != -1) {
                int selectedRow = tableUrun.getSelectedRow();
                texturunad.setText(tableModel.getValueAt(selectedRow, 1).toString());
                textalisfiyat.setText(tableModel.getValueAt(selectedRow, 2).toString());
                textsatisfiyat.setText(tableModel.getValueAt(selectedRow, 3).toString());
                spinnerstokmiktar.setValue(Integer.parseInt(tableModel.getValueAt(selectedRow, 4).toString()));


                String kategori = tableModel.getValueAt(selectedRow, 5).toString().replaceAll("[\\[\\]]", "").trim();
                String firma = tableModel.getValueAt(selectedRow, 6).toString().replaceAll("[\\[\\]]", "").trim();

                for (int i = 0; i < comboBoxkategori.getItemCount(); i++) {
                    if (comboBoxkategori.getItemAt(i).toString().trim().equalsIgnoreCase(kategori)) {
                        comboBoxkategori.setSelectedIndex(i);
                        break;
                    }
                }

                for (int i = 0; i < comboBoxfirma.getItemCount(); i++) {
                    if (comboBoxfirma.getItemAt(i).toString().trim().equalsIgnoreCase(firma)) {
                        comboBoxfirma.setSelectedIndex(i);
                        break;
                    }
                }
            }
        });


        buttonEkle.addActionListener(e -> urunEkle());

        buttonSil.addActionListener(e -> urunSil());

        buttonGuncelle.addActionListener(e -> urunGuncelle());

        setVisible(true);
    }
    private void tedarikciComboBoxDoldur() {
        List<Tedarikci> aktifTedarikciler = TedarikciDAO.aktifTedarikcileriGetir();
        comboBoxfirma.removeAllItems();
        comboBoxfirma.addItem("");
        for (Tedarikci tedarikci : aktifTedarikciler) {
            comboBoxfirma.addItem(tedarikci);
        }
    }



    private void kategoriComboBoxDoldur() {
        List<Kategori> aktifKategoriler = KategoriDAO.aktifKategorileriGetir();
        comboBoxkategori.removeAllItems();
        comboBoxkategori.addItem("");
        for (Kategori kategori : aktifKategoriler) {
            comboBoxkategori.addItem(kategori);
        }
    }


    private void urunleriListele() {
        tableModel.setRowCount(0);
        List<Urun> urunler = urunDAO.urunleriGetir();

        for (Urun urun : urunler) {
            tableModel.addRow(new Object[]{
                    urun.getUrunId(),
                    urun.getUrunAdi(),
                    urun.getAlisFiyat(),
                    urun.getSatisFiyat(),
                    urun.getStokMiktari(),
                    urun.getKategori().getKategoriAdi(),
                    urun.getTedarikci().getAd(),
                    urun.isAktif() ? "Aktif" : "Pasif"
            });
        }
    }

    private void urunEkle() {
        try {
            if (texturunad.getText().trim().isEmpty() ||
                    textalisfiyat.getText().trim().isEmpty() ||
                    textsatisfiyat.getText().trim().isEmpty() ||
                    (int) spinnerstokmiktar.getValue() < 0 ||
                    comboBoxkategori.getSelectedItem().toString().equals("") ||
                    comboBoxfirma.getSelectedItem().toString().equals("")) {

                JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurunuz.");
                return;
            }

            double alisFiyat = Double.parseDouble(textalisfiyat.getText());
            double satisFiyat = Double.parseDouble(textsatisfiyat.getText());

            if (alisFiyat > satisFiyat) {
                JOptionPane.showMessageDialog(this, "Alış fiyatı, satış fiyatından büyük olamaz.");
                return;
            }
            Urun urun = new Urun();
            urun.setUrunAdi(texturunad.getText());
            urun.setAlisFiyat(Double.parseDouble(textalisfiyat.getText()));
            urun.setSatisFiyat(Double.parseDouble(textsatisfiyat.getText()));
            urun.setStokMiktari((int) spinnerstokmiktar.getValue());
            urun.setKategori((Kategori) comboBoxkategori.getSelectedItem());
            urun.setTedarikci((Tedarikci) comboBoxfirma.getSelectedItem());

            urun.setAktif(true);

            if (urunDAO.urunEkle(urun)) {
                JOptionPane.showMessageDialog(this, "Ürün başarıyla eklendi.");
                urunleriListele();
            } else {
                JOptionPane.showMessageDialog(this, "Ürün eklenemedi.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
        }
    }

    private void urunSil() {
        int selectedRow = tableUrun.getSelectedRow();
        if (selectedRow != -1) {
            int urunId = (int) tableModel.getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(this, "Bu ürünü silmek istediğinizden emin misiniz?", "Silme Onayı", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (urunDAO.urunSil(urunId)) {
                    JOptionPane.showMessageDialog(this, "Ürün başarıyla silindi.");
                    urunleriListele();
                } else {
                    JOptionPane.showMessageDialog(this, "Ürün silinemedi.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen silinecek bir ürün seçin.");
        }
    }

    private void urunGuncelle() {
        int selectedRow = tableUrun.getSelectedRow();
        if (selectedRow != -1) {
            try {

                if (texturunad.getText().trim().isEmpty() ||
                        textalisfiyat.getText().trim().isEmpty() ||
                        textsatisfiyat.getText().trim().isEmpty() ||
                        (int) spinnerstokmiktar.getValue() < 0 ||
                        comboBoxkategori.getSelectedItem().toString().equals("") ||
                        comboBoxfirma.getSelectedItem().toString().equals("")) {

                    JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurunuz.");
                    return;
                }

                double alisFiyat = Double.parseDouble(textalisfiyat.getText());
                double satisFiyat = Double.parseDouble(textsatisfiyat.getText());

                if (alisFiyat > satisFiyat) {
                    JOptionPane.showMessageDialog(this, "Alış fiyatı, satış fiyatından büyük olamaz.");
                    return;
                }

                Urun urun = new Urun();
                urun.setUrunId((int) tableModel.getValueAt(selectedRow, 0));
                urun.setUrunAdi(texturunad.getText());
                urun.setAlisFiyat(Double.parseDouble(textalisfiyat.getText()));
                urun.setSatisFiyat(Double.parseDouble(textsatisfiyat.getText()));
                urun.setStokMiktari((int) spinnerstokmiktar.getValue());
                urun.setKategori((Kategori) comboBoxkategori.getSelectedItem());
                urun.setTedarikci((Tedarikci) comboBoxfirma.getSelectedItem());

                urun.setAktif(true);

                if (urunDAO.urunGuncelle(urun)) {
                    JOptionPane.showMessageDialog(this, "Ürün başarıyla güncellendi.");
                    urunleriListele();
                } else {
                    JOptionPane.showMessageDialog(this, "Ürün güncellenemedi.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen güncellenecek bir ürün seçin.");
        }
    }

    private void temizle() {
        textalisfiyat.setText("");
        texturunad.setText("");
        textsatisfiyat.setText("");
        spinnerstokmiktar.setValue(0);
        comboBoxkategori.setSelectedIndex(0);
        comboBoxfirma.setSelectedIndex(0);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UrunlerYonetimi::new);
    }
}

