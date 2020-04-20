
package Pieza;

import Mantenimientos.*;
import Libre.Conexion;
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
 * @author score
 */
public class Tarjeta_Grafica extends javax.swing.JFrame {


    public Tarjeta_Grafica() {
        initComponents();
        this.setLocationRelativeTo(null);
        Traer();
        TraerArticulo();
        TraerCombo();
    }
    
    Conexion con;
    
    void TraerCombo()
    {
        TraerIluminacion();
        TraerVram();
        TraerFrecuencia();
        TraerTamaño();
        TraerVoltaje();
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
          tr = reg.createStatement();
          rt =tr.executeQuery("SELECT `Id` FROM `tipoarticulo` WHERE `Descripcion` = 'Tarjeta Gradica'");
          rt.next();
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `articulo` WHERE `IdTipoArticulo`='"+rt.getString(1)+"' AND `Descripcion` LIKE '%"+ Valor +"%' order by `Descripcion` desc limit 500");
          while(st.next())
          {
                Vector v = new Vector();
                dk = reg.createStatement();
                kd = dk.executeQuery("SELECT  `Descripcion` FROM `tipoarticulo` WHERE `Id`='"+st.getString(2)+"'");
                kd.next();
                v.add(st.getInt(1));
                v.add(st.getString(3));
                v.add(kd.getString(1));
                modelo.addRow(v);
                Table1.setModel(modelo);
         }
        }
        catch (SQLException e)
        {
            
        }
    }
        void TraerArticulo()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       ResultSet st,nm,kl;
       Statement cn,mn,lk;
       DefaultTableModel modelo = (DefaultTableModel) Table1.getModel();
       modelo.getDataVector().clear();
       int Id=0;
        try
         {
        cn = reg.createStatement();
        st =cn.executeQuery("SELECT `Id` FROM `tipoarticulo` WHERE `Descripcion` = 'Tarjeta Grafica'");
        st.next();
        mn= reg.createStatement();
        nm= mn.executeQuery("SELECT `Id`,`Descripcion` FROM `articulo` WHERE `IdTipoArticulo`='"+st.getString(1)+"'");
      while(nm.next())
        {
          
           lk = reg.createStatement();
           kl =lk.executeQuery("SELECT * FROM `Detalle_Articulo` WHERE `Id` = '"+nm.getString(1)+"'");
           while(kl.next())
           {
           Vector v = new Vector();
           v.add(kl.getInt(1));
           v.add(nm.getString(2));
           v.add(kl.getString(2));
           modelo.addRow(v);
           Table1.setModel(modelo);
           }
        }
        TxtModelo.requestFocus(true);
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
     void TraerIluminacion()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `Iluminacion` order by Id ASC limit 500");
         ComboIluminacion.removeAllItems();
          while(st.next())
          {
             ComboIluminacion.addItem(st.getString(2));
         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
     void TraerVram()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `Vram` order by Id ASC limit 500");
         ComboVram.removeAllItems();
          while(st.next())
          {
             ComboVram.addItem(st.getString(2));
         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
     void TraerFrecuencia()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
        int Frecuencia=0;
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `Frecuencia` order by Id ASC limit 500");
         ComboFrecuenciaN.removeAllItems();
         ComboFrecuenciaM.removeAllItems();
          while(st.next())
          {
              Frecuencia=Integer.parseInt(st.getString(2));
             ComboFrecuenciaN.addItem(String.valueOf(Frecuencia));
             ComboFrecuenciaM.addItem(String.valueOf(Frecuencia));
         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
 void TraerTamaño()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `Tamaño` order by Id ASC limit 500");
         ComboTamaño.removeAllItems();
          while(st.next())
          {
             ComboTamaño.addItem(st.getString(2));
         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
 void TraerVoltaje()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `Voltajes` order by Id ASC limit 500");
         ComboVoltaje.removeAllItems();
          while(st.next())
          {
             ComboVoltaje.addItem(st.getString(2));
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
           TxtId.setText(Table2.getValueAt(fila, 0).toString());
           TxtModelo.setText(Table2.getValueAt(fila, 1).toString());
       }
          TxtModelo.requestFocus(true);
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
        ResultSet st,lk,mw,nm,as,gf,hj,xz;
        Statement cn,kl,wm,mn,sa,fg,jh,zx;
        int memoria=0;
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `Tarjeta_Grafica` order by id ASC limit 500");
          while(st.next())
          {
                Vector v = new Vector();
                kl= reg.createStatement();//marca 
                lk= kl.executeQuery("SELECT `Id`,`Descripcion` FROM `articulo` WHERE `Id` ='"+st.getString(1)+"' ");
                lk.next();
                wm= reg.createStatement();//Iluminacion
                mw= wm.executeQuery("SELECT  `Descripcion` FROM `Iluminacion` WHERE `Id`='"+st.getString(3)+"'");
                mw.next();
                mn= reg.createStatement();//VRam
                nm= mn.executeQuery("SELECT  `Descripcion` FROM `Vram` WHERE `Id`='"+st.getString(4)+"'");
                nm.next();
                sa= reg.createStatement();//Frecuencia-Nucleo
                as= sa.executeQuery("SELECT  `Descripcion` FROM `Frecuencia` WHERE `Id`='"+st.getString(5)+"'");
                as.next();
                zx= reg.createStatement();//Frecuencia-Memoria
                xz= zx.executeQuery("SELECT  `Descripcion` FROM `Frecuencia` WHERE `Id`='"+st.getString(6)+"'");
                xz.next();
                fg= reg.createStatement();//Tamaño
                gf= fg.executeQuery("SELECT  `Descripcion` FROM `Tamaño` WHERE `Id`='"+st.getString(7)+"'");
                gf.next();
                jh= reg.createStatement();//Voltaje
                hj= jh.executeQuery("SELECT  `Descripcion` FROM `Voltajes` WHERE `Id`='"+st.getString(8)+"'");
                hj.next();
                v.add(lk.getInt(1));//id
                v.add(lk.getString(2));//marca
                v.add(st.getString(2));//modelo
                v.add(mw.getString(1));//Iluminacion
                memoria=Integer.parseInt(nm.getString(1));
                if(memoria<=1000)
                {
                   v.add(memoria + " Mb ");//VRam    
                }
                else
                {
                   memoria /=1000;
                   v.add(memoria + " Gb ");//VRam
                }
                v.add(as.getString( 1 ) + " Mhz ");//Frecuencia Nucleo  
                v.add(xz.getString( 1 ) + " Mhz ");//Frecuencia Memoria
                v.add(gf.getString(1));//Tamaño  
                v.add(hj.getString( 1 ) + " W ");//Voltaje   
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
        ResultSet st,lk,sa,sd,hj,yu;
        Statement cn,kl,as,ds,jh,uy;
        int Il=0,VR=0,FeN=0,FeM=0,Ta=0,Vo=0;
         String Iluminacion="",Vram="",FrecuenciaN="",FrecuenciaM="",Tamaño="",Voltaje="";
         Iluminacion=ComboIluminacion.getSelectedItem().toString();
         Vram=ComboVram.getSelectedItem().toString();
         FrecuenciaN=ComboFrecuenciaN.getSelectedItem().toString();
         FrecuenciaM=ComboFrecuenciaM.getSelectedItem().toString();
         Tamaño=ComboTamaño.getSelectedItem().toString();
         Voltaje=ComboVoltaje.getSelectedItem().toString();
        try
         {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT `Id` FROM `Iluminacion` WHERE  `Descripcion`='"+Iluminacion+"' ");
          while(st.next())
          {
              kl= reg.createStatement();
              lk= kl.executeQuery("SELECT `Id`  FROM `Vram` WHERE `Descripcion`='"+Vram+"'");
              lk.next();
              as= reg.createStatement();
              sa= as.executeQuery("SELECT `Id`  FROM `Frecuencia` WHERE `Descripcion`='"+FrecuenciaN+"'");
              sa.next();
              ds= reg.createStatement();
              sd= ds.executeQuery("SELECT `Id`  FROM `Frecuencia` WHERE `Descripcion`='"+FrecuenciaM+"'");
              sd.next();
              jh= reg.createStatement();
              hj= jh.executeQuery("SELECT `Id`  FROM `Tamaño` WHERE `Descripcion`='"+Tamaño+"'");
              hj.next();
              uy= reg.createStatement();
              yu= uy.executeQuery("SELECT `Id`  FROM `Voltajes` WHERE `Descripcion`='"+Voltaje+"'");
              yu.next();
              Il=Integer.parseInt(st.getString(1));
              VR=Integer.parseInt(lk.getString(1));
              FeN=Integer.parseInt(sa.getString(1));
              FeM=Integer.parseInt(sd.getString(1));
              Ta=Integer.parseInt(hj.getString(1));
              Vo=Integer.parseInt(yu.getString(1));
          }
           PreparedStatement ps = reg.prepareStatement("INSERT INTO `tarjeta_grafica`(`Id`, `Descripcion`, `Iluminacion`, `Vram`, `Frecuencia_Nucleo`, `Frecuencia_Memoria`, `Tamaño`, `Voltaje`) VALUES ('"+TxtId.getText()+"','"+TxtModelo.getText()+"','"+Il+"','"+VR+"','"+FeN+"','"+FeM+"','"+Ta+"','"+Vo+"')");
           ps.executeUpdate();
           JOptionPane.showMessageDialog(null, "Tarjeta grafica  agregado" );
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
    void Actualizar()
    {
        con = new Conexion();
       Connection reg = con.getConnection();
        ResultSet st,lk,sa,sd,hj,yu;
        Statement cn,kl,as,ds,jh,uy;
        int Il=0,VR=0,FeN=0,FeM=0,Ta=0,Vo=0;
         String Iluminacion="",Vram="",FrecuenciaN="",FrecuenciaM="",Tamaño="",Voltaje="";
         Iluminacion=ComboIluminacion.getSelectedItem().toString();
         Vram=ComboVram.getSelectedItem().toString();
         FrecuenciaN=ComboFrecuenciaN.getSelectedItem().toString();
         FrecuenciaM=ComboFrecuenciaM.getSelectedItem().toString();
         Tamaño=ComboTamaño.getSelectedItem().toString();
         Voltaje=ComboVoltaje.getSelectedItem().toString();
        try
         {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT `Id` FROM `Iluminacion` WHERE  `Descripcion`='"+Iluminacion+"' ");
          while(st.next())
          {
              kl= reg.createStatement();
              lk= kl.executeQuery("SELECT `Id`  FROM `Vram` WHERE `Descripcion`='"+Vram+"'");
              lk.next();
              as= reg.createStatement();
              sa= as.executeQuery("SELECT `Id`  FROM `Frecuencia` WHERE `Descripcion`='"+FrecuenciaN+"'");
              sa.next();
              ds= reg.createStatement();
              sd= ds.executeQuery("SELECT `Id`  FROM `Frecuencia` WHERE `Descripcion`='"+FrecuenciaM+"'");
              sd.next();
              jh= reg.createStatement();
              hj= jh.executeQuery("SELECT `Id`  FROM `Tamaño` WHERE `Descripcion`='"+Tamaño+"'");
              hj.next();
              uy= reg.createStatement();
              yu= uy.executeQuery("SELECT `Id`  FROM `Voltajes` WHERE `Descripcion`='"+Voltaje+"'");
              yu.next();
              Il=Integer.parseInt(st.getString(1));
              VR=Integer.parseInt(lk.getString(1));
              FeN=Integer.parseInt(sa.getString(1));
              FeM=Integer.parseInt(sd.getString(1));
              Ta=Integer.parseInt(hj.getString(1));
              Vo=Integer.parseInt(yu.getString(1));
          }
         
        PreparedStatement ps = reg.prepareStatement("UPDATE `tarjeta_grafica` SET `Iluminacion`='"+Il+"',`Vram`='"+VR+"',`Frecuencia_Nucleo`='"+FeN+"',`Frecuencia_Memoria`='"+FeM+"',`Tamaño`='"+Ta+"',`Voltaje`='"+Vo+"' WHERE `tarjeta_grafica`.`Descripcion`='"+TxtModelo.getText()+"'");
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Tarjeta Grafica modificado");
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
        PreparedStatement ps = reg.prepareStatement("DELETE FROM `tarjeta_grafica`  WHERE  `Descripcion` ='"+TxtModelo.getText()+"'");
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Tarjeta Grafica Eliminado");
          }catch(SQLException ex){
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
    void Limpiar()
    {
        TxtId.setText("");
        TxtDescripcion.setText("");
        TxtModelo.setText("");
        TxtModelo.requestFocus(true);
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
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        ComboIluminacion = new javax.swing.JComboBox<>();
        ComboVram = new javax.swing.JComboBox<>();
        ComboFrecuenciaN = new javax.swing.JComboBox<>();
        BtBuscar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        ComboFrecuenciaM = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        ComboTamaño = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        ComboVoltaje = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
                "Id", "Marca", "Modelo", "Iluminacion", "Vram", "Frecuencia de Nucleo", "Frecuencia de Memoria", "Tamaño", "Voltaje"
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

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Iluminacion:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Vram:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Frecuencia del Nucleo:");

        ComboIluminacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboIluminacionActionPerformed(evt);
            }
        });

        ComboVram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboVramActionPerformed(evt);
            }
        });

        ComboFrecuenciaN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboFrecuenciaNActionPerformed(evt);
            }
        });

        BtBuscar.setText("Buscar");
        BtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Frecuencia de Memoria:");

        ComboFrecuenciaM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboFrecuenciaMActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Tamaño:");

        ComboTamaño.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboTamañoActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Voltaje:");

        ComboVoltaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboVoltajeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TxtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(BtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboIluminacion, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ComboVram, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboFrecuenciaN, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 940, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ComboFrecuenciaM, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ComboTamaño, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ComboVoltaje, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addComponent(BtInserta, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(BtActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89)
                .addComponent(BtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(BtEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(BtBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(TxtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboIluminacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboVram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboFrecuenciaN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(ComboFrecuenciaM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboTamaño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(ComboVoltaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtActualizar)
                    .addComponent(BtInserta)
                    .addComponent(BtMenu)
                    .addComponent(BtEliminar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE))
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
   
    Desconectar();
   
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
    Traer();
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
              Traer();
              Desconectar();
          } 
    }//GEN-LAST:event_TxtIdKeyReleased

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

    private void Table1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table1MousePressed

    }//GEN-LAST:event_Table1MousePressed

    private void Table2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table2MouseClicked
         selectTable2();
    }//GEN-LAST:event_Table2MouseClicked

    private void Table2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table2MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Table2MousePressed

    private void ComboIluminacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboIluminacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboIluminacionActionPerformed

    private void ComboVramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboVramActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboVramActionPerformed

    private void ComboFrecuenciaNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboFrecuenciaNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboFrecuenciaNActionPerformed

    private void BtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarActionPerformed
        FiltradorMarca(TxtDescripcion.getText());
    }//GEN-LAST:event_BtBuscarActionPerformed

    private void ComboFrecuenciaMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboFrecuenciaMActionPerformed
      
    }//GEN-LAST:event_ComboFrecuenciaMActionPerformed

    private void ComboTamañoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboTamañoActionPerformed
        
    }//GEN-LAST:event_ComboTamañoActionPerformed

    private void ComboVoltajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboVoltajeActionPerformed
        
    }//GEN-LAST:event_ComboVoltajeActionPerformed

    private void TxtDescripcionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtDescripcionKeyReleased
       FiltradorMarca(TxtDescripcion.getText());
    }//GEN-LAST:event_TxtDescripcionKeyReleased

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
            java.util.logging.Logger.getLogger(Tarjeta_Grafica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tarjeta_Grafica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tarjeta_Grafica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tarjeta_Grafica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Tarjeta_Grafica().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtActualizar;
    private javax.swing.JButton BtBuscar;
    private javax.swing.JButton BtEliminar;
    private javax.swing.JButton BtInserta;
    private javax.swing.JButton BtMenu;
    private javax.swing.JComboBox<String> ComboFrecuenciaM;
    private javax.swing.JComboBox<String> ComboFrecuenciaN;
    private javax.swing.JComboBox<String> ComboIluminacion;
    private javax.swing.JComboBox<String> ComboTamaño;
    private javax.swing.JComboBox<String> ComboVoltaje;
    private javax.swing.JComboBox<String> ComboVram;
    private javax.swing.JTable Table1;
    private javax.swing.JTable Table2;
    private javax.swing.JTextField TxtDescripcion;
    private javax.swing.JTextField TxtId;
    private javax.swing.JTextField TxtModelo;
    private javax.swing.JLabel jLabel1;
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
