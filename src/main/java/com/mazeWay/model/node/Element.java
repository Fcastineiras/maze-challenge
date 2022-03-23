package com.mazeWay.model.node;

import com.fasterxml.jackson.annotation.JsonIgnore;

import static com.mazeWay.model.MatrixData.X;
import static com.mazeWay.model.MatrixData.Y;

public class Element {

    private final int[] location;

    private final char character;

    public Element(int[] location, char character) {
        this.location = location;
        this.character = character;
    }

    public int[] getLocation() {
        return location;
    }
    @JsonIgnore
    public int getY() {
        return this.location[Y];
    }
    @JsonIgnore
    public int getX() {
        return this.location[X];
    }

    public char getChar() {
        return character;
    }
}
