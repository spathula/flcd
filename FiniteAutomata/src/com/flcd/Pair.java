package com.flcd;

import java.util.Objects;

public class Pair<T, X> {
    private T left;

    private X right;

    public Pair(T left, X right) {
        this.left = left;
        this.right = right;
    }

    public T getLeft() {
        return left;
    }

    public void setLeft(T left) {
        this.left = left;
    }

    public X getRight() {
        return right;
    }

    public void setRight(X right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return left + "," + right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(left, pair.left) && Objects.equals(right, pair.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}
