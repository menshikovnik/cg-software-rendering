package obj_writer_test;

import com.graphics.rendering.math.vector.Vector2D;
import com.graphics.rendering.math.vector.Vector3D;
import com.graphics.rendering.model.Model;
import com.graphics.rendering.model.Polygon;
import com.graphics.rendering.objreader.ObjectReader;
import com.graphics.rendering.objwriter.ObjectWriter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ObjWriterTest {

    @Test
    void write() throws IOException {
        Model model = createSampleModel();
        String fileName = "model_norm.obj";
        ObjectWriter.write(fileName, model);
        Path path = Path.of(fileName);
        String fileContent = Files.readString(path);
        Model testModel = ObjectReader.read(fileContent);
        int vertexCount = testModel.getVertices().size();
        int textureVertexCount = testModel.getTextureVertices().size();
        int normalCount = testModel.getNormals().size();
        int polygonCount = testModel.getPolygons().size();
        assertTrue(new File(fileName).exists());



        assertEquals(3, vertexCount);
        assertEquals(3, textureVertexCount);
        assertEquals(3, normalCount);
        assertEquals(1, polygonCount);
    }

    private Model createSampleModel() {
        Model model = new Model();
        Vector3D vertex1 = new Vector3D(1.0f, 2.0f, 3.0f);
        Vector3D vertex2 = new Vector3D(4.0f, 5.0f, 6.0f);
        Vector3D vertex3 = new Vector3D(7.0f, 8.0f, 9.0f);

        Vector2D textureVertex1 = new Vector2D(0.1f, 0.2f);
        Vector2D textureVertex2 = new Vector2D(0.3f, 0.4f);
        Vector2D textureVertex3 = new Vector2D(0.5f, 0.6f);

        Vector3D normal1 = new Vector3D(0.7f, 0.8f, 0.9f);
        Vector3D normal2 = new Vector3D(1.0f, 1.1f, 1.2f);
        Vector3D normal3 = new Vector3D(1.3f, 1.4f, 1.5f);

        model.getVertices().add(vertex1);
        model.getVertices().add(vertex2);
        model.getVertices().add(vertex3);

        model.getTextureVertices().add(textureVertex1);
        model.getTextureVertices().add(textureVertex2);
        model.getTextureVertices().add(textureVertex3);

        model.getNormals().add(normal1);
        model.getNormals().add(normal2);
        model.getNormals().add(normal3);

        Polygon polygon = new Polygon();
        polygon.setVertexIndices(new ArrayList<>(Arrays.asList(0, 1, 2)));
        polygon.setTextureVertexIndices(new ArrayList<>(Arrays.asList(0, 1, 2)));
        polygon.setNormalIndices(new ArrayList<>(Arrays.asList(0, 1, 2)));


        model.getPolygons().add(polygon);

        return model;
    }

    @Test
    public void testWriteModelWithoutTextureAndNormals() throws IOException {
        Model model = new Model();

        model.getVertices().add(new Vector3D(1.0f, 2.0f, 3.0f));
        model.getVertices().add(new Vector3D(4.0f, 5.0f, 6.0f));
        model.getVertices().add(new Vector3D(7.0f, 8.0f, 9.0f));

        Polygon polygon = new Polygon();
        polygon.setVertexIndices(new ArrayList<>(Arrays.asList(0, 1, 2)));

        model.getPolygons().add(polygon);

        String fileName = "model_without_texture_normals.obj";
        ObjectWriter.write(fileName, model);
        Path path = Path.of(fileName);
        String fileContent = Files.readString(path);
        Model testModel = ObjectReader.read(fileContent);
        int vertexCount = testModel.getVertices().size();
        int textureVertexCount = testModel.getTextureVertices().size();
        int normalCount = testModel.getNormals().size();
        int polygonCount = testModel.getPolygons().size();
        assertTrue(new File(fileName).exists());



        assertEquals(3, vertexCount);
        assertEquals(0, textureVertexCount);
        assertEquals(0, normalCount);
        assertEquals(1, polygonCount);
    }


    @Test
    public void testWriteModelWithTextureWithoutNormals() throws IOException {
        Model model = new Model();
        model.getVertices().add(new Vector3D(1.0f, 2.0f, 3.0f));
        model.getVertices().add(new Vector3D(4.0f, 5.0f, 6.0f));
        model.getVertices().add(new Vector3D(7.0f, 8.0f, 9.0f));
        model.getTextureVertices().add(new Vector2D(0.0f, 0.0f));
        model.getTextureVertices().add(new Vector2D(1.0f, 1.0f));
        model.getTextureVertices().add(new Vector2D(2.0f, 2.0f));

        Polygon polygon = new Polygon();
        polygon.setVertexIndices(new ArrayList<>(Arrays.asList(0, 1, 2)));
        polygon.setTextureVertexIndices(new ArrayList<>(Arrays.asList(0, 1, 2)));
        model.getPolygons().add(polygon);

        String fileName = "model_with_texture_without_normals.obj";

        ObjectWriter.write(fileName, model);

        Path path = Path.of(fileName);
        String fileContent = Files.readString(path);

        Model testModel = ObjectReader.read(fileContent);

        int vertexCount = testModel.getVertices().size();
        int textureVertexCount = testModel.getTextureVertices().size();
        int normalCount = testModel.getNormals().size();
        int polygonCount = testModel.getPolygons().size();
        assertTrue(new File(fileName).exists());



        assertEquals(3, vertexCount);
        assertEquals(3, textureVertexCount);
        assertEquals(0, normalCount);
        assertEquals(1, polygonCount);
    }
    @Test
    public void testWriteModelWithoutTextureAndWithNormals() throws IOException {
        Model model = new Model();

        model.getVertices().add(new Vector3D(1.0f, 2.0f, 3.0f));
        model.getVertices().add(new Vector3D(4.0f, 5.0f, 6.0f));
        model.getVertices().add(new Vector3D(7.0f, 8.0f, 9.0f));

        Vector3D normal1 = new Vector3D(0.7f, 0.8f, 0.9f);
        Vector3D normal2 = new Vector3D(1.0f, 1.1f, 1.2f);
        Vector3D normal3 = new Vector3D(1.3f, 1.4f, 1.5f);

        model.getNormals().add(normal1);
        model.getNormals().add(normal2);
        model.getNormals().add(normal3);

        Polygon polygon = new Polygon();
        polygon.setVertexIndices(new ArrayList<>(Arrays.asList(0, 1, 2)));
        polygon.setNormalIndices(new ArrayList<>(Arrays.asList(0, 1, 2)));

        model.getPolygons().add(polygon);

        String fileName = "model_without_texture_with_normals.obj";
        ObjectWriter.write(fileName, model);
        Path path = Path.of(fileName);
        String fileContent = Files.readString(path);
        Model testModel = ObjectReader.read(fileContent);
        int vertexCount = testModel.getVertices().size();
        int textureVertexCount = testModel.getTextureVertices().size();
        int normalCount = testModel.getNormals().size();
        int polygonCount = testModel.getPolygons().size();
        assertTrue(new File(fileName).exists());



        assertEquals(3, vertexCount);
        assertEquals(0, textureVertexCount);
        assertEquals(3, normalCount);
        assertEquals(1, polygonCount);
    }
 }