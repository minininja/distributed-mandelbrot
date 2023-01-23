package org.dorkmaster.mandlebrot.common.math;

import java.math.BigDecimal;

import static org.dorkmaster.mandlebrot.common.math.Calculator.Operand.*;
import static org.dorkmaster.mandlebrot.common.math.Calculator.calc;

public class MathUtils {
    public static double[] range(int steps, BigDecimal min, BigDecimal max) {
        double[] result = new double[steps];
        BigDecimal range = calc(min, max, MINUS, ABS);
        for (int i = 0; i < steps; i++) {
            result[i] = calc(min, range, steps, DIVIDE, i, MULTIPLY, PLUS).doubleValue();
        }
        return result;
    }
}
