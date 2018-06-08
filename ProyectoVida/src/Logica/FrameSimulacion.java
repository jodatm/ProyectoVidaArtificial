package Logica;

/**
 *
 * @author Stephany Rivera
 */
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class FrameSimulacion extends JFrame {
	
	JPanel contentPane;
	public static JTextField textFieldPasto;
	public static JTextField textFieldCantidadMaxCesped;
	public static JTextField textFieldCantidadO;
	JTextField textFieldComidaO;
	public static JTextField textFieldVidaO;
	JTextField textFieldAparearO;
	public static JTextField textFieldCantidadL;
	/////////////////////////////////////////////////////////
        // Autor: jonathan loaiza rosero
	public static JTextField textFieldComidaL; //se habilita esta variable para acceder desde otras clases.
	////////////////////////////////////////////////////////
	

	public FrameSimulacion() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 10, 200, 700);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		interfaz();		
                setVisible(true);       
                setResizable(false);	
	}
		
	
	public void interfaz(){
		
		JPanel panelDatos = new JPanel();
		panelDatos.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelDatos.setBackground(Color.WHITE);
		panelDatos.setBounds(10, 27, 172, 560);
		contentPane.add(panelDatos);
		panelDatos.setLayout(null);
		
		JLabel labelTitulo = new JLabel("Configuraci\u00F3n C\u00E9sped");
		labelTitulo.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelTitulo.setBounds(10, 11, 132, 14);
		panelDatos.add(labelTitulo);
		
                
                
                    
		JPanel labeltiempoPasto = new JPanel();
		labeltiempoPasto.setBackground(Color.WHITE);
		labeltiempoPasto.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Tiempo aparici\u00F3n ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 128, 128)));
		labeltiempoPasto.setBounds(10, 36, 152, 43);
		panelDatos.add(labeltiempoPasto);
		labeltiempoPasto.setLayout(null);
		
		textFieldPasto = new JTextField();
		textFieldPasto.setBounds(10, 17, 57, 20);
		labeltiempoPasto.add(textFieldPasto);
		textFieldPasto.setColumns(10);
		
                JPanel labelMaxPasto = new JPanel();
		labelMaxPasto.setBackground(Color.WHITE);
		labelMaxPasto.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "M\u00E1xima Cantidad Pasto", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 128, 128)));
		labelMaxPasto.setBounds(10, 80, 152, 43);
		panelDatos.add(labelMaxPasto);
		labelMaxPasto.setLayout(null);
                
                //Campo para digitar la máxima cantidad de pasto en el ambiente
                textFieldCantidadMaxCesped = new JTextField();
                textFieldCantidadMaxCesped.setColumns(10);
		textFieldCantidadMaxCesped.setBounds(10, 17, 57, 20);
		labelMaxPasto.add(textFieldCantidadMaxCesped);
                
                
		JLabel labelTitulo1 = new JLabel("Configuraci\u00F3n Ovejas");
		labelTitulo1.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelTitulo1.setBounds(10, 131, 132, 14);
		panelDatos.add(labelTitulo1);
		
		JPanel labelCantidad = new JPanel();
		labelCantidad.setLayout(null);
		labelCantidad.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Cantidad", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 128, 128)));
		labelCantidad.setBackground(Color.WHITE);
		labelCantidad.setBounds(10, 143, 152, 43);
		panelDatos.add(labelCantidad);
		
		textFieldCantidadO = new JTextField();
		textFieldCantidadO.setColumns(10);
		textFieldCantidadO.setBounds(10, 17, 57, 20);
		labelCantidad.add(textFieldCantidadO);
		
                
                    
		JPanel labelTiempoComida = new JPanel();
		labelTiempoComida.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Tiempo m\u00E1x sin comer", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 128, 128)));
		labelTiempoComida.setLayout(null);		
		labelTiempoComida.setBackground(Color.WHITE);
		labelTiempoComida.setBounds(10, 184, 152, 43);
		panelDatos.add(labelTiempoComida);
		
		textFieldComidaO = new JTextField();
		textFieldComidaO.setColumns(10);
		textFieldComidaO.setBounds(10, 17, 57, 20);
		labelTiempoComida.add(textFieldComidaO);
		
		JPanel labelVida = new JPanel();
		labelVida.setLayout(null);
		labelVida.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Tiempo de vida", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 128, 128)));
		labelVida.setBackground(Color.WHITE);
		labelVida.setBounds(10, 237, 152, 43);
		panelDatos.add(labelVida);
		
		textFieldVidaO = new JTextField();
		textFieldVidaO.setColumns(10);
		textFieldVidaO.setBounds(10, 17, 57, 20);
		labelVida.add(textFieldVidaO);
		
		JPanel labelAparearse = new JPanel();
		labelAparearse.setLayout(null);
		labelAparearse.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Tiempo de aparearse", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 128, 128)));
		labelAparearse.setBackground(Color.WHITE);
		labelAparearse.setBounds(10, 291, 152, 43);
		panelDatos.add(labelAparearse);
		
		textFieldAparearO = new JTextField();
		textFieldAparearO.setColumns(10);
		textFieldAparearO.setBounds(10, 17, 57, 20);
		labelAparearse.add(textFieldAparearO);
		
		JLabel labelTitulo2 = new JLabel("Configuraci\u00F3n Lobos");
		labelTitulo2.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelTitulo2.setBounds(10, 343, 132, 14);
		panelDatos.add(labelTitulo2);
		
		JPanel labelCantidad1 = new JPanel();
		labelCantidad1.setLayout(null);
		labelCantidad1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Cantidad", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 128, 128)));
		labelCantidad1.setBackground(Color.WHITE);
		labelCantidad1.setBounds(10, 370, 152, 43);
		panelDatos.add(labelCantidad1);
		
		textFieldCantidadL = new JTextField();
		textFieldCantidadL.setColumns(10);
		textFieldCantidadL.setBounds(10, 17, 57, 20);
		labelCantidad1.add(textFieldCantidadL);
		
		JPanel labelComida1 = new JPanel();
		labelComida1.setLayout(null);
		labelComida1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Tiempo Alimentaci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 128, 128)));
		labelComida1.setBackground(Color.WHITE);
		labelComida1.setBounds(10, 420, 152, 43);
		panelDatos.add(labelComida1);
		
		textFieldComidaL = new JTextField();
		textFieldComidaL.setColumns(10);
		textFieldComidaL.setBounds(10, 17, 57, 20);
		labelComida1.add(textFieldComidaL);
		
		JButton buttonIniciar = new JButton("Iniciar ");
		buttonIniciar.setBounds(10, 473, 132, 23);
		panelDatos.add(buttonIniciar);
                textFieldCantidadL.setText("5");
                textFieldCantidadO.setText("10");
//                textFieldCantidadMaxCesped.setText("20");
//                textFieldPasto.setText("10");
//                textFieldComidaL.setText("5");
//                textFieldComidaO.setText("10");
//                textFieldVidaO.setText("5");
//                textFieldAparearO.setText("10");
                
                buttonIniciar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    iniciar();
                }
            });
	}
                public void iniciar(){
                    MostrarSimulacion simu = new MostrarSimulacion();
                    simu.movimiento();
                    dispose();
                }
		

}
