import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MusteriDAO {
    public static boolean musteriKaydet(Musteri musteri) {
        String sql = "INSERT INTO Musteri (Ad, Soyad, IletisimBilgisi, Aktif) VALUES (?, ?, ?, ?)";

        try (Connection con = Baglanti.getInstance().baglan();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, musteri.getAd());
            pstmt.setString(2, musteri.getSoyad());
            pstmt.setString(3, musteri.getIletisimBilgisi());
            pstmt.setBoolean(4, musteri.isAktif());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean musteriGuncelle(Musteri musteri) {
        String sql = "UPDATE Musteri SET Ad = ?, Soyad = ?, IletisimBilgisi = ?, Aktif = ? WHERE MusteriId = ?";

        try (Connection con = Baglanti.getInstance().baglan();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, musteri.getAd());
            pstmt.setString(2, musteri.getSoyad());
            pstmt.setString(3, musteri.getIletisimBilgisi());
            pstmt.setBoolean(4, musteri.isAktif());
            pstmt.setInt(5, musteri.getId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean musteriSil(int musteriId) {
        String sql = "UPDATE Musteri SET Aktif = ? WHERE MusteriId = ?";

        try (Connection con = Baglanti.getInstance().baglan();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setBoolean(1, false);
            pstmt.setInt(2, musteriId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Musteri> musteriGetir() {
        List<Musteri> musteriler = new ArrayList<>();
        String sql = "SELECT * FROM Musteri";

        try (Connection con = Baglanti.getInstance().baglan();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Musteri musteri = new Musteri(
                        rs.getInt("MusteriId"),
                        rs.getString("Ad"),
                        rs.getString("Soyad"),
                        rs.getString("IletisimBilgisi"),
                        "",
                        rs.getBoolean("Aktif")
                );
                musteriler.add(musteri);
            }


        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Veritabanından müşteri getirilemedi.");
        }

        return musteriler;
    }

    public static List<Musteri> aktifMusterileriGetir() {
        List<Musteri> musteriler = new ArrayList<>();
        String sql = "SELECT * FROM Musteri WHERE Aktif = true";

        try (Connection con = Baglanti.getInstance().baglan();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Musteri musteri = new Musteri(
                        rs.getInt("MusteriId"),
                        rs.getString("Ad"),
                        rs.getString("Soyad"),
                        rs.getString("IletisimBilgisi"),
                        "",
                        rs.getBoolean("Aktif")
                );
                musteriler.add(musteri);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return musteriler;
    }

}
