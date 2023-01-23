package org.dorkmaster.mandlebrot.common.math;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Stack;

public class Calculator {
    protected static MathContext MC = MathContext.DECIMAL128;

    enum Operand {

        PLUS {
            @Override
            BigDecimal exec(Stack<BigDecimal> stack) {
                BigDecimal b = stack.pop();
                return stack.pop().add(b);
            }
        },
        MINUS {
            @Override
            BigDecimal exec(Stack<BigDecimal> stack) {
                BigDecimal b = stack.pop();
                return stack.pop().subtract(b);
            }
        },
        MULTIPLY {
            @Override
            BigDecimal exec(Stack<BigDecimal> stack) {
                BigDecimal b = stack.pop();
                return stack.pop().multiply(b, MC);
            }
        },
        DIVIDE {
            @Override
            BigDecimal exec(Stack<BigDecimal> stack) {
                BigDecimal b = stack.pop();
                return stack.pop().divide(b, MC);
            }
        },
        SQRT {
            @Override
            BigDecimal exec(Stack<BigDecimal> stack) {
                return new BigDecimal(Math.sqrt(stack.pop().doubleValue()));
            }
        },
        ABS {
            @Override
            BigDecimal exec(Stack<BigDecimal> stack) {
                return stack.pop().abs();
            }
        };

        abstract BigDecimal exec(Stack<BigDecimal> stack);
    }

    public static BigDecimal calc(Object... sequence) {
        Stack<BigDecimal> stack = new Stack<>();

        for (Object action: sequence) {
            if (action instanceof Operand) {
                System.out.println(stack.toString() + " " + action);
                BigDecimal result = ((Operand) action).exec(stack);
                System.out.println("  result: " + result);
                stack.push(result);
            } else if (action instanceof BigDecimal) {
                stack.push((BigDecimal) action);
            } else {
                stack.push(new BigDecimal(action.toString()));
            }
        }

        return stack.pop();
    }
}
