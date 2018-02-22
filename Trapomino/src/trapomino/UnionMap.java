package trapomino;

/**
 * This class manages the UnionFind algorithm that will check to see if an Animal is trapped by blocks.
 * @author Micheal Peterson
 */
public class UnionMap {

    public static int[][] id;//for union find, same ids are connected

    /**
    * This constructor initializes the id array and fills each location with a different integer.
    * @param N - the length of the 2D-array
    * @param M - the width of the 2D-array
    */
    public UnionMap(int N, int M){
        id = new int[N][M];
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                id[i][j] = i*100 + j;
            }
        }
    }

    /**
    * This method checks to see if the two passed Positions in the array have the same value.
    * @param pos1 - the first set of indexes in the array
    * @param pos2 - the second set of indexes in the array
    * @return true if the values are the same, otherwise return false
    */
    public boolean find(Position pos1, Position pos2){
        return id[pos1.getX()][pos1.getY()] == id[pos2.getX()][pos2.getY()];
    }

    /**
    * This method checks through every index in the 2D-array to see if it has the same value as the given Position.
    * If any Position in the array has the same value as the given Position, it unites the 2 Positions with the same value.
    * @param pos1 - the first set of indexes in the array
    * @param pos2 - the second set of indexes in the array
    */
    public void Unite(Position pos1, Position pos2){
        int pos1Id = id[pos2.getX()][pos2.getY()];
        for(int i = 0; i < id.length; i++){
            for(int j = 0; j < id[0].length; j++){
                if(id[i][j] == pos1Id){ 
                    id[i][j] = id[pos1.getX()][pos1.getY()];
                }
            }
        }
    }

    /**
    * This method prints out the UnionMap into the console and is used for debugging the UnionFind algorithm.
    * @return the String to be printed in the console
    */
    @Override
    public String toString(){
        String map = "";
        for(int i = 0; i < id.length; i++){
            for(int j = 0; j < id[0].length; j++){
                map = map + id[i][j] + ", ";
            }
            map = map + "\n";
        }
        return map;
    }
}