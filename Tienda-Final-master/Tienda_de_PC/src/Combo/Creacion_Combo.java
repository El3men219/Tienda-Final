
package Combo;

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

/**
 *
 * @author score
 */
public class Creacion_Combo extends javax.swing.JFrame {


    public Creacion_Combo() {
        initComponents();
        this.setLocationRelativeTo(null);
         Traer();
         TraerArticulo();
        
    }
    
    Conexion con;
       
   
  
        void TraerArticulo()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       ResultSet st,nm,lk;
       Statement cn,mn,kl;
       DefaultTableModel modelo = (DefaultTableModel) Table1.getModel();
       modelo.getDataVector().clear();
       int Id=0;
        try
         {
          kl = reg.createStatement();
          lk = kl.executeQuery("SELECT  `Id` FROM `tipoarticulo` WHERE `Descripcion`='Combo'");
          lk.next();
        mn= reg.createStatement();
        nm= mn.executeQuery("SELECT `Id`,`IdTipoArticulo`, `Descripcion` FROM `articulo` WHERE `IdTipoArticulo`='"+lk.getInt(1)+"'");
      while(nm.next())
        {
           Vector v = new Vector();
           v.add(nm.getInt(1));
           v.add(nm.getString(3));
           cn = reg.createStatement();
           st = cn.executeQuery("SELECT  `Descripcion` FROM `tipoarticulo` WHERE `Id`='"+nm.getString(2)+"'");
           st.next();
           v.add(st.getString(1));
           modelo.addRow(v);
           Table1.setModel(modelo);
        }
        TxtModelo.requestFocus(true);
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
   void FiltradorMarca(String Valor)
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        DefaultTableModel modelo = (DefaultTableModel) Table1.getModel();
        modelo.getDataVector().clear();
        ResultSet st,kd;
        Statement cn,dk;
        
        try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `articulo` where CONCAT(`Id`, `IdTipoArticulo`, `Descripcion`) LIKE '%"+ Valor +"%' order by `Descripcion` desc limit 500");
          while(st.next())
          {
                Vector v = new Vector();
                v.add(st.getInt(1));
                v.add(st.getString(3));
                dk = reg.createStatement();
                kd = dk.executeQuery("SELECT  `Descripcion` FROM `tipoarticulo` WHERE `Id`='"+st.getString(2)+"'");
                kd.next();
                v.add(kd.getString(1));
                modelo.addRow(v);
                Table1.setModel(modelo);
         }
        }
        catch (SQLException e)
        {
            
        }
    }
    
        
    void selectTable()
    {
     try{
     
        int fila = Table1.getSelectedRow();
       if(fila >=0)
       {
           TxtId.setText(Table1.getValueAt(fila, 0).toString());
           TxtDescripcion.setText(Table1.getValueAt(fila, 1).toString());
       }
          TxtModelo.requestFocus(true);
       }catch (HeadlessException ex){

             JOptionPane.showMessageDialog(null, "Error: "+ex+"\nInténtelo nuevamente", " .::Error En la Operacion::." ,JOptionPane.ERROR_MESSAGE);

       }     
    }
    void selectTable2()
    {
     try{
     
        int fila = Table2.getSelectedRow();
       if(fila >=0)
       {
           TxtModelo.setText(Table2.getValueAt(fila, 2).toString());
       }
          TxtCantidad.requestFocus(true);
       }catch (HeadlessException ex){

             JOptionPane.showMessageDialog(null, "Error: "+ex+"\nInténtelo nuevamente", " .::Error En la Operacion::." ,JOptionPane.ERROR_MESSAGE);

       }     
    }
    void Traer()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        DefaultTableModel modelo = (DefaultTableModel) Table2.getModel();
        modelo.getDataVector().clear();
        DecimalFormat Formatea = new DecimalFormat(" RD$ ###,###,###.## ");
        ResultSet st,lk,mw,nm,as;
        Statement cn,kl,wm,mn,sa;
        double Costo=0,Precio=0;
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `detalle_articulo` order by id ASC limit 500");
          while(st.next())
          {
                kl= reg.createStatement();//marca 
                lk= kl.executeQuery("SELECT * FROM `articulo` WHERE `Id` ='"+st.getString(1)+"' ");
                lk.next();
                wm= reg.createStatement();//Unidad
                mw= wm.executeQuery("SELECT `Descripcion` FROM `Unidad` WHERE `Id` ='"+st.getString(3)+"' ");
                mw.next();
                Vector v = new Vector();
                v.add(lk.getString(1));//Id   
                v.add(lk.getString(3));//Marca
                v.add(st.getString(2));//Modelo
                v.add(mw.getString(1));  //Unidad
                v.add(st.getString(4));  //Cantidad
                v.add(st.getString(5));  //Fecha
                Costo=Double.parseDouble(st.getString(6));
                Precio=Double.parseDouble(st.getString(7));
                v.add(Formatea.format(Costo));
                v.add(Formatea.format(Precio));  
                modelo.addRow(v);
                Table2.setModel(modelo);
         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
    
    void AddArticulo()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        DefaultTableModel modelo = (DefaultTableModel) Table3.getModel();
        DecimalFormat Formatea = new DecimalFormat(" RD$ ###,###,###.## ");
        ResultSet st;
        Statement cn;
      try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `detalle_articulo` WHERE `Descripcion`='"+TxtModelo.getText()+"'");
          while(st.next())
          {
                Vector v = new Vector();
                v.add(st.getString(1));//Id
                v.add(st.getString(2));//Modelo
                v.add(TxtCantidad.getText());//Cantidad
                v.add(Formatea.format(st.getFloat(7)));  //Precio
                modelo.addRow(v);
                Table3.setModel(modelo);
         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
    void InsertarDetalle()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
        ResultSet st,lk;
        Statement cn,kl;
        int Un=0;
       try
         {
        
         String jItbis="",jPrecio="",JId="",JDe="",JUn="",JCa="";
         for(int i=0; i<Table3.getRowCount();i++)
         {
             JId= (Table3.getValueAt(i, 0).toString());
             JDe= (Table3.getValueAt(i, 1).toString());
             JCa= (Table3.getValueAt(i, 2).toString());
             jPrecio= (Table3.getValueAt(i, 3).toString());
         
             String digits2 = jPrecio.replaceAll("[^0-9.]", "");
             PreparedStatement ps = reg.prepareStatement("INSERT INTO `detalle_combo`(`Id`, `IdArticulo`, `Descripcion`, `cantidad`, `Precio`)"
                     + " VALUES ('"+TxtId.getText()+"','"+JId+"','"+JDe+"','"+JCa+"','"+digits2+"')");
           ps.executeUpdate();
          }
           
            TxtDescripcion.setEditable(true);
           
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
   void Limpiar()
    {
        TxtId.setText("");
        TxtModelo.setText("");
        TxtCantidad.setText("");
        TxtDescripcion.setText("");
        TxtModelo.requestFocus(true);
        TxtId.setBackground(Color.WHITE);
        TxtDescripcion.setBackground(Color.WHITE);
        TxtModelo.setBackground(Color.WHITE);
        TxtCantidad.setBackground(Color.WHITE);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TxtId = new javax.swing.JTextField();
        TxtDescripcion = new javax.swing.JTextField();
        BtInserta = new javax.swing.JButton();
        BtEliminar = new javax.swing.JButton();
        BtMenu = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table2 = new javax.swing.JTable();
        TxtModelo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        BtBuscar = new javax.swing.JButton();
        TxtCantidad = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        Table3 = new javax.swing.JTable();
        BtInserta1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Id: ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Descripcion:");

        TxtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtIdActionPerformed(evt);
            }
        });
        TxtId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TxtIdKeyReleased(evt);
            }
        });

        TxtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TxtDescripcionKeyReleased(evt);
            }
        });

        BtInserta.setText("Añadir");
        BtInserta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtInsertaActionPerformed(evt);
            }
        });

        BtEliminar.setText("Eliminar");
        BtEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtEliminarActionPerformed(evt);
            }
        });

        BtMenu.setText("Menu");
        BtMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtMenuActionPerformed(evt);
            }
        });

        Table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Descripcion", "Tipo"
            }
        ));
        Table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Table1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(Table1);

        Table2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Marca", "Modelo", "Unidad de medida", "Cantidad", "Fecha", "Costo", "Precio"
            }
        ));
        Table2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table2MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Table2MousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(Table2);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Modelo:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Cantidad:");

        BtBuscar.setText("Buscar");
        BtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarActionPerformed(evt);
            }
        });

        Table3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Descripcion", "Cantidad", "Precio"
            }
        ));
        Table3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table3MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Table3MousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(Table3);

        BtInserta1.setText("Procesar");
        BtInserta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtInserta1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TxtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addComponent(BtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addComponent(BtInserta1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TxtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(BtInserta, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(BtEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(BtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
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
                    .addComponent(BtBuscar)
                    .addComponent(BtInserta1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3)
                            .addComponent(TxtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtInserta)
                            .addComponent(BtMenu)
                            .addComponent(BtEliminar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtInsertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtInsertaActionPerformed
        if(TxtModelo.getText().equals("")){
        JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
        TxtModelo.requestFocus(true);
        TxtModelo.setBackground(Color.YELLOW);
        return;
        }
        else {
            TxtModelo.setBackground(Color.WHITE);
        }
        if(TxtCantidad.getText().equals("")){
        JOptionPane.showMessageDialog(null, "El campo de Descripcion esta vacio");
        TxtCantidad.requestFocus(true);
        TxtCantidad.setBackground(Color.YELLOW);
        return;
        }
        else {
            TxtCantidad.setBackground(Color.WHITE);
        }
          AddArticulo();
          TxtCantidad.setText("");
          TxtModelo.setText("");
    }//GEN-LAST:event_BtInsertaActionPerformed

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
        TxtDescripcion.requestFocus(true);  
        
    }//GEN-LAST:event_TxtIdActionPerformed

    private void TxtIdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtIdKeyReleased
          if(TxtId.getText().equals(""))
          {
              System.out.println("Digite un Id");
          }
          else
          {
              Traer();
              Desconectar();
          } 
    }//GEN-LAST:event_TxtIdKeyReleased

    private void BtEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtEliminarActionPerformed
     int Fila= Table3.getSelectedRow();
     DefaultTableModel modelo = (DefaultTableModel) Table3.getModel();
     if(Fila>=0)
     {
        modelo.removeRow(Fila);
        Limpiar();
        
        JOptionPane.showMessageDialog(null, "El articulo selecionado fue eliminado");
     }
     else
     {
         JOptionPane.showMessageDialog(null, "Tabla vacia o no seleciono ningun articulo");
     }
    }//GEN-LAST:event_BtEliminarActionPerformed

    private void Table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table1MouseClicked
      selectTable();
        
    }//GEN-LAST:event_Table1MouseClicked

    private void Table1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table1MousePressed

    }//GEN-LAST:event_Table1MousePressed

    private void Table2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table2MouseClicked
        selectTable2();
    }//GEN-LAST:event_Table2MouseClicked

    private void Table2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table2MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Table2MousePressed

    private void BtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtBuscarActionPerformed

    private void TxtDescripcionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtDescripcionKeyReleased
           FiltradorMarca(TxtDescripcion.getText());      
    }//GEN-LAST:event_TxtDescripcionKeyReleased

    private void BtMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtMenuActionPerformed
       Menu sc=new Menu();
        sc.setVisible(true);
        dispose();
    }//GEN-LAST:event_BtMenuActionPerformed

    private void Table3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Table3MouseClicked

    private void Table3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table3MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Table3MousePressed

    private void BtInserta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtInserta1ActionPerformed
        InsertarDetalle();
    }//GEN-LAST:event_BtInserta1ActionPerformed

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
            java.util.logging.Logger.getLogger(Creacion_Combo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Creacion_Combo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Creacion_Combo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Creacion_Combo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Creacion_Combo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtBuscar;
    private javax.swing.JButton BtEliminar;
    private javax.swing.JButton BtInserta;
    private javax.swing.JButton BtInserta1;
    private javax.swing.JButton BtMenu;
    private javax.swing.JTable Table1;
    private javax.swing.JTable Table2;
    private javax.swing.JTable Table3;
    private javax.swing.JTextField TxtCantidad;
    private javax.swing.JTextField TxtDescripcion;
    private javax.swing.JTextField TxtId;
    private javax.swing.JTextField TxtModelo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
