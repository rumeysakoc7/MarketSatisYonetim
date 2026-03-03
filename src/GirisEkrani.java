import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GirisEkrani extends JFrame {
    private JPanel pnlGirisEkran;
    private JTextField txtkullaniciad;
    private JLabel JlblKullaniciAd;
    private JLabel JlblSifre;
    private JTextField txtSifre;
    private JButton buttonkullanicigris;
    private JButton buttonkayitol;
    private JPasswordField passwordFieldsifre;

    public GirisEkrani() {

        setTitle("GİRİŞ EKRANI");
        setSize(300, 400);
        setContentPane(pnlGirisEkran);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        buttonkayitol.addActionListener(e -> {
            new Kayit_ol();
        });

        buttonkullanicigris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String kullaniciAdi = txtkullaniciad.getText().trim();
                String sifre = new String(passwordFieldsifre.getText().trim());

                if (kullaniciAdi.isEmpty() || sifre.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Kullanıcı adı ve şifre boş olamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
                } else {
                    Calisan calisan = new Calisan();
                    calisan.setKullaniciAdi(kullaniciAdi);
                    calisan.setSifre(sifre);

                    Calisan calisanDB = CalisanDAO.kullaniciDogrula(calisan.getKullaniciAdi(), calisan.getSifre());

                    if (calisanDB != null) {
                        JOptionPane.showMessageDialog(null, "Giriş Başarılı!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);

                        Anamenu anasayfa = new Anamenu(calisanDB);
                        anasayfa.setVisible(true);

                        setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Kullanıcı adı veya şifre hatalı!", "Hata", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
            setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(GirisEkrani::new);
    }
}