package Logica;

/**
 *
 * @author Santiago Romero
 */
public class Oveja {
    int id;
    int x;
    int y;
    boolean viva = true;
    int vejesMaxima;

    
    public Oveja(int id, int x, int y, int vejesMaxima){
        this.id = id;
        this.x = x;
        this.y = y;
        this.vejesMaxima = vejesMaxima; 
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }  

    public boolean estaViva() {
        return viva;
    }

    public int getVejesMaxima() {
        return vejesMaxima;
    }

    public void matarOveja() {
        this.viva = false;
    }

    public void setVejesMaxima(int vejesMaxima) {
        this.vejesMaxima = vejesMaxima;
    }
    
    
    
}

