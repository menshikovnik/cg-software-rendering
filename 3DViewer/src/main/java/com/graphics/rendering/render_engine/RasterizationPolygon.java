package com.graphics.rendering.render_engine;

import com.graphics.rendering.math.matrix.Matrix4D;
import com.graphics.rendering.math.vector.Vector3D;
import com.graphics.rendering.model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import javax.vecmath.Point2f;
import java.util.ArrayList;
import java.util.HashMap;

import static com.graphics.rendering.model.Triangulation.sortPoints;
import static com.graphics.rendering.render_engine.GraphicConveyor.translateRotateScale;
import static com.graphics.rendering.render_engine.GraphicConveyor.vertexToPoint;

public class RasterizationPolygon {

    public static float wx1;
    public static float wx2;

    public static float dxLeft;
    public static float dxRight;

    public static void rasterization(Canvas canvas, GraphicsContext ctx, Model mesh, Camera camera, Color color) {
        Matrix4D projectionViewModelMatrix = projectionMatrix(camera);
        ArrayList<Triangle2f> listWithTriangles = new ArrayList<>(Triangulation.sortPoints(mesh,projectionViewModelMatrix, (int) canvas.getWidth(), (int) canvas.getHeight()));
        for (int i = 0; i < listWithTriangles.size(); i++) {
            rasterize(listWithTriangles.get(i), ctx, color);
        }
    }

    public static Matrix4D projectionMatrix(Camera camera) {
        Matrix4D modelMatrix = translateRotateScale();
        Matrix4D viewMatrix = camera.getViewMatrix();
        Matrix4D projectionMatrix = camera.getProjectionMatrix();

        Matrix4D projectionViewMatrix = projectionMatrix.multiplyMatrix(viewMatrix);
        Matrix4D projectionViewModelMatrix = projectionViewMatrix.multiplyMatrix(modelMatrix);
        return projectionViewModelMatrix;
    }

    public static void rasterize(Triangle2f triangle, GraphicsContext graphicsContext, Color color) {
        Point2f p1 = triangle.getP1();
        Point2f p2 = triangle.getP2();
        Point2f p3 = triangle.getP3();

        int x1 = (int) p1.x;
        int y1 = (int) p1.y;
        int x2 = (int) p2.x;
        int y2 = (int) p2.y;
        int x3 = (int) p3.x;
        int y3 = (int) p3.y;

        float dx12 = calculateSideXIncrement(x1, y1, x2, y2);
        float dx13 = calculateSideXIncrement(x1, y1, x3, y3);
        float dx23 = calculateSideXIncrement(x2, y2, x3, y3);

        computeBeforeUpperPart(dx12, dx13, x1);
        drawPart(y1, y2 - 1, graphicsContext, color);

        computeBeforeLowerPart(dx13, dx23, x1, y1, x2, y2);
        drawPart(y2, y3, graphicsContext, color);
    }


    public static void computeBeforeUpperPart(float dx12, float dx13, float wx) {
        wx1 = wx;
        wx2 = wx1;

        if (dx13 < dx12) {
            dxLeft = dx13;
            dxRight = dx12;
            return;
        }
        dxLeft = dx12;
        dxRight = dx13;
    }

    public static void computeBeforeLowerPart(float dx13, float dx23, int x1, int y1, int x2, int y2) {
        if (y1 == y2) {
            wx1 = x1;
            wx2 = x2;
            if (wx1 > wx2) {
                wx1 = x2;
                wx2 = x1;
            }
        }

        if (dx13 < dx23) {
            dxLeft = dx23;
            dxRight = dx13;
            return;
        }
        dxLeft = dx13;
        dxRight = dx23;
    }

    public static float calculateSideXIncrement(int x1, int y1, int x2, int y2) {
        return (y1 == y2) ? 0.0F : (float) (x2 - x1) / (y2 - y1);
    }

    public static void drawPart(int leftY, int rightY, GraphicsContext context, Color color) {
        for (int y = leftY; y <= rightY; y++, wx1 += dxLeft, wx2 += dxRight) {
            drawLine(y, context, color);
        }
    }

    public static void drawLine(int y, GraphicsContext graphicsContext, Color color) {
        PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        for (int x = (int) (wx1); x <= (int) (wx2); x++) {
                pixelWriter.setColor(x, y, color);
        }
    }

}

