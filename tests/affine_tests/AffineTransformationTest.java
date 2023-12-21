package affine_tests;

import com.graphics.rendering.math.AffineTransformation;
import com.graphics.rendering.math.vector.Vector3D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AffineTransformationTest {

    @Test
    void scale() {
        Vector3D vector3D = new Vector3D(5, 4, 3);
        Vector3D check = AffineTransformation.scale(5, 11, -3, vector3D);
        Vector3D result = new Vector3D(25, 44, -9);
        Assertions.assertEquals(result,check );
    }

    @Test
    void rotate() {
        Vector3D vector3D = new Vector3D(5, 4, 3);
        Vector3D check = AffineTransformation.rotate(0, 0, 45, vector3D);
        Vector3D result = new Vector3D((float) (vector3D.getX() * Math.cos(Math.toRadians(45))
                        + vector3D.getY() * Math.sin(Math.toRadians(45))), (float) (-vector3D.getX() * Math.sin(Math.toRadians(45))
                                        + vector3D.getY() * Math.cos(Math.toRadians(45))), vector3D.getZ());
        Assertions.assertEquals(result, check);
    }

    @Test
    void parallelTranslation() {
        Vector3D vector3D = new Vector3D(5, 4, 3);
        Vector3D check = AffineTransformation.parallelTranslation(10,-10,0,vector3D);
        Vector3D result = new Vector3D(15, -6, 3);
        Assertions.assertEquals(result, check);
    }
}