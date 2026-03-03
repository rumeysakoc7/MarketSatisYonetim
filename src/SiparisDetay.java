public class SiparisDetay {
    private Urun urun;
    private int miktar;
    private double birimFiyat;

    public SiparisDetay(Urun urun, int miktar, double birimFiyat) {
        this.urun = urun;
        this.miktar = miktar;
        this.birimFiyat = birimFiyat;
    }

    public SiparisDetay(int siparisDetayID, Urun urun, int miktar, double birimFiyat, double toplamTutar) {
    }

    public double getToplamTutar() {
        return miktar * birimFiyat;
    }
    public Urun getUrun() {
        return urun;
    }

    public void setUrun(Urun urun) {
        this.urun = urun;
    }

    public int getMiktar() {
        return miktar;
    }

    public void setMiktar(int miktar) {
        this.miktar = miktar;
    }

    public double getBirimFiyat() {
        return birimFiyat;
    }

    public void setBirimFiyat(double birimFiyat) {
        this.birimFiyat = birimFiyat;
    }

}




