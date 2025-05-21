package uygulama;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KutuphaneArayuz extends JFrame {
    
    private JButton btnKitapYonetimi;
    private JButton btnUyeYonetimi;
    private JButton btnOduncIslemleri;
    private JButton btnCikis;
    
    public KutuphaneArayuz() {

        setTitle("KÜTÜPHANE SİSTEMİ");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
 
        btnKitapYonetimi = new JButton("1. Kitap Yönetimi");
        btnUyeYonetimi = new JButton("2. Üye Yönetimi");
        btnOduncIslemleri = new JButton("3. Ödünç İşlemleri");
        btnCikis = new JButton("0. Çıkış");
        

        btnKitapYonetimi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
             
                KitapYoneticiGUI kitapGUI = new KitapYoneticiGUI();
                kitapGUI.setVisible(true);
            }
        });
        
        btnUyeYonetimi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              
                UyeYoneticiGUI uyeGUI = new UyeYoneticiGUI();
                uyeGUI.setVisible(true);
            }
        });
        
        btnOduncIslemleri.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
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
        

        panel.add(btnKitapYonetimi);
        panel.add(btnUyeYonetimi);
        panel.add(btnOduncIslemleri);
        panel.add(btnCikis);
        

        add(panel);
    }
    
    public static void main(String[] args) {
  
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                KutuphaneArayuz arayuz = new KutuphaneArayuz();
                arayuz.setVisible(true);
            }
        });
    }
}

