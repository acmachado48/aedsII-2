/*Implemente um programa denominado Combinador, que recebe duas strings 
e deve combiná-las, alternando as letras de cada string, começando com a 
primeira letra da primeira string, seguido pela primeira letra da segunda 
tring, em seguida pela segunda letra da primeira string, e assim sucessivamente.
As letras restantes da cadeia mais longa devem ser adicionadas ao fim da string
resultante e retornada.*/

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int main (){
    char frase1[100], frase2[100];

    fgets(frase1, sizeof(frase1), stdin);
    fgets(frase2, sizeof(frase2), stdin);

    char resultado[200];
    int i = 0, j = 0, k = 0;
  
  while (frase1[i] != '\0' && frase2[j] != '\0') {
      resultado[k++] = frase1[i++];
      resultado[k++] = frase2[j++];
  }

  
  while (frase1[i] != '\0') {
      resultado[k++] = frase1[i++];
  }

  while (frase2[i] != '\0') {
      resultado[k++] = frase2[i++];
  }

  resultado[k] = '\0';

  printf("%s\n", resultado);

    return 0;
}