package Logica;

import java.awt.*;
import java.awt.image.*;
import java.net.URL;
import java.util.*;
import javax.swing.*;

public class MostrarSimulacion extends JFrame {

    Simulacion laSimulacion = new Simulacion();
    ArrayList<Oveja> lasOvejas = new ArrayList<>();
    ArrayList<Lobo> losLobos = new ArrayList<>();
    ArrayList<Pasto> elPasto = new ArrayList<>();
    Thread hiloUno;

    Thread hiloSegundos; // este hilo controla el contador de segundos de la simulacion global
    JFrame VentanaSegundos;
    JTextField textoSegundos;

    Graphics buffer;
    BufferedImage dibujo;
    Image ovejo = new ImageIcon(getClass().getResource("/img/ovejo.png")).getImage();
    Image oveja = new ImageIcon(getClass().getResource("/img/oveja.png")).getImage();
    Image lobo = new ImageIcon(getClass().getResource("/img/lobo.png")).getImage();
    Image pasto = new ImageIcon(getClass().getResource("/img/pasto.png")).getImage();
    Image fondo = new ImageIcon(getClass().getResource("/img/fondo.png")).getImage();
    int cantOvejas;
    int cantLobos;
    int velocidadOvejas = 2; //velocidad de las ovejas
    int velocidadLobos = 2; //velocidad de los lobos

    int cantPasto = 5; //cantidad de pasto que quirenque aparezca cada X tiempo
    int maxCantidadPasto; //cantidad máxima de pasto en el ambiente
    int tiempoPasto;

    //tamano de la ventana
    int anchoVentana = 1200;
    int altoVetana = 700;

    //variable Random
    Random r = new Random();

