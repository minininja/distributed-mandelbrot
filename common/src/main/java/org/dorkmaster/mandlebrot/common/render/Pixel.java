package org.dorkmaster.mandlebrot.common.render;

import org.dorkmaster.mandlebrot.common.math.ComplexNumber;

import java.io.Serializable;

public class Pixel implements Serializable {
    protected int x;
    protected int y;
    protected int iterations = -1;
    protected ComplexNumber c;

    public Pixel(int x, int y, int iterations, ComplexNumber c) {
        this.x = x;
        this.y = y;
        this.iterations = iterations;
        this.c = c;
    }

    public Pixel(int x, int y, ComplexNumber c) {
        this.x = x;
        this.y = y;
        this.c = c;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getIterations() {
        return iterations;
    }

    public ComplexNumber getC() {
        return c;
    }
}
