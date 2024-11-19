public class Matriz {
    CelulaMat inicio;

    // Construtor da classe Matriz
    public Matriz() {
        inicio = null;
    }

    // Método para percorrer a matriz e imprimir os elementos
    public void percorrerMatriz() {
        CelulaMat tmp = inicio;

        while (tmp != null) { // Percorre horizontalmente
            for (CelulaMat mat = tmp; mat != null; mat = mat.inf) { // Percorre verticalmente
                for (Celula i = mat.primeiro; i != null; i = i.prox) { // Percorre a lista interna
                    System.out.print(i.numero + " ");
                }
                System.out.println(); // Nova linha após cada CelulaMat
            }
            tmp = tmp.prox;
        }
    }

    // Método para adicionar elementos na matriz (apenas para fins de teste)
    public void adicionarElemento(int numero) {
        if (inicio == null) {
            inicio = new CelulaMat();
            inicio.primeiro = new Celula(numero);
        } else {
            CelulaMat tmp = inicio;
            while (tmp.prox != null) {
                tmp = tmp.prox;
            }
            tmp.prox = new CelulaMat();
            tmp.prox.primeiro = new Celula(numero);
        }
    }

    // Classe interna CelulaMat
    class CelulaMat {
        CelulaMat prox, ant, sup, inf;
        Celula primeiro, ultimo;

        // Construtor da classe CelulaMat
        public CelulaMat() {
            prox = ant = sup = inf = null;
            primeiro = ultimo = null;
        }
    }

    // Classe interna Celula
    class Celula {
        int numero;
        Celula prox;

        // Construtor da classe Celula
        public Celula(int numero) {
            this.numero = numero;
            this.prox = null;
        }
    }

    // Método principal para testes
    public static void main(String[] args) {
        Matriz matriz = new Matriz();
        
        // Adicionando elementos na matriz
        matriz.adicionarElemento(1);
        matriz.adicionarElemento(2);
        matriz.adicionarElemento(3);
        matriz.adicionarElemento(7);
        matriz.adicionarElemento(9);
        matriz.adicionarElemento(5);

        // Percorrendo a matriz e imprimindo os elementos
        matriz.percorrerMatriz();
    }
}
