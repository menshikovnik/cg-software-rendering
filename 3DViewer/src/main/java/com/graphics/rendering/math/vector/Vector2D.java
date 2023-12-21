package com.graphics.rendering.math.vector;


public class Vector2D {
    private static final float ESP = 1e-4f;
    private float x;
    private float y;

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float get(int index) {
        switch (index) {
            case 0:
                return x;
            case 1:
                return y;
        }
        throw new IllegalArgumentException();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }


    /**
     * Операция сложения
     */
    public Vector2D sumVector(Vector2D v) {
        return new Vector2D(x + v.getX(), y + v.getY());
    }


    /**
     * Операция вычитания
     */
    public Vector2D subtractVector(Vector2D v) {
        return new Vector2D(x - v.getX(), y - v.getY());
    }


    /**
     * Операция умножения на скаляр
     */
    public Vector2D multiplyScalar(float scalar) {
        return new Vector2D(x * scalar, y * scalar);
    }


    /**
     * Операция деления на скаляр
     */
    public Vector2D divScalar(float scalar) {
        if (Math.abs(scalar) < ESP) {
            throw new ArithmeticException("Деление на ноль не допускается.");
        }
        return new Vector2D(x / scalar, y / scalar);
    }


    /**
     * Операция вычисления длины
     */
    public float getLength() {
        return (float) Math.sqrt(x * x + y * y);
    }


    /**
     * Операция нормализации вектора
     */
    public Vector2D normalize() {
        float length = getLength();
        if (Math.abs(length) > ESP) {
            return new Vector2D(x / length, y / length);
        } else {
            throw new ArithmeticException("Невозможно нормализовать нулевой вектор.");
        }
    }


    /**
     * Операция скалярного произведения
     */
    public float dotProduct(Vector2D v) {
        return this.x * v.getX() + this.y * v.getY();
    }

    @Override
    public String toString() {
        return "Vector2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector2D other)) return false;
        return Math.abs(x - other.x) < ESP && Math.abs(y - other.y) < ESP;
    }
}
