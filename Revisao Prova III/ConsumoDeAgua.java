import java.io.*;
import java.util.*;

public class ConsumoDeAgua {

    static class Imovel implements Comparable<Imovel> {
        int moradores;
        int consumo;

        Imovel(int moradores, int consumo) {
            this.moradores = moradores;
            this.consumo = consumo;
        }

        @Override
        public int compareTo(Imovel outro) {
            // Comparar pelo consumo por morador
            int thisConsumoPorMorador = this.consumo / this.moradores;
            int outroConsumoPorMorador = outro.consumo / outro.moradores;
            return Integer.compare(thisConsumoPorMorador, outroConsumoPorMorador);
        }

        @Override
        public String toString() {
            return moradores + "-" + (consumo / moradores);
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        StringBuilder output = new StringBuilder();
        int cidade = 1;

        while (true) {
            int N = sc.nextInt();
            if (N == 0) break;

            List<Imovel> imoveis = new ArrayList<>();
            int totalMoradores = 0;
            int totalConsumo = 0;

            for (int i = 0; i < N; i++) {
                int moradores = sc.nextInt();
                int consumo = sc.nextInt();
                imoveis.add(new Imovel(moradores, consumo));
                totalMoradores += moradores;
                totalConsumo += consumo;
            }

            Collections.sort(imoveis);

            if (cidade > 1) {
                output.append("\n");
            }
            output.append("Cidade# ").append(cidade).append(":\n");

            for (int i = 0; i < imoveis.size(); i++) {
                if (i > 0) {
                    output.append(" ");
                }
                output.append(imoveis.get(i));
            }

            double consumoMedio = (double) totalConsumo / totalMoradores;
            output.append("\nConsumo medio: ")
                  .append(String.format(Locale.US, "%.2f", consumoMedio))
                  .append(" m3.");

            cidade++;
        }

        System.out.print(output);
    }
}
