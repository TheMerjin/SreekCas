package src.cas;

import java.util.Map;


public class Var extends Expr {
    String name;

    public Var(String name) {
        this.name = name;
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

