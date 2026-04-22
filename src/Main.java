import src.cas.Const;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Const Num = new Const(5.0);
        Map<String, Double> env = new HashMap<>();
        env.put("x", 10.0);   // x = 10
        env.put("y", 3.5);    // y = 3.5

        double result = Num.eval(env); // returns 5.0 because it's a constant
        System.out.println(result);

        
    }
}
//javac -d out src/cas/*.java src/Main.java
//java -cp out Main