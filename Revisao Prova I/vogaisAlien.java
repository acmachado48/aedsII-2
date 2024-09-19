
import java.util.ArrayList;
import java.util.Scanner;

public class vogaisAlien {
  public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);

while(!sc.hasNext()){
   
   String vogais = sc.nextLine();
      ArrayList<Character> v = new ArrayList<>();
      int cont = 0;

      for(int i=0; i < vogais.length(); i++){
         char c = vogais.charAt(i);
         v.add(c);
      }

      String frase = sc.nextLine();

      for(int i=0; i < frase.length(); i++){
         char c = frase.charAt(i);
         
         if(v.contains(c))
           cont++;
      }

     System.out.println(cont);
}
   

  }
}