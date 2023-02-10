/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pramudya
 */
public class DatauserFrame extends javax.swing.JFrame {

    /**
     * Creates new form DatauserFrame
     */
    public DatauserFrame() {
        initComponents();
        this.btn_dashboard.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_dtpembeli.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_dtsupplier.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_transbeli.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_transjual.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_logout.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_laporan.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_about.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_dtbarang.setBackground(new java.awt.Color(0,0,0,0));
        this.textFindBarang.setBackground(new java.awt.Color(0,0,0,0));
        this.jTextField1.setBackground(new java.awt.Color(0,0,0,0));
        updateTable();
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

     private void updateTable() {
        String[] columnNames = {"ID", "nama", "no.telepon"};
        DefaultTableModel tablemodel = new DefaultTableModel(columnNames, 0);
        
        try {
            Connection c = (Connection) koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT kode_akun, nama, telepon FROM akun");
            
            while(rs.next()) {
                String idAkun = rs.getString("kode_akun"),
                            namaAkun = rs.getString("nama"),
                            telpAkun = rs.getString("telepon");
                
                // create a single array of one rows worth of data
                String[] data = {idAkun, namaAkun, telpAkun};
                tablemodel.addRow(data);
                
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
        }
        this.TabelBarang.setModel(tablemodel);
    }
    
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
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelBarang = new javax.swing.JTable();
        textFindBarang = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        btn_dashboard = new javax.swing.JButton();
        label_nama = new javax.swing.JLabel();
        btn_dtsupplier = new javax.swing.JButton();
        btn_dtpembeli = new javax.swing.JButton();
        btn_dtbarang = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btn_transjual = new javax.swing.JButton();
        btn_transbeli = new javax.swing.JButton();
        btn_laporan = new javax.swing.JButton();
        btn_about = new javax.swing.JButton();
        btn_logout = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label_pemasukan.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        label_pemasukan.setForeground(new java.awt.Color(0, 0, 0));
        label_pemasukan.setText("Rp 0");
        getContentPane().add(label_pemasukan, new org.netbeans.lib.awtextra.AbsoluteConstraints(437, 92, 110, 20));

        label_pengeluaran.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        label_pengeluaran.setForeground(new java.awt.Color(0, 0, 0));
        label_pengeluaran.setText("Rp 0");
        getContentPane().add(label_pengeluaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(729, 92, 120, 20));

        TabelBarang.setBorder(new javax.swing.border.MatteBorder(null));
        TabelBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "nama", "nomor telepon"
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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 150, 580, 290));

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
        getContentPane().add(textFindBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 440, 510, 36));

        jTextField1.setText("Cari Pengguna");
        jTextField1.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextField1.setEnabled(false);
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 410, 510, 36));

        btn_dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_dashboardActionPerformed(evt);
            }
        });
        getContentPane().add(btn_dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 210, 300, 40));

        label_nama.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        label_nama.setForeground(new java.awt.Color(0, 0, 0));
        label_nama.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_nama.setText("Nama");
        getContentPane().add(label_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 280, 30));

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

        jButton1.setText("Cari");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 440, 70, 36));

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

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Data User.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void TabelBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelBarangMouseClicked
        
    }//GEN-LAST:event_TabelBarangMouseClicked

    private void TabelBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TabelBarangKeyPressed
      
    }//GEN-LAST:event_TabelBarangKeyPressed

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String[] columnNames = {"ID", "nama", "no.telepon"};
        DefaultTableModel tablemodel = new DefaultTableModel(columnNames, 0);
        try {
            String findBarang = this.textFindBarang.getText();
            String sql = ("SELECT kode_akun, nama, telepon FROM akun WHERE nama LIKE '%" + findBarang + "%'");
            Connection konekin = (Connection) koneksi.configDB();
            Statement stat = konekin.createStatement();
            ResultSet rs = stat.executeQuery(sql);
              while(rs.next()) {
                String idAkun = rs.getString("kode_akun"),
                            namaAkun = rs.getString("nama"),
                            telpAkun = rs.getString("telepon");
                
                // create a single array of one rows worth of data
                String[] data = {idAkun, namaAkun, telpAkun};
                tablemodel.addRow(data);
            } }
             catch(SQLException ex){
            JOptionPane.showMessageDialog(this, "error : " + ex.getMessage());
        }
        this.TabelBarang.setModel(tablemodel);
        
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(DatauserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DatauserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DatauserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DatauserFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DatauserFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TabelBarang;
    private javax.swing.JButton btn_about;
    private javax.swing.JButton btn_dashboard;
    private javax.swing.JButton btn_dtbarang;
    private javax.swing.JButton btn_dtpembeli;
    private javax.swing.JButton btn_dtsupplier;
    private javax.swing.JButton btn_laporan;
    private javax.swing.JButton btn_logout;
    private javax.swing.JButton btn_transbeli;
    private javax.swing.JButton btn_transjual;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel label_nama;
    private javax.swing.JLabel label_pemasukan;
    private javax.swing.JLabel label_pengeluaran;
    private javax.swing.JTextField textFindBarang;
    // End of variables declaration//GEN-END:variables
}
