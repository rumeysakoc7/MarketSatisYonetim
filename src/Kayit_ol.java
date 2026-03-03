import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;

public class Kayit_ol extends JFrame {
    private JPanel kayit_ol;
    private JTextField textFieldadi;
    private JTextField textFieldkullaniciad;
    private JTextField textFieldsoyad;
    private JFormattedTextField formattedTexttelno;
    private JPasswordField passwordfieldsifre;
    private JComboBox comboBoxunvan;
    private JComboBox comboBoxgorev;
    private JButton buttonkayitol;
    private JLabel lblad;
    private JLabel lblsoyad;
    private JLabel lbltelno;
    private JLabel lblkullaniciad;
    private JLabel lblsifre;
    private JLabel lblunvan;
    private JLabel lblgorev;
    public Kayit_ol() {
        setTitle("KAYIT OL");
        setSize(600,600);
        setContentPane(kayit_ol);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);
        setVisible(true);

        try {
            MaskFormatter telefonFormatter = new MaskFormatter("0##########");
            telefonFormatter.setPlaceholderCharacter('_');
            formattedTexttelno.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(telefonFormatter));
        } catch (ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Telefon numarası formatı ayarlanamadı.", "Hata", JOptionPane.ERROR_MESSAGE);
        }

        ArrayList<String> gorevListesi = KayitOlDAO.getGorevler();
        for (String gorev : gorevListesi) {
            comboBoxgorev.addItem(gorev);
        }

        comboBoxgorev.setEnabled(false);

        comboBoxunvan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedUnvan = (String) comboBoxunvan.getSelectedItem();
                if ("Personel".equals(selectedUnvan)) {
                    comboBoxgorev.setEnabled(true);
                } else {
                    comboBoxgorev.setEnabled(false);
                }
            }
        });

        buttonkayitol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                telefonNumarasiGecerli();

                String ad = textFieldadi.getText().trim();
                String soyad = textFieldsoyad.getText().trim();
                String kullaniciAdi = textFieldkullaniciad.getText().trim();
                String telefonNo = formattedTexttelno.getText().trim();
                String sifre = new String(passwordfieldsifre.getPassword()).trim();
                String unvan = (String) comboBoxunvan.getSelectedItem();
                String gorev = (String) comboBoxgorev.getSelectedItem();


                if (ad.isEmpty() || soyad.isEmpty() || kullaniciAdi.isEmpty() || telefonNo.isEmpty() || sifre.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Tüm alanlar doldurulmalıdır!", "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if ("Seçiniz...".equals(unvan)) {
                    JOptionPane.showMessageDialog(null, "Lütfen geçerli bir unvan seçiniz!", "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if ("Personel".equals(unvan) && "Seçiniz...".equals(gorev)) {
                    JOptionPane.showMessageDialog(null, "Lütfen geçerli bir görev seçiniz!", "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (KayitOlDAO.kullaniciAdiVarMi(kullaniciAdi)) {
                    JOptionPane.showMessageDialog(null, "Bu kullanıcı adı zaten kullanılıyor bu kullanıcı sisteme kayıtlı!", "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                    if (unvan.equals("Müdür")) {
                        Mudur mudur = new Mudur();
                        mudur.setAd(ad);
                        mudur.setSoyad(soyad);
                        mudur.setKullaniciAdi(kullaniciAdi);
                        mudur.setIletisimBilgisi(telefonNo);
                        mudur.setSifre(sifre);
                        mudur.setUnvan(unvan);

                        boolean kayitSonucu = KayitOlDAO.mudurKaydet(mudur);
                        if (kayitSonucu) {
                            JOptionPane.showMessageDialog(null, "Müdür kaydı başarıyla tamamlandı!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                            setVisible(false);
                        } else {
                            JOptionPane.showMessageDialog(null, "Müdür kaydı sırasında bir hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        Personel personel = new Personel();
                        personel.setAd(ad);
                        personel.setSoyad(soyad);
                        personel.setKullaniciAdi(kullaniciAdi);
                        personel.setIletisimBilgisi(telefonNo);
                        personel.setSifre(sifre);
                        personel.setUnvan(unvan);
                        personel.setGorev(gorev);

                        boolean kayitSonucu = KayitOlDAO.personelKaydet(personel);
                        if (kayitSonucu) {
                            JOptionPane.showMessageDialog(null, "Personel kaydı başarıyla tamamlandı!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                            setVisible(false); // Kayıt başarılıysa, ekranı kapat
                        } else {
                            JOptionPane.showMessageDialog(null, "Personel kaydı sırasında bir hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
                        }
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

    public static void main(String[] args) {

        SwingUtilities.invokeLater(Kayit_ol::new);
    }
}
