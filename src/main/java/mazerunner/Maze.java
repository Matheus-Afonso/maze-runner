package mazerunner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Maze {
	
	private static final Random random = new Random();
	private int altura;
	private int largura;
	private int alturaCell;
	private int larguraCell;
	private String[][] mazeGrid;	// Cada celula fica com o nome de um número
	private Cell[][] cellGrid;
	
	// Constantes
	private static final String FREE = "  ";
	private static final String WALL = "\u2588\u2588";
	
	public Maze(int size) {
		this(size, size);
	}
	
	public Maze(int altura, int largura) {
		// Atualiza as variáveis
		initDimensions(altura, largura);
		
		// Geração do labirinto
		initCells();
		generateMaze();
	}
	
	public Maze(FileReader existingMaze) throws IOException {
		int qtdColumns;
		int qtdLines;
		StringBuilder mazeString = new StringBuilder();
		
		try (BufferedReader readMaze = new BufferedReader(existingMaze)) {
			String line = readMaze.readLine();
			// Conta a quantidade de linhas e colunas. Cada elemento consite de 2 caracteres
			qtdColumns = line.length()/2;
			qtdLines = 0;
			
			while (line != null) {
				qtdLines++;
				mazeString.append(line).append("#");	// para facilitar o split
				line = readMaze.readLine();
			}
		}
		
		initDimensions(qtdLines, qtdColumns);
		// Salva o labirinto no mazeGrid
		importMazeToGrid(mazeString.toString());
	}
	
	private void initDimensions(int altura, int largura) {
		// Os parametros de mazeGrid são maiores por considerar paredes e celulas
		this.altura = altura;
		this.largura = largura;
		mazeGrid = new String[altura][largura];

		// Parametros das células
		this.alturaCell = (altura - 1)/2;
		this.larguraCell = (largura - 1)/2;
	}
	
	private void initCells() {
		cellGrid = new Cell[alturaCell][larguraCell];
		for(int i = 0; i < alturaCell; i++) {
			for(int j = 0; j < larguraCell; j++) {
				cellGrid[i][j] = new Cell(i, j, false);
			}
		}
	}
	
	private void generateMaze() {
		// Sempre irá iniciar em um ponto aleatório na primeira coluna
		generateMaze(random.nextInt(alturaCell), 0);
	}
	
	private void generateMaze(int startX, int startY) {
		
		// Inicia a lista de cellsVisited com a cell de início
		Cell startCell = cellGrid[startX][startY];
		startCell.setVisited(true);
		List<Cell> cellsVisited = new ArrayList<>();
		cellsVisited.add(startCell);
		
		while (!cellsVisited.isEmpty()) {
			Cell cell;
			
			// Remove o último elemento da lista cellsVisited e analisa esse elemento removido
			cell = cellsVisited.remove(cellsVisited.size() - 1);
			
			// Pega os vizinhos dessa celula e filtra os necessários
			List<Cell> neighbours = new ArrayList<>(Arrays.asList(
					getCell(cell.getX() + 1, cell.getY()),
					getCell(cell.getX() - 1, cell.getY()),
					getCell(cell.getX(), cell.getY() + 1),
					getCell(cell.getX(), cell.getY() - 1)
					));
			neighbours.removeIf(n -> (n == null || n.isVisited() || n.isWall()));
			
			if(!neighbours.isEmpty()) {
				Cell selected = neighbours.get(random.nextInt(neighbours.size()));
				cell.addNeighbor(selected);
				selected.setVisited(true);
				// Coloca de volta o cell retirado e adiciona o novo
				cellsVisited.add(cell);
				cellsVisited.add(selected);
			}
		}
		createGrid();
	}

	public void draw() {
		for (String[] line: mazeGrid) {
			for (String symbol: line) {
				System.out.print(symbol);
			}
			System.out.println();
		}
	}
	
	private Cell getCell(int x, int y) {
		try {
			return cellGrid[x][y];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	private void createGrid() {
		// Responsável por popular o mazeGrid para o desenho
		createDefaultWalls();
		
		for (int i = 0; i < alturaCell; i++) {
			for (int j = 0; j < larguraCell; j++) {
				Cell selected = cellGrid[i][j];
				// Toda celula de cellgrid equivale a celula 2*num + 1 de mazegrid
				int mazeI = 2*i + 1;
				int mazeJ = 2*j + 1;
				mazeGrid[mazeI][mazeJ] = FREE;
				
				// Checa a fronteira da direita e coloca o símbolo correto no grid
				if (selected.hasRigthCellNeighbor()) {
					mazeGrid[mazeI][mazeJ + 1] = FREE;
				} else {
					mazeGrid[mazeI][mazeJ + 1] = WALL;
				}
				
				// Checa a fronteira de baixo
				if (selected.hasLowerCellNeighbor()) {
					mazeGrid[mazeI + 1][mazeJ] = FREE;
				} else {
					mazeGrid[mazeI + 1][mazeJ] = WALL;
				}
				
 			}
		}
		drawRemainingWalls();
		drawRemainingFree();
		insertEntranceAndExit();
	}
	
	private void createDefaultWalls() {
		// Primeira coluna: so parede
		for (int i = 0; i < altura; i++) {
			mazeGrid[i][0] = WALL;
		}

		// Primeira linha: Só parede
		for (int j = 0; j < largura; j++) {
			mazeGrid[0][j] = WALL;
		}
	}
	
	private void drawRemainingWalls() {
		// Substitui as partes nulas que sobraram por parede
		for (int i = 0; i < altura; i++) {
			for (int j = 0; j < largura; j++) {
				if (mazeGrid[i][j] == null) {
					mazeGrid[i][j] = WALL;
				}
			}
		}
	}
	
	private void insertEntranceAndExit() {
		// Entrada
		int randomEntrance = random.nextInt(cellGrid.length);
		mazeGrid[2*randomEntrance + 1][0] = FREE;
		
		// Saída
		int randomExit = random.nextInt(cellGrid.length);
		while (randomExit == randomEntrance) {
			// Evita linhas retas entre a entrada e a saída
			randomExit = random.nextInt(cellGrid.length);
		}
		mazeGrid[2*randomExit + 1][mazeGrid[0].length - 1] = FREE;
		
		// Melhora a saída para o caso de tamanho par. Adiciona mais um espaço em branco
		if (largura % 2 == 0) {
			mazeGrid[2*randomExit + 1][mazeGrid[0].length - 2] = FREE;
		}
	}
	
	// Metodo para que não apareça paredes juntas em todo lugar quando a largura ou altura forem pares
	private void drawRemainingFree() {
		if (altura % 2 == 0) {
			for (int i = 0; i < alturaCell; i++) {
				if (random.nextInt(2) == 1) {
					mazeGrid[2*i + 1][mazeGrid[0].length - 2] = FREE;
				}
			}
		}
		
		if (largura % 2 == 0) {
			for (int j = 0; j < larguraCell; j++) {
				if (random.nextInt(2) == 1) {
					mazeGrid[mazeGrid.length - 2][2*j + 1] = FREE;
				}
			}
		}
	}
 	
	// Métodos para salvar o labirinto em um arquivo
	private void importMazeToGrid(String mazeString) {
		// Popula o maze grid de acordo com o arquivo pego pelo construtor
		String[] lines = mazeString.split("#");
		
		for (int i = 0; i < lines.length; i++) {
			// Separa de 2 em 2, sem perder strings(?<=) e considerando tudo(\\G)
			mazeGrid[i] = lines[i].split("(?<=\\G.{2})");
		}
	}
	
	public void saveMaze(String filename) throws IOException {
		try (BufferedWriter mazeArq = new BufferedWriter(new FileWriter(filename))) {
			for (int i = 0; i < altura; i++) {
				for (int j = 0; j < largura; j++) {
					mazeArq.write(mazeGrid[i][j]);
				}
				mazeArq.write("\n");
			}
		} 
	}
	
	public int getAltura() {
		return altura;
	}

	public int getLargura() {
		return largura;
	}
	
	public String[][] getMazeGrid() {
		return mazeGrid;
	}

	@Override
	public String toString() {
		return "Maze [cellHeight=" + alturaCell + ", cellWidth=" + larguraCell + ", mazeHeight=" + altura
				+ ", mazeWidth=" + largura + "]";
	}
}
