package com.graphics.rendering.model;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Triangulation extends Model  {
    public static void main(String[] args) {



    }




    public static ArrayList<Triangle> triangulate(List<Point2D.Double> polygon) {
        ArrayList<Triangle> triangles = new ArrayList<>();
        int indexVertexPolygon = 1; // индекс углов полигона

        for (int i = 2; i < polygon.size(); i++){

            Point2D.Double p1 = polygon.get(0);
            Point2D.Double p2 = polygon.get(indexVertexPolygon);
            Point2D.Double p3 = polygon.get(indexVertexPolygon + 1);

            Triangle triangle = new Triangle(p1, p2, p3);
            triangles.add(triangle);
            indexVertexPolygon++;
        }

        return triangles;
    }




}

