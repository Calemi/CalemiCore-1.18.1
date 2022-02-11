package com.tm.calemicore.util.helper.shape;

import com.tm.calemicore.util.Location;

import java.util.ArrayList;

public class ShapeCubeFromRadius extends ShapeBase {

    public ShapeCubeFromRadius(Location origin, int xRad, int yRad, int zRad) {

        ArrayList<Location> shapeLocations = new ArrayList<>();

        for (int x = -xRad; x <= xRad; x++) {

            for (int y = -yRad; y <= yRad; y++) {

                for (int z = -zRad; z <= zRad; z++) {
                    Location nextLocation = new Location(origin.level, origin.x + x, origin.y + y, origin.z + z);
                    shapeLocations.add(nextLocation);
                }
            }
        }

        this.shapeLocations = shapeLocations;
    }
}
