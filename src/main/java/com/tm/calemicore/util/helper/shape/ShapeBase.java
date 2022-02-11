package com.tm.calemicore.util.helper.shape;

import com.tm.calemicore.util.Location;

import java.util.ArrayList;

/**
 * Base class for a Shape.
 */
public abstract class ShapeBase {

    /**
     * A list of Locations required to build the Shape.
     */
    private ArrayList<Location> shapeLocations = new ArrayList<>();

    /**
     * @return A list of Locations required to build the Shape.
     */
    public ArrayList<Location> getShapeLocations() {
        return shapeLocations;
    }

    /**
     * Adds a list of Shape Locations to the base one.
     * @param shapeLocations The list of Shape Locations to add.
     */
    public void addShapeLocations(ArrayList<Location> shapeLocations) {
        getShapeLocations().addAll(shapeLocations);
    }
}
