package mazerunner;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/* Projeto Maze Runner do Hyperskill
 * 
 * Criar um labirinto com uma saída usando um array bidimensional
 * O labirinto deve ter tamanho variável, quadrado e aleatório
 * Cercado por paredes com exceção da entrada e saída
 * A parede deve ser representada no print por dois blocos (\u2588\u2588)
 * O espaço equivale a dois espaços em branco "  "
 * Menu com 5 opções. Elas não aparecem de uma vez. Começa com 1,2 e 0
 	* 1. Generate a new maze: Recebe só um parametro. Um 17 gera 17x17.
 	* 2. Load a new maze: Carrega o labirinto de um arquivo. Se o arquivo não existir, avisa
 	* 3. Save the Maze: Salva o labirinto gerado em um arquivo.
 	* 4. Display the Maze: Printa o labirinto. Apenas roda se tiver sido gerado um antes.
 	* 5. Find the escape: Mostra o caminho mais rápido de sair do labirinto. Marcado por //
 	* 0. Exit.
 * */

public class Main {
	
	private static final Scanner sc = new Scanner(System.in);
	private static Maze maze;
	
	public static void main(String[] args) {

		while(true) {
			menu();
			int opt = sc.nextInt();

			if (opt == 1) {
				maze = generate();
				if(maze != null) {
					maze.draw();
				}
			} else if (opt == 2) {
				maze = load();
			} else if (opt == 3 && maze != null) {
				save();
			} else if (opt == 4 && maze != null) {
				maze.draw();
			} else if (opt == 5 && maze != null) {
				solve();
			} else if (opt == 0) {
				break;
			} else {
				System.out.println("Opcao invalida. Tente Novamente.");
			}
			
			System.out.println();
		}
		sc.close();
		System.out.println("Encerrado!");
	}
	
	public static void menu() {
		System.out.println("=== Menu ===");
		System.out.println("1. Gerar um novo labirinto.");
		System.out.println("2. Carregar um labirinto de um arquivo.");
		
		if (maze != null) {
			System.out.println("3. Salvar o labirinto em um arquivo .txt.");
			System.out.println("4. Mostrar o labirinto.");
			System.out.println("5. Achar a saida.");
		}
		
		System.out.println("0. Encerrar");
	}
	
	public static Maze generate() {
		// Desenhar um labirinto quadrado
		System.out.print("Tamanho do labirinto (minimo: 5): ");
		int size = sc.nextInt();
		
		if (size <= 4) {
			System.out.println("ERRO: O labirinto deve possuir tamanho minimo 5.");
			return null;
		} 
		return new Maze(size);
	}
	
	public static Maze load() {
		sc.nextLine();
		System.out.print("Nome do arquivo (com extensao): ");
		String filename = sc.nextLine();
		
		try (FileReader mazeFile = new FileReader(filename)) {
			maze = new Maze(mazeFile);
		} catch (IOException e) {
			System.out.println("O arquivo '" + filename + "' nao foi encontrado");
		}
		
		return maze;
	}
	
	public static void save() {
		sc.nextLine();
		System.out.print("Nome do arquivo (com extensao): ");
		String filename = sc.nextLine();
		if (filename.endsWith(".txt")) {
			try {
				maze.saveMaze(filename);
			} catch (IOException e) {
				System.out.println("Erro na escrita do arquivo: " + filename);
			}
		} else {
			System.out.println("Arquivo com extensao invalida. O arquivo deve possuir extensao .txt");
		}
	}
	
	public static void solve() {
		MazeSolver solvedMaze = new MazeSolver(maze);
		solvedMaze.draw();
	}
}
