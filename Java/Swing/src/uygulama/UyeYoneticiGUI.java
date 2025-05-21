package uygulama;



import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UyeYoneticiGUI extends JFrame {

    private JPanel anaPanel;
    private JTable uyeTablosu;
    private DefaultTableModel tableModel;
    private JButton btnYeniUye;
    private JButton btnUyeleriGoster;
    private JButton btnOrnekUyeEkle;
    private JButton btnUyeAra;
    private JButton btnUyeSil;
    private JButton btnUyeGuncelle;
    private JButton btnCikis;

    private String csvDosyaAdi = "uyeler.csv";

    public UyeYoneticiGUI() {
        setTitle("Üye Yönetimi");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        anaPanel = new JPanel(new BorderLayout());

        JPanel butonlarPanel = new JPanel(new GridLayout(7, 1, 5, 5));
        butonlarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        btnYeniUye = new JButton("1. Yeni Üye Ekle");
        btnUyeleriGoster = new JButton("2. Tüm Üyeleri Göster");
        btnOrnekUyeEkle = new JButton("3. Örnek Üyeler Ekle");
        btnUyeAra = new JButton("4. Üye Ara");
        btnUyeSil = new JButton("5. Üye Sil");
        btnUyeGuncelle = new JButton("6. Üye Güncelle");
        btnCikis = new JButton("7. Çıkış");

        btnYeniUye.addActionListener(e -> yeniUyeEkle());
        btnUyeleriGoster.addActionListener(e -> uyeleriGoster());
        btnOrnekUyeEkle.addActionListener(e -> ornekUyelerEkle());
        btnUyeAra.addActionListener(e -> uyeAra());
        btnUyeSil.addActionListener(e -> uyeSil());
        btnUyeGuncelle.addActionListener(e -> uyeGuncelle());
        btnCikis.addActionListener(e -> dispose());

        butonlarPanel.add(btnYeniUye);
        butonlarPanel.add(btnUyeleriGoster);
        butonlarPanel.add(btnOrnekUyeEkle);
        butonlarPanel.add(btnUyeAra);
        butonlarPanel.add(btnUyeSil);
        butonlarPanel.add(btnUyeGuncelle);
        butonlarPanel.add(btnCikis);

        String[] kolonlar = {"ID", "Ad", "Soyad", "E-posta", "Telefon", "Adres"};
        tableModel = new DefaultTableModel(kolonlar, 0);
        uyeTablosu = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(uyeTablosu);

        anaPanel.add(butonlarPanel, BorderLayout.WEST);
        anaPanel.add(scrollPane, BorderLayout.CENTER);

        add(anaPanel);
    }

    private void yeniUyeEkle() {
        JTextField tfUyeID = new JTextField(10);
        JTextField tfAd = new JTextField(20);
        JTextField tfSoyad = new JTextField(20);
        JTextField tfEposta = new JTextField(25);
        JTextField tfTelefon = new JTextField(15);
        JTextField tfAdres = new JTextField(30);

        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.add(new JLabel("Üye ID:"));
        panel.add(tfUyeID);
        panel.add(new JLabel("Ad:"));
        panel.add(tfAd);
        panel.add(new JLabel("Soyad:"));
        panel.add(tfSoyad);
        panel.add(new JLabel("E-posta:"));
        panel.add(tfEposta);
        panel.add(new JLabel("Telefon:"));
        panel.add(tfTelefon);
        panel.add(new JLabel("Adres:"));
        panel.add(tfAdres);

        int result = JOptionPane.showConfirmDialog(this, panel, "Yeni Üye Ekle",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int uyeID = Integer.parseInt(tfUyeID.getText());
                String ad = tfAd.getText();
                String soyad = tfSoyad.getText();
                String eposta = tfEposta.getText();
                String telefon = tfTelefon.getText();
                String adres = tfAdres.getText();

                Uye uye = new Uye(uyeID, ad, soyad, eposta, telefon, adres);
                uye.csvDosyasinaYaz(csvDosyaAdi);

                JOptionPane.showMessageDialog(this, "Üye başarıyla eklendi!");
                uyeleriGoster();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Hata: Üye ID sayı olmalıdır!",
                        "Giriş Hatası", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Üye eklenirken hata oluştu: " + e.getMessage(),
                        "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void uyeleriGoster() {
        tableModel.setRowCount(0);

        List<Uye> uyeler = Uye.csvDosyasindanOku(csvDosyaAdi);

        if (uyeler.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Henüz üye bulunmamaktadır!",
                    "Bilgi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Uye uye : uyeler) {
            Object[] row = {
                    uye.getirUyeID(),
                    uye.getirAd(),
                    uye.getirSoyad(),
                    uye.getirEposta(),
                    uye.getirTelefon(),
                    uye.getirAdres()
            };
            tableModel.addRow(row);
        }
    }

    private void ornekUyelerEkle() {
        Uye[] ornekUyeler = {
                new Uye(1, "Ahmet", "Yılmaz", "ahmet@ornek.com", "05551234567", "Ankara/Çankaya"),
                new Uye(2, "Mehmet", "Öztürk", "mehmet@ornek.com", "05553456789", "İstanbul/Kadıköy"),
                new Uye(3, "Ayşe", "Kaya", "ayse@ornek.com", "05557890123", "İzmir/Karşıyaka"),
                new Uye(4, "Fatma", "Demir", "fatma@ornek.com", "05551230987", "Bursa/Nilüfer"),
                new Uye(5, "Ali", "Yıldız", "ali@ornek.com", "05559876543", "Antalya/Muratpaşa")
        };

        for (Uye uye : ornekUyeler) {
            uye.csvDosyasinaYaz(csvDosyaAdi);
        }

        JOptionPane.showMessageDialog(this, "Örnek üyeler başarıyla eklendi!",
                "Bilgi", JOptionPane.INFORMATION_MESSAGE);

        uyeleriGoster();
    }

    private void uyeAra() {
        String arananKelime = JOptionPane.showInputDialog(this,
                "Aramak istediğiniz üyenin adını veya soyadını girin:",
                "Üye Ara", JOptionPane.QUESTION_MESSAGE);

        if (arananKelime == null || arananKelime.isEmpty()) {
            return;
        }

        arananKelime = arananKelime.toLowerCase();
        List<Uye> uyeler = Uye.csvDosyasindanOku(csvDosyaAdi);
        List<Uye> bulunanUyeler = new ArrayList<>();

        for (Uye uye : uyeler) {
            if (uye.getirAd().toLowerCase().contains(arananKelime) ||
                    uye.getirSoyad().toLowerCase().contains(arananKelime)) {
                bulunanUyeler.add(uye);
            }
        }

        tableModel.setRowCount(0);

        if (bulunanUyeler.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aramanıza uygun üye bulunamadı!",
                    "Bilgi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Uye uye : bulunanUyeler) {
            Object[] row = {
                    uye.getirUyeID(),
                    uye.getirAd(),
                    uye.getirSoyad(),
                    uye.getirEposta(),
                    uye.getirTelefon(),
                    uye.getirAdres()
            };
            tableModel.addRow(row);
        }

        JOptionPane.showMessageDialog(this, "Aranan kelime ile ilgili " + bulunanUyeler.size() + " üye bulundu.",
                "Arama Sonucu", JOptionPane.INFORMATION_MESSAGE);
    }

    private void uyeSil() {
        uyeleriGoster();

        String idStr = JOptionPane.showInputDialog(this,
                "Silmek istediğiniz üyenin ID'sini girin:",
                "Üye Sil", JOptionPane.QUESTION_MESSAGE);

        if (idStr == null || idStr.isEmpty()) {
            return;
        }

        try {
            int silinecekID = Integer.parseInt(idStr);
            List<Uye> uyeler = Uye.csvDosyasindanOku(csvDosyaAdi);

            boolean uyeBulundu = false;
            String silinecekUyeBilgisi = "";
            List<Uye> kalanUyeler = new ArrayList<>();

            for (Uye uye : uyeler) {
                if (uye.getirUyeID() == silinecekID) {
                    uyeBulundu = true;
                    silinecekUyeBilgisi = uye.metneCevir();
                } else {
                    kalanUyeler.add(uye);
                }
            }

            if (!uyeBulundu) {
                JOptionPane.showMessageDialog(this, "Belirtilen ID'ye sahip üye bulunamadı!",
                        "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int onay = JOptionPane.showConfirmDialog(this,
                    "Aşağıdaki üye silinecek:\n\n" + silinecekUyeBilgisi,
                    "Silme Onayı", JOptionPane.YES_NO_OPTION);

            if (onay != JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(this, "Silme işlemi iptal edildi.",
                        "Bilgi", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            try {
                FileWriter writer = new FileWriter(csvDosyaAdi, false);
                writer.write("UyeID,Ad,Soyad,Eposta,Telefon,Adres\n");
                writer.close();

                for (Uye uye : kalanUyeler) {
                    uye.csvDosyasinaYaz(csvDosyaAdi);
                }

                JOptionPane.showMessageDialog(this, "Üye başarıyla silindi!",
                        "Bilgi", JOptionPane.INFORMATION_MESSAGE);
                uyeleriGoster();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Dosya yazma hatası: " + e.getMessage(),
                        "Hata", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Geçersiz ID! Lütfen sayı girin.",
                    "Hata", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Üye silinirken hata oluştu: " + e.getMessage(),
                    "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void uyeGuncelle() {
        uyeleriGoster();

        String idStr = JOptionPane.showInputDialog(this,
                "Güncellemek istediğiniz üyenin ID'sini girin:",
                "Üye Güncelle", JOptionPane.QUESTION_MESSAGE);

        if (idStr == null || idStr.isEmpty()) {
            return;
        }

        try {
            int guncellenecekID = Integer.parseInt(idStr);
            List<Uye> uyeler = Uye.csvDosyasindanOku(csvDosyaAdi);

            boolean uyeBulundu = false;
            Uye guncellenecekUye = null;
            List<Uye> kalanUyeler = new ArrayList<>();

            for (Uye uye : uyeler) {
                if (uye.getirUyeID() == guncellenecekID) {
                    uyeBulundu = true;
                    guncellenecekUye = uye;
                } else {
                    kalanUyeler.add(uye);
                }
            }

            if (!uyeBulundu) {
                JOptionPane.showMessageDialog(this, "Belirtilen ID'ye sahip üye bulunamadı!",
                        "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JTextField tfAd = new JTextField(guncellenecekUye.getirAd(), 20);
            JTextField tfSoyad = new JTextField(guncellenecekUye.getirSoyad(), 20);
            JTextField tfEposta = new JTextField(guncellenecekUye.getirEposta(), 25);
            JTextField tfTelefon = new JTextField(guncellenecekUye.getirTelefon(), 15);
            JTextField tfAdres = new JTextField(guncellenecekUye.getirAdres(), 30);

            JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
            panel.add(new JLabel("Ad:"));
            panel.add(tfAd);
            panel.add(new JLabel("Soyad:"));
            panel.add(tfSoyad);
            panel.add(new JLabel("E-posta:"));
            panel.add(tfEposta);
            panel.add(new JLabel("Telefon:"));
            panel.add(tfTelefon);
            panel.add(new JLabel("Adres:"));
            panel.add(tfAdres);

            int result = JOptionPane.showConfirmDialog(this, panel, "Üye Bilgilerini Güncelle",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result != JOptionPane.OK_OPTION) {
                JOptionPane.showMessageDialog(this, "Güncelleme işlemi iptal edildi.",
                        "Bilgi", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String yeniAd = tfAd.getText();
            String yeniSoyad = tfSoyad.getText();
            String yeniEposta = tfEposta.getText();
            String yeniTelefon = tfTelefon.getText();
            String yeniAdres = tfAdres.getText();

            Uye yeniUye = new Uye(
                    guncellenecekID,
                    yeniAd,
                    yeniSoyad,
                    yeniEposta,
                    yeniTelefon,
                    yeniAdres
            );

            try {
                FileWriter writer = new FileWriter(csvDosyaAdi, false);
                writer.write("UyeID,Ad,Soyad,Eposta,Telefon,Adres\n");
                writer.close();

                for (Uye uye : kalanUyeler) {
                    uye.csvDosyasinaYaz(csvDosyaAdi);
                }

                yeniUye.csvDosyasinaYaz(csvDosyaAdi);

                JOptionPane.showMessageDialog(this, "Üye başarıyla güncellendi!",
                        "Bilgi", JOptionPane.INFORMATION_MESSAGE);
                uyeleriGoster();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Dosya yazma hatası: " + e.getMessage(),
                        "Hata", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Geçersiz ID! Lütfen sayı girin.",
                    "Hata", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Üye güncellenirken hata oluştu: " + e.getMessage(),
                    "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
}