package com.graphics.rendering.model;

import com.graphics.rendering.math.vector.Vector2D;
import com.graphics.rendering.math.vector.Vector3D;
import java.util.ArrayList;

public class Model {

    public ArrayList<Vector3D> vertices = new ArrayList<>();
    public ArrayList<Vector2D> textureVertices = new ArrayList<>();
    public ArrayList<Vector3D> normals = new ArrayList<>();
    public ArrayList<Polygon> polygons = new ArrayList<>();
    public ArrayList<String> nameOfMaterial = new ArrayList<>();
    private float normalInterpolationFactor;
    private String materialTemplateLib;
    private String nameOfModel;


    public String getNameOfModel() {
        return nameOfModel;
    }

    public void setNameOfModel(String nameOfModel) {
        this.nameOfModel = nameOfModel;
    }

    public String getMaterialTemplateLib() {
        return materialTemplateLib;
    }

    public void setMaterialTemplateLib(String materialTemplateLib) {
        this.materialTemplateLib = materialTemplateLib;
    }

    public float getNormalInterpolationFactor() {
        return normalInterpolationFactor;
    }

    public void setNormalInterpolationFactor(float normalInterpolationFactor) {
        this.normalInterpolationFactor = normalInterpolationFactor;
    }

    public ArrayList<Vector3D> getVertices() {
        return vertices;
    }

    public ArrayList<Vector2D> getTextureVertices() {
        return textureVertices;
    }

    public ArrayList<Vector3D> getNormals() {
        return normals;
    }

    public ArrayList<Polygon> getPolygons() {
        return polygons;
    }

    public ArrayList<String> getNameOfMaterial() {
        return nameOfMaterial;
    }
}
