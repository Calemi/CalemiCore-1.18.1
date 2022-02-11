package com.tm.calemicore.util.helper.shape;

import com.tm.calemicore.util.Location;

import java.util.ArrayList;

public abstract class ShapeBase {

    protected ArrayList<Location> shapeLocations;

    public ShapeBase() {}

    public ArrayList<Location> getShapeLocations() {
        return shapeLocations;
    }
}
