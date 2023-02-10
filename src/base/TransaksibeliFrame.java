/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import javax.swing.Timer;

/**
 *
 * @author Pramudya
 */
public class TransaksibeliFrame extends javax.swing.JFrame {

    /**
     * Creates new form JualFrame
     */
    public TransaksibeliFrame() {
        initComponents();
        this.btn_dashboard.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_dtpembeli.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_dtsupplier.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_transjual.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_dtakun.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_logout.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_laporan.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_about.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_dtbarang.setBackground(new java.awt.Color(0,0,0,0));
        showName();
        this.text_kodetransaksi.setEditable(false);
        text_idcust.setEditable(false);
        text_namacus.setEditable(false);
        text_alamatcus.setEditable(false);
        this.text_totbayar.setEditable(false);
        this.btnDeletetrans.setBackground(new java.awt.Color(0,0,0,0));
        this.btnCetaktrans.setBackground(new java.awt.Color(0,0,0,0));
        this.btnTambahdata.setBackground(new java.awt.Color(0,0,0,0));
        this.btnFindpelanggan.setBackground(new java.awt.Color(0,0,0,0));
        tanggalSekarang();
        jamsekarang();
        updateTableBarang();
        lastRecord();
    }
    
   private void tanggalSekarang(){
       
       Date harisekarang = new Date();
       SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
       this.label_date.setText(ft.format(harisekarang));
   }
   
   private void jamsekarang(){
       new Timer(0, new ActionListener(){
             @Override
           public void actionPerformed(ActionEvent e) {
               Date d = new Date();
               SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss a");
               label_time.setText(s.format(d));
           }
             
       }
            ).start();
   }
    
    public void totalBiaya(){
        int jumlahBaris = TabelDetail.getRowCount();
        int totalBiaya = 0;
        int hargaBarang;
        for (int i = 0; i < jumlahBaris; i++) {
            hargaBarang = Integer.parseInt(TabelDetail.getValueAt(i, 4).toString());
            totalBiaya = totalBiaya + hargaBarang;
        }
        this.text_totbayar.setText("Rp " + totalBiaya);
    }
    
