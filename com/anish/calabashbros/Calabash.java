package com.anish.calabashbros;

import java.awt.Color;

import com.anish.screen.WorldScreen;
import com.anish.maze.MazeSolver;
import com.anish.maze.Pos;

public class Calabash extends Creature implements Runnable {
    private WorldScreen wscreen;
    private int target = 0;

    public Calabash(Color color, int index, World world, WorldScreen ws) {
        super(color, (char) 2, world, index, 40);
        this.wscreen = ws;
    }

    private void getTarget() {
        int min = 10000;
        target = -1;
        for (int i = 0; i < 4; i++) {
            if (wscreen.snakes[i] == null){
                continue;
            }
            else{
                int d = Math.abs(this.getX() - wscreen.snakes[i].getX()) + Math.abs(this.getY() - wscreen.snakes[i].getY());
                if (d < min){
                    min = d;
                    target = i;
                }
            }
        }
    }

    private boolean isReadToAtttack() {
        if(wscreen.snakes[target] == null) {
            target = -1;
            return false;
        }
        int distance = Math.abs(this.getX() - wscreen.snakes[target].getX()) + Math.abs(this.getY() - wscreen.snakes[target].getY());
        if (distance == 1){
            return true;
        }
        else {
            return false;
        }
    }

    private void attack() {
        wscreen.snakes[target].hurt(10);
    }

    private void move() {
        MazeSolver ms = new MazeSolver(wscreen.getMaze(), wscreen.snakes[target].getX(), wscreen.snakes[target].getY(), this.getX(), this.getY());
        Pos nextStep = ms.getSolution();
        int preX = this.getX();
        int preY = this.getY();
        int nextX = preX;
        int nextY = preY;
        switch(nextStep) {
            case up:{
                nextX--;
                break;
            }
            case down:{
                nextX++;
                break;
            }
            case left:{
                nextY--;
                break;
            }
            case right:{
                nextY++;
                break;
            }
            default:{
                return;
            }
        }
        this.moveTo(nextX, nextY);
        wscreen.bros[preX][preY] = new Floor(wscreen.world);
        wscreen.world.put(wscreen.bros[preX][preY], preX, preY);
        wscreen.bros[nextX][nextY] = this;
    }

    public void run(){
        while(this.isAlive()){
            try {
                wscreen.waitForAvailable();
                this.getTarget();
                if(this.target != -1) {
                    if (this.isReadToAtttack()){
                        this.attack();
                    }
                    else{
                        this.move();
                    }
                }
                wscreen.markAsAvailable();
                Thread.yield();
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }            
        }
        int preX = this.getX();
        int preY = this.getY();
        wscreen.bros[preX][preY] = new Floor(wscreen.world);
        wscreen.world.put(wscreen.bros[preX][preY], preX, preY);
        wscreen.calabashs[this.getIndex()] = null;
    }

}
