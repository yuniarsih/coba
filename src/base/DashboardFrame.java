/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import com.sun.prism.paint.Color;
import java.awt.BorderLayout;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Pramudya
 */

public class DashboardFrame extends javax.swing.JFrame {

    /**
     * Creates new form DashboardFrame
     */
    public DashboardFrame() {
        initComponents();
        this.btn_dtsupplier.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_dtpembeli.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_dtsupplier.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_transbeli.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_transjual.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_dtakun.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_logout.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_laporan.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_about.setBackground(new java.awt.Color(0,0,0,0));
        this.btn_dtbarang.setBackground(new java.awt.Color(0,0,0,0));
        showName();
        activityBaru();
        showLineChart();
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
    
      private void showLineChart(){
        // get value how much transaction sell has done
            String arrTgl[] = new String[7];
            int arrJumlah[] = new int[7];
         try {
            Connection c = (Connection) koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT DATE(tgl_penjualan) AS tanggal, COUNT(kode_penjualan) AS jumlah_transaksi, DAY(tgl_penjualan) AS haritgl, MONTH(tgl_penjualan) AS motgl \n" +
"FROM `penjualan` getLastRecord\n" +
"GROUP BY tanggal\n" +
"ORDER BY `kode_penjualan` DESC  LIMIT 7");
            
            int i = 0;
             while (rs.next()) {
                 String idJumlah = rs.getString("jumlah_transaksi"),
                         tglTrans = rs.getString("haritgl"),
                         mntTrans = rs.getString("motgl");
                 arrJumlah[i] = Integer.parseInt(idJumlah);
                 arrTgl[i] = tglTrans + "/" + mntTrans;
                 i++;
             }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
        }
        //create dataset for the graph
         DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(arrJumlah[6], "Amount", arrTgl[6]);
        dataset.setValue(arrJumlah[5], "Amount", arrTgl[5]);
        dataset.setValue(arrJumlah[4], "Amount", arrTgl[4]);
        dataset.setValue(arrJumlah[3], "Amount", arrTgl[3]);
        dataset.setValue(arrJumlah[2], "Amount", arrTgl[2]);
        dataset.setValue(arrJumlah[1], "Amount", arrTgl[1]);
        dataset.setValue(arrJumlah[0], "Amount", arrTgl[0]);
        
        //create chart
        JFreeChart linechart; 
        linechart = ChartFactory.createLineChart("analisis penjualan","harian","jumlah", 
                dataset, PlotOrientation.VERTICAL, false,true,false);
        
        //create plot object
         CategoryPlot lineCategoryPlot;
        lineCategoryPlot = linechart.getCategoryPlot();
       // lineCategoryPlot.setRangeGridlinePaint(Color.BLUE);
        lineCategoryPlot.setBackgroundPaint(java.awt.Color.WHITE);
        
        //create render object to change the moficy the line properties like color
        LineAndShapeRenderer lineRenderer = (LineAndShapeRenderer) lineCategoryPlot.getRenderer();
//        Color lineChartColor;
//        lineChartColor = new Color(204,0,51);
        lineRenderer.setSeriesPaint(0, java.awt.Color.BLACK);
        
         //create chartPanel to display chart(graph)
        ChartPanel lineChartPanel = new ChartPanel(linechart);
        panelLineChart.removeAll();
        panelLineChart.add(lineChartPanel, BorderLayout.CENTER);
        panelLineChart.validate();
    }
    
    private void activityBaru(){
         String[] columnNames = {"nama transaksi", "nama barang", "total harga", "tanggal"};
        DefaultTableModel tablemodel = new DefaultTableModel(columnNames, 0);
        
         try {
            Connection c = (Connection) koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery("SELECT * FROM aktifitas_transaksi ORDER BY ID DESC LIMIT 10");
            
            while(rs.next()) {
                String namaTransaksi = rs.getString("jenis_transaksi"),
                            namaBarang = rs.getString("nama_barang"),
                            totHarga = rs.getString("total_harga"),
                            tanggal = rs.getString("tanggal");
                            
                
                // create a single array of one rows worth of data
                String[] data = {namaTransaksi, namaBarang, totHarga, tanggal};
                tablemodel.addRow(data);
                
            }
        this.tabel_aktivitas.setModel(tablemodel);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
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

        label_pengeluaran = new javax.swing.JLabel();
        label_pemasukan = new javax.swing.JLabel();
        panelLineChart = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_aktivitas = new javax.swing.JTable();
        btn_dtsupplier = new javax.swing.JButton();
        btn_dtpembeli = new javax.swing.JButton();
        label_nama = new javax.swing.JLabel();
        btn_dtbarang = new javax.swing.JButton();
        btn_dtakun = new javax.swing.JButton();
        btn_transjual = new javax.swing.JButton();
        btn_transbeli = new javax.swing.JButton();
        btn_laporan = new javax.swing.JButton();
        btn_about = new javax.swing.JButton();
        btn_logout = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        label_pengeluaran.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        label_pengeluaran.setForeground(new java.awt.Color(0, 0, 0));
        label_pengeluaran.setText("Rp 0");
        getContentPane().add(label_pengeluaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(729, 92, 160, 20));

        label_pemasukan.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        label_pemasukan.setForeground(new java.awt.Color(0, 0, 0));
        label_pemasukan.setText("Rp 0");
        getContentPane().add(label_pemasukan, new org.netbeans.lib.awtextra.AbsoluteConstraints(437, 92, 160, 20));

        panelLineChart.setLayout(new javax.swing.BoxLayout(panelLineChart, javax.swing.BoxLayout.LINE_AXIS));
        getContentPane().add(panelLineChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 200, 520, 240));

        tabel_aktivitas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "nama transaksi", "nama barang", "total harga", "tanggal"
            }
        ));
        tabel_aktivitas.setRowHeight(23);
        jScrollPane1.setViewportView(tabel_aktivitas);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 510, 560, 120));

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

        label_nama.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        label_nama.setForeground(new java.awt.Color(0, 0, 0));
        label_nama.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_nama.setText("Nama");
        getContentPane().add(label_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 280, 30));

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

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/DashbordMenu.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(DashboardFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashboardFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashboardFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashboardFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashboardFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_about;
    private javax.swing.JButton btn_dtakun;
    private javax.swing.JButton btn_dtbarang;
    private javax.swing.JButton btn_dtpembeli;
    private javax.swing.JButton btn_dtsupplier;
    private javax.swing.JButton btn_laporan;
    private javax.swing.JButton btn_logout;
    private javax.swing.JButton btn_transbeli;
    private javax.swing.JButton btn_transjual;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_nama;
    private javax.swing.JLabel label_pemasukan;
    private javax.swing.JLabel label_pengeluaran;
    private javax.swing.JPanel panelLineChart;
    private javax.swing.JTable tabel_aktivitas;
    // End of variables declaration//GEN-END:variables
}
