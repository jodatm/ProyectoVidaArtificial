package Logica;


import java.util.Random;

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
    boolean horaComer;
    int tiempoSinComer;
    int tiempoBuscarComida;
    int tipoOveja;
    private Random r;
    
    public Oveja(int id, int x, int y, char genero, int vejesMaxima,int tiempoAparearse, int tiempoMaxComida,int tipoOveja){
        this.id = id;
        this.x = x;
        this.y = y;
        this.genero = genero;
        this.tiempoVida = tiempoVida;
        this.tiempoAparearse = tiempoAparearse;
        this.horaAparearse = false;
        this.vejesMaxima = vejesMaxima;
        this.horaComer=false;
        tiempoSinComer=0;
        this.tipoOveja=tipoOveja;
        r= new Random();
        setTiempoBuscarComida(r.nextInt(tiempoMaxComida-1));
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

    public boolean isHoraComer() {
        horaComer = tiempoSinComer>=tiempoBuscarComida;
        return horaComer;
    }

    public void setHoraComer(boolean tiempoComer) {
        this.horaComer = tiempoComer;
    }

    public int getTiempoSinComer() {
        
        return tiempoSinComer;
    }

    public void resetTiempoSinComer() {
        this.tiempoSinComer = 0;
    }
    
    public void masHambre(){
        this.tiempoSinComer++;
    }

    private void setTiempoBuscarComida(int tiempoBuscarComida) {
        this.tiempoBuscarComida = tiempoBuscarComida;
    }

    public int getTipoOveja() {
        return tipoOveja;
    }

    public void setTipoOveja(int tipoOveja) {
        this.tipoOveja = tipoOveja;
    }
    
    
  
}

