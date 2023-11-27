package com.graphics.rendering.math.vector;

import com.graphics.rendering.math.Vector3f;

public class Vector3D {
    private static final float ESP = 1e-4f;
    private float x;
    private float y;
    private float z;

    public Vector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }


    public float get(int index) {
        switch (index) {
            case 0:
                return x;
            case 1:
                return y;
            case 2:
                return z;
        }
        throw new IllegalArgumentException();
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

    public void sumVector1(Vector3D v) {
        this.x += v.getX();
        this.y += v.getY();
        this.z += v.getZ();
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
        if (Math.abs(scalar) < ESP) {
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
        float a;
        float b;
        float c;
        float length = getLength();
        if (Math.abs(length) > ESP) {
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

    /**
     * Операция перевода вектора 3 в вектор 4, где w = 1
     */
    public Vector4D translationToVector4D() {
        return new Vector4D(getX(), getY(), getZ(), 1);
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
        return Math.abs(x - vector3D.x) < ESP && Math.abs(y - vector3D.y) < ESP && Math.abs(z - vector3D.z) < ESP;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector3D other)) return false;
        return Math.abs(x - other.x) < ESP && Math.abs(y - other.y) < ESP && Math.abs(z - other.z) < ESP;
    }
}
