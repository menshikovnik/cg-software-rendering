package com.graphics.rendering.objwriter;

import com.graphics.rendering.math.vector.Vector2D;
import com.graphics.rendering.math.vector.Vector3D;
import com.graphics.rendering.model.Model;
import com.graphics.rendering.model.Polygon;

import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class ObjectWriter {
    public static void write(String fileName, Model model) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
            writeVertices(writer, model.vertices);
            writeTextureVertices(writer, model.textureVertices);
            writeNormals(writer, model.normals);
            writePolygons(writer, model.polygons);
        } catch (IOException e) {
            throw new ObjWriterException(e.getMessage());
        }
    }


    private static void writeVertices(BufferedWriter writer, List<Vector3D> vertices) throws IOException {
        DecimalFormatSymbols customSymbols = new DecimalFormatSymbols(Locale.US);
        customSymbols.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("0.######", customSymbols);

        for (Vector3D vertex : vertices) {
            writer.write("v " + decimalFormat.format(vertex.getX()) + " " + decimalFormat.format(vertex.getY()) + " " + decimalFormat.format(vertex.getZ()));
            writer.newLine();
        }
    }

    private static void writeTextureVertices(BufferedWriter writer, List<Vector2D> textureVertices) throws IOException {
        DecimalFormatSymbols customSymbols = new DecimalFormatSymbols(Locale.US);
        customSymbols.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("0.######", customSymbols);

        for (Vector2D textureVertex : textureVertices) {
            writer.write("vt " + decimalFormat.format(textureVertex.getX()) + " " + decimalFormat.format(textureVertex.getY()));
            writer.newLine();
        }
    }

    private static void writeNormals(BufferedWriter writer, List<Vector3D> normals) throws IOException {
        DecimalFormatSymbols customSymbols = new DecimalFormatSymbols(Locale.US);
        customSymbols.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("0.######", customSymbols);

        for (Vector3D normal : normals) {
            writer.write("vn " + decimalFormat.format(normal.getX()) + " " + decimalFormat.format(normal.getY()) + " " + decimalFormat.format(normal.getZ()));
            writer.newLine();
        }
    }

    private static void writePolygons(BufferedWriter writer, List<Polygon> polygons) throws IOException {
        for (Polygon polygon : polygons) {
            writer.write("f ");
            List<Integer> vertexIndices = polygon.getVertexIndices();
            List<Integer> textureVertexIndices = polygon.getTextureVertexIndices();
            List<Integer> normalIndices = polygon.getNormalIndices();

            for (int i = 0; i < vertexIndices.size(); i++) {
                if (!textureVertexIndices.isEmpty()) {
                    writer.write(vertexIndices.get(i) + 1 + "/" + (textureVertexIndices.get(i) + 1));
                } else {
                    writer.write(String.valueOf(vertexIndices.get(i) + 1));
                }
                if (!normalIndices.isEmpty()) {
                    if (textureVertexIndices.isEmpty()) {
                        writer.write("/");
                    }
                    writer.write("/" + (normalIndices.get(i) + 1));
                }
                writer.write(" ");
            }
            writer.newLine();

        }
    }
}
