# Trabalho Prático I

O grupo deverá resolver dois problemas clássicos da área de Sistemas Operacionais, para tanto as instruções abaixo são
brevemente elucidativas e, portanto, devem ser complementadas a partir da bibliografia da disciplina. Os problemas são:

## 01 – Problema da barbearia de Hilzer

William Stallings (Stallings, 2012) apresenta uma versão mais complicada do problema da barbearia, que ele atribui a
Ralph Hilzer da Universidade da Califórnia. O problema consiste em uma barbearia com três barbeiros e três cadeiras
próprias de barbeiros, também existe uma série de lugares para que os clientes possam esperar. Tanto os barbeiros quanto
os clientes devem ser implementados como Threads.

Requisitos:

1. três cadeiras;
2. três barbeiros;
3. uma sala de espera com um sofá de quatro lugares;
4. o número total de clientes permitidos na barbearia é 20;
5. nenhum cliente entrará se a capacidade do local estiver satisfeita;
6. se o cliente entrou e tiver lugar no sofá ele se senta, caso contrário ele espera
   em pé;
7. quando um barbeiro está livre para atender, o cliente que está a mais tempo no
   sofá é atendido e o que está a mais tempo em pé se senta;
8. qualquer barbeiro pode aceitar pagamento, mas somente um cliente pode pagar
   por vez, porque só há uma POS;
9. os barbeiros dividem o seu tempo entre cortar cabelo, receber pagamento e
   dormir enquanto esperam por um cliente.

Referencias

Stallings, W., & Paul, G. K. (2012). Operating systems: internals and design principles (Vol. 9). New York: Pearson.

## O que deve ser entregue junto com a solução do trabalho:

1. Link de um vídeo publicado no Yotube (pode ser não listado), onde o aluno deve
   apresentar a solução;
2. Deve demonstrar que funciona;
3. No vídeo gravado deve conter no mínimo a explicação de como foi dada solução
   para os 9 requisitos.

## Nota sobre atrasos:

Para cada semana de atraso é descontado um ponto do valor total do trabalho. Sendo assim, um trabalho com atraso de uma
semana tem sua nota máxima igual a 9, dessa forma, o grupo que executou 50% receberá 50% de 9, logo 4,5. Sendo que o
atraso máximo tolerado é de 3 semanas.