    public MostrarSimulacion() {

        laSimulacion.setTamanoSimulacion(anchoVentana, altoVetana); //trasnfiera el tamano de ventana a los margenes de la simulacion

        cantOvejas = Integer.parseInt(FrameSimulacion.textFieldCantidadO.getText());
        cantLobos = Integer.parseInt(FrameSimulacion.textFieldCantidadL.getText());
        tiempoPasto = Integer.parseInt(FrameSimulacion.textFieldPasto.getText());
        maxCantidadPasto = Integer.parseInt(FrameSimulacion.textFieldCantidadMaxCesped.getText());

        lasOvejas = laSimulacion.Oveja(cantOvejas);
        losLobos = laSimulacion.Lobo(cantLobos);
        elPasto = laSimulacion.Pasto(cantPasto);
        setTitle("Lobos y Ovejas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(10, 10, anchoVentana, altoVetana);
        dibujo = new BufferedImage(anchoVentana, altoVetana, BufferedImage.TYPE_INT_RGB);
        buffer = dibujo.createGraphics();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        hiloPrincipal();

        repaint();

        //no descomentar ya es esta seccion fue reemplazado por la func hiloPrincipal()
        //la dejo aca solo con fines de entendimiento
        /*hiloUno = new Thread(new Runnable() { 
            
                int segundosEjecucion; // hace las vecs de segundero
                int contador = 0; // cuanta cuantas veces se detien el hilo
                @Override
                public void run() {
                    while (true) {
                        try {
                            moverOveja();
                            moverLobo();
                            repaint();                            
                            
                            if(contador == 20){ 
                                // cuando contador == 20 quiere decir que ha parado 20 veces por un tiempo de 50 milisegundos
                                //lo cual es un segundo (20*50 = 1000 milisegundos), asi que aumenta en un segundo la variable
                                segundosEjecucion++;
                                contador = 0;
                            } 
                            if (segundosEjecucion%3 == 0){
                                elPasto = laSimulacion.Pasto();
                            }
                            contador++;
                            Thread.sleep(50); //se detien por 50 milisegundos,        
                                                                                                               
                        } catch (InterruptedException e) {
                        }
                    }
                }
            });    */
    }

    private void hiloPrincipal() {
        //seccion para mostrar un panel de simulacion de segundos transcurridos para saber
        //en que momento aparece el pasto (por ejmplo)
        //este hilo  tambien debera ejecutar todo a la vez, 
        //creo que esta es la forma de adentro de el, ejecutar los segundos que sean globales a todo

        VentanaSegundos = new JFrame("tiempo");
        VentanaSegundos.setResizable(false);
        VentanaSegundos.setLocation(10, 10);
        VentanaSegundos.setBounds(0, 0, 50, 50);
        textoSegundos = new JTextField("0");
        VentanaSegundos.setBackground(Color.red);
        VentanaSegundos.add(textoSegundos);

        VentanaSegundos.setVisible(true);

        hiloSegundos = new Thread(new Runnable() {

            int segundosEjecucion; // hace las vecs de segundero
            int contador = 0; // cuanta cuantas veces se detiene el hilo

            @Override
            public void run() {
                while (true) {
                    try {
                        moverOveja();
                        moverLobo();

                        if (contador == 20) {
                            // cuando contador == 20 quiere decir que ha parado 20 veces por un tiempo de 50 milisegundos
                            //lo cual es un segundo (20*50 = 1000 milisegundos), asi que aumenta en un segundo la variable segundosEjecucion
                            segundosEjecucion++;
                            contador = 0; // se reinicia el contador

                            System.out.println("segundo: " + segundosEjecucion);
                            textoSegundos.setText("segundo: " + segundosEjecucion);

                            if (segundosEjecucion % tiempoPasto == 0) {//cada X segundos agrega pasto (en este caso 3 segundos)

                                //si la cantidad de pasto alcanza el máximo permitido dejará de producirse 
                                if (elPasto.size() <= maxCantidadPasto) {
                                    agregarPasto();
                                    System.out.println("cantPasto: " + elPasto.size());
                                }
                            }
                        }

                        contador++;
                        Thread.sleep(10); //se detien por 50 milisegundos, 

                        repaint();
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
    }

    public void paint(Graphics g) {
        update(g);
    }

    @Override
    public void update(Graphics g) {
        buffer.drawImage(fondo, 0, 0, this);

        //ovejas
        for (int i = 0; i < lasOvejas.size(); i++) {
            if (i % 2 == 0) {

                //Pinta identificador de la oveja
                g.drawString(String.valueOf(lasOvejas.get(i).getId() + 1), lasOvejas.get(i).getX() + 8, lasOvejas.get(i).getY());
                Font font = new Font("Serif", Font.BOLD, 20);
                g.setFont(font);
                g.setColor(Color.RED);
                //Pinta oveja
                buffer.drawImage(oveja, lasOvejas.get(i).getX(), lasOvejas.get(i).getY(), this);

            } else {
                //Pinta identificador de la oveja
                g.drawString(String.valueOf(lasOvejas.get(i).getId() + 1), lasOvejas.get(i).getX() + 8, lasOvejas.get(i).getY());
                Font font = new Font("Serif", Font.BOLD, 20);
                g.setFont(font);
                g.setColor(Color.RED);
                //Pinta oveja
                buffer.drawImage(ovejo, lasOvejas.get(i).getX(), lasOvejas.get(i).getY(), this);

            }
        }

        //lobos
        for (int j = 0; j < losLobos.size(); j++) {
            buffer.drawImage(lobo, losLobos.get(j).getX(), losLobos.get(j).getY(), this);
        }

        //pasto
        for (int k = 0; k < elPasto.size(); k++) {
            buffer.drawImage(pasto, elPasto.get(k).getX(), elPasto.get(k).getY(), this);
        }
        g.drawImage(dibujo, 0, 0, this);
    }

    public void movimiento() {
        hiloSegundos.start();// inicia el hilo principal
    }

    public void moverOveja() {
        for (int i = 0; i < lasOvejas.size(); i++) {
            int randomX;
            int randomY;

            int[] numerosAleatorios = {-1, 1};
            randomX = r.nextInt(2);
            randomY = r.nextInt(2);

            /*
            *Movimientos aleatorios comprobando los limites del frame
             */
            if (numerosAleatorios[randomX] == 1 && numerosAleatorios[randomY] == 1) {
                lasOvejas.get(i).setX(lasOvejas.get(i).getX() + ((lasOvejas.get(i).getX() >= 1163) ? -velocidadOvejas : velocidadOvejas));
                lasOvejas.get(i).setY(lasOvejas.get(i).getY() + ((lasOvejas.get(i).getY() >= 27) ? -velocidadOvejas : velocidadOvejas));
            } else if (numerosAleatorios[randomX] == 1 && numerosAleatorios[randomY] == -1) {
                lasOvejas.get(i).setX(lasOvejas.get(i).getX() + ((lasOvejas.get(i).getX() >= 1163) ? -velocidadOvejas : velocidadOvejas));
                lasOvejas.get(i).setY(lasOvejas.get(i).getY() - ((lasOvejas.get(i).getY() >= 672) ? velocidadOvejas : -velocidadOvejas));
            } else if (numerosAleatorios[randomX] == -1 && numerosAleatorios[randomY] == -1) {
                lasOvejas.get(i).setX(lasOvejas.get(i).getX() - ((lasOvejas.get(i).getX() >= 5) ? velocidadOvejas : -velocidadOvejas));
                lasOvejas.get(i).setY(lasOvejas.get(i).getY() - ((lasOvejas.get(i).getY() >= 672) ? velocidadOvejas : -velocidadOvejas));
            } else if (numerosAleatorios[randomX] == -1 && numerosAleatorios[randomY] == 1) {
                lasOvejas.get(i).setX(lasOvejas.get(i).getX() - ((lasOvejas.get(i).getX() >= 5) ? velocidadOvejas : -velocidadOvejas));
                lasOvejas.get(i).setY(lasOvejas.get(i).getY() + ((lasOvejas.get(i).getY() >= 27) ? -velocidadOvejas : velocidadOvejas));
            }
        }
    }

    public void moverLobo() {
        for (int i = 0; i < losLobos.size(); i++) {
            int randomX;
            int randomY;

            int[] numerosAleatorios = {-1, 1}; // números aleatorios a seleccionar
            randomX = r.nextInt(2);
            randomY = r.nextInt(2);

            /*
            *Movimientos aleatorios comprobando los limites del frame
             */
            if (numerosAleatorios[randomX] == 1 && numerosAleatorios[randomY] == 1) {
                losLobos.get(i).setX(losLobos.get(i).getX() + ((losLobos.get(i).getX() >= 1163) ? -velocidadLobos : velocidadLobos));
                losLobos.get(i).setY(losLobos.get(i).getY() + ((losLobos.get(i).getY() >= 27) ? -velocidadLobos : velocidadLobos));
            } else if (numerosAleatorios[randomX] == 1 && numerosAleatorios[randomY] == -1) {
                losLobos.get(i).setX(losLobos.get(i).getX() + ((losLobos.get(i).getX() >= 1163) ? -velocidadLobos : velocidadLobos));
                losLobos.get(i).setY(losLobos.get(i).getY() - ((losLobos.get(i).getY() >= 672) ? velocidadLobos : -velocidadLobos));
            } else if (numerosAleatorios[randomX] == -1 && numerosAleatorios[randomY] == -1) {
                losLobos.get(i).setX(losLobos.get(i).getX() - ((losLobos.get(i).getX() >= 5) ? velocidadLobos : -velocidadLobos));
                losLobos.get(i).setY(losLobos.get(i).getY() - ((losLobos.get(i).getY() >= 672) ? velocidadLobos : -velocidadLobos));
            } else if (numerosAleatorios[randomX] == -1 && numerosAleatorios[randomY] == 1) {
                losLobos.get(i).setX(losLobos.get(i).getX() - ((losLobos.get(i).getX() >= 5) ? velocidadLobos : -velocidadLobos));
                losLobos.get(i).setY(losLobos.get(i).getY() + ((losLobos.get(i).getY() >= 27) ? -velocidadLobos : velocidadLobos));
            }
        }
    }

    public void agregarPasto() {
        elPasto = laSimulacion.Pasto(cantPasto);
    }

}
