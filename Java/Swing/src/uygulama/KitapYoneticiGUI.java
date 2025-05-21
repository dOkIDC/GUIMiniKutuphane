package uygulama;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KitapYoneticiGUI extends JFrame {
    
    private JPanel anaPanel;
    private JTable kitapTablosu;
    private DefaultTableModel tableModel;
    private JButton btnYeniKitap;
    private JButton btnKitaplariGoster;
    private JButton btnOrnekKitapEkle;
    private JButton btnKitapAra;
    private JButton btnKitapSil;
    private JButton btnKitapGuncelle;
    private JButton btnCikis;
    
    private String csvDosyaAdi = "kitaplar.csv";
    
    public KitapYoneticiGUI() {
        // Frame ayarları
        setTitle("Kitap Yönetimi");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Ana panel oluşturma
        anaPanel = new JPanel(new BorderLayout());
        
        // Butonlar paneli
        JPanel butonlarPanel = new JPanel(new GridLayout(7, 1, 5, 5));
        butonlarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btnYeniKitap = new JButton("1. Yeni Kitap Ekle");
        btnKitaplariGoster = new JButton("2. Tüm Kitapları Göster");
        btnOrnekKitapEkle = new JButton("3. Örnek Kitaplar Ekle");
        btnKitapAra = new JButton("4. Kitap Ara");
        btnKitapSil = new JButton("5. Kitap Sil");
        btnKitapGuncelle = new JButton("6. Kitap Güncelle");
        btnCikis = new JButton("7. Çıkış");
        
        // Butonlara işlevsellik ekleme
        btnYeniKitap.addActionListener(e -> yeniKitapEkle());
        btnKitaplariGoster.addActionListener(e -> kitaplariGoster());
        btnOrnekKitapEkle.addActionListener(e -> ornekKitaplarEkle());
        btnKitapAra.addActionListener(e -> kitapAra());
        btnKitapSil.addActionListener(e -> kitapSil());
        btnKitapGuncelle.addActionListener(e -> kitapGuncelle());
        btnCikis.addActionListener(e -> dispose());
        
        // Butonları panele ekleme
        butonlarPanel.add(btnYeniKitap);
        butonlarPanel.add(btnKitaplariGoster);
        butonlarPanel.add(btnOrnekKitapEkle);
        butonlarPanel.add(btnKitapAra);
        butonlarPanel.add(btnKitapSil);
        butonlarPanel.add(btnKitapGuncelle);
        butonlarPanel.add(btnCikis);
        
        // Tablo oluşturma
        String[] kolonlar = {"ID", "Başlık", "Yazar", "Stok", "Yayın Tarihi", "ISBN"};
        tableModel = new DefaultTableModel(kolonlar, 0);
        kitapTablosu = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(kitapTablosu);
        
        // Bileşenleri ana panele yerleştirme
        anaPanel.add(butonlarPanel, BorderLayout.WEST);
        anaPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Ana paneli frame'e ekleme
        add(anaPanel);
    }
    
    // Yeni kitap ekleme işlemi
    private void yeniKitapEkle() {
        JTextField tfKitapID = new JTextField(10);
        JTextField tfBaslik = new JTextField(20);
        JTextField tfYazar = new JTextField(20);
        JTextField tfStokMiktari = new JTextField(10);
        JTextField tfYayinTarihi = new JTextField(10);
        JTextField tfISBN = new JTextField(15);
        
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.add(new JLabel("Kitap ID:"));
        panel.add(tfKitapID);
        panel.add(new JLabel("Başlık:"));
        panel.add(tfBaslik);
        panel.add(new JLabel("Yazar:"));
        panel.add(tfYazar);
        panel.add(new JLabel("Stok Miktarı:"));
        panel.add(tfStokMiktari);
        panel.add(new JLabel("Yayın Tarihi:"));
        panel.add(tfYayinTarihi);
        panel.add(new JLabel("ISBN:"));
        panel.add(tfISBN);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Yeni Kitap Ekle", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                
        if (result == JOptionPane.OK_OPTION) {
            try {
                int kitapID = Integer.parseInt(tfKitapID.getText());
                String baslik = tfBaslik.getText();
                String yazar = tfYazar.getText();
                int stokMiktari = Integer.parseInt(tfStokMiktari.getText());
                String yayinTarihi = tfYayinTarihi.getText();
                String isbn = tfISBN.getText();
                
                Kitap kitap = new Kitap(kitapID, baslik, yazar, stokMiktari, yayinTarihi, isbn);
                kitap.csvDosyasinaYaz(csvDosyaAdi);
                
                JOptionPane.showMessageDialog(this, "Kitap başarıyla eklendi!");
                kitaplariGoster(); // Tabloyu güncelle
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Hata: Kitap ID ve stok miktarı sayı olmalıdır!", 
                        "Giriş Hatası", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Kitap eklenirken hata oluştu: " + e.getMessage(), 
                        "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Tüm kitapları listele
    private void kitaplariGoster() {
        // Tabloyu temizle
        tableModel.setRowCount(0);
        
        List<Kitap> kitaplar = Kitap.csvDosyasindanOku(csvDosyaAdi);
        
        if (kitaplar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Henüz kitap bulunmamaktadır!", 
                    "Bilgi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Kitapları tabloya ekle
        for (Kitap kitap : kitaplar) {
            Object[] row = {
                kitap.getirkitapID(),
                kitap.getirbaslik(),
                kitap.getiryazar(),
                kitap.getirstokMiktarı(),
                kitap.getiryayinTarihi(),
                kitap.getirISBN()
            };
            tableModel.addRow(row);
        }
    }
    
    // Örnek kitapları ekle
    private void ornekKitaplarEkle() {
        Kitap[] ornekKitaplar = {
            new Kitap(1, "1984", "George Orwell", 10, "1949", "9789750718533"),
            new Kitap(2, "Marie Antoinette", "Stefan Zweig", 5, "1932", "9789750734328"),
            new Kitap(3, "Hayvan Çiftliği", "George Orwell", 8, "1945", "9789750719387"),
            new Kitap(4, "Suç ve Ceza", "Fyodor Dostoyevski", 12, "1866", "9789751026989"),
            new Kitap(5, "Simyacı", "Paulo Coelho", 15, "1988", "9789750726439")
        };
        
        for (Kitap kitap : ornekKitaplar) {
            kitap.csvDosyasinaYaz(csvDosyaAdi);
        }
        
        JOptionPane.showMessageDialog(this, "Örnek kitaplar başarıyla eklendi!", 
                "Bilgi", JOptionPane.INFORMATION_MESSAGE);
        
        kitaplariGoster(); // Tabloyu güncelle
    }
    
    // Kitap ara
    private void kitapAra() {
        String arananKelime = JOptionPane.showInputDialog(this, 
                "Aramak istediğiniz kitap adını veya yazarını girin:", 
                "Kitap Ara", JOptionPane.QUESTION_MESSAGE);
                
        if (arananKelime == null || arananKelime.isEmpty()) {
            return;
        }
        
        arananKelime = arananKelime.toLowerCase();
        List<Kitap> kitaplar = Kitap.csvDosyasindanOku(csvDosyaAdi);
        List<Kitap> bulunanKitaplar = new ArrayList<>();
        
        for (Kitap kitap : kitaplar) {
            if (kitap.getirbaslik().toLowerCase().contains(arananKelime) || 
                kitap.getiryazar().toLowerCase().contains(arananKelime)) {
                bulunanKitaplar.add(kitap);
            }
        }
        
        // Tabloyu temizle
        tableModel.setRowCount(0);
        
        if (bulunanKitaplar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aramanıza uygun kitap bulunamadı!", 
                    "Bilgi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Bulunan kitapları tabloya ekle
        for (Kitap kitap : bulunanKitaplar) {
            Object[] row = {
                kitap.getirkitapID(),
                kitap.getirbaslik(),
                kitap.getiryazar(),
                kitap.getirstokMiktarı(),
                kitap.getiryayinTarihi(),
                kitap.getirISBN()
            };
            tableModel.addRow(row);
        }
        
        JOptionPane.showMessageDialog(this, "Aranan kelime ile ilgili " + bulunanKitaplar.size() + " kitap bulundu.", 
                "Arama Sonucu", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Kitap silme
    private void kitapSil() {
        // Önce tüm kitapları göster
        kitaplariGoster();
        
        String idStr = JOptionPane.showInputDialog(this, 
                "Silmek istediğiniz kitabın ID'sini girin:", 
                "Kitap Sil", JOptionPane.QUESTION_MESSAGE);
                
        if (idStr == null || idStr.isEmpty()) {
            return;
        }
        
        try {
            int silinecekID = Integer.parseInt(idStr);
            List<Kitap> kitaplar = Kitap.csvDosyasindanOku(csvDosyaAdi);
            
            // Silinecek kitabı bul
            boolean kitapBulundu = false;
            String silinecekKitapBilgisi = "";
            List<Kitap> kalanKitaplar = new ArrayList<>();
            
            for (Kitap kitap : kitaplar) {
                if (kitap.getirkitapID() == silinecekID) {
                    kitapBulundu = true;
                    silinecekKitapBilgisi = kitap.metneCevir();
                } else {
                    kalanKitaplar.add(kitap);
                }
            }
            
            if (!kitapBulundu) {
                JOptionPane.showMessageDialog(this, "Belirtilen ID'ye sahip kitap bulunamadı!", 
                        "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Silme onayı al
            int onay = JOptionPane.showConfirmDialog(this, 
                    "Aşağıdaki kitabı silmek istediğinizden emin misiniz?\n\n" + silinecekKitapBilgisi, 
                    "Silme Onayı", JOptionPane.YES_NO_OPTION);
                    
            if (onay != JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "Silme işlemi iptal edildi.", 
                        "Bilgi", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Dosyayı baştan oluştur
            try {
                FileWriter writer = new FileWriter(csvDosyaAdi, false); // false parametresi dosyayı sıfırlar
                // Başlık satırını yaz
                writer.write("KitapID,Baslik,Yazar,StokMiktari,YayinTarihi,ISBN\n");
                writer.close();
                
                // Kalan kitapları tekrar yaz
                for (Kitap kitap : kalanKitaplar) {
                    kitap.csvDosyasinaYaz(csvDosyaAdi);
                }
                
                JOptionPane.showMessageDialog(this, "Kitap başarıyla silindi!", 
                        "Bilgi", JOptionPane.INFORMATION_MESSAGE);
                kitaplariGoster(); // Tabloyu güncelle
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Dosya yazma hatası: " + e.getMessage(), 
                        "Hata", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Geçersiz ID! Lütfen sayı girin.", 
                    "Hata", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Kitap silinirken hata oluştu: " + e.getMessage(), 
                    "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Kitap güncelleme
    private void kitapGuncelle() {
        // Önce tüm kitapları göster
        kitaplariGoster();
        
        String idStr = JOptionPane.showInputDialog(this, 
                "Güncellemek istediğiniz kitabın ID'sini girin:", 
                "Kitap Güncelle", JOptionPane.QUESTION_MESSAGE);
                
        if (idStr == null || idStr.isEmpty()) {
            return;
        }
        
        try {
            int guncellenecekID = Integer.parseInt(idStr);
            List<Kitap> kitaplar = Kitap.csvDosyasindanOku(csvDosyaAdi);
            
            // Güncellenecek kitabı bul
            boolean kitapBulundu = false;
            Kitap guncellenecekKitap = null;
            List<Kitap> kalanKitaplar = new ArrayList<>();
            
            for (Kitap kitap : kitaplar) {
                if (kitap.getirkitapID() == guncellenecekID) {
                    kitapBulundu = true;
                    guncellenecekKitap = kitap;
                } else {
                    kalanKitaplar.add(kitap);
                }
            }
            
            if (!kitapBulundu) {
                JOptionPane.showMessageDialog(this, "Belirtilen ID'ye sahip kitap bulunamadı!", 
                        "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Güncelleme formu oluştur
            JTextField tfBaslik = new JTextField(guncellenecekKitap.getirbaslik(), 20);
            JTextField tfYazar = new JTextField(guncellenecekKitap.getiryazar(), 20);
            JTextField tfStokMiktari = new JTextField(String.valueOf(guncellenecekKitap.getirstokMiktarı()), 10);
            JTextField tfYayinTarihi = new JTextField(guncellenecekKitap.getiryayinTarihi(), 10);
            JTextField tfISBN = new JTextField(guncellenecekKitap.getirISBN(), 15);
            
            JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
            panel.add(new JLabel("Başlık:"));
            panel.add(tfBaslik);
            panel.add(new JLabel("Yazar:"));
            panel.add(tfYazar);
            panel.add(new JLabel("Stok Miktarı:"));
            panel.add(tfStokMiktari);
            panel.add(new JLabel("Yayın Tarihi:"));
            panel.add(tfYayinTarihi);
            panel.add(new JLabel("ISBN:"));
            panel.add(tfISBN);
            
            int result = JOptionPane.showConfirmDialog(this, panel, "Kitap Bilgilerini Güncelle", 
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    
            if (result != JOptionPane.OK_OPTION) {
                JOptionPane.showMessageDialog(this, "Güncelleme işlemi iptal edildi.", 
                        "Bilgi", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            // Yeni değerleri al
            String yeniBaslik = tfBaslik.getText();
            String yeniYazar = tfYazar.getText();
            int yeniStok = Integer.parseInt(tfStokMiktari.getText());
            String yeniYayinTarihi = tfYayinTarihi.getText();
            String yeniISBN = tfISBN.getText();
            
            // Güncellenmiş kitap nesnesi oluştur
            Kitap yeniKitap = new Kitap(
                guncellenecekID,
                yeniBaslik,
                yeniYazar,
                yeniStok,
                yeniYayinTarihi,
                yeniISBN
            );
            
            try {
                FileWriter writer = new FileWriter(csvDosyaAdi, false); 
                // Başlık satırını yaz
                writer.write("KitapID,Baslik,Yazar,StokMiktari,YayinTarihi,ISBN\n");
                writer.close();
                
                // Kalan kitapları tekrar yaz
                for (Kitap kitap : kalanKitaplar) {
                    kitap.csvDosyasinaYaz(csvDosyaAdi);
                }
                
        
                yeniKitap.csvDosyasinaYaz(csvDosyaAdi);
                
                JOptionPane.showMessageDialog(this, "Kitap başarıyla güncellendi!", 
                        "Bilgi", JOptionPane.INFORMATION_MESSAGE);
                kitaplariGoster(); 
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Dosya yazma hatası: " + e.getMessage(), 
                        "Hata", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Geçersiz değer! Lütfen sayısal alanlar için sayı girin.", 
                    "Hata", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Kitap güncellenirken hata oluştu: " + e.getMessage(), 
                    "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
}