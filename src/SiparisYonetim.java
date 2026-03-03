import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SiparisYonetim extends JFrame {
    private JTextField textmusteribilgi;
    private JTextField texturunad;
    private JTextField texturunfiyat;
    private JSpinner spinnermiktar;
    private JComboBox comboBoxodemeturu;
    private JButton buttonekle;
    private JButton buttontemizle;
    private JList listsepet;
    private JTextField texttoplamtutar;
    private JButton buttonurunsil;
    private JButton Buttonsiparisekle;
    private JTextField texturunara;
    private JTextField textmusteriara;
    private JLabel siparisislem;
    private JLabel musteribilgi;
    private JLabel urunad;
    private JLabel urunfiyat;
    private JLabel miktar;
    private JLabel odemetur;
    private JLabel urunsepet;
    private JLabel toplamtutar;
    private JLabel urunara;
    private JLabel musteriara;
    private JPanel siparisler;
    private JTable tableurunler;
    private JTable tablemusteriler;

    // Sepet için liste
    private DefaultListModel<String> sepetModel;
    private List<SiparisDetay> sepetDetaylari;
    private Musteri seciliMusteri;
    public SiparisYonetim() {
        setTitle("SİPARİŞ YÖNETİMİ");
        setSize(800,800);
        setContentPane(siparisler);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        sepetModel = new DefaultListModel<>();
        listsepet.setModel(sepetModel);
        sepetDetaylari = new ArrayList<>();

        aktifUrunleriDoldur();
        aktifMusterileriDoldur();

        buttonekle.addActionListener(e -> sepeteEkle());
        buttonurunsil.addActionListener(e -> sepettenUrunSil());
        Buttonsiparisekle.addActionListener(e -> siparisiTamamla());
        buttontemizle.addActionListener(e -> temizle());

        urunAra();
        musteriAra();
        setVisible(true);
    }

    private void urunAra() {
        texturunara.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String filtreMetni = texturunara.getText();
                TabloFiltreleme.tabloyuFiltrele(tableurunler, filtreMetni, 1);
            }
        });
    }

    private void musteriAra() {
        textmusteriara.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String filtreMetni = textmusteriara.getText();
                TabloFiltreleme.tabloyuFiltrele(tablemusteriler, filtreMetni, 1);
            }
        });
    }


    private int secilenUrunId;

    private void aktifUrunleriDoldur() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"Ürün ID", "Ürün Adı", "Fiyat", "Stok"}, 0);
        List<Urun> urunler = UrunDAO.aktifUrunleriGetir();
        for (Urun urun : urunler) {
            model.addRow(new Object[]{urun.getUrunId(), urun.getUrunAdi(), urun.getSatisFiyat(), urun.getStokMiktari()});
        }
        tableurunler.setModel(model);

        tableurunler.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tableurunler.getSelectedRow();
                if (selectedRow != -1) {
                    secilenUrunId = (int) model.getValueAt(selectedRow, 0);
                    texturunad.setText(model.getValueAt(selectedRow, 1).toString());
                    texturunfiyat.setText(model.getValueAt(selectedRow, 2).toString());
                }
            }
        });
    }

    private void aktifMusterileriDoldur() {

        DefaultTableModel model = new DefaultTableModel(new String[]{"Müşteri ID", "Ad", "Soyad"}, 0);
        List<Musteri> musteriler = MusteriDAO.aktifMusterileriGetir();
        for (Musteri musteri : musteriler) {
            model.addRow(new Object[]{musteri.getId(), musteri.getAd(), musteri.getSoyad()});
        }
        tablemusteriler.setModel(model);

        tablemusteriler.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tablemusteriler.getSelectedRow();
                if (selectedRow != -1) {

                    textmusteribilgi.setText(model.getValueAt(selectedRow, 1).toString() + " " + model.getValueAt(selectedRow, 2).toString());

                    seciliMusteri = new Musteri(
                            (int) model.getValueAt(selectedRow, 0),
                            model.getValueAt(selectedRow, 1).toString(),
                            model.getValueAt(selectedRow, 2).toString(),
                            "",
                            "",
                            true
                    );
                }
            }
        });
    }


    private void sepeteEkle() {
        String urunAdi = texturunad.getText().trim();
        String fiyatStr = texturunfiyat.getText().trim();
        int miktar = (int) spinnermiktar.getValue();

        if (urunAdi.isEmpty() || fiyatStr.isEmpty() || miktar <= 0) {
            JOptionPane.showMessageDialog(this, "Lütfen ürün bilgilerini doğru giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int selectedRow = tableurunler.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir ürün seçin!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tableurunler.getModel();
        int stokMiktari = Integer.parseInt(model.getValueAt(selectedRow, 3).toString());


        if (miktar > stokMiktari) {
            JOptionPane.showMessageDialog(this, "Seçilen üründen sadece " + stokMiktari + " adet alabilirsiniz!", "Stok Hatası", JOptionPane.ERROR_MESSAGE);
            return;
        }


        double fiyat = Double.parseDouble(fiyatStr);
        double toplamTutar = fiyat * miktar;

        SiparisDetay detay = new SiparisDetay(
                new Urun(secilenUrunId, urunAdi, 0, fiyat, 0, true, null, null),
                miktar,
                fiyat
        );

        sepetDetaylari.add(detay);

        sepetModel.addElement(urunAdi + " - " + miktar + " x " + fiyat + " = " + toplamTutar);
        toplamTutariHesapla();
    }
    private void toplamTutariHesapla() {
        double toplamTutar = sepetDetaylari.stream()
                .mapToDouble(SiparisDetay::getToplamTutar)
                .sum();
        texttoplamtutar.setText(String.format("%.2f", toplamTutar));
    }


    private void sepettenUrunSil() {
        int selectedIndex = listsepet.getSelectedIndex();
        if (selectedIndex != -1) {
            sepetModel.remove(selectedIndex);
            sepetDetaylari.remove(selectedIndex);
            toplamTutariHesapla();
        }
    }

    private void siparisiTamamla() {
        if (comboBoxodemeturu.getSelectedItem().toString().equals("Seçiniz...")) {
            JOptionPane.showMessageDialog(this, "Lütfen bir ödeme türü seçiniz!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }


        if (seciliMusteri == null || sepetDetaylari.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen müşteri seçin ve sepete ürün ekleyin!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String odemeTuru = comboBoxodemeturu.getSelectedItem().toString();
        String siparisTarihi = LocalDate.now().toString();
        Siparis siparis = new Siparis(0, seciliMusteri, odemeTuru, siparisTarihi);
        siparis.getSiparisDetaylari().addAll(sepetDetaylari);

        boolean basarili = SiparisDAO.siparisKaydet(siparis);
        if (basarili) {
            aktifUrunleriDoldur();
            JOptionPane.showMessageDialog(this, "Sipariş başarıyla tamamlandı!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            temizle();
        } else {
            JOptionPane.showMessageDialog(this, "Sipariş sırasında bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void temizle() {
        textmusteribilgi.setText("");
        texturunad.setText("");
        texturunfiyat.setText("");
        spinnermiktar.setValue(1);
        comboBoxodemeturu.setSelectedIndex(0);
        sepetModel.clear();
        sepetDetaylari.clear();
        texttoplamtutar.setText("");
        seciliMusteri = null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SiparisYonetim::new);
    }
}

