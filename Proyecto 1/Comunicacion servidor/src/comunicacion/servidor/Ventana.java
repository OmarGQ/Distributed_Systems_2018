/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comunicacion.servidor;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.JScrollPane;

/**
 *
 * @author Alumnos
 */
public class Ventana extends JFrame{//servidor
    private String portn, text, ipn;
    int ip;
    private JTextField texport, mensaje;
    private JTextArea historial;
    private JButton start, finish, send;
    private JLabel port, message;
    Hilo hilo;
    
    public Ventana()
    {
        super();//se declara como superclase
        start=new JButton("Iniciar");
        start.setLocation(300,30);
        start.setSize(100,30);
        finish=new JButton("Detener");
        finish.setLocation(450,30);
        finish.setSize(100,30);
        finish.setEnabled(false);
        send=new JButton("Enviar");
        send.setLocation(650,300);
        send.setSize(100,30);
        send.setEnabled(false);
        texport=new JTextField();
        texport.setLocation(150,30);
        texport.setSize(100,30);
        historial=new JTextArea();
        historial.setEditable(false);
        mensaje=new JTextField();
        mensaje.setLocation(200,300);
        mensaje.setSize(400,30);
        port=new JLabel("puerto:");
        port.setLocation(100,30);
        port.setSize(100,30);
        message=new JLabel("mansaje:");
        message.setLocation(100,300);
        message.setSize(100,30);

        JScrollPane scroll = new JScrollPane(historial);
        
        scroll.setLocation(100,70);
        scroll.setSize(600,200);

        add(scroll);
        
        start.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent ae) {
                portn = texport.getText();
                texport.setEnabled(false);
                start.setEnabled(false);
                finish.setEnabled(true);
                send.setEnabled(true);
                hilo=new Hilo(portn);
                hilo.start();
            }
            
        });
        
        send.addActionListener(new ActionListener(){
            
            @Override
            public void actionPerformed(ActionEvent ae) {
                text = mensaje.getText();
                mensaje.setText("");
                mensaje.setText("");
                historial.append("\nTu: " + text);
                final int PUERTO_SERVIDOR = Integer.parseInt("900");
                //buffer donde se almacenara los mensajes
                byte[] buffer = new byte[1024];
                try {
                    //Obtengo la localizacion de localhost
                    InetAddress direccionServidor = InetAddress.getByName(ipn);

                    //Creo el socket de UDP
                    DatagramSocket socketUDP = new DatagramSocket();

                    String mensajes = text;

                    //Convierto el mensaje a bytes
                    buffer = mensajes.getBytes();

                    //Creo un datagrama
                    DatagramPacket pregunta = new DatagramPacket(buffer, buffer.length, direccionServidor, PUERTO_SERVIDOR);

                    //Lo envio con send
                    socketUDP.send(pregunta);

                    //Preparo la respuesta
                    DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);

                    //cierro el socket
                    socketUDP.close();

                } catch (SocketException ex) {
                    Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnknownHostException ex) {
                    Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        
        finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                texport.setEnabled(true);
                start.setEnabled(true);
                finish.setEnabled(false);
                send.setEnabled(false);
                hilo.stop();
            }
        });
      
        setLayout(null);
        
        add(start);
        add(finish);
        add(send);
        add(texport);
        add(mensaje);
        add(port);
        add(message);
        
        setSize(800, 400);//se define el tamaño de pantalla
        Toolkit tk;
        tk=Toolkit.getDefaultToolkit ();
        Dimension tamPant=tk.getScreenSize();
        setLocation((tamPant.width-getSize().width)/2,(tamPant.height-getSize().height)/2);
        setTitle("Comunicacion Servidor");//se define el nombre de la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);//se define lo cuando se ciierre la vantana se termine el proceso
        setResizable(false);//no permite que el usuario modifique el tamaño de la ventana
        setVisible(true);//para que sea visible (siempre va al final
    }

    public class Hilo extends Thread {
            String portn;
        public Hilo(String puerto) {
            super(puerto);
            portn = puerto;
        }
        public void run(){
                final int PUERTO = Integer.parseInt(portn);
                byte[] buffer = new byte[1024];

                try {
                    //Creacion del socket
                    DatagramSocket socketUDP = new DatagramSocket(PUERTO);

                    //Siempre atendera peticiones
                    while (true) {

                        //Preparo la respuesta
                        DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);

                        //Recibo el datagrama
                        socketUDP.receive(peticion);

                        //Convierto lo recibido y mostrar el mensaje
                        String mensaje = new String(peticion.getData());

                        //Obtengo el puerto y la direccion de origen
                        //Sino se quiere responder, no es necesario
                        int puertoCliente = peticion.getPort();
                        InetAddress direccion = peticion.getAddress();
                        ipn = direccion.getHostAddress();
                        historial.append("\n"+ipn+": "+ mensaje);
                    }

                } catch (SocketException ex) {
                    Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
}
