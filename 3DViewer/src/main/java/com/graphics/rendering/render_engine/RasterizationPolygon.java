package com.graphics.rendering.render_engine;

import com.graphics.rendering.math.matrix.Matrix4D;
import com.graphics.rendering.math.vector.Vector3D;
import com.graphics.rendering.model.Model;
import com.graphics.rendering.model.Polygon;
import com.graphics.rendering.model.Triangle;
import com.graphics.rendering.model.Triangulation;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import javax.vecmath.Point2f;
import java.util.ArrayList;
import java.util.HashMap;

import static com.graphics.rendering.render_engine.GraphicConveyor.translateRotateScale;
import static com.graphics.rendering.render_engine.GraphicConveyor.vertexToPoint;

public class RasterizationPolygon {

    public static float wx1;
    public static float wx2;

    public static float dxLeft;
    public static float dxRight;

    public static void rasterization(Canvas canvas, GraphicsContext ctx, Model mesh, Camera camera, Color color) {
        ArrayList<Triangle> listWithTriangles = new ArrayList<>(Triangulation.triangulate(mesh));
        Matrix4D projectionViewModelMatrix = projectionMatrix(camera);

        float[][] zBuffer = new float[(int) canvas.getWidth()][(int) canvas.getHeight()];
        for (int i = 0; i < canvas.getWidth(); i++) {
            for (int j = 0; j < canvas.getHeight(); j++) {
                zBuffer[i][j] = Float.MAX_VALUE;
            }
        }
        for (int i = 0; i < listWithTriangles.size(); i++) {
            rasterize(listWithTriangles.get(i), ctx, (int) canvas.getWidth(), (int) canvas.getHeight(), projectionViewModelMatrix, color, zBuffer);
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

    public static void rasterize(Triangle triangle, GraphicsContext graphicsContext, int width, int height, Matrix4D projectionViewModelMatrix, Color color, float[][] zBuffer) {
        Point2f p1 = vertexToPoint(projectionViewModelMatrix.multiplyVectorDivW(triangle.getP1()), width, height);
        Point2f p2 = vertexToPoint(projectionViewModelMatrix.multiplyVectorDivW(triangle.getP2()), width, height);
        Point2f p3 = vertexToPoint(projectionViewModelMatrix.multiplyVectorDivW(triangle.getP3()), width, height);

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
        drawPart(y1, y2 - 1, graphicsContext, color, p1, p2, p3, triangle, zBuffer);

        computeBeforeLowerPart(dx13, dx23, x1, y1, x2, y2);
        drawPart(y2, y3, graphicsContext, color, p1, p2, p3, triangle, zBuffer);
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

    public static void drawPart(int leftY, int rightY, GraphicsContext context, Color color, Point2f p1, Point2f p2, Point2f p3, Triangle triangle, float[][] zBuffer) {
        for (int y = leftY; y <= rightY; y++, wx1 += dxLeft, wx2 += dxRight) {
            drawLine(y, context, color, p1, p2, p3, triangle, zBuffer);
        }
    }

    public static void drawLine(int y, GraphicsContext graphicsContext, Color color, Point2f p1, Point2f p2, Point2f p3, Triangle triangle, float[][] zBuffer) {
        PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        double red = 0.4 * color.getRed();
        for (int x = (int) (wx1); x <= (int) (wx2); x++) {
            if (zBuffer(p1, p2, p3, x, y, triangle, zBuffer)) {
                Color pixelColor = Color.color(red, 0, 0);
                pixelWriter.setColor(x, y, pixelColor);
           }
        }
    }

    public static boolean zBuffer(Point2f p1, Point2f p2, Point2f p3, int x, int y, Triangle triangle, float[][] zBuffer) {
        float z1 = triangle.getP1().getZ();
        float z2 = triangle.getP2().getZ();
        float z3 = triangle.getP3().getZ();
        float delitel = (p2.y - p3.y) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.y - p3.y);
        float alpha = ((p2.y - p3.y) * (x - p3.x) + (p3.x - p2.x) * (y - p3.y)) / delitel;
        float beta = ((p3.y - p1.y) * (x - p3.x) + (p1.x - p3.x) * (y - p3.y)) /delitel;
        float gamma = 1 - alpha - beta;
        float z = alpha * z1 + beta * z2 + gamma * z3;
        if (z < zBuffer[x][y]) {
            zBuffer[x][y] = z;
            return true;
        }
        return false;
    }
}

