
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


public class Empleado extends javax.swing.JFrame {

    /**
     * Creates new form Empleado
     */
    public Empleado() {
        initComponents();
        this.setLocationRelativeTo(null);
        TxtId.requestFocus(true); 
        Traer();
        TraerSucursal();
        TraerPusto();
        TraerDepartamento();
        TraerTipo();
        TraerTandas();
        TraerEstado();
        llenarTabla();
    }
    
    Conexion con;
    
    void Traer()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       ResultSet st,mw,kl,sa,ds;
       Statement cn,lk,jh,as,sd;
       DefaultTableModel modelo = (DefaultTableModel) Table1.getModel();
       modelo.getDataVector().clear();
      try
         {
        cn = reg.createStatement();
        st =cn.executeQuery("SELECT * FROM `tercero` order by `IdTercero` ASC limit 500");
         while(st.next())
          {
                Vector v = new Vector();

                v.add(st.getString(1));//Id
                v.add(st.getString(2));//Nombre
                modelo.addRow(v);
                Table1.setModel(modelo);
          }
         }catch(SQLException ex)
         {
          javax.swing.JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
    void TraerSucursal()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `sucursall` order by Id ASC limit 500");
         ComboSucursal.removeAllItems();
          while(st.next())
          {
             ComboSucursal.addItem(st.getString(2));
         }
        }
        catch (SQLException e)
        {
         javax.swing.JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
    
    void TraerPusto()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `puesto` order by Id ASC limit 500");
         ComboPuesto.removeAllItems();
          while(st.next())
          {
             ComboPuesto.addItem(st.getString(2));
         }
        }
        catch (SQLException e)
        {
         javax.swing.JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
    
    void TraerDepartamento()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `departamento` order by Id ASC limit 500");
         ComboDepartamento.removeAllItems();
          while(st.next())
          {
             ComboDepartamento.addItem(st.getString(2));
         }
        }
        catch (SQLException e)
        {
         javax.swing.JOptionPane.showMessageDialog(null, "Error"+e );
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
         st =cn.executeQuery("SELECT * FROM `tipo_empleados` order by Id ASC limit 500");
         ComboTipo.removeAllItems();
          while(st.next())
          {
             ComboTipo.addItem(st.getString(2));
         }
        }
        catch (SQLException e)
        {
         javax.swing.JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
    
    void TraerTandas()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `tandas` order by Id ASC limit 500");
         ComboTanda.removeAllItems();
          while(st.next())
          {
             ComboTanda.addItem(st.getString(2));
         }
        }
        catch (SQLException e)
        {
         javax.swing.JOptionPane.showMessageDialog(null, "Error"+e );
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
         st =cn.executeQuery("SELECT * FROM `estado_empleado` order by Id ASC limit 500");
         ComboEstado.removeAllItems();
          while(st.next())
          {
             ComboEstado.addItem(st.getString(2));
         }
        }
        catch (SQLException e)
        {
         javax.swing.JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
    
     void llenarTabla()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        DefaultTableModel modelo = (DefaultTableModel) Table2.getModel();
        modelo.getDataVector().clear();
        ResultSet st,kl,dp,ew,op,nb;
        Statement cn,lk,qa,we,po,bn;
        
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM  `empleado` order by `Tercero` ASC limit 500");
         

         while(st.next())
         {
            
            Vector v = new Vector();
            
            qa = reg.createStatement();
            dp = qa.executeQuery("SELECT `Nombre` FROM `tercero` WHERE `idTercero` ='"+st.getString(1)+"' ");
            dp.next();
            
            lk = reg.createStatement();
            kl =lk.executeQuery("SELECT `Descripcion` FROM `sucursall` WHERE `Id` ='"+st.getString(2)+"' ");
            kl.next();
            
            po = reg.createStatement();
            op = po.executeQuery("SELECT `Descripcion` FROM `puesto` WHERE `Id` ='"+st.getString(2)+"' ");
            op.next();
            
            we = reg.createStatement();
            ew = we.executeQuery("SELECT `Descripcion` FROM `departamento` WHERE `Id` ='"+st.getString(2)+"' ");
            ew.next();
            
            bn = reg.createStatement();
            nb = bn.executeQuery("SELECT `Descripcion` FROM `tipo_empleados` WHERE `id` ='"+st.getString(2)+"' ");
            nb.next();
            
            v.add(dp.getString(1));
            v.add(kl.getString(2));
            v.add(op.getString(3));
            v.add(ew.getString(4));
            v.add(nb.getString(5));
            modelo.addRow(v);
            Table2.setModel(modelo);  
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
           TxtId.setText(Table1.getValueAt(fila, 0).toString());
           
       }

       }catch (HeadlessException ex){

             JOptionPane.showMessageDialog(null, "Error: "+ex+"\nInténtelo nuevamente", " .::Error En la Operacion::." ,JOptionPane.ERROR_MESSAGE);

       }     
    }
       
       void Insertar()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       ResultSet st,kl,dp,ew,op,nb,pt;
       Statement cn,lk,qa,we,po,bn,tp;
       int SucursalI=0,PuestoI=0,DepartamentoI=0,TipoI=0,TandaI=0,EstadoI=0;
       String Sucursal="", Puesto="", Departamento="", Tipo="",Tanda="",Estado="";
       Sucursal=ComboSucursal.getSelectedItem().toString();
       Puesto=ComboPuesto.getSelectedItem().toString();
       Departamento=ComboDepartamento.getSelectedItem().toString();
       Tipo=ComboTipo.getSelectedItem().toString();
       Tanda=ComboTanda.getSelectedItem().toString();
       Estado=ComboEstado.getSelectedItem().toString();
         
        try
         {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT `Id` FROM `sucursall` WHERE  `Descripcion`='"+Sucursal+"' ");
         st.next();
         SucursalI=Integer.parseInt(st.getString(1));      
                  
            lk = reg.createStatement();
            kl =lk.executeQuery("SELECT `Id` FROM `puesto` WHERE `Descripcion` ='"+Puesto+"' ");
            kl.next();
            PuestoI=Integer.parseInt(st.getString(1));
            
            po = reg.createStatement();
            op = po.executeQuery("SELECT `Id` FROM `departamento` WHERE `Descripcion` ='"+Departamento+"' ");
            op.next();
            DepartamentoI=Integer.parseInt(st.getString(1));
            
            we = reg.createStatement();
            ew = we.executeQuery("SELECT `id` FROM `tipo_empleados` WHERE `Descripcion` ='"+Tipo+"' ");
            ew.next();
            TipoI=Integer.parseInt(st.getString(1));
            
            tp = reg.createStatement();
            pt = tp.executeQuery("SELECT `id` FROM `tandas` WHERE `Descripcion` ='"+Tipo+"' ");
            pt.next();
            TandaI=Integer.parseInt(st.getString(1));
            
            bn = reg.createStatement();
            nb = bn.executeQuery("SELECT `Id` FROM `estado_empleado` WHERE `Descripcion` ='"+Estado+"' ");
            nb.next();
            EstadoI=Integer.parseInt(st.getString(1));
      
          
           PreparedStatement ps = reg.prepareStatement("INSERT INTO `empleado`(`Id`, `Sucursal`, `Puesto`, `Departamento`, `Tipo_Empleado`, `Tanda`, `Estado`) VALUES "
                   + "('"+TxtId.getText()+"','"+SucursalI+"','"+PuestoI+"','"+DepartamentoI+"','"+TipoI+"','"+TandaI+"','"+EstadoI+"')");
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null, "Nuevo Empleado  agregada" );
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
       
        void FiltradorTercero(String Valor)
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        DefaultTableModel modelo = (DefaultTableModel) Table1.getModel();
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
                Table1.setModel(modelo);
         }
        }
        catch (SQLException e)
        {

        }
    }
       
       void Limpiar()
    {
        TxtId.setText("");
        
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

        jToolBar1 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        TxtId = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        ComboPuesto = new javax.swing.JComboBox<>();
        ComboTipo = new javax.swing.JComboBox<>();
        ComboTanda = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        ComboEstado = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        Table2 = new javax.swing.JTable();
        BtInserta = new javax.swing.JButton();
        BtActualizar = new javax.swing.JButton();
        BtEliminar = new javax.swing.JButton();
        BtMenu = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        TxtBuscar = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        ComboSucursal = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        ComboDepartamento = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        jToolBar1.setRollover(true);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Id: ");

        Table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Apellido"
            }
        ));
        Table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Table1);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Puesto: ");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Tipo_Empleado:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Tanda:");

        ComboPuesto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ComboTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        ComboTanda.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Estado:");

        ComboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        Table2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nombre", "Sucursal", "Puesto", "Departamento", "Tipo_Empleado"
            }
        ));
        jScrollPane3.setViewportView(Table2);

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

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Buscar:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Sucursal:");

        ComboSucursal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Departamento:");

        ComboDepartamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel9.setText("Registro de Empleado");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 13, Short.MAX_VALUE)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(BtInserta, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BtActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(ComboPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(74, 74, 74)
                                        .addComponent(jLabel4))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(ComboSucursal, 0, 136, Short.MAX_VALUE)
                                            .addComponent(TxtId))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel8))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ComboDepartamento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ComboTipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ComboTanda, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(TxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8)
                            .addComponent(ComboDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel6))
                    .addComponent(TxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel7)
                                        .addComponent(ComboSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(ComboPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(ComboTanda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(ComboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtActualizar)
                            .addComponent(BtInserta)
                            .addComponent(BtEliminar)
                            .addComponent(BtMenu))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
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
        
    Insertar();
    Traer();
    llenarTabla();
    Limpiar();
    Desconectar();
    }                                         

    private void BtActualizarActionPerformed(java.awt.event.ActionEvent evt) {                                             
         if(TxtId.getText().equals("")){
        JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
        TxtId.requestFocus(true);
        TxtId.setBackground(Color.YELLOW);
        return;
        }
        else {
            TxtId.setBackground(Color.WHITE);
        }
        
    Insertar();
    Limpiar();
    Traer();
    Desconectar(); 
    }//GEN-LAST:event_BtInsertaActionPerformed

    private void Table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table1MouseClicked
       int fila = Table1.getSelectedRow();
        if (fila >= 0) {
            TxtId.setText(Table1.getValueAt(fila, 0).toString());
            
        }
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
            java.util.logging.Logger.getLogger(Empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Empleado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtActualizar;
    private javax.swing.JButton BtEliminar;
    private javax.swing.JButton BtInserta;
    private javax.swing.JButton BtMenu;
    private javax.swing.JComboBox<String> ComboDepartamento;
    private javax.swing.JComboBox<String> ComboEstado;
    private javax.swing.JComboBox<String> ComboPuesto;
    private javax.swing.JComboBox<String> ComboSucursal;
    private javax.swing.JComboBox<String> ComboTanda;
    private javax.swing.JComboBox<String> ComboTipo;
    private javax.swing.JTable Table1;
    private javax.swing.JTable Table2;
    private javax.swing.JTextField TxtBuscar;
    private javax.swing.JTextField TxtId;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
