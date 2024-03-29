package matrix_tests;

import com.graphics.rendering.math.matrix.Matrix4D;
import com.graphics.rendering.math.vector.Vector3D;
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
        Assertions.assertTrue(result.equalsAns(check));
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
        Assertions.assertTrue(result.equalsAns(check));
    }

    @Test
    void multiplyVector() {
        float[][] matrix = {
                {1, 2, 3, 4},
                {4, 5, 6, 4},
                {7, 8, 9, 4},
                {7, 8, 9, 4}
        };
        Vector4D vector4D = new Vector4D(5, 5, 5, 2);
        Matrix4D matrix4D = new Matrix4D(matrix);
        Vector4D check = matrix4D.multiplyVector(vector4D);
        Vector4D result = new Vector4D(38, 83, 128, 128);
        Assertions.assertEquals(result, check);
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
                {247, 68, 81, 46},
                {472, 113, 138, 100},
                {697, 158, 195, 154},
                {697, 158, 195, 154}
        };
        Matrix4D matrix4D = new Matrix4D(matrix);
        Matrix4D matrix4D1 = new Matrix4D(matrix1);
        Matrix4D check = matrix4D.multiplyMatrix(matrix4D1);
        Matrix4D result = new Matrix4D(result1);
        Assertions.assertTrue(result.equalsAns(check));
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
        Assertions.assertTrue(result.equalsAns(check));
    }

    @Test
    void determinant() {
        float[][] matrix = {
                {1, 3, 3, 1},
                {4, 5, 2, 4},
                {1, 1, 9, 4},
                {7, 8, 1, 1}
        };
        float result = 342;
        Matrix4D matrix4D = new Matrix4D(matrix);
        float check = matrix4D.determinant();
        Assertions.assertEquals(result, check);

    }
}