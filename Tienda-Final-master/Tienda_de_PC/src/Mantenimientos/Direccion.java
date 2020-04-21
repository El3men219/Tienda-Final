
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


public class Direccion extends javax.swing.JFrame {

    /**
     * Creates new form Direccion
     */
    public Direccion() {
        initComponents();
        this.setLocationRelativeTo(null);
        TxtDescripcion.requestFocus(true); 
        Traer();
        AutoIncremento();
        TraerProvincia();
        TraerCiudad();
        TraerMunicipio();
        TraerSector();
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
        st =cn.executeQuery("SELECT MAX(`Id`) FROM `direccion`");
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
       
        void TraerProvincia()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `provincia` order by Id ASC limit 500");
         ComboProvincia.removeAllItems();
          while(st.next())
          {
             ComboProvincia.addItem(st.getString(2));
         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
        
     void TraerCiudad()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `Ciudad` order by id ASC limit 500");
         ComboCiudad.removeAllItems();
          while(st.next())
          {
             ComboCiudad.addItem(st.getString(3));
         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
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
         st =cn.executeQuery("SELECT * FROM `municipio` order by id ASC limit 500");
         ComboMuncipio.removeAllItems();
          while(st.next())
          {
             ComboMuncipio.addItem(st.getString(3));
         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
     
     void TraerSector()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `sector` order by id ASC limit 500");
         ComboSector.removeAllItems();
          while(st.next())
          {
             ComboSector.addItem(st.getString(2));
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
        ResultSet st,kl,ms,lo,yp;
        Statement cn,lk,qa,za,we;
        
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM  `direccion` order by id ASC limit 500");
          while(st.next())
          {
              Vector v = new Vector();
                lk = reg.createStatement();
                kl = lk.executeQuery("SELECT `Descripcion` FROM `provincia` WHERE `Id` ='"+st.getString(2)+"' ");
                kl.next();
                qa = reg.createStatement();
                ms = qa.executeQuery("SELECT `Descripcion` FROM `ciudad` WHERE `Id` ='"+st.getString(3)+"' ");
                ms.next();
                za = reg.createStatement();
                lo = za.executeQuery("SELECT `Descripcion` FROM `municipio` WHERE `Id` ='"+st.getString(4)+"' ");
                lo.next();
                we = reg.createStatement();
                yp = we.executeQuery("SELECT `Descripcion` FROM `sector` WHERE `Id` ='"+st.getString(5)+"' ");
                yp.next();
                v.add(st.getInt(1));//Id
                v.add(kl.getString(1));//Provincia
                v.add(ms.getString(1));//Ciudad
                v.add(lo.getString(1));//Municipio
                v.add(yp.getString(1));//Sector
                v.add(st.getString(6));//Descripcion
                v.add(st.getString(7));//Calle
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
       ResultSet st,kl,ms,lo,yp;
       Statement cn,lk,qa,za,we;
       int provi=0,ciud=0,muni=0,sect=0;
       String prov="",ciu="",mun="",sec="";
       prov=ComboProvincia.getSelectedItem().toString();
       ciu=ComboCiudad.getSelectedItem().toString();
       mun=ComboMuncipio.getSelectedItem().toString();
       sec=ComboSector.getSelectedItem().toString();
       
        try
         {
             cn = reg.createStatement();
             st = cn.executeQuery("SELECT `id` FROM `provincia` WHERE `Descripcion`='"+prov+"' ");
             st.next() ;
             provi=Integer.parseInt(st.getString(1));
             
             lk = reg.createStatement();
             kl = lk.executeQuery("SELECT `Id` FROM `ciudad` WHERE `Descripcion`='"+ciu+"' ");
             kl.next() ;
             ciud=Integer.parseInt(kl.getString(1));
             
             qa = reg.createStatement();
             ms = qa.executeQuery("SELECT `Id` FROM `municipio` WHERE `Descripcion`='"+mun+"' ");
             ms.next() ;
             muni=Integer.parseInt(ms.getString(1));
             
             za = reg.createStatement();
             lo = za.executeQuery("SELECT `Id` FROM `sector` WHERE `Descripcion`='"+sec+"' ");
             lo.next() ;
             sect=Integer.parseInt(lo.getString(1));
             
             
             
           PreparedStatement ps = reg.prepareStatement("INSERT INTO `direccion` (`id`, `Provincia`, `Ciudad`, `Municipio`, `Sector`, `Descripcion`, `Calle`) "
          + "VALUES ('"+TxtId.getText()+"','"+provi+"','"+ciud+"','"+muni+"','"+sect+"','"+TxtDescripcion.getText()+"','"+TxtCalle.getText()+"')");
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null, "Nueva Direccion agregado" );
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
    void Actualizar()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       ResultSet st,kl,ms,lo,yp;
       Statement cn,lk,qa,za,we;
       int provi=0,ciud=0,muni=0,sect=0;
       String prov="",ciu="",mun="",sec="";
       prov=ComboProvincia.getSelectedItem().toString();
       ciu=ComboCiudad.getSelectedItem().toString();
       mun=ComboMuncipio.getSelectedItem().toString();
       sec=ComboSector.getSelectedItem().toString();
       
       try
         {
             cn = reg.createStatement();
             st = cn.executeQuery("SELECT `id` FROM `provincia` WHERE `Descripcion`='"+prov+"' ");
             st.next() ;
             provi=Integer.parseInt(st.getString(1));
             
             lk = reg.createStatement();
             kl = lk.executeQuery("SELECT `Id` FROM `ciudad` WHERE `Descripcion`='"+ciu+"' ");
             kl.next() ;
             ciud=Integer.parseInt(kl.getString(1));
             
             qa = reg.createStatement();
             ms = qa.executeQuery("SELECT `Id` FROM `municipio` WHERE `Descripcion`='"+mun+"' ");
             ms.next() ;
             muni=Integer.parseInt(ms.getString(1));
             
             za = reg.createStatement();
             lo = za.executeQuery("SELECT `Id` FROM `sector` WHERE `Descripcion`='"+sec+"' ");
             lo.next() ;
             sect=Integer.parseInt(lo.getString(1));
             
        PreparedStatement ps = reg.prepareStatement("UPDATE `direccion` SET `Provincia`='"+provi+"',`Ciudad`='"+ciud+"',`Municipio`='"+muni+"',`Sector`='"+sect+"',`Descripcion`='"+TxtDescripcion.getText()+"',`Calle`='"+TxtCalle.getText()+"' WHERE  `direccion`.`id` ='"+TxtId.getText()+"'");
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "provincia modificado");
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
        PreparedStatement ps = reg.prepareStatement("DELETE FROM `direccion` WHERE  Id='"+TxtId.getText()+"'");
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, " direccion Eliminado");
          }catch(SQLException ex){
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
    void Limpiar()
    {
        TxtId.setText("");
        TxtDescripcion.setText("");
        TxtCalle.setText("");
        TxtCalle.requestFocus(true);
        TxtId.setBackground(Color.WHITE);
        TxtCalle.setBackground(Color.WHITE);
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
        ComboProvincia = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        ComboCiudad = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        ComboMuncipio = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        ComboSector = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        TxtDescripcion = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        TxtCalle = new javax.swing.JTextField();
        BtMenu = new javax.swing.JButton();
        BtEliminar = new javax.swing.JButton();
        BtInserta = new javax.swing.JButton();
        BtActualizar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Id: ");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Provincia:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Ciudad:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Municipio:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Sector:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Descripcion:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Calle:");

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

        Table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Provincia", "Ciudad", "Municipio", "Sector", "Calle"
            }
        ));
        jScrollPane1.setViewportView(Table1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(ComboProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(TxtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(ComboMuncipio, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(36, 36, 36)
                                        .addComponent(jLabel1))
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(77, 77, 77)
                                        .addComponent(jLabel11)
                                        .addGap(18, 18, 18)
                                        .addComponent(ComboSector, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(ComboCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(TxtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(BtInserta, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(BtActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addComponent(BtEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(BtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(ComboSector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(ComboProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(ComboCiudad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(TxtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(ComboMuncipio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtActualizar)
                    .addComponent(BtMenu)
                    .addComponent(BtInserta)
                    .addComponent(BtEliminar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(Direccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Direccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Direccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Direccion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Direccion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtActualizar;
    private javax.swing.JButton BtEliminar;
    private javax.swing.JButton BtInserta;
    private javax.swing.JButton BtMenu;
    private javax.swing.JComboBox<String> ComboCiudad;
    private javax.swing.JComboBox<String> ComboMuncipio;
    private javax.swing.JComboBox<String> ComboProvincia;
    private javax.swing.JComboBox<String> ComboSector;
    private javax.swing.JTable Table1;
    private javax.swing.JTextField TxtCalle;
    private javax.swing.JTextField TxtDescripcion;
    private javax.swing.JTextField TxtId;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
