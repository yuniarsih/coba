/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import com.sun.glass.events.KeyEvent;
import java.awt.Cursor;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Pramudya
 */
public class DatabarangFrame extends javax.swing.JFrame {

    /**
     * Creates new form DatabarangFrame
     */
    public DatabarangFrame() {
        initComponents();
        this.btn_dashboard.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_dtpembeli.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_dtsupplier.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_transbeli.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_transjual.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_dtakun.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_logout.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_laporan.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_about.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_dtbarang.setBackground(new java.awt.Color(0,0,0,0));
        this.textFindBarang.setBackground(new java.awt.Color(0,0,0,1));
        updateTable();
        lastRecord();
        showName();
        Spp();
        this.textid.setEditable(false);
        this.texteditid.setEditable(false);
    }
    
    private void Spp(){
       java.util.Date harisekarang = new java.util.Date();
       SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
       String hariinicuy = ft.format(harisekarang); 
       
        try{
            String sql = "SELECT tgl_penjualan, SUM(total) AS total_harga FROM `penjualan` getLastRecord WHERE  DATE(tgl_penjualan) = '"+hariinicuy+"' GROUP BY DATE(tgl_penjualan)" ;
            Connection c = (Connection) koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql);
            if(r.next()){
                String jumlahPendapatan = r.getString("total_harga");
                int jpRes = Integer.parseInt(jumlahPendapatan);
                DecimalFormat formatter = new DecimalFormat("#,###");
                this.label_pemasukan.setText("Rp "+ formatter.format(jpRes));
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        
       try{
            String sql = "SELECT tgl_pembelian, SUM(total_beli) AS total_harga FROM `pembelian` getLastRecord WHERE  DATE(tgl_pembelian) = '"+hariinicuy+"' GROUP BY DATE(tgl_pembelian)" ;
            Connection c = (Connection) koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql);
            if(r.next()){
                String jumlahPendapatan = r.getString("total_harga");
                int jpRes = Integer.parseInt(jumlahPendapatan);
                DecimalFormat formatter = new DecimalFormat("#,###");
                this.label_pengeluaran.setText("Rp "+ formatter.format(jpRes));
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
//    private void Spp(){
//        try{
//            String sql = "SELECT tgl_penjualan, SUM(total) AS total_harga FROM `penjualan` getLastRecord GROUP BY DATE(tgl_penjualan) ORDER BY kode_penjualan DESC LIMIT 1";
//            Connection c = (Connection) koneksi.configDB();
//            Statement s = c.createStatement();
//            ResultSet r = s.executeQuery(sql);
//            if(r.next()){
//                String jumlahPendapatan = r.getString("total_harga");
//                int jpRes = Integer.parseInt(jumlahPendapatan);
//                DecimalFormat formatter = new DecimalFormat("#,###");
//                this.label_pemasukan.setText("Rp "+ formatter.format(jpRes));
//            }
//        }catch (Exception e){
//            JOptionPane.showMessageDialog(this, e.getMessage());
//        }
//        
//        try{
//            String sqlPeng = "SELECT tgl_pembelian, SUM(total_beli) AS total_harga FROM `pembelian` getLastRecord GROUP BY DATE(tgl_pembelian) ORDER BY kode_pembelian DESC LIMIT 1";
//            Connection c = (Connection) koneksi.configDB();
//            Statement s = c.createStatement();
//            ResultSet r = s.executeQuery(sqlPeng);
//            if(r.next()){
//                String jumlahPendapatan = r.getString("total_harga");
//                int jpRes = Integer.parseInt(jumlahPendapatan);
//                DecimalFormat formatter = new DecimalFormat("#,###");
//                this.label_pengeluaran.setText("Rp "+ formatter.format(jpRes));
//            }
//        }catch (Exception e){
//            JOptionPane.showMessageDialog(this, e.getMessage());
//        }
//    }
    
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

    private void updateTable() {
        String[] columnNames = {"kode_barang", "nama_barang", "stock", "satuan", "expired"};
        DefaultTableModel tablemodel = new DefaultTableModel(columnNames, 0);
        
        try {
            Connection c = (Connection) koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT kode_barang, nama_barang, stock, satuan, expired FROM barang");
            
            while(rs.next()) {
                String idBarang = rs.getString("kode_barang"),
                            namaBarang = rs.getString("nama_barang"),
                            stockBarang = rs.getString("stock"),
                            hargaBarang = rs.getString("satuan"),
                            expiredBarang = rs.getString("expired");
                
                // create a single array of one rows worth of data
                String[] data = {idBarang, namaBarang, stockBarang, hargaBarang, expiredBarang};
                tablemodel.addRow(data);
                
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
        }
        this.TabelBarang.setModel(tablemodel);
    }
    
    private void lastRecord(){
        try{
            String sql = "SELECT * FROM barang getLastRecord ORDER BY kode_barang DESC LIMIT 1";
            Connection c = (Connection) koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql);
            
            // show + add increment
            if (r.next()){
                String idBarang = r.getString("kode_barang");
                String slice = idBarang.substring(3);
                int idBarangResult = Integer.parseInt(slice) + 1;
                String idAfterIncrement = "BRG" + Integer.toString(idBarangResult);
                this.textid.setText(idAfterIncrement);
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "error : " + ex.getMessage());
        }
        updateTable();
    }
    
    private String idSelected;
    
    private void showData() {
        try{
            String sql = "SELECT * FROM barang WHERE kode_barang = '" + this.idSelected + "'";
            Connection c = (Connection) koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql);
            
            // get data
            if (r.next()) {
                String namaBarang = r.getString("nama_barang"),
                            stockBarang = r.getString("stock"),
                            hargaBarang = r.getString("satuan"),
                            expiredBarang = r.getString("expired");
                
                // menampilkan data
                this.texteditid.setText(this.idSelected);
                this.texteditnama.setText(namaBarang);
                this.texteditstock.setText(stockBarang);
                this.texteditharga.setText(hargaBarang);
                java.util.Date datef;
                try {
                    datef = new SimpleDateFormat("yyyy-MM-dd").parse((String)expiredBarang);
                    texteditdatechooser.setDate(datef);
                } catch (ParseException ex) {
                    Logger.getLogger(DatabarangFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } 
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "error : " + ex.getMessage());
        }
    }
    
//    private void deleteData(){
//        try{
//            String idBarang = 
//        }
//    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_transjual = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelBarang = new javax.swing.JTable();
        textFindBarang = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        backgroundTextFind = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btn_laporan = new javax.swing.JButton();
        btndeletedata = new javax.swing.JButton();
        btnGofind = new javax.swing.JButton();
        label_pemasukan = new javax.swing.JLabel();
        label_pengeluaran = new javax.swing.JLabel();
        texteditid = new javax.swing.JTextField();
        btn_logout = new javax.swing.JButton();
        texteditnama = new javax.swing.JTextField();
        btn_dtbarang = new javax.swing.JButton();
        texteditstock = new javax.swing.JTextField();
        texteditharga = new javax.swing.JTextField();
        btn_dtakun = new javax.swing.JButton();
        btn_about = new javax.swing.JButton();
        btnupdatedata = new javax.swing.JButton();
        textharga = new javax.swing.JTextField();
        textstock = new javax.swing.JTextField();
        textnama = new javax.swing.JTextField();
        textid = new javax.swing.JTextField();
        label_nama = new javax.swing.JLabel();
        btn_transbeli = new javax.swing.JButton();
        btn_dtpembeli = new javax.swing.JButton();
        btn_dtsupplier = new javax.swing.JButton();
        btn_dashboard = new javax.swing.JButton();
        texteditdatechooser = new com.toedter.calendar.JDateChooser();
        textdatechooser = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_transjual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_transjualActionPerformed(evt);
            }
        });
        getContentPane().add(btn_transjual, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 300, 40));

        TabelBarang.setBorder(new javax.swing.border.MatteBorder(null));
        TabelBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "nama", "stock", "harga", "expired"
            }
        ));
        TabelBarang.setRowHeight(30);
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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 150, 580, 260));

        textFindBarang.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        textFindBarang.setForeground(new java.awt.Color(0, 0, 0));
        textFindBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textFindBarangMouseClicked(evt);
            }
        });
        textFindBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFindBarangActionPerformed(evt);
            }
        });
        textFindBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textFindBarangKeyTyped(evt);
            }
        });
        getContentPane().add(textFindBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 410, 450, 36));

        jTextField1.setText("Cari Barang");
        jTextField1.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextField1.setEnabled(false);
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 410, 450, 36));

        backgroundTextFind.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        backgroundTextFind.setEnabled(false);
        getContentPane().add(backgroundTextFind, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 410, 450, 36));

        btnSimpan.setText("add");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        getContentPane().add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 490, 60, 37));

        btn_laporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_laporanActionPerformed(evt);
            }
        });
        getContentPane().add(btn_laporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 500, 300, 40));

        btndeletedata.setText("Delete");
        btndeletedata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeletedataActionPerformed(evt);
            }
        });
        getContentPane().add(btndeletedata, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 410, 80, 37));

        btnGofind.setText("GO");
        btnGofind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGofindActionPerformed(evt);
            }
        });
        getContentPane().add(btnGofind, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 410, 50, 37));

        label_pemasukan.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        label_pemasukan.setForeground(new java.awt.Color(0, 0, 0));
        label_pemasukan.setText("Rp 0");
        getContentPane().add(label_pemasukan, new org.netbeans.lib.awtextra.AbsoluteConstraints(437, 92, 160, 20));

        label_pengeluaran.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        label_pengeluaran.setForeground(new java.awt.Color(0, 0, 0));
        label_pengeluaran.setText("Rp 0");
        getContentPane().add(label_pengeluaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(729, 92, 130, 20));
        getContentPane().add(texteditid, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 610, 110, 30));

        btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logoutActionPerformed(evt);
            }
        });
        getContentPane().add(btn_logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 580, 300, 40));
        getContentPane().add(texteditnama, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 610, 110, 30));

        btn_dtbarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dtbarangActionPerformed(evt);
            }
        });
        getContentPane().add(btn_dtbarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 300, 40));

        texteditstock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                texteditstockKeyTyped(evt);
            }
        });
        getContentPane().add(texteditstock, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 610, 100, 30));

        texteditharga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textedithargaKeyTyped(evt);
            }
        });
        getContentPane().add(texteditharga, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 610, 110, 30));

        btn_dtakun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dtakunActionPerformed(evt);
            }
        });
        getContentPane().add(btn_dtakun, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 300, 40));

        btn_about.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_aboutActionPerformed(evt);
            }
        });
        getContentPane().add(btn_about, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 540, 300, 40));

        btnupdatedata.setText("edit");
        btnupdatedata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdatedataActionPerformed(evt);
            }
        });
        getContentPane().add(btnupdatedata, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 603, 60, 37));

        textharga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                texthargaKeyTyped(evt);
            }
        });
        getContentPane().add(textharga, new org.netbeans.lib.awtextra.AbsoluteConstraints(689, 496, 110, 30));

        textstock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textstockKeyTyped(evt);
            }
        });
        getContentPane().add(textstock, new org.netbeans.lib.awtextra.AbsoluteConstraints(588, 496, 100, 30));
        getContentPane().add(textnama, new org.netbeans.lib.awtextra.AbsoluteConstraints(478, 496, 110, 30));

        textid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textidActionPerformed(evt);
            }
        });
        getContentPane().add(textid, new org.netbeans.lib.awtextra.AbsoluteConstraints(367, 496, 110, 30));

        label_nama.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        label_nama.setForeground(new java.awt.Color(0, 0, 0));
        label_nama.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_nama.setText("Nama");
        getContentPane().add(label_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 280, 30));

        btn_transbeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_transbeliActionPerformed(evt);
            }
        });
        getContentPane().add(btn_transbeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 460, 300, 40));

        btn_dtpembeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dtpembeliActionPerformed(evt);
            }
        });
        getContentPane().add(btn_dtpembeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 293, 300, 40));

        btn_dtsupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dtsupplierActionPerformed(evt);
            }
        });
        getContentPane().add(btn_dtsupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 251, 300, 40));

        btn_dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dashboardActionPerformed(evt);
            }
        });
        getContentPane().add(btn_dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 300, 40));
        getContentPane().add(texteditdatechooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 610, 98, 30));
        getContentPane().add(textdatechooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 496, 97, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/DataBarang.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
                   String idBarang = this.textid.getText(),
                    namaBarang = this.textnama.getText(),
                    stockBarang = this.textstock.getText(),
                    hargaBarang = this.textharga.getText();
//                    expiredBarang = this.textexpired.getText();
    
        if (Integer.parseInt(this.textstock.getText()) <= 0 || Integer.parseInt(this.textharga.getText()) <= 0){
            JOptionPane.showMessageDialog(rootPane, "nilai angka stock ataupun harga harus bernilai positif");
        }else{
        
            try{
                   String sql = "INSERT INTO barang VALUES (?, ?, ?, ?, ?)";
                   Connection conn = (Connection) koneksi.configDB();
                   PreparedStatement pst = conn.prepareStatement(sql);

                   pst.setString(1, idBarang);
                   pst.setString(2, namaBarang);
                   pst.setInt(3, Integer.parseInt(stockBarang));
                   pst.setInt(4, Integer.parseInt(hargaBarang));
    //               pst.setString(5, expiredBarang);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String dateBarang = sdf.format(textdatechooser.getDate());
                    pst.setString(5, dateBarang);
                   pst.executeUpdate();

                   conn.close();
                   clearanceaddtextbarang();
                   JOptionPane.showMessageDialog(rootPane, "Data Berhasil di Input");
               }catch(SQLException ex){
                   clearanceaddtextbarang();
                   ex.printStackTrace();
                   JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
               }
            this.updateTable();
            this.lastRecord();
        
        }

    }//GEN-LAST:event_btnSimpanActionPerformed

    private void clearanceaddtextbarang(){
        this.textid.setText("");
        this.textnama.setText("");
        this.textstock.setText("");
        this.textharga.setText("");
        this.textdatechooser.setCalendar(null);
    }
