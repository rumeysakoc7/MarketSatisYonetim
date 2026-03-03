import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Siparisdetaylari extends JFrame {
    private JTable tableSiparisdetay;
    private JPanel Siparisdetay;
    private JTextField texturunara;
    private JTextField textmusteriara;
    private JLabel siparisdetay;
    private JLabel urunara;
    private JLabel musteriara;
    private DefaultTableModel tableModel;


    public Siparisdetaylari() {
        setTitle("SİPARİŞ DETAYLARI");
        setSize(1000,800);
        setContentPane(Siparisdetay);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] columnNames = {
                "Sipariş ID", "Sipariş Tarihi", "Ödeme Türü",
                "Müşteri ID", "Müşteri Ad Soyad", "Ürün ID",
                "Ürün Adı", "Miktar", "Birim Fiyat", "Toplam Tutar"
        };
        tableModel = new DefaultTableModel(columnNames, 0);
        tableSiparisdetay.setModel(tableModel);
        setLocationRelativeTo(null);
        verileriYukle();
        urunAra();
        musteriAra();
        setVisible(true);
    }

    private void urunAra() {
        texturunara.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String filtreMetni = texturunara.getText();
                TabloFiltreleme.tabloyuFiltrele(tableSiparisdetay, filtreMetni, 6);
            }
        });
    }

    private void musteriAra() {
        textmusteriara.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String filtreMetni = textmusteriara.getText();
                TabloFiltreleme.tabloyuFiltrele(tableSiparisdetay, filtreMetni, 4);
            }
        });
    }

    private void verileriYukle() {
        SiparisDetayDAO dao = new SiparisDetayDAO();
        ArrayList<Siparis> siparisListesi = dao.getAllSiparisler();

        tableModel.setRowCount(0);

        for (Siparis siparis : siparisListesi) {
            for (SiparisDetay detay : siparis.getSiparisDetaylari()) {
                tableModel.addRow(new Object[]{
                        siparis.getSiparisId(),
                        siparis.getSiparisTarihi(),
                        siparis.getOdemeTuru(),
                        siparis.getMusteri().getId(),
                        siparis.getMusteri().getAd() + " " + siparis.getMusteri().getSoyad(),
                        detay.getUrun().getUrunId(),
                        detay.getUrun().getUrunAdi(),
                        detay.getMiktar(),
                        detay.getBirimFiyat(),
                        detay.getToplamTutar()
                });
            }
        }
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(Siparisdetaylari::new);
    }
}
