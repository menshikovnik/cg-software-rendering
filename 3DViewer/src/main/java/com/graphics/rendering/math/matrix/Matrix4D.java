package com.graphics.rendering.math.matrix;

import com.graphics.rendering.math.vector.Vector3D;
import com.graphics.rendering.math.vector.Vector4D;

public class Matrix4D {
    private static final float esp = 1e-4f;
    private float[][] matrix;

    public Matrix4D() {
        this.matrix = new float[4][4];
    }


    /**
     * Конструктор матрицы 4x4
     *
     * @param isUnitMatrix true/false единичная матрица/нулевая матрица
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

    /**
     * Конструктор матрицы 4x4.
     *
     * @param matrix Двумерный массив значений, представляющий матрицу 4x4.
     * @throws IllegalArgumentException Если предоставленная матрица не является матрицей 4x4.
     */
    public Matrix4D(float[][] matrix) {
        if (matrix.length != 4 || matrix[0].length != 4) {
            throw new IllegalArgumentException("Предоставленная матрица должна быть матрицей 4 на 4");
        }
        this.matrix = matrix;
    }

    /**
     * Возвращает двумерный массив значений текущей матрицы.
     *
     * @return Двумерный массив значений текущей матрицы 4x4.
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
     * @param matrix4D Матрица 4x4, которую нужно сложить с текущей матрицей.
     * @return Новая матрица 4x4, являющаяся результатом сложения текущей матрицы и предоставленной матрицы.
     * @throws IllegalArgumentException Если предоставленная матрица не является матрицей 4x4.
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
     * Операция вычитания матрицы.
     *
     * @param matrix4D Матрица 4x4, которую нужно вычесть из текущей матрицы.
     * @return Новая матрица 4x4, являющаяся результатом вычитания предоставленной матрицы из текущей матрицы.
     * @throws IllegalArgumentException Если предоставленная матрица не является матрицей 4x4.
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
     * Операция умножения на соответствующий вектор-столбец.
     *
     * @param vectorCol Вектор-столбец, на который нужно умножить текущую матрицу.
     * @return Новый вектор, являющийся результатом умножения текущей матрицы на предоставленный вектор-столбец.
     * @throws NullPointerException Если предоставленный вектор равен null.
     */
    public Vector4D multiplyVector(Vector4D vectorCol) {
        if (vectorCol == null) {
            throw new NullPointerException("Предоставленный вектор не может быть нулевым");
        }

        float[] values = new float[4];
        for (int i = 0; i < matrix.length; i++) {
            values[i] = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                values[i] += matrix[i][j] * vectorCol.get(j);
            }
        }
        return new Vector4D(values[0], values[1], values[2], values[3]);
    }

    /**
     * Операция умножения на соответствующий вектор-столбец с делением на W-координату.
     *
     * @param vectorCol3D Вектор-столбец с трехмерными координатами, на который нужно умножить текущую матрицу.
     * @return Новый вектор, являющийся результатом умножения текущей матрицы на предоставленный вектор-столбец,
     * с последующим делением на W-координату нового вектора.
     * @throws NullPointerException Если предоставленный вектор равен null.
     */
    public Vector3D multiplyVectorDivW(Vector3D vectorCol3D) {
        if (vectorCol3D == null) {
            throw new NullPointerException("Предоставленный вектор не может быть нулевым");
        }
        Vector4D vector4DCol = vectorCol3D.translationToVector4D();

        float[] values = new float[4];
        for (int i = 0; i < matrix.length; i++) {
            values[i] = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                values[i] += matrix[i][j] * vector4DCol.get(j);
            }
        }
        return new Vector3D(values[0] / values[3], values[1] / values[3], values[2] / values[3]);
    }


    /**
     * Операция перемножения матриц.
     *
     * @param matrix4D Матрица 4x4, на которую нужно умножить текущую матрицу.
     * @return Новая матрица 4x4, являющаяся результатом перемножения текущей матрицы на предоставленную матрицу.
     * @throws IllegalArgumentException Если предоставленная матрица не является матрицей 4x4.
     */
    public Matrix4D multiplyMatrix(Matrix4D matrix4D) {
        if (matrix4D.getMatrix().length != 4 || matrix4D.getMatrix()[0].length != 4) {
            throw new IllegalArgumentException("Предоставленная матрица должна быть матрицей 4 на 4.");
        }
        float[][] values = new float[4][4];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                values[i][j] = 0;
                for (int k = 0; k < 4; k++) {
                    values[i][j] += matrix[i][k] * matrix4D.getCell(k, j);
                }
            }
        }
        return new Matrix4D(values);
    }


    /**
     * Операция транспонирования.
     *
     * @return Новая матрица 4x4, являющаяся результатом транспонирования текущей матрицы.
     * @throws IllegalArgumentException Если текущая матрица не является матрицей 4x4.
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

    /**
     * Вычисление определителя матрицы.
     *
     * @return Определитель текущей матрицы 4x4.
     * @throws IllegalArgumentException Если текущая матрица не является матрицей 3x3.
     */
    public float determinant() {
        if (matrix.length != 4 || matrix[0].length != 4) {
            throw new IllegalArgumentException("Предоставленная матрица должна быть матрицей 4 на 4.");
        }

        return matrix[0][0] * (matrix[1][1] * (matrix[2][2] * matrix[3][3] - matrix[2][3] * matrix[3][2])
                - matrix[1][2] * (matrix[2][1] * matrix[3][3] - matrix[2][3] * matrix[3][1])
                + matrix[1][3] * (matrix[2][1] * matrix[3][2] - matrix[2][2] * matrix[3][1]))
                - matrix[0][1] * (matrix[1][0] * (matrix[2][2] * matrix[3][3] - matrix[2][3] * matrix[3][2])
                - matrix[1][2] * (matrix[2][0] * matrix[3][3] - matrix[2][3] * matrix[3][0])
                + matrix[1][3] * (matrix[2][0] * matrix[3][2] - matrix[2][2] * matrix[3][0]))
                + matrix[0][2] * (matrix[1][0] * (matrix[2][1] * matrix[3][3] - matrix[2][3] * matrix[3][1])
                - matrix[1][1] * (matrix[2][0] * matrix[3][3] - matrix[2][3] * matrix[3][0])
                + matrix[1][3] * (matrix[2][0] * matrix[3][1] - matrix[2][1] * matrix[3][0]))
                - matrix[0][3] * (matrix[1][0] * (matrix[2][1] * matrix[3][2] - matrix[2][2] * matrix[3][1])
                - matrix[1][1] * (matrix[2][0] * matrix[3][2] - matrix[2][2] * matrix[3][0])
                + matrix[1][2] * (matrix[2][0] * matrix[3][1] - matrix[2][1] * matrix[3][0]));
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
