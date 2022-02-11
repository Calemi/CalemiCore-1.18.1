package com.tm.calemicore.util.helper.shape;

import com.tm.calemicore.util.Location;

import java.util.ArrayList;

public class ShapeCube extends ShapeBase {

    /**
     * Creates a cube Shape.
     * @param corner1 The first corner Location of the cube.
     * @param corner2 The second corner Location of the cube.
     */
    public ShapeCube(Location corner1, Location corner2) {

        ArrayList<Location> shapeLocations = new ArrayList<>();

        for (int x = Math.min(corner1.x, corner2.x); x <= Math.max(corner1.x, corner2.x); x++) {

            for (int y = Math.min(corner1.y, corner2.y); y <= Math.max(corner1.y, corner2.y); y++) {

                for (int z = Math.min(corner1.z, corner2.z); z <= Math.max(corner1.z, corner2.z); z++) {
                    shapeLocations.add(new Location(corner1.level, x, y, z));
                }
            }
        }

        addShapeLocations(shapeLocations);
    }

    /**
     * Creates a hollow cube Shape.
     * @param origin The origin Location of the circle.
     * @param xRadius The x radius of the cube.
     * @param yRadius The y radius of the cube.
     * @param zRadius The z radius of the cube.
     */
    public ShapeCube(Location origin, int xRadius, int yRadius, int zRadius) {

        ArrayList<Location> shapeLocations = new ArrayList<>();

        for (int x = -xRadius; x <= xRadius; x++) {

            for (int y = -yRadius; y <= yRadius; y++) {

                for (int z = -zRadius; z <= zRadius; z++) {
                    Location nextLocation = new Location(origin.level, origin.x + x, origin.y + y, origin.z + z);
                    shapeLocations.add(nextLocation);
                }
            }
        }

        addShapeLocations(shapeLocations);
    }
}
