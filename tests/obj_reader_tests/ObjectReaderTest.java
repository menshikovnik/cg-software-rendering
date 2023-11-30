package obj_reader_tests;

import com.graphics.rendering.math.vector.Vector2D;
import com.graphics.rendering.math.vector.Vector3D;
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

import static org.junit.jupiter.api.Assertions.assertTrue;

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
        Assertions.assertDoesNotThrow(() -> ObjectReader.read(fileContent));
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
        ObjReaderException thrown = Assertions.assertThrowsExactly(ObjReaderException.class, () -> ObjectReader.read(fileContent));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 1. Token set incorrectly."));
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
        ObjReaderException thrown = Assertions.assertThrowsExactly(ObjReaderException.class, () -> ObjectReader.parseVertex(wordsInLineWithoutToken, 8));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 8. Failed to parse float value."));
    }

    @Test
    void testParseVertex04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0l"));
        ObjReaderException thrown = Assertions.assertThrowsExactly(ObjReaderException.class, () -> ObjectReader.parseVertex(wordsInLineWithoutToken, 8));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 8. Failed to parse float value."));
    }

    @Test
    void testParseVertex05() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0", "4.0"));
        ObjReaderException thrown = Assertions.assertThrowsExactly(ObjReaderException.class, () -> ObjectReader.parseVertex(wordsInLineWithoutToken, 8));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 8. Too many vertex arguments."));
    }

    @Test
    void testParseVertex06() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0"));
        ObjReaderException thrown = Assertions.assertThrowsExactly(ObjReaderException.class, () -> ObjectReader.parseVertex(wordsInLineWithoutToken, 8));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 8. Too few vertex arguments."));
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
        ObjReaderException thrown = Assertions.assertThrowsExactly(ObjReaderException.class, () -> ObjectReader.parseTextureVertex(wordsInLineWithoutToken, 8));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 8. Too many texture vertex arguments."));
    }

    @Test
    void testParseTextureVertex03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02l"));
        ObjReaderException thrown = Assertions.assertThrowsExactly(ObjReaderException.class, () -> ObjectReader.parseTextureVertex(wordsInLineWithoutToken, 8));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 8. Failed to parse float value."));
    }

    @Test
    void testParseNormal01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03", "1.04"));
        ObjReaderException thrown = Assertions.assertThrowsExactly(ObjReaderException.class, () -> ObjectReader.parseNormal(wordsInLineWithoutToken, 8));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 8. Too many normal arguments."));
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
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02l", "1.03"));
        ObjReaderException thrown = Assertions.assertThrowsExactly(ObjReaderException.class, () -> ObjectReader.parseNormal(wordsInLineWithoutToken, 8));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 8. Failed to parse float value."));
    }

    @Test
    void testParseNormal04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02"));
        ObjReaderException thrown = Assertions.assertThrowsExactly(ObjReaderException.class, () -> ObjectReader.parseNormal(wordsInLineWithoutToken, 8));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 8. Too few normal arguments."));
    }

    @Test
    void testParseFace01() {
        String fileContent =
                """
                        v 1 2 3
                        v 2 3 4
                        v 3 4 5
                        vt 1 2
                        vt 1 2
                        vt 1 2
                        vn 1 2 4
                        vn 1 2 4
                        f 1//2 2/3/1""";
        ObjReaderException thrown = Assertions.assertThrows(ObjReaderException.class, () -> ObjectReader.read(fileContent));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 9. Incorrect face format."));
    }

    @Test
    void testParseFace02() {
        String fileContent =
                """
                        v 1 2 3
                        v 2 3 4
                        v 3 4 5
                        vt 1 2
                        vt 1 2
                        vt 1 2
                        vn 1 2 4
                        vn 1 2 4
                        f 1//2 2//1""";
        ObjReaderException thrown = Assertions.assertThrows(ObjReaderException.class, () -> ObjectReader.read(fileContent));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 9. Incorrect face format."));
    }

    @Test
    void testParseFace03() {
        String fileContent =
                """
                        v 1 2 3
                        v 2 3 4
                        v 3 4 5
                        vt 1 2
                        vt 1 2
                        vt 1 2
                        vn 1 2 4
                        vn 1 2 4
                        f 1 2""";
        ObjReaderException thrown = Assertions.assertThrows(ObjReaderException.class, () -> ObjectReader.read(fileContent));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 9. Incorrect face format."));
    }

    @Test
    void testParseFace04() {
        String fileContent =
                """
                        v 1 2 3
                        v 2 3 4
                        v 3 4 5
                        vt 1 2
                        vt 1 2
                        vt 1 2
                        vn 1 2 4
                        vn 1 2 4
                        f 1//2 2""";
        ObjReaderException thrown = Assertions.assertThrows(ObjReaderException.class, () -> ObjectReader.read(fileContent));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 9. Incorrect face format."));
    }

    @Test
    void testParseFace05() {
        String fileContent =
                """
                        v 1 2 3
                        v 2 3 4
                        v 3 4 5
                        vt 1 2
                        vt 1 2
                        vt 1 2
                        vn 1 2 4
                        vn 1 2 4
                        f 1//2 2/3/1 3/4/1""";
        ObjReaderException thrown = Assertions.assertThrows(ObjReaderException.class, () -> ObjectReader.read(fileContent));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 9. Incorrect face format."));
    }

    @Test
    void testParseFace06() {
        String fileContent =
                """
                        v 1 2 3
                        v 2 3 4
                        v 3 4 5
                        vt 1 2
                        vt 1 2
                        vt 1 2
                        vn 1 2 4
                        vn 1 2 4
                        f a/b/c 2/3/1 3/4/1""";
        ObjReaderException thrown = Assertions.assertThrows(ObjReaderException.class, () -> ObjectReader.read(fileContent));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 9. Failed to parse int value."));
    }

    @Test
    void testParseFace07() {
        String fileContent =
                """
                        v 1 2 3
                        v 2 3 4
                        v 3 4 5
                        vt 1 2
                        vt 1 2
                        vt 1 2
                        vn 1 2 4
                        vn 1 2 4
                        f a/b/c 2/3/1 lakf""";
        ObjReaderException thrown = Assertions.assertThrows(ObjReaderException.class, () -> ObjectReader.read(fileContent));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 9. Failed to parse int value."));
    }

    @Test
    void testParseFace08(){
        String fileContent =
                """
                        v 1 2 3
                        v 2 3 4
                        v 3 4 5
                        vt 1 2
                        vt 1 2
                        vt 1 2
                        vn 1 2 4
                        vn 1 2 4
                        f 1/2/2 1/3/2 1/2/""";
        ObjReaderException thrown = Assertions.assertThrows(ObjReaderException.class, () -> ObjectReader.read(fileContent));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 9. Incorrect face format."));
    }

    @Test
    void testParseFace09() {
        String fileContent =
                """
                        v 1 2 3
                        v 2 3 4
                        v 3 4 5
                        vt 1 2
                        vt 1 2
                        vt 1 2
                        vn 1 2 4
                        vn 1 2 4
                        f 1/2/3 4/5/6 7/8/9""";
        ObjReaderException thrown = Assertions.assertThrows(ObjReaderException.class, () -> ObjectReader.read(fileContent));
        assertTrue(thrown.getMessage().contains("Error parsing OBJ file on line: 9. Index is too much."));
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