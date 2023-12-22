package com.graphics.rendering.math.vector;

public class Vector4D {
    private static final float ESP = 1e-4f;
    private float x;
    private float y;
    private float z;
    private float w;

    /**
     * Конструктор четырехмерного вектора.
     *
     * @param x Координата по оси X.
     * @param y Координата по оси Y.
     * @param z Координата по оси Z.
     * @param w Координата по оси W.
     */
    public Vector4D(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
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
            case 2:
                return z;
            case 3:
                return w;
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
     * Получение координаты Z вектора.
     *
     * @return Значение координаты Z вектора.
     */
    public float getZ() {
        return z;
    }

    /**
     * Получение координаты W вектора.
     *
     * @return Значение координаты W вектора.
     */
    public float getW() {
        return w;
    }


    /**
     * Операция сложения векторов.
     *
     * @param v Вектор, который нужно сложить с текущим вектором.
     * @return Новый вектор, являющийся результатом сложения двух векторов.
     */
    public Vector4D sumVector(Vector4D v) {
        float a = x + v.getX();
        float b = y + v.getY();
        float c = z + v.getZ();
        float d = w + v.getW();
        return new Vector4D(a, b, c, d);
    }


    /**
     * Операция вычитания векторов.
     *
     * @param v Вектор, который нужно вычесть из текущего вектора.
     * @return Новый вектор, являющийся результатом вычитания двух векторов.
     */
    public Vector4D subtractVector(Vector4D v) {
        return new Vector4D(x - v.getX(), y - v.getY(), z - v.getZ(), w - v.getW());
    }


    /**
     * Операция умножения вектора на скаляр.
     *
     * @param scalar Скаляр, на который нужно умножить текущий вектор.
     * @return Новый вектор, являющийся результатом умножения текущего вектора на скаляр.
     */
    public Vector4D multiplyScalar(float scalar) {
        return new Vector4D(x * scalar, y * scalar, z * scalar, w * scalar);
    }


    /**
     * Операция деления вектора на скаляр.
     *
     * @param scalar Скаляр, на который нужно разделить текущий вектор.
     * @return Новый вектор, являющийся результатом деления текущего вектора на скаляр.
     * @throws ArithmeticException Если происходит деление на ноль.
     */
    public Vector4D divScalar(float scalar) {
        if (Math.abs(scalar) < ESP) {
            throw new ArithmeticException("Деление на ноль не допускается.");
        } else {
            return new Vector4D(x / scalar, y / scalar, z / scalar, w / scalar);
        }
    }


    /**
     * Операция вычисления длины вектора.
     *
     * @return Длина текущего вектора.
     */
    public float getLength() {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }


    /**
     * Операция нормализации вектора.
     *
     * @return Новый вектор, являющийся результатом нормализации текущего вектора.
     * @throws ArithmeticException Если происходит попытка нормализовать нулевой вектор.
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
     * Операция скалярного произведения векторов.
     *
     * @param v Вектор, на который нужно умножить текущий вектор.
     * @return Скалярное произведение двух векторов.
     */
    public float dotProduct(Vector4D v) {
        return this.x * v.getX() + this.y * v.getY() + this.z * v.getZ() + this.w * v.getW();
    }

    /**
     * Операция перевода четырехмерного вектора в трехмерный, убирая компоненту W.
     *
     * @return Новый трехмерный вектор.
     */
    public Vector3D translationToVector3D() {
        return new Vector3D(getX(), getY(), getZ());
    }

    /**
     * Переопределение метода toString для удобного вывода информации о векторе.
     *
     * @return Строковое представление вектора.
     */
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

