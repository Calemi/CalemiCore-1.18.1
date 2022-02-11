package com.tm.calemicore.util.helper.shape;

import com.tm.calemicore.util.Location;
import net.minecraft.core.Direction;

import java.util.ArrayList;

public class ShapeFlatCube extends ShapeBase {

    public ShapeFlatCube(Location origin, Direction face, int radius) {

        ArrayList<Location> shapeLocations = new ArrayList<>();

        int xRad = radius;
        int yRad = radius;
        int zRad = radius;

        if (face == Direction.UP || face == Direction.DOWN) {
            yRad = 0;
        }

        else if (face == Direction.NORTH || face == Direction.SOUTH) {
            zRad = 0;
        }

        else if (face == Direction.EAST || face == Direction.WEST) {
            xRad = 0;
        }

        this.shapeLocations = new ShapeCubeFromRadius(origin, xRad, yRad, zRad).getShapeLocations();
    }
}
