package com.mazeWay.model.node;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Node extends Element {

    private final Direction from;
    private final int repeated;

    public Node(int[] coordinates, char character, Direction from, int repeated) {
        super(coordinates, character);
        this.from = from;
        this.repeated = repeated;
    }

    @JsonIgnore
    public Direction getFrom() {
        return from;
    }

    @JsonIgnore
    public int getRepeated() {
        return repeated;
    }

}
