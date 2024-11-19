#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <ctype.h>

#define QUEUE_SIZE 5

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
    Pokemon queue[QUEUE_SIZE];
    int front;
    int rear;
    int size;
} Queue;

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

// Inicializa a fila
void initQueue(Queue *q) {
    q->front = 0;
    q->rear = -1;
    q->size = 0;
}

// Verifica se a fila está cheia
int isQueueFull(Queue *q) {
    return q->size == QUEUE_SIZE;
}

// Verifica se a fila está vazia
int isQueueEmpty(Queue *q) {
    return q->size == 0;
}

// Insere um Pokémon na fila
void enqueue(Queue *q, Pokemon *pokemon) {
    if (isQueueFull(q)) {
        q->front = (q->front + 1) % QUEUE_SIZE; // Remove o mais antigo
        q->size--;
    }
    q->rear = (q->rear + 1) % QUEUE_SIZE;
    q->queue[q->rear] = *pokemon;
    q->size++;
}

// Remove um Pokémon da fila
Pokemon dequeue(Queue *q) {
    if (isQueueEmpty(q)) {
        printf("Erro: Fila vazia\n");
        exit(1);
    }
    Pokemon removed = q->queue[q->front];
    q->front = (q->front + 1) % QUEUE_SIZE;
    q->size--;
    return removed;
}

// Calcula a média da taxa de captura dos Pokémons na fila
double averageCaptureRate(Queue *q) {
    if (isQueueEmpty(q)) {
        return 0.0;
    }
    int sum = 0;
    for (int i = 0; i < q->size; i++) {
        int index = (q->front + i) % QUEUE_SIZE;
        sum += q->queue[index].captureRate;
    }
    return (double)sum / q->size;
}

// Imprime os Pokémons na fila
void imprimirFila(Queue *q) {
    for (int i = 0; i < q->size; i++) {
        int index = (q->front + i) % QUEUE_SIZE;
        Pokemon *pokemon = &q->queue[index];
        char dateStr[20];
        strftime(dateStr, sizeof(dateStr), "%d/%m/%Y", &pokemon->captureDate);

        printf("[#%s -> %s: %.1fkg, %.1fm, %d%% de captura, %s, geração %d]\n",
               pokemon->id, pokemon->name, pokemon->weight, pokemon->height,
               pokemon->captureRate, pokemon->isLegendary ? "Lendário" : "Comum",
               pokemon->generation);
    }
}

// Lê os Pokémons de um arquivo CSV
void lerDadosDoArquivo(Queue *queue, const char *filename) {
    FILE *file = fopen(filename, "r");
    if (!file) {
        perror("Erro ao abrir o arquivo");
        exit(1);
    }

    char line[2048];
    fgets(line, sizeof(line), file); // Ignora o cabeçalho

    while (fgets(line, sizeof(line), file)) {
        line[strcspn(line, "\r\n")] = 0; // Remove o caractere de nova linha

        Pokemon pokemon;
        memset(&pokemon, 0, sizeof(Pokemon));

        // Processa os campos da linha
        char *fields[20];
        int numFields = split_at_char(line, ',', fields, 20);

        if (numFields < 10) {
            printf("Linha incompleta: %s\n", line);
            continue;
        }

        // Popula os dados do Pokémon
        strcpy(pokemon.id, fields[0]);
        pokemon.generation = atoi(fields[1]);
        strcpy(pokemon.name, fields[2]);
        pokemon.weight = atof(fields[3]);
        pokemon.height = atof(fields[4]);
        pokemon.captureRate = atoi(fields[5]);
        pokemon.isLegendary = atoi(fields[6]);

        // Insere na fila
        enqueue(queue, &pokemon);
    }

    fclose(file);
}

// Função principal para processar comandos
void processCommands() {
    Queue queue;
    initQueue(&queue);

    char command[10];
    while (scanf("%s", command) != EOF) {
        if (strcmp(command, "FIM") == 0) {
            break;
        } else if (strcmp(command, "IMPRIMIR") == 0) {
            imprimirFila(&queue);
        } else if (strcmp(command, "AVERAGE") == 0) {
            printf("Média da taxa de captura: %.2f%%\n", averageCaptureRate(&queue));
        } else {
            printf("Comando inválido: %s\n", command);
        }
    }
}

int main() {
    processCommands();
    return 0;
}
