package src.cas;

import java.util.Map;

public class Add extends Expr { // Add should extend Expr
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
    public Expr expand() {
        // expand left and right
        return new Add(left.expand(), right.expand());
    }

    @Override
    public Expr simplify() {
        // expand left and right
        Expr simplifiedLeft = left.simplify();
        Expr simplifiedRight = right.simplify();

        if (simplifiedLeft instanceof Const lc && simplifiedRight instanceof Const rc) {
            return new Const(lc.value + rc.value); // eg. const 1+ comst 2 = const 3 eg. 6 + 1 = 7
        }
        if (simplifiedLeft instanceof Const lc && lc.value == 0)
            return simplifiedRight; // 0 + x == x 
        if (simplifiedRight instanceof Const rc && rc.value == 0) // x + 0 == x
            return simplifiedLeft;
        if (simplifiedLeft instanceof Var lv && simplifiedRight instanceof Var rv &&
                lv.name.equals(rv.name)) {
            return new Mul(new Const(2), lv); // x + x → 2*x
        }

        // now we need tackle some complicated cases 

        // what if one side is a Var and other side a Mult

        if (simplifiedLeft instanceof Var lv && simplifiedRight instanceof Mul rv) {
            if (rv.right instanceof Var v && v.name.equals(lv.name)) {
                return new Mul(new Add(rv.left, new Const(1)).simplify(), simplifiedLeft);
            }
        }

        if (simplifiedRight instanceof Var rvVar && simplifiedLeft instanceof Mul lm) {
            if (lm.left instanceof Const lc && lm.right instanceof Var lv2 && lv2.name.equals(rvVar.name)) {
                Expr newCoeff = new Add(lc, new Const(1)).simplify();
                return new Mul(newCoeff, rvVar);
            }
        }

            // implemented 2x *x and x*2x and y*x + x == (y+1 ) * x and TODO: implement these
            /*
            TODO: Additional simplifications to implement:
            
           
            
            
            
            
            
            2. Nested Adds (flattening)
            - Example: (x + 5) + (x + 10) -> x + x + 15
            
            3. Combining constants in Add nodes with Mul
            - Example: x + (2 + 3) -> x + 5
            
            4. Ordering / normalization (optional)
            - Ensure Var or Mul comes first, then constants for consistency
            
            5. General like-term combining helper
            - Implement a helper to extract coefficient and variable
            - Handles Var -> 1*Var, Mul(Const, Var) -> coefficient*Var
            - Simplifies Var + Var, Var + Mul, Mul + Mul
            
            6. Handle more complex expressions
            - Example: (x + y) + (x + 2*y)
            - May require recursive simplification or expansion
            */

            //Mul with Mull eg. 2*x + 3*x => 5*x 

            if (simplifiedLeft instanceof Mul lm1 && simplifiedRight instanceof Mul rm) {
                if (lm1.right instanceof Var lv && rm.right instanceof Var rv && lv.name.equals(rv.name)
                        && lm1.left instanceof Const lc1 && rm.left instanceof Const lc2) {
                    return new Mul(new Const(lc1.value + lc2.value), lv);
                }
            }



            // fall back if no simplification can be seen 
            // :- )
            return new Add(simplifiedLeft, simplifiedRight);
        
    }

    @Override
    public String toString() {
        return "(" + left + " + " + right + ")";
    }

}