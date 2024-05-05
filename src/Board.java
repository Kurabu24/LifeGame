import java.util.ArrayList;

public class Board {
    int gridSize;
    int[][] board;
    int[][] nextBoard;
    ArrayList<Position> cells = new ArrayList<Position>();

    public Board(int gridSize) {
        this.gridSize = gridSize;
        this.board = new int[gridSize][gridSize];
        this.nextBoard = new int[gridSize][gridSize];
    }

    private void refresh() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (this.isAlive(new Position(i, j))) {
                    this.cells.add(new Position(i, j));
                }
            }
        }
    }

    private void reset() {
        this.cells.clear();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                this.nextBoard[i][j] = 0;
            }
        }
    }

    private void add(Position p) {
        int x = p.x;
        int y = p.y;
        this.nextBoard[x][y] = 1;
    }

    public void initialize(Position p) {
        int x = p.x;
        int y = p.y;
        this.board[x][y] = 1;
    }

    private void del(Position p) {
        int x = p.x;
        int y = p.y;
        this.nextBoard[x][y] = 0;
    }

    private boolean isAlive(Position p) {
        int x = p.x;
        int y = p.y;
        return this.board[x][y] == 1;
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (!this.isAlive(new Position(i, j))) {
                    result += "☐";
                } else {
                    result += "▣";
                }
            }
            result += "\n";
        }
        result += "\n\n";
        return result;
    }

    private int aliveArround(Position p) {
        int x = p.x;
        int y = p.y;
        int aliveCount = 0;
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if ((i > 0 && i < gridSize) && (j > 0 && j < gridSize)) {
                    if (i == x && j == y) {

                    } else if (this.isAlive(new Position(i, j))) {
                        aliveCount++;
                    }
                }
            }
        }
        return aliveCount;
    }

    private void checkLife(Position p) {
        int aliveCount = this.aliveArround(p);
        if (!this.isAlive(p)) {
            if (aliveCount == 3) {
                this.add(p);
            }
        } else {
            if (!(aliveCount == 2 || aliveCount == 3)) {
                this.del(p);
            } else {
                this.add(p);
            }
        }
    }

    public String lifeGame() {
        this.refresh();
        if (cells.isEmpty()) {
            return "there is no living cells";
        }
        for (Position cell : this.cells) {
            int x = cell.x;
            int y = cell.y;
            checkLife(cell);
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if ((i > 0 && i < gridSize) && (j > 0 && j < gridSize)) {
                        if (i == x || j == y) {

                        } else {
                            checkLife(new Position(i, j));
                        }
                    }
                }
            }
        }
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                this.board[i][j] = this.nextBoard[i][j];
            }
        }
        this.reset();
        return this.toString();
    }

}
