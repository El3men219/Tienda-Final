
package Armado;

import Pieza.*;
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
public class Seleccion_Pieza extends javax.swing.JFrame {


    public Seleccion_Pieza() {
        initComponents();
        this.setLocationRelativeTo(null);
        TraerArticulo();
        TraerTipo();
        AutoIncremento();
        TraerTipoOrden();
        TraerEstado();
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
        st =cn.executeQuery("SELECT MAX(`NumOrden`) FROM `Orden`");
         while(st.next())
          {
         Id=Integer.parseInt(String.valueOf(st.getInt(1)));
         Id+=1;
        if(Id<=1)
         { 
             Id=1;
             TxtIdOrden.setText(String.valueOf(Id));
         }
        else 
        {
            TxtIdOrden.setText(String.valueOf(Id));
        }}  
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
    void TraerEmpleado() 
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       ResultSet st,nm,lk,rt;
       Statement cn,mn,kl,tr;
       String usu="",con="";
       Libre.Login ne = new Libre.Login();
       usu=ne.Usu;
       con=ne.Con;
               try
         {
        mn= reg.createStatement();
        nm= mn.executeQuery("SELECT MAX(`Id`) FROM `historial_de_acceso`");
      while(nm.next())
        {
            cn= reg.createStatement();//Historial
            st= cn.executeQuery("SELECT * FROM  `historial_de_acceso`  WHERE  `Id`='"+nm.getString(1)+"'");
            st.next();
            kl= reg.createStatement();//Usuario
            lk= kl.executeQuery("SELECT * FROM `usuarios` WHERE `Usuario`='"+st.getString(2)+"'");
            lk.next();
            tr= reg.createStatement();//Empleado
            rt= tr.executeQuery("SELECT * FROM `empleado` WHERE `Id`='"+lk.getString(1)+"'");
            rt.next();
            TxtEmpleado.setText(rt.getString(1));
            TxtSucursal.setText(rt.getString(2));
        }}catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
       
    }
  
