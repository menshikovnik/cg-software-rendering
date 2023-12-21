package vector_tests;

import com.graphics.rendering.math.vector.Vector3D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Vector3DTest {

    @Test
    void sumVector() {
        Vector3D vector3D = new Vector3D(5, 5, 5);
        Vector3D vector3D1 = new Vector3D(15, 5, 3);
        Vector3D check = vector3D.sumVector(vector3D1);
        Vector3D result = new Vector3D(20, 10, 8);
        Assertions.assertEquals(result, check);
    }

    @Test
    void subtractVector() {
        Vector3D vector3D = new Vector3D(5, 5, 5);
        Vector3D vector3D1 = new Vector3D(15, 5, 3);
        Vector3D check = vector3D.subtractVector(vector3D1);
        Vector3D result = new Vector3D(-10, 0, 2);
        Assertions.assertEquals(result, check);
    }

    @Test
    void multiplyScalar() {
        Vector3D vector3D = new Vector3D(5, 5, 5);
        float scalar = 3;
        Vector3D check = vector3D.multiplyScalar(scalar);
        Vector3D result = new Vector3D(15, 15, 15);
        Assertions.assertEquals(result, check);
    }

    @Test
    void divScalar() {
        Vector3D vector3D = new Vector3D(5, 5, 5);
        float scalar = 5;
        Vector3D check = vector3D.divScalar(scalar);
        Vector3D result = new Vector3D(1, 1, 1);
        Assertions.assertEquals(result, check);
    }

    @Test
    void getLength() {
        Vector3D vector3D = new Vector3D(5, 5, 5);
        float check = vector3D.getLength();
        float result = 8.66025f;
        Assertions.assertEquals(result,check , 1e-4);
    }

    @Test
    void normalize() {
        Vector3D vector3D = new Vector3D(5, 5, 5);
        Vector3D check = vector3D.normalize();
        Vector3D result = new Vector3D(5 / 8.66025f, 5 / 8.66025f, 5 / 8.66025f);
        Assertions.assertEquals(result.getX(), check.getX(), 1e-4);
        Assertions.assertEquals(result.getY(), check.getY(), 1e-4);
        Assertions.assertEquals(result.getZ(), check.getZ(), 1e-4);
    }

    @Test
    void dotProduct() {
        Vector3D vector3D = new Vector3D(5, 5, 5);
        Vector3D vector3D1 = new Vector3D(15, 5, 3);

        float check = vector3D.dotProduct(vector3D1);
        float result = 115;
        Assertions.assertEquals(result, check, 1e-4);
    }

    @Test
    void vectorMultiply() {
        Vector3D vector3D = new Vector3D(5, 5, 5);
        Vector3D vector3D1 = new Vector3D(15, 5, 3);
        Vector3D check = vector3D.vectorMultiply(vector3D1);
        Vector3D result = new Vector3D(-10, 60, -50);
        Assertions.assertEquals(result, check);
    }
}