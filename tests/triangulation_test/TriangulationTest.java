package triangulation_test;
import com.graphics.rendering.math.vector.Vector3D;
import com.graphics.rendering.model.Model;
import com.graphics.rendering.model.Polygon;
import com.graphics.rendering.model.Triangle;
import com.graphics.rendering.model.Triangulation;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;

public class TriangulationTest {

    ArrayList<Triangle> triangles = new ArrayList<>();
    ArrayList<Polygon> pol = new ArrayList();
    ArrayList<Vector3D> vert = new ArrayList();

    @Test
    public void ShouldCountTrianglesFromThreeVertex() {
        Model model = new Model();
        Polygon polygon = new Polygon();
        pol.add(polygon);

        vert.add(new Vector3D(100, 100,3));
        vert.add(new Vector3D(150, 200,3));
        vert.add(new Vector3D(100, 200,3));

        model.setVertices(vert);
        model.setPolygons(pol);

        polygon.getVertexIndices().add(0);
        polygon.getVertexIndices().add(1);
        polygon.getVertexIndices().add(2);

        triangles = Triangulation.triangulate(model);

        Assert.assertEquals(1,triangles.size());


    }
    @Test
    public void ShouldCountTrianglesFromTwoPolygons(){
        Model model = new Model();
        Polygon polygon = new Polygon();
        Polygon polygon2 = new Polygon();


        pol.add(polygon);
        pol.add(polygon2);


        vert.add(new Vector3D(200, 100,3));
        vert.add(new Vector3D(100, 120,3));
        vert.add(new Vector3D(100, 140,3));
        vert.add(new Vector3D(200, 200,3));
        vert.add(new Vector3D(300, 200,3));
        vert.add(new Vector3D(350, 140,3));
        vert.add(new Vector3D(350, 120,3));
        vert.add(new Vector3D(300, 100,3));

        vert.add(new Vector3D(100, 100,3));
        vert.add(new Vector3D(150, 200,3));
        vert.add(new Vector3D(100, 200,3));

        model.setVertices(vert);
        model.setPolygons(pol);



        polygon.getVertexIndices().add(0);
        polygon.getVertexIndices().add(1);
        polygon.getVertexIndices().add(2);
        polygon.getVertexIndices().add(3);
        polygon.getVertexIndices().add(4);
        polygon.getVertexIndices().add(5);
        polygon.getVertexIndices().add(6);
        polygon.getVertexIndices().add(7);



        polygon2.getVertexIndices().add(0);
        polygon2.getVertexIndices().add(1);
        polygon2.getVertexIndices().add(2);


        triangles = Triangulation.triangulate(model);
        Assert.assertEquals(7,triangles.size());
    }
    @Test
    public void ShouldCountTrianglesFromSixVertex(){
        Model model = new Model();
        Polygon polygon = new Polygon();
        pol.add(polygon);

        vert.add(new Vector3D(100, 100,3));
        vert.add(new Vector3D(100, 200,3));
        vert.add(new Vector3D(150, 250,3));
        vert.add(new Vector3D(200, 200,3));
        vert.add(new Vector3D(200, 100,3));
        vert.add(new Vector3D(150, 50,3));

        model.setVertices(vert);
        model.setPolygons(pol);

        polygon.getVertexIndices().add(0);
        polygon.getVertexIndices().add(1);
        polygon.getVertexIndices().add(2);
        polygon.getVertexIndices().add(3);
        polygon.getVertexIndices().add(4);
        polygon.getVertexIndices().add(5);

        triangles = Triangulation.triangulate(model);

        Assert.assertEquals(4,triangles.size());
    }
}

