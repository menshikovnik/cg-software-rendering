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
        if (matrix.length != 3 || matrix[0].length != 3) {
            throw new IllegalArgumentException("Предоставленная матрица должна быть матрицей 3 на 3");
        }
        this.matrix = matrix;
    }

    public float[][] getMatrix() {
        return matrix;
    }

    public float getCell(int row, int col) {
        return matrix[row][col];
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
    //Переделал, теперь возвращаем не матрицу 1 на 3, а вектор3
    public Vector3D multiplyVector(Vector3D vectorCol) {
        if (vectorCol == null) {
            throw new NullPointerException("Предоставленный вектор не может быть нулевым");
        }
        float[] values = new float[3];
        for (int i = 0; i < matrix.length; i++) {
            values[i] = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                values[i] += matrix[i][j] * vectorCol.get(j);
            }
        }
        return new Vector3D(values[0], values[1], values[2]);
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
                values[i][j] = 0;
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

    public float determinate() {
        if (matrix.length != 3 || matrix[0].length != 3) {
            throw new IllegalArgumentException("Предоставленная матрица должна быть матрицей 3 на 3.");
        }
        return (matrix[0][0] * matrix[1][1] * matrix[2][2] - (matrix[0][2] * matrix[1][1] * matrix[2][0])
                + matrix[0][1] * matrix[1][2] * matrix[2][0] - (matrix[0][1] * matrix[1][0] * matrix[2][2])
                + matrix[0][2] * matrix[1][0] * matrix[2][1] - (matrix[0][0] * matrix[1][2] * matrix[2][1]));
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
