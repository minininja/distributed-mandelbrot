package org.dorkmaster.mandlebrot.common.math;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class Tests {
    @Test
    public void testPlus() {
        ComplexNumber a = new ComplexNumber(1, 1);
        ComplexNumber b = new ComplexNumber( 1, 1);
        ComplexNumber c = a.plus(b);
        assertEquals(2, c.getR().intValue());
    }
    @Test
    public void testMultiply() {
        ComplexNumber a = new ComplexNumber(1, 1);
        ComplexNumber b = new ComplexNumber( 1, 1);
        ComplexNumber c = a.multiply(b);
        assertEquals(1, c.getR().intValue());
    }

    @Test
    public void testRange() {
        BigDecimal a = new BigDecimal(0);
        BigDecimal b = new BigDecimal(2);

        double[] steps = MathUtils.range(3, a, b);
        assertEquals(3, steps.length);
//        assertEquals(-1, steps[0]);
//        assertEquals(0, steps[1]);
//        assertEquals(1, steps[2]);
    }
}
