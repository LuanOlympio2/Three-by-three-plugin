# ThreeByThree Plugin

Plugin para servidores Minecraft que adiciona ferramentas 3x3 capazes de quebrar blocos em area.

## Sobre o plugin

O ThreeByThree adiciona ferramentas especiais nos formatos picareta, pa, machado e enxada que agilizam a mineracao e a coleta de recursos no modo survival, com protecao para servidores e compatibilidade ampla.

## Funcionalidades principais

* Mineracao em area de 3 por 3 blocos
* Variantes completas nos niveis madeira, pedra, cobre, ferro e diamante
* Receitas balanceadas no padrao survival
* Todas as pedras e variantes ativadas por padrao
* Compatibilidade com protecoes de terreno e jogadores Bedrock

## Comandos

* /give3x3 <jogador> <tipo> [nivel]
Tipos disponiveis: pickaxe, shovel, axe, hoe
Niveis disponiveis: wood, stone, copper, iron, diamond
Permissao padrao: threebythree.give

## Receitas de criacao

As ferramentas sao produzidas na bancada de trabalho combinando gravetos com os respectivos blocos ou materiais:

* Madeira: troncos de madeira bruta
* Pedra: pedra lisa
* Cobre: blocos de cobre
* Ferro: blocos de ferro
* Diamante: blocos de diamante

## Como compilar

Utilize o Maven para gerar o arquivo jar:

mvn clean package

Copie o jar gerado na pasta target para a pasta plugins do seu servidor.
