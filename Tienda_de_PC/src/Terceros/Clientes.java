
package Terceros;

import Mantenimientos.*;
import Libre.Conexion;
import java.awt.Color;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import javax.swing.text.*;

/**
 *
 * @author score
 */
public class Clientes extends javax.swing.JFrame {


    public Clientes() {
        initComponents();
        this.setLocationRelativeTo(null);
        DesabilitarCampos();
        TraerSexo();
        TraerEstadoCivil();
        TraerIdentificacion();
        TraerNacionalidad();
        Traer();
     }
    
    Conexion con;
  
    void InsertarTercero()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       ResultSet st;
       Statement cn;
       int Id=0,Estado=1;
        try
         {
        cn = reg.createStatement();
        st =cn.executeQuery("SELECT MAX(`IdTercero`) FROM `tercero`");
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
        }   
           }

           PreparedStatement ps = reg.prepareStatement("INSERT INTO `tercero`(`IdTercero`, `Nombre`, `Fecha`, `Estado`) VALUES ('"+Id+"','"+TxtNombre.getText()+"',now(),'"+Estado+"')");
           ps.executeUpdate();
           HabilitarCampos();
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
    void llenarId()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       ResultSet st;
       Statement cn;
       int Id=0;
        try
         {
        cn = reg.createStatement();
        st =cn.executeQuery("SELECT MAX(`IdTercero`) FROM `tercero`");
         while(st.next())
          {
            Id=Integer.parseInt(st.getString(1));
            TxtId.setText(String.valueOf(Id));
            TxtNombre.requestFocus(true);
           }
           
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
    void TraerSexo()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM  `sexo` order by Id ASC limit 500");
         ComboSexo.removeAllItems();
          while(st.next())
          {
             ComboSexo.addItem(st.getString(2));
         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
    void TraerEstadoCivil()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM  `estadocivil` order by Id ASC limit 500");
         ComboEstadoCivil.removeAllItems();
          while(st.next())
          {
             ComboEstadoCivil.addItem(st.getString(2));
         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
    
    void TraerIdentificacion()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM  `tipoidentificacion` order by Id ASC limit 500");
         ComboIdentificacion.removeAllItems();
          while(st.next())
          {
             ComboIdentificacion.addItem(st.getString(2));
         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
    void TraerNacionalidad()
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
         
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT * FROM  `nacionalidades` order by Id ASC limit 500");
         ComboNacionalidad.removeAllItems();
          while(st.next())
          {
             ComboNacionalidad.addItem(st.getString(2));
         }
        }
        catch (SQLException e)
        {
         JOptionPane.showMessageDialog(null, "Error"+e );
        }
    }
        
    void DesabilitarCampos()
    {
        TxtNombre.requestFocus(true);
        TxtId.setEditable(false);
        TxtApellido.setEditable(false);
        TxtRazonSocial.setEditable(false);
        TxtId.setBackground(Color.GRAY);
        TxtApellido.setBackground(Color.GRAY);
       TxtRazonSocial.setBackground(Color.GRAY);
        JOptionPane.showMessageDialog(null, "Por favor Ingrese el nombre del cliente" );
    }
        void HabilitarCampos()
    {
        TxtId.setEditable(true);
        TxtApellido.setEditable(true);
        TxtIdentificacion.setEditable(true);
        TxtRazonSocial.setEditable(true);
        TxtId.setBackground(Color.WHITE);
        TxtApellido.setBackground(Color.WHITE);
        TxtIdentificacion.setBackground(Color.WHITE);
        TxtRazonSocial.setBackground(Color.WHITE);
    }
        
        void InsertarPersonas()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       ResultSet st,mw,kl;
       Statement cn,lk,jh;
       int Sexo=0,EstadoCivil=0,nacionalidadc=0;
       String SexoC="",EstadoCivilC="",Nacionalidad="";
         SexoC=ComboSexo.getSelectedItem().toString();
         EstadoCivilC=ComboEstadoCivil.getSelectedItem().toString();
         Nacionalidad=ComboNacionalidad.getSelectedItem().toString();
         SimpleDateFormat Formato = new SimpleDateFormat("yyyy-mm-dd");
       try
         {
        cn = reg.createStatement();
        st =cn.executeQuery("SELECT Id FROM `sexo` where Descripcion='"+SexoC+"'");
         while(st.next())
          {
              Sexo=Integer.parseInt(st.getString(1));
                lk = reg.createStatement();
                mw = lk.executeQuery("SELECT Id FROM  `estadocivil` WHERE Descripcion='"+EstadoCivilC+"'");
                mw.next();
                EstadoCivil=Integer.parseInt(mw.getString(1));
                jh = reg.createStatement();
                kl = jh.executeQuery("SELECT `Id` FROM `nacionalidades`  WHERE `Nacionalidad`='"+Nacionalidad+"'");
                kl.next();
              nacionalidadc=Integer.parseInt(kl.getString(1));
          }
           PreparedStatement ps = reg.prepareStatement("INSERT INTO `persona`(`Id`, `Apellido`, `Sexo`, `Nacionalidad`, `EstadoCivil`, `Nacimiento`)  VALUES ('"+TxtId.getText()+"','"+TxtApellido.getText()+"','"+Sexo+"','"+nacionalidadc+"','"+EstadoCivil+"','"+Formato.format(FechaN.getDate())+"')");
           ps.executeUpdate();
           InsertarIdentificacion();
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }    

        void InsertarIdentificacion()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       ResultSet st;
       Statement cn;
       int Id=0;
       String Identificacion="";
       Identificacion = ComboIdentificacion.getSelectedItem().toString();
      try
         {
        cn = reg.createStatement();
        st =cn.executeQuery("SELECT Id FROM `tipoidentificacion` where Descripcion='"+Identificacion+"'");
         while(st.next())
          {
           Id=Integer.parseInt(st.getString(1));
          }
           String text = TxtIdentificacion.getText();
           String digits = text.replaceAll("[^0-9.]", "");
          
           PreparedStatement ps = reg.prepareStatement("INSERT INTO `identificacion`(`Id`, `IdTipoIdentificacion`, `idTercero`)  VALUES ('"+digits+"','"+String.valueOf(Id)+"','"+TxtId.getText()+"')");
           ps.executeUpdate();
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    } 

    void InsertarCliente()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
      try
         {
           PreparedStatement ps = reg.prepareStatement("INSERT INTO `cliente`(`Id`, `RazonSocial`, `Nota`)  VALUES ('"+TxtId.getText()+"','"+TxtRazonSocial.getText()+"','"+TxtNota.getText()+"')");
           ps.executeUpdate();
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }  
    
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
        st =cn.executeQuery("SELECT `Id`,`Apellido`, `Sexo`, `Nacionalidad`, `EstadoCivil`, `Nacimiento` FROM `persona`");
         while(st.next())
          {
                Vector v = new Vector();
                
                lk = reg.createStatement();//Personas
                mw =lk.executeQuery("SELECT `IdTercero`, `Nombre` FROM `tercero` WHERE `IdTercero`='"+st.getInt(1)+"'  ");
                mw.next();
                jh = reg.createStatement();//Sexo
                kl =jh.executeQuery("SELECT `Descripcion` FROM `sexo` WHERE `Id`='"+st.getString(3)+"'");
                kl.next();
                sd = reg.createStatement();//Estado Civil
                ds =sd.executeQuery("SELECT `Descripcion` FROM `estadocivil` WHERE `Id`='"+st.getString(5)+"'");
                ds.next();
                as = reg.createStatement();//Razon Social
                sa =as.executeQuery("SELECT `Id`, `RazonSocial`, `Nota` FROM `cliente` WHERE `Id`='"+st.getInt(1)+"'");
                sa.next();
                v.add(st.getString(1));//Id
                v.add(mw.getString(2));//Nombre
                v.add(st.getString(2));//Apellido
                v.add(kl.getString(1));//Sexo
                v.add(ds.getString(1));//Estado Civil
                v.add(sa.getString(2));//Razon Social
                modelo.addRow(v);
                Table1.setModel(modelo);
          }
         }catch(SQLException ex)
         {
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
    void Comprobacion()
    {
        if(TxtId.getText().equals("")){
        JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
        TxtId.requestFocus(true);
        TxtId.setBackground(Color.YELLOW);
        return;
        }
        else {
            TxtId.setBackground(Color.WHITE);
        }
        if(TxtNombre.getText().equals("")){
        JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
        TxtNombre.requestFocus(true);
        TxtNombre.setBackground(Color.YELLOW);
        return;
        }
        else {
            TxtNombre.setBackground(Color.WHITE);
        }
        if(TxtApellido.getText().equals("")){
        JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
        TxtApellido.requestFocus(true);
        TxtApellido.setBackground(Color.YELLOW);
        return;
        }
        else {
            TxtApellido.setBackground(Color.WHITE);
        }
        if(TxtIdentificacion.getText().equals("")){
        JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
        TxtIdentificacion.requestFocus(true);
        TxtIdentificacion.setBackground(Color.YELLOW);
        return;
        }
        else {
            TxtIdentificacion.setBackground(Color.WHITE);
        }
        if(TxtRazonSocial.getText().equals("")){
        JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
        TxtRazonSocial.requestFocus(true);
        TxtRazonSocial.setBackground(Color.YELLOW);
        return;
        }
        else {
            TxtRazonSocial.setBackground(Color.WHITE);
        }
        if(TxtNota.getText().equals("")){
        JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
        TxtNota.requestFocus(true);
        TxtNota.setBackground(Color.YELLOW);
        return;
        }
        else {
            TxtNota.setBackground(Color.WHITE);
        }
        
    }
    
        void selectTable() throws SQLException
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       ResultSet st,mw,kl,sa,ds;
       Statement cn,lk,jh,as,sd;
       String Sexo, Nacionalidad, EstadoCivil;
     try{
       int fila = Table1.getSelectedRow();
       if(fila >=0)
       {
           TxtId.setText(Table1.getValueAt(fila, 0).toString());
           TxtNombre.setText(Table1.getValueAt(fila, 1).toString());
           TxtApellido.setText(Table1.getValueAt(fila, 2).toString());
           TxtRazonSocial.setText(Table1.getValueAt(fila, 5).toString());
        cn = reg.createStatement();
        st =cn.executeQuery("SELECT `Id`,`Apellido`, `Sexo`, `Nacionalidad`, `EstadoCivil`, `Nacimiento` FROM `persona` WHERE Id='"+TxtId.getText()+"'");
       while(st.next())
          {
              Sexo=st.getString(3);
              Nacionalidad=st.getString(4);
              EstadoCivil=st.getString(5);
              ComboSexo.setSelectedItem(Sexo);
              ComboNacionalidad.setSelectedItem(Nacionalidad);
              ComboEstadoCivil.setSelectedItem(EstadoCivil);
          }
       }
       

       }catch (HeadlessException ex){

             JOptionPane.showMessageDialog(null, "Error: "+ex+"\nInténtelo nuevamente", " .::Error En la Operacion::." ,JOptionPane.ERROR_MESSAGE);

       }     
    }
 void Actualizar()
    {
       con = new Conexion();
       Connection reg = con.getConnection();
       try
         {
        PreparedStatement ps = reg.prepareStatement("UPDATE `sexo` SET `Descripcion` = '"+TxtNombre.getText()+"' WHERE `unidad_medida`.`Id` ='"+TxtId.getText()+"'");
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "sexo modificado");
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
        PreparedStatement ps = reg.prepareStatement("DELETE FROM `sexo` WHERE  Id='"+TxtId.getText()+"'");
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "sexo Eliminado");
          }catch(SQLException ex){
          JOptionPane.showMessageDialog(null, "Error"+ex );
         }
    }
    
    void Limpiar()
    {
        TxtId.setText("");
        TxtNombre.setText("");
        TxtApellido.setText("");
        TxtIdentificacion.setText("");
        TxtRazonSocial.setText("");
        TxtNota.setText("");
        FechaN.setCalendar(null);
        TxtNombre.requestFocus(true);
        TxtId.setBackground(Color.WHITE);
        TxtNombre.setBackground(Color.WHITE);
        TxtApellido.setBackground(Color.WHITE);;
        TxtIdentificacion.setBackground(Color.WHITE);
        TxtRazonSocial.setBackground(Color.WHITE);
        TxtNota.setBackground(Color.WHITE);
        JOptionPane.showMessageDialog(null, "Los campos has sido limpiardo");
    }
    
    void Desconectar()
    {
         con.desconectar();
    }
    
    void Cargardatos()//primera dos colunmas y las otras tabla clientes
    {
        con = new Conexion();
        Connection reg = con.getConnection();
        ResultSet st;
        Statement cn;
        
       try
        {
         cn = reg.createStatement();
         st =cn.executeQuery("SELECT `Descripcion` FROM `sexo` WHERE id='"+TxtId.getText()+"' ");
          while(st.next())
          {
            TxtNombre.setText(st.getString(1));   
             JOptionPane.showMessageDialog(null, "Este id ya existe " );
             TxtId.requestFocus(true);
             Limpiar();
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TxtId = new javax.swing.JTextField();
        TxtNombre = new javax.swing.JTextField();
        BtInserta = new javax.swing.JButton();
        BtEliminar = new javax.swing.JButton();
        BtActualizar = new javax.swing.JButton();
        BtMenu = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        TxtApellido = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        ComboSexo = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        ComboEstadoCivil = new javax.swing.JComboBox<>();
        FechaN = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        ComboIdentificacion = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        TxtRazonSocial = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        TxtNota = new javax.swing.JTextField();
        TxtIdentificacion = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        ComboNacionalidad = new javax.swing.JComboBox<>();
        BtInserta1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Id: ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Nombre:");

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

        TxtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtNombreActionPerformed(evt);
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
                "Id", "Nombre", "Apellido", "Sexo", "Estado Civil", "Razon Social"
            }
        ));
        jScrollPane1.setViewportView(Table1);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Apellido:");

        TxtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtApellidoActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Sexo:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Estado Civil:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Fecha de Nacimiento:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Tipo de identificicacion:");

        ComboIdentificacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ComboIdentificacionMouseClicked(evt);
            }
        });
        ComboIdentificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboIdentificacionActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Identificicacion:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Razon Social:");

        TxtRazonSocial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtRazonSocialActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Nota:");

        try {
            TxtIdentificacion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(###)-(#######)-(#)")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        TxtIdentificacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtIdentificacionActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Nacionalidad:");

        ComboNacionalidad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ComboNacionalidadMouseClicked(evt);
            }
        });
        ComboNacionalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboNacionalidadActionPerformed(evt);
            }
        });

        BtInserta1.setText("Limpiar");
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ComboSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(126, 126, 126)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TxtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(TxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel5)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(ComboEstadoCivil, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(jLabel3)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(TxtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(ComboIdentificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(29, 29, 29)
                                                .addComponent(jLabel8)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(TxtIdentificacion, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TxtNota, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(15, 15, 15)))))
                        .addGap(0, 68, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(BtInserta, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BtActualizar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(BtMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BtEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(BtInserta1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(FechaN, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(ComboNacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(TxtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6))
                            .addComponent(FechaN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(TxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(ComboIdentificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(TxtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(TxtIdentificacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(ComboSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(TxtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(ComboNacionalidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtActualizar)
                            .addComponent(BtMenu))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BtInserta)
                            .addComponent(BtEliminar))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(ComboEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(TxtNota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BtInserta1)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtInsertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtInsertaActionPerformed
       Comprobacion();
       InsertarPersonas();
       InsertarCliente();
       Limpiar();
       Traer();
    }//GEN-LAST:event_BtInsertaActionPerformed

    private void BtActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtActualizarActionPerformed
        
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

    private void BtEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtEliminarActionPerformed

    }//GEN-LAST:event_BtEliminarActionPerformed

    private void TxtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtNombreActionPerformed
        if(TxtNombre.getText().equals("")){
        JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
        TxtNombre.requestFocus(true);
        TxtNombre.setBackground(Color.YELLOW);
        return;
        }
        else {
            TxtNombre.setBackground(Color.WHITE);
        }
        TxtApellido.requestFocus(true);  
        InsertarTercero();
    }//GEN-LAST:event_TxtNombreActionPerformed

    private void TxtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtApellidoActionPerformed
        if(TxtApellido.getText().equals("")){
        JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
        TxtApellido.requestFocus(true);
        TxtApellido.setBackground(Color.YELLOW);
        return;
        }
        else {
            TxtApellido.setBackground(Color.WHITE);
        }
        TxtIdentificacion.requestFocus(true); 
    }//GEN-LAST:event_TxtApellidoActionPerformed

    private void ComboIdentificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboIdentificacionActionPerformed
       
    }//GEN-LAST:event_ComboIdentificacionActionPerformed

    private void ComboIdentificacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ComboIdentificacionMouseClicked
        //nada 
    }//GEN-LAST:event_ComboIdentificacionMouseClicked

    private void TxtIdentificacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtIdentificacionActionPerformed
        if(TxtIdentificacion.getText().equals("")){
        JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
        TxtIdentificacion.requestFocus(true);
        TxtIdentificacion.setBackground(Color.YELLOW);
        return;
        }
        else {
            TxtIdentificacion.setBackground(Color.WHITE);
        }
        
        TxtRazonSocial.requestFocus(true); 
    }//GEN-LAST:event_TxtIdentificacionActionPerformed

    private void TxtRazonSocialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtRazonSocialActionPerformed
        if(TxtRazonSocial.getText().equals("")){
        JOptionPane.showMessageDialog(null, "El campo de Id esta vacio");
        TxtRazonSocial.requestFocus(true);
        TxtRazonSocial.setBackground(Color.YELLOW);
        return;
        }
        else {
            TxtRazonSocial.setBackground(Color.WHITE);
        }
        TxtNota.requestFocus(true);  
    }//GEN-LAST:event_TxtRazonSocialActionPerformed

    private void ComboNacionalidadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ComboNacionalidadMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboNacionalidadMouseClicked

    private void ComboNacionalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboNacionalidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboNacionalidadActionPerformed

    private void BtInserta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtInserta1ActionPerformed
        Limpiar();
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
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Clientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Clientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtActualizar;
    private javax.swing.JButton BtEliminar;
    private javax.swing.JButton BtInserta;
    private javax.swing.JButton BtInserta1;
    private javax.swing.JButton BtMenu;
    private javax.swing.JComboBox<String> ComboEstadoCivil;
    private javax.swing.JComboBox<String> ComboIdentificacion;
    private javax.swing.JComboBox<String> ComboNacionalidad;
    private javax.swing.JComboBox<String> ComboSexo;
    private com.toedter.calendar.JDateChooser FechaN;
    private javax.swing.JTable Table1;
    private javax.swing.JTextField TxtApellido;
    private javax.swing.JTextField TxtId;
    private javax.swing.JFormattedTextField TxtIdentificacion;
    private javax.swing.JTextField TxtNombre;
    private javax.swing.JTextField TxtNota;
    private javax.swing.JTextField TxtRazonSocial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
