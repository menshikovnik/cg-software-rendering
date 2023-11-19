package com.graphics.rendering.render_engine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.graphics.rendering.math.Vector3f;
import javafx.scene.canvas.GraphicsContext;
import javax.vecmath.*;
import com.graphics.rendering.model.Model;
import static com.graphics.rendering.render_engine.GraphicConveyor.*;

//Тут короче я artb22 потом переделаю надо добавить вычисление матрицы: модели, видовой, проекционной. Ещё с сеткой поработать
public class RenderEngine {

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final List<Model> meshes,
            final int width,
            final int height)
    {
        Matrix4f modelMatrix = rotateScaleTranslate();
        Matrix4f viewMatrix = camera.getViewMatrix();
        Matrix4f projectionMatrix = camera.getProjectionMatrix();

        Matrix4f modelViewProjectionMatrix = new Matrix4f(modelMatrix);
        modelViewProjectionMatrix.mul(viewMatrix);
        modelViewProjectionMatrix.mul(projectionMatrix);
        for (int i = 0;i < meshes.size();i++) {
            final int nPolygons = meshes.get(i).polygons.size();
            for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
                final int nVerticesInPolygon = meshes.get(i).polygons.get(polygonInd).getVertexIndices().size();

                ArrayList<Point2f> resultPoints = new ArrayList<>();
                for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                    Vector3f vertex = meshes.get(i).vertices.get(meshes.get(i).polygons.get(polygonInd).getVertexIndices().get(vertexInPolygonInd));

                    javax.vecmath.Vector3f vertexVecmath = new javax.vecmath.Vector3f(vertex.x, vertex.y, vertex.z);

                    Point2f resultPoint = vertexToPoint(multiplyMatrix4ByVector3(modelViewProjectionMatrix, vertexVecmath), width, height);
                    resultPoints.add(resultPoint);
                }

                for (int vertexInPolygonInd = 1; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                    graphicsContext.strokeLine(
                            resultPoints.get(vertexInPolygonInd - 1).x,
                            resultPoints.get(vertexInPolygonInd - 1).y,
                            resultPoints.get(vertexInPolygonInd).x,
                            resultPoints.get(vertexInPolygonInd).y);
                }

                if (nVerticesInPolygon > 0)
                    graphicsContext.strokeLine(
                            resultPoints.get(nVerticesInPolygon - 1).x,
                            resultPoints.get(nVerticesInPolygon - 1).y,
                            resultPoints.get(0).x,
                            resultPoints.get(0).y);
            }
        }
    }
}