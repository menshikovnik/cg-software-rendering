package com.graphics.rendering.model;

import java.util.ArrayList;
import java.util.Objects;

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

    public void setVertexIndices(ArrayList<Integer> vertexIndices) {
        this.vertexIndices = vertexIndices;
    }

    public void setTextureVertexIndices(ArrayList<Integer> textureVertexIndices) {
        this.textureVertexIndices = textureVertexIndices;
    }

    public void setNormalIndices(ArrayList<Integer> normalIndices) {
        this.normalIndices = normalIndices;
    }

    public ArrayList<Integer> getVertexIndices() {
        return vertexIndices;
    }

    public ArrayList<Integer> getTextureVertexIndices() {
        return textureVertexIndices;
    }

    public ArrayList<Integer> getNormalIndices() {
        return normalIndices;
    }
}