     private void lastRecord(){
        try{
            String sql = "SELECT * FROM pembelian getLastRecord ORDER BY kode_pembelian DESC LIMIT 1";
            Connection c = (Connection) koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql);
            
            // show + add increment
            if (r.next()){
                String idBarang = r.getString("kode_pembelian");
                String slice = idBarang.substring(3);
                int idBarangResult = Integer.parseInt(slice) + 1;
                String idAfterIncrement = "TRB" + Integer.toString(idBarangResult);
                this.text_kodetransaksi.setText(idAfterIncrement);
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "error : " + ex.getMessage());
        }
    }
    
    private String idSelected;
    
    private void updateTableBarang() {
        String[] columnNames = {"kode_barang", "nama_barang", "stock", "expired"};
        DefaultTableModel tablemodel = new DefaultTableModel(columnNames, 0);
        
        try {
            Connection c = (Connection) koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT kode_barang, nama_barang, stock, expired FROM barang");
            
            while(rs.next()) {
                String idBarang = rs.getString("kode_barang"),
                            namaBarang = rs.getString("nama_barang"),
                            stockBarang = rs.getString("stock"),
                            expiredBarang = rs.getString("expired");
                
                // create a single array of one rows worth of data
                String[] data = {idBarang, namaBarang, stockBarang, expiredBarang};
                tablemodel.addRow(data);
                
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
        }
        this.TabelBarang.setModel(tablemodel);
    }
    
    private void showName(){
         try {
            String sql = "SELECT * FROM session_login WHERE kode_sesi = 1";
            Connection c = (Connection) koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql);
            if(r.next()){
                String namaAkun = r.getString("nama");
                this.label_nama.setText(namaAkun);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        text_hargabrg = new javax.swing.JTextField();
        label_time = new javax.swing.JLabel();
        label_date = new javax.swing.JLabel();
        text_jumlahbrg = new javax.swing.JTextField();
        text_kembalian = new javax.swing.JTextField();
        text_alamatcus = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelBarang = new javax.swing.JTable();
        text_findbarang = new javax.swing.JTextField();
        text_namacus = new javax.swing.JTextField();
        btn_dashboard = new javax.swing.JButton();
        btn_dtsupplier = new javax.swing.JButton();
        btn_dtpembeli = new javax.swing.JButton();
        btn_dtbarang = new javax.swing.JButton();
        label_nama = new javax.swing.JLabel();
        btn_dtakun = new javax.swing.JButton();
        btn_transjual = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TabelDetail = new javax.swing.JTable();
        text_idcust = new javax.swing.JTextField();
        text_bayar = new javax.swing.JTextField();
        text_totbayar = new javax.swing.JTextField();
        text_kodetransaksi = new javax.swing.JTextField();
        text_buyer = new javax.swing.JTextField();
        btnTambahdata = new javax.swing.JButton();
        btnFindpelanggan = new javax.swing.JButton();
        btn_laporan = new javax.swing.JButton();
        btn_about = new javax.swing.JButton();
        btn_logout = new javax.swing.JButton();
        btnCetaktrans = new javax.swing.JButton();
        btnDeletetrans = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(10020, 725));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(text_hargabrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 444, 130, 30));

        label_time.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        label_time.setForeground(new java.awt.Color(0, 0, 0));
        label_time.setText("Time");
        getContentPane().add(label_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 70, 90, 20));

        label_date.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        label_date.setForeground(new java.awt.Color(0, 0, 0));
        label_date.setText("Date");
        getContentPane().add(label_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 70, 90, 20));
        getContentPane().add(text_jumlahbrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 444, 90, 30));

        text_kembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_kembalianActionPerformed(evt);
            }
        });
        getContentPane().add(text_kembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 398, 140, 30));

        text_alamatcus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_alamatcusActionPerformed(evt);
            }
        });
        getContentPane().add(text_alamatcus, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 240, 150, 35));

        TabelBarang.setBorder(new javax.swing.border.MatteBorder(null));
        TabelBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "nama", "stock", "expired"
            }
        ));
        TabelBarang.setRowHeight(24);
        TabelBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelBarangMouseClicked(evt);
            }
        });
        TabelBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TabelBarangKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(TabelBarang);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 480, 580, 130));

        text_findbarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_findbarangActionPerformed(evt);
            }
        });
        getContentPane().add(text_findbarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 610, 320, 38));

        text_namacus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_namacusActionPerformed(evt);
            }
        });
        getContentPane().add(text_namacus, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 170, 150, 35));

        btn_dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dashboardActionPerformed(evt);
            }
        });
        getContentPane().add(btn_dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 300, 40));

        btn_dtsupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dtsupplierActionPerformed(evt);
            }
        });
        getContentPane().add(btn_dtsupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 251, 300, 40));

        btn_dtpembeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dtpembeliActionPerformed(evt);
            }
        });
        getContentPane().add(btn_dtpembeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 293, 300, 40));

        btn_dtbarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dtbarangActionPerformed(evt);
            }
        });
        getContentPane().add(btn_dtbarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 300, 40));

        label_nama.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        label_nama.setForeground(new java.awt.Color(0, 0, 0));
        label_nama.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_nama.setText("Nama");
        getContentPane().add(label_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 280, 30));

        btn_dtakun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dtakunActionPerformed(evt);
            }
        });
        getContentPane().add(btn_dtakun, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 300, 40));

        btn_transjual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_transjualActionPerformed(evt);
            }
        });
        getContentPane().add(btn_transjual, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 300, 40));

        TabelDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Barang", "Nama Barang", "Harga", "Jumlah", "Total"
            }
        ));
        TabelDetail.setRowHeight(20);
        jScrollPane2.setViewportView(TabelDetail);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 170, 400, 150));

        text_idcust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_idcustActionPerformed(evt);
            }
        });
        getContentPane().add(text_idcust, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 100, 110, 35));

        text_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_bayarActionPerformed(evt);
            }
        });
        text_bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_bayarKeyReleased(evt);
            }
        });
        getContentPane().add(text_bayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 365, 140, 30));
        getContentPane().add(text_totbayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 332, 140, 30));
        getContentPane().add(text_kodetransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 130, 130, 30));

        text_buyer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_buyerActionPerformed(evt);
            }
        });
        getContentPane().add(text_buyer, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 100, 130, 30));

        btnTambahdata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahdataActionPerformed(evt);
            }
        });
        getContentPane().add(btnTambahdata, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 450, 70, 20));

        btnFindpelanggan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/search (1).png"))); // NOI18N
        btnFindpelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindpelangganActionPerformed(evt);
            }
        });
        getContentPane().add(btnFindpelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 100, 35, 35));

        btn_laporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_laporanActionPerformed(evt);
            }
        });
        getContentPane().add(btn_laporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 500, 300, 40));

        btn_about.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_aboutActionPerformed(evt);
            }
        });
        getContentPane().add(btn_about, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 540, 300, 40));

        btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logoutActionPerformed(evt);
            }
        });
        getContentPane().add(btn_logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 580, 300, 40));

        btnCetaktrans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetaktransActionPerformed(evt);
            }
        });
        getContentPane().add(btnCetaktrans, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 390, 80, 30));

        btnDeletetrans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletetransActionPerformed(evt);
            }
        });
        getContentPane().add(btnDeletetrans, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 340, 70, 20));

        jButton4.setText("GO");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 610, 50, 39));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/TransaksiBeli.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void text_findbarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_findbarangActionPerformed
        caridatabarang();
    }//GEN-LAST:event_text_findbarangActionPerformed

    private void text_namacusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_namacusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_namacusActionPerformed

    private void btn_dashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dashboardActionPerformed
        new DashboardFrame().setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_dashboardActionPerformed

    private void btn_dtsupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dtsupplierActionPerformed
        new DatasupplierFrame().setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_dtsupplierActionPerformed

    private void btn_dtpembeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dtpembeliActionPerformed
        new NewDatapembeliFrame().setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_dtpembeliActionPerformed

    private void btn_dtbarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dtbarangActionPerformed
        new DatabarangFrame().setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_dtbarangActionPerformed

    private void btn_dtakunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dtakunActionPerformed
        if ("administrator".equals(label_nama.getText())){
            new adminDatauserFrame().setVisible(true);
            dispose();
        }else{
            new DatauserFrame().setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_btn_dtakunActionPerformed

    private void btn_transjualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_transjualActionPerformed
        new JualFrame().setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_transjualActionPerformed

    private void btn_laporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_laporanActionPerformed
        new LaporansajaFrame().setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_laporanActionPerformed

    private void btn_aboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_aboutActionPerformed
        new TentangkamiFrame().setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_aboutActionPerformed

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed
        new LoginFrame().setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_logoutActionPerformed

    private void TabelBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelBarangMouseClicked
        this.idSelected = this.TabelBarang.getValueAt(this.TabelBarang.getSelectedRow(), 0).toString();
//        this.showData();
    }//GEN-LAST:event_TabelBarangMouseClicked

    private void TabelBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TabelBarangKeyPressed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.idSelected = this.TabelBarang.getValueAt(this.TabelBarang.getSelectedRow() - 1, 0).toString();
