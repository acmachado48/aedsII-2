#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <ctype.h>

typedef struct {
    char id[20];
    int generation;
    char name[100];
    char description[1000];
    char **types;
    int num_types;
    char **abilities;
    int num_abilities;
    double weight;
    double height;
    int captureRate;
    int isLegendary;
    struct tm captureDate;
} Pokemon;

typedef struct {
    Pokemon *stack;
    int top;
    int capacity;
} Stack;

char *trim(char *str) {
    while (isspace((unsigned char)*str))
        str++;
    if (*str == 0)
        return str;
    char *end = str + strlen(str) - 1;
    while (end > str && isspace((unsigned char)*end))
        end--;
    end[1] = '\0';
    return str;
}

int split_at_char(char *str, char delimiter, char **tokens, int max_tokens) {
    int count = 0;
    char *start = str;
    while (*str) {
        if (*str == delimiter) {
            *str = '\0';
            tokens[count++] = start;
            start = str + 1;
            if (count >= max_tokens)
                return count;
        }
        str++;
    }
    tokens[count++] = start;
    return count;
}

void lerDadosDoArquivo(Stack *stack) {
    const char *FILE_NAME = "/tmp/pokemon.csv";
    FILE *file = fopen(FILE_NAME, "r");
    if (!file) {
        perror("Erro ao abrir o arquivo");
        exit(1);
    }

    char line[2048];
    fgets(line, sizeof(line), file); // Skip header line

    stack->capacity = 1000; // Initial capacity
    stack->stack = (Pokemon *)malloc(stack->capacity * sizeof(Pokemon));
    stack->top = -1; // Initial top of the stack

    while (fgets(line, sizeof(line), file)) {
        line[strcspn(line, "\r\n")] = 0; // Remove newline character

        Pokemon pokemon;
        memset(&pokemon, 0, sizeof(Pokemon));

        char *blocos[10];
        int num_blocos = split_at_char(line, '"', blocos, 10);

        if (num_blocos < 3) {
            printf("Linha mal formatada ou incompleta: %s\n", line);
            continue;
        }

        char *atributo[20];
        int num_atributo = split_at_char(blocos[0], ',', atributo, 20);
        for (int i = 0; i < num_atributo; i++) {
            atributo[i] = trim(atributo[i]);
        }

        if (num_atributo < 6) {
            printf("Linha mal formatada ou incompleta: %s\n", line);
            continue;
        }

        strcpy(pokemon.id, atributo[0]);
        pokemon.generation = atoi(atributo[1]);
        strcpy(pokemon.name, atributo[2]);
        strcpy(pokemon.description, atributo[3]);

        pokemon.types = malloc(2 * sizeof(char *));
        pokemon.num_types = 0;
        if (strlen(trim(atributo[4])) > 0) {
            pokemon.types[pokemon.num_types++] = strdup(trim(atributo[4]));
        }
        if (num_atributo > 5 && strlen(trim(atributo[5])) > 0) {
            pokemon.types[pokemon.num_types++] = strdup(trim(atributo[5]));
        }

        char *abilities_str = blocos[1];
        char *abilities_cleaned = (char *)malloc(strlen(abilities_str) + 1);
        int idx = 0;
        for (int i = 0; abilities_str[i] != '\0'; i++) {
            if (abilities_str[i] != '[' && abilities_str[i] != ']') {
                abilities_cleaned[idx++] = abilities_str[i];
            }
        }
        abilities_cleaned[idx] = '\0';

        char *abilities_tokens[20];
        int num_abilities_tokens = split_at_char(abilities_cleaned, ',', abilities_tokens, 20);
        for (int i = 0; i < num_abilities_tokens; i++) {
            abilities_tokens[i] = trim(abilities_tokens[i]);
        }

        pokemon.abilities = malloc(num_abilities_tokens * sizeof(char *));
        pokemon.num_abilities = num_abilities_tokens;
        for (int i = 0; i < num_abilities_tokens; i++) {
            pokemon.abilities[i] = strdup(abilities_tokens[i]);
        }

        free(abilities_cleaned);

        char *resto = blocos[2];
        if (resto[0] == ',') {
            resto++;
        }

        char *atributo3[20];
        int num_atributo3 = split_at_char(resto, ',', atributo3, 20);
        for (int i = 0; i < num_atributo3; i++) {
            atributo3[i] = trim(atributo3[i]);
        }

        if (num_atributo3 > 0) {
            pokemon.weight = atof(atributo3[0]);
        }
        if (num_atributo3 > 1) {
            pokemon.height = atof(atributo3[1]);
        }
        if (num_atributo3 > 2) {
            pokemon.captureRate = atoi(atributo3[2]);
        }
        if (num_atributo3 > 3) {
            pokemon.isLegendary = atoi(atributo3[3]);
        }
        if (num_atributo3 > 4) {
            char *captureDateStr = atributo3[4];
            if (strptime(captureDateStr, "%d/%m/%Y", &pokemon.captureDate) == NULL) {
                printf("Erro ao parsear a data: %s\n", captureDateStr);
            }
        }

        if (stack->top + 1 >= stack->capacity) {
            stack->capacity *= 2;
            stack->stack = (Pokemon *)realloc(stack->stack, stack->capacity * sizeof(Pokemon));
        }

        stack->stack[++stack->top] = pokemon; // Push onto the stack
    }

    fclose(file);
}

