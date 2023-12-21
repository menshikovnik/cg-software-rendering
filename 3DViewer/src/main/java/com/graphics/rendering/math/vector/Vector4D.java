package com.graphics.rendering.math.vector;

public class Vector4D {
    private static final float ESP = 1e-4f;
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

    public float get(int index) {
        switch (index) {
            case 0:
                return x;
            case 1:
                return y;
            case 2:
                return z;
            case 3:
                return w;
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
        return new Vector4D(x - v.getX(), y - v.getY(), z - v.getZ(), w - v.getW());
    }


    /**
     * Операция умножения на скаляр
     */
    public Vector4D multiplyScalar(float scalar) {
        return new Vector4D(x * scalar, y * scalar, z * scalar, w * scalar);
    }


    /**
     * Операция деления на скаляр
     */
    public Vector4D divScalar(float scalar) {
        if (Math.abs(scalar) < ESP) {
            throw new ArithmeticException("Деление на ноль не допускается.");
        } else {
            return new Vector4D(x / scalar, y / scalar, z / scalar, w / scalar);
        }
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
        float length = getLength();
        if (Math.abs(length) > ESP) {
            return new Vector4D(x / length, y / length, z / length, w / length);
        } else {
            throw new ArithmeticException("Невозможно нормализовать нулевой вектор.");
        }

    }


    /**
     * Операция скалярного произведения
     */
    public float dotProduct(Vector4D v) {
        return this.x * v.getX() + this.y * v.getY() + this.z * v.getZ() + this.w * v.getW();
    }

    /**
     * Операция перевода вектора 4 в вектор 3, где w убираем
     */
    public Vector3D translationToVector3D() {
        return new Vector3D(getX(), getY(), getZ());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector4D other)) return false;
        return Math.abs(x - other.x) < ESP && Math.abs(y - other.y) < ESP && Math.abs(z - other.z) < ESP && Math.abs(w - other.w) < ESP;
    }
}

