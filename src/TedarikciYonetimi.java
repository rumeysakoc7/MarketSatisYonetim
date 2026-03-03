import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

public class TedarikciYonetimi extends JFrame {
    private JTable tableTedarikciBilgileri;
    private JPanel Tedarikciler;
    private JButton buttonekle;
    private JButton buttonsil;
    private JButton buttonguncelle;
    private JFormattedTextField formattedTextelno;
    private JTextField textFirmaad;
    private JTextField textSoyadi;
    private JTextField textAdi;
    private JLabel tedarikcibilgi;
    private JLabel tedarikciad;
    private JLabel tedarikcisoyad;
    private JLabel firmaadi;
    private JLabel telno;
    private JButton Buttontemizle;

    public TedarikciYonetimi() {
        setTitle("TEDARİKÇİLER SAYFASI");
        setSize(700,700);
        setContentPane(Tedarikciler);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        initializeTable();
        tedarikcileriYenile();
        Buttontemizle.addActionListener(e -> temizle());
        setVisible(true);

        try {
            MaskFormatter telefonFormatter = new MaskFormatter("0##########");
            telefonFormatter.setPlaceholderCharacter('_');
            formattedTextelno.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(telefonFormatter));
        } catch (ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Telefon numarası formatı ayarlanamadı.", "Hata", JOptionPane.ERROR_MESSAGE);
        }

        tableTedarikciBilgileri.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tableTedarikciBilgileri.getSelectedRow();
                if (selectedRow != -1) {
                    // Tablo verilerini ilgili alanlara aktar
                    textAdi.setText(tableTedarikciBilgileri.getValueAt(selectedRow, 1).toString());
                    textSoyadi.setText(tableTedarikciBilgileri.getValueAt(selectedRow, 2).toString());
                    textFirmaad.setText(tableTedarikciBilgileri.getValueAt(selectedRow, 3).toString());
                    formattedTextelno.setText(tableTedarikciBilgileri.getValueAt(selectedRow, 4).toString());
                }
            }
        });
        

        buttonekle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                telefonNumarasiGecerli();
                String ad = textAdi.getText().trim();
                String soyad = textSoyadi.getText().trim();
                String telNo = formattedTextelno.getText().trim();
                String firmaAd = textFirmaad.getText().trim();

                if (!ad.isEmpty() && !soyad.isEmpty() && !telNo.isEmpty() && !firmaAd.isEmpty()) {
                    Tedarikci yeniTedarikci = new Tedarikci();
                    yeniTedarikci.setAd(ad);
                    yeniTedarikci.setSoyad(soyad);
                    yeniTedarikci.setIletisimBilgisi(telNo);
                    yeniTedarikci.setFirmaAdi(firmaAd);
                    yeniTedarikci.setAktif(true);

                    if (TedarikciDAO.tedarikciKaydet(yeniTedarikci)) {
                        JOptionPane.showMessageDialog(null, "Tedarikçi başarıyla eklendi.");
                        tedarikcileriYenile();
                    } else {
                        JOptionPane.showMessageDialog(null, "Tedarikçi eklenemedi.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Tüm alanları doldurun.");
                }
            }
        });

        buttonsil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableTedarikciBilgileri.getSelectedRow();
                if (selectedRow != -1) {
                    int tedarikciId = (int) tableTedarikciBilgileri.getValueAt(selectedRow, 0);
                    if (TedarikciDAO.tedarikciSil(tedarikciId)) {
                        JOptionPane.showMessageDialog(null, "Tedarikçi başarıyla silindi.");
                        tedarikcileriYenile();
                    } else {
                        JOptionPane.showMessageDialog(null, "Tedarikçi silinemedi.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Bir tedarikçi seçin.");
                }
            }
        });

        buttonguncelle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                telefonNumarasiGecerli();
                int selectedRow = tableTedarikciBilgileri.getSelectedRow();
                if (selectedRow != -1) {
                    int tedarikciId = (int) tableTedarikciBilgileri.getValueAt(selectedRow, 0);
                    String yeniAd = textAdi.getText().trim();
                    String yeniSoyad = textSoyadi.getText().trim();
                    String yeniTel = formattedTextelno.getText().trim();
                    String yeniFirmaAd = textFirmaad.getText().trim();

                    if (!yeniAd.isEmpty() && !yeniSoyad.isEmpty() && !yeniTel.isEmpty() && !yeniFirmaAd.isEmpty()) {
                    Tedarikci tedarikci = new Tedarikci();
                    tedarikci.setId(tedarikciId);
                    tedarikci.setAd(yeniAd);
                    tedarikci.setSoyad(yeniSoyad);
                    tedarikci.setIletisimBilgisi(yeniTel);
                    tedarikci.setFirmaAdi(yeniFirmaAd);
                    tedarikci.setAktif(true);

                    if (TedarikciDAO.tedarikciGuncelle(tedarikci)) {
                        JOptionPane.showMessageDialog(null, "Tedarikçi başarıyla güncellendi.");
                        tedarikcileriYenile();
                    } else {
                        JOptionPane.showMessageDialog(null, "Tedarikçi güncellenemedi.");
                    }
                    } else {
                        JOptionPane.showMessageDialog(null, "Tüm alanları doldurun.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Bir tedarikçi seçin.");
                }
            }
        });
    }

    private void initializeTable() {
        String[] columnNames = {"ID", "Ad", "Soyad", "Firma Adı", "İletişim", "Durum"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        tableTedarikciBilgileri.setModel(model);
    }


    private boolean telefonNumarasiGecerli() {
        String telefon = formattedTextelno.getText().trim();
        if (telefon.contains("_")) {
            return false;
        }
        if (!telefon.startsWith("0")) {
            JOptionPane.showMessageDialog(this, "Telefon numarası 05 ile başlamalıdır.", "Hata", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void tedarikcileriYenile() {
        DefaultTableModel model = (DefaultTableModel) tableTedarikciBilgileri.getModel();
        model.setRowCount(0);

        List<Tedarikci> tedarikciler = TedarikciDAO.tedarikciGetir();

        if (tedarikciler != null && !tedarikciler.isEmpty()) {
            for (Tedarikci tedarikci : tedarikciler) {
                model.addRow(new Object[]{
                        tedarikci.getId(),
                        tedarikci.getAd(),
                        tedarikci.getSoyad(),
                        tedarikci.getFirmaAdi(),
                        tedarikci.getIletisimBilgisi(),
                        tedarikci.isAktif() ? "Aktif" : "Pasif"
                });
            }
        } else {
            System.out.println("Tedarikçi listesi boş veya null.");
        }
    }

    private void temizle() {
        textAdi.setText("");
        textFirmaad.setText("");
        textSoyadi.setText("");
        formattedTextelno.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TedarikciYonetimi::new);
    }
}
