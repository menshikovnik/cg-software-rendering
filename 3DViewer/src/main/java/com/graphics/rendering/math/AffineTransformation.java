package com.graphics.rendering.math;

import com.graphics.rendering.math.matrix.Matrix4D;
import com.graphics.rendering.math.vector.Vector3D;
import com.graphics.rendering.math.vector.Vector4D;

public class AffineTransformation {

    // todo: Возможно придётся округлять значения, например до 4х знаков после запятой

    /**
     * @param x,y,z    - коэффициенты для матрицы масштабирования
     * @param vector4D - вектор, который необходимо масштабировать
     */
    public static Vector4D scale(float x, float y, float z, Vector4D vector4D) {
        float[][] scale = new float[][]{
                {x, 0, 0, 0},
                {0, y, 0, 0},
                {0, 0, z, 0},
                {0, 0, 0, 1}};
        Matrix4D ScaleMatrix = new Matrix4D(scale);

        return ScaleMatrix.multiplyVector(vector4D);
    }


    /**
     * @param a        - угол поворота в градусах для матрицы поворота по оси X
     * @param b        - угол поворота в градусах для матрицы поворота по оси Y
     * @param c        - угол поворота в градусах для матрицы поворота по оси Z
     * @param vector4D - вектор, который необходимо повернуть
     */
    public static Vector4D rotate(int a, int b, int c, Vector4D vector4D) {
        float[][] rotateX = new float[][]{
                {1, 0, 0, 0},
                {0, (float) Math.cos(Math.toRadians(a)), (float) Math.sin(Math.toRadians(a)), 0},
                {0, (float) -Math.sin(Math.toRadians(a)), (float) Math.cos(Math.toRadians(a)), 0},
                {0, 0, 0, 1}};
        float[][] rotateY = new float[][]{
                {(float) Math.cos(Math.toRadians(b)), 0, (float) Math.sin(Math.toRadians(b)), 0},
                {0, 1, 0, 0},
                {(float) -Math.sin(Math.toRadians(b)), 0, (float) Math.cos(Math.toRadians(b)), 0},
                {0, 0, 0, 1}};

        float[][] rotateZ = new float[][]{
                {(float) Math.cos(Math.toRadians(c)), (float) Math.sin(Math.toRadians(c)), 0, 0},
                {(float) -Math.sin(Math.toRadians(c)), (float) Math.cos(Math.toRadians(c)), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}};

        Matrix4D RotateXMatrix = new Matrix4D(rotateX);
        Matrix4D RotateYMatrix = new Matrix4D(rotateY);
        Matrix4D RotateZMatrix = new Matrix4D(rotateZ);

        Matrix4D RotateXY = RotateXMatrix.multiplyMatrix(RotateYMatrix);
        Matrix4D RotateXYZ = RotateXY.multiplyMatrix(RotateZMatrix);

        return RotateXYZ.multiplyVector(vector4D);
    }


    /**
     * @param tx        - значение на которое смещается x
     * @param ty        - значение на которое смещается y
     * @param tz        - значение на которое смещается z
     * @param vector4D - вектор, который необходимо сместить
     */
    public static Vector4D parallelTranslation(float tx, float ty, float tz, Vector4D vector4D) {
        float[][] translation = new float[][]{
                {1, 0, 0, tx},
                {0, 1, 0, ty},
                {0, 0, 1, tz},
                {0, 0, 0, 1}};
        Matrix4D Translation = new Matrix4D(translation);
        return Translation.multiplyVector(vector4D);
    }

}
