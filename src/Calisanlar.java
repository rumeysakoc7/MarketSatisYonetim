import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.util.List;

public class Calisanlar extends JFrame {
    private JTable tablepersonel;
    private JPanel calisanlar;
    private JPasswordField passwordsifre;
    private JFormattedTextField formattedTexttelno;
    private JTextField textKullaniciad;
    private JTextField textisim;
    private JTextField textsoyisim;
    private JTextField textFieldgorev;
    private JComboBox comboBoxUnvan;
    private JButton buttonekle;
    private JButton buttonSil;
    private JButton buttonGuncelle;
    private JLabel Calisanlar;
    private JLabel calisanisim;
    private JLabel calisansoyad;
    private JLabel telno;
    private JLabel kullaniciad;
    private JLabel sifre;
    private JLabel unvan;
    private JLabel GOREV;
    private JTable tablemudur;
    private JScrollPane scrollpersoneltable;
    private JScrollPane scrollmudurtable;
    private JButton buttontemizle;

    public Calisanlar() {
        setTitle("ÇALIŞANLAR SAYFASI");
        setSize(1000, 700);
        setContentPane(calisanlar);
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

        tabloPersonelDoldur();
        tabloMudurDoldur();
        buttontemizle.addActionListener(e -> temizle());
        comboBoxUnvan.addActionListener(e -> {
            String unvan = (String) comboBoxUnvan.getSelectedItem();
            if (unvan.equals("Müdür")) {
                textFieldgorev.setEnabled(false);
                textFieldgorev.setText("");
            } else {
                textFieldgorev.setEnabled(true);
            }
        });

        buttonekle.addActionListener(e -> {
            if (telefonNumarasiGecerli()) {
                ekleIslemi();
            }
        });


        buttonSil.addActionListener(e -> silIslemi());


        buttonGuncelle.addActionListener(e -> {
            if (telefonNumarasiGecerli()) {
                guncelleIslemi();
            }
        });


        tablepersonel.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablepersonel.getSelectedRow() != -1) {
                int selectedRow = tablepersonel.getSelectedRow();

                textisim.setText(tablepersonel.getValueAt(selectedRow, 1).toString());
                textsoyisim.setText(tablepersonel.getValueAt(selectedRow, 2).toString());
                formattedTexttelno.setText(tablepersonel.getValueAt(selectedRow, 3).toString());
                comboBoxUnvan.setSelectedItem(tablepersonel.getValueAt(selectedRow, 4).toString());
                textKullaniciad.setText(tablepersonel.getValueAt(selectedRow, 5).toString());
                passwordsifre.setText(tablepersonel.getValueAt(selectedRow, 6).toString());
                textFieldgorev.setText(tablepersonel.getValueAt(selectedRow, 7).toString());
            }
        });


        tablemudur.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablemudur.getSelectedRow() != -1) {
                int selectedRow = tablemudur.getSelectedRow();

                textisim.setText(tablemudur.getValueAt(selectedRow, 1).toString());
                textsoyisim.setText(tablemudur.getValueAt(selectedRow, 2).toString());
                formattedTexttelno.setText(tablemudur.getValueAt(selectedRow, 3).toString());
                comboBoxUnvan.setSelectedItem(tablemudur.getValueAt(selectedRow, 4).toString());
                textKullaniciad.setText(tablemudur.getValueAt(selectedRow, 5).toString());
                passwordsifre.setText(tablemudur.getValueAt(selectedRow, 6).toString());
                textFieldgorev.setText("");
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

    private void tabloPersonelDoldur() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Ad", "Soyad", "Telefon", "Unvan", "Kullanıcı Adı","Şifre", "Görev", "Aktif"}, 0);
        List<Personel> personeller = CalisanDAO.personelListele();

        for (Personel personel : personeller) {
            model.addRow(new Object[]{
                    personel.getId(),
                    personel.getAd(),
                    personel.getSoyad(),
                    personel.getIletisimBilgisi(),
                    personel.getUnvan(),
                    personel.getKullaniciAdi(),
                    personel.getSifre(),
                    personel.getGorev(),
                    personel.isAktif() ? "Evet" : "Hayır"
            });
        }

        tablepersonel.setModel(model);
    }


    private void tabloMudurDoldur() {
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Ad", "Soyad", "Telefon", "Unvan", "Kullanıcı Adı","Şifre", "Aktif"}, 0);
        List<Mudur> mudurler = CalisanDAO.mudurListele();

        for (Mudur mudur : mudurler) {
            model.addRow(new Object[]{
                    mudur.getId(),
                    mudur.getAd(),
                    mudur.getSoyad(),
                    mudur.getIletisimBilgisi(),
                    mudur.getUnvan(),
                    mudur.getKullaniciAdi(),
                    mudur.getSifre(),
                    mudur.isAktif() ? "Evet" : "Hayır"
            });
        }

        tablemudur.setModel(model);
    }



    private void ekleIslemi() {
        String ad = textisim.getText().trim();
        String soyad = textsoyisim.getText().trim();
        String telefon = formattedTexttelno.getText().trim();
        String kullaniciAdi = textKullaniciad.getText().trim();
        String sifre = new String(passwordsifre.getPassword()).trim();
        String unvan = (String) comboBoxUnvan.getSelectedItem();


        if (ad.isEmpty() || soyad.isEmpty() || telefon.isEmpty() || kullaniciAdi.isEmpty() || sifre.isEmpty() || unvan == null || unvan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurun!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (CalisanDAO.kullaniciVarMi(kullaniciAdi)) {
            JOptionPane.showMessageDialog(this, "Bu kullanıcı adı zaten kayıtlı! Lütfen başka bir kullanıcı adı deneyin.", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ("Seçiniz...".equals(unvan)) {
            JOptionPane.showMessageDialog(this, "Lütfen geçerli bir unvan seçiniz!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (unvan.equals("Personel")) {
            String gorev = textFieldgorev.getText().trim();


            if (gorev.isEmpty() || "Seçiniz...".equals(gorev)) {
                JOptionPane.showMessageDialog(this, "Lütfen geçerli bir görev giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Personel personel = new Personel(0, ad, soyad, telefon, unvan, kullaniciAdi, sifre, gorev, true);
            CalisanDAO.personelEkle(personel);
            tabloPersonelDoldur();
        } else if (unvan.equals("Müdür")) {
            Mudur mudur = new Mudur(0, ad, soyad, telefon, unvan, kullaniciAdi, sifre, true);
            CalisanDAO.mudurEkle(mudur);
            tabloMudurDoldur();
        }
    }

    private void silIslemi() {
        int selectedRow;

        if (comboBoxUnvan.getSelectedItem().equals("Personel")) {
            selectedRow = tablepersonel.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) tablepersonel.getValueAt(selectedRow, 0);
                if (CalisanDAO.personelSil(id)) {  // Başarılıysa güncelle
                    JOptionPane.showMessageDialog(this, "Personel güvenli bir şekilde silindi.");
                    tabloPersonelDoldur();
                } else {
                    JOptionPane.showMessageDialog(this, "Personel silme işlemi başarısız.");
                }
            }
        } else if (comboBoxUnvan.getSelectedItem().equals("Müdür")) {
            selectedRow = tablemudur.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) tablemudur.getValueAt(selectedRow, 0);
                if (CalisanDAO.mudurSil(id)) {  // Başarılıysa güncelle
                    JOptionPane.showMessageDialog(this, "Müdür güvenli bir şekilde silindi.");
                    tabloMudurDoldur();
                } else {
                    JOptionPane.showMessageDialog(this, "Müdür silme işlemi başarısız.");
                }
            }
        }
    }

    private void guncelleIslemi() {
        int selectedRow;
        String ad = textisim.getText().trim();
        String soyad = textsoyisim.getText().trim();
        String telefon = formattedTexttelno.getText().trim();
        String kullaniciAdi = textKullaniciad.getText().trim();
        String sifre = new String(passwordsifre.getPassword()).trim();
        String unvan = (String) comboBoxUnvan.getSelectedItem();


        if (ad.isEmpty() || soyad.isEmpty() || telefon.isEmpty() || kullaniciAdi.isEmpty() || sifre.isEmpty() || unvan == null || unvan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurun!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if ("Seçiniz...".equals(unvan)) {
            JOptionPane.showMessageDialog(this, "Lütfen geçerli bir unvan seçiniz!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (unvan.equals("Personel")) {
            String gorev = textFieldgorev.getText().trim();


            if (gorev.isEmpty() || "Seçiniz...".equals(gorev)) {
                JOptionPane.showMessageDialog(this, "Lütfen geçerli bir görev giriniz!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            selectedRow = tablepersonel.getSelectedRow();
            if (selectedRow != -1) {
                int id = Integer.parseInt(tablepersonel.getValueAt(selectedRow, 0).toString());


                boolean aktifDurumu = true;

                Personel personel = new Personel(id, ad, soyad, telefon, unvan, kullaniciAdi, sifre, gorev, aktifDurumu);
                CalisanDAO.personelGuncelle(personel);
                tabloPersonelDoldur();
                JOptionPane.showMessageDialog(this, "Personel bilgileri güncellendi ve aktif hale getirildi.");
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen güncellemek için bir personel seçin.");
            }
        } else if (unvan.equals("Müdür")) {
            selectedRow = tablemudur.getSelectedRow();
            if (selectedRow != -1) {
                int id = Integer.parseInt(tablemudur.getValueAt(selectedRow, 0).toString());


                boolean aktifDurumu = true;

                Mudur mudur = new Mudur(id, ad, soyad, telefon, unvan, kullaniciAdi, sifre, aktifDurumu);
                CalisanDAO.mudurGuncelle(mudur);
                tabloMudurDoldur();
                JOptionPane.showMessageDialog(this, "Müdür bilgileri güncellendi ve aktif hale getirildi.");
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen güncellemek için bir müdür seçin.");
            }
        }
    }

    private void temizle() {
        textisim.setText("");
        textsoyisim.setText("");
        textKullaniciad.setText("");
        textFieldgorev.setText("");
        formattedTexttelno.setText("");
        passwordsifre.setText("");
        comboBoxUnvan.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Calisanlar::new);
    }}


