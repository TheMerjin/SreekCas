package src.cas;

import java.util.Map;


public class Const extends Expr {
    double value;

    public Const(double value) {
        this.value = value;
    }
    @Override
    public Expr expand() {
        return this; // constants are already "expanded"
    }
    @Override
    public Expr simplify() {
        return this; // constants are already "expanded"
    }



    public double eval(Map<String, Double> env) {
        return value;
    }

    public String toString() {
        return Double.toString(value);
    }
}

