package com.graphics.rendering.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Objects;

@Data
public class Polygon {

    private ArrayList<Integer> vertexIndices;
    private ArrayList<Integer> textureVertexIndices;
    private ArrayList<Integer> normalIndices;


    public Polygon() {
        vertexIndices = new ArrayList<>();
        textureVertexIndices = new ArrayList<>();
        normalIndices = new ArrayList<>();
    }

    public boolean equals(Polygon polygon) {
        if (this == polygon) return true;
        return (polygon.getVertexIndices().containsAll(vertexIndices) && getVertexIndices().size() == vertexIndices.size())
                && (polygon.getTextureVertexIndices().containsAll(textureVertexIndices) && getTextureVertexIndices().size() == textureVertexIndices.size())
                && (polygon.getNormalIndices().containsAll(normalIndices) && getNormalIndices().size() == normalIndices.size());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVertexIndices(), getTextureVertexIndices(), getNormalIndices());
    }
}
