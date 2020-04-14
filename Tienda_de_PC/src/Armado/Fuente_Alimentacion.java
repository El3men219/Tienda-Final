
package Armado;

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
public class Fuente_Alimentacion extends javax.swing.JFrame {


    public Fuente_Alimentacion() {
        initComponents();
        this.setLocationRelativeTo(null);
        Traer();
        TraerArticulo();
        cargarCombo();
    }
    
    Conexion con;
    void cargarCombo()
    {
        TraerTamaño();
        TraerTipo();
        TraerIluminacion();
        TraerVoltaje();
        TraerCertificado();
    }
        void TraerArticulo()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       ResultSet st,nm;
       Statement cn,mn;
       DefaultTableModel modelo = (DefaultTableModel) Table1.getModel();
       modelo.getDataVector().clear();
       int Id=0;
        try
         {
        cn = reg.createStatement();
        st =cn.executeQuery("SELECT `Id` FROM `tipoarticulo` WHERE `Descripcion` = 'power supply'");
        st.next();
        mn= reg.createStatement();
        nm= mn.executeQuery("SELECT `Id`,`Descripcion` FROM `articulo` WHERE `IdTipoArticulo`='"+st.getString(1)+"'");
      while(nm.next())
        {
           Vector v = new Vector();
           v.add(nm.getInt(1));
           v.add(nm.getString(2));
           modelo.addRow(v);
           Table1.setModel(modelo);
        }
        TxtModelo.requestFocus(true);
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
         st =cn.executeQuery("SELECT * FROM `Tipo_Fuente` order by Id ASC limit 500");
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
     void TraerTamaño()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `Tamaño_Fuente` order by Id ASC limit 500");
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
    void TraerCertificado()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `Certificado_Fuente` order by Id ASC limit 500");
         ComboCertificado.removeAllItems();
          while(st.next())
          {
             ComboCertificado.addItem(st.getString(2));
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
    void Traer()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        DefaultTableModel modelo = (DefaultTableModel) Table2.getModel();
        modelo.getDataVector().clear();
        ResultSet st,lk,mw,nm,as,gf,hj;
        Statement cn,kl,wm,mn,sa,fg,jh;
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM `Fuente_Alimentacio` order by id ASC limit 500");
          while(st.next())
          {
                Vector v = new Vector();
                kl= reg.createStatement();//marca 
                lk= kl.executeQuery("SELECT `Id`,`Descripcion` FROM `articulo` WHERE `Id` ='"+st.getString(1)+"' ");
                lk.next();
                wm= reg.createStatement();//Tipo
                mw= wm.executeQuery("SELECT  `Descripcion` FROM `Tipo_Fuente` WHERE `Id`='"+st.getString(3)+"'");
                mw.next();
                mn= reg.createStatement();//Tamaño
                nm= mn.executeQuery("SELECT  `Descripcion` FROM `Tamaño_Fuente` WHERE `Id`='"+st.getString(4)+"'");
                nm.next();
                sa= reg.createStatement();//Iluminacion
                as= sa.executeQuery("SELECT  `Descripcion` FROM `Iluminacion` WHERE `Id`='"+st.getString(5)+"'");
                as.next();
                fg= reg.createStatement();//Voltaje
                gf= fg.executeQuery("SELECT  `Descripcion` FROM `Voltajes` WHERE `Id`='"+st.getString(6)+"'");
                gf.next();
                jh= reg.createStatement();//Certificado
                hj= jh.executeQuery("SELECT  `Descripcion` FROM `Certificado_Fuente` WHERE `Id`='"+st.getString(7)+"'");
                hj.next();
                v.add(lk.getInt(1));//id
                v.add(lk.getString(2));//marca
                v.add(st.getString(2));//modelo
                v.add(mw.getString(1));//tipo
                v.add(nm.getString(1));//Tamaño   
                v.add(as.getString(1));//Iluminacion   
                v.add(gf.getString( 1 ) + " W ");//Voltaje   
                v.add(hj.getString(1));//Certificado   
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
        ResultSet st,lk,sa,rt,gf;
        Statement cn,kl,as,tr,fg;
        int Ti=0,Ta=0,Il=0,Vo=0,Ce=0;
         String Tipo="",Tamaño="",Iluminacion="",Voltaje="",Certificado="";
         Tipo=ComboTipo.getSelectedItem().toString();
         Tamaño=ComboTamaño.getSelectedItem().toString();
         Iluminacion=ComboIluminacion.getSelectedItem().toString();
         Voltaje=ComboVoltaje.getSelectedItem().toString();
         Certificado=ComboCertificado.getSelectedItem().toString();
        try
         {
         cn = reg.createStatement();//Tipo
         st =cn.executeQuery("SELECT `Id` FROM `Tipo_Fuente` WHERE  `Descripcion`='"+Tipo+"' ");
          while(st.next())
          {
              kl= reg.createStatement();//Tamaño
              lk= kl.executeQuery("SELECT `Id`  FROM `Tamaño_Fuente` WHERE `Descripcion`='"+Tamaño+"'");
              lk.next();
              as= reg.createStatement();//Iluminacion
              sa= as.executeQuery("SELECT `Id`  FROM `Iluminacion` WHERE `Descripcion`='"+Iluminacion+"'");
              sa.next();
              tr= reg.createStatement();//Voltaje
              rt= tr.executeQuery("SELECT `Id`  FROM `Voltajes` WHERE `Descripcion`='"+Voltaje+"'");
              rt.next();
              fg= reg.createStatement();//Certificado
              gf= fg.executeQuery("SELECT `Id`  FROM `Certificado_Fuente` WHERE `Descripcion`='"+Certificado+"'");
              gf.next();
              Ti=Integer.parseInt(st.getString(1));
              Ta=Integer.parseInt(lk.getString(1));
              Il=Integer.parseInt(sa.getString(1));
              Vo=Integer.parseInt(rt.getString(1));
              Ce=Integer.parseInt(gf.getString(1));
          }
           PreparedStatement ps = reg.prepareStatement("INSERT INTO `fuente_alimentacio`(`Id`, `Descripcion`, `Tipo`, `Tamaño`, `Iluminacion`, `Voltaje`, `Certificado`) VALUES ('"+TxtId.getText()+"','"+TxtModelo.getText()+"','"+Ti+"','"+Ta+"','"+Il+"','"+Vo+"','"+Ce+"')");
           ps.executeUpdate();
            TxtDescripcion.setEditable(true);
           JOptionPane.showMessageDialog(null, "Fuente de alimentacion  agregado" );
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
    void Actualizar()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
        ResultSet st,lk,sa,rt,gf;
        Statement cn,kl,as,tr,fg;
        int Ti=0,Ta=0,Il=0,Vo=0,Ce=0;
         String Tipo="",Tamaño="",Iluminacion="",Voltaje="",Certificado="";
         Tipo=ComboTipo.getSelectedItem().toString();
         Tamaño=ComboTamaño.getSelectedItem().toString();
         Iluminacion=ComboIluminacion.getSelectedItem().toString();
         Voltaje=ComboVoltaje.getSelectedItem().toString();
         Certificado=ComboCertificado.getSelectedItem().toString();
        try
         {
         cn = reg.createStatement();//Tipo
         st =cn.executeQuery("SELECT `Id` FROM `Tipo_Fuente` WHERE  `Descripcion`='"+Tipo+"' ");
          while(st.next())
          {
              kl= reg.createStatement();//Tamaño
              lk= kl.executeQuery("SELECT `Id`  FROM `Tamaño_Fuente` WHERE `Descripcion`='"+Tamaño+"'");
              lk.next();
              as= reg.createStatement();//Iluminacion
              sa= as.executeQuery("SELECT `Id`  FROM `Iluminacion` WHERE `Descripcion`='"+Iluminacion+"'");
              sa.next();
              tr= reg.createStatement();//Voltaje
              rt= tr.executeQuery("SELECT `Id`  FROM `Voltajes` WHERE `Descripcion`='"+Voltaje+"'");
              rt.next();
              fg= reg.createStatement();//Certificado
              gf= fg.executeQuery("SELECT `Id`  FROM `Certificado_Fuente` WHERE `Descripcion`='"+Certificado+"'");
              gf.next();
              Ti=Integer.parseInt(st.getString(1));
              Ta=Integer.parseInt(lk.getString(1));
              Il=Integer.parseInt(sa.getString(1));
              Vo=Integer.parseInt(rt.getString(1));
              Ce=Integer.parseInt(gf.getString(1));
          }
        PreparedStatement ps = reg.prepareStatement("UPDATE `fuente_alimentacio` SET `Tipo`='"+Ti+"',`Tamaño`='"+Ta+"',`Iluminacion`='"+Il+"',`Voltaje`='"+Vo+"',`Certificado`='"+Ce+"'  WHERE `fuente_alimentacio`.`Descripcion`='"+TxtModelo.getText()+"'");
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Fuente modificada");
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
        PreparedStatement ps = reg.prepareStatement("DELETE FROM `fuente_alimentacio`  WHERE  `Descripcion` ='"+TxtModelo.getText()+"'");
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Fuente Eliminada");
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
        ComboTipo = new javax.swing.JComboBox<>();
        ComboTamaño = new javax.swing.JComboBox<>();
        ComboIluminacion = new javax.swing.JComboBox<>();
        BtBuscar = new javax.swing.JButton();
        ComboVoltaje = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        ComboCertificado = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();

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
                "Id", "Modelo"
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
                "Id", "Marca", "Modelo", "Tipo", "Tamaño", "Iluminacion", "Voltaje", "Certificado"
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
        jLabel4.setText("Tipo:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Tamaño:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Iluminacion:");

        ComboTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboTipoActionPerformed(evt);
            }
        });

        ComboTamaño.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboTamañoActionPerformed(evt);
            }
        });

        ComboIluminacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboIluminacionActionPerformed(evt);
            }
        });

        BtBuscar.setText("Buscar");
        BtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBuscarActionPerformed(evt);
            }
        });

        ComboVoltaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboVoltajeActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Voltaje:");

        ComboCertificado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboCertificadoActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Certificado:");

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
                        .addComponent(ComboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ComboTamaño, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ComboIluminacion, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 940, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 6, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(74, 74, 74)
                                .addComponent(BtInserta, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ComboVoltaje, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(BtActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(BtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)
                                .addComponent(BtEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ComboCertificado, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
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
                    .addComponent(ComboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboTamaño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ComboIluminacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(ComboCertificado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(ComboVoltaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtActualizar)
                    .addComponent(BtInserta)
                    .addComponent(BtMenu)
                    .addComponent(BtEliminar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        // TODO add your handling code here:
    }//GEN-LAST:event_Table2MouseClicked

    private void Table2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table2MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Table2MousePressed

    private void ComboTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboTipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboTipoActionPerformed

    private void ComboTamañoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboTamañoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboTamañoActionPerformed

    private void ComboIluminacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboIluminacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboIluminacionActionPerformed

    private void BtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtBuscarActionPerformed

    private void ComboVoltajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboVoltajeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboVoltajeActionPerformed

    private void ComboCertificadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboCertificadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboCertificadoActionPerformed

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
            java.util.logging.Logger.getLogger(Fuente_Alimentacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Fuente_Alimentacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Fuente_Alimentacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Fuente_Alimentacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Fuente_Alimentacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtActualizar;
    private javax.swing.JButton BtBuscar;
    private javax.swing.JButton BtEliminar;
    private javax.swing.JButton BtInserta;
    private javax.swing.JButton BtMenu;
    private javax.swing.JComboBox<String> ComboCertificado;
    private javax.swing.JComboBox<String> ComboIluminacion;
    private javax.swing.JComboBox<String> ComboTamaño;
    private javax.swing.JComboBox<String> ComboTipo;
    private javax.swing.JComboBox<String> ComboVoltaje;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
