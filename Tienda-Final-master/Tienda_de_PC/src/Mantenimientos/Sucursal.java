
package Mantenimientos;

import Libre.Conexion;
import Libre.Menu;
import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class Sucursal extends javax.swing.JFrame {

    /**
     * Creates new form Sucursal
     */
    public Sucursal() {
        initComponents();
        this.setLocationRelativeTo(null);
        TxtDescripcion.requestFocus(true); 
        Traer();
        TraeTipo();
        TraerDireccion();
        TraerEstado();
        AutoIncremento();
    }
    
    Conexion con;
    
    void AutoIncremento()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       ResultSet st;
       Statement cn;
       int Id=0;
        try
         {
        cn = reg.createStatement();
        st =cn.executeQuery("SELECT MAX(`Id`) FROM `sucursall`");
         while(st.next())
          {
         Id=Integer.parseInt(String.valueOf(st.getInt(1)));
         Id+=1;
        if(Id<=1)
         {
             System.out.println(""+Id);
             Id=1;
             TxtId.setText(String.valueOf(Id));
         }
        else 
        {
            TxtId.setText(String.valueOf(Id));
        }}  
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
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

       }catch (HeadlessException ex){

             JOptionPane.showMessageDialog(null, "Error: "+ex+"\nInténtelo nuevamente", " .::Error En la Operacion::." ,JOptionPane.ERROR_MESSAGE);

       }     
    }
       
        void TraeTipo()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `tiposucursal` order by Id ASC limit 500");
         ComboSucursal.removeAllItems();
          while(st.next())
          {
             ComboSucursal.addItem(st.getString(2));
         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
        
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
         
          void TraerEstado()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `estado_sucursal` order by Id ASC limit 500");
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
    
    void Traer()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        DefaultTableModel modelo = (DefaultTableModel) Table1.getModel();
        modelo.getDataVector().clear();
        ResultSet st,kl,lq,la;
        Statement cn,lk,ql,al;
        
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM  `sucursall` order by id ASC limit 500");
          while(st.next())
          {
                Vector v = new Vector();
                
                ql = reg.createStatement();
                lq = ql.executeQuery("SELECT `Descripcion` FROM `direccion` WHERE `id` ='"+st.getString(3)+"' ");
                lq.next();
                
                lk = reg.createStatement();
                kl = lk.executeQuery("SELECT `Descripcion` FROM `tiposucursal` WHERE `Id` ='"+st.getString(4)+"' ");
                kl.next();
                
                
                
                al = reg.createStatement();
                la = al.executeQuery("SELECT `Descripcion` FROM `estado_sucursal` WHERE `Id` ='"+st.getString(5)+"' ");
                la.next();
                
                v.add(st.getInt(1));
                v.add(st.getString(2));
                v.add(lq.getString(1));
                v.add(kl.getString(1));
                v.add(la.getString(1));
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
       ResultSet st,kl,ms;
       Statement cn,lk,qa;
       int TipoI=0,DireccionI=0,EstadoI=0;
       String Tipo="",Direccion="",Estado="";
       Tipo=ComboSucursal.getSelectedItem().toString();
       Direccion=ComboDireccion.getSelectedItem().toString();
       Estado=ComboEstado.getSelectedItem().toString();
       
        try
         {
            lk = reg.createStatement();
            kl = lk.executeQuery("SELECT `id` FROM `direccion` WHERE `Descripcion`='"+Direccion+"' ");
            kl.next() ;
            DireccionI=Integer.parseInt(kl.getString(1)); 
             
            cn = reg.createStatement();
            st = cn.executeQuery("SELECT `Id` FROM `tiposucursal` WHERE `Descripcion`='"+Tipo+"' ");
            st.next() ;
            TipoI=Integer.parseInt(st.getString(1));
            
            
            
            qa = reg.createStatement();
            ms = qa.executeQuery("SELECT `Id` FROM `estado_sucursal` WHERE `Descripcion`='"+Estado+"' ");
            ms.next() ;
            EstadoI=Integer.parseInt(ms.getString(1));
             
           PreparedStatement ps = reg.prepareStatement("INSERT INTO `sucursall` (`Id`, `Descripcion`, `direccion`, `Tipo_Sucursal`, `Estado`) "
                   + "VALUES ('"+TxtId.getText()+"','"+TxtDescripcion.getText()+"','"+DireccionI+"','"+TipoI+"','"+EstadoI+"')");
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null, "Nueva Sucursal agregado" );
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
    void Actualizar()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       ResultSet st,kl,ms;
       Statement cn,lk,qa;
       int TipoI=0,DireccionI=0,EstadoI=0;
       String Tipo="",Direccion="",Estado="";
       Tipo=ComboSucursal.getSelectedItem().toString();
       Direccion=ComboDireccion.getSelectedItem().toString();
       Estado=ComboEstado.getSelectedItem().toString();
       
       try
         {
             lk = reg.createStatement();
            kl = lk.executeQuery("SELECT `id` FROM `direccion` WHERE `Descripcion`='"+Direccion+"' ");
            kl.next() ;
            DireccionI=Integer.parseInt(kl.getString(1)); 
             
            cn = reg.createStatement();
            st = cn.executeQuery("SELECT `Id` FROM `tiposucursal` WHERE `Descripcion`='"+Tipo+"' ");
            st.next() ;
            TipoI=Integer.parseInt(st.getString(1));
            
            
            
            qa = reg.createStatement();
            ms = qa.executeQuery("SELECT `Id` FROM `estado_sucursal` WHERE `Descripcion`='"+Estado+"' ");
            ms.next() ;
            EstadoI=Integer.parseInt(ms.getString(1));
             
             
        PreparedStatement ps = reg.prepareStatement("UPDATE `sucursall` SET `Descripcion`='"+TxtDescripcion.getText()+"',`direccion`='"+DireccionI+"',`Tipo_Sucursal`='"+TipoI+"',`Estado`='"+EstadoI+"' WHERE  `sucursall`.`Id` ='"+TxtId.getText()+"'");
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "sucursal modificado");
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
        PreparedStatement ps = reg.prepareStatement("DELETE FROM `sucursall` WHERE  Id='"+TxtId.getText()+"'");
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, " sucursal Eliminado");
          }catch(SQLException ex){
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
    void Limpiar()
    {
        TxtId.setText("");
        TxtDescripcion.setText("");
        TxtDescripcion.requestFocus(true);
        TxtId.setBackground(Color.WHITE);
        TxtDescripcion.setBackground(Color.WHITE);
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
        TxtId = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        ComboDireccion = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        TxtDescripcion = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        ComboSucursal = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        ComboEstado = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        Table1 = new javax.swing.JTable();
        BtInserta = new javax.swing.JButton();
        BtActualizar = new javax.swing.JButton();
        BtEliminar = new javax.swing.JButton();
        BtMenu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Id: ");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Direccion:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Descripcion: ");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Tipo_Sucursal: ");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Estado:");

        Table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Direccion", "Tipo", "Estado"
            }
        ));
        Table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(Table1);

        BtInserta.setText("Insertar");
        BtInserta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtInsertaActionPerformed(evt);
            }
        });

        BtActualizar.setText("Actualizar");

        BtEliminar.setText("Eliminar");

        BtMenu.setText("Menu");
        BtMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtMenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BtInserta, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BtActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(BtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ComboSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(TxtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ComboDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(ComboDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(TxtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(ComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(ComboSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtActualizar)
                    .addComponent(BtInserta)
                    .addComponent(BtEliminar)
                    .addComponent(BtMenu))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtInsertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtInsertaActionPerformed
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
        Insertar();
        Limpiar();
        Traer();
        AutoIncremento();
        Desconectar();
    }//GEN-LAST:event_BtInsertaActionPerformed

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
            java.util.logging.Logger.getLogger(Sucursal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sucursal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sucursal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sucursal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sucursal().setVisible(true);
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
    private javax.swing.JComboBox<String> ComboSucursal;
    private javax.swing.JTable Table1;
    private javax.swing.JTextField TxtDescripcion;
    private javax.swing.JTextField TxtId;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
