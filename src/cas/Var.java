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
    

    public Expr diff(Var var) {
        if (var.name != name) {
            return new Const(0);
        }
        return new Const(1);
    }
    @Override
    public boolean dependsOn(Var v) {
        return this.name.equals(v.name);
    }

    public Expr integrate(Var var) {
        if (this.name.equals(var.name)) {
            return new Mul (new Pow(this, new Const(2)), new Pow(new Const(2), new Const(-1)));
            
        } else {
            return new Const(0);
        }

        
    }

    

    public String toString() {
        return name;
    }
}

