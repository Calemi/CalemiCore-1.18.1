package com.tm.calemicore.util.helper.shape;

import com.tm.calemicore.util.Location;

import java.util.ArrayList;

public class ShapeSphere extends ShapeBase {

    /**
     * Creates a sphere Shape.
     * @param origin The origin Location of the sphere.
     * @param edge The edge Location of the sphere.
     * @param isHollow If true, the sphere will be hollow on the inside.
     * @param thickness The thickness of the sphere's edge. Only used for hollow sphere.
     */
    public ShapeSphere(Location origin, Location edge, boolean isHollow, int thickness) {

        ArrayList<Location> shapeLocations = new ArrayList<>();

        int radius = (int) origin.getDistance(edge);

        for (int x = -radius; x <= radius; x++) {

            for (int y = -radius; y <= radius; y++) {

                for (int z = -radius; z <= radius; z++) {

                    int rx = origin.x + x;
                    int ry = origin.y + y;
                    int rz = origin.z + z;

                    Location nextLoc = new Location(origin.level, rx, ry, rz);

                    if (origin.getDistance(nextLoc) - 0.2F <= radius) {

                        if (!isHollow || origin.getDistance(nextLoc) - 0.2F >= radius - thickness) {
                            shapeLocations.add(nextLoc);
                        }
                    }
                }
            }
        }

        addShapeLocations(shapeLocations);
    }
}
