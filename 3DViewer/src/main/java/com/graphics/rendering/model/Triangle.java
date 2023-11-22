package com.graphics.rendering.model;

import javafx.scene.Node;

import java.awt.geom.Point2D;

public class Triangle extends Node {
    Point2D.Double p1, p2, p3;

    public Triangle(Point2D.Double p1, Point2D.Double p2, Point2D.Double p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }
}
