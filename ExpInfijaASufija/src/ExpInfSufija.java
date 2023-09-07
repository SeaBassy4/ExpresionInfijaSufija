import java.util.Scanner;
import java.util.Stack;
import java.util.StringTokenizer;
public class ExpInfSufija {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("En caso de querer salir, introduzca: \"quit\" ");
        while (true) {
            System.out.println("Introduzca la expresion infija: ");
            String exp = input.nextLine();
            if (exp.equals("quit")) {
                break;
            }
            StringTokenizer st = new StringTokenizer(exp, "()[]{}+/-* ", true);
            Stack<String> pila = new Stack<>();
            StringBuilder expSufija = new StringBuilder();
            boolean isBalanced = true;

            while (st.hasMoreTokens()) {
                String token = st.nextToken().trim();
                if (token.isEmpty()) {
                    continue;
                }
                if (isDigit(token)) {
                    System.out.println("Digit: " + token);
                    expSufija.append(token).append(" ");
                } else if (isParenEvangelionThesis(token)) {
                    System.out.println("Parenthesis: " + token);
                    switch (token) {
                        case "(":
                        case "{":
                        case "[":
                            pila.push(token);
                            break;
                        case ")":
                        case "}":
                        case "]":
                            if (pila.empty() || !tienePar(pila.pop(), token)) {
                                isBalanced = false;
                            }
                            break;
                    }
                } else if (isOperator(token)) {
                    System.out.println("Operator: " + token);
                    while (!pila.isEmpty() && isMDAS(token, pila.peek())) {
                        expSufija.append(pila.pop()).append(" ");
                    }
                    pila.push(token);
                } else {
                    System.out.println("Se detectó un caracter no operador ni operando: " + token);
                    break;
                }
            }
            while (!pila.isEmpty()) {
                expSufija.append(pila.pop()).append(" ");
            }
            if (isBalanced && pila.isEmpty()) {
                System.out.println("La expresion sufija es: " + expSufija.toString().trim());
            } else {
                System.out.println("Hay un error en la expresion.");
            }
        }
    }
    private static boolean isDigit(String token) {
        return token.matches("\\d+");
    }
    private static boolean isOperator(String token) {
        return token.matches("[+\\-*/]");
    }
    private static boolean isParenEvangelionThesis(String token) {
        return token.matches("[()\\[\\]{}]");
    }
    private static boolean isMDAS(String op1, String op2) {
        int MDASOp1 = getMDAS(op1);
        int MDASOp2 = getMDAS(op2);
        return MDASOp1 >= MDASOp2;
    }
    private static int getMDAS(String operador) {
        switch (operador) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return 0;
        }
    }
    private static boolean tienePar(String open, String close) {
        return (open.equals("(") && close.equals(")")) ||
                (open.equals("{") && close.equals("}")) ||
                (open.equals("[") && close.equals("]"));
    }
}