package com.tm.calemicore.util.helper.shape;

import com.tm.calemicore.util.Location;

import java.util.ArrayList;

public class ShapeCubeFromCorners extends ShapeBase {

    public ShapeCubeFromCorners(Location corner1, Location corner2) {

        ArrayList<Location> shapeLocations = new ArrayList<>();

        for (int x = Math.min(corner1.x, corner2.x); x <= Math.max(corner1.x, corner2.x); x++) {

            for (int y = Math.min(corner1.y, corner2.y); y <= Math.max(corner1.y, corner2.y); y++) {

                for (int z = Math.min(corner1.z, corner2.z); z <= Math.max(corner1.z, corner2.z); z++) {
                    shapeLocations.add(new Location(corner1.level, x, y, z));
                }
            }
        }

        this.shapeLocations = shapeLocations;
    }
}
