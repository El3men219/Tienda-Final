
package Envios;

import Mantenimientos.*;
import Libre.Conexion;
import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Seguro_Vehiculo extends javax.swing.JFrame {

    /**
     * Creates new form Provincia
     */
    public Seguro_Vehiculo() {
        initComponents();
        this.setLocationRelativeTo(null);
        Traer();
       TraerMatricula();
    }
    
    Conexion con;
 
    
    void Traer()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        DefaultTableModel modelo = (DefaultTableModel) Table1.getModel();
        modelo.getDataVector().clear();
        ResultSet st,lk;
        Statement cn,kl;
        
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM  `seguro_vehiculos` order by `Vehiculo` ASC limit 500");
          while(st.next())
          {
                Vector v = new Vector();
               kl = reg.createStatement();
               lk =kl.executeQuery("SELECT DATEDIFF(NOW(),'"+st.getString(2)+"')");
               lk.next();
                v.add(st.getInt(1));
                v.add(st.getString(2));
                v.add(lk.getString(1));
                modelo.addRow(v);
                Table1.setModel(modelo);
         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
    void TraerMatricula()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `Vehiculos` order by Id ASC limit 500");
         ComboMatricula.removeAllItems();
          while(st.next())
          {
             ComboMatricula.addItem(st.getString(1));
         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
    void Insertar()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
        String Matricula="";
        SimpleDateFormat Formato = new SimpleDateFormat("yyyy-MM-dd");
        Matricula= ComboMatricula.getSelectedItem().toString();
        try
         {
           PreparedStatement ps = reg.prepareStatement("INSERT INTO `seguro_vehiculos`(`Vehiculo`, `Fecha_Vencimiento`)  VALUES ('"+Integer.parseInt(Matricula)+"','"+Formato.format(FechaV.getDate())+"')");
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null, "Nuevo `seguro_vehiculos` agregado" );
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
    void Actualizar()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
        String Matricula="";
        SimpleDateFormat Formato = new SimpleDateFormat("yyyy-mm-dd");
        Matricula= ComboMatricula.getSelectedItem().toString();
       try
         {
        PreparedStatement ps = reg.prepareStatement("UPDATE `seguro_vehiculos` SET `Fecha_Vencimiento`='"+Formato.format(FechaV.getDate())+"'  WHERE `seguro_vehiculos`.`Id`='"+Integer.parseInt(Matricula)+"' ");
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "`seguro_vehiculos` modificado");
          }catch(SQLException ex){
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
    void Eliminar()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       String Matricula="";
       Matricula= ComboMatricula.getSelectedItem().toString();
       try
         {
        PreparedStatement ps = reg.prepareStatement("DELETE FROM `seguro_vehiculos` WHERE  Id='"+Integer.parseInt(Matricula)+"'");
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "`seguro_vehiculos` Eliminado");
          }catch(SQLException ex){
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    

    
    void Desconectar()
    {
         con.desconectar();
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BtInserta = new javax.swing.JButton();
        BtActualizar = new javax.swing.JButton();
        BtMenu = new javax.swing.JButton();
        BtEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        FechaV = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        ComboMatricula = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        BtInserta.setText("Insertar");
        BtInserta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtInsertaActionPerformed(evt);
            }
        });

        BtActualizar.setText("Actualizar");
        BtActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtActualizarActionPerformed(evt);
            }
        });

        BtMenu.setText("Menu");

        BtEliminar.setText("Eliminar");
        BtEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtEliminarActionPerformed(evt);
            }
        });

        Table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Matricula", "Fecha De Vencimiento", "Dia Restante"
            }
        ));
        Table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table1);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Matricula:");

        FechaV.setDateFormatString("yyyy-MM-dd");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Fecha de Vencimiento:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ComboMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(FechaV, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BtActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(61, 61, 61)
                                .addComponent(BtInserta, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 17, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtActualizar)
                            .addComponent(BtMenu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtInserta)
                            .addComponent(BtEliminar)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(ComboMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(FechaV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtInsertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtInsertaActionPerformed

        Insertar();
        Traer();
        Desconectar();
    }//GEN-LAST:event_BtInsertaActionPerformed

    private void BtActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtActualizarActionPerformed
        Actualizar();
        Traer();
        Desconectar();
    }//GEN-LAST:event_BtActualizarActionPerformed

    private void BtEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtEliminarActionPerformed
        Eliminar();
        Traer();
        Desconectar();
    }//GEN-LAST:event_BtEliminarActionPerformed

    private void Table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table1MouseClicked
       
    }//GEN-LAST:event_Table1MouseClicked

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
            java.util.logging.Logger.getLogger(Seguro_Vehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Seguro_Vehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Seguro_Vehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Seguro_Vehiculo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Seguro_Vehiculo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtActualizar;
    private javax.swing.JButton BtEliminar;
    private javax.swing.JButton BtInserta;
    private javax.swing.JButton BtMenu;
    private javax.swing.JComboBox<String> ComboMatricula;
    private com.toedter.calendar.JDateChooser FechaV;
    private javax.swing.JTable Table1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
