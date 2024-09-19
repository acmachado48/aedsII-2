
import java.util.ArrayList;
import java.util.Scanner;

public class textoAlien {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();// numeros de c presentes no alfabeto alien
        int M = sc.nextInt();// numero de 'letras'na frase a ser analisada

        ArrayList<Character> alf = new ArrayList<>();
        ArrayList<Character> frase = new ArrayList<>();

        String No = sc.nextLine(); //alfabeto
        String MO = sc.nextLine();//frase

        for (int i = 0; i < N; i++) {
            char c = No.charAt(i);
            alf.add(c);    
        }

        for (int i = 0; i < M; i++) {
            char c = MO.charAt(i);
            frase.add(c);    
        }

      //a frase nao basta conter as letras do alfabeto alien mas rtodas precisam estar no alfabeto
      //se usar o contain da lista vai dar verdadeiro se um dos caracteres forem iguais
      //fazer um if, se nao conter adicionar 1 ao cont
      // final se cont > 0 print Nao else SIm


    }
}