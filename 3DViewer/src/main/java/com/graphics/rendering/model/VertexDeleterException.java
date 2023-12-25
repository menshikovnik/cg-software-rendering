package com.graphics.rendering.model;

public class VertexDeleterException extends RuntimeException {
    public VertexDeleterException(String errorMessage) {
        super("Deleting vertices error: " + errorMessage);
    }
}
