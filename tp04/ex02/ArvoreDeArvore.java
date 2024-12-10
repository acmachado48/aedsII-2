import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

class ArvoreArvore {
    private NoMisto raiz;

    // Construtor vazio
    public ArvoreArvore() {
        this.raiz = null;
    }

    // Getters e Setters
    public NoMisto getRaiz() {
        return raiz;
    }

    public void setRaiz(NoMisto raiz) {
        this.raiz = raiz;
    }

    public void inserir(int valor) {
        raiz = inserirRec(raiz, valor);
    }

    private NoMisto inserirRec(NoMisto no, int valor) {
        if (no == null) {
            return new NoMisto(valor);
        }

        if (valor < no.getValor()) {
            no.setEsquerda(inserirRec(no.getEsquerda(), valor));
        } else if (valor > no.getValor()) {
            no.setDireita(inserirRec(no.getDireita(), valor));
        }

        return no;
    }

    public void inserirNaSubArvore(int valor, String str) {
        int chave = valor % 15;
        NoMisto no = buscarNo(raiz, chave);

        if (no != null) {
            no.getSubArvore().inserir(str);
        } else {
            System.out.println("Chave " + chave + " não encontrada na árvore.");
        }
    }

    private NoMisto buscarNo(NoMisto no, int chave) {
        if (no == null || no.getValor() == chave) {
            return no;
        }

        if (chave < no.getValor()) {
            return buscarNo(no.getEsquerda(), chave);
        } else {
            return buscarNo(no.getDireita(), chave);
        }
    }

    public void AcharPersonagem(String str) {
        System.out.println("=> " + str );
        System.out.print("raiz");
        if (AcharPersonagemRec(raiz, str)) {
            System.out.println(" SIM");
        } else {
            System.out.println(" NAO");
        }
    }

    private boolean AcharPersonagemRec(NoMisto no, String str) {
        boolean resp = false;
        if (no != null) {
          
            resp = no.getSubArvore().buscar(str);

            if (resp) {
                return true;
            }
            if (!resp) {
                System.out.print(" ESQ ");
                resp = AcharPersonagemRec(no.getEsquerda(), str);
            }
            if (!resp) {
                System.out.print(" DIR ");
                resp = AcharPersonagemRec(no.getDireita(), str);
            }
        }

        return resp;
    }

    public void imprimirSubArvores() {
        imprimirSubArvoresRec(raiz);
    }

       private void imprimirSubArvoresRec(NoMisto no) {
        if (no != null) {
            System.out.println("Subárvore para valor " + no.getValor() + ":");
            no.getSubArvore().imprimir(); // Chama método de impressão da ArvoreNo

            imprimirSubArvoresRec(no.getEsquerda());
            imprimirSubArvoresRec(no.getDireita());
        }

    }
}

class NoMisto {
    private int valor;
    private ArvoreNo subArvore;
    private NoMisto esquerda;
    private NoMisto direita;

    // Construtor vazio
    public NoMisto() {
        this.valor = 0;
        this.subArvore = new ArvoreNo();
        this.esquerda = null;
        this.direita = null;
    }

    // Construtor com valor
    public NoMisto(int valor) {
        this.valor = valor;
        this.subArvore = new ArvoreNo();
        this.esquerda = null;
        this.direita = null;
    }

    // Getters e Setters
    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public ArvoreNo getSubArvore() {
        return subArvore;
    }

     public void setSubArvore(ArvoreNo subArvore) {
        this.subArvore = subArvore;
    }

    public NoMisto getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(NoMisto esquerda) {
        this.esquerda = esquerda;
    }

    public NoMisto getDireita() {
        return direita;
    }

    public void setDireita(NoMisto direita) {
        this.direita = direita;
    }
}

class No{ 
    private String valor;
    No direita, esquerda;

public No() {
        this.valor = "";
        this.esquerda = null;
        this.direita = null;
    }

    // Construtor com valor
    public No(String valor) {
        this.valor = valor;
        this.esquerda = null;
        this.direita = null;
    }

    // Getters e Setters
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public No getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(No esquerda) {
        this.esquerda = esquerda;
    }

    public No getDireita() {
        return direita;
    }

    public void setDireita(No direita) {
        this.direita = direita;
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


 class ArvoreNo{
     private No raiz;

    // Construtor vazio
    public ArvoreNo() {
        this.raiz = null;
    }

    // Getters e Setters
    public No getRaiz() {
        return raiz;
    }

    public void setRaiz(No raiz) {
        this.raiz = raiz;
    }


    public void inserir(String valor) {
        raiz = inserirRec(raiz, valor);
    }

    private No inserirRec(No no, String valor) {
        if (no == null || no.getValor().equals("")) {
            return new No(valor);
        }

        if (valor.compareTo(no.getValor()) < 0) {
            no.setEsquerda(inserirRec(no.getEsquerda(), valor));
        } else if (valor.compareTo(no.getValor()) > 0) {
            no.setDireita(inserirRec(no.getDireita(), valor));
        }

        return no;
    }

    public boolean buscar(String valor) {
        if (raiz == null) {
            return false;
        }
        return buscarRec(raiz, valor);
    }

    private boolean buscarRec(No no, String valor) {
        if (no == null) {
            return false;
        }

        if (valor.compareTo(no.getValor()) == 0) {
        
            return true;
        } else if (valor.compareTo(no.getValor()) < 0) {
        
            System.out.print("esq ");
            return buscarRec(no.getEsquerda(), valor);
        } else {
            
            System.out.print("dir ");
            return buscarRec(no.getDireita(), valor);
        }
    }

    public void imprimir() {
        imprimirRec(raiz);
    }

    private void imprimirRec(No no) {
        if (no != null) {
            imprimirRec(no.getEsquerda());
            System.out.print(no.getValor() + " ");
            imprimirRec(no.getDireita());
        }
    }


}

public class ArvoreDeArvore{
    public static void main(String[] args) throws Exception {
    Scanner scanner = new Scanner(System.in);
    
    Pokemon entidade = new Pokemon();
    List<Pokemon> allCharacters = entidade.lerPokemons();

    String id;
    int[] ordem = { 7, 3, 11, 1, 5, 9, 13, 0, 2, 4, 6, 8, 10, 12, 14 };
    ArvoreArvore questao = new ArvoreArvore();
    for (int i = 0; i < 15; i++) {
        questao.inserir(ordem[i]);
    }

    while (!(id = scanner.nextLine()).equals("FIM")) {
        for (Pokemon personagem : allCharacters) {
            if (personagem.getId().equals(id)) {
                questao.inserirNaSubArvore(personagem.getCaptureRate(), personagem.getName());
            }
        }
    }

    String nome;
    while (!(nome = scanner.nextLine()).equals("FIM")) {
        //System.out.print(nome + " => ");
        questao.AcharPersonagem(nome);
    }

    scanner.close();
 }
} 