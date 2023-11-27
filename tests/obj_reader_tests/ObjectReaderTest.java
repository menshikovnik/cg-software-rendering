package obj_reader_tests;

import com.graphics.rendering.math.vector.Vector2D;
import com.graphics.rendering.math.vector.Vector3D;
import com.graphics.rendering.model.Polygon;
import com.graphics.rendering.objreader.ObjReaderException;
import com.graphics.rendering.objreader.ObjectReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class ObjectReaderTest extends ObjectReader {
    @Test
    void testRead01() {
        File file = new File("tests/obj_reader_tests/obj_reader_test.obj");
        Path fileName = Path.of(file.getAbsolutePath());
        String fileContent;
        try {
            fileContent = Files.readString(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ObjectReader.read(fileContent);
    }

    @Test
    void testRead02() {
        File file = new File("tests/obj_reader_tests/obj_reader_test_02.obj");
        Path fileName = Path.of(file.getAbsolutePath());
        String fileContent;
        try {
            fileContent = Files.readString(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            ObjectReader.read(fileContent);
        } catch (ObjReaderException e) {
            String expectedError = "Error parsing OBJ file on line: 1. Token set incorrectly.";
            Assertions.assertEquals(expectedError, e.getMessage());
        }

    }

    @Test
    void testParseVertex01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3D result = ObjectReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3D expectedResult = new Vector3D(1.01f, 1.02f, 1.03f);
        Assertions.assertEquals(result, expectedResult);
    }

    @Test
    void testParseVertex02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1"));
        Vector3D result = ObjectReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3D expectedResult = new Vector3D(1.01f, 1.02f, 1.03f);
        Assertions.assertNotEquals(result, expectedResult);
    }

    @Test
    void testParseVertex03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("ab", "o", "ba"));
        try {
            ObjectReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseVertex04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0l"));
        try {
            ObjectReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException e) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, e.getMessage());
        }
    }

    @Test
    void testParseVertex05() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0", "4.0"));
        try {
            ObjectReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too many vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseVertex06() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0"));
        try {
            ObjectReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseTextureVertex01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02"));
        Vector2D result = ObjectReader.parseTextureVertex(wordsInLineWithoutToken, 5);
        Vector2D expectedResult = new Vector2D(1.01f, 1.02f);
        Assertions.assertEquals(result, expectedResult);
    }

    @Test
    void testParseTextureVertex02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        try {
            ObjectReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too many texture vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseTextureVertex03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02l"));
        try {
            ObjectReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseNormal01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03", "1.04"));
        try {
            ObjectReader.parseNormal(wordsInLineWithoutToken, 8);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 8. Too many normal arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseNormal02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3D expectedResult = new Vector3D(1.01f, 1.02f, 1.03f);
        Vector3D result = ObjectReader.parseNormal(wordsInLineWithoutToken, 8);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void testParseNormal03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02l"));
        try {
            ObjectReader.parseNormal(wordsInLineWithoutToken, 8);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 8. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseNormal04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02"));
        try {
            ObjectReader.parseNormal(wordsInLineWithoutToken, 8);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 8. Too few normal arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseFace01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1//2", "2/3/1"));
        try {
            ObjectReader.parseFace(wordsInLineWithoutToken, 8);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 8. Incorrect face format.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseFace02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1//2", "2//1"));
        try {
            ObjectReader.parseFace(wordsInLineWithoutToken, 8);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 8. Incorrect face format.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseFace03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1", "2"));
        try {
            ObjectReader.parseFace(wordsInLineWithoutToken, 8);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 8. Incorrect face format.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseFace04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1//2", "2"));
        try {
            ObjectReader.parseFace(wordsInLineWithoutToken, 8);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 8. Incorrect face format.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseFace05() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1//2", "2/3/1", "3/4/1"));
        try {
            ObjectReader.parseFace(wordsInLineWithoutToken, 8);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 8. Incorrect face format.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseFace06() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("a/b/c", "2/3/1", "3/4/1"));
        try {
            ObjectReader.parseFace(wordsInLineWithoutToken, 8);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 8. Failed to parse int value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseFace07() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1/1/1", "2/2/2", "3/3/3", "lakf"));
        try {
            ObjectReader.parseFace(wordsInLineWithoutToken, 8);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 8. Failed to parse int value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseFace08() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1/1/1", "2/2/2", "3/3/3"));

        ArrayList<Integer> expectedVertexIndecesResult = new ArrayList<>(Arrays.asList(0, 1, 2));
        ArrayList<Integer> expectedTextureVertexIndecesResult = new ArrayList<>(Arrays.asList(0, 1, 2));
        ArrayList<Integer> expectedNormalIndecesResult = new ArrayList<>(Arrays.asList(0, 1, 2));

        Polygon expectedPolygon = new Polygon();

        expectedPolygon.setVertexIndices(expectedVertexIndecesResult);
        expectedPolygon.setTextureVertexIndices(expectedTextureVertexIndecesResult);
        expectedPolygon.setNormalIndices(expectedNormalIndecesResult);

        Polygon result = ObjectReader.parseFace(wordsInLineWithoutToken, 8);

        Assertions.assertTrue(result.equals(expectedPolygon));
    }

    @Test
    void testParseFace09() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1/1/1", "2/2/2", "3/3/3", "4//4"));
        try {
            ObjectReader.parseFace(wordsInLineWithoutToken, 8);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 8. Incorrect face format.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseFace10() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1/1/1", "2/2/2", "3/3/3", "4/4/"));
        try {
            ObjectReader.parseFace(wordsInLineWithoutToken, 8);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 8. Incorrect face format.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseNameOfModel01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("Cube", "LK"));
        try {
            ObjectReader.parseNameOfModel(wordsInLineWithoutToken, 8);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 8. Incorrect name of model";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }
}