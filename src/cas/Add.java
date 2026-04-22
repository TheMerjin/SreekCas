package src.cas;

import java.util.Map;

public class Add extends Expr {  // Add should extend Expr
    Expr left;
    Expr right;

    public Add(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public double eval(Map<String, Double> env) {
        return left.eval(env) + right.eval(env);
    }

    @Override
    public String toString() {
        return "(" + left + " + " + right + ")";
    }
}