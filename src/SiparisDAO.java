import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class SiparisDAO {

    public static boolean siparisKaydet(Siparis siparis) {
        String siparisSQL = "INSERT INTO Siparis (MusteriID, OdemeTuru, SiparisTarihi) VALUES (?, ?, ?)";
        String detaySQL = "INSERT INTO SiparisDetay (SiparisID, UrunID, Miktar, BirimFiyat, ToplamTutar) VALUES (?, ?, ?, ?, ?)";
        String stokGuncelleSQL = "UPDATE Urun SET StokMiktari = StokMiktari - ? WHERE UrunID = ?";

        try (Connection con = Baglanti.getInstance().baglan()) {
            con.setAutoCommit(false);


            try (PreparedStatement siparisStmt = con.prepareStatement(siparisSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
                siparisStmt.setInt(1, siparis.getMusteri().getId());
                siparisStmt.setString(2, siparis.getOdemeTuru());
                siparisStmt.setString(3, siparis.getSiparisTarihi());

                int affectedRows = siparisStmt.executeUpdate();
                if (affectedRows == 0) throw new SQLException("Sipariş kaydedilemedi!");


                ResultSet generatedKeys = siparisStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    siparis.setSiparisId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Sipariş ID alınamadı!");
                }
            }


            try (PreparedStatement detayStmt = con.prepareStatement(detaySQL);
                 PreparedStatement stokStmt = con.prepareStatement(stokGuncelleSQL)) {
                for (SiparisDetay detay : siparis.getSiparisDetaylari()) {
                    int urunID = detay.getUrun().getUrunId();

                    double toplamTutar = detay.getMiktar() * detay.getBirimFiyat();


                    detayStmt.setInt(1, siparis.getSiparisId());
                    detayStmt.setInt(2, urunID);
                    detayStmt.setInt(3, detay.getMiktar());
                    detayStmt.setDouble(4, detay.getBirimFiyat());
                    detayStmt.setDouble(5, toplamTutar);
                    detayStmt.addBatch();

                    stokStmt.setInt(1, detay.getMiktar());
                    stokStmt.setInt(2, urunID);
                    stokStmt.addBatch();
                }

                detayStmt.executeBatch();
                stokStmt.executeBatch();
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
