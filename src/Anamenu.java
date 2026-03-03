import javax.swing.*;

public class Anamenu extends JFrame {
    private JButton siparisDetaylarıButton;
    private JButton TedarikciButton;
    private JButton siparislerButton;
    private JButton buttonkategoriler;
    private JButton buttonmusteriler;
    private JButton urunAlisButton;
    private JButton buttonurunler;
    private JButton buttonCalisanlar;
    private JPanel Anamenu;
    private JLabel anamenu;

    public Anamenu(Calisan calisan) {
        setTitle("ANA MENÜ");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setContentPane(Anamenu);
        yetkilendirmeKontrol(calisan);

        siparisDetaylarıButton.addActionListener(e -> {
            new Siparisdetaylari();
        });
        TedarikciButton.addActionListener(e -> {
            new TedarikciYonetimi();
        });
        siparislerButton.addActionListener(e -> {
            new SiparisYonetim();
        });
        buttonkategoriler.addActionListener(e -> {
            new Kategoriler();
        });
        buttonmusteriler.addActionListener(e ->  {
            new MusteriYonetimi();
        });

        urunAlisButton.addActionListener(e -> {
            new TedarikciAlisYonetim();
        });
        buttonurunler.addActionListener(e -> {
            new UrunlerYonetimi();
        });
        buttonCalisanlar.addActionListener(e -> {
            new Calisanlar();
        });

        setVisible(true);
    }

    private void yetkilendirmeKontrol(Calisan calisan) {
        String unvan = calisan.getUnvan();
        if (unvan.equalsIgnoreCase("Personel")) {
           buttonCalisanlar.setEnabled(false);
            TedarikciButton.setEnabled(false);
            urunAlisButton.setEnabled(false);
        } else if (unvan.equalsIgnoreCase("Müdür")) {
        } else {
            JOptionPane.showMessageDialog(this, "Tanımsız bir unvan: " + unvan, "Uyarı", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GirisEkrani::new);
    }

}
