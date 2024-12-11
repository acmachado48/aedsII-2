import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class PilhaDeCartas {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int n = sc.nextInt();
            if (n == 0) break; // Encerra o processamento ao encontrar 0

            Queue<Integer> pilha = new LinkedList<>();
            for (int i = 1; i <= n; i++) {
                pilha.add(i); // Preenche a pilha com cartas de 1 a n
            }

            StringBuilder descartadas = new StringBuilder();

            while (pilha.size() > 1) {
                // Remove e registra a carta descartada
                if (descartadas.length() > 0) {
                    descartadas.append(", ");
                }
                descartadas.append(pilha.poll());

                // Move a próxima carta para a base da pilha
                pilha.add(pilha.poll());
            }

            // A carta restante
            int cartaRestante = pilha.poll();

            // Saída conforme o formato solicitado
            System.out.println("Discarded cards: " + descartadas);
            System.out.println("Remaining card: " + cartaRestante);
        }

        sc.close();
    }
}
