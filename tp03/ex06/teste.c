

import java.io.*;
import java.util.*;

class Personagem {
    private String id;
    private String name;
    private List<String> alternateNames;
    private String house;
    private String ancestry;
    private String species;
    private String patronus;
    private boolean hogwartsStaff;
    private String hogwartsStudent;
    private String actorName;
    private boolean alive;
    private String dateOfBirth;
    private int yearOfBirth;
    private String eyeColour;
    private String gender;
    private String hairColour;
    private boolean wizard;

    public Personagem() {
        this.id = "";
        this.name = "";
        this.alternateNames = new ArrayList<>();
        this.house = "";
        this.ancestry = "";
        this.species = "";
        this.patronus = "";
        this.hogwartsStaff = false;
        this.hogwartsStudent = "";
        this.actorName = "";
        this.alive = false;
        this.dateOfBirth = "";
        this.yearOfBirth = Integer.MIN_VALUE;
        this.eyeColour = "";
        this.gender = "";
        this.hairColour = "";
        this.wizard = false;
    }

    // Construtor com todos os campos
    public Personagem(String id, String name, List<String> alternateNames, String house, String ancestry,
         String species, String patronus, String hogwartsStaff, String hogwartsStudent, String actorName,
          boolean alive, String dateOfBirth, int yearOfBirth, String eyeColour, String gender, String hairColour, boolean wizard) {
                        this.id = id;
                        this.name = name;
                        this.alternateNames = alternateNames;
                        this.house = house;
                        this.ancestry = ancestry;
                        this.species = species;
                        this.patronus = patronus;

                        this.hogwartsStaff = true;

                        this.hogwartsStudent = hogwartsStudent;
                        this.actorName = actorName;
                        this.alive = true;
                        this.dateOfBirth = dateOfBirth;
                        this.yearOfBirth = yearOfBirth;
                        this.eyeColour = eyeColour;
                        this.gender = gender;
                        this.hairColour = hairColour;
                        this.wizard = true;
    }


    //sets e gets 

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAlternateNames() {
        return alternateNames;
    }

