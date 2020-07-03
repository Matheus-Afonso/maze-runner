# Maze Runner 
Programa que cria e acha a saída de labirintos, além de poder salvar e importar um novo de um arquivo .txt. 
O labirinto deve ter tamanho mínimo de 5x5 blocos. Código feito inteiramente em **Java**.
 
Feito de acordo com o projeto [Maze Runner](https://hyperskill.org/projects/47) do Hyperskill.

[front](/images/front.jpg)

## Instruções

### Execução

Para Windows: Recomendado clonar o repositório e executar em sua IDE de preferência com console próprio como Eclipse, já que no prompt de comando do Windows
a leitura dos símbolos para parede (*\u2588*) não é suportada, o que dificulta a visualização do labirinto.

Para iniciar o programa em Linux e Mac, execute o arquivo .jar na pasta target:
 ```
 cd target
 java -jar maze-1.0.jar
 ```

### Menu

O menu possui 6 opções, na quais 3 são ocultas inicialmente até que um labirinto seja gerado ou importado pelo usuário.

Número | Nome | Oculto | Descrição
------ | ---- | ------ | ---------
1 | Gerar um novo labirinto | **Não** | Pergunta o tamanho do labirinto NxN e o gera após o usuário inserir o valor. Mostra o labirinto após gerá-lo
2 | Carregar um labirinto de um arquivo | **Não** | Importa o labirinto de um arquivo .txt
3 | Salvar o labirinto em um arquivo .txt | **Sim** | Salva o labirinto atual em um arquivo
4 | Mostrar o labirinto | **Sim** | Mostra o labirinto atual no console
5 | Achar a saida | **Sim** | Gera e mostra o menor caminho de saída do labirinto no console.
0 | Encerrar | **Não** | Encerra o programa

### Gerar Labirinto
Após escolher a primeira opção, o programa espera o usuário digitar o tamanho do labirinto NxN, sendo obrigatório receber apenas um número e maior e igual a 5.
O labirinto é gerado usando o [algoritmo de busca de profundidade](https://pt.qwe.wiki/wiki/Maze_generation_algorithm#Depth-first_search) e o resultado final
é mostrado no console. O labirinto representa paredes como blocos duplos (*\u2588\u2588*) e espaços livres como dois espaços em branco.

[maze](/images/maze.jpg)

### Importar Labirinto
Ao escolher a segunda opção o programa aguarda o usuário digitar o nome do arquivo .txt que possui o labirinto, com extensão. Mesmo após importar com sucesso,
o labirinto não irá ser mostrado no console. Necessário selecionar a opção 4 após isso para mostrar o labirinto. 

**OBS: Após a geração ou importação as opções 3 a 5 não estarão mais ocultas.**

### Salvar Labirinto
Ao selecionar essa opção, o programa aguarda o usuário digitar o nome do arquivo com a extensão .txt. O arquivo será salvo diretamente na pasta do programa.

### Achar a Saída
Ao escolher a quinta opção, o programa calcula o caminho mais curto entre a entrada e saída e o mostra no console. Exemplo de labirinto após escolher a 
quinta opção:

[solved](/images/solved.jpg)
