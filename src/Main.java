import src.cas.Const;
import src.cas.Expr;
import src.cas.Func;
import src.cas.Var;
import src.cas.Add;
import src.cas.Mul;
import src.cas.Pow;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Main {

    static void test(Expr e) {
        System.out.println("Original:   " + e);
        Expr expanded = e.expand();
        System.out.println("Expanded:   " + expanded);
        System.out.println("Simplified: " + expanded.simplify());
        System.out.println("----------------------------");
    }

static void diffTest(Expr e) {
    System.out.println("Expr:        " + e);
    Expr d = e.diff(new Var("x"));
    System.out.println("Derivative:  " + d);
    System.out.println("Simplified:  " + d.simplify());
    System.out.println("----------------------------");
}
    public static void main(String[] args) {
        


    System.out.println("let's try diffs");
   System.out.println("=== DIFF TEST SUITE ===");

diffTest(new Func("sin", new Var("x")));
// expected: cos(x)

diffTest(new Func("cos", new Var("x")));
// expected: -sin(x)

diffTest(new Func("exp", new Var("x")));
// expected: exp(x)

diffTest(new Func("log", new Var("x")));
// expected: 1/x

diffTest(new Func("sin", new Mul(new Var("x"), new Const(2))));
// expected: cos(2x) * 2

diffTest(new Func("sin",
    new Func("cos", new Var("x"))
));
// expected: cos(cos(x)) * (-sin(x))

diffTest(new Mul(
    new Var("x"),
    new Func("sin", new Var("x"))
));
diffTest(new Func("sin", new Var("y")));
// expected: 0

// expected: sin(x) + x*cos(x)
diffTest(
    new Func(
        "sin",
        new Func("y", new Var("x"))
    )
);
System.out.println("=== EXTRA DIFF TEST SUITE ===");

// 1. Basic sanity (constant inside function)
diffTest(new Func("sin", new Const(5)));
// expected: 0

// 2. Simple chain rule with power
diffTest(new Func("sin",
    new Pow(new Var("x"), new Const(2))
));
// expected: cos(x^2) * 2x

// 3. Exp of polynomial
diffTest(new Func("exp",
    new Add(new Mul(new Const(3), new Var("x")), new Const(2))
));
// expected: exp(3x+2) * 3

// 4. Log of product
diffTest(new Func("log",
    new Mul(new Var("x"), new Const(5))
));
// expected: (1/(5x)) * 5

// 5. Nested function chain (3 layers)
diffTest(new Func("sin",
    new Func("cos",
        new Func("exp", new Var("x"))
    )
));
// expected: cos(cos(exp(x))) * (-sin(exp(x)) * exp(x))

// 6. Function inside product rule
diffTest(new Mul(
    new Func("sin", new Var("x")),
    new Func("cos", new Var("x"))
));
// expected: cos(x)*cos(x) + sin(x)*(-sin(x))

// 7. Product + inner function complexity
diffTest(new Mul(
    new Func("sin", new Mul(new Var("x"), new Var("x"))),
    new Var("x")
));
// expected: cos(x^2)*2x*x + sin(x^2)

// 8. Mixed Add + Func + Pow
diffTest(new Add(
    new Func("sin", new Var("x")),
    new Pow(new Var("x"), new Const(3))
));
// expected: cos(x) + 3x^2

// 9. Function of function of function (deep nesting stress)

diffTest(new Func("cos",
    new Func("sin",
        new Func("exp",
            new Func("cos", new Var("x"))
        )
    )
));

// expected: giant chain rule expression

// 10. Zero dependency inside function
diffTest(new Func("sin", new Var("y")));
// expected: 0

// 11. Mixed variables (only x differentiates)
diffTest(new Add(
    new Func("sin", new Var("x")),
    new Func("sin", new Var("y"))
));
// expected: cos(x)

// 12. Power of function
diffTest(new Pow(
    new Func("sin", new Var("x")),
    new Const(2)
));
// expected: 2*sin(x)*cos(x)

// 13. Function multiplied by constant
diffTest(new Mul(
    new Const(4),
    new Func("exp", new Var("x"))
));
// expected: 4*exp(x)

// 14. Full chaos expression (real stress test)
diffTest(new Mul(
    new Func("sin", new Pow(new Var("x"), new Const(2))),
    new Add(
        new Func("cos", new Var("x")),
        new Var("x")
    )
));
// expected: product rule + chain rule combined


// → needs chain rule → currently wrong
// → 6*x
    // Expected: 9 * x^2
    // let's hope this prints (x+2) + (2*x )  = 3x + 2nope this isn't implemented yet

    

        
    }
}
//javac -d out src/cas/*.java src/Main.java
//java -cp out Main