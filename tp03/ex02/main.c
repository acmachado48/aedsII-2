#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MAX_POKEMONS 803
#define MAX_CHAR 500
#define FILE_NAME "/tmp/pokemon.csv"

typedef struct {
    char id[10];
    char name[MAX_CHAR];
    char description[MAX_CHAR];
} Pokemon;

typedef struct {
    Pokemon pokemons[MAX_POKEMONS];
    int count;
} ListaPokemons;

void inicializarListaPokemons(ListaPokemons* lista) {
    lista->count = 0;
}

void inserir(ListaPokemons* lista, Pokemon* pokemon, int posicao) {
    if (lista->count < MAX_POKEMONS) {
        for (int i = lista->count; i > posicao; i--) {
            lista->pokemons[i] = lista->pokemons[i - 1];
        }
        lista->pokemons[posicao] = *pokemon;
        lista->count++;
    }
}

Pokemon removerInicio(ListaPokemons* lista) {
    Pokemon removido = lista->pokemons[0];
    for (int i = 1; i < lista->count; i++) {
        lista->pokemons[i - 1] = lista->pokemons[i];
    }
    lista->count--;
    return removido;
}

Pokemon removerFim(ListaPokemons* lista) {
    Pokemon removido = lista->pokemons[lista->count - 1];
    lista->count--;
    return removido;
}

Pokemon remover(ListaPokemons* lista, int posicao) {
    Pokemon removido = lista->pokemons[posicao];
    for (int i = posicao; i < lista->count - 1; i++) {
        lista->pokemons[i] = lista->pokemons[i + 1];
    }
    lista->count--;
    return removido;
}

void imprimirPokemon(Pokemon* pokemon) {
    printf("[#%s -> %s: %s]\n", pokemon->id, pokemon->name, pokemon->description);
}

int lerPokemons(ListaPokemons* allPokemons) {
    int id;
    char name[MAX_CHAR];
    char description[MAX_CHAR];

    while (scanf("%d", &id) && id != "FIM") { // 34 representa o "FIM"
        scanf("%s", name);
        scanf(" %[^\n]s", description);

        Pokemon pokemon;
        sprintf(pokemon.id, "%d", id);
        strcpy(pokemon.name, name);
        strcpy(pokemon.description, description);

        allPokemons->pokemons[allPokemons->count++] = pokemon;
    }

    return 0;
}

void processarComandos(ListaPokemons* listaPokemons, ListaPokemons* allPokemons) {
    char comando[MAX_CHAR];

    while (scanf(" %[^\n]s", comando) != EOF && strcmp(comando, "Saída") != 0) {
        char* partes[3];
        int i = 0;
        partes[i] = strtok(comando, " ");
        while (partes[i] != NULL) {
            partes[++i] = strtok(NULL, " ");
        }

        if (strcmp(partes[0], "II") == 0) {
            Pokemon novo;
            for (int k = 0; k < allPokemons->count; k++) {
                if (strcmp(allPokemons->pokemons[k].id, partes[1]) == 0) {
                    novo = allPokemons->pokemons[k];
                    break;
                }
            }
            inserir(listaPokemons, &novo, 0);
        } else if (strcmp(partes[0], "I*") == 0) {
            int posicao = atoi(partes[1]);
            Pokemon novo;
            for (int k = 0; k < allPokemons->count; k++) {
                if (strcmp(allPokemons->pokemons[k].id, partes[2]) == 0) {
                    novo = allPokemons->pokemons[k];
                    break;
                }
            }
            inserir(listaPokemons, &novo, posicao);
        } else if (strcmp(partes[0], "IF") == 0) {
            Pokemon novo;
            for (int k = 0; k < allPokemons->count; k++) {
                if (strcmp(allPokemons->pokemons[k].id, partes[1]) == 0) {
                    novo = allPokemons->pokemons[k];
                    break;
                }
            }
            inserir(listaPokemons, &novo, listaPokemons->count);
        } else if (strcmp(partes[0], "RI") == 0) {
            if (listaPokemons->count > 0) {
                Pokemon removido = removerInicio(listaPokemons);
                printf("(R) %s\n", removido.name);
            }
        } else if (strcmp(partes[0], "R*") == 0) {
            int posicaoR = atoi(partes[1]);
            if (posicaoR < listaPokemons->count) {
                Pokemon removido = remover(listaPokemons, posicaoR);
                printf("(R) %s\n", removido.name);
            }
        } else if (strcmp(partes[0], "RF") == 0) {
            if (listaPokemons->count > 0) {
                Pokemon removido = removerFim(listaPokemons);
                printf("(R) %s\n", removido.name);
            }
        }
    }
}

// Função principal
int main() {
    ListaPokemons listaPokemons;
    ListaPokemons allPokemons;

    inicializarListaPokemons(&listaPokemons);
    inicializarListaPokemons(&allPokemons);

    if (lerPokemons(&allPokemons) == -1) {
        return 1;
    }

    processarComandos(&listaPokemons, &allPokemons);

    for (int i = 0; i < listaPokemons.count; i++) {
        imprimirPokemon(&listaPokemons.pokemons[i]);
    }

    return 0;
}
