import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class KayitOlDAO {
    public static boolean kullaniciAdiVarMi(String kullaniciAdi) {
        String sql = "SELECT COUNT(*) FROM (SELECT KullaniciAdi FROM Personel UNION SELECT KullaniciAdi FROM Mudur) AS KullaniciKontrol WHERE KullaniciAdi = ?";
        try (Connection con = Baglanti.getInstance().baglan();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, kullaniciAdi);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean personelKaydet(Personel personel) {

        if (kullaniciAdiVarMi(personel.getKullaniciAdi())) {
            System.out.println("Bu kullanıcı adı zaten kullanılıyor: " + personel.getKullaniciAdi());
            return false;
        }

        String sql = "INSERT INTO Personel (P_Ad, P_Soyad, KullaniciAdi, P_IletisimBilgisi, Sifre, Unvan, Gorev) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Baglanti.getInstance().baglan();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, personel.getAd());
            pstmt.setString(2, personel.getSoyad());
            pstmt.setString(3, personel.getKullaniciAdi());
            pstmt.setString(4, personel.getIletisimBilgisi());
            pstmt.setString(5, personel.getSifre());
            pstmt.setString(6, personel.getUnvan());
            pstmt.setString(7, personel.getGorev());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean mudurKaydet(Mudur mudur) {
        if (kullaniciAdiVarMi(mudur.getKullaniciAdi())) {
            System.out.println("Bu kullanıcı adı zaten kullanılıyor: " + mudur.getKullaniciAdi());
            return false;
        }

        String sql = "INSERT INTO Mudur (M_Ad, M_Soyad, KullaniciAdi, M_IletisimBilgisi, Sifre, Unvan) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Baglanti.getInstance().baglan();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, mudur.getAd());
            pstmt.setString(2, mudur.getSoyad());
            pstmt.setString(3, mudur.getKullaniciAdi());
            pstmt.setString(4, mudur.getIletisimBilgisi());
            pstmt.setString(5, mudur.getSifre());
            pstmt.setString(6, mudur.getUnvan());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<String> getGorevler() {
        ArrayList<String> gorevListesi = new ArrayList<>();
        String sql = "SELECT DISTINCT Gorev FROM Personel WHERE Gorev IS NOT NULL";
        try (Connection con = Baglanti.getInstance().baglan();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                gorevListesi.add(rs.getString("Gorev"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gorevListesi;
    }

}
