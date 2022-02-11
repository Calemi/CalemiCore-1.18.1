package com.tm.calemicore.util.helper.shape;

import com.tm.calemicore.util.Location;

import java.util.ArrayList;

public class ShapeCylinder extends ShapeBase {

    /**
     * Creates a cylinder Shape.
     * @param origin The origin Location of the cylinder.
     * @param edge The edge Location of the cylinder.
     * @param isHollow If true, the cylinder will be hollow on the inside.
     * @param thickness The thickness of the cylinder's edge. Only used for hollow cylinder.
     */
    public ShapeCylinder(Location origin, Location edge, boolean isHollow, int thickness) {

        ArrayList<Location> shapeLocations = new ArrayList<>();

        int minY = Math.min(origin.y, edge.y);
        int maxY = Math.max(origin.y, edge.y);

        for (int y = minY; y <= maxY; y++) {
            shapeLocations.addAll(new ShapeCircle(new Location(origin.level, origin.x, y, origin.z), new Location(edge.level, edge.x, y, edge.z), isHollow, thickness).getShapeLocations());
        }

        addShapeLocations(shapeLocations);
    }
}
