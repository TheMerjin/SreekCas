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
        return this; // variables stay as-is
    }
    

    public String toString() {
        return "(" + left + "^" + "(" + right + ")" +")";
    }
}

