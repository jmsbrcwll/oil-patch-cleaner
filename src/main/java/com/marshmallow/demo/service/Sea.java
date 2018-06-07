package com.marshmallow.demo.service;


import com.marshmallow.demo.exception.CoordinateOutOfBoundsException;

public class Sea {

    private boolean[][] grid;

    private int currentX;

    private int currentY;

    private int patchesCleaned;

    private static final String NORTH = "N";
    private static final String EAST = "E";
    private static final String SOUTH = "S";
    private static final String WEST = "W";

    public Sea(boolean[][] grid, int currentX, int currentY) {
        this.grid = grid;
        this.currentX = currentX;
        this.currentY = currentY;
        patchesCleaned = 0;
    }

    public void clean(String instructions) {
        if (instructions == null) {
            throw new IllegalArgumentException("instructions cannot be null");
        }

        if (instructions.isEmpty()) {
            return;
        }

        for (String instruction : instructions.split("")) {
            navigate(instruction);
            if (onOilSpill()) {
                cleanSpill();
            }
        }
    }

    private void navigate(String instruction) {
        switch (instruction) {
            case NORTH:
                currentY++;
                break;
            case EAST:
                currentX++;
                break;
            case SOUTH:
                currentY--;
                break;
            case WEST:
                currentX--;
                break;
            default:
                throw new IllegalArgumentException("character " + instruction + " is invalid direction");
        }

        validateNavigation();
    }

    private void validateNavigation() {
        try {
            boolean peek = grid[currentX][currentY];
        } catch (IndexOutOfBoundsException e) {
            String coordinate = "(" + currentX + "," + currentY + ")";
            throw new CoordinateOutOfBoundsException("navigation resulted in out-of-bounds co-ordinate: " +  coordinate);
        }
    }

    private boolean onOilSpill() {
        return grid[currentX][currentY];
    }

    private void cleanSpill() {
        grid[currentX][currentY] = false;
        patchesCleaned++;
    }

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }
    public int getPatchesCleaned() {
        return patchesCleaned;
    }
}
