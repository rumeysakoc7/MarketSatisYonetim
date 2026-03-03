public class Musteri extends Kisi{
    private String odemeTuru;
    public Musteri(int id, String ad, String soyad, String iletisimBilgisi,String odemeTuru, boolean aktif) {
        super(id, ad, soyad, iletisimBilgisi,aktif);

        this.odemeTuru = odemeTuru;
    }
    public Musteri() {
        super();
    }

}
