

import java.util.Scanner;

public class Matriz {
    private int[][] matriz;
    private int linhas;
    private int colunas;

    public Matriz(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.matriz = new int[linhas][colunas];
    }

    public void lerMatriz(Scanner scanner) {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                matriz[i][j] = scanner.nextInt();
            }
        }
    }

    public void mostrarDiagonalPrincipal() {
        for (int i = 0; i < linhas && i < colunas; i++) {
            System.out.print(matriz[i][i] + " ");
        }
        System.out.println();
    }

    public void mostrarDiagonalSecundaria() {
        for (int i = 0; i < linhas && i < colunas; i++) {
            System.out.print(matriz[i][colunas - 1 - i] + " ");
        }
        System.out.println();
    }

    public static Matriz soma(Matriz a, Matriz b) {
        if (a.linhas != b.linhas || a.colunas != b.colunas) {
            throw new IllegalArgumentException("As matrizes devem ter o mesmo tamanho para realizar a soma.");
        }

        Matriz resultado = new Matriz(a.linhas, a.colunas);
        for (int i = 0; i < a.linhas; i++) {
            for (int j = 0; j < a.colunas; j++) {
                resultado.matriz[i][j] = a.matriz[i][j] + b.matriz[i][j];
            }
        }

        return resultado;
    }

    public static Matriz multiplicacao(Matriz a, Matriz b) {
        if (a.colunas != b.linhas) {
            throw new IllegalArgumentException("O número de colunas da primeira matriz deve ser igual ao número de linhas da segunda matriz para realizar a multiplicação.");
        }

        Matriz resultado = new Matriz(a.linhas, b.colunas);
        for (int i = 0; i < a.linhas; i++) {
            for (int j = 0; j < b.colunas; j++) {
                for (int k = 0; k < a.colunas; k++) {
                    resultado.matriz[i][j] += a.matriz[i][k] * b.matriz[k][j];
                }
            }
        }

        return resultado;
    }

    public void imprimirMatriz() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numCasosTeste = scanner.nextInt();

        for (int caso = 0; caso < numCasosTeste; caso++) {
            int linhasA = scanner.nextInt();
            int colunasA = scanner.nextInt();

            Matriz matrizA = new Matriz(linhasA, colunasA);
            matrizA.lerMatriz(scanner);

            int linhasB = scanner.nextInt();
            int colunasB = scanner.nextInt();

            Matriz matrizB = new Matriz(linhasB, colunasB);
            matrizB.lerMatriz(scanner);

            // Mostrar diagonais principais e secundárias
            matrizA.mostrarDiagonalPrincipal();
            matrizA.mostrarDiagonalSecundaria();

            // Realizar soma e mostrar resultado
            Matriz resultadoSoma = Matriz.soma(matrizA, matrizB);
            resultadoSoma.imprimirMatriz();

            // Realizar multiplicação e mostrar resultado
            Matriz resultadoMultiplicacao = Matriz.multiplicacao(matrizA, matrizB);
            resultadoMultiplicacao.imprimirMatriz();
        }

        scanner.close();
    }
}