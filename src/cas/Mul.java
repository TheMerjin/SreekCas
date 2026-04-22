package src.cas;

import java.util.Map;

public class Mul extends Expr {
    Expr left;
    Expr right;

    public Mul(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Expr simplify() {
        // Simplify children first
        Expr leftS = left.simplify();
        Expr rightS = right.simplify();

        // 1. If both are constants: multiply them
        if (leftS instanceof Const lc && rightS instanceof Const rc) {
            return new Const(lc.value * rc.value);
        }

        // 2. If either side is 1: return the other
        if (leftS instanceof Const lc1 && lc1.value == 1)
            return rightS;
        if (rightS instanceof Const rc1 && rc1.value == 1)
            return leftS;

        // 3. If either side is 0: return 0
        if (leftS instanceof Const lc0 && lc0.value == 0)
            return new Const(0);
        if (rightS instanceof Const rc0 && rc0.value == 0)
            return new Const(0);

        if (leftS instanceof Var && rightS instanceof Const rc) {
            // Swap so constant is on the left
            return new Mul(rc, leftS);
        }

        if (leftS instanceof Mul mul1 && rightS instanceof Mul mul2) {
                return new Mul(new Mul(mul1.left, mul2.left).simplify(), new Mul (mul1.right, mul2.right).simplify()).simplify();
            }
        


        // 4. Keep as Mul if can't simplify further
        return new Mul(leftS, rightS);
    }
    @Override
    public Expr expand() {
        // expand left and right
        return new Mul(left.expand(), right.expand());
    }

    @Override
    public double eval(Map<String, Double> env) {
        return left.eval(env) * right.eval(env);
    }

    @Override
    public String toString() {
        return "(" + left + " * " + right + ")";
    }
}