//    private void addincrementid(){
//        
//    }
    private void textidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textidActionPerformed

    private void btn_dashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dashboardActionPerformed
        new DashboardFrame().setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_dashboardActionPerformed

    private void btn_transjualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_transjualActionPerformed
        new JualFrame().setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_transjualActionPerformed

    private void TabelBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelBarangMouseClicked
        this.idSelected = this.TabelBarang.getValueAt(this.TabelBarang.getSelectedRow(), 0).toString();
        this.showData();
    }//GEN-LAST:event_TabelBarangMouseClicked

    private void TabelBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TabelBarangKeyPressed
        this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            this.idSelected = this.TabelBarang.getValueAt(this.TabelBarang.getSelectedRow() - 1, 0).toString();
            this.showData();
        } else if (evt.getKeyCode() == KeyEvent.VK_DOWN){
            this.idSelected = this.TabelBarang.getValueAt(this.TabelBarang.getSelectedRow() + 1, 0).toString();
            this.showData();
        }
        this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_TabelBarangKeyPressed

    private void btndeletedataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeletedataActionPerformed
        int response = JOptionPane.showConfirmDialog(this, "kamu yakin ingin menghapusnya?", "konfirmasi hapus", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (response == JOptionPane.YES_OPTION){
            
            try {
                String sql = "DELETE FROM barang WHERE kode_barang = '" + idSelected + "' ";
                Connection konekni = (Connection) koneksi.configDB();
                Statement stat = konekni.createStatement();
                stat.executeUpdate(sql);
                this.updateTable();
                this.texteditid.setText("");
                this.texteditnama.setText("");
                this.texteditstock.setText("");
                this.texteditharga.setText("");
                this.texteditdatechooser.setDate(null);
                JOptionPane.showMessageDialog(this, "data berhasil di hapus");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        lastRecord();
        }
        else if (response == JOptionPane.NO_OPTION){
            System.out.println("opsi kena tutup");
        }
        else if (response == JOptionPane.NO_OPTION){
            System.out.println("tidak jadi guys");
        }
    }//GEN-LAST:event_btndeletedataActionPerformed

    private void btnupdatedataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdatedataActionPerformed
        String idBarang = this.texteditid.getText(),
                    namaBarang = this.texteditnama.getText(),
                    stockBarang = this.texteditstock.getText(),
                    hargaBarang = this.texteditharga.getText();
//                    expiredBarang = this.texteditexpired.getText();

        if (Integer.parseInt(this.texteditstock.getText()) <= 0 || Integer.parseInt(this.texteditharga.getText()) <= 0){
            JOptionPane.showMessageDialog(rootPane, "nilai angka stock ataupun harga harus bernilai positif");
        }else{
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateBarang = sdf.format(texteditdatechooser.getDate());
            try {
                String sql = String.format("UPDATE barang SET nama_barang = '%s', stock = '%s', satuan = '%s', expired = '%s' WHERE kode_barang = '%s'", namaBarang, stockBarang,
                        hargaBarang, dateBarang, idBarang);
                Connection konekin = (Connection) koneksi.configDB();
                Statement stat = konekin.createStatement();
                stat.executeUpdate(sql);
                this.updateTable();
                this.texteditid.setText("");
                this.texteditnama.setText("");
                this.texteditstock.setText("");
                this.texteditharga.setText("");
                this.texteditdatechooser.setDate(null);
                JOptionPane.showMessageDialog(this, "data berhasil terupdate");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        
        }

    }//GEN-LAST:event_btnupdatedataActionPerformed

    private void btn_dtsupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dtsupplierActionPerformed
        new DatasupplierFrame().setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_dtsupplierActionPerformed

    private void btn_dtpembeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dtpembeliActionPerformed
        new NewDatapembeliFrame().setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_dtpembeliActionPerformed

    private void btn_transbeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_transbeliActionPerformed
        new TransaksibeliFrame().setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_transbeliActionPerformed

    private void btnGofindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGofindActionPerformed
        String[] columnNames = {"kode_barang", "nama_barang", "stock", "satuan", "expired"};
        DefaultTableModel tablemodel = new DefaultTableModel(columnNames, 0);
        try {
            String findBarang = this.textFindBarang.getText();
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
    }//GEN-LAST:event_btnGofindActionPerformed

    private void textFindBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textFindBarangMouseClicked
        
    }//GEN-LAST:event_textFindBarangMouseClicked

    private void textFindBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFindBarangActionPerformed
        
    }//GEN-LAST:event_textFindBarangActionPerformed

    private void textFindBarangKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textFindBarangKeyTyped
        
        if ("" != textFindBarang.getText()){
            jTextField1.setVisible(false);
            jTextField1.setEnabled(false);
        }
        else{
            jTextField1.setVisible(true);
            jTextField1.setEnabled(false);
        }
    }//GEN-LAST:event_textFindBarangKeyTyped

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

    private void btn_dtakunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dtakunActionPerformed
        if ("administrator".equals(label_nama.getText())){
            new adminDatauserFrame().setVisible(true);
            dispose();
        }else{
            new DatauserFrame().setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_btn_dtakunActionPerformed

    private void btn_dtbarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dtbarangActionPerformed
        new DatabarangFrame().setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_dtbarangActionPerformed

    public void filterAngka(java.awt.event.KeyEvent a){
        if(Character.isAlphabetic(a.getKeyChar())){
            a.consume();
        }else{
            
        }
    }
    
    private void textstockKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textstockKeyTyped
        this.filterAngka(evt);
    }//GEN-LAST:event_textstockKeyTyped

    private void texthargaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_texthargaKeyTyped
        this.filterAngka(evt);
    }//GEN-LAST:event_texthargaKeyTyped

    private void texteditstockKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_texteditstockKeyTyped
        this.filterAngka(evt);
    }//GEN-LAST:event_texteditstockKeyTyped

    private void textedithargaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textedithargaKeyTyped
        this.filterAngka(evt);
        // TODO add your handling code here:
    }//GEN-LAST:event_textedithargaKeyTyped

   
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
            java.util.logging.Logger.getLogger(DatabarangFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DatabarangFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DatabarangFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DatabarangFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DatabarangFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TabelBarang;
    private javax.swing.JTextField backgroundTextFind;
    private javax.swing.JButton btnGofind;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btn_about;
    private javax.swing.JButton btn_dashboard;
    private javax.swing.JButton btn_dtakun;
    private javax.swing.JButton btn_dtbarang;
    private javax.swing.JButton btn_dtpembeli;
    private javax.swing.JButton btn_dtsupplier;
    private javax.swing.JButton btn_laporan;
    private javax.swing.JButton btn_logout;
    private javax.swing.JButton btn_transbeli;
    private javax.swing.JButton btn_transjual;
    private javax.swing.JButton btndeletedata;
    private javax.swing.JButton btnupdatedata;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel label_nama;
    private javax.swing.JLabel label_pemasukan;
    private javax.swing.JLabel label_pengeluaran;
    private javax.swing.JTextField textFindBarang;
    private com.toedter.calendar.JDateChooser textdatechooser;
    private com.toedter.calendar.JDateChooser texteditdatechooser;
    private javax.swing.JTextField texteditharga;
    private javax.swing.JTextField texteditid;
    private javax.swing.JTextField texteditnama;
    private javax.swing.JTextField texteditstock;
    private javax.swing.JTextField textharga;
    private javax.swing.JTextField textid;
    private javax.swing.JTextField textnama;
    private javax.swing.JTextField textstock;
    // End of variables declaration//GEN-END:variables
}
