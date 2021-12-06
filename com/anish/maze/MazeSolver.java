package com.anish.maze;

import com.anish.maze.Pos;

public class MazeSolver {
    private int[][] maze;
    private int[][] DFSGraph;
    private Pos nextStep;
    private int posX;
    private int posY;
    private int startX;
    private int startY;
    
    public MazeSolver(int [][] initMaze, int targetX, int targetY, int startX, int startY){
        maze = initMaze;
        DFSGraph = new int[maze.length][maze[0].length];
        for(int i = 0; i < maze.length; i++){
            for (int j = 0; j < maze[0].length; j++){
                if(maze[i][j] == 1){
                    DFSGraph[i][j] = -1;
                }
                else{
                    DFSGraph[i][j] = maze.length * maze[0].length;
                }
            }
        }
        nextStep = Pos.none;
        posX = targetX;
        posY = targetY;
        this.startX = startX;
        this.startY = startY;
    }

    private void traverseDFS(int i, int j){
        if (i > 0 && DFSGraph[i - 1][j] != -1 && DFSGraph[i-1][j] > DFSGraph[i][j] + 1){
            DFSGraph[i-1][j] = DFSGraph[i][j] + 1;
            traverseDFS(i - 1, j);
        }
        if (i < maze.length - 1 && DFSGraph[i + 1][j] != -1 && DFSGraph[i+1][j] > DFSGraph[i][j] + 1){
            DFSGraph[i+1][j] = DFSGraph[i][j] + 1;
            traverseDFS(i + 1, j);
        }
        if (j > 0 && DFSGraph[i][j-1] != -1 && DFSGraph[i][j-1] > DFSGraph[i][j] + 1){
            DFSGraph[i][j-1] = DFSGraph[i][j] + 1;
            traverseDFS(i, j-1);
        }
        if (j < maze[0].length - 1 && DFSGraph[i][j+1] != -1 && DFSGraph[i][j+1] > DFSGraph[i][j] + 1){
            DFSGraph[i][j+1] = DFSGraph[i][j] + 1;
            traverseDFS(i, j+1);
        }
    }

    private void getPathDFS(int i, int j){
        if (i > 0 && DFSGraph[i - 1][j] != -1 && DFSGraph[i-1][j] == DFSGraph[i][j] - 1){
            nextStep = Pos.down;
            getPathDFS(i - 1, j);
            return;
        }
        if (i < maze.length - 1 && DFSGraph[i + 1][j] != -1 && DFSGraph[i+1][j] == DFSGraph[i][j] - 1){
            nextStep = Pos.up;
            getPathDFS(i + 1, j);
            return;
        }
        if (j > 0 && DFSGraph[i][j-1] != -1 && DFSGraph[i][j-1] == DFSGraph[i][j] - 1){
            nextStep = Pos.right;
            getPathDFS(i, j-1);
            return;
        }
        if (j < maze[0].length - 1 && DFSGraph[i][j+1] != -1 && DFSGraph[i][j+1] == DFSGraph[i][j] - 1){
            nextStep = Pos.left;
            getPathDFS(i, j+1);
            return;
        }
    }

    public Pos getSolution(){
        DFSGraph[startX][startY] = 0;
        DFSGraph[posX][posY] = maze.length * maze[0].length;
        traverseDFS(startX, startY);
        getPathDFS(posX, posY);
        return nextStep;
    }
}
