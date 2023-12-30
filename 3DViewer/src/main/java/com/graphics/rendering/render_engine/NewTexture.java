package com.graphics.rendering.render_engine;

import com.graphics.rendering.math.matrix.Matrix4D;
//import com.graphics.rendering.math.vector.Vector2D;
//
//import com.graphics.rendering.math.vector.Vector3D;
//import com.graphics.rendering.model.Model;
//import com.graphics.rendering.model.Triangle;
//import javafx.scene.Node;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.image.Image;
//import javafx.scene.image.PixelReader;
//import javafx.scene.image.PixelWriter;
//import javafx.scene.paint.Color;
//
//import javax.vecmath.Point2f;
//import java.util.*;
//import static com.graphics.rendering.render_engine.GraphicConveyor.vertexToPoint;
//
//public class NewTexture {
//    public static float wx1;
//    public static float wx2;
//
//    public static float dxLeft;
//    public static float dxRight;
//
//    public static void NaloshenieText(Model mesh) {
//        List<ArrayList<Triangle2>> lisyTriangTextureVertices = Arrays.asList(triangulate1(mesh));
//        Image texture = new Image("/photoForMainModel/test1.jpeg");
//        Color textureColor = getColorFromTexture(texture, u, v);
//
//
//        for (int i = 0; i < lisyTriangTextureVertices.size(); i++) {
//            rasterize(lisyTriangTextureVertices.get(i), ctx, (int) canvas.getWidth(), (int) canvas.getHeight());
//        }
//
//    }
//
//    public static ArrayList<Triangle2> triangulate1(Model mesh) {
//        ArrayList<Triangle2> trianglesTextureCoordinates = new ArrayList<>();
//
//        for (int i = 0; i < mesh.getPolygons().size(); i++) {
//            int indexVertexPolygon = 1; // индекс углов полигона
//
//            for (int j = 2; j < mesh.getPolygons().get(i).getTextureVertexIndices().size(); j++) {
//                Vector2D p1 = mesh.getTextureVertices().get(mesh.getPolygons().get(i).getTextureVertexIndices().get(0));
//                Vector2D p2 = mesh.getTextureVertices().get(mesh.getPolygons().get(i).getTextureVertexIndices().get(indexVertexPolygon));
//                Vector2D p3 = mesh.getTextureVertices().get(mesh.getPolygons().get(i).getTextureVertexIndices().get(indexVertexPolygon + 1));
//
//
//                ArrayList<Vector2D> points = new ArrayList<>();
//                points.add(p1);
//                points.add(p2);
//                points.add(p3);
//
//
//                Collections.sort(points, new Comparator<Vector2D>() {
//                    @Override
//                    public int compare(Vector2D o1, Vector2D o2) {
//                        if (o1.getY() > o2.getY())
//                            return -1;
//                        else if (o1.getY() < o2.getY())
//                            return 1;
//                        else
//                            return 0;
//                    }
//                });
//                p1 = points.get(0);
//                p2 = points.get(1);
//                p3 = points.get(2);
//                Triangle2 triangle2 = new Triangle2(p1, p2, p3);
//                trianglesTextureCoordinates.add(triangle2);
//                indexVertexPolygon++;
//            }
//        }
//
//        return trianglesTextureCoordinates;
//    }
//
//    public static void rasterize(Triangle2 triangle, GraphicsContext graphicsContext, int width, int height, Color color) {
//        Vector2D p1 = triangle.getP1();
//        Vector2D p2 = triangle.getP2();
//        Vector2D p3 = triangle.getP3();
//
//
//        int x1 = (int) p1.getX();
//        int y1 = (int) p1.getY();
//        int x2 = (int) p2.getX();
//        int y2 = (int) p2.getY();
//        int x3 = (int) p3.getX();
//        int y3 = (int) p3.getY();
//
//        float dx12 = calculateSideXIncrement(x1, y1, x2, y2);
//        float dx13 = calculateSideXIncrement(x1, y1, x3, y3);
//        float dx23 = calculateSideXIncrement(x2, y2, x3, y3);
//
//        computeBeforeUpperPart(dx12, dx13, x1);
//        drawPart(y1, y2 - 1, graphicsContext, color, p1, p2, p3, triangle);
//
//        computeBeforeLowerPart(dx13, dx23, x1, y1, x2, y2);
//        drawPart(y2, y3, graphicsContext, color, p1, p2, p3, triangle);
//    }
//
//
//    public static void computeBeforeUpperPart(float dx12, float dx13, float wx) {
//        wx1 = wx;
//        wx2 = wx1;
//
//        if (dx13 < dx12) {
//            dxLeft = dx13;
//            dxRight = dx12;
//            return;
//        }
//        dxLeft = dx12;
//        dxRight = dx13;
//    }
//
//    public static void computeBeforeLowerPart(float dx13, float dx23, int x1, int y1, int x2, int y2) {
//        if (y1 == y2) {
//            wx1 = x1;
//            wx2 = x2;
//            if (wx1 > wx2) {
//                wx1 = x2;
//                wx2 = x1;
//            }
//        }
//
//        if (dx13 < dx23) {
//            dxLeft = dx23;
//            dxRight = dx13;
//            return;
//        }
//        dxLeft = dx13;
//        dxRight = dx23;
//    }
//
//    public static float calculateSideXIncrement(int x1, int y1, int x2, int y2) {
//        return (y1 == y2) ? 0.0F : (float) (x2 - x1) / (y2 - y1);
//    }
//
//    public static void drawPart(int leftY, int rightY, GraphicsContext context, Color color, Point2f p1, Point2f p2, Point2f p3, Triangle triangle, float[][] zBuffer) {
//        for (int y = leftY; y <= rightY; y++, wx1 += dxLeft, wx2 += dxRight) {
//            drawLine(y, context, color, p1, p2, p3, triangle, zBuffer);
//        }
//    }
//
//    public static void drawLine(int y, GraphicsContext graphicsContext, Color color, Point2f p1, Point2f p2, Point2f p3, Triangle triangle, float[][] zBuffer) {
//        PixelWriter pixelWriter = graphicsContext.getPixelWriter();
//        double red = 0.4 * color.getRed();
//        for (int x = (int) (wx1); x <= (int) (wx2); x++) {
//            if (zBuffer(p1, p2, p3, x, y, triangle, zBuffer)) {
//                Color pixelColor = Color.color(red, 0, 0);
//                pixelWriter.setColor(x, y, pixelColor);
//            }
//        }
//    }
//
//    private Color getColorFromTexture(Image texture, double u, double v) {
//        // Получение PixelReader из текстуры
//        PixelReader pixelReader = texture.getPixelReader();
//
//        // Получение цвета из текстуры по текстурным координатам
//        int textureWidth = (int) texture.getWidth();
//        int textureHeight = (int) texture.getHeight();
//        int x = (int) (u * textureWidth);
//        int y = (int) (v * textureHeight);
//
//        // Проверка на выход за границы текстуры
//        if (x < 0) x = 0;
//        if (y < 0) y = 0;
//        if (x >= textureWidth) x = textureWidth - 1;
//        if (y >= textureHeight) y = textureHeight - 1;
//
//        // Получение цвета
//        return pixelReader.getColor(x, y);
//    }
//
//    public static boolean zBuffer(Point2f p1, Point2f p2, Point2f p3, int x, int y, Triangle triangle, float[][] zBuffer) {
//        float z1 = triangle.getP1().getZ();
//        float z2 = triangle.getP2().getZ();
//        float z3 = triangle.getP3().getZ();
//        float delitel = (p2.y - p3.y) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.y - p3.y);
//        float alpha = ((p2.y - p3.y) * (x - p3.x) + (p3.x - p2.x) * (y - p3.y)) / delitel;
//        float beta = ((p3.y - p1.y) * (x - p3.x) + (p1.x - p3.x) * (y - p3.y)) / delitel;
//        float gamma = 1 - alpha - beta;
//        float z = alpha * z1 + beta * z2 + gamma * z3;
//        if (z < zBuffer[x][y]) {
//            zBuffer[x][y] = z;
//            return true;
//        }
//        return false;
//    }
//}
//class Triangle2 extends Node {
//    Vector2D p1;
//    Vector2D p2;
//    Vector2D p3;
//
//    public Triangle2(Vector2D p1, Vector2D p2, Vector2D p3) {
//        this.p1 = p1;
//        this.p2 = p2;
//        this.p3 = p3;
//    }
//
//    public Vector2D getP1() {
//        return p1;
//    }
//
//    public void setP1(Vector2D p1) {
//        this.p1 = p1;
//    }
//
//    public Vector2D getP2() {
//        return p2;
//    }
//
//    public void setP2(Vector2D p2) {
//        this.p2 = p2;
//    }
//
//    public Vector2D getP3() {
//        return p3;
//    }
//
//    public void setP3(Vector2D p3) {
//        this.p3 = p3;
//    }
//}
