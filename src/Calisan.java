public class Calisan extends Kisi{
    private String unvan;
    private String kullaniciAdi;
    private String sifre;

    public Calisan(int id, String ad, String soyad, String iletisimBilgisi,
                   String unvan, String kullaniciAdi, String sifre, boolean aktif) {
        super(id, ad, soyad, iletisimBilgisi,aktif);
        this.unvan = unvan;
        this.kullaniciAdi = kullaniciAdi;
        this.sifre = sifre;

    }

    public Calisan() {
        super();
    }

    public String getUnvan() {
        return unvan;
    }

    public void setUnvan(String unvan) {
        this.unvan = unvan;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public boolean girisYap(String kullaniciAdi, String sifre) {
        return this.kullaniciAdi.equals(kullaniciAdi) && this.sifre.equals(sifre);
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

}