void imprimirPokemon(Pokemon *pokemon, int index) {
    char dateStr[20];
    strftime(dateStr, sizeof(dateStr), "%d/%m/%Y", &pokemon->captureDate);

    printf("[%d] [#%s -> %s: %s - [", index, pokemon->id, pokemon->name, pokemon->description);
    for (int i = 0; i < pokemon->num_types; i++) {
        printf("'%s'", pokemon->types[i]);
        if (i < pokemon->num_types - 1) {
            printf(", ");
        }
    }
    printf("] - [");
    for (int i = 0; i < pokemon->num_abilities; i++) {
        printf("%s", pokemon->abilities[i]);
        if (i < pokemon->num_abilities - 1) {
            printf(", ");
        }
    }
    printf("] - %.1fkg - %.1fm - %d%% - %s - %d gen] - %s\n",
           pokemon->weight, pokemon->height, pokemon->captureRate,
           pokemon->isLegendary ? "true" : "false",
           pokemon->generation, dateStr);
}

// Função para inicializar uma pilha
void initStack(Stack *stack, int capacity) {
    stack->capacity = capacity;
    stack->stack = (Pokemon *)malloc(stack->capacity * sizeof(Pokemon));
    stack->top = -1;
}

// Função para empurrar no topo da pilha (inserir no final)
void push(Stack *stack, Pokemon *pokemon) {
    if (stack->top + 1 >= stack->capacity) {
        stack->capacity *= 2;
        stack->stack = (Pokemon *)realloc(stack->stack, stack->capacity * sizeof(Pokemon));
    }
    stack->stack[++stack->top] = *pokemon; // Empurra no topo da pilha
}

// Função para inserir no início (desloca os elementos para a direita)
void insertAtBeginning(Stack *stack, Pokemon *pokemon) {
    if (stack->top + 1 >= stack->capacity) {
        stack->capacity *= 2;
        stack->stack = (Pokemon *)realloc(stack->stack, stack->capacity * sizeof(Pokemon));
    }
    // Desloca os elementos
    for (int i = stack->top; i >= 0; i--) {
        stack->stack[i + 1] = stack->stack[i];
    }
    stack->stack[0] = *pokemon; // Insere no início
    stack->top++;
}

// Função para remover do topo da pilha (remover do final)
Pokemon pop(Stack *stack) {
    if (stack->top == -1) {
        printf("Erro ao remover: pilha vazia!\n");
        exit(1);
    }
    return stack->stack[stack->top--]; // Remove do topo da pilha
}

// Função para remover do início (desloca os elementos para a esquerda)
Pokemon removeAtBeginning(Stack *stack) {
    if (stack->top == -1) {
        printf("Erro ao remover: pilha vazia!\n");
        exit(1);
    }
    Pokemon removed = stack->stack[0];
    // Desloca os elementos para a esquerda
    for (int i = 0; i < stack->top; i++) {
        stack->stack[i] = stack->stack[i + 1];
    }
    stack->top--;
    return removed;
}

// Função para remover do final (basicamente pop)
Pokemon removeAtEnd(Stack *stack) {
    return pop(stack);
}

// Função para inserir em uma posição específica
void insertAtPosition(Stack *stack, Pokemon *pokemon, int position) {
    if (position < 0 || position > stack->top + 1) {
        printf("Erro: posição inválida!\n");
        return;
    }

    if (stack->top + 1 >= stack->capacity) {
        stack->capacity *= 2;
        stack->stack = (Pokemon *)realloc(stack->stack, stack->capacity * sizeof(Pokemon));
    }

    // Desloca os elementos a partir da posição
    for (int i = stack->top; i >= position; i--) {
        stack->stack[i + 1] = stack->stack[i];
    }
    stack->stack[position] = *pokemon; // Insere na posição especificada
    stack->top++;
}

