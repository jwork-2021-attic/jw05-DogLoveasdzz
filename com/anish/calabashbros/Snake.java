package com.anish.calabashbros;

import java.awt.Color;

import com.anish.screen.WorldScreen;

public class Snake extends Creature implements Runnable{
    private WorldScreen wscreen;
    private int target = 0;
    private int magicTimer = 2;
    private int tx = 0;
    private int ty = 0;

    private final int attackSpeed = 3;

    public Snake(Color color, int index, World world, WorldScreen ws) {
        super(color, (char) 1, world, index, 40);
        this.wscreen = ws;
    }

    private void getTarget() {
        int min = 10000;
        target = -1;
        for (int i = 0; i < 4; i++) {
            if (wscreen.calabashs[i] == null){
                continue;
            }
            else{
                int d = Math.abs(this.getX() - wscreen.calabashs[i].getX()) + Math.abs(this.getY() - wscreen.calabashs[i].getY());
                if (d < min){
                    min = d;
                    target = i;
                }
            }
        }
    }

    private boolean isReadyToAtttack() {
        if (0 == magicTimer){
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isReadyToClear() {
        if (magicTimer == attackSpeed) {
            return true;
        }
        else {
            return false;
        }
    }


    private void attack() {
        int targetX = wscreen.calabashs[target].getX();
        int targetY = wscreen.calabashs[target].getY();
        tx = targetX;
        ty = targetY;
        int x = this.getX();
        int y = this.getY();
        if (x == targetX) {
            if (targetY < y) {
                int temp = targetY;
                targetY = y;
                y = temp;
            }
            for (int i = y; i <= targetY; i++) {
                if (wscreen.bros[x][i].getClass() == Calabash.class) {
                    attack(x, i);
                }
                else if (wscreen.bros[x][i].getClass() == Snake.class) {
                    continue;
                }
                else {
                    wscreen.bros[x][i] = new Wizard(wscreen.world);
                    wscreen.world.put(wscreen.bros[x][i], x, i);
                }
            }
        }
        else if (y == targetY) {
            if (targetX < x) {
                int temp = targetX;
                targetX = x;
                x = temp;
            }
            for (int i = x; i <= targetX; i++) {
                if (wscreen.bros[i][y].getClass() == Calabash.class) {
                    attack(i, y);
                }
                else if (wscreen.bros[i][y].getClass() == Snake.class) {
                    continue;
                }
                else {
                    wscreen.bros[i][y] = new Wizard(wscreen.world);
                    wscreen.world.put(wscreen.bros[i][y], i, y);
                }
            } 
        }
        else {
            if (Math.abs(x - targetX) > Math.abs(y - targetY)) {
                if (targetX < x) {
                    int temp = targetX;
                    targetX = x;
                    x = temp;
                    temp = targetY;
                    targetY = y;
                    y = temp;
                }
                for (int i = x; i <= targetX; i++){
                    int j = (int)(((double)i - (double)x)*((double)targetY - (double)y)/((double)targetX - (double)x) + (double)y);
                    if (wscreen.bros[i][j].getClass() == Calabash.class) {
                        attack(i,j);
                    }
                    else if (wscreen.bros[i][j].getClass() == Snake.class) {
                        continue;
                    }
                    else {
                        wscreen.bros[i][j] = new Wizard(wscreen.world);
                        wscreen.world.put(wscreen.bros[i][j], i, j);
                    }
                }
            }
            else {
                if (targetY < y) {
                    int temp = targetX;
                    targetX = x;
                    x = temp;
                    temp = targetY;
                    targetY = y;
                    y = temp;
                }
                for (int j = y; j <= targetY; j++){
                    int i = (int)(((double)j - (double)y)*((double)targetX - (double)x)/((double)targetY - (double)y) + (double)x);
                    if (wscreen.bros[i][j].getClass() == Calabash.class) {
                        attack(i,j);
                    }
                    else if (wscreen.bros[i][j].getClass() == Snake.class) {
                        continue;
                    }
                    else {
                        wscreen.bros[i][j] = new Wizard(wscreen.world);
                        wscreen.world.put(wscreen.bros[i][j], i, j);
                    }
                }
            }
        }
    }

    private void clearMagic() {
        int targetX = tx;
        int targetY = ty;
        int x = this.getX();
        int y = this.getY();
        if (x == targetX) {
            if (targetY < y) {
                int temp = targetY;
                targetY = y;
                y = temp;
            }
            for (int i = y; i <= targetY; i++) {
                if (wscreen.bros[x][i].getClass() == Wizard.class) {
                    wscreen.bros[x][i] = new Floor(wscreen.world);
                    wscreen.world.put(wscreen.bros[x][i], x, i);
                }
            }
        }
        else if (y == targetY) {
            if (targetX < x) {
                int temp = targetX;
                targetX = x;
                x = temp;
            }
            for (int i = x; i <= targetX; i++) {
                if (wscreen.bros[i][y].getClass() == Wizard.class) {
                    wscreen.bros[i][y] = new Floor(wscreen.world);
                    wscreen.world.put(wscreen.bros[i][y], i, y);
                }
            } 
        }
        else {
            if (Math.abs(x - targetX) > Math.abs(y - targetY)) {
                if (targetX < x) {
                    int temp = targetX;
                    targetX = x;
                    x = temp;
                    temp = targetY;
                    targetY = y;
                    y = temp;
                }
                for (int i = x; i <= targetX; i++){
                    int j = (int)(((double)i - (double)x)*((double)targetY - (double)y)/((double)targetX - (double)x) + (double)y);
                    if (wscreen.bros[i][j].getClass() == Wizard.class) {
                        wscreen.bros[i][j] = new Floor(wscreen.world);
                        wscreen.world.put(wscreen.bros[i][j], i, j);
                    }
                }
            }
            else {
                if (targetY < y) {
                    int temp = targetX;
                    targetX = x;
                    x = temp;
                    temp = targetY;
                    targetY = y;
                    y = temp;
                }
                for (int j = y; j <= targetY; j++){
                    int i = (int)(((double)j - (double)y)*((double)targetX - (double)x)/((double)targetY - (double)y) + (double)x);
                    if (wscreen.bros[i][j].getClass() == Wizard.class) {
                        wscreen.bros[i][j] = new Floor(wscreen.world);
                        wscreen.world.put(wscreen.bros[i][j], i, j);
                    }
                }
            }
        }
    }

    private void attack(int x, int y) {
        Calabash enemy = (Calabash)wscreen.bros[x][y];
        enemy.hurt(4);
    }

    public void run() {
        while (this.isAlive()) {
            try {
                wscreen.waitForAvailable();
                if (isReadyToClear()) {
                    this.clearMagic();
                }
                if (isReadyToAtttack()) {
                    this.getTarget();
                    if (target != -1) {
                        this.attack();
                    }
                    magicTimer = attackSpeed;
                }
                else {
                    magicTimer--;
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
        wscreen.snakes[this.getIndex()] = null;
    }
    
}