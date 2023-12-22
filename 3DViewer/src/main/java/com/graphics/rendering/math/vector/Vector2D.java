package com.graphics.rendering.math.vector;


public class Vector2D {
    private static final float ESP = 1e-4f;
    private float x;
    private float y;

    /**
     * Конструктор двумерного вектора.
     *
     * @param x Координата по оси X.
     * @param y Координата по оси Y.
     */
    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Получение значения компонента вектора по заданному индексу.
     *
     * @param index Индекс компонента.
     * @return Значение компонента вектора по заданному индексу.
     * @throws IllegalArgumentException Если передан некорректный индекс.
     */
    public float get(int index) {
        switch (index) {
            case 0:
                return x;
            case 1:
                return y;
        }
        throw new IllegalArgumentException();
    }

    /**
     * Получение координаты X вектора.
     *
     * @return Значение координаты X вектора.
     */
    public float getX() {
        return x;
    }

    /**
     * Получение координаты Y вектора.
     *
     * @return Значение координаты Y вектора.
     */
    public float getY() {
        return y;
    }


    /**
     * Операция сложения векторов.
     *
     * @param v Вектор, который нужно сложить с текущим вектором.
     * @return Новый вектор, являющийся результатом сложения двух векторов.
     */
    public Vector2D sumVector(Vector2D v) {
        return new Vector2D(x + v.getX(), y + v.getY());
    }


    /**
     * Операция вычитания векторов.
     *
     * @param v Вектор, который нужно вычесть из текущего вектора.
     * @return Новый вектор, являющийся результатом вычитания двух векторов.
     */
    public Vector2D subtractVector(Vector2D v) {
        return new Vector2D(x - v.getX(), y - v.getY());
    }


    /**
     * Операция умножения вектора на скаляр.
     *
     * @param scalar Скаляр, на который нужно умножить текущий вектор.
     * @return Новый вектор, являющийся результатом умножения текущего вектора на скаляр.
     */
    public Vector2D multiplyScalar(float scalar) {
        return new Vector2D(x * scalar, y * scalar);
    }


    /**
     * Операция деления вектора на скаляр.
     *
     * @param scalar Скаляр, на который нужно разделить текущий вектор.
     * @return Новый вектор, являющийся результатом деления текущего вектора на скаляр.
     * @throws ArithmeticException Если происходит деление на ноль.
     */
    public Vector2D divScalar(float scalar) {
        if (Math.abs(scalar) < ESP) {
            throw new ArithmeticException("Деление на ноль не допускается.");
        }
        return new Vector2D(x / scalar, y / scalar);
    }


    /**
     * Операция вычисления длины вектора.
     *
     * @return Длина текущего вектора.
     */
    public float getLength() {
        return (float) Math.sqrt(x * x + y * y);
    }


    /**
     * Операция нормализации вектора.
     *
     * @return Новый вектор, являющийся результатом нормализации текущего вектора.
     * @throws ArithmeticException Если происходит попытка нормализовать нулевой вектор.
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
     * Операция скалярного произведения векторов.
     *
     * @param v Вектор, на который нужно умножить текущий вектор.
     * @return Скалярное произведение двух векторов.
     */
    public float dotProduct(Vector2D v) {
        return this.x * v.getX() + this.y * v.getY();
    }

    /**
     * Переопределение метода toString для удобного вывода информации о векторе.
     *
     * @return Строковое представление вектора.
     */
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
