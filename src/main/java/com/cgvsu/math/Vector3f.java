package com.cgvsu.math;

// Это заготовка для собственной библиотеки для работы с линейной алгеброй
public class Vector3f {
    final float eps = 1e-7f;
    public final float x;
    public final float y;
    public final float z;

    public Vector3f(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean equals(final Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Vector3f other = (Vector3f) obj;
        return Math.abs(x - other.x) < eps && Math.abs(y - other.y) < eps;
    }

    /*public boolean equals(Vector3f other) {
        // todo: желательно, чтобы это была глобальная константа
        //final float eps = 1e-7f;
        return Math.abs(x - other.x) < eps && Math.abs(y - other.y) < eps && Math.abs(z - other.z) < eps;
    }*/

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}
