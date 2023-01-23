package org.dorkmaster.mandlebrot.common.render;

import org.dorkmaster.mandlebrot.common.math.ComplexNumber;
import org.dorkmaster.mandlebrot.common.math.MathUtils;

public class RenderContext {
    protected int width;
    protected int height;
    protected int iterations;
    protected double escape;
    protected ComplexNumber lowerC;
    protected ComplexNumber upperC;
    double[] rRange;
    double[] iRange;

    public RenderContext(int width, int height, int iterations, double escape, ComplexNumber lowerC, ComplexNumber upperC) {
        this.width = width;
        this.height = height;
        this.iterations = iterations;
        this.escape = escape;
        this.lowerC = lowerC;
        this.upperC = upperC;
        this.rRange = MathUtils.range(width, lowerC.getR(), upperC.getR());
        this.iRange = MathUtils.range(height, lowerC.getI(), upperC.getI());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getIterations() {
        return iterations;
    }

    public double getEscape() {
        return escape;
    }

    public ComplexNumber getLowerC() {
        return lowerC;
    }

    public ComplexNumber getUpperC() {
        return upperC;
    }

    public double[] getrRange() {
        return rRange;
    }

    public double[] getiRange() {
        return iRange;
    }
}
