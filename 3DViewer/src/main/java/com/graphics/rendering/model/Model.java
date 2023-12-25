package com.graphics.rendering.model;

import com.graphics.rendering.math.AffineTransformation;
import com.graphics.rendering.math.vector.Vector2D;
import com.graphics.rendering.math.vector.Vector3D;

import java.util.ArrayList;

public class Model {

    private ArrayList<Vector3D> vertices = new ArrayList<>();
    private ArrayList<Vector2D> textureVertices = new ArrayList<>();
    private ArrayList<Vector3D> normals = new ArrayList<>();
    private ArrayList<Polygon> polygons = new ArrayList<>();
    private ArrayList<String> nameOfMaterial = new ArrayList<>();
    private float normalInterpolationFactor;
    private String materialTemplateLib;
    private String nameOfModel;
    private String filePath;
    private static final float TRANSLATION = 0.4f;
    private static final int ROTATE = 3;

    private static final float SCALE = 10f;

    public Model() {
    }

    public Model(ArrayList<Vector3D> vertices, ArrayList<Vector2D> textureVertices, ArrayList<Vector3D> normals, ArrayList<Polygon> polygons, ArrayList<String> nameOfMaterial, float normalInterpolationFactor, String materialTemplateLib, String nameOfModel) {
        this.vertices = vertices;
        this.textureVertices = textureVertices;
        this.normals = normals;
        this.polygons = polygons;
        this.nameOfMaterial = nameOfMaterial;
        this.normalInterpolationFactor = normalInterpolationFactor;
        this.materialTemplateLib = materialTemplateLib;
        this.nameOfModel = nameOfModel;
    }

    public Model(Model model) {
        this.vertices = new ArrayList<>(model.vertices);
        this.textureVertices = new ArrayList<>(model.textureVertices);
        this.normals = new ArrayList<>(model.normals);
        this.polygons = new ArrayList<>(model.polygons);
        this.nameOfMaterial = new ArrayList<>(model.nameOfMaterial);
        this.normalInterpolationFactor = model.normalInterpolationFactor;
        this.materialTemplateLib = model.materialTemplateLib;
        this.nameOfModel = model.nameOfModel;
    }

    public static void moveModelLeft(Model model) {
        for (int i = 0; i < model.vertices.size(); i++) {
            Vector3D vector3D = AffineTransformation.parallelTranslation(model.getVertices().get(i).getX(), model.getVertices().get(i).getY(), model.getVertices().get(i).getZ(), new Vector3D(TRANSLATION, 0, 0));
            model.vertices.set(i, vector3D);
        }
    }

    public static void moveModelRight(Model model) {
        for (int i = 0; i < model.vertices.size(); i++) {
            Vector3D vector3D = AffineTransformation.parallelTranslation(model.getVertices().get(i).getX(), model.getVertices().get(i).getY(), model.getVertices().get(i).getZ(), new Vector3D(-TRANSLATION, 0, 0));
            model.vertices.set(i, vector3D);
        }
    }

    public static void moveModelForward(Model model) {
        for (int i = 0; i < model.vertices.size(); i++) {
            Vector3D vector3D = AffineTransformation.parallelTranslation(model.getVertices().get(i).getX(), model.getVertices().get(i).getY(), model.getVertices().get(i).getZ(), new Vector3D(0, 0, -TRANSLATION));
            model.vertices.set(i, vector3D);
        }
    }

    public static void moveModelBackward(Model model) {
        for (int i = 0; i < model.vertices.size(); i++) {
            Vector3D vector3D = AffineTransformation.parallelTranslation(model.getVertices().get(i).getX(), model.getVertices().get(i).getY(), model.getVertices().get(i).getZ(), new Vector3D(0, 0, TRANSLATION));
            model.vertices.set(i, vector3D);
        }
    }

    public static void moveModelOnYUp(Model model){
        for (int i = 0; i < model.vertices.size(); i++) {
            Vector3D vector3D = AffineTransformation.parallelTranslation(model.getVertices().get(i).getX(), model.getVertices().get(i).getY(), model.getVertices().get(i).getZ(), new Vector3D(0, TRANSLATION, 0));
            model.vertices.set(i, vector3D);
        }
    }

