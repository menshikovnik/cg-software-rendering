package com.graphics.rendering.objreader;

import com.graphics.rendering.math.vector.Vector2D;
import com.graphics.rendering.math.vector.Vector3D;
import com.graphics.rendering.model.Model;
import com.graphics.rendering.model.Polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ObjectReader {

    private static final String OBJ_VERTEX_TOKEN = "v";
    private static final String OBJ_TEXTURE_TOKEN = "vt";
    private static final String OBJ_NORMAL_TOKEN = "vn";
    private static final String OBJ_FACE_TOKEN = "f";
    private static final String OBJ_MTL_TOKEN = "mtllib";
    public static final String COMMENT_TOKEN = "#";
    public static final String USE_MTL_TOKEN = "usemtl";
    public static final String NAME_OF_MODEL_TOKEN = "o";
    public static final String SMOOTHING_FACTOR_TOKEN = "s";
    private static Model result = new Model();

    public static Model read(String fileContent) {
        result = new Model();

        int lineInd = 0;
        Scanner scanner = new Scanner(fileContent);
        while (scanner.hasNextLine()) {
            final String line = scanner.nextLine();
            ArrayList<String> wordsInLine = new ArrayList<>(Arrays.asList(line.split("\\s+")));
            if (wordsInLine.contains("")) {
                lineInd++;
                continue;
            }

            final String token = wordsInLine.get(0);
            if (token.equals(COMMENT_TOKEN)) {
                lineInd++;
                continue;
            }
            wordsInLine.remove(0);

            lineInd++;
            switch (token) {
                case OBJ_VERTEX_TOKEN -> result.getVertices().add(parseVertex(wordsInLine, lineInd));
                case OBJ_TEXTURE_TOKEN -> result.getTextureVertices().add(parseTextureVertex(wordsInLine, lineInd));
                case OBJ_NORMAL_TOKEN -> result.getNormals().add(parseNormal(wordsInLine, lineInd));
                case OBJ_FACE_TOKEN -> result.getPolygons().add(parseFace(wordsInLine, lineInd));
                case OBJ_MTL_TOKEN -> result.setMaterialTemplateLib(parseMaterialTextureLib(wordsInLine, lineInd));
                case USE_MTL_TOKEN -> result.getNameOfMaterial().add(parseMaterialTextureLib(wordsInLine, lineInd));
                case NAME_OF_MODEL_TOKEN -> result.setNameOfModel(parseNameOfModel(wordsInLine, lineInd));
                case SMOOTHING_FACTOR_TOKEN ->
                        result.setNormalInterpolationFactor(parseNormalInterpolationFactor(wordsInLine, lineInd));
                default -> throw new ObjReaderException("Token set incorrectly.", lineInd);
            }
        }
        return result;
    }


    protected static Vector3D parseVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        if (wordsInLineWithoutToken.size() > 3) {
            throw new ObjReaderException("Too many vertex arguments.", lineInd);
        }

        try {
            return new Vector3D(
                    Float.parseFloat(wordsInLineWithoutToken.get(0)),
                    Float.parseFloat(wordsInLineWithoutToken.get(1)),
                    Float.parseFloat(wordsInLineWithoutToken.get(2)));

        } catch (NumberFormatException e) {
            throw new ObjReaderException("Failed to parse float value.", lineInd);
        } catch (IndexOutOfBoundsException e) {
            throw new ObjReaderException("Too few vertex arguments.", lineInd);
        }
    }

    protected static String parseMaterialTextureLib(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        if (wordsInLineWithoutToken.get(0).isEmpty()) {
            throw new ObjReaderException("The material texture format is set incorrectly.", lineInd);
        }
        StringBuilder sb = new StringBuilder();
        try {
            for (String string : wordsInLineWithoutToken) {
                sb.append(string);
            }
        } catch (RuntimeException e) {
            throw new ObjReaderException("The material texture format is set incorrectly.", lineInd);
        }
        return sb.toString();
    }

    protected static Vector2D parseTextureVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        if (wordsInLineWithoutToken.size() > 2) {
            throw new ObjReaderException("Too many texture vertex arguments.", lineInd);
        }

        try {
            return new Vector2D(
                    Float.parseFloat(wordsInLineWithoutToken.get(0)),
                    Float.parseFloat(wordsInLineWithoutToken.get(1)));

        } catch (NumberFormatException e) {
            throw new ObjReaderException("Failed to parse float value.", lineInd);
        } catch (IndexOutOfBoundsException e) {
            throw new ObjReaderException("Too few texture vertex arguments.", lineInd);
        }
    }

    protected static Vector3D parseNormal(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        if (wordsInLineWithoutToken.size() > 3) {
            throw new ObjReaderException("Too many normal arguments.", lineInd);
        }

        try {
            return new Vector3D(
                    Float.parseFloat(wordsInLineWithoutToken.get(0)),
                    Float.parseFloat(wordsInLineWithoutToken.get(1)),
                    Float.parseFloat(wordsInLineWithoutToken.get(2)));

        } catch (NumberFormatException e) {
            throw new ObjReaderException("Failed to parse float value.", lineInd);
        } catch (IndexOutOfBoundsException e) {
            throw new ObjReaderException("Too few normal arguments.", lineInd);
        }
    }

    protected static Polygon parseFace(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        ArrayList<Integer> onePolygonVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonNormalIndices = new ArrayList<>();

        for (String str : wordsInLineWithoutToken) {
            parseFaceWord(str, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, lineInd);
        }

        Polygon result = new Polygon();

        if (checkCorrectFaceFormat(onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices)) {
            result.setVertexIndices(onePolygonVertexIndices);
            result.setTextureVertexIndices(onePolygonTextureVertexIndices);
            result.setNormalIndices(onePolygonNormalIndices);
        } else throw new ObjReaderException("Incorrect face format.", lineInd);

        return result;
    }

    protected static void parseFaceWord(
            String wordInLine,
            ArrayList<Integer> onePolygonVertexIndices,
            ArrayList<Integer> onePolygonTextureVertexIndices,
            ArrayList<Integer> onePolygonNormalIndices,
            int lineInd) {
        try {
            String[] wordIndices = wordInLine.split("/");
            switch (wordIndices.length) {
                case 1 -> {
                    if (Integer.parseInt(wordIndices[0]) - 1 > result.getVertices().size()) {
                        throw new ObjReaderException("Vertex index is too much", lineInd);
                    }
                    onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
                }

                case 2 -> {

                    if (Integer.parseInt(wordIndices[0]) - 1 > result.getVertices().size()
                            || Integer.parseInt(wordIndices[1]) - 1 > result.getTextureVertices().size()) {
                        throw new ObjReaderException("Index is too much.", lineInd);
                    } else if (onePolygonVertexIndices.size() != onePolygonTextureVertexIndices.size()) {
                        throw new ObjReaderException("Incorrect face format.", lineInd);
                    }

                    onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
                    onePolygonTextureVertexIndices.add(Integer.parseInt(wordIndices[1]) - 1);
                }
                case 3 -> {
                    if (isIndexOutOfBoundInFace(Integer.parseInt(wordIndices[0]), result.getVertices().size())
                            || isIndexOutOfBoundInFace(Integer.parseInt(wordIndices[2]), result.getNormals().size())) {
                        throw new ObjReaderException("Index is too much.", lineInd);
                    } else if (onePolygonVertexIndices.size() != onePolygonNormalIndices.size()) {
                        throw new ObjReaderException("Incorrect face format.", lineInd);
                    }

                    onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
                    onePolygonNormalIndices.add(Integer.parseInt(wordIndices[2]) - 1);

                    if (!wordIndices[1].isEmpty() && !isIndexOutOfBoundInFace(Integer.parseInt(wordIndices[1]), result.getTextureVertices().size())) {
                        onePolygonTextureVertexIndices.add(Integer.parseInt(wordIndices[1]) - 1);
                        if (onePolygonVertexIndices.size() != onePolygonTextureVertexIndices.size()) {
                            throw new ObjReaderException("Incorrect face format.", lineInd);
                        }
                    } else if (!wordIndices[1].isEmpty() && isIndexOutOfBoundInFace(Integer.parseInt(wordIndices[1]), result.getTextureVertices().size()))
                        throw new ObjReaderException("Index of normal is too much.", lineInd);
                }

                default -> throw new ObjReaderException("Invalid element size.", lineInd);

            }

        } catch (NumberFormatException e) {
            throw new ObjReaderException("Failed to parse int value.", lineInd);

        } catch (IndexOutOfBoundsException e) {
            throw new ObjReaderException("Too few arguments.", lineInd);
        }
    }

    public static boolean checkCorrectFaceFormat(
            ArrayList<Integer> onePolygonVertexIndices,
            ArrayList<Integer> onePolygonTextureVertexIndices,
            ArrayList<Integer> onePolygonNormalIndices) {
        if (onePolygonVertexIndices.size() < 3) {
            return false;
        }
        if (!onePolygonTextureVertexIndices.isEmpty() && onePolygonTextureVertexIndices.size() < 3) {
            return false;
        }
        if (!onePolygonNormalIndices.isEmpty() && onePolygonNormalIndices.size() < 3) {
            return false;
        }
        return true;
    }

    private static boolean isIndexOutOfBoundInFace(int index, int size) {
        return index - 1 >= size;
    }

    protected static String parseNameOfModel(final ArrayList<String> wordsInLineWithoutToken,
                                             int lineInd) {
        if (wordsInLineWithoutToken.size() > 1) {
            throw new ObjReaderException("Incorrect name of model", lineInd);
        } else if (!wordsInLineWithoutToken.isEmpty()) {
            return wordsInLineWithoutToken.get(0);
        }
        return " ";
    }

    public static float parseNormalInterpolationFactor(
            final ArrayList<String> wordsInLineWithoutToken,
            int lineInd) {
        if (wordsInLineWithoutToken.size() > 1) {
            throw new ObjReaderException("Incorrect normal interpolation factor", lineInd);
        } else if (!wordsInLineWithoutToken.get(0).equals("off")) {
            return Float.parseFloat(wordsInLineWithoutToken.get(0));
        }
        return 0.0f;
    }
}