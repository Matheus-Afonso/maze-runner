package mazerunner;

import java.util.ArrayList;
import java.util.List;

public class Cell {
	
	// Representa a posição de cada célula do labirinto para ser usada pelo Prim
	private final int x;
	private final int y;
	private boolean visited;
	// Responsável por saber quais são as células vizinhas
	private final List<Cell> neighbors;
	
	//MazeSolver: avisa se é uma parede
	private boolean wall;
	
	// MazeSolver: avisa se a célula faz parte do menor caminho ou se já foi descartada
	private boolean inPath;
	
	public Cell(int x, int y) {
		this(x, y, false);
	}
	
	public Cell(int x, int y, boolean wall) {
		this.x = x;
		this.y = y;
		this.wall = wall;
		this.visited = false;
		this.neighbors = new ArrayList<>();
		this.inPath = false;
	}
	
	public void addNeighbor(Cell otherCell) {
		// Evitar duplicados
		if(!neighbors.contains(otherCell)) {
			neighbors.add(otherCell);
		}
		
		// Quando adiciona em uma, adiciona na List da celula vizinha
		if(!otherCell.neighbors.contains(this)) {
			otherCell.neighbors.add(this);
		}
	}
	
	// Para MazeSolver
	public void addNeighbor(List<Cell> neighbours) {
		neighbours.forEach(this::addNeighbor);
	}
	
	public boolean hasRigthCellNeighbor() {
		return neighbors.stream()
				.anyMatch(c -> c.getX() == this.x && c.getY() == this.y + 1);
	}
	
	public boolean hasLowerCellNeighbor() {
		return neighbors.stream()
				.anyMatch(c -> c.getX() == this.x + 1 && c.getY() == this.y);
	}
	
	// Usado apenas para MazeSolver
	public boolean hasUpperCellNeighbor() {
		return neighbors.stream()
				.anyMatch(c -> c.getX() == this.x - 1 && c.getY() == this.y);
	}
	
	// Usado para MazeSolver
	public boolean hasLeftCellNeighbor() {
		return neighbors.stream()
				.anyMatch(c -> c.getX() == this.x && c.getY() == this.y - 1);
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public boolean isWall() {
		return wall;
	}

	public void setWall(boolean wall) {
		this.wall = wall;
	}
	
	public boolean isInPath() {
		return inPath;
	}

	public void setInPath(boolean inPath) {
		this.inPath = inPath;
	}

	public List<Cell> getNeighbors() {
		return neighbors;
	}

	@Override
	public String toString() {
		return "Cell [x=" + x + ", y=" + y + ", visited=" + visited + ", wall=" + wall + ", inPath=" + inPath + "]";
	}


}
