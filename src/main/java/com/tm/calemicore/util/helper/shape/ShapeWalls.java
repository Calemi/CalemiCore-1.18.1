package com.tm.calemicore.util.helper.shape;

import com.tm.calemicore.util.Location;

import java.util.ArrayList;

public class ShapeWalls extends ShapeBase {

    /**
     * Creates a walls Shape.
     * @param origin The origin Location of the walls.
     * @param radius The radius of the walls.
     * @param y1 The first y coordinate of the walls. Interchangeable. Used to determine height.
     * @param y2 The second y coordinate of the walls. Interchangeable. Used to determine height.
     */
    public ShapeWalls(Location origin, int radius, int y1, int y2) {

        ArrayList<Location> shapeLocations = new ArrayList<>();

        int minY = Math.min(y1, y2);
        int maxY = Math.max(y1, y2);

        if (radius < 0) return;

        else if (radius == 0) {

            for (int y = minY; y <= maxY; y++) {
                shapeLocations.add(new Location(origin.level, origin.x, y, origin.z));
            }
        }

        else {

            for (int i = -radius; i <= radius; i++) {

                for (int y = minY; y <= maxY; y++) {
                    shapeLocations.add(new Location(origin.level, origin.x + radius, y, origin.z + i));
                    shapeLocations.add(new Location(origin.level, origin.x - radius, y, origin.z + i));
                }
            }

            for (int i = -radius + 1; i <= radius - 1; i++) {

                for (int y = minY; y <= maxY; y++) {
                    shapeLocations.add(new Location(origin.level, origin.x + i, y, origin.z + radius));
                    shapeLocations.add(new Location(origin.level, origin.x + i, y, origin.z - radius));
                }
            }
        }

        addShapeLocations(shapeLocations);
    }
}
