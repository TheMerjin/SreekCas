package src.cas;

import java.util.Map;

public abstract class Expr {

    // Every expression must implement eval
    public abstract double eval(Map<String, Double> env);

    // Override for printing
    @Override
    public abstract String toString();

    public abstract Expr expand();

    public abstract Expr simplify();

    public abstract Expr diff(Var var);

    public abstract boolean dependsOn(Var var);

    public abstract Expr integrate(Var var); 
    
}