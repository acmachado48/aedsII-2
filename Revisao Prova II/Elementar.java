
import java.util.Scanner;

class Elementar{
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int T = sc.nextInt(); //intancias
            int N; //interios na sequencia
            
            
            int i=0;
            
            while(i<T){
                N = sc.nextInt();
                int[] vetor = new int[N];
                for(int j=0; j<N ;j++){
                    vetor[j] = sc.nextInt();
                    
                }
                //chamar metodo de ordenacao que conte movimentacoes
                int min;
                min = ord(vetor);
                System.out.println(min);
                
                i++;
            }
        } //intancias
    }

  static int ord (int[] vetor){
        int mov = 0;
        int n = vetor.length;

        for(int i=0; i<n; i++){
            int menor = vetor[i];
            int j = i-1;

            while( j>=0 && vetor[j] > menor){
                vetor[j + 1] = vetor[j];
                mov++;
                j = j-1;
            }
           vetor[j + 1 ] = menor;
        }

        return mov;
    }
}