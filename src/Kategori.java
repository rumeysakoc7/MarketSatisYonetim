public class Kategori {
    private int kategoriId;
    private String kategoriAdi;

    private String kategoriAciklama;
    private boolean aktif;
    public Kategori(int kategoriId, String kategoriAdi,String kategoriAciklama, boolean aktif) {
        this.kategoriId = kategoriId;
        this.kategoriAdi = kategoriAdi;
        this.kategoriAciklama = kategoriAciklama;
        this.aktif = aktif;
    }

    public Kategori() {

    }
    @Override
    public String toString() {
        return this.kategoriAdi;
    }

    public int getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(int kategoriId) {
        this.kategoriId = kategoriId;
    }

    public String getKategoriAdi() {
        return kategoriAdi;
    }

    public String getKategoriAciklama() {
        return kategoriAciklama;
    }

    public void setKategoriAciklama(String kategoriAciklama) {
        this.kategoriAciklama = kategoriAciklama;
    }


    public boolean isAktif() {
        return aktif;
    }

    public void setAktif(boolean aktif) {
        this.aktif = aktif;
    }

    public void setKategoriAdi(String kategoriAdi) {

        this.kategoriAdi = kategoriAdi;
    }

}
