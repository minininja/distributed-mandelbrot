package org.dorkmaster.mandlebrot.common.math;

import java.io.Serializable;
import java.math.BigDecimal;

import static org.dorkmaster.mandlebrot.common.math.Calculator.Operand.MULTIPLY;
import static org.dorkmaster.mandlebrot.common.math.Calculator.Operand.PLUS;
import static org.dorkmaster.mandlebrot.common.math.Calculator.calc;

public class ComplexNumber implements Serializable {

    protected BigDecimal r;
    protected BigDecimal i;

    public ComplexNumber(Integer r, Integer i) {
        this.r = new BigDecimal(r);
        this.i = new BigDecimal(i);
    }

    public ComplexNumber(BigDecimal r, BigDecimal i) {
        this.r = r;
        this.i = i;
    }

    public ComplexNumber plus(ComplexNumber b) {
        return new ComplexNumber(
                calc(this.r, b.r, PLUS),
                calc(this.i, b.i, PLUS)
        );
    }

    public ComplexNumber multiply(ComplexNumber b) {
        return new ComplexNumber(
                calc(this.r, b.r, MULTIPLY),
                calc(this.i, b.i, MULTIPLY)
        );
    }

    public double abs() {
        BigDecimal r = this.r.pow(2);
        BigDecimal i = this.i.pow(2);
        return Math.sqrt(r.subtract(i).doubleValue());
    }

    public BigDecimal getR() {
        return r;
    }

    public BigDecimal getI() {
        return i;
    }
}