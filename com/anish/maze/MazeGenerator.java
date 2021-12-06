package com.anish.maze;

//import java.util.ArrayList;
//import java.util.Stack;
import java.util.Random;
//import java.util.Arrays;

public class MazeGenerator {
    private int dimension;
    private int[][]maze;
    private int[][]tempMaze;
    Random  random = new Random();
    private double wallRate = 0.37;

    public MazeGenerator(int d) {
        dimension = d;
        maze = new int[d][d];
        tempMaze = new int[d][d];
    }

    public int[][] getMaze(){
        return maze;
    }

    public void generateMaze() {
        initMaze();
        for(int i = 0; i < 5; i++) {
            smoothMaze();
        }
    }

    private void initMaze(){
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                maze[i][j] = random.nextDouble() <= wallRate ? 1 : 0;
            }
        }
    }

    private void smoothMaze() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int count = getCount(i, j);
                if (count < 4) {
                    tempMaze[i][j] = 0;
                }
                else if (count >= 4) {
                    tempMaze[i][j] = 1;
                }
            }
        }
        int[][] temp = tempMaze;
        tempMaze = maze;
        maze = temp;
    }

    private int getCount(int x, int y) {
        int count = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i < 0 || i >= dimension || j < 0 || j >= dimension) {
                    count += random.nextDouble() < wallRate ? 1 : 0;
                    continue;
                }
                if (x == i && j == y) {
                    continue;
                }
                else {
                    count += maze[i][j];
                }
            }
        }
        return count;
    }
}