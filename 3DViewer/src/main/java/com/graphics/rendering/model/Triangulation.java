package com.graphics.rendering.model;

import com.graphics.rendering.math.AffineTransformation;
import com.graphics.rendering.math.matrix.Matrix4D;
import com.graphics.rendering.math.vector.Vector3D;
import javafx.scene.Node;

import javax.vecmath.Point2f;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.graphics.rendering.render_engine.GraphicConveyor.vertexToPoint;

public class Triangulation {
    public static ArrayList<Triangle> triangulate(Model mesh) {
        ArrayList<Triangle> triangles = new ArrayList<>();

        for (int i = 0; i < mesh.getPolygons().size(); i++) {
            int indexVertexPolygon = 1; // индекс углов полигона

            for (int j = 2; j < mesh.getPolygons().get(i).getVertexIndices().size(); j++) {
                Vector3D p1 = mesh.getVertices().get(mesh.getPolygons().get(i).getVertexIndices().get(0));
                Vector3D p2 = mesh.getVertices().get(mesh.getPolygons().get(i).getVertexIndices().get(indexVertexPolygon));
                Vector3D p3 = mesh.getVertices().get(mesh.getPolygons().get(i).getVertexIndices().get(indexVertexPolygon + 1));
                Triangle triangle = new Triangle(p1, p2, p3);
                triangles.add(triangle);
                indexVertexPolygon++;
            }
        }

        return triangles;
    }
    public static ArrayList<Triangle2f> realCoordinates(Model mesh, Matrix4D projectionViewModelMatrix,int width,int height){
        ArrayList<Triangle> until = new ArrayList<>(triangulate(mesh));
        ArrayList<Triangle2f> after = new ArrayList<>();
        for(int i = 0; i < until.size();i++){
            Point2f p1 = vertexToPoint(projectionViewModelMatrix.multiplyVectorDivW(until.get(i).getP1()), width, height);
            Point2f p2 = vertexToPoint(projectionViewModelMatrix.multiplyVectorDivW(until.get(i).getP2()), width, height);
            Point2f p3 = vertexToPoint(projectionViewModelMatrix.multiplyVectorDivW(until.get(i).getP3()), width, height);
            Triangle2f triangle = new Triangle2f(p1,p2,p3);
            after.add(triangle);
        }
        return after;

    }
    public static ArrayList<Triangle2f> sortPoints(Model mesh,Matrix4D projection,int width,int height){
        ArrayList<Triangle2f> untilSort = new ArrayList<>(realCoordinates(mesh,projection,width,height));
        ArrayList<Triangle2f> afterSort = new ArrayList<>();
        for(int i = 0; i < untilSort.size();i++){
            ArrayList<Point2f> points = new ArrayList<>();
            points.add(untilSort.get(i).getP1());
            points.add(untilSort.get(i).getP2());
            points.add(untilSort.get(i).getP3());
            Collections.sort(points, new Comparator<Point2f>() {
                @Override
                public int compare(Point2f o1, Point2f o2) {
                    if (o1.y > o2.y)
                        return 1;
                    else if (o1.y < o2.y)
                        return -1;
                    else
                        return 0;
                }
            });
            Point2f p1 = points.get(0);
            Point2f p2 = points.get(1);
            Point2f p3 = points.get(2);

            Triangle2f triangle = new Triangle2f(p1, p2, p3);
            afterSort.add(triangle);
        }
        return afterSort;
    }

}
