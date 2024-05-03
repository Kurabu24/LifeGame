import java.util.ArrayList;

public class Board {
    int[][] board = new int[30][30];
    int[][] nextBoard = new int[30][30];
    ArrayList<Position> cells = new ArrayList<Position>();

    public void refresh() {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (this.board[i][j] == 1) {
                    this.cells.add(new Position(i, j));
                }
            }
        }
    }

    public void reset() {
        this.cells.clear();
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                this.nextBoard[i][j] = 0;
            }
        }
    }

    public void add(Position p) {
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
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
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

    private int AliveArround(Position p) {
        int x = p.x;
        int y = p.y;
        int aliveCount = 0;
        if (x == 0) {
            if (y == 0) {
                if (this.isAlive(new Position(x, y + 1))) {
                    aliveCount++;
                }
                if (this.isAlive(new Position(x + 1, y + 1))) {
                    aliveCount++;
                }
                if (this.isAlive(new Position(x + 1, y))) {
                    aliveCount++;
                }
            } else if (y == 29) {
                if (this.isAlive(new Position(x, y - 1))) {
                    aliveCount++;
                }
                if (this.isAlive(new Position(x + 1, y - 1))) {
                    aliveCount++;
                }
                if (this.isAlive(new Position(x + 1, y))) {
                    aliveCount++;
                }
            } else {
                for (int i = 0; i < 3; i++) {
                    if (this.isAlive(new Position(x + 1, y - 1 + i))) {
                        aliveCount++;
                    }
                }
                if (this.isAlive(new Position(x, y + 1))) {
                    aliveCount++;
                }
                if (this.isAlive(new Position(x, y - 1))) {
                    aliveCount++;
                }
            }
        } else if (x == 29) {
            if (y == 0) {
                if (this.isAlive(new Position(x, y + 1))) {
                    aliveCount++;
                }
                if (this.isAlive(new Position(x - 1, y + 1))) {
                    aliveCount++;
                }
                if (this.isAlive(new Position(x - 1, y))) {
                    aliveCount++;
                }
            } else if (y == 29) {
                if (this.isAlive(new Position(x, y - 1))) {
                    aliveCount++;
                }
                if (this.isAlive(new Position(x - 1, y - 1))) {
                    aliveCount++;
                }
                if (this.isAlive(new Position(x - 1, y))) {
                    aliveCount++;
                }
            } else {
                for (int i = 0; i < 3; i++) {
                    if (this.isAlive(new Position(x - 1, y - 1 + i))) {
                        aliveCount++;
                    }
                }
                if (this.isAlive(new Position(x, y + 1))) {
                    aliveCount++;
                }
                if (this.isAlive(new Position(x, y - 1))) {
                    aliveCount++;
                }
            }
        } else {
            if (y == 0) {
                if (this.isAlive(new Position(x - 1, y))) {
                    aliveCount++;
                }
                if (this.isAlive(new Position(x + 1, y))) {
                    aliveCount++;
                }
                for (int j = 0; j < 3; j++) {
                    if (this.isAlive(new Position(x - 1 + j, y + 1))) {
                        aliveCount++;
                    }
                }
            } else if (y == 29) {
                if (this.isAlive(new Position(x - 1, y))) {
                    aliveCount++;
                }
                if (this.isAlive(new Position(x + 1, y))) {
                    aliveCount++;
                }
                for (int j = 0; j < 3; j++) {
                    if (this.isAlive(new Position(x - 1 + j, y - 1))) {
                        aliveCount++;
                    }
                }
            } else {
                for (int i = 0; i < 3; i++) {
                    if (this.isAlive(new Position(x - 1, y - 1 + i))) {
                        aliveCount++;
                    }
                    if (this.isAlive(new Position(x + 1, y - 1 + i))) {
                        aliveCount++;
                    }
                }
                if (this.isAlive(new Position(x, y + 1))) {
                    aliveCount++;
                }
                if (this.isAlive(new Position(x, y - 1))) {
                    aliveCount++;
                }
            }
        }
        return aliveCount;
    }

    private void checkLife(Position p) {
        int aliveCount = this.AliveArround(p);
        if (!this.isAlive(p)) {
            if (aliveCount == 3) {
                this.add(p);
            }
        } else {
            if (!(aliveCount == 2 || aliveCount == 3)) {
                this.del(p);
            }
            else{
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
            if (x == 0) {
                if (y == 0) {
                    this.checkLife(new Position(x, y + 1));
                    this.checkLife(new Position(x + 1, y + 1));
                    this.checkLife(new Position(x + 1, y));
                } else if (y == 29) {
                    this.checkLife(new Position(x, y - 1));
                    this.checkLife(new Position(x + 1, y - 1));
                    this.checkLife(new Position(x + 1, y));
                } else {
                    for (int i = 0; i < 3; i++) {
                        this.checkLife(new Position(x + 1, y - 1 + i));
                    }
                    this.checkLife(new Position(x, y + 1));
                    this.checkLife(new Position(x, y - 1));
                }
            } else if (x == 29) {
                if (y == 0) {
                    this.checkLife(new Position(x, y + 1));
                    this.checkLife(new Position(x - 1, y + 1));
                    this.checkLife(new Position(x - 1, y));
                } else if (y == 29) {
                    this.checkLife(new Position(x, y - 1));
                    this.checkLife(new Position(x - 1, y - 1));
                    this.checkLife(new Position(x - 1, y));
                } else {
                    for (int i = 0; i < 3; i++) {
                        this.checkLife(new Position(x - 1, y - 1 + i));
                    }
                    this.checkLife(new Position(x, y + 1));
                    this.checkLife(new Position(x, y - 1));
                }
            } else {
                if (y == 0) {
                    this.checkLife(new Position(x - 1, y));
                    this.checkLife(new Position(x + 1, y));
                    for (int j = 0; j < 3; j++) {
                        this.checkLife(new Position(x - 1 + j, y + 1));
                    }
                } else if (y == 29) {
                    this.checkLife(new Position(x - 1, y));
                    this.checkLife(new Position(x + 1, y));
                    for (int j = 0; j < 3; j++) {
                        this.checkLife(new Position(x - 1 + j, y - 1));
                    }
                } else {
                    for (int i = 0; i < 3; i++) {
                        this.checkLife(new Position(x - 1, y - 1 + i));
                        this.checkLife(new Position(x + 1, y - 1 + i));
                    }
                    this.checkLife(new Position(x, y + 1));
                    this.checkLife(new Position(x, y - 1));
                }
            }
        }
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                this.board[i][j] = this.nextBoard[i][j];
            }
        }
        this.reset();
        return this.toString();
    }

}
