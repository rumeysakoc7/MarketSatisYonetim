import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TedarikciAlisYonetim extends JFrame {

    private JButton buttonsepeteekle;
    private JButton buttontemizle;
    private JList listurunsepet;
    private JTextField texttoplammaliyet;
    private JTextField textalisfiyat;
    private JTextField texturunad;
    private JTextField texttedarikci;
    private JSpinner spinnermiktar;
    private JButton buttonurunsil;
    private JButton buttonsatinal;
    private JTextField texturunara;
    private JLabel urunara;
    private JLabel urunad;
    private JLabel alisfiyat;
    private JLabel miktar;
    private JLabel tedarikci;
    private JLabel urunsepet;
    private JLabel toplammaliyet;
    private JPanel Tedarikcialis;
    private JTable tabletedarikci;

    private DefaultListModel<String> sepetModel;
    private List<Alis> sepetDetaylari;
    private int secilenUrunId;
    private int secilenTedarikciId;

    public TedarikciAlisYonetim() {
        setTitle("TEDARİKÇİ ALIŞ SAYFASI");
        setSize(800, 800);
        setContentPane(Tedarikcialis);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        sepetModel = new DefaultListModel<>();
        listurunsepet.setModel(sepetModel);
        sepetDetaylari = new ArrayList<>();

        urunTedarikciTablosunuDoldur();

        buttonsepeteekle.addActionListener(e -> sepeteEkle());
        buttonurunsil.addActionListener(e -> sepettenUrunSil());
        buttonsatinal.addActionListener(e -> alisIsleminiTamamla());
        buttontemizle.addActionListener(e -> temizle());

        urunAra();

        setVisible(true);
    }
    private void urunTedarikciTablosunuDoldur() {
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"Ürün ID", "Ürün Adı", "Stok", "Alış Fiyatı", "Tedarikçi ID", "Tedarikçi Adı", "Firma Adı", "İletişim Bilgisi"}, 0
        );

        List<Alis> alisListesi = AlisDAO.urunVeTedarikciBilgileriniGetir();

        SwingUtilities.invokeLater(() -> {
            for (Alis alis : alisListesi) {
                model.addRow(new Object[]{
                        alis.getUrun().getUrunId(),
                        alis.getUrun().getUrunAdi(),
                        alis.getUrun().getStokMiktari(),
                        alis.getUrun().getAlisFiyat(),
                        alis.getTedarikci().getId(),
                        alis.getTedarikci().getAd(),
                        alis.getTedarikci().getFirmaAdi(),
                        alis.getTedarikci().getIletisimBilgisi()

                });
            }

            tabletedarikci.setModel(model);

        });

        tabletedarikci.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tabletedarikci.getSelectedRow();
                if (selectedRow != -1) {
                    secilenUrunId = (int) model.getValueAt(selectedRow, 0);
                    texturunad.setText(model.getValueAt(selectedRow, 1).toString());
                    textalisfiyat.setText(model.getValueAt(selectedRow, 3).toString());

                    secilenTedarikciId = (int) model.getValueAt(selectedRow, 4);
                    texttedarikci.setText(model.getValueAt(selectedRow, 6).toString());
                }
            }
        });
    }

    private void urunAra() {
        texturunara.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String filtreMetni = texturunara.getText();
                TabloFiltreleme.tabloyuFiltrele(tabletedarikci, filtreMetni, 1);
            }
        });
    }

    private void sepeteEkle() {
        String urunAdi = texturunad.getText();
        String alisFiyatStr = textalisfiyat.getText();
        int miktar = (int) spinnermiktar.getValue();

        if (urunAdi.isEmpty()  || alisFiyatStr.isEmpty() || miktar <= 0) {
            JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurun ve miktarı doğru girin!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double alisFiyati = Double.parseDouble(alisFiyatStr);
        double toplamMaliyet = alisFiyati * miktar;

        Alis alis = new Alis(new Urun(secilenUrunId, urunAdi, 0, alisFiyati, 0, true, null, null),
                new Tedarikci(secilenTedarikciId, "", "", "", "", true)
, alisFiyati, miktar);

        sepetDetaylari.add(alis);
        sepetModel.addElement(urunAdi + " - "  + " - " + miktar + " x " + alisFiyati + " = " + toplamMaliyet);
        toplamMaliyetiHesapla();
    }

    private void toplamMaliyetiHesapla() {
        double toplamMaliyet = sepetDetaylari.stream()
                .mapToDouble(Alis::toplamMaliyet)
                .sum();
        texttoplammaliyet.setText(String.format("%.2f", toplamMaliyet));
    }

    private void sepettenUrunSil() {
        int selectedIndex = listurunsepet.getSelectedIndex();
        if (selectedIndex != -1) {
            sepetModel.remove(selectedIndex);
            sepetDetaylari.remove(selectedIndex);
            toplamMaliyetiHesapla();
        }
    }

    private void alisIsleminiTamamla() {
        if (sepetDetaylari.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen sepete ürün ekleyin!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean basarili = true;
        for (Alis alis : sepetDetaylari) {
            basarili &= AlisDAO.alisKaydet(alis);
        }

        if (basarili) {
            urunTedarikciTablosunuDoldur();
            JOptionPane.showMessageDialog(this, "Alış işlemi başarıyla tamamlandı!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            temizle();
        } else {
            JOptionPane.showMessageDialog(this, "Alış işlemi sırasında bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void temizle() {
        texturunad.setText("");
        texttedarikci.setText("");
        textalisfiyat.setText("");
        spinnermiktar.setValue(0);
        sepetModel.clear();
        sepetDetaylari.clear();
        texttoplammaliyet.setText("");
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TedarikciAlisYonetim::new);
    }

}
