 import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

 class HashTable {
        private List<Pokemon>[] table;
        private int size;
        private int reserveSize;
    
        @SuppressWarnings("unchecked")
        public HashTable(int size, int reserveSize) {
            this.size = size;
            this.reserveSize = reserveSize;
            table = new ArrayList[size + reserveSize];
            for (int i = 0; i < size + reserveSize; i++) {
                table[i] = new ArrayList<>();
            }
        }
    
        private int hashFunction(String name) {
            int asciiSum = 0;
            for (char c : name.toCharArray()) {
                asciiSum += c;
            }
            return asciiSum % size;
        }
    
        public void insert(Pokemon personagem) {
            int index = hashFunction(personagem.getName());
            if (table[index].size() < 1) {
                table[index].add(personagem);
            } else {
                for (int i = size; i < size + reserveSize; i++) {
                    if (table[i].size() < 1) {
                        table[i].add(personagem);
                        return;
                    }
                 }
                
            }
        }

            public String search(String name) {
            int index = hashFunction(name);
            if (!table[index].isEmpty() && table[index].get(0).getName().equals(name)) {
                return ("=> " + name+ ":" + " (Posicao: " + index + ") SIM");
            }
            for (int i = size; i < size + reserveSize; i++) {
                if (!table[i].isEmpty() && table[i].get(0).getName().equals(name)) {
                    return ("=> " + name+ ":" + " (Posicao: " + i + ") SIM");
                }
            }
            return ("=> " + name+":" + " NAO");
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
    
    class hashDireta{
        public static void main(String[] args) throws Exception {
            Scanner scanner = new Scanner(System.in);
    
            Pokemon entidade = new Pokemon();
            List<Pokemon> allCharacters = entidade.lerPokemons();

            HashTable hashTable = new HashTable(21, 9);
    
            String id;
            while (!(id = scanner.nextLine()).equals("FIM")) {
                for (Pokemon personagem : allCharacters) {
                    if (personagem.getId().equals(id)) {
                        hashTable.insert(personagem);
                    }
                }
            }
    
            String nome;
          while (!(nome = scanner.nextLine()).equals("FIM")) {
            System.out.println(hashTable.search(nome));
          }
    
            scanner.close();
        }
    }