#include <stdio.h>
#include <string.h>
#include <ctype.h>

int contarMaiusculas(const char *nome, int index) {
    // Caso base: se o índice atingir o final da string, retorna 0
    if (index >= strlen(nome)) {
        return 0;
    }

    // Verifica se o caractere atual é maiúsculo
    int cont = isupper(nome[index]) ? 1 : 0;

    // Chama a função recursivamente para o próximo caractere
    return cont + contarMaiusculas(nome, index + 1);
}

int main() {
    char nome[100];  

    while (1) {
       
        fgets(nome, sizeof(nome), stdin); 
        
        // Remove o '\n' adicionado pelo fgets
        nome[strcspn(nome, "\n")] = '\0';
        
        if (strcmp(nome, "FIM") == 0) {
            break;  // Sai do loop se a entrada for "FIM"
        }

        
        int cont = contarMaiusculas(nome, 0);
        printf("%d\n", cont);
    }
    
    return 0;
}
