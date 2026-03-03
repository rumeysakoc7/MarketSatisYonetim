import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TedarikciDAO {

    public static boolean tedarikciKaydet(Tedarikci tedarikci) {
        String sql = "INSERT INTO Tedarikci (Ad, Soyad, IletisimBilgisi, Firmaadi, Aktif) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Baglanti.getInstance().baglan();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, tedarikci.getAd());
            pstmt.setString(2, tedarikci.getSoyad());
            pstmt.setString(3, tedarikci.getIletisimBilgisi());
            pstmt.setString(4, tedarikci.getFirmaAdi());
            pstmt.setBoolean(5, tedarikci.isAktif());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean tedarikciGuncelle(Tedarikci tedarikci) {
        String sql = "UPDATE Tedarikci SET Ad = ?, Soyad = ?, IletisimBilgisi = ?,Firmaadi = ?, Aktif = ? WHERE TedarikciId = ?";

        try (Connection con = Baglanti.getInstance().baglan();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, tedarikci.getAd());
            pstmt.setString(2, tedarikci.getSoyad());
            pstmt.setString(3, tedarikci.getIletisimBilgisi());
            pstmt.setString(4, tedarikci.getFirmaAdi());
            pstmt.setBoolean(5, tedarikci.isAktif());
            pstmt.setInt(6, tedarikci.getId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean tedarikciSil(int tedarikciId) {
        String sql = "UPDATE Tedarikci SET Aktif = ? WHERE TedarikciId = ?";

        try (Connection con = Baglanti.getInstance().baglan();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setBoolean(1, false);
            pstmt.setInt(2, tedarikciId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static List<Tedarikci> tedarikciGetir() {
        List<Tedarikci> tedarikciler = new ArrayList<>();
        String sql = "SELECT * FROM Tedarikci";

        try (Connection con = Baglanti.getInstance().baglan();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Tedarikci tedarikci = new Tedarikci(
                        rs.getInt("TedarikciId"),
                        rs.getString("Ad"),
                        rs.getString("Soyad"),
                        rs.getString("IletisimBilgisi"),
                        rs.getString("Firmaadi"),
                        rs.getBoolean("Aktif")
                );

                tedarikciler.add(tedarikci);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Veritabanından tedarikçi getirilemedi.");
        }

        return tedarikciler;
    }


    public static List<Tedarikci> aktifTedarikcileriGetir() {
        List<Tedarikci> tedarikciler = new ArrayList<>();
        String sql = "SELECT * FROM Tedarikci WHERE Aktif = TRUE";

        try (Connection con = Baglanti.getInstance().baglan();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Tedarikci tedarikci = new Tedarikci(
                        rs.getInt("TedarikciId"),
                        rs.getString("Ad"),
                        rs.getString("Soyad"),
                        rs.getString("IletisimBilgisi"),
                        rs.getString("Firmaadi"),
                        rs.getBoolean("Aktif")
                );
                tedarikciler.add(tedarikci);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Veritabanından aktif tedarikçiler getirilemedi.");
        }

        return tedarikciler;
    }
}
