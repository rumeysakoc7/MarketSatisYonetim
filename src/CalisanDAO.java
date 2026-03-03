import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CalisanDAO {

    public static Calisan kullaniciDogrula(String kullaniciAdi, String sifre) {

        Calisan calisan = null;
        String queryMudur = "SELECT * FROM Mudur WHERE KullaniciAdi = ? AND Sifre = ? AND Aktif = true";
        String queryPersonel = "SELECT * FROM Personel WHERE KullaniciAdi = ? AND Sifre = ? AND Aktif = true";

        Connection con = Baglanti.getInstance().baglan();

        try {
            // Müdür kontrolü
            try (PreparedStatement pst = con.prepareStatement(queryMudur)) {
                pst.setString(1, kullaniciAdi);
                pst.setString(2, sifre);

                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    Mudur mudur = new Mudur();
                    mudur.setId(rs.getInt("MudurID"));
                    mudur.setAd(rs.getString("M_Ad"));
                    mudur.setSoyad(rs.getString("M_Soyad"));
                    mudur.setIletisimBilgisi(rs.getString("M_IletisimBilgisi"));
                    mudur.setUnvan(rs.getString("Unvan"));
                    mudur.setKullaniciAdi(kullaniciAdi);
                    mudur.setSifre(sifre);
                    mudur.setAktif(true);

                    return mudur;
                }
            }

            try (PreparedStatement pst = con.prepareStatement(queryPersonel)) {
                pst.setString(1, kullaniciAdi);
                pst.setString(2, sifre);

                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    Personel personel = new Personel();
                    personel.setId(rs.getInt("PersonelID"));
                    personel.setAd(rs.getString("P_Ad"));
                    personel.setSoyad(rs.getString("P_Soyad"));
                    personel.setIletisimBilgisi(rs.getString("P_IletisimBilgisi"));
                    personel.setUnvan(rs.getString("Unvan"));
                    personel.setKullaniciAdi(kullaniciAdi);
                    personel.setSifre(sifre);
                    personel.setGorev(rs.getString("Gorev"));
                    personel.setAktif(true);

                    return personel;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void personelEkle(Personel personel) {
        String sql = "INSERT INTO Personel (P_Ad, P_Soyad, P_IletisimBilgisi, Unvan, Gorev, KullaniciAdi, Sifre, Aktif) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, true)";
        try (Connection con = Baglanti.getInstance().baglan();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, personel.getAd());
            pst.setString(2, personel.getSoyad());
            pst.setString(3, personel.getIletisimBilgisi());
            pst.setString(4, personel.getUnvan());
            pst.setString(5, personel.getGorev());
            pst.setString(6, personel.getKullaniciAdi());
            pst.setString(7, personel.getSifre());

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void mudurEkle(Mudur mudur) {
        String sql = "INSERT INTO Mudur (M_Ad, M_Soyad, M_IletisimBilgisi, Unvan, KullaniciAdi, Sifre, Aktif) " +
                "VALUES (?, ?, ?, ?, ?, ?, true)";
        try (Connection con = Baglanti.getInstance().baglan();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, mudur.getAd());
            pst.setString(2, mudur.getSoyad());
            pst.setString(3, mudur.getIletisimBilgisi());
            pst.setString(4, mudur.getUnvan());
            pst.setString(5, mudur.getKullaniciAdi());
            pst.setString(6, mudur.getSifre());

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void personelGuncelle(Personel personel) {
        String sql = "UPDATE Personel SET P_Ad = ?, P_Soyad = ?, P_IletisimBilgisi = ?, Unvan = ?, Gorev = ?, KullaniciAdi = ?, Sifre = ?, Aktif = ? " +
                "WHERE PersonelID = ?";
        try (Connection con = Baglanti.getInstance().baglan();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, personel.getAd());
            pst.setString(2, personel.getSoyad());
            pst.setString(3, personel.getIletisimBilgisi());
            pst.setString(4, personel.getUnvan());
            pst.setString(5, personel.getGorev());
            pst.setString(6, personel.getKullaniciAdi());
            pst.setString(7, personel.getSifre());
            pst.setBoolean(8, personel.isAktif());
            pst.setInt(9, personel.getId());

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void mudurGuncelle(Mudur mudur) {
        String sql = "UPDATE Mudur SET M_Ad = ?, M_Soyad = ?, M_IletisimBilgisi = ?, Unvan = ?, KullaniciAdi = ?, Sifre = ?, Aktif = ? " +
                "WHERE MudurID = ?";
        try (Connection con = Baglanti.getInstance().baglan();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, mudur.getAd());
            pst.setString(2, mudur.getSoyad());
            pst.setString(3, mudur.getIletisimBilgisi());
            pst.setString(4, mudur.getUnvan());
            pst.setString(5, mudur.getKullaniciAdi());
            pst.setString(6, mudur.getSifre());
            pst.setBoolean(7, mudur.isAktif());
            pst.setInt(8, mudur.getId());

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static boolean personelSil(int personelId) {
        String sql = "UPDATE Personel SET Aktif = false WHERE PersonelID = ?";
        try (Connection con = Baglanti.getInstance().baglan();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, personelId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean mudurSil(int mudurId) {
        String sql = "UPDATE Mudur SET Aktif = false WHERE MudurID = ?";
        try (Connection con = Baglanti.getInstance().baglan();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, mudurId);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static List<Mudur> mudurListele() {
        List<Mudur> mudurler = new ArrayList<>();
        String query = "SELECT * FROM Mudur";

        try (Connection con = Baglanti.getInstance().baglan();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                mudurler.add(new Mudur(
                        rs.getInt("MudurID"),
                        rs.getString("M_Ad"),
                        rs.getString("M_Soyad"),
                        rs.getString("M_IletisimBilgisi"),
                        rs.getString("Unvan"),
                        rs.getString("KullaniciAdi"),
                        rs.getString("Sifre"),
                        rs.getBoolean("Aktif")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mudurler;
    }

    public static List<Personel> personelListele() {
        List<Personel> personeller = new ArrayList<>();
        String query = "SELECT * FROM Personel";

        try (Connection con = Baglanti.getInstance().baglan();
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                personeller.add(new Personel(
                        rs.getInt("PersonelID"),
                        rs.getString("P_Ad"),
                        rs.getString("P_Soyad"),
                        rs.getString("P_IletisimBilgisi"),
                        rs.getString("Unvan"),
                        rs.getString("KullaniciAdi"),
                        rs.getString("Sifre"),
                        rs.getString("Gorev"),
                        rs.getBoolean("Aktif")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return personeller;
    }

    public static boolean kullaniciVarMi(String kullaniciAdi) {
        String sqlMudur = "SELECT 1 FROM Mudur WHERE KullaniciAdi = ? AND Aktif = true";
        String sqlPersonel = "SELECT 1 FROM Personel WHERE KullaniciAdi = ? AND Aktif = true";

        try (Connection con = Baglanti.getInstance().baglan()) {

            try (PreparedStatement pstMudur = con.prepareStatement(sqlMudur)) {
                pstMudur.setString(1, kullaniciAdi);
                try (ResultSet rs = pstMudur.executeQuery()) {
                    if (rs.next()) {
                        return true;
                    }
                }
            }

            try (PreparedStatement pstPersonel = con.prepareStatement(sqlPersonel)) {
                pstPersonel.setString(1, kullaniciAdi);
                try (ResultSet rs = pstPersonel.executeQuery()) {
                    if (rs.next()) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}








