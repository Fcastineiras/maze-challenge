package com.mazeWay.model.node;

public enum Direction {
    UP("DOWN"), DOWN("UP"), LEFT("RIGHT"), RIGHT("LEFT");

    public final String opposite;

    private Direction(String opposite) {
        this.opposite = opposite;
    }

    public Direction getOpposite() {
        return Direction.valueOf(this.opposite);
    }
}
