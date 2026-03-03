public abstract class Kisi {
    private int id;
    private String ad;
    private String soyad;
    private String iletisimBilgisi;
    private boolean aktif;

    public Kisi(int id, String ad, String soyad, String iletisimBilgisi, boolean aktif) {
        this.id = id;
        this.ad = ad;
        this.soyad = soyad;
        this.iletisimBilgisi = iletisimBilgisi;
        this.aktif = aktif;
    }

    public Kisi() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getIletisimBilgisi() {
        return iletisimBilgisi;
    }

    public boolean isAktif() {
        return aktif;
    }

    public void setAktif(boolean aktif) {
        this.aktif = aktif;
    }

    public void setIletisimBilgisi(String iletisimBilgisi) {
        this.iletisimBilgisi = iletisimBilgisi;
    }

}
