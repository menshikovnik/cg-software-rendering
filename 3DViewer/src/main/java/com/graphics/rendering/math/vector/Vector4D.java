package com.graphics.rendering.math.vector;

public class Vector4D {
    private static final float esp = 1e-4f;
    private float x;
    private float y;
    private float z;
    private float w;

    public Vector4D(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
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

    public float getW() {
        return w;
    }


    /**
     * Операция сложения
     */
    public Vector4D sumVector(Vector4D v) {
        float a = x + v.getX();
        float b = y + v.getY();
        float c = z + v.getZ();
        float d = w + v.getW();
        return new Vector4D(a, b, c, d);
    }


    /**
     * Операция вычитания
     */
    public Vector4D subtractVector(Vector4D v) {
        float a = x - v.getX();
        float b = y - v.getY();
        float c = z - v.getZ();
        float d = w - v.getW();
        return new Vector4D(a, b, c, d);
    }


    /**
     * Операция умножения на скаляр
     */
    public Vector4D multiplyScalar(float scalar) {
        float a = x * scalar;
        float b = y * scalar;
        float c = z * scalar;
        float d = w * scalar;
        return new Vector4D(a, b, c, d);
    }


    /**
     * Операция деления на скаляр
     */
    public Vector4D divScalar(float scalar) {
        if (Math.abs(scalar) < esp) {
            throw new IllegalArgumentException("Деление на ноль не допускается.");
        }
        float a = x / scalar;
        float b = y / scalar;
        float c = z / scalar;
        float d = w / scalar;
        return new Vector4D(a, b, c, d);
    }


    /**
     * Операция вычисления длины
     */
    public float getLength() {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }


    /**
     * Операция нормализации вектора
     */
    public Vector4D normalize() {
        float a = 0;
        float b = 0;
        float c = 0;
        float d = 0;
        float length = getLength();
        if (Math.abs(length) > esp) {
            a = x / length;
            b = y / length;
            c = z / length;
            d = w / length;
        } else {
            throw new IllegalArgumentException("Невозможно нормализовать нулевой вектор.");
        }
        return new Vector4D(a, b, c, d);
    }


    /**
     * Операция скалярного произведения
     */
    public float dotProduct(Vector4D v) {
        return this.x * v.getX() + this.y * v.getY() + this.z * v.getZ() + this.w * v.getW();
    }

    @Override
    public String toString() {
        return "Vector4D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", w=" + w +
                '}';
    }
    public boolean equalsAns(Vector4D vector4D) {
        return x == vector4D.getX()
                && y == vector4D.getY()
                && z == vector4D.getZ()
                && w == vector4D.getW();
    }
}
