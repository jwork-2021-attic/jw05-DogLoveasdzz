package com.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Random;

import com.anish.calabashbros.Calabash;
import com.anish.calabashbros.World;
import com.anish.maze.MazeGenerator;
import com.anish.calabashbros.Wall;
import com.anish.calabashbros.Wizard;
import com.anish.calabashbros.Floor;
import com.anish.calabashbros.Thing;
import com.anish.calabashbros.Snake;
import com.Main;


import asciiPanel.AsciiPanel;

public class WorldScreen implements Screen {

    public World world;
    public Thing[][] bros;
    public int[][] maze;
    private MazeGenerator mazeGenerator;
    private Random randomGenerator = new Random();
    private Main mainThread;

    public Calabash[] calabashs;
    public Snake[] snakes;

    private boolean isAvailable;

    private Thread[] t;

    public WorldScreen(Main m) {
        world = new World();
        mainThread = m;

        mazeGenerator = new MazeGenerator(60);
        mazeGenerator.generateMaze();
        maze = mazeGenerator.getMaze();
        bros = new Thing[maze.length][maze[0].length];
        for(int i = 0; i < bros.length; i++){
            for(int j = 0; j < bros[0].length; j++){
                if (maze[i][j] == 1){
                    bros[i][j] = new Wall(world);
                }
                else{
                    bros[i][j] = new Floor(world);
                }
                world.put(bros[i][j], i, j);
            }
        }
        isAvailable = true;
        calabashs = new Calabash[4];
        calabashs[0] = new Calabash(new Color(204, 0, 0), 0, world, this);
        calabashs[1] = new Calabash(new Color(204, 0, 0), 1, world, this);
        calabashs[2] = new Calabash(new Color(204, 0, 0), 2, world, this);
        calabashs[3] = new Calabash(new Color(204, 0, 0), 3, world, this);

        snakes = new Snake[4];
        snakes[0] = new Snake(new Color(78, 154, 6), 0, world, this);
        snakes[1] = new Snake(new Color(78, 154, 6), 1, world, this);
        snakes[2] = new Snake(new Color(78, 154, 6), 2, world, this);
        snakes[3] = new Snake(new Color(78, 154, 6), 3, world, this);

        randomPut();

        t = new Thread[8];
        for (int i = 0; i < 4; i++) {
            t[i] = new Thread(calabashs[i]);
            t[i].start();
        }
        for (int i = 4; i < 8; i++) {
            t[i] = new Thread(snakes[i - 4]);
            t[i].start();
        }
    }

    private void randomPut() {
        int indexc = 0; 
        int indexs = 0;
        while (indexc < 4) {
            int x = this.randomGenerator.nextInt(60);
            int y = this.randomGenerator.nextInt(60);
            if (maze[x][y] == 1) {
                continue;
            }
            else {
                bros[x][y] = calabashs[indexc];
                world.put(bros[x][y], x, y);
                indexc++;
            }
        }
        while(indexs < 4) {
            int x = this.randomGenerator.nextInt(60);
            int y = this.randomGenerator.nextInt(60);
            if (maze[x][y] == 1) {
                continue;
            }
            else {
                bros[x][y] = snakes[indexs];
                world.put(bros[x][y], x, y);
                indexs++;
            }
        }
    }

    public synchronized void waitForAvailable() throws InterruptedException {
        while (isAvailable == false) {
            wait();
        }
        isAvailable = false;
    }

    public synchronized void markAsAvailable() throws InterruptedException{
        isAvailable = true;
        mainThread.repaint();
        notifyAll();
    }

    public int[][] getMaze() {
        for (int i = 0; i < 60; i++) {
            for (int j = 0; j < 60; j++) {
                if(bros[i][j].getClass() == Floor.class || bros[i][j].getClass() == Wizard.class) {
                    maze[i][j] = 0;
                }
                else {
                    maze[i][j] = 1;
                }
            }
        }
        return maze;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {

        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {

                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());

            }
        }
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        return this;
    }

}
