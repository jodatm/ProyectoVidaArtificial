
package Logica;

import java.util.*;

/**
 *
 * @author Santiago Romero
 */
public class Simulacion {
    int randomX;
    int randomY;
    int valorAleatorio;
    //variable Random
    Random r = new Random();
   
   int randomVejesmaxima;
    
    int ancho = 1150;
    int  alto = 720;
    
    ArrayList<Oveja> Ovejas = new ArrayList<>();
    Oveja laOveja;
    ArrayList<Lobo> Lobos = new ArrayList<>();
    Lobo elLobo;
    ArrayList<Pasto> Pasto = new ArrayList<>();    
    Pasto elPasto;

    public void setTamanoSimulacion(int anchoVentana, int altoVentana){
        ancho = anchoVentana-50; //menos el ancho de los iconos de animales
        alto = altoVentana-50; //menos al alto de los iconos de los animales
    }
    
    
    public ArrayList<Oveja> Oveja(int cantOvejas, int tiempoVida, int tiempoAparearse, int tiempoMaxComida){
        for(int i=0; i< cantOvejas;i++){     
            randomX= (int) (Math.random()*(ancho)) + 10;
            randomY= (int) (Math.random()*(alto)) + 10;
            randomVejesmaxima = Integer.parseInt(FrameSimulacion.textFieldVidaO.getText());
            int tipo= r.nextInt(10)+1;
            if(i%2 == 0){
                laOveja = new Oveja(i, randomX, randomY,'0',tiempoVida,tiempoAparearse, tiempoMaxComida,tipo);
            }
            else{
                laOveja = new Oveja(i, randomX, randomY,'0',tiempoVida,tiempoAparearse,tiempoMaxComida,tipo);
            }            
            Ovejas.add(laOveja);      
        }        
        return Ovejas;        
    }
    
    
    /*
    A la oveja originada de un apareamiento se selecciona su genero de manera aleatoria.
    */
    public ArrayList<Oveja> OvejaHija(int cantidadOvejasActual,int cantOvejas, int tiempoVida, int tiempoAparearse,int tiempoMaxComida, int tipoOvejaMacho, int tipoOvejaHembra){
        for(int i=0; i< cantOvejas;i++){     
            randomX= (int) (Math.random()*(ancho)) + 10;
            randomY= (int) (Math.random()*(alto)) + 10;
            char[] generoAleatorios = {'M', 'H'};
            int[] tipoOvejaAleatorios = {tipoOvejaHembra, tipoOvejaMacho};
            valorAleatorio = r.nextInt(2); //Selección del genero de manera aleatoria
            
            if(i%2 == 0){
                laOveja = new Oveja(cantidadOvejasActual, randomX, randomY,generoAleatorios[valorAleatorio],tiempoVida,tiempoAparearse, tiempoMaxComida,tipoOvejaAleatorios[valorAleatorio]);
            }
            else{
                laOveja = new Oveja(cantidadOvejasActual, randomX, randomY,generoAleatorios[valorAleatorio],tiempoVida,tiempoAparearse,tiempoMaxComida,tipoOvejaAleatorios[valorAleatorio]);
            }            
            Ovejas.add(laOveja);      
        }        
        return Ovejas;        
    }
    
    public ArrayList<Lobo> Lobo(int cantLobos) {     
        for(int i=0; i< cantLobos;i++){
            randomX= (int) (Math.random()*(ancho)) + 10;
            randomY= (int) (Math.random()*(alto)) + 10;
            elLobo = new Lobo(i, randomX, randomY);
            Lobos.add(elLobo);
        }
        return Lobos;
    }
    
    public ArrayList<Pasto> Pasto(int cantPasto){
        //int cantPasto = 1;        
        for(int i=0; i< cantPasto;i++){
            randomX= (int) (Math.random()*(ancho)) + 10;
            randomY= (int) (Math.random()*(alto)) + 10;
            elPasto = new Pasto(i, randomX, randomY);
            Pasto.add(elPasto);            
        }  
        return Pasto;
    }
}
