package com.graphics.rendering.math.matrix;

import com.graphics.rendering.math.vector.Vector3D;

public class Matrix3D {
    private static final float esp = 1e-4f;
    private float[][] matrix;

    public Matrix3D() {
        this.matrix = new float[3][3];
    }

    /**
     * Конструктор
     *
     * @param isUnitMatrix true - единичная матрица
     * @param isUnitMatrix false - нулевая матрица
     */
    public Matrix3D(boolean isUnitMatrix) {
        if (isUnitMatrix) {
            this.matrix = new float[][]{
                    {1, 0, 0},
                    {0, 1, 0},
                    {0, 0, 1}};
        } else {
            this.matrix = new float[3][3];
        }
    }

    public Matrix3D(float[][] matrix) {
        if ((matrix.length == 3 && matrix[0].length == 3) || (matrix.length == 3 && matrix[0].length == 1)) {
            this.matrix = matrix;
        } else
            throw new IllegalArgumentException("Предоставленная матрица должна быть матрицей 3 на 3 или вектором-столбцом.");

    }

    public float[][] getMatrix() {
        return matrix;
    }

    public float getCell(int row, int col) {
        return matrix[row][col];
    }


    /**
     * Операция составления вектора-столбца
     */
    public static Matrix3D setVectorCol(Vector3D vector3D) {
        float[][] values = new float[][]{
                {vector3D.getX()},
                {vector3D.getY()},
                {vector3D.getZ()}};
        return new Matrix3D(values);
    }

    /**
     * Операция вывода матрицы
     */
    public void printMatrix() {
        System.out.println("Matrix: ");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Операция сложения матрицы
     */
    public Matrix3D sumMatrix(Matrix3D matrix3D) {
        if (matrix3D.getMatrix().length != 3 || matrix3D.getMatrix()[0].length != 3) {
            throw new IllegalArgumentException("Предоставленная матрица должна быть матрицей 3 на 3.");
        }

        float[][] values = new float[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                values[i][j] = matrix[i][j] + matrix3D.getCell(i, j);
            }
        }
        return new Matrix3D(values);
    }

    /**
     * Операция вычитания матрицы
     */
    public Matrix3D subtractMatrix(Matrix3D matrix3D) {
        if (matrix3D.getMatrix().length != 3 || matrix3D.getMatrix()[0].length != 3) {
            throw new IllegalArgumentException("Предоставленная матрица должна быть матрицей 3 на 3.");
        }

        float[][] values = new float[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                values[i][j] = matrix[i][j] - matrix3D.getCell(i, j);
            }
        }
        return new Matrix3D(values);
    }

    /**
     * Операция умножения на соответствующий вектор-столбец
     */
    public Matrix3D multiplyVector(Vector3D vectorCol) {
        Matrix3D matrix3DVector = Matrix3D.setVectorCol(vectorCol);
        float[][] values = new float[3][1];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                values[i][0] += matrix[i][j] * matrix3DVector.getCell(i, 0);
            }
        }
        return new Matrix3D(values);
    }
    public Matrix3D multiplyVector(float[][] vector) {

        Matrix3D matrix3DVector = new Matrix3D(vector);
        float[][] values = new float[3][1];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                values[i][0] += matrix[i][j] * matrix3DVector.getCell(i, 0);
            }
        }
        return new Matrix3D(values);
    }
    public Matrix3D multiplyVector(Matrix3D matrix3DVector) {
        float[][] values = new float[3][1];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                values[i][0] += matrix[i][j] * matrix3DVector.getCell(i, 0);
            }
        }
        return new Matrix3D(values);
    }

    /**
     * Операция перемножения матриц
     */
    public Matrix3D multiplyMatrix(Matrix3D matrix3D) {
        if (matrix3D.getMatrix().length != 3 || matrix3D.getMatrix()[0].length != 3) {
            throw new IllegalArgumentException("Предоставленная матрица должна быть матрицей 3 на 3.");
        }
        float[][] values = new float[3][3];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                for (int k = 0; k < 3; k++) {
                    values[i][j] += matrix[i][k] * matrix3D.getCell(k, j);
                }
            }
        }
        return new Matrix3D(values);
    }

    /**
     * Операция транспонирования
     */
    public Matrix3D transpose() {
        if (matrix.length != 3 || matrix[0].length != 3) {
            throw new IllegalArgumentException("Предоставленная матрица должна быть матрицей 3 на 3.");
        }
        float[][] transposed = new float[3][3];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return new Matrix3D(transposed);
    }

    public boolean equalsAns(Matrix3D matrix3D) {
        boolean flag = false;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == matrix3D.getCell(i, j)) {
                    flag = true;
                } else return false;
            }
        }
        return flag;
    }
}
