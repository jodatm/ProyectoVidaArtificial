package Logica;

import java.awt.*;
import java.awt.image.*;
import java.net.URL;
import java.util.*;
import javax.swing.*;

public class MostrarSimulacion extends JFrame {

    Simulacion laSimulacion = new Simulacion();
    ArrayList<Oveja> lasOvejas = new ArrayList<>();
    //ArrayList<Integer> lasOvejasObjetivo = new ArrayList<>(); // guarda el indice de cada oveja objetivo para cada lobo.
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
    
    Image ovejaMuerta = new ImageIcon(getClass().getResource("/img/ovejaRip.png")).getImage();                         
    Image ovejoMuerto = new ImageIcon(getClass().getResource("/img/ovejoRip.png")).getImage();
    
    int cantOvejas;
    int cantLobos;
    int velocidadOvejas = 2; //velocidad de las ovejas
    int velocidadLobos = 2; //velocidad de los lobos

    /////////////////////////////
    //autor: jonathan loaiza rosero
    boolean horaDeComer = false;
    //boolean[] horaDeComerLobo = {false,false,false,false,false};  // arreglo para saber cuando los lobos tienen hambre
    int tiempoLobosAlimento;     //guarda la cantidad de tiempo que tiene que transcurrir para que un lobo se coma una oveja
    int ovejaObjetivo;
    int loboAleatorio;
    /////////////////////////////
    
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
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // autor: jonathan loaiza rosero
        /*lasOvejasObjetivo.add(1);
        lasOvejasObjetivo.add(2);  
        lasOvejasObjetivo.add(3);  
        lasOvejasObjetivo.add(4);
        lasOvejasObjetivo.add(5);*/
        tiempoLobosAlimento = Integer.parseInt(FrameSimulacion.textFieldComidaL.getText());  // se obtiene el tiempo apartir un lobo busca su comida
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
                            
                            /////////////////////////////////////////////////////////////////////////
                            if(segundosEjecucion % tiempoLobosAlimento == 0){ // cada X segundos un lobo busca comida.
                                /*for(int i=0;i<5;i++){
                                    if(horaDeComerLobo[i]==false){
                                        horaDeComerLobo[i]=true;
                                        ovejaObjetivo = ovejaMasCercana(i);   // a cada lobo se le asigna una oveja en especifico
                                        lasOvejasObjetivo.set(i,ovejaObjetivo);   
                                        i=5 +1;
                                    }
                                } */
                                
                                horaDeComer = true;  
                                loboAleatorio = r.nextInt(5);   // se selecciona el lobo aleatorio
                                ovejaObjetivo = ovejaMasCercana(loboAleatorio);

                            }
                            
                            //////////////////////////////////////////////////////////////////////////

                            if (segundosEjecucion % tiempoPasto == 0) {//cada X segundos agrega pasto (en este caso 3 segundos)

                                //si la cantidad de pasto alcanza el máximo permitido dejará de producirse 
                                if (elPasto.size() <= maxCantidadPasto) {
                                    agregarPasto();
                                    System.out.println("cantPasto: " + elPasto.size());
                                }
                               
                                
                            }
                            
