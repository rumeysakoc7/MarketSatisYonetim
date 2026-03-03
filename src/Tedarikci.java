public class Tedarikci extends Kisi {
    private boolean aktif;
    private String firmaAdi;

    public Tedarikci(int id, String ad, String soyad, String iletisimBilgisi,String firmaAdi,boolean aktif) {
        super(id, ad, soyad, iletisimBilgisi,aktif);
        this.firmaAdi = firmaAdi;
    }

    public Tedarikci() {
        super();
    }

    public String getFirmaAdi() {
        return firmaAdi;
    }

    public void setFirmaAdi(String firmaAdi) {
        this.firmaAdi = firmaAdi;
    }


    @Override
    public String toString() {
        return this.firmaAdi;
    }

}
