package com.graphics.rendering.render_engine;

import com.graphics.rendering.math.matrix.Matrix4D;
import com.graphics.rendering.math.vector.Vector3D;

import javax.vecmath.Point2f;

public class GraphicConveyor {

    public static Matrix4D translateRotateScale() {
        return new Matrix4D(true);
    }

    public static Matrix4D lookAt(Vector3D eye, Vector3D target) {
        return lookAt(eye, target, new Vector3D(0F, 1.0F, 0F));
    }

    public static Matrix4D lookAt(Vector3D eye, Vector3D target, Vector3D up) {
        Vector3D resultX;
        Vector3D resultY;
        Vector3D resultZ;

        resultZ = target.subtractVector(eye);
        resultX = up.vectorMultiply(resultZ);
        resultY = resultZ.vectorMultiply(resultX);

        resultX = resultX.normalize();
        resultY = resultY.normalize();
        resultZ = resultZ.normalize();


        float[][] matrixTranslation = new float[][]{
                {1, 0, 0, -resultX.dotProduct(eye)},
                {0, 1, 0, -resultY.dotProduct(eye)},
                {0, 0, 1, -resultZ.dotProduct(eye)},
                {0, 0, 0, 1}};
        Matrix4D translation = new Matrix4D(matrixTranslation);

        float[][] matrixDot = new float[][]{
                {resultX.getX(), resultX.getY(), resultX.getZ(), 0},
                {resultY.getX(), resultY.getY(), resultY.getZ(), 0},
                {resultZ.getX(), resultZ.getY(), resultZ.getZ(), 0},
                {0, 0, 0, 1}};
        Matrix4D dot = new Matrix4D(matrixDot);

        return translation.multiplyMatrix(dot);
    }

    public static Matrix4D perspective(
            final float fov,
            final float aspectRatio,
            final float nearPlane,
            final float farPlane) {
        float tangentMinusOnDegree = (float) (1.0F / (Math.tan(fov * 0.5F)));
        float[][] projection = new float[][]
                {{tangentMinusOnDegree / aspectRatio, 0, 0, 0},
                        {0, tangentMinusOnDegree, 0, 0},
                        {0, 0, (farPlane + nearPlane) / (farPlane - nearPlane), 2 * (nearPlane * farPlane) / (nearPlane - farPlane)},
                        {0, 0, 1, 0}};
        return new Matrix4D(projection);
    }


    public static Point2f vertexToPoint(final Vector3D vertex, final int width, final int height) {
        return new Point2f ((width - 1) / 2.0f * vertex.getX() + (width - 1) / 2.0f , (1 - height) / 2.0F * vertex.getY() + (height - 1) / 2.0F);
    }
}
