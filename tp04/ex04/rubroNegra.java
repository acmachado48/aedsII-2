import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


enum Cor {
    VERMELHO,
    PRETO
}

// Classe que define um nó da árvore Rubro-Negra
class No {
    Pokemon elemento;
    No esq;
    No dir;
    No pai;
    Cor cor;

    // Construtor para um nó com chave e cor
    public No(Pokemon elemento, Cor cor) {
        this.elemento = elemento;
        this.cor = cor;
        this.esq = null;
        this.dir = null;
        this.pai = null;
    }
}

// Classe que define a árvore Rubro-Negra
class ArvoreRubroNegra {
    private static No raiz;
    private static No sentinela; // Nó sentinela para os filhos nulos

    // Construtor da árvore Rubro-Negra
    public ArvoreRubroNegra() {
        sentinela = new No(null, Cor.PRETO);
        raiz = null;
    }

    // Método para inserir um nó na árvore Rubro-Negra
    public static void inserir(Pokemon elemento) {
        No novoNo = new No(elemento, Cor.VERMELHO);
        novoNo.esq = sentinela;
        novoNo.dir = sentinela;

        if (raiz == null) {
            raiz = novoNo;
            raiz.cor = Cor.PRETO; // A raiz sempre será preta
        } else {
            inserirRecursivo(raiz, novoNo);
            corrigirInsercao(novoNo);
        }
    }

    // Método recursivo para inserir um nó na árvore Rubro-Negra
    private static void inserirRecursivo(No raizSub, No novoNo) {
        if (novoNo.elemento.getName().compareTo(raizSub.elemento.getName()) < 0) {
            if (raizSub.esq == sentinela) {
                raizSub.esq = novoNo;
                novoNo.pai = raizSub;
            } else {
                inserirRecursivo(raizSub.esq, novoNo);
            }
        } else if (novoNo.elemento.getName().compareTo(raizSub.elemento.getName()) > 0) {
            if (raizSub.dir == sentinela) {
                raizSub.dir = novoNo;
                novoNo.pai = raizSub;
            } else {
                inserirRecursivo(raizSub.dir, novoNo);
            }
        }
    }

    // Método para corrigir a árvore após a inserção de um nó
    private static void corrigirInsercao(No novoNo) {
        No pai = null;
        No avo = null;

        while (novoNo != raiz && novoNo.cor == Cor.VERMELHO && novoNo.pai.cor == Cor.VERMELHO) {
            pai = novoNo.pai;
            avo = pai.pai;

            // Caso 1: O pai de novoNo é o filho esquerdo do avô
            if (pai == avo.esq) {
                No tio = avo.dir;

                // Caso 1A: O tio de novoNo é VERMELHO - Recoloração
                if (tio != null && tio.cor == Cor.VERMELHO) {
                    avo.cor = Cor.VERMELHO;
                    pai.cor = Cor.PRETO;
                    tio.cor = Cor.PRETO;
                    novoNo = avo; // Move para cima para verificar a violação
                } else {
                    // Caso 1B: O tio de novoNo é PRETO ou NIL
                    if (novoNo == pai.dir) {
                        rotacaoEsquerda(pai);
                        novoNo = pai;
                        pai = novoNo.pai;
                    }
                    rotacaoDireita(avo);
                    pai.cor = Cor.PRETO;
                    avo.cor = Cor.VERMELHO;
                }
            } else { // Caso 2: O pai de novoNo é o filho direito do avô
                No tio = avo.esq;

                // Caso 2A: O tio de novoNo é VERMELHO - Recoloração
                if (tio != null && tio.cor == Cor.VERMELHO) {
                    avo.cor = Cor.VERMELHO;
                    pai.cor = Cor.PRETO;
                    tio.cor = Cor.PRETO;
                    novoNo = avo; // Move para cima para verificar a violação
                } else {
                    // Caso 2B: O tio de novoNo é PRETO ou NIL
                    if (novoNo == pai.esq) {
                        rotacaoDireita(pai);
                        novoNo = pai;
                        pai = novoNo.pai;
                    }
                    rotacaoEsquerda(avo);
                    pai.cor = Cor.PRETO;
                    avo.cor = Cor.VERMELHO;
                }
            }
        }
        raiz.cor = Cor.PRETO; // Garante que a raiz seja preta
    }

    // Método para realizar rotação à esquerda
    private static void rotacaoEsquerda(No x) {
        No y = x.dir;
        x.dir = y.esq;
        if (y.esq != sentinela) {
            y.esq.pai = x;
        }
        y.pai = x.pai;
        if (x.pai == null) {
            raiz = y;
        } else if (x == x.pai.esq) {
            x.pai.esq = y;
        } else {
            x.pai.dir = y;
        }
        y.esq = x;
        x.pai = y;
    }

    // Método para realizar rotação à direita
    private static void rotacaoDireita(No x) {
        No y = x.esq;
        x.esq = y.dir;
        if (y.dir != sentinela) {
            y.dir.pai = x;
        }
        y.pai = x.pai;
        if (x.pai == null) {
            raiz = y;
        } else if (x == x.pai.dir) {
            x.pai.dir = y;
        } else {
            x.pai.esq = y;
        }
        y.dir = x;
        x.pai = y;
    }


   

    public static boolean pesquisar(String nome) {
        System.out.print("raiz ");
        return pesquisar(nome, raiz);
    }

    private static boolean pesquisar(String nome, No i) {
        if (i == null) {
            System.out.println("NAO");
            return false;
        } else if (nome.equals(i.elemento.getName())) {
            System.out.println("SIM");
            return true;
        } else if (nome.compareTo(i.elemento.getName()) < 0) {
            System.out.print("esq ");
            return pesquisar(nome, i.esq);
        } else {
            System.out.print("dir ");
            return pesquisar(nome, i.dir);
        }
    }

   
}

