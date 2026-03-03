public class Personel extends Calisan {
    private String gorev;

    public Personel(int id, String ad, String soyad, String iletisimBilgisi, String unvan, String kullaniciAdi, String sifre, String gorev, boolean aktif) {
        super(id, ad, soyad, iletisimBilgisi, unvan, kullaniciAdi, sifre,aktif);
        this.gorev = gorev;
    }
    public Personel() {
        super();
    }



    public Personel(String ad, String soyad, String unvan, String gorev, boolean aktif, String iletisim) {
    }

    public String getGorev() {
        return gorev;
    }

    public void setGorev(String gorev) {
        this.gorev = gorev;
    }

}
