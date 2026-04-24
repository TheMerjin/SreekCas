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

public class TestIntegration {
    public static void main(String[] args) {
        Var x = new Var("x");
        Var y = new Var("y");
        Const c4 = new Const(4.0);
        Pow x2 = new Pow(x, new Const(2));

        // --- Test 1: Constant ---
        System.out.println("∫4 dx = " + c4.integrate(x));

        // --- Test 2: Variable ---
        System.out.println("∫x dx = " + x.integrate(x));
        System.out.println("∫y dx = " + y.integrate(x));

        // --- Test 3: Power ---
        System.out.println("∫x^2 dx = " + x2.integrate(x));

        // --- Test 4: Sum rule ---
        Expr sumExpr = new Add(x2, new Mul(new Const(3), x));
        System.out.println("∫(x^2 + 3*x) dx = " + sumExpr.integrate(x));

        // --- Test 5: Integration by parts examples ---
        Func exp = new Func("exp", x);
        Expr ibp1 = new Mul(x, exp);
        System.out.println("∫x*e^x dx = " + ibp1.integrate(x));

        Func sinx = new Func("sin", x);
        Expr ibp2 = new Mul(x, sinx);
        System.out.println("∫x*sin(x) dx = " + ibp2.integrate(x));

        // --- Test 6: Unevaluated integral ---
        Pow xX = new Pow(x, x);
        System.out.println("∫x^x dx = " + xX.integrate(x));
    }
}

//javac -d out src/cas/*.java src/TestIntegration.java 
//java -cp out TestIntegration