package com.graphics.rendering.render_engine;


import com.graphics.rendering.math.vector.Vector3D;
import com.graphics.rendering.model.Triangle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Canvas mainCanvas;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GraphicsContext ctx = mainCanvas.getGraphicsContext2D();
        float[][] zBuffer = new float[1440][800];
        for (int i = 0; i < 1440; i++) {
            for (int j = 0; j < 800; j++) {  // тут тоже поменять размер
                zBuffer[i][j] = Float.MAX_VALUE;
            }
        }
        // добавить цикл прохождения по всем треугольникам
//        drawTriangle(ctx, 0, 200, 100, Color.RED, Color.GREEN,Color.BLUE); // сюда как то закинуть координаты точек р1 р2 р3
    }

    void drawTriangle(GraphicsContext graphicsContext, Vector3D p1, Vector3D p2, Vector3D p3, Color color1, Color color2, Color color3, float[][] zBuffer) {

        PixelWriter pixelWriter = graphicsContext.getPixelWriter();

        float minX = min(p1.getX(), p2.getX(), p3.getX());
        float minY = min(p1.getY(), p2.getY(), p3.getY());
        float maxX = max(p1.getX(), p2.getX(), p3.getX());
        float maxY = max(p1.getY(), p2.getY(), p3.getY());



//Переменные minX, minY, maxX и maxY используются для определения границ области, в которой будет производиться рисование.
//Эти переменные вычисляются на основе координат вершин треугольника, который будет закрашен.

        for (float x = minX; x <= maxX; x++) {
            for (float y = minY; y <= maxY; y++) {
                if (isPointInTriangle(x, y, p1, p2, p3)) {//где x и y - координаты текущего пикселя, x1, y1, x2, y2, x3, y3 - координаты вершин треугольника
                    // вычисляются значения барицентрических координат alpha, beta и gamma с помощью формул,
                    // которые используются для вычисления площадей подтреугольников,
                    // образованных вершинами треугольника и координатами пикселя.

                    float alpha =  ((p2.getY() - p3.getY()) * (x - p3.getX()) + (p3.getX() - p2.getX()) * (y - p3.getY())) / ((p2.getY() - p3.getY()) * (p1.getX() - p3.getX()) + (p3.getX() - p2.getX()) * (p1.getY() - p3.getY()));
                    float beta =  ((p3.getY() - p1.getY()) * (x - p3.getX()) + (p1.getX() - p3.getX()) * (y - p3.getY())) / ((p2.getY() - p3.getY()) * (p1.getX() - p3.getX()) + (p3.getX() - p2.getX()) * (p1.getY() - p3.getY()));
                    float gamma =  1 - alpha - beta;
                    // в теории тут можно поставить if про проверку точки в треугольнике,а не в конце

                    double red = (alpha * color1.getRed() + beta * color2.getRed() + gamma * color3.getRed());
                    double green = (alpha * color1.getGreen() + beta * color2.getGreen() + gamma * color3.getGreen());
                    double blue = alpha * color1.getBlue() + beta * color2.getBlue() + gamma * color3.getBlue();
                    red = Math.max(0.0, Math.min(1.0, red));
                    green = Math.max(0.0, Math.min(1.0, green));
                    blue = Math.max(0.0, Math.min(1.0, blue));

                    for (float xx = minX; xx <= maxX; xx++) {
                        for (float yy = minY; yy <= maxY; yy++) {

                            if (alpha >= 0 && beta >= 0 && gamma >= 0) {
                                float z = alpha * p1.getZ() + beta * p2.getZ() + gamma * p3.getZ();

                                // Сравнение с текущим значением Z-буфера
                                if (z < zBuffer[(int) xx][(int) yy]) {
                                    // Обновление Z-буфера
                                    zBuffer[(int) xx][(int) yy] = z;

                                    Color pixelColor = Color.color(red, green, blue);
                                    pixelWriter.setColor((int) x, (int) y, pixelColor);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public static void zBufferAlg(){

    }


    boolean isPointInTriangle(float x, float y, Vector3D p1, Vector3D p2 , Vector3D p3) {

        float d1 = sign(x, y ,p1, p2);
        float d2 = sign(x, y, p1, p2);
        float d3 = sign(x, y, p1, p2);
        boolean hasNeg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        boolean hasPos = (d1 > 0) || (d2 > 0) || (d3 > 0);
        return !(hasNeg && hasPos);

    }

    float sign(float x, float y,Vector3D p1, Vector3D p2) {

        return ((x - p2.getX()) * (p1.getY() - p2.getY()) * (p1.getX() - p2.getX()) * (y - p2.getY()));

    }
    //Данный код проверяет, находится ли заданная точка внутри треугольника, заданного координатами своих вершин.
    // Для этого используется формула, основанная на вычислении знака площади треугольника,
    // образованного заданными вершинами и заданной точкой. Если знаки площадей треугольников,
    // образованных заданными вершинами и заданной точкой, разные, то точка находится внутри треугольника.
    // Функция sign вычисляет знак площади треугольника, образованного тремя заданными точками. Функция
    // isPointInTriangle использует функцию sign для вычисления знаков площадей треугольников,
    // образованных заданными вершинами и заданной точкой, и проверяет, разные ли знаки у этих треугольников.
    // Если знаки разные, то точка находится внутри треугольника.

    float min(float p1, float p2, float p3) {
        float min = Math.min(p1,p2);
         return Math.min(min,p3);

    }
    float max(float p1, float p2, float p3) {
        float max = Math.max(p1, p2);

        return Math.max(max, p3);
    }
}