//            this.showData();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN){
            this.idSelected = this.TabelBarang.getValueAt(this.TabelBarang.getSelectedRow() + 1, 0).toString();
//            this.showData();
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_TabelBarangKeyPressed

    private void text_buyerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_buyerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_buyerActionPerformed

    private void text_idcustActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_idcustActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_idcustActionPerformed

    private void text_alamatcusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_alamatcusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_alamatcusActionPerformed

    
    private void btnCetaktransActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetaktransActionPerformed
//        ifelsecekdatahasvaluee
    int cekcustomer = text_namacus.getText().length();
    int cektotalharga = this.text_totbayar.getText().length();
    int cekbayar = this.text_bayar.getText().length();
    if( (cekcustomer == 0) || (cektotalharga == 0) || (this.TabelDetail.getRowCount() == 0) || (cekbayar == 0)){
        JOptionPane.showMessageDialog(rootPane, "Data Belum Lengkap");
        
    }else{
        
    String kdPenjualan = this.text_kodetransaksi.getText();
    String kdKastamer = text_idcust.getText();
    String totalSemua = this.text_totbayar.getText().substring(3);
    try{
               String sql = "INSERT INTO pembelian (kode_pembelian, kode_supplier, total_beli) VALUES (?, ?, ?)";
               Connection conn = (Connection) koneksi.configDB();
               PreparedStatement pst = conn.prepareStatement(sql);
               
               pst.setString(1, kdPenjualan);
               pst.setString(2, kdKastamer);
               pst.setInt(3, Integer.parseInt(totalSemua));
               pst.executeUpdate();
               conn.close();
               
           }catch(SQLException ex){
               ex.printStackTrace();
               JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
           }
        
    for (int i = 0; i < TabelDetail.getRowCount(); i++) {
            
        try {
            String idbrgDetail = this.TabelDetail.getValueAt(i, 0).toString();
            String nmbrgDetail = this.TabelDetail.getValueAt(i, 1).toString();
            String jmlbrgDetail = this.TabelDetail.getValueAt(i, 3).toString();
            String hrgbrgDetail = this.TabelDetail.getValueAt(i, 2).toString();
            String tothrgbrgDetail = this.TabelDetail.getValueAt(i, 4).toString();
            String forsql = "INSERT INTO tr_pembelian_detail VALUES (?,?,?,?,?,?)";
            Connection conn = (Connection) koneksi.configDB();
            PreparedStatement pst = conn.prepareStatement(forsql);
            
            pst.setString(1, kdPenjualan);
            pst.setString(2, idbrgDetail);
            pst.setString(3, nmbrgDetail);
            pst.setInt(4, Integer.parseInt(jmlbrgDetail));
            pst.setInt(5, Integer.parseInt(hrgbrgDetail));
            pst.setInt(6, Integer.parseInt(tothrgbrgDetail));
            pst.executeUpdate();
            conn.close();
            updateTableBarang();
            
        } catch (SQLException ex) {
            Logger.getLogger(TransaksibeliFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        
        // clear value of all field
        text_idcust.setText("");
        text_namacus.setText("");
        text_alamatcus.setText("");
        text_buyer.setText("");
        this.text_kodetransaksi.setText("");
        this.text_bayar.setText("");
        this.text_totbayar.setText("");
        this.text_kembalian.setText("");
        
        // clear value of tabel
        DefaultTableModel model = (DefaultTableModel) this.TabelDetail.getModel();
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        
        JOptionPane.showMessageDialog(rootPane, "Transaksi Berhasil");
        lastRecord();
    }
    }//GEN-LAST:event_btnCetaktransActionPerformed
    
    private void addrowtotabeldetail(Object[] dataRow){
        DefaultTableModel model = (DefaultTableModel)TabelDetail.getModel();
        model.addRow(dataRow);
    }
    
    private String resultid = "";
    private void btnTambahdataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahdataActionPerformed
        if((this.text_jumlahbrg.getText().isEmpty()) || (this.text_hargabrg.getText().isEmpty()) || (this.TabelBarang.getSelectedRow() == -1)){           // or bla bla bla is empty, must be has data
            JOptionPane.showMessageDialog(rootPane, "Data belum lengkap");
        }
        else if (Integer.parseInt(this.text_jumlahbrg.getText()) < 0 || Integer.parseInt(this.text_jumlahbrg.getText()) == 0){
            JOptionPane.showMessageDialog(this, "jumlah barang harus angka positif");
        }
        else if (Integer.parseInt(TabelBarang.getValueAt(TabelBarang.getSelectedRow(), 2).toString()) < Integer.parseInt(this.text_jumlahbrg.getText())){
            JOptionPane.showMessageDialog(this, "maaf, stok barang ini tidak mencukupi");
        }
        else{
            int jumlahBarang = Integer.parseInt(text_jumlahbrg.getText());
                    int hargaBarang = Integer.parseInt(this.text_hargabrg.getText());
                    int total = jumlahBarang * hargaBarang;
            if(TabelDetail.getRowCount() >= 1){
                        for (int i = 0; i < TabelDetail.getRowCount(); i++) {
                    String id = TabelDetail.getValueAt(i, 0).toString();
                    String getidbarang = TabelBarang.getValueAt(TabelBarang.getSelectedRow(),0).toString();
                    
                    if (id.equals(getidbarang)){
                        resultid = id;
                    }
                    }
                if(resultid != ""){
                    JOptionPane.showMessageDialog(rootPane, "maaf duplikasi data");
                    resultid = "";
                }
                else{
                    
                    addrowtotabeldetail(new Object[]{
                     this.TabelBarang.getValueAt(this.TabelBarang.getSelectedRow(), 0).toString(),
                     this.TabelBarang.getValueAt(this.TabelBarang.getSelectedRow(), 1).toString(),
                     this.text_hargabrg.getText(),
                     this.text_jumlahbrg.getText(),
                     total
                });
                }
            totalBiaya();
            }else{
                addrowtotabeldetail(new Object[]{
                 this.TabelBarang.getValueAt(this.TabelBarang.getSelectedRow(), 0).toString(),
                 this.TabelBarang.getValueAt(this.TabelBarang.getSelectedRow(), 1).toString(),
                 this.text_hargabrg.getText(),
                 this.text_jumlahbrg.getText(),
                 total
                });
            totalBiaya();
            }
        }
    }//GEN-LAST:event_btnTambahdataActionPerformed
// ================== get customer data =========================================================
    public static void addidcust(String idcuss){
        text_idcust.setText(idcuss);
    }
    public static void addnamecust(String namecuss){
        text_namacus.setText(namecuss);
        text_buyer.setText(namecuss);
    }
    public static void addalamatcus(String almtcus){
        text_alamatcus.setText(almtcus);
    }
    private void btnFindpelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindpelangganActionPerformed
        ListsupplierFrame popup = new ListsupplierFrame();
        popup.setVisible(true);
        popup.pack();
    }//GEN-LAST:event_btnFindpelangganActionPerformed

    private void btnDeletetransActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletetransActionPerformed
        DefaultTableModel model = (DefaultTableModel) this.TabelDetail.getModel();
        int rowSelected = TabelDetail.getSelectedRow();
        model.removeRow(rowSelected);
        JOptionPane.showMessageDialog(this, "data dihapus");
        totalBiaya();
    }//GEN-LAST:event_btnDeletetransActionPerformed

    private void text_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_bayarActionPerformed
        if (this.text_totbayar.getText().length() != 0){
           String hargastring = text_totbayar.getText().substring(3);
            int totalHarga = Integer.parseInt(hargastring);
            int totalBayar = Integer.parseInt(this.text_bayar.getText());
            if (totalBayar >= totalHarga){
                int hasilKembalian = totalBayar - totalHarga;
                DecimalFormat formatter = new DecimalFormat("#,###");
                this.text_kembalian.setText("Rp " + formatter.format(hasilKembalian));
                this.text_bayar.setText(formatter.format(totalBayar));
            }else{
                JOptionPane.showMessageDialog(this, "Uang Anda Tidak Cukup");
            }
        }
    }//GEN-LAST:event_text_bayarActionPerformed

    private void text_bayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_bayarKeyReleased
