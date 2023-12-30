package com.graphics.rendering.model;

import com.graphics.rendering.math.vector.Vector3D;
import javafx.scene.Node;

public class Triangle extends Node {
    Vector3D p1;
    Vector3D p2;
    Vector3D p3;

    public Triangle(Vector3D p1, Vector3D p2, Vector3D p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public Vector3D getP1() {
        return p1;
    }

    public void setP1(Vector3D p1) {
        this.p1 = p1;
    }

    public Vector3D getP2() {
        return p2;
    }

    public void setP2(Vector3D p2) {
        this.p2 = p2;
    }

    public Vector3D getP3() {
        return p3;
    }

    public void setP3(Vector3D p3) {
        this.p3 = p3;
    }
}
