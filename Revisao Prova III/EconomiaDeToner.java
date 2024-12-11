import java.util.*;

public class EconomiaDeToner {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder output = new StringBuilder();

        while (true) {
            int N = sc.nextInt();
            if(N == 0){
break;
            } 
            sc.nextLine();  // Consumir a nova linha após o número N
            String[] telefones = new String[N];

            for (int i = 0; i < N; i++) {
                telefones[i] = sc.nextLine();
            }

            Arrays.sort(telefones);
            int economiaTotal = 0;

            for (int i = 0; i < N - 1; i++) {
                economiaTotal += prefixoComum(telefones[i], telefones[i + 1]);
            }

            output.append(economiaTotal).append("\n");
        }

        System.out.print(output);
    }

    private static int prefixoComum(String a, String b) {
        int maxLen = Math.min(a.length(), b.length());
        int i = 0;
        while (i < maxLen && a.charAt(i) == b.charAt(i)) {
            i++;
        }
        return i;
    }
}
