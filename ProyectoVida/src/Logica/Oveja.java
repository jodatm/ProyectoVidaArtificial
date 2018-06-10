package Logica;

/**
 *
 * @author Santiago Romero
 */
public class Oveja {

    int id;
    int x;
    int y;
    char genero;
    int tiempoVida;
    int tiempoAparearse;
    boolean horaAparearse;
    boolean viva = true;
    int vejesMaxima;

    public Oveja(int id, int x, int y, char genero, int vejesMaxima,int tiempoAparearse){
        this.id = id;
        this.x = x;
        this.y = y;
        this.genero = genero;
        this.tiempoVida = tiempoVida;
        this.tiempoAparearse = tiempoAparearse;
        this.horaAparearse = false;
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

    public char getGenero() {
        return genero;
    }

    public int getTiempoVida() {
        return tiempoVida;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    public void setTiempoVida(int tiempoVida) {
        this.tiempoVida = tiempoVida;
    }

    public int getTiempoAparearse() {
        return tiempoAparearse;
    }

    public void setTiempoAparearse(int tiempoAparearse) {
        this.tiempoAparearse = tiempoAparearse;
    }

    public boolean isHoraAparearse() {
        return horaAparearse;
    }

    public void setHoraAparearse(boolean horaAparearse) {
        this.horaAparearse = horaAparearse;
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

