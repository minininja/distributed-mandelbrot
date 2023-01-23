package org.dorkmaster.mandlebrot.renderer;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import org.dorkmaster.mandlebrot.common.math.ComplexNumber;
import org.dorkmaster.mandlebrot.common.render.Pixel;
import org.dorkmaster.mandlebrot.common.render.Render;
import org.dorkmaster.mandlebrot.common.render.RenderContext;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Starter {
    protected static HazelcastInstance hi = Hazelcast.newHazelcastInstance();

    public boolean render(OutputStream out, RenderContext context, Collection<Future<Collection<Pixel>>> futures) throws IOException, ExecutionException, InterruptedException, TimeoutException {
        boolean complete = true;

        int[] colors = new int[context.getIterations() + 1];
        for (int i = 0; i < context.getIterations(); i++) {
            colors[i] = Color.HSBtoRGB(i/256f, 1, i/(i+8f));
        }

        int i = 0;
        BufferedImage image = new BufferedImage(context.getWidth(), context.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (Future<Collection<Pixel>> chunk : futures) {
            if (!chunk.isDone()) {
                complete = false;
                i++;
            } else {
                for (Pixel p : chunk.get(1, TimeUnit.SECONDS)) {
                    image.setRGB(p.getX(), p.getY(), colors[p.getIterations()]);
                }
            }
        }
        System.out.println(i + " chunks not complete, returning " + complete);
        ImageIO.write(image, "png", out);

        return complete;
    }

    public Set<Future<Collection<Pixel>>> submitTasks(int blockSize, RenderContext context) {
        TargetSelector ts = new TargetSelector(hi.getCluster().getMembers());

        Set<Future<Collection<Pixel>>> result = new HashSet<>();
        Collection<Pixel> pixels = new ArrayList<>(blockSize);
        IExecutorService es = hi.getExecutorService("mandlebrot");
        for (int x = 0; x < context.getWidth(); x++) {
            for (int y = 0; y < context.getHeight(); y++) {
                pixels.add(new Pixel(x, y, new ComplexNumber(context.getrRange()[x], context.getiRange()[y])));
                if (pixels.size() == blockSize) {
                    result.add(es.submitToMember(new Render(pixels, context.getIterations(), context.getEscape()), ts.nextMember()));
                    pixels.clear();
                }
            }
        }
        if (pixels.size() > 0) {
            result.add(es.submit(new Render(pixels, context.getIterations(), context.getEscape())));
        }

        return result;
    }
}
