package adt;

public class RandomMazeGenerator {
    
    private static final int WALL = 0;
    private static final int PATH = 1;
    private static final int VISITED = 2;
    
    private static final int[] ROW_OFFSETS = {0, 1, 0, -1};
    private static final int[] COL_OFFSETS = {1, 0, -1, 0};
    
    private int[][] maze;
    private int rows;
    private int cols;
    
    public RandomMazeGenerator(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        maze = new int[rows][cols];
        generateMaze();
    }
    
    private void generateMaze() {
        for (int row = 0; row < rows; row++) {
            maze[row][0] = WALL;
            maze[row][cols - 1] = WALL;
        }
        for (int col = 0; col < cols; col++) {
            maze[0][col] = WALL;
            maze[rows - 1][col] = WALL;
        }
        for (int row = 2; row < rows - 2; row += 2) {
            for (int col = 2; col < cols - 2; col += 2) {
                maze[row][col] = WALL;
            }
        }
        generateMaze(2, 2);
    }

    private void generateMaze(int row, int col) {
        int[] offsets = {0, 1, 2, 3};
        shuffle(offsets);
        for (int i = 0; i < offsets.length; i++) {
            int nextRow = row + ROW_OFFSETS[offsets[i]];
            int nextCol = col + COL_OFFSETS[offsets[i]];
            int nextNextRow = row + 2 * ROW_OFFSETS[offsets[i]];
            int nextNextCol = col + 2 * COL_OFFSETS[offsets[i]];
    
            // Check if the nextNextRow and nextNextCol are within bounds
            if (nextNextRow >= 0 && nextNextRow < rows && nextNextCol >= 0 && nextNextCol < cols) {
                if (maze[nextNextRow][nextNextCol] == WALL) {
                    maze[nextNextRow][nextNextCol] = PATH;
                    maze[nextRow][nextCol] = PATH;
                    generateMaze(nextNextRow, nextNextCol);
                }
            }
        }
    }
    
    

    private void shuffle(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int j = i + (int)(Math.random() * (array.length - i));
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    public void print() {
        for (int row = 0; row < rows; row++) {
            System.out.print(" ");
            for (int col = 0; col < cols; col++) {
                if (maze[row][col] == WALL) {
                    System.out.print("X");
                } else if (maze[row][col] == PATH) {
                    System.out.print(" ");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        RandomMazeGenerator maze = new RandomMazeGenerator(10, 20);
        maze.print();
    }
}
