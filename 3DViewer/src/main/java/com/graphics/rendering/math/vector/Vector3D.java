package com.graphics.rendering.math.vector;

public class Vector3D {
    private static final float ESP = 1e-7f;
    private float x;
    private float y;
    private float z;

    /**
     * Конструктор трехмерного вектора.
     *
     * @param x Координата по оси X.
     * @param y Координата по оси Y.
     * @param z Координата по оси Z.
     */
    public Vector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Конструктор трехмерного вектора с нулевыми значениями координат.
     */
    public Vector3D() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
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
     * Операция сложения векторов.
     *
     * @param v Вектор, который нужно сложить с текущим вектором.
     * @return Новый вектор, являющийся результатом сложения двух векторов.
     */
    public Vector3D sumVector(Vector3D v) {
        return new Vector3D(x + v.getX(), y + v.getY(), z + v.getZ());
    }

    /**
     * Операция сложения векторов (модификация текущего вектора).
     *
     * @param v Вектор, который нужно добавить к текущему вектору.
     */
    public void sumVectorThis(Vector3D v) {
        this.x += v.getX();
        this.y += v.getY();
        this.z += v.getZ();
    }

    /**
     * Операция вычитания векторов.
     *
     * @param v Вектор, который нужно вычесть из текущего вектора.
     * @return Новый вектор, являющийся результатом вычитания двух векторов.
     */
    public Vector3D subtractVector(Vector3D v) {
        return new Vector3D(x - v.getX(), y - v.getY(), z - v.getZ());
    }

    /**
     * Операция умножения вектора на скаляр.
     *
     * @param scalar Скаляр, на который нужно умножить текущий вектор.
     * @return Новый вектор, являющийся результатом умножения текущего вектора на скаляр.
     */
    public Vector3D multiplyScalar(float scalar) {
        return new Vector3D(x * scalar, y * scalar, z * scalar);
    }

    /**
     * Операция деления вектора на скаляр.
     *
     * @param scalar Скаляр, на который нужно разделить текущий вектор.
     * @return Новый вектор, являющийся результатом деления текущего вектора на скаляр.
     * @throws ArithmeticException Если происходит деление на ноль.
     */
    public Vector3D divScalar(float scalar) {
        if (Math.abs(scalar) < ESP) {
            throw new ArithmeticException("Деление на ноль не допускается.");
        } else {
            return new Vector3D(x / scalar, y / scalar, z / scalar);
        }
    }


    /**
     * Операция вычисления длины вектора.
     *
     * @return Длина текущего вектора.
     */
    public float getLength() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }


    /**
     * Операция нормализации вектора.
     *
     * @return Новый вектор, являющийся результатом нормализации текущего вектора.
     * @throws ArithmeticException Если происходит попытка нормализовать нулевой вектор.
     */
    public Vector3D normalize() {
        float length = getLength();
        if (Math.abs(length) > ESP) {
            return new Vector3D(x / length, y / length, z / length);
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
    public float dotProduct(Vector3D v) {
        return this.x * v.getX() + this.y * v.getY() + this.z * v.getZ();
    }


    /**
     * Операция векторного произведения векторов.
     *
     * @param v Вектор, на который нужно умножить текущий вектор.
     * @return Новый вектор, являющийся результатом векторного произведения двух векторов.
     */
    public Vector3D vectorMultiply(Vector3D v) {
        float newX = this.y * v.getZ() - this.z * v.getY();
        float newY = this.z * v.getX() - this.x * v.getZ();
        float newZ = this.x * v.getY() - this.y * v.getX();

        return new Vector3D(newX, newY, newZ);
    }

    /**
     * Операция перевода трехмерного вектора в четырехмерный, где W = 1.
     *
     * @return Новый четырехмерный вектор.
     */
    public Vector4D translationToVector4D() {
        return new Vector4D(getX(), getY(), getZ(), 1);
    }

    /**
     * Переопределение метода toString для удобного вывода информации о векторе.
     *
     * @return Строковое представление вектора.
     */
    @Override
    public String toString() {
        return "Vector3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector3D other)) return false;
        return Math.abs(x - other.x) < ESP && Math.abs(y - other.y) < ESP && Math.abs(z - other.z) < ESP;
    }
}
