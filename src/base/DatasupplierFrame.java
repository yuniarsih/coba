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
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pramudya
 */
public class DatasupplierFrame extends javax.swing.JFrame {

    /**
     * Creates new form DatasupplierFrame
     */
    public DatasupplierFrame() {
        initComponents();
        this.btn_dashboard.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_dtpembeli.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_transbeli.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_transjual.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_dtakun.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_logout.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_laporan.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_about.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_dtbarang.setBackground(new java.awt.Color(0,0,0,0));
        this.textFindBarang.setBackground(new java.awt.Color(0,0,0,0));
        this.textid.setEditable(false);
        updateTable();
        lastRecord();
        showName();
        Spp();
    }
    
    private void Spp(){
       Date harisekarang = new Date();
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
    
     private void lastRecord() {
         try{
            String sql = "SELECT * FROM supplier getLastRecord ORDER BY kode_supplier DESC LIMIT 1";
            Connection c = (Connection) koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql);
            
            // show + add increment
            if (r.next()){
                String idBarang = r.getString("kode_supplier");
               String slice = idBarang.substring(3);
                int idBarangResult = Integer.parseInt(slice) + 1;
                String idAfterIncrement = "SUP" + Integer.toString(idBarangResult);
                this.textid.setText(idAfterIncrement);
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "error : " + ex.getMessage());
        }
        updateTable();
    }                                                            // done ===========================================
    