//        int getNumber = Integer.parseInt(this.text_bayar.getText());
//        String numberbayar = String.format("%,d", getNumber);
//        text_bayar.setText(numberbayar);
    }//GEN-LAST:event_text_bayarKeyReleased

    private void text_kembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_kembalianActionPerformed
        
    }//GEN-LAST:event_text_kembalianActionPerformed

    private void caridatabarang(){
        String[] columnNames = {"kode_barang", "nama_barang", "stock", "expired"};
        DefaultTableModel tablemodel = new DefaultTableModel(columnNames, 0);
        try {
            String findBarang = this.text_findbarang.getText();
            String sql = ("SELECT kode_barang, nama_barang, stock, satuan, expired FROM barang WHERE nama_barang LIKE '%" + findBarang + "%'");
            Connection konekin = (Connection) koneksi.configDB();
            Statement stat = konekin.createStatement();
            ResultSet rs = stat.executeQuery(sql);
              while(rs.next()) {
                String idBarang = rs.getString("kode_barang"),
                            namaBarang = rs.getString("nama_barang"),
                            stockBarang = rs.getString("stock"),
                            hargaBarang = rs.getString("satuan"),
                            expiredBarang = rs.getString("expired");
                
                // create a single array of one rows worth of data
                String[] data = {idBarang, namaBarang, stockBarang, hargaBarang, expiredBarang};
                tablemodel.addRow(data);
                
            } }
             catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "error : " + ex.getMessage());
        }
        this.TabelBarang.setModel(tablemodel);
    }
    
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        caridatabarang();
    }//GEN-LAST:event_jButton4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TransaksibeliFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TransaksibeliFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TransaksibeliFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TransaksibeliFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TransaksibeliFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TabelBarang;
    private javax.swing.JTable TabelDetail;
    private javax.swing.JButton btnCetaktrans;
    private javax.swing.JButton btnDeletetrans;
    private javax.swing.JButton btnFindpelanggan;
    private javax.swing.JButton btnTambahdata;
    private javax.swing.JButton btn_about;
    private javax.swing.JButton btn_dashboard;
    private javax.swing.JButton btn_dtakun;
    private javax.swing.JButton btn_dtbarang;
    private javax.swing.JButton btn_dtpembeli;
    private javax.swing.JButton btn_dtsupplier;
    private javax.swing.JButton btn_laporan;
    private javax.swing.JButton btn_logout;
    private javax.swing.JButton btn_transjual;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel label_date;
    private javax.swing.JLabel label_nama;
    private javax.swing.JLabel label_time;
    private static javax.swing.JTextField text_alamatcus;
    private javax.swing.JTextField text_bayar;
    private static javax.swing.JTextField text_buyer;
    private javax.swing.JTextField text_findbarang;
    private javax.swing.JTextField text_hargabrg;
    private static javax.swing.JTextField text_idcust;
    private javax.swing.JTextField text_jumlahbrg;
    private javax.swing.JTextField text_kembalian;
    private javax.swing.JTextField text_kodetransaksi;
    private static javax.swing.JTextField text_namacus;
    private javax.swing.JTextField text_totbayar;
    // End of variables declaration//GEN-END:variables

    private void OR(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
