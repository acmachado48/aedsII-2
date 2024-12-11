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

// Definindo a estrutura para nós da árvore AVL
typedef struct No {
    Pok valor;
    struct No* esq;
    struct No* dir;
    int altura;
} No;

// Funções auxiliares de árvore AVL
int altura(No* n) {
    if (n == NULL) {
        return 0;
    }
    return n->altura;
}

int max(int a, int b) {
    return (a > b) ? a : b;
}

No* criarNo(Pokemon pokemon) {
    No* novoNo = (No*)malloc(sizeof(No));
    novoNo->valor = pokemon;
    novoNo->esq = NULL;
    novoNo->dir = NULL;
    novoNo->altura = 1; // Novo nó sempre tem altura 1
    return novoNo;
}


int fatorBalanceamento(No* n) {
    if (n == NULL) {
        return 0;
    }
    return altura(n->esq) - altura(n->dir);
}

No* rotacionarDireita(No* y) {
    No* x = y->esq;
    No* T2 = x->dir;

    x->dir = y;
    y->esq = T2;

    y->altura = max(altura(y->esq), altura(y->dir)) + 1;
    x->altura = max(altura(x->esq), altura(x->dir)) + 1;

    return x;
}

No* rotacionarEsquerda(No* x) {
    No* y = x->dir;
    No* T2 = y->esq;

    y->esq = x;
    x->dir = T2;

    x->altura = max(altura(x->esq), altura(x->dir)) + 1;
    y->altura = max(altura(y->esq), altura(y->dir)) + 1;

    return y;
}

No* adicionar(No* node, Pokemon valor) {
    if (node == NULL) {
        return criarNo(valor);
    }

    if (strcmp(valor.name, node->valor.name) < 0) {
        node->esq = adicionar(node->esq, valor);
    } else if (strcmp(valor.name, node->valor.name) > 0) {
        node->dir = adicionar(node->dir, valor);
    } else {
        return node; // Não adiciona duplicados
    }

    node->altura = 1 + max(altura(node->esq), altura(node->dir));

    int balance = fatorBalanceamento(node);

    if (balance > 1 && strcmp(valor.name, node->esq->valor.name) < 0) {
        return rotacionarDireita(node);
    }

    if (balance < -1 && strcmp(valor.name, node->dir->valor.name) > 0) {
        return rotacionarEsquerda(node);
    }

    if (balance > 1 && strcmp(valor.name, node->esq->valor.name) > 0) {
        node->esq = rotacionarEsquerda(node->esq);
        return rotacionarDireita(node);
    }

    if (balance < -1 && strcmp(valor.name, node->dir->valor.name) < 0) {
        node->dir = rotacionarDireita(node->dir);
        return rotacionarEsquerda(node);
    }

    return node;
}

No* buscar(No* node, const char* name) {
    if (node == NULL) {
        return NULL;
    }

    printf(" raiz ");
    if (strcmp(name, node->valor.name) == 0) {
        return node;
    } else if (strcmp(name, node->valor.name) < 0) {
        printf("esq ");
        return buscar(node->esq, name);
    } else {
        printf("dir ");
        return buscar(node->dir, name);
    }
}

// Função para exibir os dados do Pokemon
void exibirPokemon(Pokemon pokemon) {
    printf("[#%s -> %s: %s - [", pokemon.id, pokemon.name, pokemon.description);
    printf("%s", pokemon.types[0]);
    if (strlen(pokemon.types[1]) > 0) {
        printf(", %s", pokemon.types[1]);
    }
    printf("] - [");
    printf("%s", pokemon.abilities[0]);
    for (int i = 1; i < MAX_ABILITIES; i++) {
        if (strlen(pokemon.abilities[i]) > 0) {
            printf(", %s", pokemon.abilities[i]);
        }
    }
    printf("] - %.1fkg - %.1fm - %d%% - %s - %d gen] - %s\n", pokemon.weight, pokemon.height, pokemon.captureRate,
        pokemon.isLegendary ? "true" : "false", pokemon.generation, pokemon.captureDate);
}

// Função para ler o arquivo CSV
int lerArquivo(Pokemon* pokemons) {
    FILE* file = fopen(ARQ, "r");
    if (!file) {
        perror("Erro ao abrir o arquivo");
        return 0;
    }

    char linha[MAX_LINE_LENGTH];
    int i = 0;
    while (fgets(linha, sizeof(linha), file)) {
        if (i == 0) {
            i++;
            continue; // Pular cabeçalho
        }

        Pok pokemon;
        char tempTypes[MAX_TYPES][50] = {0};
        char tempAbilities[MAX_ABILITIES][50] = {0};

        sscanf(linha, "%s,%d,%49[^,],%255[^,],%49[^,],%49[^,],%lf,%lf,%d,%d,%10[^,]",
               pokemon.id, &pokemon.generation, pokemon.name, pokemon.description,
               tempTypes[0], tempTypes[1], &pokemon.weight, &pokemon.height, &pokemon.captureRate,
               &pokemon.isLegendary, pokemon.captureDate);

        // Copiar tipos
        strncpy(pokemon.types[0], tempTypes[0], 50);
        if (strlen(tempTypes[1]) > 0) {
            strncpy(pokemon.types[1], tempTypes[1], 50);
        }

        // Lidar com habilidades
        char* token = strtok(linha, "[,]");
        int abilityIndex = 0;
        while (token != NULL && abilityIndex < MAX_ABILITIES) {
            strncpy(tempAbilities[abilityIndex], token, 50);
            abilityIndex++;
            token = strtok(NULL, ",");
        }
        for (int j = 0; j < MAX_ABILITIES; j++) {
            strncpy(pokemon.abilities[j], tempAbilities[j], 50);
        }

        pokemons[i++] = pokemon;
    }

    fclose(file);
    return i - 1;
}


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
                adicionar(allCharacters[i]);
            }
        }
    }

    int qnr = quantidadeDeNos();
    printf("%d\n", qnr);

    free(allCharacters);
    return 0;
}

