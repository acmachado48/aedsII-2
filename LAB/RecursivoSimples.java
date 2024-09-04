import java.util.Scanner;

public class RecursivoSimples {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

      
        int inferior = sc.nextInt();
      
        int superior = sc.nextInt();

        // Imprime em ordem crescente
        imprimirCrescente(inferior, superior);

        // Imprime em ordem decrescente
        imprimirDecrescente(superior, inferior);
    }

    public static void imprimirCrescente(int atual, int superior) {
        if (atual > superior) {
            return;
        }
        System.out.println(atual);
        imprimirCrescente(atual + 1, superior);
    }

    public static void imprimirDecrescente(int atual, int inferior) {
        if (atual < inferior) {
            return;
        }
        System.out.println(atual);
        imprimirDecrescente(atual - 1, inferior);
    }
}
