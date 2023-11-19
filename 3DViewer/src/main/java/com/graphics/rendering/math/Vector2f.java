package com.graphics.rendering.math;

// Это заготовка для собственной библиотеки для работы с линейной алгеброй

public class Vector2f {
    public float x, y;
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
