import java.util.ArrayList;
public class Siparis {
    private int siparisId;
    private Musteri musteri;
    private String odemeTuru;
    private String siparisTarihi;
    private ArrayList<SiparisDetay> siparisDetaylari;

    public Siparis(int siparisId, Musteri musteri, String odemeTuru, String siparisTarihi) {
        this.siparisId = siparisId;
        this.musteri = musteri;
        this.odemeTuru = odemeTuru;
        this.siparisTarihi = siparisTarihi;
        this.siparisDetaylari = new ArrayList<>();
    }

    public Siparis(int siparisID, Musteri musteri) {
    }
    public int getSiparisId() {
        return siparisId;
    }

    public void setSiparisId(int siparisId) {
        this.siparisId = siparisId;
    }

    public Musteri getMusteri() {
        return musteri;
    }

    public void setMusteri(Musteri musteri) {
        this.musteri = musteri;
    }

    public String getOdemeTuru() {
        return odemeTuru;
    }

    public void setOdemeTuru(String odemeTuru) {
        this.odemeTuru = odemeTuru;
    }

    public String getSiparisTarihi() {
        return siparisTarihi;
    }


    public ArrayList<SiparisDetay> getSiparisDetaylari() {
        return siparisDetaylari;
    }


    }
