#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <ctype.h>

typedef struct
{
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

typedef struct
{
    Pokemon *listaDePokemons;
    int numPokemons;
} Pokedex;

char *trim(char *str)
{
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

int split_at_char(char *str, char delimiter, char **tokens, int max_tokens)
{
    int count = 0;
    char *start = str;
    while (*str)
    {
        if (*str == delimiter)
        {
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

void lerDadosDoArquivo(Pokedex *pokedex, char **idsSolicitados, int numIdsSolicitados)
{
    const char *FILE_NAME = "/tmp/pokemon.csv";
    FILE *file = fopen(FILE_NAME, "r");
    if (!file)
    {
        perror("Erro ao abrir o arquivo");
        exit(1);
    }

    char line[2048];
    char *result;

    // Skip header line
    result = fgets(line, sizeof(line), file);

    int capacity = 1000;
    pokedex->listaDePokemons = (Pokemon *)malloc(capacity * sizeof(Pokemon));
    pokedex->numPokemons = 0;

    while (fgets(line, sizeof(line), file))
    {
        line[strcspn(line, "\r\n")] = 0;

        Pokemon pokemon;
        memset(&pokemon, 0, sizeof(Pokemon));

        char *blocos[10];
        int num_blocos = split_at_char(line, '"', blocos, 10);

        if (num_blocos < 3)
        {
            printf("Linha mal formatada ou incompleta: %s\n", line);
            continue;
        }

        char *atributo[20];
        int quant_atributo = split_at_char(blocos[0], ',', atributo, 20);
        for (int i = 0; i < quant_atributo; i++)
        {
            atributo[i] = trim(atributo[i]);
        }

        if (quant_atributo < 6)
        {
            printf("Linha mal formatada ou incompleta: %s\n", line);
            continue;
        }

        char *id = atributo[0];
        strcpy(pokemon.id, id);

        int found = 0;
        for (int i = 0; i < numIdsSolicitados; i++)
        {
            if (strcmp(id, idsSolicitados[i]) == 0)
            {
                found = 1;
                break;
            }
        }
        if (!found)
            continue;

        pokemon.generation = atoi(atributo[1]);
        strcpy(pokemon.name, atributo[2]);
        strcpy(pokemon.description, atributo[3]);

        pokemon.types = malloc(2 * sizeof(char *));
        pokemon.num_types = 0;
        if (strlen(trim(atributo[4])) > 0)
        {
            pokemon.types[pokemon.num_types++] = strdup(trim(atributo[4]));
        }
        if (quant_atributo > 5 && strlen(trim(atributo[5])) > 0)
        {
            pokemon.types[pokemon.num_types++] = strdup(trim(atributo[5]));
        }

        char *abilities_str = blocos[1];
        char *abilities_cleaned = (char *)malloc(strlen(abilities_str) + 1);
        int idx = 0;
        for (int i = 0; abilities_str[i] != '\0'; i++)
        {
            if (abilities_str[i] != '[' && abilities_str[i] != ']')
            {
                abilities_cleaned[idx++] = abilities_str[i];
            }
        }
        abilities_cleaned[idx] = '\0';

        char *abilities_tokens[20];
        int num_abilities_tokens = split_at_char(abilities_cleaned, ',', abilities_tokens, 20);
        for (int i = 0; i < num_abilities_tokens; i++)
        {
            abilities_tokens[i] = trim(abilities_tokens[i]);
        }

        pokemon.abilities = malloc(num_abilities_tokens * sizeof(char *));
        pokemon.num_abilities = num_abilities_tokens;
        for (int i = 0; i < num_abilities_tokens; i++)
        {
            pokemon.abilities[i] = strdup(abilities_tokens[i]);
        }

        free(abilities_cleaned);

        char *resto = blocos[2];
        if (resto[0] == ',')
            resto++;
        char *atributo3[20];
        int quant_atributo3 = split_at_char(resto, ',', atributo3, 20);
        for (int i = 0; i < quant_atributo3; i++)
        {
            atributo3[i] = trim(atributo3[i]);
        }

        if (quant_atributo3 > 0)
        {
            pokemon.weight = atof(atributo3[0]);
        }
        if (quant_atributo3 > 1)
        {
            pokemon.height = atof(atributo3[1]);
        }
        if (quant_atributo3 > 2)
        {
            pokemon.captureRate = atoi(atributo3[2]);
        }
        if (quant_atributo3 > 3)
        {
            pokemon.isLegendary = atoi(atributo3[3]);
        }
        if (quant_atributo3 > 4)
        {
            char *captureDateStr = atributo3[4];
            if (strptime(captureDateStr, "%d/%m/%Y", &pokemon.captureDate) == NULL)
            {
                printf("Erro ao parsear a data: %s\n", captureDateStr);
            }
        }

        if (pokedex->numPokemons >= capacity)
        {
            capacity *= 2;
            pokedex->listaDePokemons = (Pokemon *)realloc(pokedex->listaDePokemons, capacity * sizeof(Pokemon));
        }
        pokedex->listaDePokemons[pokedex->numPokemons++] = pokemon;
    }

    fclose(file);
}

void imprimirPokemon(Pokemon *pokemon)
{
    char dateStr[20];
    strftime(dateStr, sizeof(dateStr), "%d/%m/%Y", &pokemon->captureDate);
    printf("[#%s -> %s: %s - [", pokemon->id, pokemon->name, pokemon->description);

    for (int i = 0; i < pokemon->num_types; i++)
    {
        printf("'%s'", pokemon->types[i]);
        if (i < pokemon->num_types - 1)
        {
            printf(", ");
        }
    }
    printf("] - [");
    for (int i = 0; i < pokemon->num_abilities; i++)
    {
        printf("%s", pokemon->abilities[i]); // Removendo as aspas simples
        if (i < pokemon->num_abilities - 1)
        {
            printf(", ");
        }
    }
    printf("] - ");
    printf("%.1fkg - %.1fm - %d%% - %s - %d gen] - %s\n",
       pokemon->weight, pokemon->height, pokemon->captureRate,
       pokemon->isLegendary ? "true" : "false",
       pokemon->generation, dateStr);

}

void bubbleSort(Pokemon *lista, int numPokemons)
{
    for (int i = 0; i < numPokemons - 1; i++)
    {
        for (int j = 0; j < numPokemons - i - 1; j++)
        {
            if (atoi(lista[j].id) > atoi(lista[j + 1].id) ||
                (atoi(lista[j].id) == atoi(lista[j + 1].id) && strcmp(lista[j].name, lista[j + 1].name) > 0))
            {
                Pokemon temp = lista[j];
                lista[j] = lista[j + 1];
                lista[j + 1] = temp;
            }
        }
    }
}

int partition(Pokemon *lista, int low, int high)
{
    Pokemon pivot = lista[high];
    int i = low - 1;

    for (int j = low; j < high; j++)
    {
    
        if (lista[j].generation < pivot.generation ||
            (lista[j].generation == pivot.generation && strcmp(lista[j].name, pivot.name) < 0))
        {
            i++;
            Pokemon temp = lista[i];
            lista[i] = lista[j];
            lista[j] = temp;

        }
    }

    Pokemon temp = lista[i + 1];
    lista[i + 1] = lista[high];
    lista[high] = temp;

    return i + 1;
}

void ordenarPokemons(Pokedex *pokedex)
{

    bubbleSort(pokedex->listaDePokemons, pokedex->numPokemons);

   
}

void liberarMemoria(Pokedex *pokedex)
{
    for (int i = 0; i < pokedex->numPokemons; i++)
    {
        free(pokedex->listaDePokemons[i].types);
        for (int j = 0; j < pokedex->listaDePokemons[i].num_abilities; j++)
        {
            free(pokedex->listaDePokemons[i].abilities[j]);
        }
        free(pokedex->listaDePokemons[i].abilities);
    }
    free(pokedex->listaDePokemons);
}

int main()
{
    Pokedex pokedex;
    char *idsSolicitados[100];
    int numIdsSolicitados = 0;

    char entrada[20];
    while (fgets(entrada, sizeof(entrada), stdin) != NULL)
    {
        entrada[strcspn(entrada, "\r\n")] = 0;
        if (strlen(entrada) == 0)
            break;
        idsSolicitados[numIdsSolicitados++] = strdup(entrada);
    }

    lerDadosDoArquivo(&pokedex, idsSolicitados, numIdsSolicitados);
    ordenarPokemons(&pokedex);

    for (int i = 0; i < pokedex.numPokemons; i++)
    {
        imprimirPokemon(&pokedex.listaDePokemons[i]);
    }

    liberarMemoria(&pokedex);
    for (int i = 0; i < numIdsSolicitados; i++)
    {
        free(idsSolicitados[i]);
    }

   
    return 0;
}