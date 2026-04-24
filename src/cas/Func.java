package src.cas;

import java.util.Map;

public class Func extends Expr {
    String name;
    Expr arg;

    public Func(String name, Expr arg) {
        this.name = name;
        this.arg = arg;
    }
    public Expr integrate(Var var) {
        return new UnevaluatedIntegral(this, var);
    }

    @Override
    public double eval(Map<String, Double> env) {
        double x = arg.eval(env);

        return switch (name) {
            case "sin" -> Math.sin(x);
            case "cos" -> Math.cos(x);
            case "exp" -> Math.exp(x);
            case "log" -> Math.log(x);
            default -> throw new RuntimeException("Unknown function: " + name);
        };
    }

    @Override
    public Expr expand() {
        return new Func(name, arg.expand());
    }

    @Override
    public Expr simplify() {
        Expr a = arg.simplify();

        // constant folding
        if (a instanceof Const c) {
            return new Const(eval(Map.of())); // quick evaluation
        }

        return new Func(name, a);
    }

    @Override
    public Expr diff(Var var) {
    

    if (!arg.dependsOn(var)) {
        return new Const(0);
    }

    Expr innerDiff = arg.diff(var);

    return switch (name) {
        case "sin" -> new Mul(new Func("cos", arg), innerDiff);
        case "cos" -> new Mul(new Const(-1),
                new Mul(new Func("sin", arg), innerDiff));
        case "exp" -> new Mul(new Func("exp", arg), innerDiff);
        case "log" -> new Mul(new Pow(arg, new Const(-1)), innerDiff);
        default -> new Mul(new Func(name + "'", arg), innerDiff);
    };
}

    @Override
    public String toString() {
        return name + "(" + arg + ")";
    }
    @Override
public boolean dependsOn(Var v) {
    return arg.dependsOn(v);
}
}