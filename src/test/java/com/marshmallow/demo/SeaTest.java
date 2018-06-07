package com.marshmallow.demo;


import com.marshmallow.demo.exception.CoordinateOutOfBoundsException;
import com.marshmallow.demo.service.Sea;
import org.junit.Assert;
import org.junit.Test;

public class SeaTest {

    private Sea initializeSimpleSea() {
        boolean[][] grid = new boolean[10][10];
        grid[2][2] = true;
        return new Sea(grid, 1,2);
    }

    private int[] getCurrentPositionAsArray(Sea sea) {
        int[] position = new int[2];
        position[0] = sea.getCurrentX();
        position[1] = sea.getCurrentY();
        return position;
    }

    @Test
    public void testNavigation() {
        Sea sea = initializeSimpleSea();
        sea.clean("N");
        int[] expectedNorth = {1, 3};
        Assert.assertArrayEquals(getCurrentPositionAsArray(sea), expectedNorth);

        sea.clean("E");
        int[] expectedEast = {2, 3};
        Assert.assertArrayEquals(getCurrentPositionAsArray(sea), expectedEast);

        sea.clean("S");
        int[] expectedSouth = {2, 2};
        Assert.assertArrayEquals(getCurrentPositionAsArray(sea), expectedSouth);

        sea.clean("W");
        int[] expectedWest = {1, 2};
        Assert.assertArrayEquals(getCurrentPositionAsArray(sea), expectedWest);
    }

    @Test
    public void testMultipleNavigationsInOneClean() {
        Sea sea = initializeSimpleSea();
        sea.clean("NESW");
        int[] expectedFinalPosition = {1,2};
        Assert.assertArrayEquals(getCurrentPositionAsArray(sea), expectedFinalPosition);
    }

    @Test
    public void testEmptyNavigationStaysStill() {
        Sea sea = initializeSimpleSea();
        sea.clean("");
        int[] expectedFinalPosition = {1,2};
        Assert.assertArrayEquals(getCurrentPositionAsArray(sea), expectedFinalPosition);
        Assert.assertEquals(sea.getPatchesCleaned(), 0);
    }

    @Test(expected = CoordinateOutOfBoundsException.class)
    public void testNavigationOutOfBoundsThrowsException() {
        Sea sea = initializeSimpleSea();
        sea.clean("NNNNNNNNNNNNNNNNNN");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullNavigationThrowsException() {
        Sea sea = initializeSimpleSea();
        sea.clean(null);
    }

    @Test
    public void testCleanPatch() {
        Sea sea = initializeSimpleSea();
        sea.clean("E");
        Assert.assertEquals(sea.getPatchesCleaned(),1);
    }

    @Test
    public void testNavigationOverPatchTwiceCleansOnce() {
        Sea sea = initializeSimpleSea();
        sea.clean("EWE");
        Assert.assertEquals(sea.getPatchesCleaned(), 1);
    }

    @Test
    public void testCleanMultiplePatches() {
        boolean[][] grid = new boolean[5][5];
        grid[1][0] = true;
        grid[2][2] = true;
        grid[2][3] = true;
        Sea sea = new Sea(grid, 1, 2);
        sea.clean("SSENNN");
        Assert.assertEquals(sea.getPatchesCleaned(), 3);

    }
}
