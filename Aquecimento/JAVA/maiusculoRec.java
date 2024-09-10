package Aquecimento.JAVA;
import java.util.Scanner;

public class maiusculoRec {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        solicitarEntrada(sc);  
        sc.close();  
    }

    // Método recursivo para solicitar entradas
    private static void solicitarEntrada(Scanner sc) {
        if (sc.hasNextLine()) {
            String nome = sc.nextLine();

            // Verifica se a string é "FIM" para encerrar a recursão
            if (!nome.equals("FIM")) {
                int cont = contarMaiusculas(nome, 0);
                System.out.println(cont);

                solicitarEntrada(sc);
            }
        }
    }

    private static int contarMaiusculas(String nome, int index) {
        // Caso base: se atingiu o fim da string, retorna 0
        if (index >= nome.length()) {
            return 0;
        }

        int cont = Character.isUpperCase(nome.charAt(index)) ? 1 : 0;

        return cont + contarMaiusculas(nome, index + 1);
    }
}