        void TraerArticulo()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       ResultSet st,nm,lk;
       Statement cn,mn,kl;
        DecimalFormat Formatea = new DecimalFormat(" RD$ ###,###,###.## ");
       DefaultTableModel modelo = (DefaultTableModel) Table1.getModel();
       modelo.getDataVector().clear();
       float precio=0;
        try
         {
        mn= reg.createStatement();
        nm= mn.executeQuery("SELECT * FROM `Detalle_Articulo` order by Id ASC limit 500");
      while(nm.next())
        {
           Vector v = new Vector();
           kl = reg.createStatement();
           lk =kl.executeQuery("SELECT * FROM `Articulo` WHERE `Id` = '"+nm.getString(1)+"'");
           lk.next();
           cn = reg.createStatement();
           st =cn.executeQuery("SELECT `Descripcion` FROM `TipoArticulo` WHERE `Id` = '"+lk.getString(2)+"'");
           st.next();
           v.add(lk.getInt(1));//Id
           v.add(lk.getString(3));//Marca
           v.add(nm.getString(2));//Modelo
           v.add(st.getString(1));//Tipo
           v.add(nm.getString(4));//Cantidad Disponible
           precio=Float.parseFloat(nm.getString( 7 ));
           v.add( Formatea.format(precio));//Precio
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
        ResultSet st,kd,rt;
        Statement cn,dk,tr;
        
        try
        {

         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `articulo` WHERE  `Descripcion` LIKE '%"+ Valor +"%' order by `Descripcion` desc limit 500");
          while(st.next())
          {
                Vector v = new Vector();
                dk = reg.createStatement();
                kd = dk.executeQuery("SELECT * FROM `Detalle_Articulo` WHERE `Id`='"+st.getString(1)+"'");
                kd.next();
                tr = reg.createStatement();
                rt =tr.executeQuery("SELECT `Descripcion` FROM `TipoArticulo` WHERE `Id` = '"+st.getString(2)+"'");
                rt.next();
                v.add(kd.getInt(1));//Id
                v.add(st.getString(3));//Marca
                v.add(kd.getString(2));//Modelo
                v.add(rt.getString(1));//Tipo
                v.add(kd.getString(4));//Cantidad Disponible
                v.add( " RD$ " + kd.getString( 7 ));//Precio
                modelo.addRow(v);
                Table1.setModel(modelo);
         }
        }
        catch (SQLException e)
        {
            
        }
    }
    void FiltradorTipo(String Valor)
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        DefaultTableModel modelo = (DefaultTableModel) Table1.getModel();
        modelo.getDataVector().clear();
        ResultSet st,kd,rt;
        Statement cn,dk,tr;
        int tipo=0;
        try
        {
         tr = reg.createStatement();
         rt =tr.executeQuery("SELECT `Id` FROM `TipoArticulo` WHERE `Descripcion` = '"+Valor+"'");
         rt.next();
         tipo=Integer.parseInt(rt.getString(1));
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `articulo` WHERE  `IdTipoArticulo` LIKE '%"+ tipo +"%' order by `IdTipoArticulo` desc limit 500");
          while(st.next())
          {
                Vector v = new Vector();
                dk = reg.createStatement();
                kd = dk.executeQuery("SELECT * FROM `Detalle_Articulo` WHERE `Id`='"+st.getString(1)+"'");
                kd.next();
                tr = reg.createStatement();
                rt =tr.executeQuery("SELECT `Descripcion` FROM `TipoArticulo` WHERE `Id` = '"+st.getString(2)+"'");
                rt.next();
                v.add(kd.getInt(1));//Id
                v.add(st.getString(3));//Marca
                v.add(kd.getString(2));//Modelo
                v.add(rt.getString(1));//Tipo
                v.add(kd.getString(4));//Cantidad Disponible
                v.add( " RD$ " + kd.getString( 7 ));//Precio
                modelo.addRow(v);
                Table1.setModel(modelo);
         }
        }
        catch (SQLException e)
        {
            
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
         st =cn.executeQuery("SELECT * FROM `TipoArticulo` order by Id ASC limit 500");
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
          void TraerEstado()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `estadoventa` order by Id ASC limit 500");
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
     void TraerTipoOrden()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `tipo_orden` order by Id ASC limit 500");
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

    void selectTable()
    {
     try{
     
        int fila = Table1.getSelectedRow();
       if(fila >=0)
       {
           TxtId.setText(Table1.getValueAt(fila, 0).toString());
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
     
        int fila = Table2.getSelectedRow();
       if(fila >=0)
       {
           TxtId.setText(Table2.getValueAt(fila, 0).toString());
           TxtModelo.setText(Table2.getValueAt(fila, 1).toString());
           TxtCantidad.setText(Table2.getValueAt(fila, 4).toString());
       }
          TxtModelo.requestFocus(true);
       }catch (HeadlessException ex){

             JOptionPane.showMessageDialog(null, "Error: "+ex+"\nInténtelo nuevamente", " .::Error En la Operacion::." ,JOptionPane.ERROR_MESSAGE);

       }     
    }
    void addArticulo()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        DefaultTableModel modelo = (DefaultTableModel) Table2.getModel();
        
        ResultSet st,lk,mw,nm,as;
        Statement cn,kl,wm,mn,sa;
        DecimalFormat Formatea = new DecimalFormat(" RD$ ###,###,###.## ");
        int Cantidad=0,Ca=0;
        float Precio=0,Total=0,ITBIS=(float) 0.18,SubTotal=0;
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `detalle_articulo` WHERE `Descripcion`='"+TxtModelo.getText()+"' ");
          while(st.next())
          {
                Vector v = new Vector();
                kl= reg.createStatement();//marca 
                lk= kl.executeQuery("SELECT * FROM `articulo` WHERE `Id` ='"+st.getInt(1)+"' ");
                lk.next();
                wm= reg.createStatement();//Tipo 
                mw= wm.executeQuery("SELECT `Descripcion` FROM `tipoarticulo` WHERE  `Id`='"+lk.getString(2)+"' ");
                mw.next();
                Cantidad=Integer.parseInt(TxtCantidad.getText());
                Ca=Integer.parseInt(st.getString(4));
                if(Cantidad>Ca)
                {
                  JOptionPane.showMessageDialog(null, "La Cantidad es superior a la disponible");
                  TxtCantidad.requestFocus(true);
                  TxtCantidad.setBackground(Color.YELLOW);
                  return; 
                }
                else
                {
                TxtCantidad.setBackground(Color.WHITE);
                Precio=Float.parseFloat(st.getString(7));
                Total= Precio*Cantidad;
                ITBIS*=Total;
                v.add(lk.getInt(1));//id
                v.add(lk.getString(3));//marca
                v.add(st.getString(2));//modelo
                v.add(mw.getString(1));//tipo
                v.add(Cantidad);//Cantidad
                v.add(Formatea.format( ITBIS ) );//ITBIS
                v.add(Formatea.format( Total ) );//Precio
                modelo.addRow(v);
                Table2.setModel(modelo);
                }

         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
    
    void llenarText()
    {
         DecimalFormat Formatea = new DecimalFormat(" RD$ ###,###,###.## ");
         DecimalFormat Formatea2 = new DecimalFormat("############.##");
         float ITBIS=0,SubTotal=0,Total=0,SubI=0,SubP=0,TotalI=0,TotalP=0,TI=0,TP=0;
         String jItbis="",jPrecio="";
         for(int i=0; i<Table2.getRowCount();i++)
         {
             jItbis= (Table2.getValueAt(i, 5).toString());
             jPrecio= (Table2.getValueAt(i, 6).toString());
             
             System.out.println(" ITBIS " + jItbis );     
             System.out.println(" Precio " + jPrecio );
             
             String digits = jItbis.replaceAll("[^0-9.]", "");
             String digits2 = jPrecio.replaceAll("[^0-9.]", "");

            TotalI= TotalI + Float.parseFloat(digits);
            TotalP= TotalP + Float.parseFloat(digits2);
          }
         SubTotal =Float.parseFloat(Formatea2.format(TotalP));
         TxtSubTotal.setText(Formatea.format(SubTotal));
         TxtITBIS.setText(Formatea.format(TotalI));
         Total= TotalI +TotalP;
         TxtTotal.setText(Formatea.format(Total));
         
    }


    
    void Insertar()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
        ResultSet st,lk,sa;
        Statement cn,kl,as;
        int Ti=0,Ve=0,Ca=0;
        float total=0;
        String Tipo="",Estado="";
         Tipo=ComboTipoOrden.getSelectedItem().toString();
         Estado=ComboEstado.getSelectedItem().toString();
       try
         {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT `Id`  FROM `tipo_orden` WHERE `Descripcion`='"+Tipo+"' ");
          while(st.next())
          {
           kl = reg.createStatement();
           lk =kl.executeQuery("SELECT `Id`  FROM `estadoventa` WHERE `Descripcion`='"+Estado+"' ");
           lk.next();
            Ti=Integer.parseInt(st.getString(1));
            Ve=Integer.parseInt(lk.getString(1));
          }
          
          String digits = TxtTotal.getText().replaceAll("[^0-9.]", "");
           PreparedStatement ps = reg.prepareStatement("INSERT INTO `orden`(`NumOrden`, `Tipo`, `Empleado`, `Sucursal`, `Fecha`, `Tercero`, `Total`) "
          + "  VALUES ('"+TxtIdOrden.getText()+"','"+Ti+"','"+TxtEmpleado.getText()+"','"+TxtSucursal.getText()+"',NOW(),'"+TxtNombre.getText()+"','"+Float.parseFloat(digits)+"')");
           ps.executeUpdate();
            TxtDescripcion.setEditable(true);
           JOptionPane.showMessageDialog(null, "Orden Procesada" );
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
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
         for(int i=0; i<Table2.getRowCount();i++)
         {
             JId= (Table2.getValueAt(i, 0).toString());
             JDe= (Table2.getValueAt(i, 2).toString());
             JCa= (Table2.getValueAt(i, 4).toString());
             jPrecio= (Table2.getValueAt(i, 6).toString());
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT `Descripcion`, `unida_medida`, `Cantidad`, `Fecha`, `Costo`, `Precio` FROM `detalle_articulo` WHERE `Id`='"+JId+"' ");
          while(st.next())
          {

            Un=Integer.parseInt(st.getString(2));
          }
           
             
             String digits2 = jPrecio.replaceAll("[^0-9.]", "");
             PreparedStatement ps = reg.prepareStatement("INSERT INTO `detalle_orden`(`NumOrden`, `Id_Articulo`, `Descripcion`, `Unidad`, `Cantidad`, `Precio`)"
                    + "  VALUES ('"+Integer.parseInt(TxtIdOrden.getText())+"','"+Integer.parseInt(JId)+"','"+JDe+"','"+Un+"','"+Integer.parseInt(JCa)+"','"+digits2 +"')");
           ps.executeUpdate();
          }
           
            TxtDescripcion.setEditable(true);
           
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    void Actualizar()
    {
        con = new Conexion();
       Connection reg = con.getConnection();
        ResultSet st,lk,sa;
        Statement cn,kl,as;
        int Ti=0,Ve=0,Ca=0;
         String Tipo="",Velocidad="",Capacidad="";
         Tipo=ComboTipo.getSelectedItem().toString();
      try
         {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT `Id` FROM `Tipo_DiscoDuro` WHERE  `Descripcion`='"+Tipo+"' ");
          while(st.next())
          {
          /*    kl= reg.createStatement();
              lk= kl.executeQuery("SELECT `Id`  FROM `Velocidad_Transferencia` WHERE `Descripcion`='"+Velocidad+"'");
              lk.next();
              as= reg.createStatement();
              sa= as.executeQuery("SELECT `Id`  FROM `Capacidad` WHERE `Descripcion`='"+Capacidad+"'");
              sa.next();
              Ti=Integer.parseInt(st.getString(1));
              Ve=Integer.parseInt(lk.getString(1));
              Ca=Integer.parseInt(sa.getString(1));*/
          }
        PreparedStatement ps = reg.prepareStatement("UPDATE `disco_duro` SET `Tipo`='"+Ti+"',`Velocidad`='"+Ve+"',`Capacidad`='"+Ca+"' WHERE `disco_duro`.`Descripcion`='"+TxtModelo.getText()+"'");
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Disco Duro modificado");
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
        PreparedStatement ps = reg.prepareStatement("DELETE FROM `disco_duro`  WHERE  `Descripcion` ='"+TxtModelo.getText()+"'");
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Disco Duro Eliminado");
          }catch(SQLException ex){
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
    void Limpiar()
    {
        TxtId.setText("");
        TxtDescripcion.setText("");
        TxtModelo.setText("");
        TxtCantidad.setText("");
        TxtSubTotal.setText("");
        TxtITBIS.setText("");
        TxtTotal.setText("");
        TxtModelo.requestFocus(true);
        TxtId.setBackground(Color.WHITE);
        TxtDescripcion.setBackground(Color.WHITE);
        TxtModelo.setBackground(Color.WHITE);
        TxtCantidad.setBackground(Color.WHITE);
        TxtSubTotal.setBackground(Color.WHITE);
        TxtITBIS.setBackground(Color.WHITE);
        TxtTotal.setBackground(Color.WHITE);
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
        BtActualizar = new javax.swing.JButton();
        BtMenu = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        Table2 = new javax.swing.JTable();
        TxtModelo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        BtBuscar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        ComboTipo = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        TxtSubTotal = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        TxtITBIS = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        TxtTotal = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        TxtCantidad = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        TxtIdOrden = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        TxtEmpleado = new javax.swing.JTextField();
        TxtSucursal = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        BtInserta1 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        ComboTipoOrden = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        ComboEstado = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        TxtNombre = new javax.swing.JTextField();

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

        Table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Marca", "Modelo", "Tipo", "Cantidad Disponible", "Precio"
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
                "Id", "Marca", "Modelo", "Tipo", "Cantidad", "ITBIS", "Precio"
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

        BtBuscar.setText("Buscar");
        BtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Tipo:");

        ComboTipo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ComboTipoMouseClicked(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Sub-Total:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("ITBIS:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Total:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Cantidad:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("NumOrden: ");

        TxtIdOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtIdOrdenActionPerformed(evt);
            }
        });
        TxtIdOrden.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TxtIdOrdenKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Empleado: ");

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

        TxtSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtSucursalActionPerformed(evt);
            }
        });
        TxtSucursal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TxtSucursalKeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Sucursal: ");

        BtInserta1.setText("Procesar");
        BtInserta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtInserta1ActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Tipo:");

        ComboTipoOrden.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ComboTipoOrdenMouseClicked(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Estado:");

        ComboEstado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ComboEstadoMouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Nombre: ");

        TxtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtNombreActionPerformed(evt);
            }
        });
        TxtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TxtNombreKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(BtInserta1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TxtITBIS, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TxtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TxtEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TxtSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ComboTipoOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 708, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TxtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(BtInserta, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(BtEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BtActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TxtIdOrden, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(98, 98, 98)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TxtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ComboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)
                                .addComponent(BtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(TxtIdOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(TxtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(BtBuscar)
                            .addComponent(ComboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(TxtEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(TxtSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(TxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(ComboTipoOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(ComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(TxtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(TxtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtInserta)
                    .addComponent(BtEliminar)
                    .addComponent(BtActualizar)
                    .addComponent(jLabel1)
                    .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(TxtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtMenu)
                    .addComponent(BtInserta1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(TxtITBIS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(TxtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

      addArticulo();
      llenarText();
        TxtId.setText("");
        TxtDescripcion.setText("");
        TxtModelo.setText("");
        TxtCantidad.setText("");
   
    }//GEN-LAST:event_BtInsertaActionPerformed

    private void BtActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtActualizarActionPerformed
        if(TxtModelo.getText().equals("")){
        JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
        TxtModelo.requestFocus(true);
        TxtModelo.setBackground(Color.YELLOW);
        return;
        }
        else {
            TxtModelo.setBackground(Color.WHITE);
        }
    Actualizar();
    Limpiar();
  
    Desconectar();
    }//GEN-LAST:event_BtActualizarActionPerformed

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
      selectTable2();
    }//GEN-LAST:event_Table2MouseClicked

    private void Table2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table2MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Table2MousePressed

    private void BtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarActionPerformed
        String Tipo="";
        Tipo= ComboTipo.getSelectedItem().toString();
        FiltradorTipo(Tipo);
    }//GEN-LAST:event_BtBuscarActionPerformed

    private void TxtDescripcionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtDescripcionKeyReleased
        FiltradorMarca(TxtDescripcion.getText());
    }//GEN-LAST:event_TxtDescripcionKeyReleased

    private void ComboTipoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ComboTipoMouseClicked

    }//GEN-LAST:event_ComboTipoMouseClicked

    private void TxtIdOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtIdOrdenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtIdOrdenActionPerformed

    private void TxtIdOrdenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtIdOrdenKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtIdOrdenKeyReleased

    private void TxtEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtEmpleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtEmpleadoActionPerformed

    private void TxtEmpleadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtEmpleadoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtEmpleadoKeyReleased

    private void TxtSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtSucursalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtSucursalActionPerformed

    private void TxtSucursalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtSucursalKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtSucursalKeyReleased

    private void BtInserta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtInserta1ActionPerformed
        Insertar();
        InsertarDetalle();
       
    }//GEN-LAST:event_BtInserta1ActionPerformed

    private void ComboTipoOrdenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ComboTipoOrdenMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboTipoOrdenMouseClicked

    private void ComboEstadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ComboEstadoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboEstadoMouseClicked

    private void TxtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtNombreActionPerformed

    private void TxtNombreKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtNombreKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtNombreKeyReleased

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
            java.util.logging.Logger.getLogger(Seleccion_Pieza.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Seleccion_Pieza.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Seleccion_Pieza.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Seleccion_Pieza.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Seleccion_Pieza().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtActualizar;
    private javax.swing.JButton BtBuscar;
    private javax.swing.JButton BtEliminar;
    private javax.swing.JButton BtInserta;
    private javax.swing.JButton BtInserta1;
    private javax.swing.JButton BtMenu;
    private javax.swing.JComboBox<String> ComboEstado;
    private javax.swing.JComboBox<String> ComboTipo;
    private javax.swing.JComboBox<String> ComboTipoOrden;
    private javax.swing.JTable Table1;
    private javax.swing.JTable Table2;
    private javax.swing.JTextField TxtCantidad;
    private javax.swing.JTextField TxtDescripcion;
    private javax.swing.JTextField TxtEmpleado;
    private javax.swing.JTextField TxtITBIS;
    private javax.swing.JTextField TxtId;
    private javax.swing.JTextField TxtIdOrden;
    private javax.swing.JTextField TxtModelo;
    private javax.swing.JTextField TxtNombre;
    private javax.swing.JTextField TxtSubTotal;
    private javax.swing.JTextField TxtSucursal;
    private javax.swing.JTextField TxtTotal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
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
