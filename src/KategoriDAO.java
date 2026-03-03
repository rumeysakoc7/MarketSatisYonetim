import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KategoriDAO {
    private static Connection getConnection() {
        return Baglanti.getInstance().baglan();
        }

    public static boolean kategoriEkle(Kategori kategori) {
        String sql = "INSERT INTO Kategori (KategoriAdi, KategoriAciklama, Aktif) VALUES (?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, kategori.getKategoriAdi());
            pst.setString(2, kategori.getKategoriAciklama());
            pst.setBoolean(3, kategori.isAktif());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Kategori eklenirken hata oluştu: " + e.getMessage());
            return false;
        }
    }


    public static boolean kategoriGuncelle(Kategori kategori) {
        String sql = "UPDATE Kategori SET KategoriAdi = ?, KategoriAciklama = ?, Aktif = ? WHERE KategoriID = ?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, kategori.getKategoriAdi());
            pst.setString(2, kategori.getKategoriAciklama());
            pst.setBoolean(3, kategori.isAktif());
            pst.setInt(4, kategori.getKategoriId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Kategori güncellenirken hata oluştu: " + e.getMessage());
            return false;
        }
    }

    public static boolean kategoriSil(int id) {
        String sql = "UPDATE Kategori SET Aktif = FALSE WHERE KategoriID = ?";
        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Kategori silinirken hata oluştu: " + e.getMessage());
            return false;
        }
    }

    public static List<Kategori> kategoriListele() {
        List<Kategori> kategoriler = new ArrayList<>();
        String sql = "SELECT * FROM Kategori";

        try (Connection con = getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                Kategori kategori = new Kategori(
                        rs.getInt("KategoriID"),
                        rs.getString("KategoriAdi"),
                        rs.getString("KategoriAciklama"),
                        rs.getBoolean("Aktif")
                );
                kategoriler.add(kategori);
            }

        } catch (SQLException e) {
            System.err.println("Kategoriler listelenirken hata oluştu: " + e.getMessage());
        }

        return kategoriler;
    }


    public static List<Kategori> aktifKategorileriGetir() {
        List<Kategori> kategoriler = new ArrayList<>();
        String sql = "SELECT * FROM Kategori WHERE Aktif = TRUE";

        try (Connection con = Baglanti.getInstance().baglan();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Kategori kategori = new Kategori(
                        rs.getInt("KategoriID"),
                        rs.getString("KategoriAdi"),
                        rs.getString("KategoriAciklama"),
                        rs.getBoolean("Aktif")
                );
                kategoriler.add(kategori);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Veritabanından aktif tedarikçiler getirilemedi.");
        }

        return kategoriler;
    }

}