    private void updateTable() {
        String[] columnNames = {"kode_supplier", "nama", "alamat", "telepon"};
        DefaultTableModel tablemodel = new DefaultTableModel(columnNames, 0);
        
        try {
            Connection c = (Connection) koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT kode_supplier, nama, alamat, alamat, telepon FROM supplier");
            
            while(rs.next()) {
                String idPelanggan = rs.getString("kode_supplier"),
                            namaPelanggan = rs.getString("nama"),
                            alamatPelanggan = rs.getString("alamat"),
                            teleponPel = rs.getString("telepon");
                
                // create a single array of one rows worth of data
                String[] data = {idPelanggan, namaPelanggan, alamatPelanggan, teleponPel};
                tablemodel.addRow(data);
                
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
        }
        this.TabelBarang.setModel(tablemodel);
    }                                                                        // done ===================================
     
    private String idSelected;
    
      private void showData() {
        try{
            String sql = "SELECT * FROM supplier WHERE kode_supplier = '" + this.idSelected + "'";
            Connection c = (Connection) koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql);
            
            // get data
            if (r.next()) {
                String namaPelanggan = r.getString("nama"),
                            alamatPelanggan = r.getString("alamat"),
                            teleponPelanggan = r.getString("telepon");
                
                // menampilkan data
                this.texteditid.setText(this.idSelected);
                this.texteditnama.setText(namaPelanggan);
                this.texteditalamat.setText(alamatPelanggan);
                this.textedittelepon.setText(teleponPelanggan);
                
            } 
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "error : " + ex.getMessage());
        }
    }                                                                           // done ======================================

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label_pemasukan = new javax.swing.JLabel();
        label_pengeluaran = new javax.swing.JLabel();
        textid = new javax.swing.JTextField();
        texttelepon = new javax.swing.JTextField();
        texteditalamat = new javax.swing.JTextField();
        textedittelepon = new javax.swing.JTextField();
        textalamat = new javax.swing.JTextField();
        textFindBarang = new javax.swing.JTextField();
        btnupdatedata = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        backgroundTextFind = new javax.swing.JTextField();
        btndeletedata = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        texteditid = new javax.swing.JTextField();
        label_nama = new javax.swing.JLabel();
        btn_dashboard = new javax.swing.JButton();
        btn_dtpembeli = new javax.swing.JButton();
        btn_dtbarang = new javax.swing.JButton();
        btn_dtakun = new javax.swing.JButton();
        btn_transjual = new javax.swing.JButton();
        btn_transbeli = new javax.swing.JButton();
        btn_laporan = new javax.swing.JButton();
        btn_about = new javax.swing.JButton();
        btn_logout = new javax.swing.JButton();
        btnGofind = new javax.swing.JButton();
        textnama = new javax.swing.JTextField();
        texteditnama = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelBarang = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label_pemasukan.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        label_pemasukan.setForeground(new java.awt.Color(0, 0, 0));
        label_pemasukan.setText("Rp 0");
        getContentPane().add(label_pemasukan, new org.netbeans.lib.awtextra.AbsoluteConstraints(437, 92, 170, 20));

        label_pengeluaran.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        label_pengeluaran.setForeground(new java.awt.Color(0, 0, 0));
        label_pengeluaran.setText("Rp 0");
        getContentPane().add(label_pengeluaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(729, 92, 140, 20));

        textid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textidActionPerformed(evt);
            }
        });
        getContentPane().add(textid, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 490, 130, 30));

        texttelepon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textteleponKeyTyped(evt);
            }
        });
        getContentPane().add(texttelepon, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 490, 130, 30));
        getContentPane().add(texteditalamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 610, 140, 30));

        textedittelepon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                texteditteleponKeyTyped(evt);
            }
        });
        getContentPane().add(textedittelepon, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 610, 120, 30));

        textalamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textalamatActionPerformed(evt);
            }
        });
        getContentPane().add(textalamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 490, 140, 30));

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

        btnupdatedata.setText("edit");
        btnupdatedata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdatedataActionPerformed(evt);
            }
        });
        getContentPane().add(btnupdatedata, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 600, 60, 37));

        jTextField1.setText("Cari Pelanggan");
        jTextField1.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextField1.setEnabled(false);
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 410, 450, 36));

        backgroundTextFind.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        backgroundTextFind.setEnabled(false);
        getContentPane().add(backgroundTextFind, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 410, 450, 36));

        btndeletedata.setText("Delete");
        btndeletedata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeletedataActionPerformed(evt);
            }
        });
        getContentPane().add(btndeletedata, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 410, 80, 37));

        btnSimpan.setText("add");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        getContentPane().add(btnSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 480, 60, 37));
        getContentPane().add(texteditid, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 610, 130, 30));

        label_nama.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        label_nama.setForeground(new java.awt.Color(0, 0, 0));
        label_nama.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_nama.setText("Nama");
        getContentPane().add(label_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 280, 30));

        btn_dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dashboardActionPerformed(evt);
            }
        });
        getContentPane().add(btn_dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 300, 40));

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

        btn_transbeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_transbeliActionPerformed(evt);
            }
        });
        getContentPane().add(btn_transbeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 460, 300, 40));

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

        btnGofind.setText("GO");
        btnGofind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGofindActionPerformed(evt);
            }
        });
        getContentPane().add(btnGofind, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 410, 50, 37));
        getContentPane().add(textnama, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 490, 130, 30));
        getContentPane().add(texteditnama, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 610, 130, 30));

        TabelBarang.setBorder(new javax.swing.border.MatteBorder(null));
        TabelBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "nama", "stock", "harga"
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

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/DataSuplier.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textidActionPerformed

    private void textalamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textalamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textalamatActionPerformed

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

    private void btnupdatedataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdatedataActionPerformed
        String idPelanggan = this.texteditid.getText(),
        namaBarang = this.texteditnama.getText(),
        stockBarang = this.texteditalamat.getText(),
        hargaBarang = this.textedittelepon.getText();
        try {
            String sql = String.format("UPDATE supplier SET nama = '%s', alamat = '%s', telepon = '%s' WHERE kode_supplier = '%s'", namaBarang, stockBarang,
                hargaBarang, idPelanggan);
            Connection konekin = (Connection) koneksi.configDB();
            Statement stat = konekin.createStatement();
            stat.executeUpdate(sql);
            this.updateTable();
            this.texteditid.setText("");
            this.texteditnama.setText("");
            this.texteditalamat.setText("");
            this.textedittelepon.setText("");
            JOptionPane.showMessageDialog(this, "data berhasil terupdate");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnupdatedataActionPerformed
                                                                    // done ==============================================================
    
    private void btndeletedataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeletedataActionPerformed
        int response = JOptionPane.showConfirmDialog(this, "apakah anda yakin ingin menghapusnya?", "konfirmasi hapus", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION){
            
            try {
                String sql = "DELETE FROM supplier WHERE kode_supplier = '" + idSelected + "' ";
                Connection konekni = (Connection) koneksi.configDB();
                Statement stat = konekni.createStatement();
                stat.executeUpdate(sql);
                this.updateTable();
                this.texteditid.setText("");
                this.texteditnama.setText("");
                this.texteditalamat.setText("");
                this.textedittelepon.setText("");
                JOptionPane.showMessageDialog(this, "data berhasil di hapus");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
            lastRecord();
        
        }   else if (response == JOptionPane.NO_OPTION){
            System.out.println("tidak jadi hehe");
        }
        else if (response == JOptionPane.CLOSED_OPTION){
            System.out.println("opsi di tutup");
        }
        
    }//GEN-LAST:event_btndeletedataActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        String idPelanggan = this.textid.getText(),
        namaPel = this.textnama.getText(),
        alamatPel = this.textalamat.getText(),
        teleponPel = this.texttelepon.getText();
        try{
            String sql = "INSERT INTO supplier VALUES (?, ?, ?, ?)";
            Connection conn = (Connection) koneksi.configDB();
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, idPelanggan);
            pst.setString(2, namaPel);
            pst.setString(3, alamatPel);
            pst.setString(4, teleponPel);

            pst.executeUpdate();

            conn.close();
             this.textid.setText("");
            this.textnama.setText("");
            this.textalamat.setText("");
            this.texttelepon.setText("");
            JOptionPane.showMessageDialog(rootPane, "Data Berhasil di Input");
        }catch(SQLException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
        }
        this.updateTable();
        this.lastRecord();
    }//GEN-LAST:event_btnSimpanActionPerformed
                                                                                                                                                          // done ========================================
    
    private void btnGofindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGofindActionPerformed
        String[] columnNames = {"kode_supplier", "nama", "alamat", "telepon"};
        DefaultTableModel tablemodel = new DefaultTableModel(columnNames, 0);
        try {
            String findBarang = this.textFindBarang.getText();
            String sql = ("SELECT kode_supplier, nama, alamat, telepon FROM supplier WHERE nama LIKE '%" + findBarang + "%'");
            Connection konekin = (Connection) koneksi.configDB();
            Statement stat = konekin.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while(rs.next()) {
                String idPelanggan = rs.getString("kode_supplier"),
                namaPel = rs.getString("nama"),
                alamatPel = rs.getString("alamat"),
                teleponPel = rs.getString("telepon");

                // create a single array of one rows worth of data
                String[] data = {idPelanggan, namaPel,alamatPel,teleponPel};
                tablemodel.addRow(data);

            } }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "error : " + ex.getMessage());
            }
            this.TabelBarang.setModel(tablemodel);
    }//GEN-LAST:event_btnGofindActionPerformed

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

    private void btn_dashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_dashboardActionPerformed
        new DashboardFrame().setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_dashboardActionPerformed

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

    private void btn_transbeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_transbeliActionPerformed
        new TransaksibeliFrame().setVisible(true);
        dispose();
    }//GEN-LAST:event_btn_transbeliActionPerformed

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

        public void filterAngka(java.awt.event.KeyEvent a){
        if(Character.isAlphabetic(a.getKeyChar())){
            a.consume();
        }else{
            
        }
    }
    
    private void textteleponKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textteleponKeyTyped
        this.filterAngka(evt);
    }//GEN-LAST:event_textteleponKeyTyped

    private void texteditteleponKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_texteditteleponKeyTyped
        this.filterAngka(evt);
    }//GEN-LAST:event_texteditteleponKeyTyped

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
            java.util.logging.Logger.getLogger(DatasupplierFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DatasupplierFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DatasupplierFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DatasupplierFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DatasupplierFrame().setVisible(true);
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
    private javax.swing.JTextField textalamat;
    private javax.swing.JTextField texteditalamat;
    private javax.swing.JTextField texteditid;
    private javax.swing.JTextField texteditnama;
    private javax.swing.JTextField textedittelepon;
    private javax.swing.JTextField textid;
    private javax.swing.JTextField textnama;
    private javax.swing.JTextField texttelepon;
    // End of variables declaration//GEN-END:variables
}