                             comprovarVidaObejas(segundosEjecucion);
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
           if(lasOvejas.get(i).estaViva()){
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
           } else{
                if (i % 2 == 0) {
                    buffer.drawImage(ovejaMuerta, lasOvejas.get(i).getX(), lasOvejas.get(i).getY(), this);
                    //lasOvejas.remove(i);
                } else {
                    buffer.drawImage(ovejoMuerto, lasOvejas.get(i).getX(), lasOvejas.get(i).getY(), this);
                    //lasOvejas.remove(i);
                }            
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
            if(lasOvejas.get(i).estaViva()){
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
    }

    public void moverLobo() {
        for (int i = 0; i < losLobos.size(); i++) {

            /*if(horaDeComerLobo[i]){
                int ovejaMarcada = obtenerOveja(lasOvejasObjetivo.get(i));
                if(ovejaMarcada==-1){
                     int ovejaTemp = ovejaMasCercana(i);
                     lasOvejasObjetivo.set(i,ovejaTemp);
                     ovejaMarcada =  obtenerOveja(lasOvejasObjetivo.get(i));      // se modifico la función para que los lobos no se queden estaticos
                     //moverLovoHastaOvejaObjetivo(i,ovejaMarcada);                 // se comprueba si la presa de cada lobo esta viva o muerta
                     moverLovoHastaOvejaObjetivo(i,ovejaMarcada);
                }else{                                                            // si esta muerta se busca otra presa.
                     //moverLovoHastaOvejaObjetivo(i,ovejaMarcada);
                     moverLovoHastaOvejaObjetivo(i,ovejaMarcada);
                }
            }else{
                moverLoboAux(i);
            }*/
            
            if(i==loboAleatorio&&horaDeComer==true){
                moverLovoHastaOvejaObjetivo(i,ovejaObjetivo);
            }else{
                moverLoboAux(i);
            }
      }
    }
    
    public void moverLoboAux(int i){
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

///////////////////////////////////////////////////////////////////////////// 
    //autor: jonathan loaiza rosero
    public int ovejaMasCercana(int wolf){
       
        int x1 = losLobos.get(wolf).getX();
        int y1 = losLobos.get(wolf).getY();
        int ovejaMasCercana = 0;                                         // se compara la distancia de un lobo con todas
        int distanciaOvejaMasCercana=2000;                               // las ovejas para saber cúal es la mas cercana
        for(int i = 0;i<lasOvejas.size();i++){
            int dist = distancia(x1,y1,lasOvejas.get(i).getX(),lasOvejas.get(i).getY());
            if(dist < distanciaOvejaMasCercana){
                distanciaOvejaMasCercana = dist;
                //ovejaMasCercana = lasOvejas.get(i).getId(); 
                ovejaMasCercana = i;
                }
        }
        return ovejaMasCercana;
    }
    
    public int distancia(int x1,int y1, int x2, int y2){
        int Dx = x2 - x1;
        int Dy = y2 - y1;                             //clasica formula de la distancia entre dos puntos. 
        return (int)Math.sqrt(Dx*Dx + Dy*Dy); 
    }
    
////////////////////////////////////////////////////////////////////////////////    
    
    public void agregarPasto() {
        elPasto = laSimulacion.Pasto(cantPasto);
    }
       public void comprovarVidaObejas(int segundosEjecucion){  // comprobar si cada una de las ovejas del vector, ya debe morir de vieja
        for (int i = 0; i < lasOvejas.size(); i++) {
            if(lasOvejas.get(i).getVejesMaxima() == segundosEjecucion){
                lasOvejas.get(i).matarOveja();
            }
        }                                  
    }
       
   /* public int obtenerOveja(int idOveja){
        int oveja=0;
        for(int i=0;i<lasOvejas.size();i++){
            if(lasOvejas.get(i).getId()==idOveja){         
                oveja = i;                                
                break;                                     
            }else{
                oveja=-1;
            }
        }
        return oveja;  
    }*/
    
    public void moverLovoHastaOvejaObjetivo(int lobo,int oveja){
        int x1 = losLobos.get(lobo).getX();
        int y1 = losLobos.get(lobo).getY();
        int x2 = lasOvejas.get(oveja).getX();
        int y2 = lasOvejas.get(oveja).getY();

        int dist = distancia(x1,y1,x2,y2);
        if(dist>=0 && dist<=20){
            lasOvejas.remove(oveja);
            horaDeComer= false;                               //calculo del punto medio entre dos puntos.
            
        }else{
            int Sx=(x1+x2)/2;
            int Sy=(y1+y2)/2;
            int Sx1 = (x1+Sx)/2;
            int Sy1 = (y1+Sy)/2;
            int Sx2= (x1+Sx1)/2;
            int Sy2 = (y1+Sy1)/2;
            losLobos.get(lobo).setX(Sx2);
            losLobos.get(lobo).setY(Sy2);
        }
      }
     
    }
    


