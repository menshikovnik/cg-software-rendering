package com.graphics.rendering.model;
import com.graphics.rendering.math.Vector2f;
import com.graphics.rendering.math.Vector3f;
import com.graphics.rendering.math.vector.Vector2D;
import com.graphics.rendering.math.vector.Vector3D;

import java.util.*;
// todo: Изменить на свой матан
public class Model {

    public ArrayList<Vector3D> vertices = new ArrayList<>();
    public ArrayList<Vector2D> textureVertices = new ArrayList<>();
    public ArrayList<Vector3D> normals = new ArrayList<>();
    public ArrayList<Polygon> polygons = new ArrayList<>();
}
