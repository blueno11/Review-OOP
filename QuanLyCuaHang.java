import java.util.*;
import java.text.DecimalFormat;

// Class đại diện cho sản phẩm
class SanPham {
    private int id;
    private String ten;
    private double gia;
    private String moTa;
    private String danhMuc;
    private int soLuongTonKho;
    private double phanTramGiamGia; // Phần trăm giảm giá (0-100)

    public SanPham(int id, String ten, double gia, String moTa, String danhMuc, int soLuongTonKho) {
        this.id = id;
        this.ten = ten;
        this.gia = gia;
        this.moTa = moTa;
        this.danhMuc = danhMuc;
        this.soLuongTonKho = soLuongTonKho;
        this.phanTramGiamGia = 0.0;
    }

    // Getter và Setter
    public int getId() { return id; }
    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }
    public double getGia() { return gia; }
    public void setGia(double gia) { this.gia = gia; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    public String getDanhMuc() { return danhMuc; }
    public void setDanhMuc(String danhMuc) { this.danhMuc = danhMuc; }
    public int getSoLuongTonKho() { return soLuongTonKho; }
    public void setSoLuongTonKho(int soLuongTonKho) { this.soLuongTonKho = soLuongTonKho; }
    public double getPhanTramGiamGia() { return phanTramGiamGia; }
    public void setPhanTramGiamGia(double phanTramGiamGia) { this.phanTramGiamGia = phanTramGiamGia; }

    // Tính giá sau khi giảm
    public double getGiaSauGiam() {
        return gia * (1 - phanTramGiamGia / 100);
    }

    // Giảm số lượng tồn kho
    public boolean giamTonKho(int soLuong) {
        if (soLuong <= soLuongTonKho) {
            soLuongTonKho -= soLuong;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,###.##");
        String giamGiaText = phanTramGiamGia > 0 ? 
            " (Giảm " + phanTramGiamGia + "% - Gia: " + df.format(getGiaSauGiam()) + " VND)" : "";
        
        return String.format("ID: %d | Ten: %s | Gia: %s VND%s\nDanh muc: %s | Ton kho: %d\nMo ta: %s\n%s",
                id, ten, df.format(gia), giamGiaText, danhMuc, soLuongTonKho, moTa,
                "─".repeat(60));
    }
}

class MonHangDatHang {
    private SanPham sanPham;
    private int soLuong;
    public MonHangDatHang(SanPham sanPham, int soLuong){
        this.sanPham = sanPham;
        this.soLuong = soLuong;
    }
    public SanPham getSanPham (){return sanPham;}
    public int getSoLuong(){return soLuong;}
    public double getTongTien(){return sanPham.getGiaSauGiam()*soLuong;}
} 
public class QuanLyCuaHang {
    private List<SanPham> dsSanPham;
    private List<MonHangDatHang> gioHang;
    private Scanner scanner;
    private DecimalFormat df;

    public QuanLyCuaHang() {
        dsSanPham = new ArrayList<>();
        gioHang = new ArrayList<>();
        scanner = new Scanner(System.in);
        df = new DecimalFormat("#,###.##");
        themSanPhamMau();
    }
    private void themSanPhamMau() {
        dsSanPham.add(new SanPham(1, "Laptop Dell XPS 13", 25000000, "Laptop cao cap, man hinh 13 inch", "Dien tu", 10));
        dsSanPham.add(new SanPham(2, "iPhone 15", 22000000, "Smartphone moi nhat cua Apple", "Dien tu", 15));
        dsSanPham.add(new SanPham(3, "Ao thun Nike", 500000, "Ao thun the thao chat luong cao", "Thoi trang", 50));
        dsSanPham.add(new SanPham(4, "Giay Adidas", 1500000, "Giay the thao chinh hang", "Thoi trang", 30));
        dsSanPham.add(new SanPham(5, "Sach Java Programming", 350000, "Sach hoc lap trinh Java", "Sach", 25));
        dsSanPham.add(new SanPham(6, "Ban phim co", 2000000, "Ban phim gaming RGB", "Dien tu", 20));
        dsSanPham.add(new SanPham(7,"Tay cam choi game",800000,"Tay cam chinh hang","Dien tu",15));
    }

    public void chayUngDung() {
        System.out.println("Chao Mung Den Cua Hang");
        System.out.println("=".repeat(50));

        while (true) {
            hienThiMenu();
            int luaChon = nhapSoNguyen("Vui long nhap lua chon cua ban: ");

            switch (luaChon) {
                case 1: hienThiTatCaSanPham(); break;
                case 2: hienThiSanPhamTheoGia(); break;
                case 3: hienThiSanPhamTheoDanhMuc(); break;
                case 4: capNhatThongTinSanPham(); break;
                case 5: tinhTongGiaTriTonKho(); break;
                case 6: giamGiaSanPham(); break;
                case 7: datHang(); break;
                case 8: xemGioHang(); break;
                case 9: thanhToan(); break;
                case 0: 
                    System.out.println("Cam on ban da su dung ung dung!");
                    return;
                default: 
                    System.out.println("Lua chon khong hop le, vui long thu lai!");
            }
            
            System.out.println("\nNhan Enter de tiep tuc...");
            scanner.nextLine();
        }
    }
    private void hienThiMenu() {
        System.out.println("\nMENU CHINH");
        System.out.println("=".repeat(30));
        System.out.println("1. Hien thi tat ca san pham");
        System.out.println("2. Hien thi san pham theo gia");
        System.out.println("3. Hien thi san pham theo danh muc");
        System.out.println("4. Cap nhat thong tin san pham");
        System.out.println("5. Tinh tong gia tri ton kho");
        System.out.println("6. Giam gia san pham");
        System.out.println("7. Dat hang");
        System.out.println("8. Xem gio hang");
        System.out.println("9. Thanh toan");
        System.out.println("0. Thoat");
        System.out.println("=".repeat(30));
    }
    private void hienThiTatCaSanPham() {
        System.out.println("\nDanh sach tat ca san pham:");
        System.out.println("=".repeat(60));
        if (dsSanPham.isEmpty()) {
            System.out.println("Khong co san pham nao trong cua hang.");
            return;
        }
        for (SanPham sp : dsSanPham) {
            System.out.println(sp);
        }
    }
    private void hienThiSanPhamTheoGia() {
        System.out.println("\nHien thi san pham theo gia");
        System.out.println("=".repeat(40));
        System.out.println("1. Tu thap den cao");
        System.out.println("2. Tu cao den thap");
        int luaChon = nhapSoNguyen("Chon cach sap xep: ");
        List<SanPham> dsSapXep = new ArrayList<>(dsSanPham);
        if (luaChon == 1) {
            dsSapXep.sort(Comparator.comparingDouble(SanPham::getGiaSauGiam));
            System.out.println("\nSan pham theo gia (thap -> cao):");
        } else if (luaChon == 2) {
            dsSapXep.sort(Comparator.comparingDouble(SanPham::getGiaSauGiam).reversed());
            System.out.println("\nSan pham theo gia (cao -> thap):");
        } else {
            System.out.println("Lua chon khong hop le!");
            return;
        }
        System.out.println("=".repeat(60));
        for (SanPham sp : dsSapXep) {
            System.out.println(sp);
        }
    }
     private void hienThiSanPhamTheoDanhMuc() {
        System.out.println("\nHien thi san pham theo danh muc");
        System.out.println("=".repeat(45));
        
        Set<String> danhMucs = new HashSet<>();
        for (SanPham sp : dsSanPham) {
            danhMucs.add(sp.getDanhMuc());
        }
        System.out.println("Danh muc co san:");
        int i = 1;
        List<String> dsDanhMuc = new ArrayList<>(danhMucs);
        for (String dm : dsDanhMuc) {
            System.out.println(i++ + ". " + dm);
        }
        int luaChon = nhapSoNguyen("Chon danh muc: ");
        if (luaChon < 1 || luaChon > dsDanhMuc.size()) {
            System.out.println("Lua chon khong hop le!");
            return;
        }
        String danhMucChon = dsDanhMuc.get(luaChon - 1);
        System.out.println("\nSan pham trong danh muc: " + danhMucChon.toUpperCase());
        System.out.println("=".repeat(60));
        
        for (SanPham sp : dsSanPham) {
            if (sp.getDanhMuc().equals(danhMucChon)) {
                System.out.println(sp);
            }
        }
    }

    private void capNhatThongTinSanPham() {
        System.out.println("\nDanh sach tat ca san pham:");
        System.out.println("=".repeat(60));
        if (dsSanPham.isEmpty()) {
            System.out.println("Khong co san pham nao trong cua hang.");
            return;
        }
        for (SanPham sp : dsSanPham) {
            System.out.println(sp);
        }
        System.out.println("\nCap nhat thong tin san pham");
        System.out.println("=".repeat(40));
        
        int id = nhapSoNguyen("Nhap ID san pham can cap nhat: ");
        SanPham sp = timSanPhamTheoId(id);
        if (sp == null) {
            System.out.println("Khong tim thay san pham voi ID: " + id);
            return;
        }
        System.out.println("Thong tin hien tai:");
        System.out.println(sp);
        System.out.println("\nChon thong tin can cap nhat:");
        System.out.println("1. Ten san pham");
        System.out.println("2. Gia");
        System.out.println("3. Mo ta");
        System.out.println("4. Danh muc");
        System.out.println("5. So luong ton kho");
        
        int luaChon = nhapSoNguyen("Nhap lua chon: ");
        
        switch (luaChon) {
            case 1:
                System.out.print("Nhap ten moi: ");
                String tenMoi = scanner.nextLine();
                sp.setTen(tenMoi);
                break;
            case 2:
                double giaMoi = nhapSoThuc("Nhap gia moi: ");
                sp.setGia(giaMoi);
                break;
            case 3:
                System.out.print("Nhap mo ta moi: ");
                String moTaMoi = scanner.nextLine();
                sp.setMoTa(moTaMoi);
                break;
            case 4:
                System.out.print("Nhap danh muc moi: ");
                String danhMucMoi = scanner.nextLine();
                sp.setDanhMuc(danhMucMoi);
                break;
            case 5:
                int soLuongMoi = nhapSoNguyen("Nhap so luong ton kho moi: ");
                sp.setSoLuongTonKho(soLuongMoi);
                break;
            default:
                System.out.println("Lua chon khong hop le!");
                return;
        }
        System.out.println("Cap nhat thanh cong!");
        System.out.println("Thong tin sau khi cap nhat:");
        System.out.println(sp);
    }

    private void tinhTongGiaTriTonKho() {
        System.out.println("\nTong gia tri ton kho theo danh muc");
        System.out.println("=".repeat(50));
        Map<String, Double> tongGiaTriTheoDanhMuc = new HashMap<>();
        Map<String, Integer> soLuongTheoDanhMuc = new HashMap<>();
        for (SanPham sp : dsSanPham) {
            String danhMuc = sp.getDanhMuc();
            double giaTri = sp.getGiaSauGiam() * sp.getSoLuongTonKho();
            
            tongGiaTriTheoDanhMuc.put(danhMuc, 
                tongGiaTriTheoDanhMuc.getOrDefault(danhMuc, 0.0) + giaTri);
            soLuongTheoDanhMuc.put(danhMuc, 
                soLuongTheoDanhMuc.getOrDefault(danhMuc, 0) + sp.getSoLuongTonKho());
        }
        double tongTatCa = 0;
        for (Map.Entry<String, Double> entry : tongGiaTriTheoDanhMuc.entrySet()) {
            String danhMuc = entry.getKey();
            double giaTri = entry.getValue();
            int soLuong = soLuongTheoDanhMuc.get(danhMuc);
            
            System.out.printf("%s: %s VND (%d san pham)\n", 
                danhMuc, df.format(giaTri), soLuong);
            tongTatCa += giaTri;
        }
        
        System.out.println("-".repeat(50));
        System.out.printf("Tong gia tri tat ca: %s VND\n", df.format(tongTatCa));
    }

    private void giamGiaSanPham() {
        System.out.println("\nGiam gia san pham");
        System.out.println("=".repeat(30));
        
        int id = nhapSoNguyen("Nhap ID san pham can giam gia: ");
        SanPham sp = timSanPhamTheoId(id);
        if (sp == null) {
            System.out.println("Khong tim thay san pham voi ID: " + id);
            return;
        }
        System.out.println("San pham duoc chon:");
        System.out.println(sp);
        
        double phanTramGiam = nhapSoThuc("Nhap phan tram giam gia (0-100): ");
        
        if (phanTramGiam < 0 || phanTramGiam > 100) {
            System.out.println("Phan tram giam gia phai tu 0 den 100!");
            return;
        }
        sp.setPhanTramGiamGia(phanTramGiam);
        System.out.println("Da ap dung giam gia thanh cong!");
        System.out.printf("Gia goc: %s VND\n", df.format(sp.getGia()));
        System.out.printf("Gia sau giam: %s VND (giam %.1f%%)\n", 
            df.format(sp.getGiaSauGiam()), phanTramGiam);
    }

    private void datHang() {
        System.out.println("\nDanh sach tat ca san pham:");
        System.out.println("=".repeat(60));
        if (dsSanPham.isEmpty()) {
            System.out.println("Khong co san pham nao trong cua hang.");
            return;
        }
        for (SanPham sp : dsSanPham) {
            System.out.println(sp);
        }
        System.out.println("\nDat hang");
        System.out.println("=".repeat(20));
        
        int id = nhapSoNguyen("Nhap ID san pham muon mua: ");
        SanPham sp = timSanPhamTheoId(id);
        
        if (sp == null) {
            System.out.println("Khong tim thay san pham voi ID: " + id);
            return;
        }
        System.out.println("San pham duoc chon:");
        System.out.println(sp);
        
        if (sp.getSoLuongTonKho() == 0) {
            System.out.println("San pham da het hang!");
            return;
        }
        int soLuong = nhapSoNguyen("Nhap so luong muon mua: ");
        if (soLuong <= 0) {
            System.out.println("So luong phai lon hon 0!");
            return;
        }
        if (soLuong > sp.getSoLuongTonKho()) {
            System.out.printf("Khong du hang ton kho! Chi con %d san pham.\n", 
                sp.getSoLuongTonKho());
            return;
        }
        boolean daTonTai = false;
        for (MonHangDatHang mh : gioHang) {
            if (mh.getSanPham().getId() == id) {
                int soLuongMoi = mh.getSoLuong() + soLuong;
                if (soLuongMoi > sp.getSoLuongTonKho()) {
                    System.out.printf("Tong so luong vuot qua ton kho! Chi co the them %d san pham nua.\n", 
                        sp.getSoLuongTonKho() - mh.getSoLuong());
                    return;
                }
                gioHang.remove(mh);
                gioHang.add(new MonHangDatHang(sp, soLuongMoi));
                daTonTai = true;
                break;
            }
        }
        if (!daTonTai) {
            gioHang.add(new MonHangDatHang(sp, soLuong));
        }
        System.out.println("Da them vao gio hang thanh cong!");
        System.out.printf("%s x %d = %s VND\n", 
            sp.getTen(), soLuong, df.format(sp.getGiaSauGiam() * soLuong));
    }
    
    private void xemGioHang() {
        System.out.println("\nGio hang cua ban");
        System.out.println("=".repeat(30));
        
        if (gioHang.isEmpty()) {
            System.out.println("Gio hang trong!");
            return;
        }
        double tongTien = 0;
        int stt = 1;
        for (MonHangDatHang mh : gioHang) {
            SanPham sp = mh.getSanPham();
            double thanhTien = mh.getTongTien();
            tongTien += thanhTien;
            
            System.out.printf("%d. %s\n", stt++, sp.getTen());
            System.out.printf("   So luong: %d | Don gia: %s VND | Thanh tien: %s VND\n",
                mh.getSoLuong(), df.format(sp.getGiaSauGiam()), df.format(thanhTien));
            
            if (sp.getPhanTramGiamGia() > 0) {
                System.out.printf("   Da giam %.1f%% tu %s VND\n", 
                    sp.getPhanTramGiamGia(), df.format(sp.getGia()));
            }
            System.out.println();
        }
        
        System.out.println("-".repeat(40));
        System.out.printf("Tong tien: %s VND\n", df.format(tongTien));
    }

    private void thanhToan() {
        System.out.println("\nThanh toan");
        System.out.println("=".repeat(20));
        
        if (gioHang.isEmpty()) {
            System.out.println("Gio hang trong! Khong the thanh toan.");
            return;
        }
        
        xemGioHang();
        
        System.out.print("Xac nhan thanh toan? (y/n): ");
        String xacNhan = scanner.nextLine().toLowerCase();
        
        if (!xacNhan.equals("y") && !xacNhan.equals("yes")) {
            System.out.println("Da huy thanh toan!");
            return;
        }
        
        for (MonHangDatHang mh : gioHang) {
            SanPham sp = mh.getSanPham();
            sp.giamTonKho(mh.getSoLuong());
        }
        
        double tongTien = gioHang.stream()
            .mapToDouble(MonHangDatHang::getTongTien)
            .sum();
        
        System.out.println("\nThanh toan thanh cong!");
        System.out.println("=".repeat(30));
        System.out.printf("Tong tien da thanh toan: %s VND\n", df.format(tongTien));
        System.out.println("Don hang se duoc giao trong 3-5 ngay lam viec.");
        System.out.println("Cam on ban da mua hang!");
        
        gioHang.clear();
    }

    private SanPham timSanPhamTheoId(int id) {
        for (SanPham sp : dsSanPham) {
            if (sp.getId() == id) {
                return sp;
            }
        }
        return null;
    }

    private int nhapSoNguyen(String thongBao) {
        while (true) {
            try {
                System.out.print(thongBao);
                int so = Integer.parseInt(scanner.nextLine());
                return so;
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap mot so nguyen hop le!");
            }
        }
    }

    private double nhapSoThuc(String thongBao) {
        while (true) {
            try {
                System.out.print(thongBao);
                double so = Double.parseDouble(scanner.nextLine());
                return so;
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap mot so hop le!");
            }
        }
    }

    public static void main(String[] args) {
        new QuanLyCuaHang().chayUngDung();
    }
}
