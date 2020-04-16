
package Ordenes;

import Libre.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class Orden extends javax.swing.JFrame {

    /**
     * Creates new form Orden
     */
    public Orden() {
        initComponents();
    }
    Conexion con;

    void AutoIncremento()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       ResultSet st;
       Statement cn;
       int NumOrden=0;
        try
         {
        cn = reg.createStatement();
        st =cn.executeQuery("SELECT MAX(`NumOrden`) FROM `orden`");
         while(st.next())
          {
             NumOrden=Integer.parseInt(String.valueOf(st.getInt(1)));
         NumOrden+=1;
        if(NumOrden<=1)
         {
             NumOrden=1;
             TxtNumOrden.setText(String.valueOf(NumOrden));
         }
        else 
        {
            TxtNumOrden.setText(String.valueOf(NumOrden));
        }} 
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
    void TraerSucursal()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
        
        try {
            cn = reg.createStatement();
            st = cn.executeQuery("SELECT * FROM 'sucursal' by Id ASC limit 500");
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
    
    void TraerTipoOrden()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
        
        try {
            cn = reg.createStatement();
            st = cn.executeQuery("SELECT * FROM 'tipo_orden' by Id ASC limit 500");
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
    
    void TraerUnidadMedida()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
        
        try {
            cn = reg.createStatement();
            st = cn.executeQuery("SELECT * FROM 'unidad' by Id ASC limit 500");
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
    void TraerEstadoOrden()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
        
        try {
            cn = reg.createStatement();
            st = cn.executeQuery("SELECT * FROM 'estadoventa' by Id ASC limit 500");
            ComboTipoOrden.removeAllItems();
            while(st.next())
            {
                ComboTipoOrden.addItem(st.getString(2));
            }
        } 
        catch (SQLException e) 
        {
            JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
    
   
    
    void InsertarOrden()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       ResultSet st,mw,kl;
       Statement cn, lk,jh;
       String sucursal="", tipoOrden="", Estado="";
       sucursal = ComboSucursal.getSelectedItem().toString();
       tipoOrden = ComboEstado.getSelectedItem().toString();
       Estado = ComboTipoOrden.getSelectedItem().toString();
       SimpleDateFormat Formato = new SimpleDateFormat("yyyy-mm-dd");

        try
         {
             cn = reg.createStatement();
             st = cn.executeQuery("SELECT Id FROM 'sucusal' where idTercero='"+sucursal+"'");
             st.next();
             
             lk = reg.createStatement();
             mw = lk.executeQuery("SELECT Id FROM 'tipo_orden' WHERE Descripcion='"+tipoOrden+"'");
             mw.next();
             
             jh = reg.createStatement();
             kl = jh.executeQuery("SELECT Id FROM 'estadoventa' WHERE Descripcion='"+Estado+"'");
             kl.next();
             
             
             
           PreparedStatement ps = reg.prepareStatement("INSERT INTO `orden`(`NumOrden`, `Empleado`, `Sucursal`, `TipoOrden, `Fecha`, `Nota`, `EstadoVenta`)"
            + " VALUES ('"+TxtNumOrden.getText()+"','"+TxtEmpleado.getText()+"','"+sucursal+"','"+tipoOrden+Formato.format(FechaN.getDate())+"','"+Estado+"'");
           ps.executeUpdate();
            TxtModelo.setEditable(true);
           JOptionPane.showMessageDialog(null, "Nuevo sexo agregado" );
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
    void InsertarDetalleOrden()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       ResultSet st;
       Statement cn;
       int Id=0;
       String Unidad="";
       Unidad = ComboUnidad.getSelectedItem().toString();
       
        for (int i = 0; i < TablaDetalle.getRowCount(); i++) {
            String id_articulo2 = "";
            String cantidad2 = "";
            String Descricion2 = "";
            String UnidadM = "";
            id_articulo2 = (TablaDetalle.getValueAt(i, 0).toString());
            Descricion2 = (TablaDetalle.getValueAt(i, 1).toString());
            UnidadM = (TablaDetalle.getValueAt(i, 2).toString());
            String precio_unid = (TablaDetalle.getValueAt(i, 3).toString());
            String canItbis = (TablaDetalle.getValueAt(i, 4).toString());
    
      try
         {
        cn = reg.createStatement();
        st =cn.executeQuery("SELECT Id FROM `unidad` where Descripcion='"+Unidad+"'");
        
        PreparedStatement ps = reg.prepareStatement("INSERT INTO `detalleorden`(`NumOrden`, `IdArticulo`, `Descripcion`, `Unidad`,`Cantidad`,`Itbis`,`Precio`)  "
          + "VALUES ('"+TxtNumOrden+"','"+id_articulo2+"','"+Descricion2+"','"+UnidadM+"','"+cantidad2+"','"+canItbis+"','"+precio_unid+"')");
        ps.executeUpdate();
        }catch(SQLException ex)
         {
              JOptionPane.showMessageDialog(null, "Error"+ex );
         }
        }
    }
    
    void FiltradorMarca(String Valor)
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
         st =cn.executeQuery("SELECT * FROM `articulo` WHERE `Descripcion` LIKE '%"+ Valor +"%' order by `Descripcion` desc limit 500");
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


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        TxtNumOrden = new javax.swing.JTextField();
        TxtEmpleado = new javax.swing.JTextField();
        ComboSucursal = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaDetalle = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        TxtModelo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        TxtCantidad = new javax.swing.JTextField();
        btAgregar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        TxtSubtotal = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        TxtItbis = new javax.swing.JTextField();
        TxtTatal = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        btProcesar = new javax.swing.JButton();
        btElminar = new javax.swing.JButton();
        btActualizar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        FechaN = new com.toedter.calendar.JDateChooser();
        ComboEstado = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        ComboTipoOrden = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        TxtId = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table1 = new javax.swing.JTable();
        ComboTipo = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        TxtMarca = new javax.swing.JTextField();
        ComboUnidad = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("NumOrden:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Tipo de Orden:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Empleado:");

        ComboSucursal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        TablaDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Descripcion", "Unidad de Medida", "Cantidad", "Itbis", "Precio"
            }
        ));
        jScrollPane1.setViewportView(TablaDetalle);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Datos de la orden");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Datos de productos ");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Modelo:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Cantidad:");

        btAgregar.setText("Agregar");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Subtotal:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("ITBIS:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Total:");

        btProcesar.setText("Procesar");

        btElminar.setText("Eliminar");

        btActualizar.setText("Actualizar");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Sucursal:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Fecha:");

        ComboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Estado de Orden:");

        ComboTipoOrden.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Id: ");

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

        Table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Tipo", "Descripcion"
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
        jScrollPane2.setViewportView(Table1);

        ComboTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Tipo:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("Marca:");

        TxtMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtMarcaActionPerformed(evt);
            }
        });
        TxtMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TxtMarcaKeyReleased(evt);
            }
        });

        ComboUnidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("Unidad de Medida:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btProcesar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btElminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(213, 213, 213)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(87, 87, 87)
                        .addComponent(jLabel6)
                        .addGap(169, 169, 169)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(FechaN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addGap(10, 10, 10))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(18, 18, 18)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ComboTipoOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ComboSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TxtEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(TxtNumOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(jScrollPane2))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TxtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel17)
                                .addGap(18, 18, 18)
                                .addComponent(ComboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(238, 238, 238))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(30, 30, 30)
                        .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(TxtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jLabel19)
                        .addGap(18, 18, 18)
                        .addComponent(ComboUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(TxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btAgregar)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TxtItbis, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TxtTatal, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel14)
                            .addComponent(jLabel7)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(FechaN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TxtNumOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(TxtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)
                            .addComponent(ComboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TxtEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ComboSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ComboTipoOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(ComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 10, Short.MAX_VALUE)))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btAgregar)
                    .addComponent(jLabel8)
                    .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel19)
                    .addComponent(ComboUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(TxtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btProcesar)
                    .addComponent(btElminar)
                    .addComponent(btActualizar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(TxtItbis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(TxtTatal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
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
        TxtNombre.requestFocus(true);
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

    private void Table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table1MouseClicked
        selectTable();

    }//GEN-LAST:event_Table1MouseClicked

    private void Table1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table1MousePressed

    }//GEN-LAST:event_Table1MousePressed

    private void TxtMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtMarcaActionPerformed

    private void TxtMarcaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtMarcaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtMarcaKeyReleased

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
            java.util.logging.Logger.getLogger(Orden.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Orden.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Orden.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Orden.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Orden().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboEstado;
    private javax.swing.JComboBox<String> ComboSucursal;
    private javax.swing.JComboBox<String> ComboTipo;
    private javax.swing.JComboBox<String> ComboTipoOrden;
    private javax.swing.JComboBox<String> ComboUnidad;
    private com.toedter.calendar.JDateChooser FechaN;
    private javax.swing.JTable TablaDetalle;
    private javax.swing.JTable Table1;
    private javax.swing.JTextField TxtCantidad;
    private javax.swing.JTextField TxtEmpleado;
    private javax.swing.JTextField TxtId;
    private javax.swing.JTextField TxtItbis;
    private javax.swing.JTextField TxtMarca;
    private javax.swing.JTextField TxtModelo;
    private javax.swing.JTextField TxtNumOrden;
    private javax.swing.JTextField TxtSubtotal;
    private javax.swing.JTextField TxtTatal;
    private javax.swing.JButton btActualizar;
    private javax.swing.JButton btAgregar;
    private javax.swing.JButton btElminar;
    private javax.swing.JButton btProcesar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
