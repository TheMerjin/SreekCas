import src.cas.Const;
import src.cas.Expr;
import src.cas.Var;
import src.cas.Add;
import src.cas.Mul;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Const Num = new Const(5.0);
        Const Num2 = new Const(10.0); 
        Add plus = new Add(Num, Num2);
        Map<String, Double> myMap = new HashMap<>();

        // Add some key-value pairs
        myMap.put("x", 5.0);
        myMap.put("y", 10.0); 

        double result = plus.eval(myMap);
        System.out.println(result);
        String greeting = plus.toString();
        System.out.println(greeting);
        Expr expr = new Add(
    new Var("x"),
    new Const(5)
        );
        Expr expr2 = new Add(
    new Var("x"),
    new Const(5)
        );

    

         
        Expr sumExpr = new Add(new Mul( new Const(10), new Var("x")), new Mul(new Const(3), new Var("x")));

    System.out.println(expr);           // prints: (x + 5)
    System.out.println(expr.expand()); // prints: (x + 5)
    // let's test whether it will prpery handle multiple multiplication now : )
    System.out.println(sumExpr.expand());
    System.out.println(sumExpr.expand().simplify()); // let's hope this prints (x+2) + (2*x )  = 3x + 2nope this isn't implemented yet

        
    }
}
//javac -d out src/cas/*.java src/Main.java
//java -cp out Main