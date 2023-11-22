package obj_reader_tests;

import com.graphics.rendering.math.Vector3f;
import com.graphics.rendering.objreader.ObjectReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class ObjectReaderTest extends ObjectReader{

    @Test
    void read() {
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
    void testRead() {
    }

    @Test
    void testParseVertex() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjectReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.03f);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    void parseTextureVertex() {
    }

    @Test
    void parseNormal() {
    }

    @Test
    void parseFace() {
    }

    @Test
    void parseFaceWord() {
    }

    @Test
    void testRead1() {
    }
}
