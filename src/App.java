import java.util.ArrayList;
public class App {
    public static void main(String[] args) throws Exception {
        Board board = new Board();
        ArrayList<Position> p = new ArrayList<Position>();
        for(int i =0; i< 3; i++){
            p.add(new Position(6, 5 + i));
        }
        p.add(new Position(5, 7));
        p.add(new Position(4, 6));
        for(Position cell:p){
            board.initialize(cell);
        }
        System.out.println(board.toString());
        for(int j =0;j < 27; j++){
            System.out.println(board.lifeGame());
        }
    }
}
