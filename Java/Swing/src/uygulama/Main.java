package uygulama;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {
    
    private JButton btnKitapYonetimi;
    private JButton btnUyeYonetimi;
    private JButton btnOduncIslemleri;
    private JButton btnCikis;
    
    public Main() {
        // Frame ayarları
        setTitle("KÜTÜPHANE SİSTEMİ");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Panel oluşturma
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Butonları oluşturma
        btnKitapYonetimi = new JButton("1. Kitap Yönetimi");
        btnUyeYonetimi = new JButton("2. Üye Yönetimi");
        btnOduncIslemleri = new JButton("3. Ödünç İşlemleri");
        btnCikis = new JButton("0. Çıkış");
        
        // Butonlara tıklama olayları ekleme
        btnKitapYonetimi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // KitapYonetici sınıfını Swing versiyonuyla çağırma
                KitapYoneticiGUI kitapGUI = new KitapYoneticiGUI();
                kitapGUI.setVisible(true);
            }
        });
        
        btnUyeYonetimi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // UyeYonetici sınıfını Swing versiyonuyla çağırma
                UyeYoneticiGUI uyeGUI = new UyeYoneticiGUI();
                uyeGUI.setVisible(true);
            }
        });
        
        btnOduncIslemleri.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // OduncYonetici sınıfını Swing versiyonuyla çağırma
                OduncYoneticiGUI oduncGUI = new OduncYoneticiGUI();
                oduncGUI.setVisible(true);
            }
        });
        
        btnCikis.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Programdan çıkılıyor...");
                System.exit(0);
            }
        });
        
        // Butonları panele ekleme
        panel.add(btnKitapYonetimi);
        panel.add(btnUyeYonetimi);
        panel.add(btnOduncIslemleri);
        panel.add(btnCikis);
        
        // Paneli frame'e ekleme
        add(panel);
    }
    
    public static void main(String[] args) {
        // Swing thread güvenliği için
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                KutuphaneArayuz arayuz = new KutuphaneArayuz();
                arayuz.setVisible(true);
            }
        });
    }
}

// Not: KitapYoneticiGUI, UyeYoneticiGUI ve OduncYoneticiGUI sınıflarını 
// ilgili dosyaları dönüştürdükten sonra oluşturacağım