// Função para remover de uma posição específica
Pokemon removeAtPosition(Stack *stack, int position) {
    if (position < 0 || position > stack->top) {
        printf("Erro: posição inválida!\n");
        exit(1);
    }

    Pokemon removed = stack->stack[position];

    // Desloca os elementos para a esquerda
    for (int i = position; i < stack->top; i++) {
        stack->stack[i] = stack->stack[i + 1];
    }
    stack->top--;
    return removed;
}

// Função para verificar se o Pokémon existe na pilha
int encontrarPokemonPorId(Stack *stack, char *id, Pokemon *resultado) {
    for (int i = 0; i <= stack->top; i++) {
        if (strcmp(stack->stack[i].id, id) == 0) {
            *resultado = stack->stack[i];
            return 1;
        }
    }
    return 0;
}

// Função para imprimir todos os Pokémons na pilha
void imprimirLista(Stack *stack) {
    for (int i = 0; i <= stack->top; i++) {
        imprimirPokemon(&stack->stack[i], i); // Chama a função de impressão para cada Pokémon
    }
}


int main() {
    Stack pokedexCompleto;
    lerDadosDoArquivo(&pokedexCompleto);

    Stack pokedexUsuario;
    pokedexUsuario.stack = malloc(100 * sizeof(Pokemon)); // Capacidade inicial
    pokedexUsuario.top = -1; // Inicializa a pilha vazia
    pokedexUsuario.capacity = 100;

    char entrada[100];

    // Leitura de IDs de Pokémon do usuário
    while (fgets(entrada, sizeof(entrada), stdin)) {
        entrada[strcspn(entrada, "\r\n")] = 0;

        if (strcmp(entrada, "FIM") == 0) {
            break;
        }

        Pokemon pokemonEncontrado;
        if (encontrarPokemonPorId(&pokedexCompleto, entrada, &pokemonEncontrado)) {
            push(&pokedexUsuario, &pokemonEncontrado);
        } else {
            printf("Pokemon com ID %s não encontrado\n", entrada);
        }
    }

    int numComandos;
    scanf("%d", &numComandos);
    getchar();

    while (numComandos > 0) {
        fgets(entrada, sizeof(entrada), stdin);
        entrada[strcspn(entrada, "\r\n")] = 0;

        char *partes[3];
        int num_partes = split_at_char(entrada, ' ', partes, 3);
        char *comando = partes[0];
        int posicao = (num_partes > 1) ? atoi(partes[1]) : -1;

        if (strcmp(comando, "II") == 0) { // Inserir no início
            Pokemon pokemon;
            if (encontrarPokemonPorId(&pokedexCompleto, partes[1], &pokemon)) {
                insertAtBeginning(&pokedexUsuario, &pokemon);
            }
        } else if (strcmp(comando, "IF") == 0) { // Inserir no final
            Pokemon pokemon;
            if (encontrarPokemonPorId(&pokedexCompleto, partes[1], &pokemon)) {
                push(&pokedexUsuario, &pokemon);
            }
        } else if (strcmp(comando, "I*") == 0 && posicao >= 0) { // Inserir na posição específica
            Pokemon pokemon;
            if (encontrarPokemonPorId(&pokedexCompleto, partes[2], &pokemon)) {
                insertAtPosition(&pokedexUsuario, &pokemon, posicao);
            }
        } else if (strcmp(comando, "RI") == 0) { // Remover do início
            Pokemon removido = removeAtBeginning(&pokedexUsuario);
            printf("(R) %s\n", removido.name);
        } else if (strcmp(comando, "RF") == 0) { // Remover do final
            Pokemon removido = removeAtEnd(&pokedexUsuario);
            printf("(R) %s\n", removido.name);
        } else if (strcmp(comando, "R*") == 0 && posicao >= 0) { // Remover da posição específica
            Pokemon removido = removeAtPosition(&pokedexUsuario, posicao);
            printf("(R) %s\n", removido.name);
        } else {
            printf("Comando inválido: %s\n", comando);
        }
        numComandos--;
    }


imprimirLista(&pokedexUsuario);


    // Liberar memória
    free(pokedexCompleto.stack);
    free(pokedexUsuario.stack);

    return 0;
}
