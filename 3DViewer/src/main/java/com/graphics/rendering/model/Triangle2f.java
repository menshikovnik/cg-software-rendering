package com.graphics.rendering.model;

import javafx.scene.Node;
import lombok.Data;

import javax.vecmath.Point2f;

@Data
public class Triangle2f extends Node {
    Point2f p1;
    Point2f p2;
    Point2f p3;

    public Triangle2f(Point2f p1, Point2f p2, Point2f p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }
}
