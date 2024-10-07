#include <stdio.h>
#include <stdlib.h>
#include <math.h>


/*int swap(int x, int y){
     int tmp = x;
     x= y;
     y = tmp;

    return y;
}
*/

void swap(int *x, int *y) {
    int tmp = *x;
    *x = *y;
    *y = tmp;
}



int main(){

   int N;//numero de notas 
   int K;//aprovados

   scanf("%d", &N);
   scanf("%i", &K);

   int vetor[N];  

   for(int i=0; i<N; i++){
     scanf("%d", &vetor[i]);
   }

   //ordenar 
   for(int i=0; i<N; i++){
   //int menor = vetor[i];
   int menor = i;
    for(int j=i+1; j<N; j++){
           if(vetor[j] < vetor[menor]){
            menor = vetor[j];
           }
    }
    
    swap(&vetor[i], &vetor[menor]);

   }
  //espero que funcione 

   int x = N - K;

   printf("%d", vetor[x]);

    return 0;
}