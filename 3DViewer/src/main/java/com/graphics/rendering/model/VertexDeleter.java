package com.graphics.rendering.model;

import com.graphics.rendering.math.vector.Vector3D;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;


public class VertexDeleter {
    public static void deleteVertices(Model model, int[] deletedVertexIndices) {
        if (model == null) {
           throw new VertexDeleterException("Model is null");
        }
        ArrayList<Vector3D> vertices = model.getVertices();
        ArrayList<Polygon> polygons = model.getPolygons();
        TreeSet<Integer> set = new TreeSet<>();
        for (int deletedVertexIndex : deletedVertexIndices) {
            set.add(deletedVertexIndex);
        }
        int[] deletedVertexIndicesWithoutDuplicates = new int[set.size()];
        int m = 0;
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int index : set) {
            map.put(index, m + 1);
            deletedVertexIndicesWithoutDuplicates[m] = index;
            m++;
        }

        if (deletedVertexIndicesWithoutDuplicates[0] < 0 ||
                deletedVertexIndicesWithoutDuplicates[deletedVertexIndicesWithoutDuplicates.length - 1] >= vertices.size()) {
            throw new VertexDeleterException("Incorrect vertex indices");
        }
        for (int i = 0; i < polygons.size(); i++) {
            ArrayList<Integer> vertexIndices = polygons.get(i).getVertexIndices();
            for (int j = 0; j < vertexIndices.size(); j++) {
                if (set.contains(vertexIndices.get(j))) {
                    polygons.remove(i);
                    i--;
                    break;
                }
                Integer floor = set.floor(vertexIndices.get(j));
                vertexIndices.set(j, vertexIndices.get(j) - (floor == null ? 0 : map.get(floor)));
            }
        }
        for (int i = deletedVertexIndicesWithoutDuplicates.length - 1; i >= 0; i--) {
            vertices.remove(deletedVertexIndicesWithoutDuplicates[i]);
        }
    }
}
