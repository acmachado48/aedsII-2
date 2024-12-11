
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class aNota{
    /* Será dado a você um inteiro N representando a quantidade de notas 
  e um valor K. Em seguida você terá um conjunto de N notas. Sua tarefa é 
  determinar qual a soma de todas as K-ésima maiores notas deste conjunto. 
    Este valor irá corresponder a nota que Rafael precisa..
Entrada
A entrada é composta por vários casos de teste e termina com EOF, 
na primeira linha da entrada possui dois inteiros N e K, N <= 10⁶,K > 0, 
K <= N. Na próxima linhas haverá N valores representando as nota NI,
NI <= 10⁵
Saída
A saída será composta por um número representando a nota que Rafael precisa 
para ser aprovado no vestibular, imprima resposta como modulo de 10⁹+7. */

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while(sc.hasNextLine()){
            int N = sc.nextInt(); //qnt de notas
            int K= sc.nextInt();//valor

        //soma k maiores notas

        ArrayList<Integer> notas = new ArrayList<>();
        int resp = 0;


        //ler as notas
        for(int i=0; i<N; i++){
           int c = sc.nextInt();
           notas.add(c);
        }

        //ordenanr
        Collections.sort(notas);

        int j = N - K;

        for(; j<notas.size(); j++){
            resp = resp + notas.get(j);
        }

      System.out.println(resp);
        
    }

        }
        
}