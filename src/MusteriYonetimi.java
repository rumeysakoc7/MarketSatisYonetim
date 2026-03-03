import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

public class MusteriYonetimi extends JFrame {
    private JTable tablemusteribilgi;
    private JTextField textsoyad;
    private JTextField textAd;
    private JFormattedTextField formattedTexttelno;
    private JButton buttonekle;
    private JButton buttonsil;
    private JButton buttonguncelle;
    private JPanel musteriYonetim;
    private JLabel musteribilgi;
    private JLabel musteriad;
    private JLabel musterisoyad;
    private JLabel telno;
    private JButton buttontemizle;

    public MusteriYonetimi() {
        setTitle("MÜŞTERİ BİLGİLERİ SAYFASI");
        setSize(600,600);
        setContentPane(musteriYonetim);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        initializeTable();
        musterileriYenile();
        buttontemizle.addActionListener(e -> temizle());
        setVisible(true);


        try {
            MaskFormatter telefonFormatter = new MaskFormatter("0##########");
            telefonFormatter.setPlaceholderCharacter('_');
            formattedTexttelno.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(telefonFormatter));
        } catch (ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Telefon numarası formatı ayarlanamadı.", "Hata", JOptionPane.ERROR_MESSAGE);
        }


        tablemusteribilgi.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = tablemusteribilgi.getSelectedRow();
                if (selectedRow != -1) {
                    textAd.setText((String) tablemusteribilgi.getValueAt(selectedRow, 1)); // Ad
                    textsoyad.setText((String) tablemusteribilgi.getValueAt(selectedRow, 2)); // Soyad
                    formattedTexttelno.setText((String) tablemusteribilgi.getValueAt(selectedRow, 3)); // Telefon Numarası
                }
            }
        });


        buttonekle.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String ad = textAd.getText().trim();
                String soyad = textsoyad.getText().trim();
                String telNo = formattedTexttelno.getText().trim();

                if (!ad.isEmpty() && !soyad.isEmpty() && !telNo.isEmpty()) {
                    Musteri yeniMusteri = new Musteri();
                    yeniMusteri.setAd(ad);
                    yeniMusteri.setSoyad(soyad);
                    yeniMusteri.setIletisimBilgisi(telNo);
                    yeniMusteri.setAktif(true);

                    if (MusteriDAO.musteriKaydet(yeniMusteri)) {
                        JOptionPane.showMessageDialog(null, "Müşteri başarıyla eklendi.");
                        musterileriYenile();
                    } else {
                        JOptionPane.showMessageDialog(null, "Müşteri eklenemedi.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Tüm alanları doldurun.");
                }
            }
        });

        buttonsil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                telefonNumarasiGecerli();
                int selectedRow = tablemusteribilgi.getSelectedRow();
                if (selectedRow != -1) {
                    int musteriId = (int) tablemusteribilgi.getValueAt(selectedRow, 0);
                    if (MusteriDAO.musteriSil(musteriId)) {
                        JOptionPane.showMessageDialog(null, "Müşteri başarıyla silindi.");
                        musterileriYenile();
                    } else {
                        JOptionPane.showMessageDialog(null, "Müşteri silinemedi.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Bir müşteri seçin.");
                }
            }
        });

        buttonguncelle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                telefonNumarasiGecerli();
                int selectedRow = tablemusteribilgi.getSelectedRow();
                if (selectedRow != -1) {
                    int musteriId = (int) tablemusteribilgi.getValueAt(selectedRow, 0);
                    String yeniAd = textAd.getText().trim();
                    String yeniSoyad = textsoyad.getText().trim();
                    String yeniTel = formattedTexttelno.getText().trim();

                    if (!yeniAd.isEmpty() && !yeniSoyad.isEmpty() && !yeniTel.isEmpty()) {
                        Musteri musteri = new Musteri();
                        musteri.setId(musteriId);
                        musteri.setAd(yeniAd);
                        musteri.setSoyad(yeniSoyad);
                        musteri.setIletisimBilgisi(yeniTel);
                        musteri.setAktif(true);

                        if (MusteriDAO.musteriGuncelle(musteri)) {
                            JOptionPane.showMessageDialog(null, "Müşteri başarıyla güncellendi.");
                            musterileriYenile();
                        } else {
                            JOptionPane.showMessageDialog(null, "Müşteri güncellenemedi.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Tüm alanları doldurun.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Bir müşteri seçin.");
                }
            }
        });
    }

    private boolean telefonNumarasiGecerli() {
        String telefon = formattedTexttelno.getText().trim();
        if (telefon.contains("_")) {
            return false;
        }
        if (!telefon.startsWith("0")) {
            JOptionPane.showMessageDialog(this, "Telefon numarası 05 ile başlamalıdır.", "Hata", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    private void initializeTable() {
        String[] columnNames = {"ID", "Ad", "Soyad", "İletişim", "Durum"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        tablemusteribilgi.setModel(model);
    }

    private void musterileriYenile() {
        DefaultTableModel model = (DefaultTableModel) tablemusteribilgi.getModel();
        model.setRowCount(0);

        List<Musteri> musteriler = MusteriDAO.musteriGetir();

        if (musteriler != null && !musteriler.isEmpty()) {
            for (Musteri musteri : musteriler) {
                model.addRow(new Object[]{
                        musteri.getId(),
                        musteri.getAd(),
                        musteri.getSoyad(),
                        musteri.getIletisimBilgisi(),
                        musteri.isAktif() ? "Aktif" : "Pasif"
                });
            }
        } else {
            System.out.println("Müşteri listesi boş veya null.");
        }

    }
    private void temizle() {
        textAd.setText("");
        textsoyad.setText("");
        formattedTexttelno.setText("");

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MusteriYonetimi::new);
    }

}
