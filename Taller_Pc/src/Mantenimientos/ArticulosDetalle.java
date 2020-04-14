
package Mantenimientos;

import Libre.Conexion;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author score
 */
public class ArticulosDetalle extends javax.swing.JFrame {

    public ArticulosDetalle() {
        initComponents();
        this.setLocationRelativeTo(null);
        TxtId.requestFocus(true);
        Traer();
        cargarUnidadDeMedida();
        JOptionPane.showMessageDialog(null, "Digite el Id o el nombre del Articulo al cual desea agregar cantidad " );
  }

    Conexion con;

      void cargarUnidadDeMedida()
       {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM  `unidad_medida` order by id ASC limit 500");
         ComboUnida.removeAllItems();
          while(st.next())
          {
             ComboUnida.addItem(st.getString(2));
         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
       }
      
      void Traer()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        DefaultTableModel modelo = (DefaultTableModel) Table1.getModel();
        modelo.getDataVector().clear();
        ResultSet st,mw;
        Statement cn,lk;
        
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM  `articulosdetalle` order by id ASC limit 500");
          while(st.next())
          {
                Vector v = new Vector();
                v.add(st.getInt(1));
                v.add(st.getString(2));
                lk = reg.createStatement();
                mw = lk.executeQuery("SELECT Descripcion FROM  `unidad_medida` WHERE Id='"+st.getString(3)+"'");
                mw.next();
                v.add(mw.getString(1));
                v.add(st.getString(4));
                v.add("RD$ "+st.getString(5));
                v.add("RD$ "+st.getString(6));
                modelo.addRow(v);
                Table1.setModel(modelo);
         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
      void Limpiar()
      {
        TxtId.setText("");
        TxtDescripcion.setText("");
        TxtCantidad.setText("");
        TxtPrecioC.setText("");
        TxtId.requestFocus(true);
        TxtId.setBackground(Color.WHITE);
        TxtDescripcion.setBackground(Color.WHITE);
        TxtCantidad.setBackground(Color.WHITE);
        TxtPrecioC.setBackground(Color.WHITE);
        
      }
        void CargarArticulosId()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
        
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT `Descripcion` FROM `articulos` WHERE id='"+TxtId.getText()+"' ");
        
         do
         {
             if(st.next())
             {
             TxtDescripcion.setText(st.getString(1));   
             TxtCantidad.requestFocus(true);  
             }
             else
             {
             Limpiar();
             JOptionPane.showMessageDialog(null, "El Id Digitado no exites en la tabla articulos Presiones el boton atras y agreguelo " ); 
             }
         }while(st.next());
         }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
        
    }
        void CargarArticulosNombre()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
        
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT `Id` FROM `articulos` WHERE `Descripcion`='"+TxtDescripcion.getText()+"' ");
       do
         {
             if(st.next())
             {
             TxtId.setText(st.getString(1));   
             TxtCantidad.requestFocus(true);  
             }
             else
             {
             Limpiar();
             JOptionPane.showMessageDialog(null, "El Nombre Digitado no exites en la tabla articulos Presiones el boton atras y agreguelo " ); 
             }
         }while(st.next());
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
        
    }
        
    void InsertarArticulos()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       ResultSet st;
       Statement cn;
       String Unidad="";
       Unidad=ComboUnida.getSelectedItem().toString();
       float PrecioC=0, ITBIS=(float) 0.18,PrecioF=0,Sum=0;
       int Id=0;    
        try
         {
         PrecioC= Float.parseFloat(TxtPrecioC.getText());
         Sum=PrecioC*ITBIS;
         PrecioF=PrecioC+Sum;
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT `Id` FROM `unidad_medida` WHERE `Descripcion`= '"+Unidad+"'");
                  
          while(st.next())
          {
           Id= Integer.parseInt(st.getString(1));
          }
           PreparedStatement ps = reg.prepareStatement("INSERT INTO `articulosdetalle`(`Id`, `Descripcion`, `Unidad_Medida`, `Cantidad`, `Precio_C`, `Precio_V`) VALUES ('"+TxtId.getText()+"','"+TxtDescripcion.getText()+"','"+Id+"','"+TxtCantidad.getText()+"','"+TxtPrecioC.getText()+"','"+PrecioF+"')");
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null, "insertado nuevo Articulos" );
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
    void TextBoxVacio()
    {
     if(TxtId.getText().equals("")){
       JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
        TxtId.requestFocus(true);
         TxtId.setBackground(Color.YELLOW);
     return;
     }
     else {
        TxtId.setBackground(Color.WHITE);
     }
     if(TxtDescripcion.getText().equals("")){
         JOptionPane.showMessageDialog(null, "El campo de Descripcion esta vacio");
          TxtDescripcion.requestFocus(true);
           TxtDescripcion.setBackground(Color.YELLOW);
        return;
        }
        else {
           TxtDescripcion.setBackground(Color.WHITE);
        }
     if(TxtCantidad.getText().equals("")){
         JOptionPane.showMessageDialog(null, "El campo de Cantidad esta vacio");
          TxtCantidad.requestFocus(true);
           TxtCantidad.setBackground(Color.YELLOW);
        return;
        }
        else {
           TxtCantidad.setBackground(Color.WHITE);
        }
     if(TxtPrecioC.getText().equals("")){
         JOptionPane.showMessageDialog(null, "El campo de Cantidad esta vacio");
          TxtPrecioC.requestFocus(true);
           TxtPrecioC.setBackground(Color.YELLOW);
        return;
        }
        else {
           TxtPrecioC.setBackground(Color.WHITE);
        }
    }
    void Desconectar()
    {
         con.desconectar();
    }
    void ActualizarArticulo()
    {
        con = new Conexion();
       Connection reg = con.getConnection();
       ResultSet st;
       Statement cn;
        String Unidad="";
         Unidad=ComboUnida.getSelectedItem().toString();
          float PrecioC=(float) 0, ITBIS=(float) 0.18,PrecioF=(float) 0,Sum=(float) 0;
       int Id=0;    
        try
         {
         PrecioC= Float.parseFloat(TxtPrecioC.getText());
         Sum=PrecioC*ITBIS;
         PrecioF=PrecioC+Sum;
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT `Id` FROM `unidad_medida` WHERE `Descripcion`= '"+Unidad+"'");
         
          while(st.next())
          {
           Id= Integer.parseInt(st.getString(1));
          }
           PreparedStatement ps = reg.prepareStatement("UPDATE `articulosdetalle` SET `Unidad_Medida`='"+Id+"',`Cantidad`='"+TxtCantidad.getText()+"',`Precio_C`='"+TxtPrecioC.getText()+"',`Precio_V`='"+PrecioF+"' WHERE `Id`='"+TxtId.getText()+"')");
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null, "Actualizado Articulos" );
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }

      
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TxtId = new javax.swing.JTextField();
        TxtDescripcion = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        ComboUnida = new javax.swing.JComboBox<>();
        BtInsertar = new javax.swing.JButton();
        BtEliminar = new javax.swing.JButton();
        BtActualizar = new javax.swing.JButton();
        BtAtra = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        TxtCantidad = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table1 = new javax.swing.JTable();
        TxtPrecioC = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Id:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Descripcion:");

        TxtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtIdActionPerformed(evt);
            }
        });

        TxtDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtDescripcionActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Unidad de Medida:");

        BtInsertar.setText("Agregar");
        BtInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtInsertarActionPerformed(evt);
            }
        });

        BtEliminar.setText("Eliminar");

        BtActualizar.setText("Actualizar");
        BtActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtActualizarActionPerformed(evt);
            }
        });

        BtAtra.setText("Atras");
        BtAtra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtAtraActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Cantidad:");

        TxtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtCantidadActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Precio de Compra:");

        Table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Descripcion", "Unidad de medida", "Cantidad", "Precio de compra", "Precio de venta"
            }
        ));
        jScrollPane1.setViewportView(Table1);

        TxtPrecioC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtPrecioCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BtInsertar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BtEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BtAtra, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboUnida, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtPrecioC, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(108, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(TxtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(ComboUnida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(TxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(TxtPrecioC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtInsertar)
                    .addComponent(BtEliminar)
                    .addComponent(BtActualizar)
                    .addComponent(BtAtra))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TxtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtIdActionPerformed
        if(TxtId.getText().equals("")){
        JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
        TxtId.requestFocus(true);
        TxtId.setBackground(Color.YELLOW);
        return;
        }
        else {
            TxtId.setBackground(Color.WHITE);
        }
        CargarArticulosId();
        Desconectar();
    }//GEN-LAST:event_TxtIdActionPerformed

    private void TxtDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtDescripcionActionPerformed
        if(TxtDescripcion.getText().equals("")){
        JOptionPane.showMessageDialog(null, "El campo de Descripcion esta vacio");
        TxtDescripcion.requestFocus(true);
        TxtDescripcion.setBackground(Color.YELLOW);
        return;
        }
        else {
            TxtDescripcion.setBackground(Color.WHITE);
        }
        TxtCantidad.requestFocus(true); 
        CargarArticulosNombre();
        Desconectar();
    }//GEN-LAST:event_TxtDescripcionActionPerformed

    private void BtInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtInsertarActionPerformed
        TextBoxVacio();
        InsertarArticulos();
        Desconectar();
    }//GEN-LAST:event_BtInsertarActionPerformed

    private void BtAtraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtAtraActionPerformed
       this.dispose();
       Articulos windows = new Articulos();
       windows.setVisible(true);
       windows.setLocationRelativeTo(null);
    }//GEN-LAST:event_BtAtraActionPerformed

    private void TxtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtCantidadActionPerformed
        if(TxtCantidad.getText().equals("")){
        JOptionPane.showMessageDialog(null, "El campo de Cantidad esta vacio");
        TxtCantidad.requestFocus(true);
        TxtCantidad.setBackground(Color.YELLOW);
        return;
        }
        else {
            TxtCantidad.setBackground(Color.WHITE);
        }
        TxtPrecioC.requestFocus(true);
    }//GEN-LAST:event_TxtCantidadActionPerformed

    private void TxtPrecioCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtPrecioCActionPerformed
        if(TxtPrecioC.getText().equals("")){
        JOptionPane.showMessageDialog(null, "El campo de Cantidad esta vacio");
        TxtPrecioC.requestFocus(true);
        TxtPrecioC.setBackground(Color.YELLOW);
        return;
        }
        else {
            TxtPrecioC.setBackground(Color.WHITE);
        }
        TxtPrecioC.requestFocus(true);
    }//GEN-LAST:event_TxtPrecioCActionPerformed

    private void BtActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtActualizarActionPerformed
       Desconectar();
    }//GEN-LAST:event_BtActualizarActionPerformed

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
            java.util.logging.Logger.getLogger(ArticulosDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ArticulosDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ArticulosDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ArticulosDetalle.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ArticulosDetalle().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtActualizar;
    private javax.swing.JButton BtAtra;
    private javax.swing.JButton BtEliminar;
    private javax.swing.JButton BtInsertar;
    private javax.swing.JComboBox<String> ComboUnida;
    private javax.swing.JTable Table1;
    private javax.swing.JTextField TxtCantidad;
    private javax.swing.JTextField TxtDescripcion;
    private javax.swing.JTextField TxtId;
    private javax.swing.JTextField TxtPrecioC;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