    public static void moveModelOnYDown(Model model){
        for (int i = 0; i < model.vertices.size(); i++) {
            Vector3D vector3D = AffineTransformation.parallelTranslation(model.getVertices().get(i).getX(), model.getVertices().get(i).getY(), model.getVertices().get(i).getZ(), new Vector3D(0, -TRANSLATION, 0));
            model.vertices.set(i, vector3D);
        }
    }

    public static void rotateModelOnYUp(Model model){
        model.vertices.replaceAll(d -> AffineTransformation.rotate(0, ROTATE, 0, d));
    }

    public static void rotateModelOnYDown(Model model){
        model.vertices.replaceAll(d -> AffineTransformation.rotate(0, -ROTATE, 0, d));
    }

    public static void rotateModelOnZClockwise(Model model) {
        model.vertices.replaceAll(d -> AffineTransformation.rotate(0, 0, ROTATE, d));
    }

    public static void rotateModelOnZNotClockwise(Model model) {
        model.vertices.replaceAll(d -> AffineTransformation.rotate(0, 0, -ROTATE, d));
    }

    public static void rotateModelOnXNotClockwise(Model model) {
        model.vertices.replaceAll(d -> AffineTransformation.rotate(ROTATE, 0, 0, d));
    }

    public static void rotateModelOnXClockwise(Model model) {
        model.vertices.replaceAll(d -> AffineTransformation.rotate(-ROTATE, 0, 0, d));
    }

    public static void scaleModelOnXForward(Model model){
        for (int i = 0; i < model.vertices.size(); i++) {
            Vector3D vector3D = AffineTransformation.scale(model.getVertices().get(i).getX(), model.getVertices().get(i).getY(), model.getVertices().get(i).getZ(), new Vector3D(SCALE, 0, 0));
            model.vertices.set(i, vector3D);
        }
    }

    public static void scaleModelOnXBackward(Model model){
        for (int i = 0; i < model.vertices.size(); i++) {
            Vector3D vector3D = AffineTransformation.scale(model.getVertices().get(i).getX(), model.getVertices().get(i).getY(), model.getVertices().get(i).getZ(), new Vector3D(-SCALE, 0, 0));
            model.vertices.set(i, vector3D);
        }
    }

    public static void scaleModelOnZRight(Model model){
        for (int i = 0; i < model.vertices.size(); i++) {
            Vector3D vector3D = AffineTransformation.scale(model.getVertices().get(i).getX(), model.getVertices().get(i).getY(), model.getVertices().get(i).getZ(), new Vector3D(0, 0, SCALE));
            model.vertices.set(i, vector3D);
        }
    }

    public static void scaleModelOnZLeft(Model model){
        for (int i = 0; i < model.vertices.size(); i++) {
            Vector3D vector3D = AffineTransformation.scale(model.getVertices().get(i).getX(), model.getVertices().get(i).getY(), model.getVertices().get(i).getZ(), new Vector3D(0, 0, -SCALE));
            model.vertices.set(i, vector3D);
        }
    }

    public static void scaleModelOnYUp(Model model){
        for (int i = 0; i < model.vertices.size(); i++) {
            Vector3D vector3D = AffineTransformation.scale(model.getVertices().get(i).getX(), model.getVertices().get(i).getY(), model.getVertices().get(i).getZ(), new Vector3D(0, SCALE, 0));
            model.vertices.set(i, vector3D);
        }
    }

    public static void scaleModelOnYDown(Model model){
        for (int i = 0; i < model.vertices.size(); i++) {
            Vector3D vector3D = AffineTransformation.scale(model.getVertices().get(i).getX(), model.getVertices().get(i).getY(), model.getVertices().get(i).getZ(), new Vector3D(0, -SCALE, 0));
            model.vertices.set(i, vector3D);
        }
    }



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

    public String getFilePath() {
        return filePath;
    }


    public void setNameOfModelAndFilePath(String nameOfModel, String filePath){
        this.nameOfModel = nameOfModel;
        this.filePath = filePath;
    }
}
