package com.graphics.rendering.render_engine;

import com.graphics.rendering.math.matrix.Matrix4D;
import com.graphics.rendering.math.vector.Vector3D;
import com.graphics.rendering.model.Model;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import javax.vecmath.Point2f;
import java.util.ArrayList;
import java.util.HashMap;

import static com.graphics.rendering.render_engine.GraphicConveyor.*;

public class RenderEngine {

    public static void render(
            final GraphicsContext graphicsContext,
            final Camera camera,
            final HashMap<String, Model> meshes,
            final int width,
            final int height) {
        Matrix4D modelMatrix = translateRotateScale();
        Matrix4D viewMatrix = camera.getViewMatrix();
        Matrix4D projectionMatrix = camera.getProjectionMatrix();

        Matrix4D projectionViewMatrix = projectionMatrix.multiplyMatrix(viewMatrix);
        Matrix4D projectionViewModelMatrix = projectionViewMatrix.multiplyMatrix(modelMatrix);
        for (Model mesh : meshes.values()) {
            final int nPolygons = mesh.getPolygons().size();
            for (int polygonInd = 0; polygonInd < nPolygons; ++polygonInd) {
                final int nVerticesInPolygon = mesh.getPolygons().get(polygonInd).getVertexIndices().size();

                ArrayList<Point2f> resultPoints = new ArrayList<>();
                for (int vertexInPolygonInd = 0; vertexInPolygonInd < nVerticesInPolygon; ++vertexInPolygonInd) {
                    Vector3D vertex = mesh.getVertices().get(mesh.getPolygons().get(polygonInd).getVertexIndices().get(vertexInPolygonInd));

                    Vector3D vertexVecmath = new Vector3D(vertex.getX(), vertex.getY(), vertex.getZ());

                    Point2f resultPoint = vertexToPoint(projectionViewModelMatrix.multiplyVectorDivW(vertexVecmath), width, height);
                    resultPoints.add(resultPoint);
                }
//                renderAxes(graphicsContext, projectionViewModelMatrix, width, height);
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

    private static void renderAxes(final GraphicsContext graphicsContext, final Matrix4D projectionViewModelMatrix, final int width, final int height) {
        // Ось X (красная)
        Line xAxis = new Line(0, 10000, 0, 0);
        xAxis.setStroke(Color.RED);
        drawLine(graphicsContext, projectionViewModelMatrix, xAxis, width, height);

        // Ось Y (зеленая)
        Line yAxis = new Line(0, 0, 1000000, 0);
        yAxis.setStroke(Color.GREEN);
        drawLine(graphicsContext, projectionViewModelMatrix, yAxis, width, height);

        // Ось Z (синяя)
        Line zAxis = new Line(0, 0, 0, 100000);
        zAxis.setStroke(Color.BLUE);
        zAxis.setTranslateX(100);
        drawLine(graphicsContext, projectionViewModelMatrix, zAxis, width, height);
    }

    private static void drawLine(final GraphicsContext graphicsContext, final Matrix4D projectionViewModelMatrix, final Line line, final int width, final int height) {
        Vector3D start = new Vector3D((float) line.getStartX(), (float) line.getStartY(), 0);
        Vector3D end = new Vector3D((float) line.getEndX(), (float) line.getEndY(), 0);

        Point2f startPoint = vertexToPoint(projectionViewModelMatrix.multiplyVectorDivW(start), width, height);
        Point2f endPoint = vertexToPoint(projectionViewModelMatrix.multiplyVectorDivW(end), width, height);

        line.setStartX(startPoint.x);
        line.setStartY(startPoint.y);
        line.setEndX(endPoint.x);
        line.setEndY(endPoint.y);

        graphicsContext.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
    }
}