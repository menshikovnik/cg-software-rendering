package com.graphics.rendering.math.matrix;

import com.graphics.rendering.math.vector.Vector4D;

public class Matrix4D {
    private static final float esp = 1e-4f;
    private float[][] matrix;

    public Matrix4D() {
        this.matrix = new float[4][4];
    }


    /**
     * Конструктор
     *
     * @param isUnitMatrix true - единичная матрица
     * @param isUnitMatrix false - нулевая матрица
     */
    public Matrix4D(boolean isUnitMatrix) {
        if (isUnitMatrix) {
            this.matrix = new float[][]{
                    {1, 0, 0, 0},
                    {0, 1, 0, 0},
                    {0, 0, 1, 0},
                    {0, 0, 0, 1}};
        } else {
            this.matrix = new float[4][4];
        }
    }

    public Matrix4D(float[][] matrix) {
        if ((matrix.length == 4 && matrix[0].length == 4) || (matrix.length == 4 && matrix[0].length == 1)) {
            this.matrix = matrix;
        } else
            throw new IllegalArgumentException("Предоставленная матрица должна быть матрицей 4 на 4 или вектором-столбцом.");
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
    public static Matrix4D setVectorCol(Vector4D vector4D) {
        float[][] values = new float[][]{
                {vector4D.getX()},
                {vector4D.getY()},
                {vector4D.getZ()},
                {vector4D.getW()}};
        return new Matrix4D(values);
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
    public Matrix4D sumMatrix(Matrix4D matrix4D) {
        if (matrix4D.getMatrix().length != 4 || matrix4D.getMatrix()[0].length != 4) {
            throw new IllegalArgumentException("Предоставленная матрица должна быть матрицей 4 на 4.");
        }
        float[][] values = new float[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                values[i][j] = matrix[i][j] + matrix4D.getCell(i, j);
            }
        }
        return new Matrix4D(values);
    }


    /**
     * Операция вычитания матрицы
     */
    public Matrix4D subtractMatrix(Matrix4D matrix4D) {
        if (matrix4D.getMatrix().length != 4 || matrix4D.getMatrix()[0].length != 4) {
            throw new IllegalArgumentException("Предоставленная матрица должна быть матрицей 4 на 4.");
        }
        float[][] values = new float[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                values[i][j] = matrix[i][j] - matrix4D.getCell(i, j);
            }
        }
        return new Matrix4D(values);
    }


    /**
     * Операция умножения на соответствующий вектор-столбец
     */
    public Matrix4D multiplyVector(Vector4D vectorCol) {
        Matrix4D matrix4DVector = Matrix4D.setVectorCol(vectorCol);
        float[][] values = new float[4][1];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                values[i][0] += matrix[i][j] * matrix4DVector.getCell(i, 0);
            }
        }
        return new Matrix4D(values);
    }

    public Matrix4D multiplyVector(float[][] vector) {
        Matrix4D matrix4DVector = new Matrix4D(vector);

        float[][] values = new float[4][1];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                values[i][0] += matrix[i][j] * matrix4DVector.getCell(i, 0);
            }
        }
        return new Matrix4D(values);
    }

    public Matrix4D multiplyVector(Matrix4D matrix4DVector) {
        float[][] values = new float[4][1];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                values[i][0] += matrix[i][j] * matrix4DVector.getCell(i, 0);
            }
        }
        return new Matrix4D(values);
    }


    /**
     * Операция перемножения матриц
     */
    public Matrix4D multiplyMatrix(Matrix4D matrix4D) {
        if (matrix4D.getMatrix().length != 4 || matrix4D.getMatrix()[0].length != 4) {
            throw new IllegalArgumentException("Предоставленная матрица должна быть матрицей 4 на 4.");
        }
        float[][] values = new float[4][4];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                for (int k = 0; k < 3; k++) {
                    values[i][j] += matrix[i][k] * matrix4D.getCell(k, j);
                }
            }
        }
        return new Matrix4D(values);
    }


    /**
     * Операция транспонирования
     */
    public Matrix4D transpose() {
        if (matrix.length != 4 || matrix[0].length != 4) {
            throw new IllegalArgumentException("Предоставленная матрица должна быть матрицей 4 на 4.");
        }
        float[][] transposed = new float[4][4];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return new Matrix4D(transposed);
    }

    public boolean equalsAns(Matrix4D matrix4D) {
        boolean flag = false;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == matrix4D.getCell(i, j)) {
                    flag = true;
                } else return false;
            }
        }
        return flag;
    }

}
