package com.graphics.rendering.math.matrix;

import com.graphics.rendering.math.vector.Vector3D;

public class Matrix3D {
    private static final float esp = 1e-4f;
    private float[][] matrix;

    public Matrix3D() {
        this.matrix = new float[3][3];
    }

    /**
     * Конструктор матрицы 3x3.
     *
     * @param isUnitMatrix true/false единичная матрица/нулевая матрица
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

    /**
     * Конструктор матрицы 3x3.
     *
     * @param matrix Двумерный массив значений, представляющий матрицу 3x3.
     * @throws IllegalArgumentException Если предоставленная матрица не является матрицей 3x3.
     */
    public Matrix3D(float[][] matrix) {
        if (matrix.length != 3 || matrix[0].length != 3) {
            throw new IllegalArgumentException("Предоставленная матрица должна быть матрицей 3 на 3");
        }
        this.matrix = matrix;
    }

    /**
     * Возвращает двумерный массив значений текущей матрицы.
     *
     * @return Двумерный массив значений текущей матрицы 3x3.
     */
    public float[][] getMatrix() {
        return matrix;
    }

    /**
     * Получение значения ячейки матрицы по заданным индексам.
     *
     * @param row Индекс строки.
     * @param col Индекс столбца.
     * @return Значение ячейки матрицы по заданным индексам.
     */
    public float getCell(int row, int col) {
        return matrix[row][col];
    }

    /**
     * Операция вывода матрицы.
     */
    public void printMatrix() {
        System.out.println("Matrix: ");
        for (float[] floats : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(floats[j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Операция сложения матрицы.
     *
     * @param matrix3D Матрица 3x3, которую нужно сложить с текущей матрицей.
     * @return Новая матрица 3x3, являющаяся результатом сложения текущей матрицы и предоставленной матрицы.
     * @throws IllegalArgumentException Если предоставленная матрица не является матрицей 3x3.
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
     * Операция вычитания матрицы.
     *
     * @param matrix3D Матрица 3x3, которую нужно вычесть из текущей матрицы.
     * @return Новая матрица 3x3, являющаяся результатом вычитания предоставленной матрицы из текущей матрицы.
     * @throws IllegalArgumentException Если предоставленная матрица не является матрицей 3x3.
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
     * Операция умножения на соответствующий вектор-столбец.
     *
     * @param vectorCol Вектор-столбец, на который нужно умножить текущую матрицу.
     * @return Новый вектор, являющийся результатом умножения текущей матрицы на предоставленный вектор-столбец.
     * @throws NullPointerException Если предоставленный вектор равен null.
     */
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
     * Операция перемножения матриц.
     *
     * @param matrix3D Матрица 3x3, на которую нужно умножить текущую матрицу.
     * @return Новая матрица 3x3, являющаяся результатом перемножения текущей матрицы на предоставленную матрицу.
     * @throws IllegalArgumentException Если предоставленная матрица не является матрицей 3x3.
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
     * Операция транспонирования.
     *
     * @return Новая матрица 3x3, являющаяся результатом транспонирования текущей матрицы.
     * @throws IllegalArgumentException Если текущая матрица не является матрицей 3x3.
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

    /**
     * Вычисление определителя матрицы.
     *
     * @return Определитель текущей матрицы 3x3.
     * @throws IllegalArgumentException Если текущая матрица не является матрицей 3x3.
     */
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
