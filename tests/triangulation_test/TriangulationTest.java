package triangulation_test;


import com.graphics.rendering.model.Triangle;
import com.graphics.rendering.model.Triangulation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class TriangulationTest {
    ArrayList<Point2D.Double> polygon = new ArrayList<>();
    ArrayList<Triangle> triangles = new ArrayList<>();
    @Before
    public void clear(){
        polygon.clear();
        triangles.clear();
    }

    @Test
    public void ShouldCountTrianglesFromThreeVertex() {


        polygon.add(new Point2D.Double(100, 100));
        polygon.add(new Point2D.Double(150, 200));
        polygon.add(new Point2D.Double(100, 200));


        triangles = Triangulation.triangulate(polygon);

        Assert.assertEquals(1,triangles.size());


    }
    @Test
    public void ShouldCountTrianglesFromEightVertex(){
        polygon.add(new Point2D.Double(200, 100));
        polygon.add(new Point2D.Double(100, 120));
        polygon.add(new Point2D.Double(100, 140));
        polygon.add(new Point2D.Double(200, 200));
        polygon.add(new Point2D.Double(300, 200));
        polygon.add(new Point2D.Double(350, 140));
        polygon.add(new Point2D.Double(350, 120));
        polygon.add(new Point2D.Double(300, 100));


        triangles = Triangulation.triangulate(polygon);

        Assert.assertEquals(6,triangles.size());
    }
    @Test
    public void ShouldCountTrianglesFromSixVertex(){
        polygon.add(new Point2D.Double(100, 100));
        polygon.add(new Point2D.Double(100, 200));
        polygon.add(new Point2D.Double(150, 250));
        polygon.add(new Point2D.Double(200, 200));
        polygon.add(new Point2D.Double(200, 100));
        polygon.add(new Point2D.Double(150, 50));
        triangles = Triangulation.triangulate(polygon);

        Assert.assertEquals(4,triangles.size());
    }

}

