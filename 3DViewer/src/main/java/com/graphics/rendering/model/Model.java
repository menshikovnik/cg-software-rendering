package com.graphics.rendering.model;
import com.graphics.rendering.math.Vector2f;
import com.graphics.rendering.math.Vector3f;

import java.util.*;
//Тут изменить на мой матан
public class Model {

    public ArrayList<Vector3f> vertices = new ArrayList<>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<>();
    public ArrayList<Vector3f> normals = new ArrayList<>();
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
}