class Pokemon {
    private static final String FILE_NAME = "/tmp/pokemon.csv";
    private String id;
    private int generation;
    private String name;
    private String description;
    private ArrayList<String> types;
    private ArrayList<String> abilities;
    private double weight;
    private double height;
    private int captureRate;
    private boolean isLegendary;
    private Date captureDate;

    // Lista de Pokemons
    public List<Pokemon> listaDePokemons = new ArrayList<>();

    public Pokemon(String id, int generation, String name, String description, ArrayList<String> types, ArrayList<String> abilities, 
                   double weight, double height, int captureRate, boolean isLegendary, Date captureDate) {
        this.id = id;
        this.generation = generation;
        this.name = name;
        this.description = description;
        this.types = types;
        this.abilities = abilities;
        this.weight = weight;
        this.height = height;
        this.captureRate = captureRate;
        this.isLegendary = isLegendary;
        this.captureDate = captureDate;
    }

    public Pokemon() {
        types = new ArrayList<>();
        abilities = new ArrayList<>();
    }

    // Setter methods
    public void setId(String id) {
        this.id = id;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTypes(List<String> types) {
        this.types = (ArrayList<String>) types;
    }

    public void setAbilities(List<String> abilities) {
        this.abilities = (ArrayList<String>) abilities;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setCaptureRate(int captureRate) {
        this.captureRate = captureRate;
    }

    public void setLegendary(boolean legendary) {
        isLegendary = legendary;
    }

    public void setCaptureDate(Date captureDate) {
        this.captureDate = captureDate;
    }

    // getter methods
    public String getId() {
        return id;
    }

    public int getGeneration() {
        return generation;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public ArrayList<String> getAbilities() {
        return abilities;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public int getCaptureRate() {
        return captureRate;
    }

    public boolean isLegendary() {
        return isLegendary;
    }

    public Date getCaptureDate() {
        return captureDate;
    }

    // Métodos de leitura de arquivo e impressão

   public List<Pokemon> lerPokemons() {
        listaDePokemons.clear();
       SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try (BufferedReader ler = new BufferedReader(new FileReader(FILE_NAME))) {
            String ln;
            ler.readLine();  // Pula o cabeçalho

            while ((ln = ler.readLine()) != null) {
                String[] partes = ln.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                if (partes.length >= 12) { // Garante que todas as 12 colunas estão presentes
                    String id = partes[0].trim();
                    int generation = Integer.parseInt(partes[1].trim());
                    String name = partes[2].trim();
                    String description = partes[3].trim();

                    // Tratamento dos tipos que estão em colunas separadas
                    ArrayList<String> types = new ArrayList<>();
                    types.add(partes[4].trim()); // Primeiro tipo
                    if (!partes[5].trim().isEmpty()) {
                        types.add(partes[5].trim()); // Segundo tipo (se existir)
                    }

                    // Tratamento das habilidades (remover aspas e colchetes)
                    String abilitiesStr = partes[6].replaceAll("[\\[\\]\"]", "").trim();
                    ArrayList<String> abilities = new ArrayList<>();
                    for (String ability : abilitiesStr.split(",")) {
                        abilities.add(ability.trim());
                    }

                    // Peso e altura
                    double weight = partes[7].isEmpty() ? 0 : Double.parseDouble(partes[7].trim());
                    double height = partes[8].isEmpty() ? 0 : Double.parseDouble(partes[8].trim());
                    int captureRate = Integer.parseInt(partes[9].trim().replace("%", ""));
                    boolean isLegendary = Integer.parseInt(partes[10].trim()) == 1;

                    // Data de captura
                    Date captureDate = null;
                    try {
                        captureDate = dateFormat.parse(partes[11].trim());
                    } catch (ParseException e) {
                        System.out.println("Erro ao parsear a data: " + partes[11].trim());
                    }

                    // Criar objeto Pokémon e adicionar à lista
                    Pokemon pokemon = new Pokemon(id, generation, name, description, types, abilities, weight, height, captureRate, isLegendary, captureDate);
                    listaDePokemons.add(pokemon);
                } else {
                    System.out.println("Linha mal formatada ou incompleta: " + ln);
                }
            }
        } catch (IOException x) {
            x.printStackTrace();
        }
        return listaDePokemons;
    }

       public void imprimir() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = captureDate != null ? dateFormat.format(captureDate) : "N/A";

        System.out.print("[#" + id + " -> " + name + ": " + description + " - [");

        // Print tipos
        for (int i = 0; i < types.size(); i++) {
            System.out.print("'" + types.get(i) + "'");
            if (i < types.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("] - [");

        // Print habilidades
        for (int i = 0; i < abilities.size(); i++) {
            System.out.print(abilities.get(i));
            if (i < abilities.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("] - ");

        System.out.printf("%.1fkg - %.1fm - %d%% - %s - %d gen] - %s\n",
                weight, height, captureRate,
                isLegendary ? "true" : "false",
                generation, formattedDate);
    }
}




public class rubroNegra {
    public static void main(String[] args) throws Exception{

        Scanner scanner = new Scanner(System.in);
        
        Pokemon entidade = new Pokemon();
        List<Pokemon> allCharacters = entidade.lerPokemons();

        String id;

        while (!(id = scanner.nextLine()).equals("FIM")) {
            for (Pokemon personagem : allCharacters) {
                if (personagem.getId().equals(id)) {
                    ArvoreRubroNegra.inserir(personagem);
                }
            }
        }

        String nome;
        

        while (!(nome = scanner.nextLine()).equals("FIM")) {
            System.out.println(nome);
            if (!ArvoreRubroNegra.pesquisar(nome)) {
               
            }
           
        }


    }
}