public class Urun {
    private int urunId;
    private String urunAdi;
    private double alisFiyat;
    private double satisFiyat;
    private int stokMiktari;
    private Kategori kategori;
    private Tedarikci tedarikci;
    private boolean aktif;

    public Urun(int urunId, String urunAdi, double alisFiyat, double satisFiyat, int stokMiktari, boolean aktif, Kategori kategori, Tedarikci tedarikci) {
        this.urunId = urunId;
        this.urunAdi = urunAdi;
        setAlisFiyat(alisFiyat);
        setSatisFiyat(satisFiyat);
        setStokMiktari(stokMiktari);
        this.aktif = aktif;
        setKategori(kategori);
        setTedarikci(tedarikci);
    }

    public Urun() {
    }
    public Urun(int urunId, String urunAdi) {
        this.urunId = urunId;
        this.urunAdi = urunAdi;
        this.alisFiyat = 0.0;
        this.satisFiyat = 0.0;
        this.stokMiktari = 0;
        this.aktif = true;
        this.kategori = null;
        this.tedarikci = null;
    }

    public int getUrunId() {
        return urunId;
    }

    public void setUrunId(int urunId) {
        this.urunId = urunId;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        if (urunAdi != null && !urunAdi.trim().isEmpty()) {
            this.urunAdi = urunAdi;
        } else {
            throw new IllegalArgumentException("Ürün adı boş olamaz.");
        }
    }

    public double getAlisFiyat() {
        return alisFiyat;
    }

    public void setAlisFiyat(double alisFiyat) {
        if (alisFiyat >= 0) {
            this.alisFiyat = alisFiyat;
        } else {
            throw new IllegalArgumentException("Alış fiyatı negatif olamaz.");
        }
    }

    public double getSatisFiyat() {
        return satisFiyat;
    }

    public void setSatisFiyat(double satisFiyat) {
        if (satisFiyat >= 0) {
            this.satisFiyat = satisFiyat;
        } else {
            throw new IllegalArgumentException("Satış fiyatı negatif olamaz.");
        }
    }
    public int getStokMiktari() {
        return stokMiktari;
    }

    public void setStokMiktari(int stokMiktari) {
        if (stokMiktari >= 0) {
            this.stokMiktari = stokMiktari;
        } else {
            throw new IllegalArgumentException("Stok miktarı negatif olamaz.");
        }
    }

    public Kategori getKategori() {
        return kategori;
    }
    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }


    public Tedarikci getTedarikci() {
        return tedarikci;
    }


    public void setTedarikci(Tedarikci tedarikci) {
            this.tedarikci = tedarikci;
    }

    public boolean isAktif() {
        return aktif;
    }

    public void setAktif(boolean aktif) {
        this.aktif = aktif;
    }

}
