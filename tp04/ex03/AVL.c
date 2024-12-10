#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define FILE_NAME "/tmp/pokemon.csv"
#define MAX_NAME_SIZE 100
#define MAX_DESC_SIZE 500
#define MAX_TYPE_SIZE 50
#define MAX_ABIL_SIZE 100

typedef struct Date {
    int day, month, year;
} Date;

typedef struct Pokemon {
    char id[MAX_NAME_SIZE];
    int generation;
    char name[MAX_NAME_SIZE];
    char description[MAX_DESC_SIZE];
    char types[2][MAX_TYPE_SIZE];
    int type_count;
    char abilities[5][MAX_ABIL_SIZE];
    int ability_count;
    double weight;
    double height;
    int captureRate;
    bool isLegendary;
    Date captureDate;
} Pokemon;

typedef struct No {
    Pokemon elemento;
    struct No *esq, *dir;
} No;

No* raiz = NULL;

void inserir(Pokemon x);
No* inserirNo(Pokemon x, No* i);
bool pesquisar(char* nome);
bool pesquisarNo(char* nome, No* i);
int quantidadeDeNos();
int quantidadeDeNosNo(No* i);
void imprimirPokemon(Pokemon p);
Pokemon* lerPokemons(int* count);

int main() {
    int count = 0;
    Pokemon* allCharacters = lerPokemons(&count);

    char id[MAX_NAME_SIZE];
    while (true) {
        scanf("%s", id);
        if (strcmp(id, "FIM") == 0) {
            break;
        }
        for (int i = 0; i < count; i++) {
            if (strcmp(allCharacters[i].id, id) == 0) {
                inserir(allCharacters[i]);
            }
        }
    }

    char nome[MAX_NAME_SIZE];
    while (true) {
        scanf("%s", nome);
        if (strcmp(nome, "FIM") == 0) {
            break;
        }
        printf("%s\n", nome);
        if (!pesquisar(nome)) {
            // Se necessário, adicionar mais lógica aqui
        }
    }

    int qnr = quantidadeDeNos();
    printf("%d\n", qnr);

    free(allCharacters);
    return 0;
}

void inserir(Pokemon x) {
    raiz = inserirNo(x, raiz);
}

No* inserirNo(Pokemon x, No* i) {
    if (i == NULL) {
        i = (No*)malloc(sizeof(No));
        i->elemento = x;
        i->esq = i->dir = NULL;
    } else if (strcmp(x.name, i->elemento.name) < 0) {
        i->esq = inserirNo(x, i->esq);
    } else if (strcmp(x.name, i->elemento.name) > 0) {
        i->dir = inserirNo(x, i->dir);
    } else {
        fprintf(stderr, "ERRO: Elemento duplicado\n");
    }
    return i;
}

bool pesquisar(char* nome) {
    printf(" =>raiz ");
    return pesquisarNo(nome, raiz);
}

bool pesquisarNo(char* nome, No* i) {
    if (i == NULL) {
        printf("NAO\n");
        return false;
    } else if (strcmp(nome, i->elemento.name) == 0) {
        printf("SIM\n");
        return true;
    } else if (strcmp(nome, i->elemento.name) < 0) {
        printf("esq ");
        return pesquisarNo(nome, i->esq);
    } else {
        printf("dir ");
        return pesquisarNo(nome, i->dir);
    }
}

int quantidadeDeNos() {
    return quantidadeDeNosNo(raiz);
}

int quantidadeDeNosNo(No* i) {
    if (i == NULL) {
        return 0;
    }
    return 1 + quantidadeDeNosNo(i->esq) + quantidadeDeNosNo(i->dir);
}

void imprimirPokemon(Pokemon p) {
    printf("[#%s -> %s: %s - [", p.id, p.name, p.description);

    for (int i = 0; i < p.type_count; i++) {
        printf("'%s'", p.types[i]);
        if (i < p.type_count - 1) {
            printf(", ");
        }
    }
    printf("] - [");

    for (int i = 0; i < p.ability_count; i++) {
        printf("%s", p.abilities[i]);
        if (i < p.ability_count - 1) {
            printf(", ");
        }
    }
    printf("] - %.1fkg - %.1fm - %d%% - %s - %d gen] - %02d/%02d/%04d\n",
           p.weight, p.height, p.captureRate, p.isLegendary ? "true" : "false",
           p.generation, p.captureDate.day, p.captureDate.month, p.captureDate.year);
}

Pokemon* lerPokemons(int* count) {
    FILE* file = fopen(FILE_NAME, "r");
    if (!file) {
        perror("Failed to open file");
        exit(EXIT_FAILURE);
    }

    // Ignorar o cabeçalho
    char buffer[1024];
    fgets(buffer, sizeof(buffer), file);

    Pokemon* pokemons = malloc(0);
    *count = 0;

    while (fgets(buffer, sizeof(buffer), file)) {
        pokemons = realloc(pokemons, (*count + 1) * sizeof(Pokemon));
        Pokemon* p = &pokemons[*count];

        char* token = strtok(buffer, ",");
        strcpy(p->id, token);

        token = strtok(NULL, ",");
        p->generation = atoi(token);

        token = strtok(NULL, ",");
        strcpy(p->name, token);

        token = strtok(NULL, ",");
        strcpy(p->description, token);

        p->type_count = 0;
        for (int i = 0; i < 2; i++) {
            token = strtok(NULL, ",");
            if (token && strlen(token) > 0) {
                strcpy(p->types[p->type_count++], token);
            }
        }

        token = strtok(NULL, ",");
        char abilities[MAX_ABIL_SIZE * 5];
        strcpy(abilities, token);
        p->ability_count = 0;
        char* abil_token = strtok(abilities, "[]\",");
        while (abil_token) {
            strcpy(p->abilities[p->ability_count++], abil_token);
            abil_token = strtok(NULL, "[]\",");
        }

        token = strtok(NULL, ",");
        p->weight = atof(token);

        token = strtok(NULL, ",");
        p->height = atof(token);

        token = strtok(NULL, ",");
        p->captureRate = atoi(token);

        token = strtok(NULL, ",");
        p->isLegendary = atoi(token) == 1;

        token = strtok(NULL, ",");
        sscanf(token, "%d/%d/%d", &p->captureDate.day, &p->captureDate.month, &p->captureDate.year);

        (*count)++;
    }

    fclose(file);
    return pokemons;
}
