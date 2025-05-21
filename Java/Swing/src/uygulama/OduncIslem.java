package uygulama;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OduncIslem {
    private int islemId;
    private int kitapId;
    private int uyeId;
    private String oduncTarihi;
    private String iadeTarihi;
    private boolean iadeEdildi;

    // Parametreli constructor
    public OduncIslem(int islemId, int kitapId, int uyeId, String oduncTarihi, String iadeTarihi, boolean iadeEdildi) {
        this.islemId = islemId;
        this.kitapId = kitapId;
        this.uyeId = uyeId;
        this.oduncTarihi = oduncTarihi;
        this.iadeTarihi = iadeTarihi;
        this.iadeEdildi = iadeEdildi;
    }

    // Boş constructor
    public OduncIslem() {
        this.islemId = 0;
        this.kitapId = 0;
        this.uyeId = 0;
        this.oduncTarihi = "";
        this.iadeTarihi = "";
        this.iadeEdildi = false;
    }

    // Test sınıfı
    public static class OduncIslemTest {
        public static void main(String[] args) {
            OduncIslem islem = new OduncIslem(1, 101, 201, "15.05.2025", "22.05.2025", false);
            
            System.out.println("İşlem ID: " + islem.islemId);
            System.out.println("Kitap ID: " + islem.kitapId);
            System.out.println("Üye ID: " + islem.uyeId);
            System.out.println("Ödünç Tarihi: " + islem.oduncTarihi);
            System.out.println("İade Tarihi: " + islem.iadeTarihi);
            System.out.println("İade Edildi: " + islem.iadeEdildi);
        }
    }

    // Getter metotları
    public int islemIdGetir() {
        return islemId;
    }

    public int kitapIdGetir() {
        return kitapId;
    }

    public int uyeIdGetir() {
        return uyeId;
    }

    public String oduncTarihiGetir() {
        return oduncTarihi;
    }

    public String iadeTarihiGetir() {
        return iadeTarihi;
    }

    public boolean iadeEdildiMi() {
        return iadeEdildi;
    }

    // Setter metotları
    public void islemIdAyarla(int islemId) {
        this.islemId = islemId;
    }

    public void kitapIdAyarla(int kitapId) {
        this.kitapId = kitapId;
    }

    public void uyeIdAyarla(int uyeId) {
        this.uyeId = uyeId;
    }

    public void oduncTarihiAyarla(String oduncTarihi) {
        this.oduncTarihi = oduncTarihi;
    }

    public void iadeTarihiAyarla(String iadeTarihi) {
        this.iadeTarihi = iadeTarihi;
    }

    public void iadeEdildiAyarla(boolean iadeEdildi) {
        this.iadeEdildi = iadeEdildi;
    }

    // Scanner ile ayarlama metodları
    public void islemIdAyarla() {
        Scanner scIslemId = new Scanner(System.in);
        System.out.print("Yeni İşlem ID: ");
        int yeniIslemId = scIslemId.nextInt();
        this.islemId = yeniIslemId;
        System.out.println("İşlem ID güncellendi: " + this.islemId);
        scIslemId.close();
    }

    public void kitapIdAyarla() {
        Scanner scKitapId = new Scanner(System.in);
        System.out.print("Yeni Kitap ID: ");
        int yeniKitapId = scKitapId.nextInt();
        this.kitapId = yeniKitapId;
        System.out.println("Kitap ID güncellendi: " + this.kitapId);
        scKitapId.close();
    }

    public void uyeIdAyarla() {
        Scanner scUyeId = new Scanner(System.in);
        System.out.print("Yeni Üye ID: ");
        int yeniUyeId = scUyeId.nextInt();
        this.uyeId = yeniUyeId;
        System.out.println("Üye ID güncellendi: " + this.uyeId);
        scUyeId.close();
    }

    public void oduncTarihiAyarla() {
        Scanner scOduncTarihi = new Scanner(System.in);
        System.out.print("Yeni Ödünç Tarihi: ");
        String yeniOduncTarihi = scOduncTarihi.nextLine();
        this.oduncTarihi = yeniOduncTarihi;
        System.out.println("Ödünç Tarihi güncellendi: " + this.oduncTarihi);
        scOduncTarihi.close();
    }

    public void iadeTarihiAyarla() {
        Scanner scIadeTarihi = new Scanner(System.in);
        System.out.print("Yeni İade Tarihi: ");
        String yeniIadeTarihi = scIadeTarihi.nextLine();
        this.iadeTarihi = yeniIadeTarihi;
        System.out.println("İade Tarihi güncellendi: " + this.iadeTarihi);
        scIadeTarihi.close();
    }

    public void iadeEdildiAyarla() {
        Scanner scIadeEdildi = new Scanner(System.in);
        System.out.print("İade Edildi mi? (true/false): ");
        boolean yeniIadeEdildi = scIadeEdildi.nextBoolean();
        this.iadeEdildi = yeniIadeEdildi;
        System.out.println("İade Durumu güncellendi: " + this.iadeEdildi);
        scIadeEdildi.close();
    }

    // Metne çevirme metodu
    public String metneCevir() {
        return "İşlem ID: " + islemId + ", Kitap ID: " + kitapId + ", Üye ID: " + uyeId +
               ", Ödünç Tarihi: " + oduncTarihi + ", İade Tarihi: " + iadeTarihi + ", İade Edildi: " + iadeEdildi;
    }

    // Dosyaya yazma metodu
    public void dosyayaYaz() {
        try {
            FileWriter writer = new FileWriter("odunc_islemler.txt", true); 
            writer.write(metneCevir() + "\n"); // Her işlemi yeni satıra yaz
            writer.close();
            System.out.println("Ödünç işlemi başarıyla dosyaya yazıldı.");
        } catch (IOException e) {
            System.out.println("Dosyaya yazma hatası: " + e.getMessage());
        }
    }

    // CSV formatına dönüştürme metodu
    public String csvFormatinaDonustur() {
        return islemId + "," + kitapId + "," + uyeId + "," + oduncTarihi + "," + iadeTarihi + "," + iadeEdildi;
    }

    // CSV dosyasına yazma metodu
    public void csvDosyasinaYaz(String dosyaAdi) {
        File dosya = new File(dosyaAdi);
        boolean baslikYaz = !dosya.exists();
        
        try {
            FileWriter writer = new FileWriter(dosyaAdi, true); // true parametresi dosyaya ekleme yapmayı sağlar
            
            // Eğer dosya yeni oluşturuluyorsa başlık satırını yaz
            if (baslikYaz) {
                writer.write("IslemID,KitapID,UyeID,OduncTarihi,IadeTarihi,IadeEdildi\n");
            }
            
            // İşlem bilgilerini CSV formatında yaz
            writer.write(csvFormatinaDonustur() + "\n");
            writer.close();
            System.out.println("Ödünç işlemi bilgileri CSV dosyasına başarıyla yazıldı: " + dosyaAdi);
        } catch (IOException e) {
            System.out.println("Dosyaya yazma hatası: " + e.getMessage());
        }
    }

    // CSV dosyasından okuma metodu (statik metot)
    public static List<OduncIslem> csvDosyasindanOku(String dosyaAdi) {
        List<OduncIslem> islemler = new ArrayList<>();
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dosyaAdi));
            String satir;
            boolean ilkSatir = true;
            
            while ((satir = reader.readLine()) != null) {
                // İlk satır başlık satırı olduğu için atla
                if (ilkSatir) {
                    ilkSatir = false;
                    continue;
                }
                
                String[] veriler = satir.split(",");
                if (veriler.length == 6) {
                    OduncIslem islem = new OduncIslem(
                        Integer.parseInt(veriler[0]),
                        Integer.parseInt(veriler[1]),
                        Integer.parseInt(veriler[2]),
                        veriler[3],
                        veriler[4],
                        Boolean.parseBoolean(veriler[5])
                    );
                    islemler.add(islem);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Dosya okuma hatası: " + e.getMessage());
        }
        
        return islemler;
    }
}