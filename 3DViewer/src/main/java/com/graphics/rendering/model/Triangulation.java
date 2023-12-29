package com.graphics.rendering.model;

import com.graphics.rendering.math.AffineTransformation;
import com.graphics.rendering.math.vector.Vector3D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Triangulation {
    public static ArrayList<Triangle> triangulate(Model mesh) {
        ArrayList<Triangle> triangles = new ArrayList<>();
        ArrayList<Vector3D> affineVertex = new ArrayList<>(affineСoordinates(mesh.getVertices()));

        for (int i = 0; i < mesh.getPolygons().size(); i++) {
            int indexVertexPolygon = 1; // индекс углов полигона

            for (int j = 2; j < mesh.getPolygons().get(i).getVertexIndices().size(); j++) {
                Vector3D p1 = affineVertex.get(mesh.getPolygons().get(i).getVertexIndices().get(0));
                Vector3D p2 = affineVertex.get(mesh.getPolygons().get(i).getVertexIndices().get(indexVertexPolygon));
                Vector3D p3 = affineVertex.get(mesh.getPolygons().get(i).getVertexIndices().get(indexVertexPolygon + 1));


                ArrayList<Vector3D> points = new ArrayList<>();
                points.add(p1);
                points.add(p2);
                points.add(p3);


                Collections.sort(points, new Comparator<Vector3D>() {
                    @Override
                    public int compare(Vector3D o1, Vector3D o2) {
                        if (o1.getY() > o2.getY())
                            return -1;
                        else if (o1.getY() < o2.getY())
                            return 1;
                        else
                            return 0;
                    }
                });
                p1 = points.get(0);
                p2 = points.get(1);
                p3 = points.get(2);
                Triangle triangle = new Triangle(p1, p2, p3);
                triangles.add(triangle);
                indexVertexPolygon++;
            }
        }

        return triangles;
    }
    public static ArrayList<Vector3D> affineСoordinates(ArrayList<Vector3D> vertices){
        ArrayList<Vector3D> vertTransition = new ArrayList<>();
        for(int i = 0; i < vertices.size(); i++){
            Vector3D p11 = AffineTransformation.scale(1,1,1,vertices.get(i));
            Vector3D p12 = AffineTransformation.rotate(0,0,0,p11);
            Vector3D p13 = AffineTransformation.parallelTranslation(0,0,0,p12);
            vertTransition.add(p13);
        }
        return vertTransition;
    }
}
