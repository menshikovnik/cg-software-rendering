package com.graphics.rendering.render_engine;
import com.graphics.rendering.math.vector.Vector3D;
import com.graphics.rendering.model.Polygon;
import com.graphics.rendering.model.Triangle;

import java.util.ArrayList;


public class Triangulation extends Polygon {

    public static ArrayList<Triangle> triangulate(ArrayList<Polygon> polygons, ArrayList<Vector3D> vertices) {
        ArrayList<Triangle> triangles = new ArrayList<>();

        for (int i = 0; i < polygons.size(); i++) {
            int indexVertexPolygon = 1; // индекс углов полигона
            for (int j = 2; j < polygons.get(i).getVertexIndices().size(); j++) {
                Vector3D p1 = vertices.get(polygons.get(i).getVertexIndices().get(0));
                Vector3D p2 = vertices.get(polygons.get(i).getVertexIndices().get(indexVertexPolygon));
                Vector3D p3 = vertices.get(polygons.get(i).getVertexIndices().get(indexVertexPolygon + 1));
                Triangle triangle = new Triangle(p1, p2, p3);
                triangles.add(triangle);
                indexVertexPolygon++;
            }
        }

        return triangles;
    }


}
