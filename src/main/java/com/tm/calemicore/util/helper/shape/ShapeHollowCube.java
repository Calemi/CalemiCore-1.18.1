package com.tm.calemicore.util.helper.shape;

import com.tm.calemicore.util.Location;
import java.util.ArrayList;

public class ShapeHollowCube extends ShapeBase {

    /**
     * Creates a hollow cube Shape.
     * @param corner1 The first corner Location of the hollow cube.
     * @param corner2 The second corner Location of the hollow cube.
     */
    public ShapeHollowCube(Location corner1, Location corner2) {

        ArrayList<Location> shapeLocations = new ArrayList<>();

        //X Walls
        for (int x = Math.min(corner1.x, corner2.x); x <= Math.max(corner1.x, corner2.x); x++) {

            for (int y = Math.min(corner1.y, corner2.y); y <= Math.max(corner1.y, corner2.y); y++) {
                shapeLocations.add(new Location(corner1.level, x, y, Math.min(corner1.z, corner2.z)));
                shapeLocations.add(new Location(corner1.level, x, y, Math.max(corner1.z, corner2.z)));
            }
        }

        //Z Walls
        for (int z = Math.min(corner1.z, corner2.z) + 1; z <= Math.max(corner1.z, corner2.z) - 1; z++) {

            for (int y = Math.min(corner1.y, corner2.y); y <= Math.max(corner1.y, corner2.y); y++) {
                shapeLocations.add(new Location(corner1.level, Math.min(corner1.x, corner2.x), y, z));
                shapeLocations.add(new Location(corner1.level, Math.max(corner1.x, corner2.x), y, z));
            }
        }

        //Top and Bottom Walls
        for (int x = Math.min(corner1.x, corner2.x) + 1; x <= Math.max(corner1.x, corner2.x) - 1; x++) {

            for (int z = Math.min(corner1.z, corner2.z) + 1; z <= Math.max(corner1.z, corner2.z) - 1; z++) {
                shapeLocations.add(new Location(corner1.level, x, Math.min(corner1.y, corner2.y), z));
                shapeLocations.add(new Location(corner1.level, x, Math.max(corner1.y, corner2.y), z));
            }
        }

        addShapeLocations(shapeLocations);
    }
}
