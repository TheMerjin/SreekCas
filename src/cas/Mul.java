package src.cas;

import java.util.Map;

public class Mul extends Expr {
    Expr left;
    Expr right;

    public Mul(Expr left, Expr right) {
        this.left = left;
        this.right = right;
    }

    
    @Override
public Expr integrate(Var var) {
    // Case 1: left is constant → factor out
    if (left instanceof Const) {
        return new Mul(left, right.integrate(var));
    }

    // Case 2: right is constant → factor out
    if (right instanceof Const) {
        return new Mul(right, left.integrate(var));
    }

    // Case 3: Try simple integration by parts heuristic
    // u = algebraic (x^n or Var), dv = rest
    Expr u = null;
    Expr dv = null;

    if (left.dependsOn(var) && !(left instanceof Const) && (left instanceof Pow || left instanceof Var)) {
        u = left;
        dv = right;
    } else if (right.dependsOn(var) && !(right instanceof Const) && (right instanceof Pow || right instanceof Var)) {
        u = right;
        dv = left;
    }

    if (u != null && dv != null) {
        // du = derivative of u
        Expr du = u.diff(var);
        // v = integral of dv
        Expr v = dv.integrate(var);

        // Apply integration by parts: u*v - ∫v*du
        return new Add(new Mul(u, v), new Mul(new Const(-1), new Mul(v, du).integrate(var)));
    }

    // Case 4: fallback: cannot solve
    return new UnevaluatedIntegral(this, var);
}

    @Override
    public Expr simplify() {

        // Simplify children first
        Expr leftS = left.simplify();
        Expr rightS = right.simplify();

        // 1. If both are constants: multiply them
        if (leftS instanceof Const lc && rightS instanceof Const rc) {
            return new Const(lc.value * rc.value);
        }

        // 2. If either side is 1: return the other
        if (leftS instanceof Const lc1 && lc1.value == 1)
            return rightS;
        if (rightS instanceof Const rc1 && rc1.value == 1)
            return leftS;

        // 3. If either side is 0: return 0
        if (leftS instanceof Const lc0 && lc0.value == 0)
            return new Const(0);
        if (rightS instanceof Const rc0 && rc0.value == 0)
            return new Const(0);

        if (leftS instanceof Var && rightS instanceof Const rc) {
            // Swap so constant is on the left
            return new Mul(rc, leftS);
        }

        if (leftS instanceof Mul mul1 && rightS instanceof Mul mul2) {
            return new Mul(new Mul(mul1.left, mul2.left).simplify(), new Mul(mul1.right, mul2.right).simplify());
        }
            
        if (leftS instanceof Const c1 && rightS instanceof Mul mul3) {
            return new Mul(new Mul(c1, mul3.left).simplify(), mul3.right)
                    .simplify();

        }
        
        if (rightS instanceof Const c2 && leftS instanceof Mul mul4) {
            return new Mul(new Mul(c2, mul4.left).simplify(), mul4.right)
                    .simplify();

        }
        

        if (leftS instanceof Mul mul5 && rightS instanceof Mul mul6) {
            return new Mul(new Mul(mul5.left, mul6.left).simplify(), new Mul(mul5.right, mul6.right).simplify())
                    .simplify();
        }
        if (leftS instanceof Var v1 && rightS instanceof Var v2) {
            if (v1.name.equals(v2.name)) {
                return new Pow(v1, new Const(2));
            }
        }
        if (leftS instanceof Pow p && rightS instanceof Var v) {
            if (p.left instanceof Var base && base.name.equals(v.name)
                    && p.right instanceof Const exp) {

                return new Pow(base, new Const(exp.value + 1));
            }
        }
        if (leftS instanceof Var v && rightS instanceof Pow p) {
            if (p.left instanceof Var base && base.name.equals(v.name)
                    && p.right instanceof Const exp) {

                return new Pow(base, new Const(exp.value + 1));
            }
        }
        if (leftS instanceof Pow p1 && rightS instanceof Pow p2) {
    if (p1.left instanceof Var v1 && p2.left instanceof Var v2
            && v1.name.equals(v2.name)
            && p1.right instanceof Const e1
            && p2.right instanceof Const e2) {

        return new Pow(v1, new Const(e1.value + e2.value));
    }
}



        


        // 4. Keep as Mul if can't simplify further
        return new Mul(leftS, rightS);
    }
    @Override
    public Expr expand() {
        Expr leftE = left.expand();
    Expr rightE = right.expand();
        if (rightE instanceof Add add) {
        return new Add(
            new Mul(leftE, add.left).expand(),
            new Mul(leftE, add.right).expand()
        );
    }

    // (a + b) * c → (a*c + b*c)
    if (leftE instanceof Add add) {
        return new Add(
            new Mul(add.left, rightE).expand(),
            new Mul(add.right, rightE).expand()
        );
    }
        // expand left and right
        return new Mul(left.expand(), right.expand());
    }

    @Override
    public double eval(Map<String, Double> env) {
        return left.eval(env) * right.eval(env);
    }

    public Expr diff(Var var) {
        return new Add(new Mul(this.left, this.right.diff(var)), new Mul(this.left.diff(var), this.right));
    }

    @Override
    public String toString() {
        return "(" + left + " * " + right + ")";
    }
    @Override
public boolean dependsOn(Var v) {
    return left.dependsOn(v) || right.dependsOn(v);
}
}