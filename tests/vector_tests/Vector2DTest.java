package vector_tests;

import com.graphics.rendering.math.vector.Vector2D;
import org.junit.jupiter.api.Assertions;


class Vector2DTest {

    @org.junit.jupiter.api.Test
    void sumVector() {
        Vector2D vector2D = new Vector2D(5, 5);
        Vector2D vector2D1 = new Vector2D(15, 5);
        Vector2D check = vector2D.sumVector(vector2D1);
        Vector2D result = new Vector2D(20, 10);

        Assertions.assertTrue(result.equalsAns(check));
    }

    @org.junit.jupiter.api.Test
    void subtractVector() {
        Vector2D vector2D = new Vector2D(5, 5);
        Vector2D vector2D1 = new Vector2D(15, 5);
        Vector2D check = vector2D.subtractVector(vector2D1);
        Vector2D result = new Vector2D(-10, 0);

        Assertions.assertTrue(result.equalsAns(check));
    }

    @org.junit.jupiter.api.Test
    void multiplyScalar() {
        Vector2D vector2D = new Vector2D(5, 5);
        float scalar = 5;
        Vector2D check = vector2D.multiplyScalar(scalar);
        Vector2D result = new Vector2D(25, 25);

        Assertions.assertTrue(result.equalsAns(check));
    }

    @org.junit.jupiter.api.Test
    void divScalar() {
        Vector2D vector2D = new Vector2D(5, 5);
        float scalar = 5;
        Vector2D check = vector2D.divScalar(scalar);
        Vector2D result = new Vector2D(1, 1);

        Assertions.assertTrue(result.equalsAns(check));
    }

    @org.junit.jupiter.api.Test
    void getLength() {
        Vector2D vector2D = new Vector2D(5, 5);
        float check = vector2D.getLength();
        float result = 7.07107f;
        Assertions.assertEquals(check, result, 1e-4);

    }

    @org.junit.jupiter.api.Test
    void normalize() {
        Vector2D vector2D = new Vector2D(5, 5);
        Vector2D check = vector2D.normalize();
        Vector2D result = new Vector2D(5 / 7.07107f, 5 / 7.07107f);
        Assertions.assertEquals(check.getX(),result.getX(),1e-4);
        Assertions.assertEquals(check.getY(),result.getY(),1e-4);
    }

    @org.junit.jupiter.api.Test
    void dotProduct() {
        Vector2D vector2D = new Vector2D(5, 5);
        Vector2D vector2D1 = new Vector2D(1, 10);
        float check = vector2D.dotProduct(vector2D1);
        float result = 55;
        Assertions.assertEquals(check, result, 1e-4);
    }
}