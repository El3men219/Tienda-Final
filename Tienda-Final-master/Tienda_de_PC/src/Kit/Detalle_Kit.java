
package Kit;

import Articulos.*;
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
public class Detalle_Kit extends javax.swing.JFrame {


    public Detalle_Kit() {
        initComponents();
        this.setLocationRelativeTo(null);
         TraerKit();
        TraerArticulo();
        cargarUnidadDeMedida();
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
         st =cn.executeQuery("SELECT * FROM  `unidad` order by id ASC limit 500");
         ComboUnidad.removeAllItems();
          while(st.next())
          {
             ComboUnidad.addItem(st.getString(2));
         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
       }
  
        void TraerArticulo()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       ResultSet st,nm,lk,gf;
       Statement cn,mn,kl,fg;
       DefaultTableModel modelo = (DefaultTableModel) Table1.getModel();
       modelo.getDataVector().clear();
       int Id=0;
        try
         {
           kl = reg.createStatement();
           lk = kl.executeQuery("SELECT `Id` FROM `tipoarticulo` WHERE `Descripcion`='Herramientas'");
           lk.next();
           
        mn= reg.createStatement();
        nm= mn.executeQuery("SELECT * FROM `articulo` WHERE `IdTipoArticulo`='"+lk.getInt(1)+"'");
      while(nm.next())
        {
           Vector v = new Vector();
           fg= reg.createStatement();
           gf= fg.executeQuery("SELECT * FROM `detalle_articulo` WHERE `Id`='"+nm.getInt(1)+"'");
           while(gf.next())
           {
           v.add(nm.getInt(1));
           v.add(nm.getString(3));
           v.add(gf.getString(2));
            modelo.addRow(v);
           Table1.setModel(modelo);  
           }
           
        }
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
           TxtModelo.setText(Table1.getValueAt(fila, 2).toString());
       }
          TxtCantidad.requestFocus(true);
       }catch (HeadlessException ex){

             JOptionPane.showMessageDialog(null, "Error: "+ex+"\nInténtelo nuevamente", " .::Error En la Operacion::." ,JOptionPane.ERROR_MESSAGE);

       }     
    }
  void selectTable2()
    {
     try{
     
        int fila = Table3.getSelectedRow();
       if(fila >=0)
       {
           TxtKit.setText(Table3.getValueAt(fila, 0).toString());
           
       }
          
       }catch (HeadlessException ex){

             JOptionPane.showMessageDialog(null, "Error: "+ex+"\nInténtelo nuevamente", " .::Error En la Operacion::." ,JOptionPane.ERROR_MESSAGE);

       }     
    }
    void TraerKit()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        DefaultTableModel modelo = (DefaultTableModel) Table3.getModel();
        ResultSet st;
        Statement cn;
        
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `kit` order by id ASC limit 500");
          while(st.next())
          {
             Vector v = new Vector();
          
           v.add(st.getInt(1));
           v.add(st.getString(2));
          
           modelo.addRow(v);  
          }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
    void addArticulo()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        DefaultTableModel modelo = (DefaultTableModel) Table2.getModel();
        ResultSet st,lk,gf;
        Statement cn,kl,fg;
        int Ca=0;
        Ca=Integer.parseInt(TxtCantidad.getText());
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `detalle_articulo` WHERE `Descripcion`='"+TxtModelo.getText()+"' ");
          while(st.next())
          {
                Vector v = new Vector();
                kl= reg.createStatement();//marca 
                 lk= kl.executeQuery("SELECT * FROM `articulo` WHERE `Id` ='"+st.getString(1)+"' ");
                lk.next();
                fg= reg.createStatement();//Unidad
                gf= fg.executeQuery("SELECT `Descripcion`FROM `unidad` WHERE `Id` ='"+st.getString(3)+"' ");
                gf.next();
                v.add(lk.getInt(1));//Id
                v.add(lk.getString(3));//Marca
                v.add(TxtModelo.getText());//Modelo
                v.add(gf.getString(1));//Unidad 
                v.add(Ca);//Cantidad
                modelo.addRow(v); 
                Table2.setModel(modelo);
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
        String Un="",Id="",De="",Ca="";
        int Unidad=0;
     try
         {
        for(int i=0; i<Table2.getRowCount();i++)
         {
             
             Id= (Table2.getValueAt(i, 0).toString());
             De= (Table2.getValueAt(i, 2).toString());
             Un= (Table2.getValueAt(i, 3).toString());
             Ca= (Table2.getValueAt(i, 4).toString());
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT `Id`FROM `unidad` WHERE `Descripcion`='"+Un+"' ");
          while(st.next())
          {
           Unidad= st.getInt(1);
          }
            PreparedStatement ps = reg.prepareStatement("INSERT INTO `detalle_kit`(`Id`, `Articulo`, `Descripcion`, `Unidad`, `Cantidad`)"
            + " VALUES ('"+TxtKit.getText()+"','"+Id+"','"+De+"','"+Unidad+"','"+Ca+"')");
           ps.executeUpdate();
         }
           JOptionPane.showMessageDialog(null, "kit agregado" );
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
    void Limpiar()
    {
        TxtId.setText("");
        TxtCantidad.setText("");
        TxtDescripcion.setText("");
        TxtId.setBackground(Color.WHITE);
        TxtDescripcion.setBackground(Color.WHITE);
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
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        ComboUnidad = new javax.swing.JComboBox<>();
        BtBuscar = new javax.swing.JButton();
        TxtCantidad = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        TxtKit = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        Table3 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        TxtModelo = new javax.swing.JTextField();
        BtInserta1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Id: ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Marca:");

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

        BtInserta.setText("Insertar");
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
                "Id", "Marca", "Modelo"
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
                "Id", "Marca", "Modelo", "Unidad de medida", "Cantidad"
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

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Unidad de medida:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Cantidad:");

        ComboUnidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboUnidadActionPerformed(evt);
            }
        });

        BtBuscar.setText("Buscar");
        BtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Kit: ");

        TxtKit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtKitActionPerformed(evt);
            }
        });
        TxtKit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TxtKitKeyReleased(evt);
            }
        });

        Table3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kit", "Descripcion"
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

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Modelo:");

        TxtModelo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TxtModeloKeyReleased(evt);
            }
        });

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
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TxtModelo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtInserta, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(BtEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TxtKit, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TxtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(16, 16, 16)
                                .addComponent(BtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(96, 96, 96))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(BtInserta1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane2))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(TxtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtBuscar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(TxtKit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(ComboUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtEliminar)
                    .addComponent(BtInserta)
                    .addComponent(BtMenu)
                    .addComponent(jLabel3)
                    .addComponent(TxtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtInserta1)
                .addGap(12, 12, 12))
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
        JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
        TxtDescripcion.requestFocus(true);
        TxtDescripcion.setBackground(Color.YELLOW);
        return;
        }
        else {
            TxtDescripcion.setBackground(Color.WHITE);
        }
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
        JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
        TxtCantidad.requestFocus(true);
        TxtCantidad.setBackground(Color.YELLOW);
        return;
        }
        else {
            TxtCantidad.setBackground(Color.WHITE);
        }
        addArticulo();
    
   
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
              
              Desconectar();
          } 
    }//GEN-LAST:event_TxtIdKeyReleased

    private void BtEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtEliminarActionPerformed
        int Fila= Table2.getSelectedRow();
     DefaultTableModel modelo = (DefaultTableModel) Table2.getModel();
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
        // TODO add your handling code here:
    }//GEN-LAST:event_Table2MouseClicked

    private void Table2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table2MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Table2MousePressed

    private void ComboUnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboUnidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboUnidadActionPerformed

    private void BtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtBuscarActionPerformed

    private void TxtDescripcionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtDescripcionKeyReleased
             
    }//GEN-LAST:event_TxtDescripcionKeyReleased

    private void BtMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtMenuActionPerformed
       Menu sc=new Menu();
        sc.setVisible(true);
        dispose(); 
    }//GEN-LAST:event_BtMenuActionPerformed

    private void TxtKitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtKitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtKitActionPerformed

    private void TxtKitKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtKitKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtKitKeyReleased

    private void Table3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table3MouseClicked
       selectTable2();
    }//GEN-LAST:event_Table3MouseClicked

    private void Table3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table3MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Table3MousePressed

    private void TxtModeloKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtModeloKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtModeloKeyReleased

    private void BtInserta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtInserta1ActionPerformed
         if(TxtKit.getText().equals("")){
        JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
        TxtKit.requestFocus(true);
        TxtKit.setBackground(Color.YELLOW);
        return;
        }
        else {
            TxtKit.setBackground(Color.WHITE);
        }
    Insertar();
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
            java.util.logging.Logger.getLogger(Detalle_Kit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Detalle_Kit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Detalle_Kit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Detalle_Kit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Detalle_Kit().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtBuscar;
    private javax.swing.JButton BtEliminar;
    private javax.swing.JButton BtInserta;
    private javax.swing.JButton BtInserta1;
    private javax.swing.JButton BtMenu;
    private javax.swing.JComboBox<String> ComboUnidad;
    private javax.swing.JTable Table1;
    private javax.swing.JTable Table2;
    private javax.swing.JTable Table3;
    private javax.swing.JTextField TxtCantidad;
    private javax.swing.JTextField TxtDescripcion;
    private javax.swing.JTextField TxtId;
    private javax.swing.JTextField TxtKit;
    private javax.swing.JTextField TxtModelo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
