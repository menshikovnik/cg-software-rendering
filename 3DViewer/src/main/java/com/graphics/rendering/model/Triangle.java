package com.graphics.rendering.model;

import com.graphics.rendering.math.Vector3f;
import javafx.scene.Node;

public class Triangle extends Node {
    Vector3f p1;
    Vector3f p2;
    Vector3f p3;

    public Triangle(Vector3f p1, Vector3f p2, Vector3f p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }
}
