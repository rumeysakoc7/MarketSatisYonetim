import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Baglanti {

    private Connection conn = null;
    private static Baglanti instance;

    private Baglanti() {
    }

    public static Baglanti getInstance() {
        if (instance == null) {
            instance = new Baglanti();
        }
        return instance;
    }

    public Connection baglan() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection( "jdbc:mysql://127.0.0.1:3306/Market_otomasyon?useSSL=false&serverTimezone=UTC", "root", "your_password_here" );
                System.out.println("Bağlantı başarılı");
            }
        } catch (SQLException var2) {
            System.out.println("Bağlantı Başarısız: " + var2.getMessage());
        }
        return conn;
    }
}
