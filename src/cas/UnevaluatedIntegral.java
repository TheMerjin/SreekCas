package src.cas;

import java.util.Map;



public class UnevaluatedIntegral extends Expr {
    private final Expr integrand;
    private final Var variable;

    public UnevaluatedIntegral(Expr integrand, Var variable) {
        this.integrand = integrand;
        this.variable = variable;
    }

    @Override
    public double eval(Map<String, Double> env) {
        throw new UnsupportedOperationException("Cannot evaluate unevaluated integral numerically.");
    }

    @Override
    public String toString() {
        return "∫(" + integrand.toString() + ") d" + variable.name;
    }

    @Override
    public Expr expand() {
        return new UnevaluatedIntegral(integrand.expand(), variable);
    }

    @Override
    public Expr simplify() {
        return new UnevaluatedIntegral(integrand.simplify(), variable);
    }

    @Override
    public Expr diff(Var var) {
        // d/dx ∫ f(t) dt = f(x) only if var == variable, otherwise 0
        if (var.equals(variable)) {
            return integrand;
        } else {
            return new Const(0);
        }
    }

    @Override
    public boolean dependsOn(Var var) {
        return integrand.dependsOn(var);
    }

    @Override
    public Expr integrate(Var var) {
        // Nested integral: wrap again
        return new UnevaluatedIntegral(this, var);
    }
}