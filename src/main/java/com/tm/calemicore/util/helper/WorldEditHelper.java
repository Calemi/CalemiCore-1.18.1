package com.tm.calemicore.util.helper;

import com.tm.calemicore.util.Location;
import com.tm.calemicore.util.helper.shape.ShapeBase;

import java.util.ArrayList;

/**
 * Use this class to help generate complex formations.
 */
public class WorldEditHelper {

    /**
     * @param shape The shape to construct.
     * @return A list of Locations required to build the shape.
     */
    public static ArrayList<Location> selectShape(ShapeBase shape) {
        return shape.getShapeLocations();
    }
}
