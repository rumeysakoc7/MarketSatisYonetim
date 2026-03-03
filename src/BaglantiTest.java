public class BaglantiTest {

    public static void main(String[] args) {
        Baglanti baglanti = Baglanti.getInstance();
        if (baglanti.baglan() != null) {
            System.out.println("Veritabanı bağlantısı başarıyla kuruldu!");
        } else {
            System.out.println("Veritabanı bağlantısı başarısız oldu!");
        }
    }
}

