/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pramudya
 */
public class ListsupplierFrame extends javax.swing.JFrame {

    /**
     * Creates new form ListpelangganFrame
     */
    public ListsupplierFrame() {
        initComponents();
        this.text_caripelanggan.setBackground(new java.awt.Color(0,0,0,0));
        updateTable();
    }
    
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
        this.tabelPelanggan.setModel(tablemodel);
    }  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabelPelanggan = new javax.swing.JTable();
        text_caripelanggan = new javax.swing.JTextField();
        text_bgcaripelanggan = new javax.swing.JTextField();
        btn_cari = new javax.swing.JButton();
        btn_pilih = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelPelanggan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "nama", "alamat", "telepon"
            }
        ));
        tabelPelanggan.setRowHeight(28);
        jScrollPane1.setViewportView(tabelPelanggan);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 620, 340));

        text_caripelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_caripelangganActionPerformed(evt);
            }
        });
        text_caripelanggan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                text_caripelangganKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                text_caripelangganKeyTyped(evt);
            }
        });
        getContentPane().add(text_caripelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 360, 480, 40));

        text_bgcaripelanggan.setText("Cari Pelanggan");
        text_bgcaripelanggan.setEnabled(false);
        text_bgcaripelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_bgcaripelangganActionPerformed(evt);
            }
        });
        getContentPane().add(text_bgcaripelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 360, 480, 40));

        btn_cari.setText("Cari");
        btn_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cariActionPerformed(evt);
            }
        });
        getContentPane().add(btn_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 360, 70, 40));

        btn_pilih.setText("Pilih");
        btn_pilih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pilihActionPerformed(evt);
            }
        });
        getContentPane().add(btn_pilih, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 70, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/listPelanggan.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cari(){
         String[] columnNames = {"kode_supplier", "nama", "alamat", "telepon"};
        DefaultTableModel tablemodel = new DefaultTableModel(columnNames, 0);
        try {
            String findBarang = this.text_caripelanggan.getText();
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
            this.tabelPelanggan.setModel(tablemodel);        
    }
    
    private void btn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cariActionPerformed
        cari();
    }//GEN-LAST:event_btn_cariActionPerformed

    private void text_caripelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_caripelangganActionPerformed
        cari();
    }//GEN-LAST:event_text_caripelangganActionPerformed

    private void text_bgcaripelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_bgcaripelangganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_bgcaripelangganActionPerformed

    private void text_caripelangganKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_caripelangganKeyTyped
    }//GEN-LAST:event_text_caripelangganKeyTyped

    private void text_caripelangganKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_caripelangganKeyReleased
        if (text_caripelanggan.getText().isEmpty()){
            text_bgcaripelanggan.setVisible(true);
            text_bgcaripelanggan.setEnabled(false);
        }
        else{
            text_bgcaripelanggan.setVisible(false);
            text_bgcaripelanggan.setEnabled(false);
        }
        
    }//GEN-LAST:event_text_caripelangganKeyReleased

    private void btn_pilihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pilihActionPerformed
        String idPelanggan = this.tabelPelanggan.getValueAt(this.tabelPelanggan.getSelectedRow(), 0).toString();
        String namaPelanggan = this.tabelPelanggan.getValueAt(this.tabelPelanggan.getSelectedRow(), 1).toString();
        String alamatPelanggan = this.tabelPelanggan.getValueAt(this.tabelPelanggan.getSelectedRow(), 2).toString();
//        JOptionPane.showMessageDialog(this, (idPelanggan + namaPelanggan + alamatPelanggan));
        TransaksibeliFrame.addidcust(idPelanggan);
        TransaksibeliFrame.addnamecust(namaPelanggan);
        TransaksibeliFrame.addalamatcus(alamatPelanggan);
        dispose();
    }//GEN-LAST:event_btn_pilihActionPerformed

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
            java.util.logging.Logger.getLogger(ListsupplierFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListsupplierFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListsupplierFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListsupplierFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListsupplierFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cari;
    public javax.swing.JButton btn_pilih;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable tabelPelanggan;
    private javax.swing.JTextField text_bgcaripelanggan;
    private javax.swing.JTextField text_caripelanggan;
    // End of variables declaration//GEN-END:variables
}
