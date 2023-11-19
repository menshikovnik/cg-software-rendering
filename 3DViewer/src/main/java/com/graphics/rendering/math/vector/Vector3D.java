package com.graphics.rendering.math.vector;

public class Vector3D {
    private static final float esp = 1e-4f;
    private float x;
    private float y;
    private float z;

    public Vector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }


    /**
     * Операция сложения
     */
    public Vector3D sumVector(Vector3D v) {
        float a = x + v.getX();
        float b = y + v.getY();
        float c = z + v.getZ();
        return new Vector3D(a, b, c);
    }


    /**
     * Операция вычитания
     */
    public Vector3D subtractVector(Vector3D v) {
        float a = x - v.getX();
        float b = y - v.getY();
        float c = z - v.getZ();
        return new Vector3D(a, b, c);
    }


    /**
     * Операция умножения на скаляр
     */
    public Vector3D multiplyScalar(float scalar) {
        float a = x * scalar;
        float b = y * scalar;
        float c = z * scalar;
        return new Vector3D(a, b, c);
    }


    /**
     * Операция деления на скаляр
     */
    public Vector3D divScalar(float scalar) {
        if (Math.abs(scalar) < esp) {
            throw new IllegalArgumentException("Деление на ноль не допускается.");
        }
        float a = x / scalar;
        float b = y / scalar;
        float c = z / scalar;
        return new Vector3D(a, b, c);
    }


    /**
     * Операция вычисления длины
     */
    public float getLength() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }


    /**
     * Операция нормализации вектора
     */
    public Vector3D normalize() {
        float a = 0;
        float b = 0;
        float c = 0;
        float length = getLength();
        if (Math.abs(length) > esp) {
            a = x / length;
            b = y / length;
            c = z / length;
        } else {
            throw new IllegalArgumentException("Невозможно нормализовать нулевой вектор.");
        }
        return new Vector3D(a, b, c);
    }


    /**
     * Операция скалярного произведения
     */
    public float dotProduct(Vector3D v) {
        return this.x * v.getX() + this.y * v.getY() + this.z * v.getZ();
    }


    /**
     * Операция векторного произведения
     */
    public Vector3D vectorMultiply(Vector3D v) {
        float newX = this.y * v.getZ() - this.z * v.getY();
        float newY = this.z * v.getX() - this.x * v.getZ();
        float newZ = this.x * v.getY() - this.y * v.getX();

        return new Vector3D(newX, newY, newZ);
    }

    @Override
    public String toString() {
        return "Vector3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
    public boolean equalsAns(Vector3D vector3D) {
        return x == vector3D.getX()
                && y == vector3D.getY()
                && z == vector3D.getZ();
    }
}
