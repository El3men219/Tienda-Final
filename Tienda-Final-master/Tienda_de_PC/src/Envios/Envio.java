
package Envios;

import Mantenimientos.*;
import Libre.Conexion;
import Libre.Menu;
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

public class Envio extends javax.swing.JFrame {

    /**
     * Creates new form Provincia
     */
    public Envio() {
        initComponents();
        this.setLocationRelativeTo(null);
        TxtEmpleado.requestFocus(true); 
        Traer();
        TraerDireccion();
        TraerVehiculo();
        TraerEstado();
        
    }
    
    Conexion con;
    
     void TraerDireccion()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `direccion` order by id  ASC limit 500");
         ComboDireccion.removeAllItems();
          while(st.next())
          {
             ComboDireccion.addItem(st.getString(6));
         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
      void TraerVehiculo()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM  `Vehiculos` order by id  ASC limit 500");
         ComboVehiculo.removeAllItems();
          while(st.next())
          {
             ComboVehiculo.addItem(st.getString(1));
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
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `estado_envio` order by id  ASC limit 500");
         ComboEstado.removeAllItems();
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
       void selectTable()
    {
     try{
     
        int fila = Table1.getSelectedRow();
       if(fila >=0)
       {
           TxtNumOrden.setText(Table1.getValueAt(fila, 0).toString());
           TxtEmpleado.setText(Table1.getValueAt(fila, 1).toString());
       }

       }catch (HeadlessException ex){

             JOptionPane.showMessageDialog(null, "Error: "+ex+"\nInténtelo nuevamente", " .::Error En la Operacion::." ,JOptionPane.ERROR_MESSAGE);

       }     
    }
    
    void Traer()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        DefaultTableModel modelo = (DefaultTableModel) Table1.getModel();
        modelo.getDataVector().clear();
        DecimalFormat Formatea = new DecimalFormat(" RD$ ###,###,###.## ");
        ResultSet st,lk;
        Statement cn,kl;
        
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM  `envio` order by `NumOrden` ASC limit 500");
          while(st.next())
          {
                Vector v = new Vector();
                kl = reg.createStatement();
                lk =kl.executeQuery("SELECT * FROM `Estado_Envio` WHERE Id='"+st.getInt(7)+"'");
                lk.next();   
                v.add(st.getInt(1));
                v.add(st.getString(2));
                v.add(st.getString(3));
                v.add(Formatea.format(st.getFloat(4)));
                v.add(lk.getString(2));
                modelo.addRow(v);
                Table1.setModel(modelo);
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
        ResultSet st,lk,kd;
        Statement cn,kl,dk;
        String Vehiculo="",Direccion="",Estado="";
       int Ve=0,Te=0,Di=0,Es=0;
       float Co=0;
       Vehiculo=ComboVehiculo.getSelectedItem().toString();
       Direccion=ComboDireccion.getSelectedItem().toString();
       Estado=ComboEstado.getSelectedItem().toString();
       Co=Float.parseFloat(TxtCosto.getText());
        try
         {
             cn = reg.createStatement();
             st =cn.executeQuery("SELECT * FROM `Orden` WHERE `NumOrden`= '"+TxtNumOrden.getText()+"'");
          while(st.next())
          {
                Vector v = new Vector();
                kl = reg.createStatement();
                lk =kl.executeQuery("SELECT `Id` FROM `Estado_Envio` WHERE `Descripcion`='"+Estado+"'");
                lk.next();
                dk = reg.createStatement();
                kd = dk.executeQuery("SELECT * FROM `direccion` WHERE `Descripcion`='"+Direccion+"'");
                kd.next();
                Es=lk.getInt(1);
                Ve=Integer.parseInt(Vehiculo);
                Te=st.getInt(6);
                Di=kd.getInt(1);
          }
           PreparedStatement ps = reg.prepareStatement("INSERT INTO `envio`(`NumOrden`, `Empleado`, `Vehiculo`, `Costo`, `Tercero`, `Direccion`, `Estado`) "
                   + "VALUES ('"+TxtNumOrden.getText()+"','"+TxtEmpleado.getText()+"','"+Ve+"','"+Co+"','"+Te+"','"+Di+"','"+Es+"')");
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null, "Nuevo `envio` agregado" );
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
    void Actualizar()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
        ResultSet st,lk,kd;
        Statement cn,kl,dk;
        String Vehiculo="",Direccion="",Estado="";
       int Ve=0,Te=0,Di=0,Es=0;
       float Co=0;
       Vehiculo=ComboVehiculo.getSelectedItem().toString();
       Direccion=ComboDireccion.getSelectedItem().toString();
       Estado=ComboEstado.getSelectedItem().toString();
       Co=Float.parseFloat(TxtCosto.getText());
        try
         {
             cn = reg.createStatement();
             st =cn.executeQuery("SELECT * FROM `Orden` WHERE `NumOrden`= '"+TxtNumOrden.getText()+"'");
          while(st.next())
          {
                Vector v = new Vector();
                kl = reg.createStatement();
                lk =kl.executeQuery("SELECT `Id` FROM `Estado_Envio` WHERE `Descripcion`='"+Estado+"'");
                lk.next();
                dk = reg.createStatement();
                kd = dk.executeQuery("SELECT * FROM `direccion` WHERE `Descripcion`='"+Direccion+"'");
                kd.next();
                Es=lk.getInt(1);
                Ve=Integer.parseInt(Vehiculo);
                Te=st.getInt(6);
                Di=kd.getInt(1);
          }
        PreparedStatement ps = reg.prepareStatement("UPDATE `envio` SET `Empleado`='"+TxtEmpleado.getText()+"',`Vehiculo`='"+Ve+"',`Costo`='"+Co+"',`Direccion`='"+Di+"',`Estado`='"+Es+"' WHERE `envio`.`NumOrden` ='"+TxtNumOrden.getText()+"'");
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "`envio` modificado");
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
        PreparedStatement ps = reg.prepareStatement("DELETE FROM `envio` WHERE  NumOrden='"+TxtNumOrden.getText()+"'");
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "`envio` Eliminado");
          }catch(SQLException ex){
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
    void Limpiar()
    {
        TxtNumOrden.setText("");
        TxtEmpleado.setText("");
        TxtEmpleado.requestFocus(true);
        TxtNumOrden.setBackground(Color.WHITE);
        TxtEmpleado.setBackground(Color.WHITE);
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
        TxtEmpleado = new javax.swing.JTextField();
        BtInserta = new javax.swing.JButton();
        BtActualizar = new javax.swing.JButton();
        BtMenu = new javax.swing.JButton();
        BtEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        ComboVehiculo = new javax.swing.JComboBox<>();
        TxtCosto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        ComboDireccion = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        ComboEstado = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

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
        BtMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtMenuActionPerformed(evt);
            }
        });

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
                "NumOrden", "Empleado", "Vehiculo", "Costo", "Fase"
            }
        ));
        Table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table1);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("NumOrden: ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Empleado:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Vehiculo:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Costo:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Direccion:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Estado:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TxtNumOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(58, 58, 58)
                                .addComponent(BtActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(BtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(ComboDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(TxtEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ComboVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(TxtCosto)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BtInserta, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(BtEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(22, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(TxtNumOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtActualizar)
                    .addComponent(BtMenu))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(TxtEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(ComboVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(TxtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtInserta)
                            .addComponent(BtEliminar))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(ComboDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(ComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
        TxtEmpleado.requestFocus(true);
        Desconectar();
    }//GEN-LAST:event_TxtNumOrdenActionPerformed

    private void TxtNumOrdenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtNumOrdenKeyReleased
        if(TxtNumOrden.getText().equals(""))
        {
            System.out.println("Digite un Id");
        }
        else
        {
            Traer();
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
        if(TxtEmpleado.getText().equals("")){
            JOptionPane.showMessageDialog(null, "El campo de Descripcion esta vacio");
            TxtEmpleado.requestFocus(true);
            TxtEmpleado.setBackground(Color.YELLOW);
            return;
        }
        else {
            TxtEmpleado.setBackground(Color.WHITE);
        }
        Insertar();
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
        if(TxtEmpleado.getText().equals("")){
            JOptionPane.showMessageDialog(null, "El campo de Descripcion esta vacio");
            TxtEmpleado.requestFocus(true);
            TxtEmpleado.setBackground(Color.YELLOW);
            return;
        }
        else {
            TxtEmpleado.setBackground(Color.WHITE);
        }
        Actualizar();
        Limpiar();
        Traer();
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
       
        Eliminar();
        Limpiar();
        Traer();
        Desconectar();
    }//GEN-LAST:event_BtEliminarActionPerformed

    private void Table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table1MouseClicked
       selectTable();
    }//GEN-LAST:event_Table1MouseClicked

    private void BtMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtMenuActionPerformed
       Menu sc=new Menu();
       sc.setVisible(true);
            dispose();
    }//GEN-LAST:event_BtMenuActionPerformed

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
            java.util.logging.Logger.getLogger(Envio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Envio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Envio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Envio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Envio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtActualizar;
    private javax.swing.JButton BtEliminar;
    private javax.swing.JButton BtInserta;
    private javax.swing.JButton BtMenu;
    private javax.swing.JComboBox<String> ComboDireccion;
    private javax.swing.JComboBox<String> ComboEstado;
    private javax.swing.JComboBox<String> ComboVehiculo;
    private javax.swing.JTable Table1;
    private javax.swing.JTextField TxtCosto;
    private javax.swing.JTextField TxtEmpleado;
    private javax.swing.JTextField TxtNumOrden;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
