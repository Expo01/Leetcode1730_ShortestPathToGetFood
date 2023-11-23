import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}


class Solution {
    public int getFood(char[][] grid) {

        int row = grid.length;
        int col = grid[0].length;

        Queue<int[]> qu = new LinkedList<>();
        boolean[][] visited = new boolean[row][col]; // stores to avoid queuing a visited direction
        for(int i=0; i<row; i++) {
            for(int j=0; j<col;j++) {
                if(grid[i][j] == '*') {
                    qu.add(new int[]{i, j}); // add start to que and break
                    visited[i][j] = true;
                    break;
                }
            }
        }
        int count = 0;
        int[] dir = new int[]{0, 1, 0, -1, 0};
        while(!qu.isEmpty()) {
            int size = qu.size();
            for(int i=0; i<size; i++) { // loop only current size to exclude incrementing count for each cell. just want each layer count++;
                int[] cur = qu.poll();
                int x = cur[0]; // cur coords
                int y = cur[1];
                if(grid[x][y] == '#') {
                    return count;
                }
                for(int a=0; a<4; a++) { // neighbor coords
                    int newx = x + dir[a];
                    int newy = y + dir[a+1];
                    if(newx<0 || newy<0 || newx>=row || newy>=col || visited[newx][newy]) { // if visit out OOB, thn onto nxt iteration in loop
                        continue;
                    }
                    if(grid[newx][newy] == 'X') { // if wall, don't add
                        continue;
                    }
                    visited[newx][newy] = true; // will add neighbor if # or 0
                    qu.add(new int[]{newx, newy});

                }
            }

            count +=1; // inc count outside each 'layer' loop
        }

        return -1;
    }
}

 // my attempt. moostly on track
class Solution {
    public int getFood(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[] startPosition = new int[2];
        boolean[][] visited = new boolean[rows][cols];

        for(int row = 0; row < rows; rows++){
            for(int col = 0; col < cols; col++){
                if(grid[row][col] == '*'){
                    startPosition = new int[]{row,col};
                    visited[row][col] = true;
                    break;
                }
            }
        }
        return BFS(startPosition[0],startPosition[1], grid, visited);
    }

    private int BFS(int startRow, int startColumn, char[][] grid, boolean[][] visited){

        Queue<Integer[]> neighbors = new ArrayDeque<>();
        neighbors.add(new Integer[]{startColumn,startColumn});
        int[][] directions = {{-1,0},{0,1},{1,0},{0,-1}};
        int distance = -1;

        while(!neighbors.isEmpty()){
            distance++; // starts at 0 for start cell, then each round of neighbors will increment

            for(int i = 0; i < neighbors.size(); i++) { // this makes it so distance won't increment with each cell dequed, but each round
                Integer[] point = neighbors.remove();

                if (grid[point[0]][point[1]] == '#') {
                    return distance;
                } else if (grid[point[0]][point[1]] == '0') { // condition unccessary. only adds # and 0. if not # then is 0
                    for (int[] dir : directions) { // add all neighbor cells to queue that are 0 or #
                        int neighborRow = point[0] + dir[0];
                        int neighborCol = point[1] + dir[1];
                        char cellVal = grid[neighborRow][neighborCol];
                        if(neighborRow < 0 || neighborCol < 0 || neighborRow >= grid.length || neighborCol >= grid[0].length || visited[neighborCol][neighborCol]){
                            continue;
                        }
                        if (cellVal == '0' || cellVal == '#') { // it is important to add # since the distance is processed
                            // when cell dequed, not added
                            neighbors.add(new Integer[]{neighborRow, neighborCol});
                        }
                    }
                }
            }

        }
        return -1; // if whole que empty since either no food or unable to que cells to get to food because of X, then return -1
    }
}

/*

- will want to BFS to expand perimeter to food. DFS could be circuitous
- start location of * not given, will need to find it. there may be a way to do this in a single loop, but I am
    just going to loop mxn until * found and break
- if X found, don't add to queue
- if 0 found, add to queue
- if # found for food, then find a way to break and return true since this is BFS and first food found should be closest
- if BFS terminates before finding food, return -1
- edge case of graph size equal 1 should be handled since no food can be present with a person

 */