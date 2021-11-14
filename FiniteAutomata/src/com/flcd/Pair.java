package com.flcd;

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
        return left + " -> " + right;
    }
}