    public void setAlternateNames(List<String> alternateNames) {
        
        this.alternateNames = alternateNames;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getAncestry(){
        return ancestry;
    }

    public void setAncestry(String ancestry){
        this.ancestry = ancestry;
    }

    public String getSpecies(){
        return species;
    }

    public void setSpecies(String species){
        this.species = species;
    }

    public String getPatronus(){
        return patronus;
    }

    public void setPatronus(String patronus){
        this.patronus = patronus;
    }

    public void setHogwartsStudent(String hogwartsStudent) {
        this.hogwartsStudent = hogwartsStudent;
    }

    public String isHogwartsStudent() {
        return hogwartsStudent;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getActorName() {
        return actorName;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }


    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth){
        this.yearOfBirth = yearOfBirth;
    }

    public int getYearOfBirth(){
        return yearOfBirth;
    }

    public void setEyeColour(String eyeColour) {
        this.eyeColour = eyeColour;
    }

    public String getEyeColour() {
        return eyeColour;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setHairColour(String hairColour) {
        this.hairColour = hairColour;
    }

    public String getHairColour() {
        return hairColour;
    }

    public void setWizard(boolean wizard) {
        this.wizard = wizard;
    }

    public boolean isWizard() {
        return wizard;
    }

    public Personagem clonarPersonagem(Personagem original) {
        Personagem clone = new Personagem();
        clone.id = original.id;
        clone.name = original.name;
        clone.alternateNames = new ArrayList<>(original.alternateNames);
        clone.house = original.house;
        clone.ancestry = original.ancestry;
        clone.species = original.species;
        clone.patronus = original.patronus;
        clone.hogwartsStaff = original.hogwartsStaff;
        clone.hogwartsStudent = original.hogwartsStudent;
        clone.actorName = original.actorName;
        clone.alive = original.alive;
        clone.dateOfBirth = original.dateOfBirth;
        clone.yearOfBirth = original.yearOfBirth;
        clone.eyeColour = original.eyeColour;
        clone.gender = original.gender;
        clone.hairColour = original.hairColour;
        clone.wizard = original.wizard;
        return clone;
    }


    public List<Personagem> lerPersonagens(String caminho) throws IOException {

        List<Personagem> allCharacters = new ArrayList<>();

        try (BufferedReader leitor = new BufferedReader(new FileReader(caminho))) {
            String linha;

            String[] campos;
            Personagem newCharacter = new Personagem();
    
            leitor.readLine();

            while ((linha = leitor.readLine()) != null) {
                campos = linha.split(";");
                campos[2] = campos[2].replace("[","")
                .replace("]","")
                .replace("'", ""); 
               
                if(campos[7].equals("VERDADEIRO") ){
                    campos[7] = "true";
                }else campos[7]= "false";

                if(campos[8].equals("VERDADEIRO") ){
                    campos[8] = "true";
                }else campos[8]= "false";



                newCharacter = new Personagem(
                        campos[0], campos[1], Arrays.asList(campos[2].split(",")), campos[3], campos[4],
                        campos[5], campos[6], campos[7], campos[8], campos[9],
                        Boolean.parseBoolean(campos[10]),campos[12],
                        Integer.parseInt(campos[13]), campos[14], campos[15], campos[16], Boolean.parseBoolean(campos[17]));

                allCharacters.add(newCharacter);
            }
        }

        return allCharacters;
    }
    
    

    public void imprimirPersonagem(int index) {
        String alternateNamesFormatted = alternateNames.toString()
                .replace("[", "{")
                .replace("]", "}")
                .replaceAll("\\s+", " ");
        
        System.out.println("[" + index + " ## " + getId() + " ## " + getName() + " ## " +
                alternateNamesFormatted + " ## " + getHouse() + " ## " +
                getAncestry() + " ## " + getSpecies() + " ## " +
                getPatronus() + " ## " + isHogwartsStudent() + " ## " +
                !isAlive() + " ## " + getActorName() + " ## " +
                isWizard() + " ## " + getDateOfBirth() + " ## " + getYearOfBirth() + " ## " +
                getEyeColour() + " ## " + getGender() + " ## " +
                getHairColour() + " ## " + isWizard() + "]");
    }
    
    }

    class Node {
        public Personagem data;
        public Node next;
    
        public Node(Personagem data) {
            this.data = data;
            this.next = null;
        }
    }
    
    class ListaPersonagens {
        private Node head;
        private int size;
    
        public ListaPersonagens() {
            head = null;
            size = 0;
        }
    
        // Método para adicionar um personagem no final da lista
        public void inserirFim(Personagem personagem) {
            Node newNode = new Node(personagem);
            if (head == null) {
                head = newNode;
            } else {
                Node temp = head;
                while (temp.next != null) {
                    temp = temp.next;
                }
                temp.next = newNode;
            }
            size++;
        }
    
        // Método para adicionar um personagem no início da lista
        public void inserirInicio(Personagem personagem) {
            Node newNode = new Node(personagem);
            newNode.next = head;
            head = newNode;
            size++;
        }
    
        // Método para adicionar um personagem em uma posição específica da lista
        public void inserir(Personagem personagem, int posicao) {
            if (posicao < 0 || posicao > size) {
                throw new IndexOutOfBoundsException("Posição inválida");
            }
            if (posicao == 0) {
                inserirInicio(personagem);
                return;
            }
            Node newNode = new Node(personagem);
            Node temp = head;
            for (int i = 0; i < posicao - 1; i++) {
                temp = temp.next;
            }
            newNode.next = temp.next;
            temp.next = newNode;
            size++;
        }
    
        // Método para remover um personagem do início da lista
        public Personagem removerInicio() {
            if (head == null) {
                throw new NoSuchElementException("Lista vazia");
            }
            Personagem removido = head.data;
            head = head.next;
            size--;
            return removido;
        }
    
        // Método para remover um personagem de uma posição específica da lista
        public Personagem remover(int posicao) {
            if (posicao < 0 || posicao >= size) {
                throw new IndexOutOfBoundsException("Posição inválida");
            }
            if (posicao == 0) {
                return removerInicio();
            }
            Node temp = head;
            for (int i = 0; i < posicao - 1; i++) {
                temp = temp.next;
            }
            Personagem removido = temp.next.data;
            temp.next = temp.next.next;
            size--;
            return removido;
        }
    
        // Método para remover um personagem do final da lista
        public Personagem removerFim() {
            if (head == null) {
                throw new NoSuchElementException("Lista vazia");
            }
            if (head.next == null) {
                return removerInicio();
            }
            Node temp = head;
            while (temp.next.next != null) {
                temp = temp.next;
            }
            Personagem removido = temp.next.data;
            temp.next = null;
            size--;
            return removido;
        }

        public boolean isEmpty() {
            return size == 0;
        } 
    
        // Método para imprimir a lista de personagens
        public void printLista() {
            int index = 0;
            Node temp = head;
            while (temp != null) {
                temp.data.imprimirPersonagem(index);
                temp = temp.next;
                index++;
            }
        }
    }
public class questao5 {
    public static void main(String[] args) throws IOException {
        ListaPersonagens listaPersonagens = new ListaPersonagens();
        Scanner scanner = new Scanner(System.in);

        String filePath = "/tmp/characters.csv";
        Personagem entidade = new Personagem();
        List<Personagem> allCharacters = entidade.lerPersonagens(filePath);

        String id;

        while (!(id = scanner.nextLine()).equals("FIM")) {
            for (Personagem personagem : allCharacters) {
                if (personagem.getId().equals(id)) {
                    listaPersonagens.inserirFim(personagem);
                }
            }
        }

        int qtd = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < qtd; i++) {
            String comando = scanner.nextLine();
            String[] partes = comando.split(" ");

            switch (partes[0]) {
                case "II":
                    // Inserir no início
                    String idII = partes[1];
                    for (Personagem personagem : allCharacters) {
                        if (personagem.getId().equals(idII)) {
                            listaPersonagens.inserirInicio(personagem);
                            break;
                        }
                    }
                    break;
                case "I*":
                    // Inserir em qualquer posição
                    int posicaoI = Integer.parseInt(partes[1]);
                    String idI = partes[2];
                    for (Personagem personagem : allCharacters) {
                        if (personagem.getId().equals(idI)) {
                            listaPersonagens.inserir(personagem, posicaoI);
                            break;
                        }
                    }
                    break;
                case "IF":
                    // Inserir no fim
                    String idIF = partes[1];
                    for (Personagem personagem : allCharacters) {
                        if (personagem.getId().equals(idIF)) {
                            listaPersonagens.inserirFim(personagem);
                            break;
                        }
                    }
                    break;
                case "RI":
                    // Remover no início
                    if (!listaPersonagens.isEmpty()) {
                        Personagem removidoRI = listaPersonagens.removerInicio();
                        System.out.println("(R) " + removidoRI.getName());
                    } else {
                        System.out.println("A lista está vazia.");
                    }
                    break;
                case "R*":
                    // Remover em qualquer posição
                    int posicaoR = Integer.parseInt(partes[1]);
                    try {
                        Personagem removidoR = listaPersonagens.remover(posicaoR);
                        System.out.println("(R) " + removidoR.getName());
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Índice inválido: " + posicaoR);
                    }
                    break;
                case "RF":
                    // Remover no fim
                    if (!listaPersonagens.isEmpty()) {
                        Personagem removidoRF = listaPersonagens.removerFim();
                        System.out.println("(R) " + removidoRF.getName());
                    } else {
                        System.out.println("A lista está vazia.");
                    }
                    break;
                default:
                    System.out.println("Comando inválido");
                    break;
            }
        }

        // Exibir a lista final
        listaPersonagens.printLista();

        scanner.close();
    }
}
    