#  Market Satış Yönetim Sistemi

Java ve MySQL kullanılarak geliştirilmiş, **Nesne Yönelimli Programlama (OOP)** prensiplerine dayalı masaüstü market otomasyon uygulamasıdır.

Bu proje; ürün, müşteri, sipariş ve stok süreçlerini yönetmek amacıyla geliştirilmiştir.

---

##  Projenin Amacı

Bu proje ile:

- OOP prensiplerini uygulama  
- JDBC ile veritabanı bağlantısı kurma  
- CRUD işlemleri geliştirme  
- SQL JOIN sorguları kullanma  
- Stok ve sipariş akışını yönetme  

becerilerinin pratiği yapılmıştır.

---

##  Özellikler

###  Kullanıcı Sistemi
- Kullanıcı girişi  
- Yeni kullanıcı kaydı  

###  Ürün Yönetimi
- Ürün ekleme, silme, güncelleme  
- Stok takibi  
- Kategori ve tedarikçi bilgisi  
- Aktif / Pasif durum kontrolü  

###  Sipariş Yönetimi
- Ürün arama  
- Müşteri seçimi  
- Sepete ürün ekleme  
- Toplam tutar hesaplama  
- Ödeme türü seçimi (Nakit / Kredi Kartı)  
- Sipariş oluşturma ve stok düşürme  

###  Sipariş Detayları
- Sipariş listeleme  
- Müşteri ve ürün bilgileriyle JOIN sorguları  
- Toplam tutar görüntüleme  

###  Yönetim Modülleri
- Müşteri Yönetimi  
- Çalışan Yönetimi  
- Tedarikçi Yönetimi  
- Kategori Yönetimi  

---

##  Yazılım Mimarisi

- OOP prensipleri (Encapsulation, Abstraction)  
- Class & Object yapısı  
- DAO (Data Access Object) yaklaşımı  
- Katmanlı yapı (UI – İş Mantığı – Veritabanı)  

---

##  Kullanılan Teknolojiler

- Java  
- Java Swing (GUI)  
- MySQL  
- JDBC  
- SQL  

---

##  Veritabanı Kurulumu

1. MySQL üzerinde yeni bir veritabanı oluşturun.  
2. `sql.script.sql` dosyasını çalıştırın.  
3. `Baglanti.java` dosyasındaki bağlantı ayarlarını kendi MySQL bilgilerinize göre düzenleyin.

---

##  Ekran Görüntüleri

Uygulama ekran görüntülerini görmek için aşağıdaki bağlantılara tıklayabilirsiniz:

-  Giriş Ekranı → [Görüntüle](Screenshots/giris.png)
-  Ana Menü → [Görüntüle](Screenshots/anamenu.png)
-  Ürün Yönetimi → [Görüntüle](Screenshots/urunYonetimi.png)
-  Sipariş İşlemleri → [Görüntüle](Screenshots/siparis.png)
-  Sipariş Detayları → [Görüntüle](Screenshots/siparisDetaylari.png)
