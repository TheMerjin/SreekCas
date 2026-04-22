#SreekCas

An attempt to implement a simple Computer Algebra System in Java.

<details>
  <summary>Why Java?</summary>

Just because I have the AP CSA exam soon and I don't know Java at all so time to learn on the Job. I also want to get better at strong type oriented static languages and OOP which this is perfect for.

</details>



The project is based on an expression tree, where each node is a constant, variable, or operation. For example, the expression 2*x + 3 can be represented as:

       +
      / \
     *   3
    / \
   2   x

Evaluating this tree with x = 5 would yield 2*5 + 3 = 13.

Current Features:

Supports Var (variables), Const (constants), Add (addition), and Mul (multiplication).
Can evaluate expressions given a variable environment.
Can expand expressions.
Performs basic simplifications, such as:
2 + 3 → 5
x + x → 2*x
1*x → x, 0*x → 0
x + 2*x → 3*x
x + x*y → (y+1)*x

Nested additions and more complex like-term combinations are not yet fully implemented.

TODO for Add.simplify():

/*
1. Mul + Mul with the same variable
   - Example: 2*x + 3*x -> 5*x

2. Nested Adds (flattening)
   - Example: (x + 5) + (x + 10) -> x + x + 15

3. Combining constants in Add nodes with Mul
   - Example: x + (2 + 3) -> x + 5

4. Ordering / normalization (optional)
   - Ensure Var or Mul comes first, then constants

5. General like-term combining helper
   - Extract coefficient and variable
   - Handles Var -> 1*Var, Mul(Const, Var) -> coefficient*Var
   - Simplifies Var + Var, Var + Mul, Mul + Mul

6. Handle more complex expressions
   - Example: (x + y) + (x + 2*y)
   - May require recursive simplification or expansion
*/