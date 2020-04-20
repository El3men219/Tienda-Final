
package Pago;

import Comprobante.*;
import Mantenimientos.*;
import Libre.Conexion;
import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class Pago extends javax.swing.JFrame {

    /**
     * Creates new form Departamento
     */
    public Pago() {
        initComponents();
        this.setLocationRelativeTo(null);
        TxtMonto.requestFocus(true); 
        TraerEstado();
        TraerFormaPago();
       
    }
           
    Conexion con;
    

       
    
    void Traer()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         DecimalFormat Formatea = new DecimalFormat(" RD$ ###,###,###.## ");
        float Monto=0,f=0;
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM  `Orden` WHERE `NumOrden`='"+TxtNumOrden.getText()+"' ");
          while(st.next())
          {    f=Float.parseFloat(st.getString(7));
               
                TxtMonto.setText(Formatea.format(f));
          }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
        void TraerMontoPendiente()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st,lk;
        Statement cn,kl;
         DecimalFormat Formatea = new DecimalFormat(" RD$ ###,###,###.## ");
        float Monto=0,f=0;
        int estado=0;
       try
        {
              kl = reg.createStatement();
              lk =kl.executeQuery("SELECT * FROM  `pago` WHERE `NumOrden`='"+TxtNumOrden.getText()+"'");
              while(lk.next())
          {   
              estado =Integer.parseInt(lk.getString(5));
              if(estado!=1)
              {
                f=Float.parseFloat(lk.getString(3));
                  System.out.println(""+f);
                TxtMonto.setText(Formatea.format(f));
                TxtMontoP.setText(Formatea.format(f)); 
              }
              else
              {
                f=Float.parseFloat(lk.getString(3));
                Monto=Float.parseFloat(lk.getString(4));
                  System.out.println(""+Monto);
                TxtMonto.setText(Formatea.format(f));
                TxtMontoP.setText(Formatea.format(Monto));
              }
          }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
     void TraerMonto()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st,lk;
        Statement cn,kl;
         DecimalFormat Formatea = new DecimalFormat(" RD$ ###,###,###.## ");
        float Monto=0,f=0;
        int estado=0;
       try
        {
              kl = reg.createStatement();
              lk =kl.executeQuery("SELECT * FROM  `Orden` WHERE `NumOrden`='"+TxtNumOrden.getText()+"'");
              while(lk.next())
          {   
               f=Float.parseFloat(lk.getString(7));
                 
                TxtMonto.setText(Formatea.format(f));
                TxtMontoP.setText(Formatea.format(f)); 
              }
          }
        
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
        void TraerFormaPago()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
        ComboPago.removeAllItems();
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `Forma_Pago` order by Id ASC limit 500");
          while(st.next())
          {
               ComboPago.addItem(st.getString(2));  
          }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
       void TraerEstado()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
        ComboEstado.removeAllItems();
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `estado_pago` order by Id ASC limit 500");
          while(st.next())
          {
               ComboEstado.addItem(st.getString(2));  
          }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
     void InsertarPago()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st,lk;
        Statement cn,kl;
        String Estado="",Forma="";
        Estado= ComboEstado.getSelectedItem().toString();
        Forma= ComboPago.getSelectedItem().toString();
        int Es=0,Fo=0;
        float Monto=0,Pend=0,Total=0;
        try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT `Id`  FROM `estado_pago` WHERE `Descripcion`='"+Estado+"' ");
          while(st.next())
          {
              kl = reg.createStatement();
              lk =kl.executeQuery("SELECT `Id`  FROM `forma_pago` WHERE `Descripcion`='"+Forma+"' ");
              lk.next();
              Es=Integer.parseInt(st.getString(1));
              Fo=Integer.parseInt(lk.getString(1));
          }
          String digits = TxtMonto.getText().replaceAll("[^0-9.]", "");
          String digits2 = TxtPago.getText().replaceAll("[^0-9.]", "");
          Monto= Float.parseFloat(digits);
          Total=Float.parseFloat(digits2);
          Pend=Monto-Total;
          if(Monto==Total)
          {
             PreparedStatement ps = reg.prepareStatement("INSERT INTO `pago`(`NumOrden`, `Forma_Pago`, `Monto`, `Monto_Restante`, `Estado`) VALUES "
                   + "('"+TxtNumOrden.getText()+"','"+Fo+"','"+Monto+"','0','"+Es+"')");
             ps.executeUpdate(); 
             JOptionPane.showMessageDialog(null, "El pago Fue realisado");
          }
          if(Monto<Total)
          {
              PreparedStatement ps = reg.prepareStatement("INSERT INTO `pago`(`NumOrden`, `Forma_Pago`, `Monto`, `Monto_Restante`, `Estado`) VALUES "
                   + "('"+TxtNumOrden.getText()+"','"+Fo+"','"+Monto+"','0','"+Es+"')");
             ps.executeUpdate(); 
             Pend*=-1;
             JOptionPane.showMessageDialog(null, "La devuelta es "+Pend);
          }
          if(Monto>Total)
          {
             PreparedStatement ps = reg.prepareStatement("INSERT INTO `pago`(`NumOrden`, `Forma_Pago`, `Monto`, `Monto_Restante`, `Estado`) VALUES "
                   + "('"+TxtNumOrden.getText()+"','"+Fo+"','"+Monto+"','"+Pend+"','"+Es+"')");
             ps.executeUpdate(); 
             Pend*=-1;
             JOptionPane.showMessageDialog(null, "EL balance pendiente es "+Pend); 
          }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    } 
       
    void Actualizar()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       try
         {
        PreparedStatement ps = reg.prepareStatement("UPDATE `estado_pago` SET `Descripcion` = '"+TxtMonto.getText()+"' WHERE `forma_pago`.`Id` ='"+TxtNumOrden.getText()+"'");
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "`estado_pago` modificado");
          }catch(SQLException ex){
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
    void Eliminar()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       try
         {
        PreparedStatement ps = reg.prepareStatement("DELETE FROM `estado_pago` WHERE  Id='"+TxtNumOrden.getText()+"'");
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, " `estado_pago` Eliminado");
          }catch(SQLException ex){
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
    void Limpiar()
    {
        TxtNumOrden.setText("");
        TxtMonto.setText("");
        TxtMontoP.setText("");
        TxtNumOrden.setText("");
        TxtPago.setText("");
        TxtMonto.requestFocus(true);
        TxtNumOrden.setBackground(Color.WHITE);
        TxtMonto.setBackground(Color.WHITE);
        TxtMontoP.setBackground(Color.WHITE);
        TxtNumOrden.setBackground(Color.WHITE);
        TxtPago.setBackground(Color.WHITE);
        JOptionPane.showMessageDialog(null, "Los campos has sido limpiardo");
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

        TxtNumOrden = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TxtMonto = new javax.swing.JTextField();
        BtInserta = new javax.swing.JButton();
        BtActualizar = new javax.swing.JButton();
        BtMenu = new javax.swing.JButton();
        BtEliminar = new javax.swing.JButton();
        ComboPago = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        TxtMontoP = new javax.swing.JTextField();
        TxtPago = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        ComboEstado = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TxtNumOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtNumOrdenActionPerformed(evt);
            }
        });
        TxtNumOrden.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TxtNumOrdenKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("NumOrden: ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Forma de Pago:");

        BtInserta.setText("Procesar");
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

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Monto: ");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Monto Pendiente: ");

        TxtPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtPagoActionPerformed(evt);
            }
        });
        TxtPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TxtPagoKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Pago: ");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Estado de Pago:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TxtNumOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TxtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ComboPago, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TxtPago, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TxtMontoP, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(BtInserta, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BtActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BtEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(TxtNumOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(ComboPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(TxtMontoP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(ComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtActualizar)
                    .addComponent(BtEliminar)
                    .addComponent(BtMenu)
                    .addComponent(BtInserta))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TxtNumOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtNumOrdenActionPerformed
        if(TxtNumOrden.getText().equals("")){
            JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
            TxtNumOrden.requestFocus(true);
            TxtNumOrden.setBackground(Color.YELLOW);
            return;
        }
        else {
            TxtNumOrden.setBackground(Color.WHITE);
        }
        TxtMonto.requestFocus(true);

       
       TraerMontoPendiente();
       TraerMonto();
    }//GEN-LAST:event_TxtNumOrdenActionPerformed

    private void TxtNumOrdenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtNumOrdenKeyReleased
        if(TxtNumOrden.getText().equals(""))
        {
            System.out.println("Digite un Id");
        }
        else
        {
           TraerMontoPendiente();
           Desconectar();
        }
    }//GEN-LAST:event_TxtNumOrdenKeyReleased

    private void BtInsertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtInsertaActionPerformed
        if(TxtNumOrden.getText().equals("")){
            JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
            TxtNumOrden.requestFocus(true);
            TxtNumOrden.setBackground(Color.YELLOW);
            return;
        }
        else {
            TxtNumOrden.setBackground(Color.WHITE);
        }
        if(TxtMonto.getText().equals("")){
            JOptionPane.showMessageDialog(null, "El campo de Descripcion esta vacio");
            TxtMonto.requestFocus(true);
            TxtMonto.setBackground(Color.YELLOW);
            return;
        }
        else {
            TxtMonto.setBackground(Color.WHITE);
        }
        InsertarPago();
        Limpiar();
        Traer();
        Desconectar();
    }//GEN-LAST:event_BtInsertaActionPerformed

    private void BtActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtActualizarActionPerformed
        if(TxtNumOrden.getText().equals("")){
            JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
            TxtNumOrden.requestFocus(true);
            TxtNumOrden.setBackground(Color.YELLOW);
            return;
        }
        else {
            TxtNumOrden.setBackground(Color.WHITE);
        }
        if(TxtMonto.getText().equals("")){
            JOptionPane.showMessageDialog(null, "El campo de Descripcion esta vacio");
            TxtMonto.requestFocus(true);
            TxtMonto.setBackground(Color.YELLOW);
            return;
        }
        else {
            TxtMonto.setBackground(Color.WHITE);
        }
        Actualizar();
        Limpiar();
        Traer();
     //   AutoIncremento();
        Desconectar();
    }//GEN-LAST:event_BtActualizarActionPerformed

    private void BtEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtEliminarActionPerformed
        if(TxtNumOrden.getText().equals("")){
            JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
            TxtNumOrden.requestFocus(true);
            TxtNumOrden.setBackground(Color.YELLOW);
            return;
        }
        else {
            TxtNumOrden.setBackground(Color.WHITE);
        }
        if(TxtMonto.getText().equals("")){
            JOptionPane.showMessageDialog(null, "El campo de Descripcion esta vacio");
            TxtMonto.requestFocus(true);
            TxtMonto.setBackground(Color.YELLOW);
            return;
        }
        else {
            TxtMonto.setBackground(Color.WHITE);
        }
        Eliminar();
        Limpiar();
        Traer();
        //AutoIncremento();
        Desconectar();
    }//GEN-LAST:event_BtEliminarActionPerformed

    private void TxtPagoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtPagoKeyReleased

       
    }//GEN-LAST:event_TxtPagoKeyReleased

    private void TxtPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtPagoActionPerformed
        DecimalFormat Formatea = new DecimalFormat(" RD$ ###,###,###.## ");
       if(TxtPago.getText().equals(""))
       {
            JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
            TxtNumOrden.requestFocus(true);
            TxtNumOrden.setBackground(Color.YELLOW);
            return;
       }
       else
       {
        float f= Float.parseFloat(TxtPago.getText());
        TxtPago.setText(Formatea.format(f));
       }
    }//GEN-LAST:event_TxtPagoActionPerformed

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
            java.util.logging.Logger.getLogger(Pago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pago.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Pago().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtActualizar;
    private javax.swing.JButton BtEliminar;
    private javax.swing.JButton BtInserta;
    private javax.swing.JButton BtMenu;
    private javax.swing.JComboBox<String> ComboEstado;
    private javax.swing.JComboBox<String> ComboPago;
    private javax.swing.JTextField TxtMonto;
    private javax.swing.JTextField TxtMontoP;
    private javax.swing.JTextField TxtNumOrden;
    private javax.swing.JTextField TxtPago;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    // End of variables declaration//GEN-END:variables
}
