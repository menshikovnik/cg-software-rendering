package com.graphics.rendering.model;
import com.graphics.rendering.math.Vector2f;
import com.graphics.rendering.math.Vector3f;
import com.graphics.rendering.math.vector.Vector2D;
import com.graphics.rendering.math.vector.Vector3D;

import java.util.*;
//Тут изменить на мой матан
public class Model {

    public ArrayList<Vector3f> vertices = new ArrayList<>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<>();
    public ArrayList<Vector3f> normals = new ArrayList<>();
    public ArrayList<Polygon> polygons = new ArrayList<>();
}
