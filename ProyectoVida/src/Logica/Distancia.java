/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

/**
 *
 * @author jheis
 */
public class Distancia {
    public int x;
    public int y;

    public Distancia() {
        
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
    
    public int medirDistancia(int origenX,int origenY,int destinoX, int destinoY){
        int ladoUno = origenX - destinoX;
        int ladoDos = origenY - destinoY;
        int distanciaFinal = (int) Math.sqrt(Math.pow(ladoUno, 2)+Math.pow(ladoDos, 2));
        return distanciaFinal;
        
    }

    @Override
    public String toString() {
        return "Distancia{" + "x=" + x + ", y=" + y + '}';
    }
    
    
}
