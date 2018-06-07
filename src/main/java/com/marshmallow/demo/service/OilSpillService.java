package com.marshmallow.demo.service;

import com.marshmallow.demo.dto.CoordinateDto;
import com.marshmallow.demo.dto.OilSpillInputDto;
import com.marshmallow.demo.dto.OilSpillOutputDto;
import com.marshmallow.demo.exception.CoordinateOutOfBoundsException;

import java.util.List;

public class OilSpillService {

    public static OilSpillOutputDto cleanOilSpill(OilSpillInputDto oilSpillInput) {
        CoordinateDto areaSize = oilSpillInput.getAreaSize();
        boolean[][] grid = buildGrid(oilSpillInput.getOilPatches(), areaSize.getX(), areaSize.getY());
        return navigateGrid(grid, oilSpillInput.getStartingPosition(), oilSpillInput.getNavigationInstructions());

    }

    private static  boolean[][] buildGrid(List<CoordinateDto> coordinates, int gridX, int gridY) {
        boolean[][] grid = new boolean[gridX][gridY];
        for(CoordinateDto coordinate : coordinates) {
            try {
                grid[coordinate.getX()][coordinate.getY()] = true;
            } catch (IndexOutOfBoundsException e) {
                String coordinateString = "(" + coordinate.getX() + "," + coordinate.getY() + ")";
                throw new CoordinateOutOfBoundsException("oil patch: " + coordinateString + " is out of bounds of the sea grid");

            }
        }

        return grid;
    }

    private static OilSpillOutputDto navigateGrid(boolean[][] grid, CoordinateDto startingPoint, String instructions) {
        Sea sea = new Sea(grid, startingPoint.getX(), startingPoint.getY());
        sea.clean(instructions);
        OilSpillOutputDto oilSpillOutput = new OilSpillOutputDto();
        oilSpillOutput.setFinalPosition(buildCoordinateDto(sea.getCurrentX(), sea.getCurrentY()));
        oilSpillOutput.setPatchesCleaned(sea.getPatchesCleaned());
        return oilSpillOutput;
    }

    private static CoordinateDto buildCoordinateDto(int x, int y) {
        CoordinateDto coordinateDto = new CoordinateDto();
        coordinateDto.setX(x);
        coordinateDto.setY(y);
        return coordinateDto;
    }
}
