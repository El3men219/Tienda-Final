/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Terceros;

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

/**
 *
 * @author endy2
 */
public class usuario extends javax.swing.JFrame {

    /**
     * Creates new form usuario
     */
    public usuario() {
        initComponents();
        this.setLocationRelativeTo(null);
        TxtId.requestFocus(true); 
        Traer();
        llenarTabla();
        TraerTipo();
    }
    
    Conexion con;
    
    void Traer()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       ResultSet st,mw;
       Statement cn,lk;
       DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
       modelo.getDataVector().clear();
      try
         {
        cn = reg.createStatement();
        st =cn.executeQuery("SELECT * FROM `tercero` order by `IdTercero` ASC limit 500");
         while(st.next())
          {
                Vector v = new Vector();

              //  lk = reg.createStatement();//Personas
                //mw =lk.executeQuery("SELECT * FROM `Persona` WHERE `Id`='"+st.getInt(1)+"'  ");
                //mw.next();
                v.add(st.getString(1));//Id
                v.add(st.getString(2));//Nombre
               // v.add(mw.getString(2));//Apellido
                modelo.addRow(v);
                jTable1.setModel(modelo);
          }
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
     void TraerTipo()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `tipo_de_usuario` order by Id ASC limit 500");
         ComboTipo.removeAllItems();
          while(st.next())
          {
             ComboTipo.addItem(st.getString(2));
         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
    
     void llenarTabla()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        DefaultTableModel modelo = (DefaultTableModel) jTable2.getModel();
        modelo.getDataVector().clear();
        ResultSet st,kl,dp;
        Statement cn,lk,qa;
        
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM  `usuarios` order by `Tercero` ASC limit 500");
         

         while(st.next())
         {
            
            Vector v = new Vector();
            lk = reg.createStatement();
            kl =lk.executeQuery("SELECT `Descripcion` FROM `tipo_de_usuario` WHERE `Id` ='"+st.getString(4)+"' ");
            kl.next();
            
            qa = reg.createStatement();
            dp = qa.executeQuery("SELECT `Nombre` FROM `tercero` WHERE `idTercero` ='"+st.getString(1)+"' ");
            dp.next();
            
            v.add(dp.getString(1));
            v.add(st.getString(2));
            v.add(st.getString(3));
          //  v.add(kl.getString(1));
            modelo.addRow(v);
            jTable2.setModel(modelo);  
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
     
        int fila = jTable1.getSelectedRow();
       if(fila >=0)
       {
           TxtId.setText(jTable1.getValueAt(fila, 0).toString());
           TxtUsuario.setText(jTable1.getValueAt(fila, 2).toString());
       }

       }catch (HeadlessException ex){

             JOptionPane.showMessageDialog(null, "Error: "+ex+"\nInténtelo nuevamente", " .::Error En la Operacion::." ,JOptionPane.ERROR_MESSAGE);

       }     
    }
     
     
    
    void Insertar()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       ResultSet st,lk;
       Statement cn,kl;
       String Estado="";
       int TipoI=0;
       String Tipo="";
       Tipo=ComboTipo.getSelectedItem().toString();
         
        try
         {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT `Id` FROM `tipo_de_usuario` WHERE  `Descripcion`='"+Tipo+"' ");
         st.next();
         TipoI=Integer.parseInt(st.getString(1));
      
        if(jRadioActivo.isSelected()){
            Estado="Activo";
        }
        if(jRadioInactivo.isSelected()){
            Estado="Inactivo";
        }
          
           PreparedStatement ps = reg.prepareStatement("INSERT INTO `usuarios`(`Tercero`, `Usuario`, `Clave`, `tipo_usuario`, `Estado`) VALUES "
                   + "('"+TxtId.getText()+"','"+TxtUsuario.getText()+"','"+TxtContra.getText()+"','"+TipoI+"','"+Estado+"')");
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null, "Nuevo Usuario  agregada" );
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
     void Actualizar()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       try
         {
        PreparedStatement ps = reg.prepareStatement("UPDATE `usuarios` SET `clave` = '"+TxtContra.getText()+"' WHERE `usuarios`.`Id` ='"+TxtId.getText()+"'");
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Usuario modificado");
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
        PreparedStatement ps = reg.prepareStatement("DELETE FROM `usuarios` WHERE  Id='"+TxtId.getText()+"'");
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Usuario Eliminado");
          }catch(SQLException ex){
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
     void Limpiar()
    {
        TxtId.setText("");
        TxtUsuario.setText("");
        TxtId.requestFocus(true);
        TxtId.setBackground(Color.WHITE);
        TxtUsuario.setBackground(Color.WHITE);
        TxtContra.setText("");
        TxtBuscar.setText("");
        TxtContra.requestFocus(true);
        TxtBuscar.setBackground(Color.WHITE);
        JOptionPane.showMessageDialog(null, "Los campos has sido limpiardo");
    }
     
     void FiltradorTercero(String Valor)
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.getDataVector().clear();
        ResultSet st,kd,rt;
        Statement cn,dk,tr;

        try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM Tercero WHERE Nombre LIKE '%"+ Valor +"%' order by Nombre desc limit 500");
          while(st.next())
          {
                Vector v = new Vector();
                dk = reg.createStatement();
                kd = dk.executeQuery("SELECT  Id,Apellido FROM tipoarticulo WHERE Id='"+st.getString(1)+"'");
                kd.next();
                v.add(kd.getInt(1));//id
                v.add(st.getString(2));//nombre
                v.add(kd.getString(2));//apellido
                modelo.addRow(v);
                jTable1.setModel(modelo);
         }
        }
        catch (SQLException e)
        {

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

        jLabel1 = new javax.swing.JLabel();
        TxtContra = new javax.swing.JPasswordField();
        TxtId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        TxtUsuario = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jRadioActivo = new javax.swing.JRadioButton();
        jRadioInactivo = new javax.swing.JRadioButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        ComboTipo = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        TxtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        Insertar = new javax.swing.JButton();
        Actualizar = new javax.swing.JButton();
        Eliminar = new javax.swing.JButton();
        Menu = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Id: ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Usuario: ");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Registro de Usuario");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Estado:");

        jRadioActivo.setText("Activo");

        jRadioInactivo.setText("Inactivo");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Contraseña:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Tipo de Usuario:");

        ComboTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Buscar:");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Apellido"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        Insertar.setText("Insertar");
        Insertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InsertarActionPerformed(evt);
            }
        });

        Actualizar.setText("Actualizar");

        Eliminar.setText("Eliminar");

        Menu.setText("Menu");
        Menu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Usuario", "Tipo"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Insertar)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel1))
                                    .addGap(29, 29, 29)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(TxtId, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                                        .addComponent(TxtUsuario))
                                    .addGap(47, 47, 47)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel5))))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(Actualizar)
                                    .addGap(39, 39, 39)
                                    .addComponent(Eliminar))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(18, 18, 18)
                                    .addComponent(jRadioActivo)
                                    .addGap(33, 33, 33)
                                    .addComponent(jRadioInactivo))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TxtContra)
                            .addComponent(ComboTipo, 0, 138, Short.MAX_VALUE)
                            .addComponent(Menu)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtContra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(TxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(TxtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(ComboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jRadioActivo)
                            .addComponent(jRadioInactivo))
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Insertar)
                            .addComponent(Actualizar)
                            .addComponent(Eliminar)
                            .addComponent(Menu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void InsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InsertarActionPerformed
        if(TxtId.getText().equals("")){
        JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
        TxtId.requestFocus(true);
        TxtId.setBackground(Color.YELLOW);
        return;
        }
        else {
            TxtId.setBackground(Color.WHITE);
        }
        if(TxtUsuario.getText().equals("")){
        JOptionPane.showMessageDialog(null, "El campo de Usuario esta vacio");
        TxtUsuario.requestFocus(true);
        TxtUsuario.setBackground(Color.YELLOW);
        return;
        }
        else {
            TxtUsuario.setBackground(Color.WHITE);
        }
        if(TxtContra.getText().equals("")){
        JOptionPane.showMessageDialog(null, "El campo de Contraseña esta vacio");
        TxtContra.requestFocus(true);
        TxtContra.setBackground(Color.YELLOW);
        return;
        }
        else {
            TxtContra.setBackground(Color.WHITE);
        }
    Insertar();
    Traer();
    llenarTabla();
    Limpiar();
    Desconectar();
    }//GEN-LAST:event_InsertarActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
           int fila = jTable1.getSelectedRow();
           if (fila >= 0) {
           TxtId.setText(jTable1.getValueAt(fila, 0).toString());
           }
    }//GEN-LAST:event_jTable1MouseClicked

    private void MenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuActionPerformed
        Menu sc=new Menu();
        sc.setVisible(true);
        dispose();
    }//GEN-LAST:event_MenuActionPerformed

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
            java.util.logging.Logger.getLogger(usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new usuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Actualizar;
    private javax.swing.JComboBox<String> ComboTipo;
    private javax.swing.JButton Eliminar;
    private javax.swing.JButton Insertar;
    private javax.swing.JButton Menu;
    private javax.swing.JTextField TxtBuscar;
    private javax.swing.JPasswordField TxtContra;
    private javax.swing.JTextField TxtId;
    private javax.swing.JTextField TxtUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JRadioButton jRadioActivo;
    private javax.swing.JRadioButton jRadioInactivo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
