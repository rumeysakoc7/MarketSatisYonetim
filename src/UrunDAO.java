import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class UrunDAO {
    private static Connection getConnection() {
        return Baglanti.getInstance().baglan();
    }

    public boolean urunEkle(Urun urun) {
        String sql = "INSERT INTO Urun (UrunAdi, AlisFiyat, SatisFiyat, StokMiktari, KategoriID, TedarikciID, Aktif) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, urun.getUrunAdi());
            pstmt.setDouble(2, urun.getAlisFiyat());
            pstmt.setDouble(3, urun.getSatisFiyat());
            pstmt.setInt(4, urun.getStokMiktari());
            pstmt.setInt(5, urun.getKategori().getKategoriId());
            pstmt.setInt(6, urun.getTedarikci().getId());
            pstmt.setBoolean(7, urun.isAktif());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Ürün ekleme hatası: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean urunSil(int urunId) {
        String sql = "UPDATE Urun SET Aktif = ? WHERE UrunID = ?";

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setBoolean(1, false);
            pstmt.setInt(2, urunId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Ürün silme hatası: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean urunGuncelle(Urun urun) {
        String sql = "UPDATE Urun SET UrunAdi = ?, AlisFiyat = ?, SatisFiyat = ?, StokMiktari = ?, KategoriID = ?, TedarikciID = ?, Aktif = ? WHERE UrunID = ?";

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setString(1, urun.getUrunAdi());
            pstmt.setDouble(2, urun.getAlisFiyat());
            pstmt.setDouble(3, urun.getSatisFiyat());
            pstmt.setInt(4, urun.getStokMiktari());
            pstmt.setInt(5, urun.getKategori().getKategoriId());
            pstmt.setInt(6, urun.getTedarikci().getId());
            pstmt.setBoolean(7, urun.isAktif());
            pstmt.setInt(8, urun.getUrunId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Ürün güncelleme hatası: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Urun> urunleriGetir() {
        List<Urun> urunler = new ArrayList<>();
        String sql = """
        SELECT u.UrunID, u.UrunAdi, u.AlisFiyat, u.SatisFiyat, u.StokMiktari, u.Aktif, 
               k.KategoriAdi, t.FirmaAdi
        FROM Urun u
        LEFT JOIN Kategori k ON u.KategoriID = k.KategoriID
        LEFT JOIN Tedarikci t ON u.TedarikciID = t.TedarikciID
    """;

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {

                Kategori kategori = new Kategori();
                kategori.setKategoriAdi(rs.getString("KategoriAdi"));

                Tedarikci tedarikci = new Tedarikci();
                tedarikci.setAd(rs.getString("FirmaAdi"));

                Urun urun = new Urun();
                urun.setUrunId(rs.getInt("UrunID"));
                urun.setUrunAdi(rs.getString("UrunAdi"));
                urun.setAlisFiyat(rs.getDouble("AlisFiyat"));
                urun.setSatisFiyat(rs.getDouble("SatisFiyat"));
                urun.setStokMiktari(rs.getInt("StokMiktari"));
                urun.setAktif(rs.getBoolean("Aktif"));
                urun.setKategori(kategori);
                urun.setTedarikci(tedarikci);

                urunler.add(urun);
            }

        } catch (SQLException e) {
            System.out.println("Ürünleri getirme hatası: " + e.getMessage());
            e.printStackTrace();
        }

        return urunler;
    }

    public static List<Urun> aktifUrunleriGetir() {
        List<Urun> urunler = new ArrayList<>();
        String sql = "SELECT UrunId, UrunAdi, SatisFiyat, StokMiktari, Aktif FROM Urun WHERE Aktif = true";

        try (Connection con = Baglanti.getInstance().baglan();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Urun urun = new Urun();
                urun.setUrunId(rs.getInt("UrunId"));
                urun.setUrunAdi(rs.getString("UrunAdi"));
                urun.setSatisFiyat(rs.getDouble("SatisFiyat"));
                urun.setStokMiktari(rs.getInt("StokMiktari"));
                urun.setAktif(rs.getBoolean("Aktif"));
                urunler.add(urun);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return urunler;
    }
}
