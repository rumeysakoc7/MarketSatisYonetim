public class Alis {
    private Urun urun;
    private Tedarikci tedarikci;
    private double alisFiyati;
    private int miktar;
    public Alis(Urun urun, Tedarikci tedarikci, double alisFiyati, int miktar) {
        this.urun = urun;
        this.tedarikci = tedarikci;
        this.alisFiyati = alisFiyati;
        this.miktar = miktar;

    }

    public Urun getUrun() {

        return urun;
    }

    public void setUrun(Urun urun) {

        this.urun = urun;
    }

    public Tedarikci getTedarikci() {

        return tedarikci;
    }

    public void setTedarikci(Tedarikci tedarikci) {

        this.tedarikci = tedarikci;
    }

    public double getAlisFiyati() {

        return alisFiyati;
    }

    public void setAlisFiyati(double alisFiyati) {

        this.alisFiyati = alisFiyati;
    }

    public int getMiktar() {
        return miktar;
    }

    public void setMiktar(int miktar) {
        this.miktar = miktar;
    }
    public double toplamMaliyet() {
        return alisFiyati * miktar;
    }

}
