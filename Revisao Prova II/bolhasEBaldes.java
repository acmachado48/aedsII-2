
import java.util.Scanner;

class bolhasEBaldes{
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int N; //interios na sequencia
            boolean keepReading = true;
            
            
             while (keepReading) {
                N = sc.nextInt();
            if (N == 0) {
                keepReading = false;
            } else {
                String nome;
                int[] vetor = new int[N];
                for(int j=0; j<N ;j++){
                    vetor[j] = sc.nextInt();
                    
                }
                
                nome = ord(vetor);
                System.out.println(nome);
                
            }
         }
        } 
    }

  static String ord (int[] vetor){
        int mov = 0;
        int n = vetor.length;
        String nome = null;

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

        if(mov % 2 == 0){
            nome = "Carlos";
        }else nome = "Marcelo";

        return nome;
    }
    }
