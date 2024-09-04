import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String comp = "";

        Stack<Character> p1 = new Stack<>();
        Stack<Character> p2 = new Stack<>();

        while (!comp.equals("FIM")) {
            comp = sc.nextLine();

            for (int i = 0; i < comp.length(); i++) {
                char c = comp.charAt(i);

                if (c == '(') {
                    p1.push(c);
                } else if (c == ')') {
                    if (!p1.isEmpty()) {
                        // Se houver um '(' na pilha1, ele corresponde ao ')'
                        p1.pop();
                    } else {
                        // Se não houver um '(' correspondente, empilhar o ')'
                        p2.push(c);
                    }
                }
            }
             //se cada ( tiver um correspondente ) saida "correto" se nao "incorreto"
            // Se ambas as pilhas estiverem vazias, os parênteses estão equilibrados
            if (p1.isEmpty() && p2.isEmpty()) {
                System.out.println("Correto");
            } else {
                System.out.println("Incorreto");
            }

            p1.clear();
            p2.clear();
        }

        sc.close();
    }
}

            

           
    