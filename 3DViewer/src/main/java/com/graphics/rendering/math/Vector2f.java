package com.graphics.rendering.math;

// Это заготовка для собственной библиотеки для работы с линейной алгеброй

import java.util.Objects;

public class Vector2f {
    private float x, y;
    private final float eps = 1e-7f;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector2f other)) return false;
        return Math.abs(x - other.x) < eps && Math.abs(y - other.y) < eps;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
