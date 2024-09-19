
import java.util.Scanner;
import java.util.Stack;

public class menorDaPilha {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Stack<Integer> pilha = new Stack<>();

        int N = sc.nextInt(); //numero de operacoes

        int i = 0;
        while(i < N){
            String tipo = sc.next(); 

            if(tipo.equals("PUSH")){
                int x = sc.nextInt();
                pilha.push(x);
            } else if(tipo.equals("POP")){
                pilha.pop();

            }else if(tipo.equals("MIN")){
                int min = pilha.peek(); //o numero que esta no topo

               for(int num : pilha){
                if(num < min){
                    min = num;
                }
               }

               System.out.println(min);

            }
               i++; 
            }

             sc.close();  
    }         
}
        

