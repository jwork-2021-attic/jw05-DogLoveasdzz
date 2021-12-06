package com.anish.calabashbros;

import java.awt.Color;

public class Creature extends Thing {

    private int health;
    private int index;

    Creature(Color color, char glyph, World world, int index, int health) {
        super(color, glyph, world);
        this.health = health;
        this.index = index;
    }

    public void moveTo(int xPos, int yPos) {
        this.world.put(this, xPos, yPos);
    }

    public void hurt(int damage) {
        health -= damage;
    }

    protected boolean isAlive() {
        if (health > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public int getIndex() {
        return index;
    }
}
