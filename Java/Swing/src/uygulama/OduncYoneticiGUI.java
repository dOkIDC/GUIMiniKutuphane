	package uygulama;


	import javax.swing.*;
	import javax.swing.table.DefaultTableModel;
	import java.awt.*;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.text.SimpleDateFormat;
	import java.util.*;
	import java.util.List;

	public class OduncYoneticiGUI extends JFrame {

	    private JPanel anaPanel;
	    private JTable oduncTablosu;
	    private DefaultTableModel tableModel;
	    private JButton btnOduncVer;
	    private JButton btnIadeEt;
	    private JButton btnTumIslemler;
	    private JButton btnGecikmisler;
	    private JButton btnUyeKitaplari;
	    private JButton btnKitapDurumu;
	    private JButton btnOrnekEkle;
	    private JButton btnCikis;

	    private String csvDosyaAdi = "oduncislemler.csv";

	    public OduncYoneticiGUI() {
	        setTitle("Ödünç İşlemleri Yönetimi");
	        setSize(1000, 500);
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setLocationRelativeTo(null);

	        anaPanel = new JPanel(new BorderLayout());

	        JPanel butonlarPanel = new JPanel(new GridLayout(8, 1, 5, 5));
	        butonlarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	        btnOduncVer = new JButton("1. Kitap Ödünç Ver");
	        btnIadeEt = new JButton("2. Kitap İade Et");
	        btnTumIslemler = new JButton("3. Tüm Ödünç İşlemlerini Göster");
	        btnGecikmisler = new JButton("4. İadesi Gecikmiş Kitapları Göster");
	        btnUyeKitaplari = new JButton("5. Üyenin Ödünç Aldığı Kitapları Göster");
	        btnKitapDurumu = new JButton("6. Kitabın Durumunu Göster");
	        btnOrnekEkle = new JButton("7. Örnek Ödünç İşlemleri Ekle");
	        btnCikis = new JButton("8. Çıkış");

	        btnOduncVer.addActionListener(e -> kitapOduncVer());
	        btnIadeEt.addActionListener(e -> kitapIadeEt());
	        btnTumIslemler.addActionListener(e -> tumOduncIslemleriGoster());
	        btnGecikmisler.addActionListener(e -> iadesiGecikmisKitaplariGoster());
	        btnUyeKitaplari.addActionListener(e -> uyeninOduncKitaplariGoster());
	        btnKitapDurumu.addActionListener(e -> kitabinDurumuGoster());
	        btnOrnekEkle.addActionListener(e -> ornekOduncIslemleriEkle());
	        btnCikis.addActionListener(e -> dispose());

	        butonlarPanel.add(btnOduncVer);
	        butonlarPanel.add(btnIadeEt);
	        butonlarPanel.add(btnTumIslemler);
	        butonlarPanel.add(btnGecikmisler);
	        butonlarPanel.add(btnUyeKitaplari);
	        butonlarPanel.add(btnKitapDurumu);
	        butonlarPanel.add(btnOrnekEkle);
	        butonlarPanel.add(btnCikis);

	        String[] kolonlar = {"İşlem ID", "Kitap ID", "Üye ID", "Ödünç Tarihi", "İade Tarihi", "Durum"};
	        tableModel = new DefaultTableModel(kolonlar, 0);
	        oduncTablosu = new JTable(tableModel);
	        JScrollPane scrollPane = new JScrollPane(oduncTablosu);

	        anaPanel.add(butonlarPanel, BorderLayout.WEST);
	        anaPanel.add(scrollPane, BorderLayout.CENTER);

	        add(anaPanel);
	    }

	    private void kitapOduncVer() {
	        JTextField tfKitapID = new JTextField(10);
	        JTextField tfUyeID = new JTextField(10);

	        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
	        panel.add(new JLabel("Kitap ID:"));
	        panel.add(tfKitapID);
	        panel.add(new JLabel("Üye ID:"));
	        panel.add(tfUyeID);

	        int result = JOptionPane.showConfirmDialog(this, panel, "Kitap Ödünç Ver",
	                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

	        if (result == JOptionPane.OK_OPTION) {
	            try {
	                int kitapId = Integer.parseInt(tfKitapID.getText());
	                int uyeId = Integer.parseInt(tfUyeID.getText());

	                // Kitabın müsait olup olmadığını kontrol et
	                if (!kitapMüsaitMi(kitapId)) {
	                    JOptionPane.showMessageDialog(this, "Bu kitap şu anda başka bir üyede ödünçte!",
	                            "Uyarı", JOptionPane.WARNING_MESSAGE);
	                    return;
	                }

	                // Yeni ödünç işlemi ID'si belirle
	                List<OduncIslem> tumIslemler = OduncIslem.csvDosyasindanOku(csvDosyaAdi);
	                int yeniIslemId = 1;
	                if (!tumIslemler.isEmpty()) {
	                    int enBuyukId = tumIslemler.stream().mapToInt(OduncIslem::islemIdGetir).max().orElse(0);
	                    yeniIslemId = enBuyukId + 1;
	                }

	                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	                String oduncTarihi = sdf.format(new Date());
	                Date iadeTarihiDate = new Date(System.currentTimeMillis() + 14 * 24 * 60 * 60 * 1000L);
	                String iadeTarihi = sdf.format(iadeTarihiDate);

	                OduncIslem yeniOduncIslem = new OduncIslem(yeniIslemId, kitapId, uyeId, oduncTarihi, iadeTarihi, false);

	                int onay = JOptionPane.showConfirmDialog(this,
	                        "Ödünç işlemi bilgileri:\n" + yeniOduncIslem.metneCevir() + "\n\nOnaylıyor musunuz?",
	                        "Onay", JOptionPane.YES_NO_OPTION);

	                if (onay == JOptionPane.YES_OPTION) {
	                    yeniOduncIslem.csvDosyasinaYaz(csvDosyaAdi);
	                    JOptionPane.showMessageDialog(this, "Ödünç işlemi başarıyla kaydedildi!");
	                    tumOduncIslemleriGoster();
	                }
	            } catch (NumberFormatException e) {
	                JOptionPane.showMessageDialog(this, "ID alanları sayı olmalıdır!",
	                        "Hata", JOptionPane.ERROR_MESSAGE);
	            } catch (Exception e) {
	                JOptionPane.showMessageDialog(this, "Ödünç verme işlemi sırasında hata oluştu: " + e.getMessage(),
	                        "Hata", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    }

	    private void kitapIadeEt() {
	        String uyeIdStr = JOptionPane.showInputDialog(this, "İade eden Üye ID:", "Kitap İade Et", JOptionPane.QUESTION_MESSAGE);
	        if (uyeIdStr == null || uyeIdStr.isEmpty()) return;

	        try {
	            int uyeId = Integer.parseInt(uyeIdStr);
	            List<OduncIslem> uyeninIslemleri = uyeninOduncIslemleriniGetir(uyeId);

	            List<OduncIslem> aktifIslemler = new ArrayList<>();
	            for (OduncIslem islem : uyeninIslemleri) {
	                if (!islem.iadeEdildiMi()) aktifIslemler.add(islem);
	            }

	            if (aktifIslemler.isEmpty()) {
	                JOptionPane.showMessageDialog(this, "Bu üyenin ödünç aldığı kitap bulunmamaktadır!");
	                return;
	            }

	            String[] secenekler = aktifIslemler.stream()
	                    .map(islem -> "İşlemID: " + islem.islemIdGetir() + " | KitapID: " + islem.kitapIdGetir() +
	                            " | Ödünç: " + islem.oduncTarihiGetir() + " | İade: " + islem.iadeTarihiGetir())
	                    .toArray(String[]::new);

	            String secim = (String) JOptionPane.showInputDialog(this, "İade edilecek işlemi seçin:",
	                    "İade Seçimi", JOptionPane.QUESTION_MESSAGE, null, secenekler, secenekler[0]);
	            if (secim == null) return;

	            int islemId = Integer.parseInt(secim.split(" ")[1]);

	            List<OduncIslem> tumIslemler = OduncIslem.csvDosyasindanOku(csvDosyaAdi);
	            List<OduncIslem> guncelIslemler = new ArrayList<>();
	            OduncIslem iadeEdilecekIslem = null;

	            for (OduncIslem islem : tumIslemler) {
	                if (islem.islemIdGetir() == islemId && !islem.iadeEdildiMi()) {
	                    iadeEdilecekIslem = islem;
	                    guncelIslemler.add(new OduncIslem(
	                            islem.islemIdGetir(),
	                            islem.kitapIdGetir(),
	                            islem.uyeIdGetir(),
	                            islem.oduncTarihiGetir(),
	                            islem.iadeTarihiGetir(),
	                            true
	                    ));
	                } else {
	                    guncelIslemler.add(islem);
	                }
	            }

	            if (iadeEdilecekIslem == null) {
	                JOptionPane.showMessageDialog(this, "Belirtilen ID'ye sahip iade edilmemiş bir işlem bulunamadı!");
	                return;
	            }

	            int onay = JOptionPane.showConfirmDialog(this,
	                    "İade edilecek kitap bilgileri:\n" + iadeEdilecekIslem.metneCevir() + "\n\nOnaylıyor musunuz?",
	                    "İade Onayı", JOptionPane.YES_NO_OPTION);

	            if (onay == JOptionPane.YES_OPTION) {
	                try {
	                    FileWriter writer = new FileWriter(csvDosyaAdi, false);
	                    writer.write("IslemID,KitapID,UyeID,OduncTarihi,IadeTarihi,IadeEdildi\n");
	                    writer.close();

	                    for (OduncIslem islem : guncelIslemler) {
	                        islem.csvDosyasinaYaz(csvDosyaAdi);
	                    }

	                    JOptionPane.showMessageDialog(this, "Kitap başarıyla iade edildi!");
	                    tumOduncIslemleriGoster();
	                } catch (IOException e) {
	                    JOptionPane.showMessageDialog(this, "Dosya yazma hatası: " + e.getMessage(),
	                            "Hata", JOptionPane.ERROR_MESSAGE);
	                }
	            }
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(this, "Geçersiz ID! Lütfen sayı girin.",
	                    "Hata", JOptionPane.ERROR_MESSAGE);
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(this, "İade işlemi sırasında hata oluştu: " + e.getMessage(),
	                    "Hata", JOptionPane.ERROR_MESSAGE);
	        }
	    }

	    private void tumOduncIslemleriGoster() {
	        tableModel.setRowCount(0);
	        List<OduncIslem> islemler = OduncIslem.csvDosyasindanOku(csvDosyaAdi);

	        for (OduncIslem islem : islemler) {
	            Object[] row = {
	                    islem.islemIdGetir(),
	                    islem.kitapIdGetir(),
	                    islem.uyeIdGetir(),
	                    islem.oduncTarihiGetir(),
	                    islem.iadeTarihiGetir(),
	                    islem.iadeEdildiMi() ? "İade Edildi" : "Ödünçte"
	            };
	            tableModel.addRow(row);
	        }
	    }

	    private void iadesiGecikmisKitaplariGoster() {
	        tableModel.setRowCount(0);
	        List<OduncIslem> islemler = OduncIslem.csvDosyasindanOku(csvDosyaAdi);
	        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	        Date bugun = new Date();

	        for (OduncIslem islem : islemler) {
	            if (!islem.iadeEdildiMi()) {
	                try {
	                    Date iadeTarihi = sdf.parse(islem.iadeTarihiGetir());
	                    if (bugun.after(iadeTarihi)) {
	                        long gecikmeGun = (bugun.getTime() - iadeTarihi.getTime()) / (24 * 60 * 60 * 1000);
	                        Object[] row = {
	                                islem.islemIdGetir(),
	                                islem.kitapIdGetir(),
	                                islem.uyeIdGetir(),
	                                islem.oduncTarihiGetir(),
	                                islem.iadeTarihiGetir(),
	                                "Gecikmiş (" + gecikmeGun + " gün)"
	                        };
	                        tableModel.addRow(row);
	                    }
	                } catch (Exception ignored) {}
	            }
	        }
	    }

	    private void uyeninOduncKitaplariGoster() {
	        String uyeIdStr = JOptionPane.showInputDialog(this, "Üye ID:", "Üyenin Ödünç Aldığı Kitaplar", JOptionPane.QUESTION_MESSAGE);
	        if (uyeIdStr == null || uyeIdStr.isEmpty()) return;

	        try {
	            int uyeId = Integer.parseInt(uyeIdStr);
	            List<OduncIslem> uyeninIslemleri = uyeninOduncIslemleriniGetir(uyeId);

	            tableModel.setRowCount(0);
	            for (OduncIslem islem : uyeninIslemleri) {
	                Object[] row = {
	                        islem.islemIdGetir(),
	                        islem.kitapIdGetir(),
	                        islem.uyeIdGetir(),
	                        islem.oduncTarihiGetir(),
	                        islem.iadeTarihiGetir(),
	                        islem.iadeEdildiMi() ? "İade Edildi" : "Ödünçte"
	                };
	                tableModel.addRow(row);
	            }
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(this, "Geçersiz ID! Lütfen sayı girin.",
	                    "Hata", JOptionPane.ERROR_MESSAGE);
	        }
	    }

	    private void kitabinDurumuGoster() {
	        String kitapIdStr = JOptionPane.showInputDialog(this, "Kitap ID:", "Kitabın Durumu", JOptionPane.QUESTION_MESSAGE);
	        if (kitapIdStr == null || kitapIdStr.isEmpty()) return;

	        try {
	            int kitapId = Integer.parseInt(kitapIdStr);
	            List<OduncIslem> kitabinIslemleri = kitabinOduncIslemleriniGetir(kitapId);

	            tableModel.setRowCount(0);
	            boolean kitapOduncte = false;
	            OduncIslem sonIslem = null;

	            for (OduncIslem islem : kitabinIslemleri) {
	                if (!islem.iadeEdildiMi()) {
	                    kitapOduncte = true;
	                    sonIslem = islem;
	                    break;
	                }
	            }

	            String durum = kitapOduncte ? "Ödünçte" : "Müsait";
	            JOptionPane.showMessageDialog(this,
	                    "Kitap ID: " + kitapId + "\nMevcut Durum: " + durum +
	                            (kitapOduncte && sonIslem != null ?
	                                    ("\nŞu andaki üye: " + sonIslem.uyeIdGetir() +
	                                            "\nÖdünç Tarihi: " + sonIslem.oduncTarihiGetir() +
	                                            "\nBeklenen İade Tarihi: " + sonIslem.iadeTarihiGetir())
	                                    : "\nKitap rafta ve ödünç verilebilir."));

	            for (OduncIslem islem : kitabinIslemleri) {
	                Object[] row = {
	                        islem.islemIdGetir(),
	                        islem.kitapIdGetir(),
	                        islem.uyeIdGetir(),
	                        islem.oduncTarihiGetir(),
	                        islem.iadeTarihiGetir(),
	                        islem.iadeEdildiMi() ? "İade Edildi" : "Ödünçte"
	                };
	                tableModel.addRow(row);
	            }
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(this, "Geçersiz ID! Lütfen sayı girin.",
	                    "Hata", JOptionPane.ERROR_MESSAGE);
	        }
	    }

	    private void ornekOduncIslemleriEkle() {
	        OduncIslem[] ornekIslemler = {
	                new OduncIslem(1, 101, 1, "01.05.2025", "15.05.2025", true),
	                new OduncIslem(2, 102, 2, "05.05.2025", "19.05.2025", false),
	                new OduncIslem(3, 103, 3, "10.05.2025", "24.05.2025", false),
	                new OduncIslem(4, 104, 1, "15.05.2025", "29.05.2025", false),
	                new OduncIslem(5, 105, 4, "01.05.2025", "15.05.2025", false)
	        };

	        for (OduncIslem islem : ornekIslemler) {
	            islem.csvDosyasinaYaz(csvDosyaAdi);
	        }

	        JOptionPane.showMessageDialog(this, "Örnek ödünç işlemleri başarıyla eklendi!",
	                "Bilgi", JOptionPane.INFORMATION_MESSAGE);

	        tumOduncIslemleriGoster();
	    }

	    // Yardımcı metotlar
	    private boolean kitapMüsaitMi(int kitapId) {
	        List<OduncIslem> kitabinIslemleri = kitabinOduncIslemleriniGetir(kitapId);
	        for (OduncIslem islem : kitabinIslemleri) {
	            if (!islem.iadeEdildiMi()) return false;
	        }
	        return true;
	    }

	    private List<OduncIslem> uyeninOduncIslemleriniGetir(int uyeId) {
	        List<OduncIslem> tumIslemler = OduncIslem.csvDosyasindanOku(csvDosyaAdi);
	        List<OduncIslem> uyeninIslemleri = new ArrayList<>();
	        for (OduncIslem islem : tumIslemler) {
	            if (islem.uyeIdGetir() == uyeId) uyeninIslemleri.add(islem);
	        }
	        return uyeninIslemleri;
	    }

	    private List<OduncIslem> kitabinOduncIslemleriniGetir(int kitapId) {
	        List<OduncIslem> tumIslemler = OduncIslem.csvDosyasindanOku(csvDosyaAdi);
	        List<OduncIslem> kitabinIslemleri = new ArrayList<>();
	        for (OduncIslem islem : tumIslemler) {
	            if (islem.kitapIdGetir() == kitapId) kitabinIslemleri.add(islem);
	        }
	        return kitabinIslemleri;
	    }
	}