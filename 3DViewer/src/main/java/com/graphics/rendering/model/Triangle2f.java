package com.graphics.rendering.model;

import javafx.scene.Node;

import javax.vecmath.Point2f;

public class Triangle2f extends Node {
    Point2f p1;
    Point2f p2;
    Point2f p3;

    public Triangle2f(Point2f p1, Point2f p2, Point2f p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public Point2f getP1() {
        return p1;
    }

    public void setP1(Point2f p1) {
        this.p1 = p1;
    }

    public Point2f getP2() {
        return p2;
    }

    public void setP2(Point2f p2) {
        this.p2 = p2;
    }

    public Point2f getP3() {
        return p3;
    }

    public void setP3(Point2f p3) {
        this.p3 = p3;
    }
}
