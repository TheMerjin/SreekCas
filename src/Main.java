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
        

        Expr sumExpr = new Add(new Add(new Mul(new Var("x"), new Const(3)), new Const(10)), new Add( new Var( "x"), new Const (12)) ); // (3x + 10) + (x+12)

   
    // let's test whether it will prpery handle multiple multiplication now : )
    System.out.println(sumExpr.expand());
    System.out.println(sumExpr.expand().simplify()); // let's hope this prints (x+2) + (2*x )  = 3x + 2nope this isn't implemented yet

        
    }
}
//javac -d out src/cas/*.java src/Main.java
//java -cp out Main