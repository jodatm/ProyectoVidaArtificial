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
    Distancia distancia = new Distancia();
    
    Thread hiloUno;
    int ovejaMacho = 0;
    Thread hiloSegundos; // este hilo controla el contador de segundos de la simulacion global
    JFrame VentanaSegundos;
    JPanel panelEstados;
    JTextField textoSegundos;
    JLabel cantidadOvejas;
    JLabel cantidadLobos;
    JLabel cantidadPasto;

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

    int tiempoVidaOvejas;
    int tiempoMaxSinComer;
    int visionOvejas = 500; // Máxima distancia de visión de las ovejas para detectar pareja o lobos
    
    boolean horaDeComer = false;
    int tiempoLobosAlimento;     //guarda la cantidad de tiempo que tiene que transcurrir para que un lobo se coma una oveja
    int ovejaObjetivo;
    int loboAleatorio;


    int cantPasto = 15; //cantidad de pasto que quirenque aparezca cada X tiempo
    int maxCantidadPasto; //cantidad máxima de pasto en el ambiente
    int tiempoPasto;
    int tiempoAparearse; // Tiempo estipulado para que una oveja entre en estado de apareamiento

    //tamano de la ventana
    int anchoVentana = 1200;
    int altoVetana = 700;

    //rango de acción
    int rangoOveja10 = 200; //Rango de acción del tipoOveja 10
    
    //variable Random
    Random r = new Random();

    public MostrarSimulacion() {

        laSimulacion.setTamanoSimulacion(anchoVentana, altoVetana); //trasnfiera el tamano de ventana a los margenes de la simulacion

        cantOvejas = Integer.parseInt(FrameSimulacion.textFieldCantidadO.getText());
        cantLobos = Integer.parseInt(FrameSimulacion.textFieldCantidadL.getText());
        tiempoPasto = Integer.parseInt(FrameSimulacion.textFieldPasto.getText());
        maxCantidadPasto = Integer.parseInt(FrameSimulacion.textFieldCantidadMaxCesped.getText());

        tiempoVidaOvejas = Integer.parseInt(FrameSimulacion.textFieldVidaO.getText());
        tiempoAparearse = Integer.parseInt(FrameSimulacion.textFieldAparearO.getText());
        tiempoMaxSinComer= Integer.parseInt(FrameSimulacion.textFieldComidaO.getText());
        tiempoLobosAlimento = Integer.parseInt(FrameSimulacion.textFieldComidaL.getText());  // se obtiene el tiempo apartir un lobo busca su comida

        lasOvejas = laSimulacion.Oveja(cantOvejas, tiempoVidaOvejas, tiempoAparearse,tiempoMaxSinComer);
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
        VentanaSegundos.setSize(100, 400);
        //VentanaSegundos.setLocation(10, 10);
        //VentanaSegundos.setBounds(0, 0, 100, 100);

        panelEstados = new JPanel();
        panelEstados.setLayout(null);
        //panelEstados.setBounds(0, 0, 100, 100);
        VentanaSegundos.getContentPane().add(panelEstados);

        textoSegundos = new JTextField("Segundos :");
        textoSegundos.setBounds(0, 0, 130, 25);
        VentanaSegundos.setBackground(Color.red);
        panelEstados.add(textoSegundos);

        cantidadOvejas = new JLabel("");
        cantidadOvejas.setBounds(0, 25, 130, 25);
        panelEstados.add(cantidadOvejas);

        cantidadLobos = new JLabel("");
        cantidadLobos.setBounds(0, 50, 130, 25);
        panelEstados.add(cantidadLobos);
        cantidadLobos.setText("Cantidad de lobos: " + cantLobos);

        cantidadPasto = new JLabel("");
        cantidadPasto.setBounds(0, 75, 130, 25);
        panelEstados.add(cantidadPasto);

        VentanaSegundos.setVisible(true);

        hiloSegundos = new Thread(new Runnable() {

            int segundosEjecucion; // hace las vecs de segundero
            int contador = 0; // cuenta cuantas veces se detiene el hilo

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

                            // System.out.println("segundo: " + segundosEjecucion);
                            textoSegundos.setText("segundo: " + segundosEjecucion);
                            cantidadPasto.setText("Cantidad Pasto: " + String.valueOf(elPasto.size()));
                            /*
                            Bloque que permite controlar el tiempo de apareamiento y alimentacion de las ovejas
                             */
                            for (int i = 0; i < lasOvejas.size(); i++) {
                                //Alimento
                                lasOvejas.get(i).masHambre();
                                
                                //fin Alimento
                                
                                
                                cantidadOvejas.setText("Número de ovejas: " + String.valueOf(cuentaOvejas()));
                                int auxHoraAparearse = lasOvejas.get(i).getTiempoAparearse(); // Variable que almacena el tiempo de apareamiento de cada oveja

                                if (lasOvejas.get(i).getTiempoAparearse() > 0) {
                                    lasOvejas.get(i).setTiempoAparearse(auxHoraAparearse - 1);
                                }

                                if (lasOvejas.get(i).getTiempoAparearse() == 0 && lasOvejas.get(i).isHoraAparearse() == false) {
                                    lasOvejas.get(i).setHoraAparearse(true);
                                }
                            }

                            tiempoApareamiento();
                            /////////////////////////////////////////////////////////////////////////
                            if (segundosEjecucion % tiempoLobosAlimento == 0) { // cada X segundos un lobo busca comida.

                                horaDeComer = true;
                                loboAleatorio = r.nextInt(5);   // se selecciona el lobo aleatorio
                                ovejaObjetivo = ovejaMasCercana(loboAleatorio);

                            }

                            //////////////////////////////////////////////////////////////////////////
                            if (segundosEjecucion % tiempoPasto == 0) {//cada X segundos agrega pasto (en este caso 3 segundos)

                                //si la cantidad de pasto alcanza el máximo permitido dejará de producirse 
                                if (elPasto.size() <= maxCantidadPasto) {
                                    agregarPasto();

                                }

                            }
                            
                            
                            //////////////////////////////////////////////////////////////////////////
                            if (segundosEjecucion % 10 == 0) {//cada X10 segundos se eliminan los cadaveres de las ovejas del pasto (en este caso 10 segundos)

                                //si la oveja esta muerta y han pasado de 5 a 10 segundos, quitarlas
                                for (int i = 0; i < lasOvejas.size(); i++) {
                                    if ( lasOvejas.get(i).estaViva()){
                                        
                                    }else{
                                        lasOvejas.remove(i);
                                    }
                                }

                            }
                            //////////////////////////////
                            
                            

                            comprobarVidaOvejas();
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
            if (lasOvejas.get(i).estaViva()) {
                if (i % 2 == 0) {

                    //Pinta identificador de la oveja
                    g.drawString(String.valueOf(lasOvejas.get(i).getId() + 1), lasOvejas.get(i).getX() + 8, lasOvejas.get(i).getY());
                    Font font = new Font("Serif", Font.BOLD, 20);
                    g.setFont(font);
                    g.setColor(Color.RED);
                    //Pinta oveja
                    /*
                    Bloque que no permite que se cambie género inicial de una oveja, ya que si una oveja muere, la simulación
                    reasignaba los géneros y podría pasar de que una oveja que antes era macho pasaba a ser hembra
                     */
                    if ((lasOvejas.get(i).getGenero() == 'H')) {
                        lasOvejas.get(i).setGenero('H');
                        buffer.drawImage(oveja, lasOvejas.get(i).getX(), lasOvejas.get(i).getY(), this);
                    } else {
                        lasOvejas.get(i).setGenero('M');
                        buffer.drawImage(ovejo, lasOvejas.get(i).getX(), lasOvejas.get(i).getY(), this);
                    }

                } else {
                    //Pinta identificador de la oveja
                    g.drawString(String.valueOf(lasOvejas.get(i).getId() + 1), lasOvejas.get(i).getX() + 8, lasOvejas.get(i).getY());
                    Font font = new Font("Serif", Font.BOLD, 20);
                    g.setFont(font);
                    g.setColor(Color.RED);
                    //Pinta oveja
                    if ((lasOvejas.get(i).getGenero() == 'M')) {
                        lasOvejas.get(i).setGenero('M');
                        buffer.drawImage(ovejo, lasOvejas.get(i).getX(), lasOvejas.get(i).getY(), this);
                    } else {
                        lasOvejas.get(i).setGenero('H');
                        buffer.drawImage(oveja, lasOvejas.get(i).getX(), lasOvejas.get(i).getY(), this);
                    }

                }
            } else if (i % 2 == 0) {
                buffer.drawImage(ovejaMuerta, lasOvejas.get(i).getX(), lasOvejas.get(i).getY(), this);
                //lasOvejas.remove(i);
            } else {
                buffer.drawImage(ovejoMuerto, lasOvejas.get(i).getX(), lasOvejas.get(i).getY(), this);
                //lasOvejas.remove(i);
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

           
        if ( lasOvejas.get(i).isHoraComer() && (elPasto.size()>0)&& lasOvejas.get(i).estaViva()) {
            alimentarse(i, pastoMasCercano(i));
        }else{
            int randomX;
            int randomY;

            int[] numerosAleatorios = {-1, 1};
            randomX = r.nextInt(2);
            randomY = r.nextInt(2);

            /*
            *Movimientos aleatorios comprobando los limites del frame
             */
            if (lasOvejas.get(i).estaViva()) {
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
    }

    /*
    Bloque de código que permite obtener el número de ovejas vivas en el ambiente
     */
    public int cuentaOvejas() {
        int ovejasMuertas = 0;
        for (int i = 0; i < lasOvejas.size(); i++) {

            if (!lasOvejas.get(i).estaViva()) {
                ovejasMuertas++;
            }
        }
        return (lasOvejas.size() - ovejasMuertas);
    }

    public void moverLobo() {
        for (int i = 0; i < losLobos.size(); i++) {

            
            if (i == loboAleatorio && horaDeComer == true && (cuentaOvejas() > 0)) {
                moverLoboHastaOvejaObjetivo(i, ovejaObjetivo);
            } else {
                moverLoboAux(i);
            }
        }
    }

    public void moverLoboAux(int i) {
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

    /*
    Función que gestiona la etapa de apareamiento de las ovejas.
    1-En esta version las ovejas se aparean si un macho y una hembra se encuentran muy cerca, para ello escogen por medio del cálculo
      de la distancia entre puntos la oveja de sexo contrario mas cercana. Hallar una pareja cercana lo hace el macho principalmente.
     */
    public void tiempoApareamiento() {

        for (int i = 0; i < lasOvejas.size(); i++) {
            /*
            * Tiempo de apareo
             */

            if (lasOvejas.get(i).getGenero() == 'M' && lasOvejas.get(i).isHoraAparearse() == true && i <= lasOvejas.size()) {
                ovejaMacho = lasOvejas.get(i).getId();

            }

            if (i <= lasOvejas.size() && ovejaMacho <= lasOvejas.size()) { //evita tomar un índice mayor al tamaño de la lista

                /*
                 Sólo las ovejas macho buscaran a sus parejas en estado de apareamiento
                 */
                for (int j = 0; j < lasOvejas.size(); j++) {
                    if (lasOvejas.get(j).getGenero() == 'H' && lasOvejas.get(j).isHoraAparearse() == true && j < lasOvejas.size() && ovejaMacho < lasOvejas.size()) {

                        int distanciaPareja = distancia.medirDistancia(lasOvejas.get(ovejaMacho).getX(), lasOvejas.get(ovejaMacho).getY(), lasOvejas.get(j).getX(), lasOvejas.get(j).getY());
                        
                        /*
                        La oveja macho tiene una vision de x distancía, si la hembra esta a esa distancia, se aparean.
                        */
                        if (distanciaPareja >= 0 && distanciaPareja <= visionOvejas && (lasOvejas.get(j).estaViva() && lasOvejas.get(ovejaMacho).estaViva())) {

                            reproducirOvejas(lasOvejas.get(ovejaMacho).getTipoOveja(),lasOvejas.get(j).getTipoOveja());
                            lasOvejas.get(j).setHoraAparearse(false); // cambia el estado de apareamiento a falso
                            lasOvejas.get(ovejaMacho).setHoraAparearse(false); // cambia el estado de apareamiento a falso
                            lasOvejas.get(j).setTiempoAparearse(tiempoAparearse); //se restablece el tiempo de apareamiento
                            lasOvejas.get(ovejaMacho).setTiempoAparearse(tiempoAparearse);//se restablece el tiempo de apareamiento
                            break;

                        }

                    }
                }
            }
        }
    }


    public int ovejaMasCercana(int wolf) {

        int x1 = losLobos.get(wolf).getX();
        int y1 = losLobos.get(wolf).getY();
        int ovejaMasCercana = 0;                                         // se compara la distancia de un lobo con todas
        int distanciaOvejaMasCercana = 2000;                               // las ovejas para saber cúal es la mas cercana
        for (int i = 0; i < lasOvejas.size(); i++) {
            int dist = distancia(x1, y1, lasOvejas.get(i).getX(), lasOvejas.get(i).getY());
            if (dist < distanciaOvejaMasCercana) {
                distanciaOvejaMasCercana = dist;
                ovejaMasCercana = i;
            }
        }
        return ovejaMasCercana;
    }
    
    public int pastoMasCercano(int oveja) {

        int x1 = lasOvejas.get(oveja).getX();
        int y1 = lasOvejas.get(oveja).getY();
        int pastoMasCercano = 0;                                         
        int distanciaPastpMasCercano = 2000;                               
        for (int i = 0; i < elPasto.size(); i++) {
            int dist = distancia(x1, y1, elPasto.get(i).getX(), elPasto.get(i).getY());
            if (dist < distanciaPastpMasCercano) {
                distanciaPastpMasCercano = dist;
                pastoMasCercano = i;
            }
        }
        return pastoMasCercano;
    }
    
    public int distancia(int x1, int y1, int x2, int y2) {
        int Dx = x2 - x1;
        int Dy = y2 - y1;                             //clasica formula de la distancia entre dos puntos. 
        return (int) Math.sqrt(Dx * Dx + Dy * Dy);
    }

////////////////////////////////////////////////////////////////////////////////    
    public void agregarPasto() {
        elPasto = laSimulacion.Pasto(cantPasto);
    }

    public void comprobarVidaOvejas() {  // comprobar si cada una de las ovejas del vector, ya debe morir de vieja
        for (int i = 0; i < lasOvejas.size(); i++) {
            int auxTiempoVida = lasOvejas.get(i).getVejesMaxima();
            lasOvejas.get(i).setVejesMaxima(auxTiempoVida - 1);
            // System.out.println("Oveja " + lasOvejas.get(i).getId() + " vida: " + lasOvejas.get(i).getVejesMaxima());
            if(lasOvejas.get(i).getTiempoSinComer()>tiempoMaxSinComer){
                                    lasOvejas.get(i).matarOveja();
            }
            if (lasOvejas.get(i).getVejesMaxima() == 0) {
                lasOvejas.get(i).matarOveja();
            }

            if (lasOvejas.get(i).estaViva() == false && lasOvejas.get(i).getVejesMaxima() == -20) {
                lasOvejas.remove(i);
            }

        }
    }

   
    /*
    Método que crea una nueva oveja en el ambiente después que una hembra y un macho se encuentran para aparearse.
    1- El género de la oveja se escoge aleatoriamente.
     */
    public void reproducirOvejas(int tipoOvejaMacho, int tipoOvejaHembra) {
        lasOvejas = laSimulacion.OvejaHija(lasOvejas.size(), 1, tiempoVidaOvejas, tiempoAparearse, tiempoMaxSinComer,tipoOvejaMacho,tipoOvejaHembra);
    }

    public void moverLoboHastaOvejaObjetivo(int lobo, int oveja) {

        if (oveja < lasOvejas.size()) {
            int x1 = losLobos.get(lobo).getX();
            int y1 = losLobos.get(lobo).getY();
            int x2 = lasOvejas.get(oveja).getX();
            int y2 = lasOvejas.get(oveja).getY();

            int dist = distancia(x1, y1, x2, y2);
            if (dist >= 0 && dist <= 20 && lasOvejas.get(oveja).estaViva() == true) {
                lasOvejas.remove(oveja);
                horaDeComer = false;                               //calculo del punto medio entre dos puntos.

            } else {
                int Sx = (x1 + x2) / 2;
                int Sy = (y1 + y2) / 2;
                int Sx1 = (x1 + Sx) / 2;
                int Sy1 = (y1 + Sy) / 2;
                int Sx2 = (x1 + Sx1) / 2;
                int Sy2 = (y1 + Sy1) / 2;
                losLobos.get(lobo).setX(Sx2);
                losLobos.get(lobo).setY(Sy2);
            }
        }
    }
    
    /**
     * 
     * Simula los comportamientos de alimentacion de una oveja segun su tipo
     * @param oveja punto de partida. la oveja que busca comida
     * @param pasto objetivo el pasto que hay disponible
     */
    public void alimentarse(int oveja, int pasto){//este metodo ya esta en uso, utiliza el pasto mas cercano y la oveja que tiene hambre
        int x1 = lasOvejas.get(oveja).getX();
        int y1 = lasOvejas.get(oveja).getY();
        int x2 = elPasto.get(pasto).getX();
        int y2 = elPasto.get(pasto).getY();
        int dist = distancia(x1, y1, x2, y2);
        
        switch(lasOvejas.get(oveja).tipoOveja){  
            case 1:// La oveja egoista, Correra por comida en cuanto le de hambre. Por Michael Palacios 
                if (oveja < lasOvejas.size()) {
                    if (dist >= 0 && dist <= 20 && lasOvejas.get(oveja).estaViva() == true) {//me puedo comer el pasto
                        elPasto.remove(pasto);
                        lasOvejas.get(oveja).setHoraComer(false);//no tengo que buscar comida
                        lasOvejas.get(oveja).resetTiempoSinComer();//ya no tengo hambre
                    } else {
                        int Sx = (x1 + x2) / 2;
                        int Sy = (y1 + y2) / 2;
                        int Sx1 = (x1 + Sx) / 2;
                        int Sy1 = (y1 + Sy) / 2;
                        int Sx2 = (x1 + Sx1) / 2;
                        int Sy2 = (y1 + Sy1) / 2;
                        int Sx3 = (x1 + Sx2) / 2;
                        int Sy3 = (y1 + Sy2) / 2;
                        int Sx4 = (x1 + Sx3) / 2;
                        int Sy4 = (y1 + Sy3) / 2;
                        lasOvejas.get(oveja).setX(Sx4);
                        lasOvejas.get(oveja).setY(Sy4);
                        if(distancia(lasOvejas.get(oveja).getX(), lasOvejas.get(oveja).getY(), x2, y2)<=50){
                            int nuevox = (lasOvejas.get(oveja).getX()+ x2) / 2;
                            int nuevoy = (lasOvejas.get(oveja).getY() + y2) / 2;
                            lasOvejas.get(oveja).setX(nuevox);
                            lasOvejas.get(oveja).setY(nuevoy);
                        }

                    }
            }
                break;
                
                
            case 2:// Tipo oveja 2 programado por Jheison Peña, solo se alimenta cuando le queda la mitad del tiempo máximo sin alimentarse
                if (dist >= 0 && dist <= 20 && lasOvejas.get(oveja).estaViva() == true && lasOvejas.get(oveja).getTiempoSinComer() == (int)tiempoMaxSinComer/2) {//me puedo comer el pasto
                    elPasto.remove(pasto);
                    lasOvejas.get(oveja).setHoraComer(false);//no tengo que buscar comida
                    lasOvejas.get(oveja).resetTiempoSinComer();//ya no tengo hambre
                }else{
                    if(lasOvejas.get(oveja).getTiempoSinComer() == (int)tiempoMaxSinComer/2){
                        int Sx = (x1 + x2) / 2;
                        int Sy = (y1 + y2) / 2;
                        int Sx1 = (x1 + Sx) / 2;
                        int Sy1 = (y1 + Sy) / 2;
                        int Sx2 = (x1 + Sx1) / 2;
                        int Sy2 = (y1 + Sy1) / 2;
                        lasOvejas.get(oveja).setX(Sx2);
                        lasOvejas.get(oveja).setY(Sy2);
                    }else{
                    moverOvejaIndividual(oveja);
                    }                    
                }
                break;
            case 3:
                if (dist >= 0 && dist <= 20 && lasOvejas.get(oveja).estaViva() == true) {//me puedo comer el pasto
                    elPasto.remove(pasto);
                    lasOvejas.get(oveja).setHoraComer(false);//no tengo que buscar comida
                    lasOvejas.get(oveja).resetTiempoSinComer();//ya no tengo hambre
                }else{moverOvejaIndividual(oveja);}
                break;
            case 4:// Tipo oveja 4. Stephany Rivera, cuando se acerca el tiempo limite de muerte por hambre, 1.5 s
                if (dist >= 0 && dist <= 20 && lasOvejas.get(oveja).estaViva() == true && lasOvejas.get(oveja).getTiempoSinComer() == (int)(tiempoMaxSinComer/tiempoMaxSinComer)+1/2) {//me puedo comer el pasto
                    elPasto.remove(pasto);
                    lasOvejas.get(oveja).setHoraComer(false);//no tengo que buscar comida
                    lasOvejas.get(oveja).resetTiempoSinComer();//ya no tengo hambre
                }else{
                    if(lasOvejas.get(oveja).getTiempoSinComer() == (int)(tiempoMaxSinComer/tiempoMaxSinComer)+1/2){
                        int Sx = (x1 + x2) / 2;
                        int Sy = (y1 + y2) / 2;
                        int Sx1 = (x1 + Sx) / 2;
                        int Sy1 = (y1 + Sy) / 2;
                        int Sx2 = (x1 + Sx1) / 2;
                        int Sy2 = (y1 + Sy1) / 2;
                        lasOvejas.get(oveja).setX(Sx2);
                        lasOvejas.get(oveja).setY(Sy2);
                    }else{
                    moverOvejaIndividual(oveja);
                    }                    
                }
                
                break;
            case 5:
                // La oveja cachonda come si el estado de aparearse es true. Santiago Romero
                if (dist >= 0 && dist <= 20 && lasOvejas.get(oveja).estaViva() == true && lasOvejas.get(oveja).isHoraAparearse()==true) {//me puedo comer el pasto
                    elPasto.remove(pasto);
                    lasOvejas.get(oveja).setHoraComer(false);//no tengo que buscar comida
                    lasOvejas.get(oveja).resetTiempoSinComer();//ya no tengo hambre
                }else{int Sx = (x1 + x2) / 2;
                        int Sy = (y1 + y2) / 2;
                        int Sx1 = (x1 + Sx) / 2;
                        int Sy1 = (y1 + Sy) / 2;
                        int Sx2 = (x1 + Sx1) / 2;
                        int Sy2 = (y1 + Sy1) / 2;
                        lasOvejas.get(oveja).setX(Sx2);
                        lasOvejas.get(oveja).setY(Sy2);}
                break;
            case 6:
                if (dist >= 0 && dist <= 20 && lasOvejas.get(oveja).estaViva() == true) {//me puedo comer el pasto
                    elPasto.remove(pasto);
                    lasOvejas.get(oveja).setHoraComer(false);//no tengo que buscar comida
                    lasOvejas.get(oveja).resetTiempoSinComer();//ya no tengo hambre
                }else{moverOvejaIndividual(oveja);}
                break;
            case 7:
                if (dist >= 0 && dist <= 20 && lasOvejas.get(oveja).estaViva() == true) {//me puedo comer el pasto
                    elPasto.remove(pasto);
                    lasOvejas.get(oveja).setHoraComer(false);//no tengo que buscar comida
                    lasOvejas.get(oveja).resetTiempoSinComer();//ya no tengo hambre
                }else{moverOvejaIndividual(oveja);}
                break;
            case 8:
                if (dist >= 0 && dist <= 20 && lasOvejas.get(oveja).estaViva() == true) {//me puedo comer el pasto
                    elPasto.remove(pasto);
                    lasOvejas.get(oveja).setHoraComer(false);//no tengo que buscar comida
                    lasOvejas.get(oveja).resetTiempoSinComer();//ya no tengo hambre
                }else{moverOvejaIndividual(oveja);}
                break;
            case 9:
                if (dist >= 0 && dist <= 20 && lasOvejas.get(oveja).estaViva() == true) {//me puedo comer el pasto
                    elPasto.remove(pasto);
                    lasOvejas.get(oveja).setHoraComer(false);//no tengo que buscar comida
                    lasOvejas.get(oveja).resetTiempoSinComer();//ya no tengo hambre
                }else{moverOvejaIndividual(oveja);}
                break;
            case 10:
                int contador=0;
                int contadorHembras = 0;
                if (dist >= 0 && dist <= 20 && lasOvejas.get(oveja).estaViva() == true) {//me puedo comer el pasto
                    elPasto.remove(pasto);
                    lasOvejas.get(oveja).setHoraComer(false);//no tengo que buscar comida
                    lasOvejas.get(oveja).resetTiempoSinComer();//ya no tengo hambre
                }else{
                    for(int k=0;k<lasOvejas.size();k++){
                        int distanciaTemp = distancia.medirDistancia(lasOvejas.get(oveja).getX(), lasOvejas.get(oveja).getY(), lasOvejas.get(k).getX(), lasOvejas.get(k).getY());
                        if(distanciaTemp<=rangoOveja10 && lasOvejas.get(oveja).estaViva() == true){
                            contador+=1;
                        }
                        if(lasOvejas.get(k).isHoraComer()&&distanciaTemp<=rangoOveja10&&lasOvejas.get(k).getGenero()=='H' && lasOvejas.get(oveja).estaViva() == true){
                            contadorHembras+=1;
                        }
                    }
                    if((contador/2)>contadorHembras){ 
                        int Sx = (x1 + x2) / 2;
                        int Sy = (y1 + y2) / 2;
                        int Sx1 = (x1 + Sx) / 2;
                        int Sy1 = (y1 + Sy) / 2;
                        int Sx2 = (x1 + Sx1) / 2;
                        int Sy2 = (y1 + Sy1) / 2;
                        int Sx3 = (x1 + Sx2) / 2;
                        int Sy3 = (y1 + Sy2) / 2;
                        int Sx4 = (x1 + Sx3) / 2;
                        int Sy4 = (y1 + Sy3) / 2;
                        lasOvejas.get(oveja).setX(Sx4);
                        lasOvejas.get(oveja).setY(Sy4);
                        if(distancia(lasOvejas.get(oveja).getX(), lasOvejas.get(oveja).getY(), x2, y2)<=50){
                            int nuevox = (lasOvejas.get(oveja).getX()+ x2) / 2;
                            int nuevoy = (lasOvejas.get(oveja).getY() + y2) / 2;
                            lasOvejas.get(oveja).setX(nuevox);
                            lasOvejas.get(oveja).setY(nuevoy);
                        }
                    }else{
                        moverOvejaIndividual(oveja);
                    }      
                }
                break;
            default:
                moverOvejaIndividual(oveja);
                
    }
    }
    
    public void moverOvejaIndividual(int oveja){

      
            int randomX;
            int randomY;

            int[] numerosAleatorios = {-1, 1};
            randomX = r.nextInt(2);
            randomY = r.nextInt(2);

            /*
            *Movimientos aleatorios comprobando los limites del frame
             */
            if (lasOvejas.get(oveja).estaViva()) {
                if (numerosAleatorios[randomX] == 1 && numerosAleatorios[randomY] == 1) {
                    lasOvejas.get(oveja).setX(lasOvejas.get(oveja).getX() + ((lasOvejas.get(oveja).getX() >= 1163) ? -velocidadOvejas : velocidadOvejas));
                    lasOvejas.get(oveja).setY(lasOvejas.get(oveja).getY() + ((lasOvejas.get(oveja).getY() >= 27) ? -velocidadOvejas : velocidadOvejas));
                } else if (numerosAleatorios[randomX] == 1 && numerosAleatorios[randomY] == -1) {
                    lasOvejas.get(oveja).setX(lasOvejas.get(oveja).getX() + ((lasOvejas.get(oveja).getX() >= 1163) ? -velocidadOvejas : velocidadOvejas));
                    lasOvejas.get(oveja).setY(lasOvejas.get(oveja).getY() - ((lasOvejas.get(oveja).getY() >= 672) ? velocidadOvejas : -velocidadOvejas));
                } else if (numerosAleatorios[randomX] == -1 && numerosAleatorios[randomY] == -1) {
                    lasOvejas.get(oveja).setX(lasOvejas.get(oveja).getX() - ((lasOvejas.get(oveja).getX() >= 5) ? velocidadOvejas : -velocidadOvejas));
                    lasOvejas.get(oveja).setY(lasOvejas.get(oveja).getY() - ((lasOvejas.get(oveja).getY() >= 672) ? velocidadOvejas : -velocidadOvejas));
                } else if (numerosAleatorios[randomX] == -1 && numerosAleatorios[randomY] == 1) {
                    lasOvejas.get(oveja).setX(lasOvejas.get(oveja).getX() - ((lasOvejas.get(oveja).getX() >= 5) ? velocidadOvejas : -velocidadOvejas));
                    lasOvejas.get(oveja).setY(lasOvejas.get(oveja).getY() + ((lasOvejas.get(oveja).getY() >= 27) ? -velocidadOvejas : velocidadOvejas));
                }
            }
        }
    
        public void moverseHacia(int ref,int x1,int y1, int x2, int y2){
            while(x1!=x2||y1!=y2){
            if(x1<x2){
                x1++;
            }else{
                x1--;
            }
            if(y1<y2){
                y1++;
            }else{
                y1--;
            }
            lasOvejas.get(ref).setX(x1);
            lasOvejas.get(ref).setX(y1);}
        }
    
        }
    
        
    
    
    

