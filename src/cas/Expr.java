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
}