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
        

        Expr sumExpr = new Mul(new Mul(new Var("x"), new Const(3)), new Mul(new Var("x"), new Const(3))); // (x+ y) + (x + y) // this is 3y*2y = 6Y^2

   
    // let's test whether it will prpery handle multiple multiplication now : )
    System.out.println(sumExpr.expand());
    System.out.println(sumExpr.expand().simplify()); // let's hope this prints (x+2) + (2*x )  = 3x + 2nope this isn't implemented yet

        
    }
}
//javac -d out src/cas/*.java src/Main.java
//java -cp out Main