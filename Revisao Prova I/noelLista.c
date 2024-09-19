#include <stdio.h>
#include <string.h>

int main (){

    int N; //numero de criancas
    int comportadas = 0;
    char comp;
    int nComportadas = 0;

    char nome[50];

    //ler numero de criancas na lista
    scanf("%d", &N);

    int i =0;
    while(i < N){
        scanf(" %c", &comp);//ler se + ou -

        if(comp == '-'){
            nComportadas++;
        }else comportadas++;

        fgets(nome, sizeof(nome), stdin);
        nome[strcspn(nome, "\n")];

         printf(nome);

      i++;
    }

    printf("Comportados: %i", comportadas);
    printf("Comportados: %i", comportadas);

    return 0;
}
