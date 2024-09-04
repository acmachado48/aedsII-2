

/*Crie um método iterativo em Java que receba como parâmetro uma string e 
etorne seu número de caracteres maiúsculos. Em seguida, teste o método
 anterior usando redirecionamento de entrada e saída. A entrada padrão é 
 composta por várias linhas sendo que a última contém a palavra FIM. 
 A saída padrão contém um número inteiro para cada linha de entrada. */

import java.util.Scanner;


 public class maiusculaInt {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String nome = "";
        

        while (!(nome.equals("FIM"))) {
            nome = sc.nextLine();
            int cont = 0;

            for(int i=0; i<nome.length(); i++){
                char pos = nome.charAt(i);
                if(Character.isUpperCase(pos)) {
                    cont++;
                }
            }
            System.out.println(cont);    
                            
        }
    }
 
    
 }