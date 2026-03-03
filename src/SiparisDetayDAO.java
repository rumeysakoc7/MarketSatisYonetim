import java.sql.*;
import java.util.ArrayList;
public class SiparisDetayDAO {

        public ArrayList<Siparis> getAllSiparisler() {
            ArrayList<Siparis> siparisListesi = new ArrayList<>();
            String sql = """
            SELECT 
                s.SiparisID, 
                s.SiparisTarihi, 
                s.OdemeTuru, 
                m.MusteriID, 
                m.Ad, 
                m.Soyad, 
                m.IletisimBilgisi, 
                u.UrunID, 
                u.UrunAdi, 
                sd.Miktar, 
                sd.BirimFiyat 
            FROM 
                Siparis s
            JOIN Musteri m ON s.MusteriID = m.MusteriID 
            JOIN SiparisDetay sd ON s.SiparisID = sd.SiparisID 
            JOIN Urun u ON sd.UrunID = u.UrunID
        """;

            try (Connection conn = Baglanti.getInstance().baglan();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Musteri musteri = new Musteri(
                            rs.getInt("MusteriID"),
                            rs.getString("Ad"),
                            rs.getString("Soyad"),
                            rs.getString("IletisimBilgisi"),
                            rs.getString("OdemeTuru"),
                            true
                    );

                    Urun urun = new Urun(
                            rs.getInt("UrunID"),
                            rs.getString("UrunAdi")
                    );

                    SiparisDetay siparisDetay = new SiparisDetay(
                            urun,
                            rs.getInt("Miktar"),
                            rs.getDouble("BirimFiyat")
                    );

                    Siparis mevcutSiparis = null;
                    for (Siparis siparis : siparisListesi) {
                        if (siparis.getSiparisId() == rs.getInt("SiparisID")) {
                            mevcutSiparis = siparis;
                            break;
                        }
                    }

                    if (mevcutSiparis == null) {
                        mevcutSiparis = new Siparis(
                                rs.getInt("SiparisID"),
                                musteri,
                                rs.getString("OdemeTuru"),
                                rs.getString("SiparisTarihi")
                        );
                        siparisListesi.add(mevcutSiparis);
                    }

                    mevcutSiparis.getSiparisDetaylari().add(siparisDetay);
                }
            } catch (SQLException e) {
                System.out.println("Siparişleri Getirme Hatası: " + e.getMessage());
            }

            return siparisListesi;
        }
    }






