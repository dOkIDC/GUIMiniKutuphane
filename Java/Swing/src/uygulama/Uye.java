package uygulama;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Uye {
    private int uyeID;
    private String ad;
    private String soyad;
    private String eposta;
    private String telefon;
    private String adres;

    // Parametreli constructor
    public Uye(int uyeID, String ad, String soyad, String eposta, String telefon, String adres) {
        this.uyeID = uyeID;
        this.ad = ad;
        this.soyad = soyad;
        this.eposta = eposta;
        this.telefon = telefon;
        this.adres = adres;
    }

    // Boş constructor
    public Uye() {
        this.uyeID = 0;
        this.ad = "";
        this.soyad = "";
        this.eposta = "";
        this.telefon = "";
        this.adres = "";
    }

    // Test sınıfı
    public static class UyeTest {
        public static void main(String[] args) {
            Uye uye = new Uye(1, "Egemen Erdem", "Gülnar", "uierdem1@gmail.com", "05000000000", "Elmadağ/Ankara");
            
            System.out.println("Üye ID: " + uye.uyeID);
            System.out.println("Ad: " + uye.ad);
            System.out.println("Soyad: " + uye.soyad);
            System.out.println("E-posta: " + uye.eposta);
            System.out.println("Telefon: " + uye.telefon);
            System.out.println("Adres: " + uye.adres);
        }
    }

    // Getter metotları
    public int getirUyeID() {
        return uyeID;
    }

    public String getirAd() {
        return ad;
    }

    public String getirSoyad() {
        return soyad;
    }

    public String getirEposta() {
        return eposta;
    }

    public String getirTelefon() {
        return telefon;
    }

    public String getirAdres() {
        return adres;
    }

    // Setter metotları
    public void uyeIDAyarla(int uyeID) {
        this.uyeID = uyeID;
    }

    public void adAyarla(String ad) {
        this.ad = ad;
    }

    public void soyadAyarla(String soyad) {
        this.soyad = soyad;
    }

    public void epostaAyarla(String eposta) {
        this.eposta = eposta;
    }

    public void telefonAyarla(String telefon) {
        this.telefon = telefon;
    }

    public void adresAyarla(String adres) {
        this.adres = adres;
    }

    // Scanner ile ayarlama metodları
    public void uyeIDAyarla() {
        Scanner uId = new Scanner(System.in);
        System.out.print("Yeni Üye ID: ");
        int yeniUyeID = uId.nextInt();
        this.uyeID = yeniUyeID;
        System.out.println("Üye ID güncellendi: " + this.uyeID);
        uId.close();
    }

    public void adAyarla() {
        Scanner uAd = new Scanner(System.in);
        System.out.print("Yeni Ad: ");
        String yeniAd = uAd.nextLine();
        this.ad = yeniAd;
        System.out.println("Ad güncellendi: " + this.ad);
        uAd.close();
    }

    public void soyadAyarla() {
        Scanner uSoyad = new Scanner(System.in);
        System.out.print("Yeni Soyad: ");
        String yeniSoyad = uSoyad.nextLine();
        this.soyad = yeniSoyad;
        System.out.println("Soyad güncellendi: " + this.soyad);
        uSoyad.close();
    }

    public void epostaAyarla() {
        Scanner uEposta = new Scanner(System.in);
        System.out.print("Yeni E-posta: ");
        String yeniEposta = uEposta.nextLine();
        this.eposta = yeniEposta;
        System.out.println("E-posta güncellendi: " + this.eposta);
        uEposta.close();
    }

    public void telefonAyarla() {
        Scanner uTelefon = new Scanner(System.in);
        System.out.print("Yeni Telefon: ");
        String yeniTelefon = uTelefon.nextLine();
        this.telefon = yeniTelefon;
        System.out.println("Telefon güncellendi: " + this.telefon);
        uTelefon.close();
    }

    public void adresAyarla() {
        Scanner uAdres = new Scanner(System.in);
        System.out.print("Yeni Adres: ");
        String yeniAdres = uAdres.nextLine();
        this.adres = yeniAdres;
        System.out.println("Adres güncellendi: " + this.adres);
        uAdres.close();
    }

    // Metne çevirme metodu
    public String metneCevir() {
        return "Üye ID: " + uyeID + ", Ad: " + ad + ", Soyad: " + soyad +
               ", E-posta: " + eposta + ", Telefon: " + telefon + ", Adres: " + adres;
    }

    // Dosyaya yazma metodu
    public void dosyayaYaz() {
        try {
            FileWriter writer = new FileWriter("uyeler.txt", true); 
            writer.write(metneCevir() + "\n"); // Her üyeyi yeni satıra yaz
            writer.close();
            System.out.println("Üye başarıyla dosyaya yazıldı.");
        } catch (IOException e) {
            System.out.println("Dosyaya yazma hatası: " + e.getMessage());
        }
    }

    // CSV formatına dönüştürme metodu
    public String csvFormatinaDonustur() {
        return uyeID + "," + ad + "," + soyad + "," + eposta + "," + telefon + "," + adres;
    }

    // CSV dosyasına yazma metodu
    public void csvDosyasinaYaz(String dosyaAdi) {
        File dosya = new File(dosyaAdi);
        boolean baslikYaz = !dosya.exists();
        
        try {
            FileWriter writer = new FileWriter(dosyaAdi, true); // true parametresi dosyaya ekleme yapmayı sağlar
            
            // Eğer dosya yeni oluşturuluyorsa başlık satırını yaz
            if (baslikYaz) {
                writer.write("UyeID,Ad,Soyad,Eposta,Telefon,Adres\n");
            }
            
            // Üye bilgilerini CSV formatında yaz
            writer.write(csvFormatinaDonustur() + "\n");
            writer.close();
            System.out.println("Üye bilgileri CSV dosyasına başarıyla yazıldı: " + dosyaAdi);
        } catch (IOException e) {
            System.out.println("Dosyaya yazma hatası: " + e.getMessage());
        }
    }

    // CSV dosyasından okuma metodu (statik metot)
    public static List<Uye> csvDosyasindanOku(String dosyaAdi) {
        List<Uye> uyeler = new ArrayList<>();
        
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
                    Uye uye = new Uye(
                        Integer.parseInt(veriler[0]),
                        veriler[1],
                        veriler[2],
                        veriler[3],
                        veriler[4],
                        veriler[5]
                    );
                    uyeler.add(uye);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Dosya okuma hatası: " + e.getMessage());
        }
        
        return uyeler;
    }
}