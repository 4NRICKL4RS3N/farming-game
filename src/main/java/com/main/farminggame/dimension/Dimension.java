package com.main.farminggame.dimension;

public class Dimension {

    int aX;
    int aY;
    int bX;
    int bY;

    public Dimension(){}

    public int getaX() {
        return aX;
    }
    public void setaX(int aX) {
        this.aX = aX;
    }
    public int getaY() {
        return aY;
    }
    public void setaY(int aY) {
        this.aY = aY;
    }
    public int getbX() {
        return bX;
    }
    public void setbX(int bX) {
        this.bX = bX;
    }
    public int getbY() {
        return bY;
    }
    public void setbY(int bY) {
        this.bY = bY;
    }

    public Dimension(int aX, int aY, int bX, int bY) {
        this.setaX(aX);
        this.setaY(aY);
        this.setbX(bX);
        this.setbY(bY);
    }

    // x0 et y0 coin supérieur gauche
    public double getArea() {
        int width = this.getbX() - this.getaX();
        int height = this.getbY() - this.getaY();
        return width*height;
    }

}
