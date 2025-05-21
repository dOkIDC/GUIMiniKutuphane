package uygulama;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
    public class Kitap {

    private  int kitapID;
    private String baslik;
    private String yazar;
    private int stokMiktari;
    private String yayinTarihi;
    private String ISBN; 

    public  Kitap(int kitapID,String baslik,String yazar,int stokMiktari,String yayinTarihi,String ISBN){
        this.kitapID = kitapID;
        this.baslik = baslik;
        this.yazar = yazar;
        this.stokMiktari=stokMiktari;
        this.yayinTarihi = yayinTarihi;
        this.ISBN=ISBN;
    }
    //Kitap sınıfının özelliklerini tanımladım.(boş)
    public Kitap() {
    
            this.kitapID = 0;
            this.baslik = "";
            this.yazar = "";
            this.stokMiktari = 0;
            this.yayinTarihi = "";
            this.ISBN = "";
        }
    //Bu sınıfın çalışıp çalışmadığını saptamak için yaptığım bir örnek  
    /*public class KitapTest {
        public static void main(String[] args) {
        
            Kitap kitap = new Kitap(1, "1984", "George Orwell", 1, "1949", "9789750718533");
            
            System.out.println("Kitap ID: " + kitap.kitapID);
            System.out.println("Başlık: " + kitap.baslik);
            System.out.println("Yazar: " + kitap.yazar);
            System.out.println("Stok Miktarı: " + kitap.stokMiktari);
            System.out.println("Yayın Tarihi: " + kitap.yayinTarihi);
            System.out.println("ISBN: " + kitap.ISBN);
        }
    }
    */
    //Burada sınıf özelliklerinin her birinin ekrana gelmesi için olan fonksiyonlar var.
    public int  getirkitapID(){
    return kitapID;
    }


    public String getirbaslik(){
        return baslik;
    }


    public String getiryazar(){
        return yazar;
    }


    public int getirstokMiktarı(){
        return stokMiktari;
    }


    public String getiryayinTarihi(){
        return yayinTarihi;
    }


    public String getirISBN(){
        return ISBN;
    }


    //Şimdi fonksiyonları kullanarak ekrana bir kitap özelliklerini yazdırmayı deneyeceğim.
    public class kitapGetir {
    public static void main(String [] args){
        Kitap kitap = new Kitap(2,"Marie Antoinette","Stefan Zweig",1,"1932","9789750734328" );
    // Kitap kitap = new Kitap(3,"Hayvan Çiftliği","George Orwell",2,"1945","9789750719387" );
    System.out.println("KitapID:" + kitap.getirkitapID());
    System.out.println("Kitap ismi: " + kitap.getirbaslik());
    System.out.println("Yazarı: " + kitap.getiryazar());
    System.out.println("Stokta bulunan kitap sayısı: "+ kitap.getirstokMiktarı());
    System.out.println("Kitap yayın tarihi:"+ kitap.getiryayinTarihi());
    System.out.println("Kitabın ISBN numarası:"+ kitap.getirISBN());
    }
    }

    //Burada kullanılacak olan fonksiyonlarla kitap sınıfı özellikleri arasında değiştirme yapılmasını sağlayacağım.

    public void kitapIDAyarla() {
        Scanner kId = new Scanner(System.in);
        System.out.print("Yeni Kitap ID: ");
        int yeniKitapID = kId.nextInt();
        this.kitapID = yeniKitapID;
        System.out.println("Kitap ID güncellendi: " + this.kitapID);
        kId.close();
    }


    public void kitapBaslikAyarla() {
        Scanner kBaslik = new Scanner(System.in);
        System.out.print("Yeni Kitap Başlığı: ");
        String yeniBaslik = kBaslik.nextLine();
        this.baslik = yeniBaslik;
        System.out.println("Kitap Başlığı güncellendi: " + this.baslik);
        kBaslik.close();
    }


    public void kitapYazarAyarla() {
        Scanner kYazar = new Scanner(System.in);
        System.out.print("Yeni Yazar: ");
        String yeniYazar = kYazar.nextLine();
        this.yazar = yeniYazar;
        System.out.println("Yazar güncellendi: " + this.yazar);
        kYazar.close();
    }


    public void kitapStokMiktariAyarla() {
        Scanner kStok = new Scanner(System.in);
        System.out.print("Yeni Stok Miktarı: ");
        int yeniStok = kStok.nextInt();
        this.stokMiktari = yeniStok;
        System.out.println("Stok Miktarı güncellendi: " + this.stokMiktari);
        kStok.close();
    }


    public void kitapYayinTarihiAyarla() {
        Scanner kYayin = new Scanner(System.in);
        System.out.print("Yeni Yayın Tarihi: ");
        String yeniYayin = kYayin.nextLine();
        this.yayinTarihi = yeniYayin;
        System.out.println("Yayın Tarihi güncellendi: " + this.yayinTarihi);
        kYayin.close();
    }


    public void kitapISBNAyarla() {
        Scanner kISBN = new Scanner(System.in);
        System.out.print("Yeni ISBN: ");
        String yeniISBN = kISBN.nextLine();
        this.ISBN = yeniISBN;
        System.out.println("ISBN güncellendi: " + this.ISBN);
        kISBN.close();
    }


    public String metneCevir() {
            return "Kitap ID: " + kitapID + ", Başlık: " + baslik + ", Yazar: " + yazar +
                ", Stok: " + stokMiktari + ", Yayın Tarihi: " + yayinTarihi + ", ISBN: " + ISBN;
        }


    public void dosyayaYaz() {
        try {
            FileWriter writer = new FileWriter("kitap.txt", true); 
            writer.write(metneCevir() + "\n"); // Her kitabı yeni satıra yaz
            writer.close();
            System.out.println("Kitap başarıyla dosyaya yazıldı.");
        } catch (IOException e) {
            System.out.println("Dosyaya yazma hatası: " + e.getMessage());
        }
        
    }
        // CSV'ye dönüştürme metodu
        public String csvFormatinaDonustur() {
            return kitapID + "," + baslik + "," + yazar + "," + stokMiktari + "," + yayinTarihi + "," + ISBN;
        }
        
        // CSV dosyasına yazma metodu
        public void csvDosyasinaYaz(String dosyaAdi) {
            File dosya = new File(dosyaAdi);
            boolean baslikYaz = !dosya.exists();
            
            try {
                FileWriter writer = new FileWriter(dosyaAdi, true); // true parametresi dosyaya ekleme yapmayı sağlar
                
                // Eğer dosya yeni oluşturuluyorsa başlık satırını yaz
                if (baslikYaz) {
                    writer.write("KitapID,Baslik,Yazar,StokMiktari,YayinTarihi,ISBN\n");
                }
                
                // Kitap bilgilerini CSV formatında yaz
                writer.write(csvFormatinaDonustur() + "\n");
                writer.close();
                System.out.println("Kitap bilgileri CSV dosyasına başarıyla yazıldı: " + dosyaAdi);
            } catch (IOException e) {
                System.out.println("Dosyaya yazma hatası: " + e.getMessage());
            }
        }
    public static List<Kitap> csvDosyasindanOku(String dosyaAdi) {
            List<Kitap> kitaplar = new ArrayList<>();
            
            try {
                BufferedReader reader = new BufferedReader(new FileReader(dosyaAdi));
                String satir;
                boolean ilkSatir = true;
                
                while ((satir = reader.readLine()) != null) {
                
                    if (ilkSatir) {
                        ilkSatir = false;
                        continue;
                    }
                    
                    String[] veriler = satir.split(",");
                    if (veriler.length == 6) {
                        Kitap kitap = new Kitap(
                            Integer.parseInt(veriler[0]),
                            veriler[1],
                            veriler[2],
                            Integer.parseInt(veriler[3]),
                            veriler[4],
                            veriler[5]
                        );
                        kitaplar.add(kitap);
                    }
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("Dosya okuma hatası: " + e.getMessage());
            }
            
            return kitaplar;
        }


    }






