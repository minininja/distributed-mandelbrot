package org.dorkmaster.mandlebrot.common.render;

import org.dorkmaster.mandlebrot.common.math.ComplexNumber;
import org.dorkmaster.mandlebrot.common.util.Timer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;


public class Render implements Callable<Collection<Pixel>>, Serializable {
    Collection<Pixel> pixels;
    protected long maxIterations;
    protected double escape;

    public Render(Collection<Pixel> pixels, long maxIterations, double escape) {
        this.pixels = pixels;
        this.maxIterations = maxIterations;
        this.escape = escape;
    }

    @Override
    public Collection<Pixel> call() throws Exception {
        Timer t = new Timer();
        Collection<Pixel> result = new ArrayList<>(pixels.size());
        for (Pixel p : pixels) {
            ComplexNumber z = new ComplexNumber(0, 0);

            int i;
            for (i = 0; i < maxIterations && z.abs() < escape; i++) {
                z = z.multiply(z).plus(p.getC());
            }

            result.add(new Pixel(p.getX(), p.getY(), i, p.getC()));
        }
        System.out.println(Thread.currentThread().getName() + ": Processed " + pixels.size() + " pixels in " + t.time() + "ms");
        return result;
    }
}
