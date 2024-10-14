import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class Pokedex {
    
    private static final String FILE_NAME = "/tmp/pokemon.csv";
    public List<Pokemon> listaDePokemons = new ArrayList<>();

    public void lerDadosDoArquivo() {
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
    }
}


class Pokemon {
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


public class count {

     public static void countingSort(List<Pokemon> pokemons, int maxCaptureRate) {
        int n = pokemons.size();

        // Array para armazenar a contagem de cada captureRate
        int[] count = new int[maxCaptureRate + 1];

        // Array temporário para armazenar o resultado da ordenação
        List<Pokemon> sortedPokemons = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            sortedPokemons.add(null);  // Preenchemos a lista com valores nulos
        }

        // Inicializa o array de contagem com 0
        for (int i = 0; i <= maxCaptureRate; i++) {
            count[i] = 0;
        }

        // Conta a ocorrência de cada captureRate
        for (Pokemon pokemon : pokemons) {
            count[pokemon.getCaptureRate()]++;
        }

        // Modifica o array de contagem para armazenar a posição dos elementos na saída
        for (int i = 1; i <= maxCaptureRate; i++) {
            count[i] += count[i - 1];
        }

        // Constrói o array de saída
        for (int i = n - 1; i >= 0; i--) {
            Pokemon pokemon = pokemons.get(i);
            int captureRate = pokemon.getCaptureRate();

            // Coloca o Pokémon na posição correta
            sortedPokemons.set(count[captureRate] - 1, pokemon);
            count[captureRate]--;
        }

        // Para Pokémon com o mesmo captureRate, ordena pelo nome
        for (int i = 0; i < n;) {
            int j = i + 1;
            // Encontra o intervalo de Pokémon com o mesmo captureRate
            while (j < n && sortedPokemons.get(i).getCaptureRate() == sortedPokemons.get(j).getCaptureRate()) {
                j++;
            }

            // Se há mais de um Pokémon com o mesmo captureRate, ordena pelo nome
            if (j - i > 1) {
                sortedPokemons.subList(i, j).sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
            }

            // Avança o índice para o próximo grupo
            i = j;
        }

        // Copia os elementos ordenados de volta para a lista original
        for (int i = 0; i < n; i++) {
            pokemons.set(i, sortedPokemons.get(i));
        }
    }

    public static void main(String[] args) throws IOException {
        Pokedex pokedex = new Pokedex();
        pokedex.lerDadosDoArquivo();

        List<Pokemon> selecionados = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        String id;
        while (!(id = sc.nextLine()).equals("FIM")) {
            Pokemon procurado = procurarPokemonId(pokedex.listaDePokemons, id);
            if (procurado != null) {
                selecionados.add(procurado);
            }
        }

        // Define o valor máximo de captureRate
        int maxCaptureRate = 255;

        // Aplica o Counting Sort baseado no captureRate, com desempate pelo nome
        countingSort(selecionados, maxCaptureRate);

        // Imprime os Pokémon ordenados
        for (Pokemon pokemon : selecionados) {
            pokemon.imprimir();
        }

        sc.close();
    }

    // Método procurarPokemonId para localizar o Pokémon pelo ID
    public static Pokemon procurarPokemonId(List<Pokemon> todosPokemons, String id) {
        for (Pokemon pokemon : todosPokemons) {
            if (pokemon.getId().equals(id)) {
                return pokemon;
            }
        }
        return null;
    }
}