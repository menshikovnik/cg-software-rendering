package matrix_tests;

import com.graphics.rendering.math.matrix.Matrix4D;
import com.graphics.rendering.math.vector.Vector4D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Matrix4DTest {

    @Test
    void sumMatrix() {
        float[][] matrix = {
                {1, 2, 3, 4},
                {4, 5, 6, 4},
                {7, 8, 9, 4},
                {7, 8, 9, 4}
        };
        float[][] matrix1 = {
                {1, 2, 3, 10},
                {4, 5, 6, 4},
                {70, 8, 10, 4},
                {7, 8, 9, 4}
        };
        float[][] result1 = {
                {2, 4, 6, 14},
                {8, 10, 12, 8},
                {77, 16, 19, 8},
                {14, 16, 18, 8}
        };
        Matrix4D matrix4D = new Matrix4D(matrix);
        Matrix4D matrix4D1 = new Matrix4D(matrix1);
        Matrix4D check = matrix4D.sumMatrix(matrix4D1);
        Matrix4D result = new Matrix4D(result1);
        Assertions.assertTrue(check.equalsAns(result));
    }

    @Test
    void subtractMatrix() {
        float[][] matrix = {
                {1, 2, 3, 4},
                {4, 5, 6, 4},
                {7, 8, 9, 4},
                {7, 8, 9, 4}
        };
        float[][] matrix1 = {
                {1, 2, 3, 10},
                {4, 5, 6, 4},
                {70, 8, 10, 4},
                {7, 8, 9, 4}
        };
        float[][] result1 = {
                {0, 0, 0, -6},
                {0, 0, 0, 0},
                {-63, 0, -1, 0},
                {0, 0, 0, 0}
        };
        Matrix4D matrix4D = new Matrix4D(matrix);
        Matrix4D matrix4D1 = new Matrix4D(matrix1);
        Matrix4D check = matrix4D.subtractMatrix(matrix4D1);
        Matrix4D result = new Matrix4D(result1);
        Assertions.assertTrue(check.equalsAns(result));
    }

    @Test
    void multiplyVector() {
        float[][] matrix = {
                {1, 2, 3, 4},
                {4, 5, 6, 4},
                {7, 8, 9, 4},
                {7, 8, 9, 4}
        };
        float[][] vector = {
                {50},
                {95},
                {140},
                {56}};
        Vector4D vector4D = new Vector4D(5, 5,5,2);
        Matrix4D matrix4D = new Matrix4D(matrix);
        Matrix4D check = matrix4D.multiplyVector(vector4D);
        Matrix4D result = new Matrix4D(vector);
        Assertions.assertTrue(check.equalsAns(result));
    }

    @Test
    void multiplyMatrix() {
        float[][] matrix = {
                {1, 2, 3, 4},
                {4, 5, 6, 4},
                {7, 8, 9, 4},
                {7, 8, 9, 4}
        };
        float[][] matrix1 = {
                {1, 2, 3, 10},
                {4, 5, 6, 4},
                {70, 8, 10, 4},
                {7, 8, 9, 4}
        };
        float[][] result1 = {
                {219, 36, 45, 30},
                {444, 81, 102, 84},
                {669, 126, 159, 138},
                {669, 126, 159, 138}
        };
        Matrix4D matrix4D = new Matrix4D(matrix);
        Matrix4D matrix4D1 = new Matrix4D(matrix1);
        Matrix4D check = matrix4D.multiplyMatrix(matrix4D1);
        Matrix4D result = new Matrix4D(result1);
        Assertions.assertTrue(check.equalsAns(result));
    }

    @Test
    void transpose() {
        float[][] matrix = {
                {1, 2, 3, 10},
                {4, 5, 6, 4},
                {70, 8, 10, 4},
                {7, 8, 9, 4}
        };
        float[][] result1 = {
                {1, 4, 70, 7},
                {2, 5, 8, 8},
                {3, 6, 10, 9},
                {10, 4, 4, 4}
        };
        Matrix4D matrix4D = new Matrix4D(matrix);
        Matrix4D check = matrix4D.transpose();
        Matrix4D result = new Matrix4D(result1);
        Assertions.assertTrue(check.equalsAns(result));
    }
}