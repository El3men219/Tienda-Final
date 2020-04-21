
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


public class Ciudad extends javax.swing.JFrame {

    /**
     * Creates new form Ciudad
     */
    public Ciudad() {
        initComponents();
        this.setLocationRelativeTo(null);
        TxtDescripcion.requestFocus(true);
        Traer();
        AutoIncremento();
        TraerMunicipio();
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
        st =cn.executeQuery("SELECT MAX(`Id`) FROM `ciudad`");
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
       
       void TraerMunicipio()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `Municipio` order by Id ASC limit 500");
         ComboMunicipio.removeAllItems();
          while(st.next())
          {
             ComboMunicipio.addItem(st.getString(3));
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
        ResultSet st,kl;
        Statement cn,lk;
        
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM  `ciudad` order by id ASC limit 500");
          while(st.next())
          {
                Vector v = new Vector();
                lk = reg.createStatement();
                kl = lk.executeQuery("SELECT `Descripcion` FROM `Municipio` WHERE `Id` ='"+st.getString(2)+"' ");
                kl.next();
                
                v.add(st.getInt(1));
                v.add(kl.getString(1));
                v.add(st.getString(3));
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
       ResultSet st,lk;
       Statement cn,kl;
       int provi=0;
       String prov="";
       prov=ComboMunicipio.getSelectedItem().toString();
       
        try
         {
             cn = reg.createStatement();
             st = cn.executeQuery("SELECT `Id` FROM `Municipio` WHERE `Descripcion`='"+prov+"' ");
             st.next() ;
             provi=Integer.parseInt(st.getString(1));
             
             
           PreparedStatement ps = reg.prepareStatement("INSERT INTO `ciudad`(`Id`, `Municipio`, `Descripcion`) "
                   + "VALUES ('"+TxtId.getText()+"','"+provi+"','"+TxtDescripcion.getText()+"')");
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null, "Nueva Ciudad agregado" );
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
        PreparedStatement ps = reg.prepareStatement("UPDATE `ciudad` SET `Descripcion` = '"+TxtDescripcion.getText()+"' WHERE `ciudad`.`Id` ='"+TxtId.getText()+"'");
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "ciudad modificado");
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
        PreparedStatement ps = reg.prepareStatement("DELETE FROM `ciudad` WHERE  Id='"+TxtId.getText()+"'");
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, " ciudad Eliminado");
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

        TxtId = new javax.swing.JTextField();
        TxtDescripcion = new javax.swing.JTextField();
        BtInserta = new javax.swing.JButton();
        BtActualizar = new javax.swing.JButton();
        BtMenu = new javax.swing.JButton();
        BtEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        ComboMunicipio = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

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
                "Id", "Municipio", "Descripcion"
            }
        ));
        Table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table1);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Id: ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Descripcion:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Municipio:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(TxtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(31, 31, 31)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(BtActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(BtInserta, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BtEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ComboMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtMenu)
                    .addComponent(BtInserta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtActualizar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(TxtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BtEliminar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(ComboMunicipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        TxtDescripcion.requestFocus(true);

        Desconectar();
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

    private void BtActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtActualizarActionPerformed
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
        Actualizar();
        Limpiar();
        Traer();
        Desconectar();
    }//GEN-LAST:event_BtActualizarActionPerformed

    private void BtEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtEliminarActionPerformed
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
            java.util.logging.Logger.getLogger(Ciudad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ciudad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ciudad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ciudad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Ciudad().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtActualizar;
    private javax.swing.JButton BtEliminar;
    private javax.swing.JButton BtInserta;
    private javax.swing.JButton BtMenu;
    private javax.swing.JComboBox<String> ComboMunicipio;
    private javax.swing.JTable Table1;
    private javax.swing.JTextField TxtDescripcion;
    private javax.swing.JTextField TxtId;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
