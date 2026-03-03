import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class AlisDAO {
    public static boolean alisKaydet(Alis alis) {
        String alisSQL = "INSERT INTO Alis (UrunID, TedarikciID, AlisFiyati, ToplamMaliyet, Miktar) VALUES (?, ?, ?, ?, ?)";
        String stokGuncelleSQL = "UPDATE Urun SET StokMiktari = StokMiktari + ? WHERE UrunID = ?";

        try (Connection con = Baglanti.getInstance().baglan()) {
            con.setAutoCommit(false);

            try (PreparedStatement alisStmt = con.prepareStatement(alisSQL)) {
                alisStmt.setInt(1, alis.getUrun().getUrunId());
                alisStmt.setInt(2, alis.getTedarikci().getId());
                alisStmt.setDouble(3, alis.getAlisFiyati());
                alisStmt.setDouble(4, alis.toplamMaliyet());
                alisStmt.setInt(5, alis.getMiktar());

                int affectedRows = alisStmt.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Alış kaydedilemedi!");
                }
            }

            try (PreparedStatement stokStmt = con.prepareStatement(stokGuncelleSQL)) {
                stokStmt.setInt(1, alis.getMiktar());
                stokStmt.setInt(2, alis.getUrun().getUrunId());
                stokStmt.executeUpdate();
            }
            con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Alis> urunVeTedarikciBilgileriniGetir() {
        List<Alis> alisListesi = new ArrayList<>();
        String sql = "SELECT u.UrunID, u.UrunAdi, u.StokMiktari, u.AlisFiyat, " +
                "t.TedarikciID, t.Ad , t.Firmaadi, t.IletisimBilgisi " +
                "FROM Urun u " +
                "INNER JOIN Tedarikci t ON u.TedarikciID = t.TedarikciID " +
                "WHERE u.Aktif = TRUE AND t.Aktif = TRUE";

        try (Connection con = Baglanti.getInstance().baglan();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Urun urun = new Urun(
                        rs.getInt("UrunID"),
                        rs.getString("UrunAdi"),
                        rs.getDouble("AlisFiyat"),
                        0.0, // Satış fiyatı gerekmiyor
                        rs.getInt("StokMiktari"),
                        true,
                        null,
                        null
                );

                Tedarikci tedarikci = new Tedarikci(
                        rs.getInt("TedarikciID"),
                        rs.getString("Ad"),
                        "",
                        rs.getString("IletisimBilgisi"),
                        rs.getString("Firmaadi"),
                        true
                );

                Alis alis = new Alis(urun, tedarikci, urun.getAlisFiyat(), urun.getStokMiktari());
                alisListesi.add(alis);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alisListesi;
    }
}
