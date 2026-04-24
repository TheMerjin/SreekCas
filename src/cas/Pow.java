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
    public Expr integrate(Var var) {
        Expr base = left;
        Expr exponent = right;
        if (base instanceof Var && ((Var) base).name.equals(var.name) && exponent instanceof Const) {
            double n = ((Const) exponent).value;
            if (n == -1) {
                // ∫x^-1 dx = ln|x|
                return new Func("ln", base);
            } else {
                // ∫x^n dx = (1/(n+1)) * x^(n+1)
                return new Mul(new Pow(new Const((n + 1)), new Const(-1)), new Pow(base, new Const(n + 1)));
            }
        }
        // fallback for complex bases or exponents
        return new UnevaluatedIntegral(this, var);
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
    @Override
public boolean dependsOn(Var v) {
    return left.dependsOn(v) || right.dependsOn(v);
}
}

