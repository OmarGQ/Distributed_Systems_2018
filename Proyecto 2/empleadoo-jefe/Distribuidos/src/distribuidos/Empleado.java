/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distribuidos;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Alumnos
 */
public class Empleado extends JFrame{
    private JTextField dato;
    private JButton btn;
    private JLabel message, etiqueta;
    private String id;
    public Connection con;
    
    public Empleado(){
        try{
        System.out.println("Creando conexion");
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://sql9.freesqldatabase.com:3306/sql9287688", "sql9287688" , "RfccTfjCEe");
        System.out.println("Conexion exitosa");
        }
        catch(SQLException e){
        System.out.println("Error en MySQL");
        System.out.println("Se ha encontrado el siguiente error " + e.getMessage());
        }
        catch(ClassNotFoundException e){
        e.printStackTrace();
        }
        catch (Exception e){
        System.out.println("Se ha encontrado el siguiente error " + e.getMessage());
        }
        
        dato = new JTextField();
        dato.setSize(200, 30);
        dato.setLocation(50, 50);
        
        btn = new JButton("Registrar");
        btn.setSize(150, 30);
        btn.setLocation(400, 300);
        
        message = new JLabel();
        message.setSize(200, 30);
        message.setLocation(50, 100);
        
        etiqueta = new JLabel("Ingrese su ID");
        etiqueta.setSize(200, 30);
        etiqueta.setLocation(50, 20);
        
        setLayout(null);
        
        btn.addActionListener(new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent e) {
                id = dato.getText();
                String sql ="INSERT INTO chequeos (ID, hf) VALUES ('"+id+"', NOW())";
                try {
                    Statement st = con.createStatement();
                    st.executeUpdate(sql);
                    message.setText("Exito");
                } catch (SQLException ex) {
                    System.out.println("Se ha encontrado el siguiente error " + ex.getMessage());
                }
            }
        });
        
        add(dato);
        add(btn);
        add(message);
        add(etiqueta);
        
        setSize(600,400);//tamano de la pantalla
        Toolkit tk;
        tk=Toolkit.getDefaultToolkit ();
        Dimension tamPant=tk.getScreenSize();
        setLocation((tamPant.width-getSize().width)/2,(tamPant.height-getSize().height)/2);
        setTitle("Empleado");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
    public void capturar(){
        id = dato.getText();
        if(dato.getText() == ""){
            message.setText("Ingrese un Id");
        }
        String sql="insert into viaje () values ()";
        try {
            Statement st = con.createStatement();
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            //Debug.log(Level.SEVERE, null, ex);
        }
    }
}
