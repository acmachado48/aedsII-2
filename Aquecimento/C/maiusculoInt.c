#include <stdio.h>
#include <string.h>
#include <ctype.h>

int main() {
    char nome[100];

    while (1) {
        int cont = 0;
       
        fgets(nome, sizeof(nome), stdin); 
        
        // Remove o '\n' adicionado pelo fgets
        nome[strcspn(nome, "\n")] = '\0';
        
        if (strcmp(nome, "FIM") == 0) {
            break;  
        }
        
        for (int i = 0; i < strlen(nome); i++) {
            if (isupper(nome[i])) {
                cont++;
            }
        }
        
        printf("%d\n", cont);
    }
    
    return 0;
}
