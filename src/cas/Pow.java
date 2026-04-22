package src.cas;

import java.util.Map;


public class Pow extends Expr {
    Expr left;
    Expr right;
    String name;

    public Pow(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }



    public double eval(Map<String, Double> env) {
    if (!env.containsKey(name)) {
        throw new IllegalArgumentException("Variable " + name + " not defined in environment.");
    }
    return env.get(name);
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
        return name;
    }
}

