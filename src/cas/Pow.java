package src.cas;

import java.util.Map;


public class Pow extends Expr {
    Expr left;
    Expr right;

    public Pow(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double eval(Map<String, Double> env) {
        double base = left.eval(env);
        double exponent = right.eval(env);
        return Math.pow(base, exponent);
    }

    @Override
    public Expr expand() {
        return this; // variables stay as-is
    }

    @Override
    public Expr simplify() {
        Expr base = left.simplify();
        Expr exp = right.simplify();
        if (base instanceof Const a && exp instanceof Const b) {
            return new Const(Math.pow(a.value, b.value));

        }
        if (right instanceof Const a) {
            if (a.value == 0) {
                return new Const(1);

            }
        }
        if (right instanceof Const a) {
            if (a.value == 1) {
                return left;

            }
        }
return new Pow(base, exp);
         // variables stay as-is
    }
    // fallback for now

    public String toString() {
        return "(" + left + "^" + "(" + right + ")" + ")";
    }
    @Override
    public Expr diff(Var var) {
        
        if (left instanceof Var v && right instanceof Const c) {
            if (var.name.equals(v.name)) {
                return new Mul(
                        new Const(c.value),
                        new Pow(v, new Const(c.value - 1)));

            }
        }
        
        return new Const(0);
    }
}

