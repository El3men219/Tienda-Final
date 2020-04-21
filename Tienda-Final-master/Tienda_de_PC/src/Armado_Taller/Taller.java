
package Armado_Taller;

import Comprobante.*;
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
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class Taller extends javax.swing.JFrame {

    /**
     * Creates new form Departamento
     */
    public Taller() {
        initComponents();
        this.setLocationRelativeTo(null);
        TraerOrdenArmado();
        TraerOrdenTaller();
        TraerEmpleado();
        TraerFase();
        Traer();
    }
 
    Conexion con;
    
     void selectTable()
    {
     try{
     
        int fila = Table2.getSelectedRow();
       if(fila >=0)
       {
           TxtNumOrden.setText(Table2.getValueAt(fila, 0).toString());
       }

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
           TxtEmpleado.setText(Table3.getValueAt(fila, 0).toString());
       }

       }catch (HeadlessException ex){

             JOptionPane.showMessageDialog(null, "Error: "+ex+"\nInténtelo nuevamente", " .::Error En la Operacion::." ,JOptionPane.ERROR_MESSAGE);

       }     
    }
     void selectTable3()
    {
     try{
     
        int fila = Table1.getSelectedRow();
       if(fila >=0)
       {
           TxtNumOrden.setText(Table1.getValueAt(fila, 0).toString());
           TxtEmpleado.setText(Table1.getValueAt(fila, 2).toString());
       }

       }catch (HeadlessException ex){

             JOptionPane.showMessageDialog(null, "Error: "+ex+"\nInténtelo nuevamente", " .::Error En la Operacion::." ,JOptionPane.ERROR_MESSAGE);

       }     
    }
    void TraerOrdenTaller()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        DefaultTableModel modelo = (DefaultTableModel) Table2.getModel();
        ResultSet st,lk,rt;
        Statement cn,kl,tr;
        
       try
        {
         kl = reg.createStatement();
         lk =kl.executeQuery("SELECT `Id` FROM `tipo_orden` WHERE `Descripcion`= 'Reparacion' ");
         lk.next();
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `orden` WHERE `Tipo`='"+lk.getInt(1)+"'  order by NumOrden ASC limit 500");
          while(st.next())
          {
                Vector v = new Vector();
                tr = reg.createStatement();
                rt =tr.executeQuery("SELECT `Descripcion` FROM `tipo_orden` WHERE `Id`= '"+st.getInt(2)+"' ");
                rt.next();
                v.add(st.getInt(1));
                v.add(rt.getString(1));
                modelo.addRow(v);
                Table2.setModel(modelo);
         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
        void TraerOrdenArmado()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        DefaultTableModel modelo = (DefaultTableModel) Table2.getModel();
        ResultSet st,lk,rt;
        Statement cn,kl,tr;
        
       try
        {
         kl = reg.createStatement();
         lk =kl.executeQuery("SELECT `Id` FROM `tipo_orden` WHERE `Descripcion`= 'Armado' ");
         lk.next();
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `orden` WHERE `Tipo`='"+lk.getInt(1)+"' order by NumOrden ASC limit 500");
          while(st.next())
          {
                Vector v = new Vector();
                tr = reg.createStatement();
                rt =tr.executeQuery("SELECT `Descripcion` FROM `tipo_orden` WHERE `Id`= '"+st.getInt(2)+"' ");
                rt.next();
                v.add(st.getInt(1));
                v.add(rt.getString(1));
                modelo.addRow(v);
                Table2.setModel(modelo);
         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
        void TraerEmpleado()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        DefaultTableModel modelo = (DefaultTableModel) Table3.getModel();
        ResultSet st,lk,rt,gf;
        Statement cn,kl,tr,fg;

       try// 
        {
         kl = reg.createStatement();
         lk =kl.executeQuery("SELECT * FROM `departamento` WHERE `Descripcion`= 'Taller'");
         lk.next();
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `empleado` WHERE `Departamento`='"+lk.getInt(1)+"'  order by id ASC limit 500");
          while(st.next())
          {
                Vector v = new Vector();
                tr = reg.createStatement();
                rt =tr.executeQuery("SELECT * FROM `tercero`  WHERE `IdTercero`= '"+st.getInt(1)+"' ");
                rt.next();
                fg = reg.createStatement();
                gf =fg.executeQuery("SELECT * FROM `persona` WHERE `Id`= '"+rt.getInt(1)+"' ");
                gf.next();
                v.add(gf.getInt(1));//Id
                v.add(rt.getString(2));//Nombre
                v.add(gf.getString(2));//Apellido
                modelo.addRow(v);
                Table3.setModel(modelo);
         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
     void TraerFase()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `Fase` order by Id ASC limit 500");
         ComboFase.removeAllItems();
          while(st.next())
          {
             ComboFase.addItem(st.getString(2));
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
       SimpleDateFormat Formato = new SimpleDateFormat("yyyy-MM-dd");
       String Fase="";
       int Fa=0,Ti=0;
       Fase= ComboFase.getSelectedItem().toString();
       try
         {
            cn = reg.createStatement();
            st =cn.executeQuery("SELECT * FROM `Fase` WHERE `Descripcion`='"+Fase+"' ");
          while(st.next())
          {
            kl = reg.createStatement();
            lk =kl.executeQuery("SELECT `Tipo` FROM `orden` WHERE `NumOrden`='"+TxtNumOrden.getText()+"' ");
            lk.next();
            Fa=st.getInt(1);
            Ti=lk.getInt(1);
          }
        PreparedStatement ps = reg.prepareStatement("INSERT INTO `taller`(`NumOrden`, `Empleado`, `Tipo`, `Fecha`, `Fase`) VALUES "
                + "('"+TxtNumOrden.getText()+"','"+TxtEmpleado.getText()+"','"+Ti+"','"+Formato.format(Fecha.getDate())+"','"+Fa+"')");
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Orden Procesada ");
          }catch(SQLException ex){
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
        void Actualizar()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       ResultSet st,lk;
       Statement cn,kl;
       SimpleDateFormat Formato = new SimpleDateFormat("yyyy-MM-dd");
       String Fase="";
       int Fa=0,Ti=0;
       Fase= ComboFase.getSelectedItem().toString();
       try
         {
            cn = reg.createStatement();
            st =cn.executeQuery("SELECT * FROM `Fase` WHERE `Descripcion`='"+Fase+"' ");
          while(st.next())
          {
            kl = reg.createStatement();
            lk =kl.executeQuery("SELECT `Tipo` FROM `orden` WHERE `NumOrden`='"+TxtNumOrden.getText()+"' ");
            lk.next();
            Fa=st.getInt(1);
            Ti=lk.getInt(1);
          }
        PreparedStatement ps = reg.prepareStatement("UPDATE `taller` SET `Empleado`='"+TxtEmpleado.getText()+"',`Tipo`='"+Ti+"',`Fecha`='"+Formato.format(Fecha.getDate())+"',`Fase`='"+Fa+"' WHERE `taller`.`NumOrden`='"+TxtNumOrden.getText()+"'");
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Orden Actualizada ");
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
        PreparedStatement ps = reg.prepareStatement("DELETE FROM `taller` WHERE  Id='"+TxtNumOrden.getText()+"'");
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, " `taller` Eliminada");
          }catch(SQLException ex){
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
    void Limpiar()
    {
        TxtNumOrden.setText("");
        TxtEmpleado.setText("");
        TxtNumOrden.setBackground(Color.WHITE);
        TxtEmpleado.setBackground(Color.WHITE);
        JOptionPane.showMessageDialog(null, "Los campos has sido limpiardo");
    }
    
    void Desconectar()
    {
         con.desconectar();
    }
        void FiltradorOrdenArmado(String Valor)
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        DefaultTableModel modelo = (DefaultTableModel) Table2.getModel();
        
        ResultSet st,kd,rt,lk;
        Statement cn,dk,tr,kl;
        
        try
        {
         kl = reg.createStatement();
         lk =kl.executeQuery("SELECT `Id` FROM `tipo_orden` WHERE `Descripcion`= 'Armado' ");
         lk.next();
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `orden` WHERE  `NumOrden` LIKE '%"+ Valor +"%' AND `Tipo`= '"+lk.getInt(1)+"'order by `NumOrden` desc limit 500");
          while(st.next())
          {
                Vector v = new Vector();
                tr = reg.createStatement();
                rt =tr.executeQuery("SELECT `Descripcion` FROM `tipo_orden` WHERE `Id`= '"+st.getInt(2)+"' ");
                rt.next();
                v.add(st.getInt(1));
                v.add(rt.getString(1));
                modelo.addRow(v);
                Table2.setModel(modelo);
         }
        }
        catch (SQLException e)
        {
            
        }
    }
        void FiltradorOrdenTaller(String Valor)
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        DefaultTableModel modelo = (DefaultTableModel) Table2.getModel();
        
        ResultSet st,rt,lk;
        Statement cn,tr,kl;
        
        try
        {
         kl = reg.createStatement();
         lk =kl.executeQuery("SELECT `Id` FROM `tipo_orden` WHERE `Descripcion`= 'Reparacion' ");
         lk.next();
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `orden` WHERE  `NumOrden` LIKE '%"+ Valor +"%' AND `Tipo`= '"+lk.getInt(1)+"'order by `NumOrden` desc limit 500");
          while(st.next())
          {
                Vector v = new Vector();
                tr = reg.createStatement();
                rt =tr.executeQuery("SELECT `Descripcion` FROM `tipo_orden` WHERE `Id`= '"+st.getInt(2)+"' ");
                rt.next();
                v.add(st.getInt(1));
                v.add(rt.getString(1));
                modelo.addRow(v);
                Table2.setModel(modelo);
         }
        }
        catch (SQLException e)
        {
            
        }
    }
        void FiltradorPersona(String Valor)
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st,gf,rt,lk;
        Statement cn,fg,tr,kl;
        DefaultTableModel modelo = (DefaultTableModel) Table3.getModel();
        try
        {
         kl = reg.createStatement();
         lk =kl.executeQuery("SELECT `Id` FROM `tipo_orden` WHERE  `departamento`= 'Taller'");
         lk.next();
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `empleado`  WHERE  `Id` LIKE '%"+ Valor +"%' AND `Departamento`= '"+lk.getInt(1)+"'order by `Id` desc limit 500");
          while(st.next())
          {
                Vector v = new Vector();
                tr = reg.createStatement();
                rt =tr.executeQuery("SELECT * FROM `tercero`  WHERE `IdTercero`= '"+st.getInt(1)+"' ");
                rt.next();
                fg = reg.createStatement();
                gf =fg.executeQuery("SELECT * FROM `persona` WHERE `Id`= '"+rt.getInt(1)+"' ");
                gf.next();
                v.add(gf.getInt(1));//Id
                v.add(rt.getString(2));//Nombre
                v.add(gf.getString(2));//Apellido
                modelo.addRow(v);
                Table3.setModel(modelo);
         }
        }
        catch (SQLException e)
        {
            
        }
    }
        void Traer()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        DefaultTableModel modelo = (DefaultTableModel) Table2.getModel();
        ResultSet st,lk,rt;
        Statement cn,kl,tr;
        
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `taller` order by NumOrden ASC limit 500");
          while(st.next())
          {
                Vector v = new Vector();
                tr = reg.createStatement();
                rt =tr.executeQuery("SELECT `Descripcion` FROM `Fase` WHERE `Id`= '"+st.getInt(5)+"' ");
                rt.next();
                kl = reg.createStatement();
                lk =kl.executeQuery("SELECT `Descripcion` FROM `tipo_orden` WHERE `Id`='"+st.getInt(3)+"' ");
                lk.next();
                v.add(st.getInt(1));//NumOrden
                v.add(st.getString(2));//Empleado
                v.add(lk.getString(1));//Tipo
                v.add(st.getString(4));//Fecha
                v.add(rt.getString(1));//Fase
                modelo.addRow(v);
                Table2.setModel(modelo);
         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
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
        BtInserta = new javax.swing.JButton();
        BtActualizar = new javax.swing.JButton();
        BtMenu = new javax.swing.JButton();
        BtEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        ComboFase = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table2 = new javax.swing.JTable();
        Fecha = new com.toedter.calendar.JDateChooser();
        jScrollPane3 = new javax.swing.JScrollPane();
        Table3 = new javax.swing.JTable();
        TxtEmpleado = new javax.swing.JTextField();

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

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("NumOrden: ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Empleado:");

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
                "NumOrden", "Tipo", "Encargado", "Fecha de Entrega", "Fase"
            }
        ));
        Table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table1);
        if (Table1.getColumnModel().getColumnCount() > 0) {
            Table1.getColumnModel().getColumn(2).setHeaderValue("Encargado");
            Table1.getColumnModel().getColumn(3).setHeaderValue("Fecha de Entrega");
            Table1.getColumnModel().getColumn(4).setHeaderValue("Fase");
        }

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Fecha De Entrega:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Fase:");

        Table2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NumOrden", "Tipo"
            }
        ));
        Table2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Table2);

        Table3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Apellido"
            }
        ));
        Table3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(Table3);

        TxtEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtEmpleadoActionPerformed(evt);
            }
        });
        TxtEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TxtEmpleadoKeyReleased(evt);
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
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ComboFase, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(BtEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BtInserta, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BtActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Fecha, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TxtEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TxtNumOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(Fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(ComboFase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtMenu)
                            .addComponent(BtEliminar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtInserta)
                            .addComponent(BtActualizar)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(TxtEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(TxtNumOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TxtNumOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtNumOrdenActionPerformed

    }//GEN-LAST:event_TxtNumOrdenActionPerformed

    private void TxtNumOrdenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtNumOrdenKeyReleased
        if(TxtNumOrden.getText().equals(""))
        {
            System.out.println("Digite un Id");
        }
        else
        {
            DefaultTableModel modelo = (DefaultTableModel) Table2.getModel();
            modelo.getDataVector().clear();
            FiltradorOrdenArmado(TxtNumOrden.getText());
            FiltradorOrdenTaller(TxtNumOrden.getText());
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
        TxtEmpleado.setBackground(Color.WHITE);
        }
        if(TxtEmpleado.getText().equals("")){
        JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
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
        TxtEmpleado.setBackground(Color.WHITE);
        }
        if(TxtEmpleado.getText().equals("")){
        JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
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
        TxtEmpleado.setBackground(Color.WHITE);
        }
        if(TxtEmpleado.getText().equals("")){
        JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
        TxtEmpleado.requestFocus(true);
        TxtEmpleado.setBackground(Color.YELLOW);
        return;
        }
        else {
            TxtEmpleado.setBackground(Color.WHITE);
        }
          
     Eliminar();
     Limpiar();
     Traer();
     Desconectar();
    }//GEN-LAST:event_BtEliminarActionPerformed

    private void Table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table1MouseClicked
       selectTable3();
    }//GEN-LAST:event_Table1MouseClicked

    private void Table2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table2MouseClicked
        selectTable();
    }//GEN-LAST:event_Table2MouseClicked

    private void Table3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table3MouseClicked
        selectTable2();
    }//GEN-LAST:event_Table3MouseClicked

    private void TxtEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtEmpleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtEmpleadoActionPerformed

    private void TxtEmpleadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtEmpleadoKeyReleased
          if(TxtEmpleado.getText().equals(""))
          {
              System.out.println("Digite un Id");
          }
          else
          {
             FiltradorPersona(TxtEmpleado.getText());
              Desconectar();
          }        
    }//GEN-LAST:event_TxtEmpleadoKeyReleased

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
            java.util.logging.Logger.getLogger(Taller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Taller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Taller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Taller.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new Taller().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtActualizar;
    private javax.swing.JButton BtEliminar;
    private javax.swing.JButton BtInserta;
    private javax.swing.JButton BtMenu;
    private javax.swing.JComboBox<String> ComboFase;
    private com.toedter.calendar.JDateChooser Fecha;
    private javax.swing.JTable Table1;
    private javax.swing.JTable Table2;
    private javax.swing.JTable Table3;
    private javax.swing.JTextField TxtEmpleado;
    private javax.swing.JTextField TxtNumOrden;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
