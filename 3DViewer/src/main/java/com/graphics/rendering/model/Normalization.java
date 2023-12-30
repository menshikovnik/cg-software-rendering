package com.graphics.rendering.model;
import com.graphics.rendering.math.vector.Vector3D;
import java.util.ArrayList;
import java.util.List;
public class Normalization {
    /**
     * @param vertices all model vertices list
     * @param normals all model normals list
     * @param polygons all model polygons list
     */
    public static void calculateModelNormals(List<Vector3D> vertices, List<Vector3D> normals,
                                             List<Polygon> polygons) {
        for (int i = 0; i < vertices.size(); i++) {
            normals.set(i, vertexNormal(i, vertices, polygons));
        }
    }

    /**
     * @param polygon  input polygon
     * @param vertices all model vertices list
     * @return normal vector to input polygon
     */
    public static Vector3D polygonNormal(Polygon polygon, List<Vector3D> vertices) {
        List<Integer> vertexIndices = polygon.getVertexIndices();

        Vector3D vertex1 = vertices.get(vertexIndices.get(0));
        Vector3D vertex2 = vertices.get(vertexIndices.get(1));
        Vector3D vertex3 = vertices.get(vertexIndices.get(2));

        //vectors in the polygon flDt
        Vector3D vector1 = new Vector3D(vertex2.getX() - vertex1.getX(), vertex2.getY() - vertex1.getY(), vertex2.getZ() - vertex1.getZ()).normalize();
        Vector3D vector2 = new Vector3D(vertex3.getX() - vertex1.getX(), vertex3.getY() - vertex1.getY(), vertex3.getZ() - vertex1.getZ()).normalize();

        return vector1.vectorMultiply(vector2).normalize();
    }

    /**
     * @param vertexIndex vertex index
     * @param vertices    all model vertices list
     * @param polygons    all model polygons list
     * @return normal vector to vertex relative to model
     */
    public static Vector3D vertexNormal(Integer vertexIndex, List<Vector3D> vertices, List<Polygon> polygons) {
        // Вариант, куда передают все вершины модели
        List<Polygon> polygonsSurroundingVertex = selectPolygonsSurroundingVertex(
                vertexIndex, vertices, polygons
        );

        Vector3D sumVector = new Vector3D();
        for (Polygon polygon : polygonsSurroundingVertex) {
            sumVector.sumVector(polygonNormal(polygon, vertices).normalize());
        }

        // return average vector
        return sumVector.divScalar(polygonsSurroundingVertex.size()).normalize();
    }

    /**
     * Support method for "normalToVertex"
     *
     * @param vertexIndex vertex index
     * @param vertices    all model vertices list
     * @param polygons    all model polygons list
     * @return polygons which bordering input vertex
     */
    protected static List<Polygon> selectPolygonsSurroundingVertex(Integer vertexIndex, List<Vector3D> vertices,
                                                                   List<Polygon> polygons) {
        List<Polygon> polygonsSurroundingVertex = new ArrayList<>();

        for (Polygon polygon : polygons) {
            for (Integer index : polygon.getVertexIndices()) {
                if (vertices.get(index).equals(vertices.get(vertexIndex))) {
                    polygonsSurroundingVertex.add(polygon);
                    break;
                }
            }
        }

        return polygonsSurroundingVertex;
    }
}

