package matrix.impl;


public class MatrixImpl {
    /**
     * 矩阵的相关实现
     * <p>
     * <p>
     * 1.矩阵的转置
     * 2.矩阵相乘
     */
    /**
     * 矩阵转置的一般算法
     * 时间复杂度为O(row*col)
     *
     * @param matrix
     */
    public void TransposeSMatrix(int[][] matrix) {
        int row = matrix.length, col = matrix[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = i + 1; j < col; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }


    /**
     * 三元组表示的矩阵的转置
     * 其基本操作分为三步：
     * 1.将矩阵的行列值交换
     * 2.将三元组中的i和j交换
     * 3.将得到的矩阵按照行序进行排序
     *
     * @param tsMatrix
     * @return
     */
    /**
     * 第一种方法，按照列序进行转置
     *
     * @param tsMatrix
     * @return
     */
    public TSMatrix TransposeSMatrix(TSMatrix tsMatrix) {
        TSMatrix rs = new TSMatrix();
        rs.mu = tsMatrix.nu;
        rs.nu = tsMatrix.mu;
        rs.tu = tsMatrix.tu;
        Triple[] triples = tsMatrix.triples;
        Triple[] newTrip = new Triple[rs.tu];

        int q = 0;
        for (int i = 0; i < tsMatrix.nu; i++) {
            for (int j = 0; j < tsMatrix.tu; j++) {
                if (triples[j].j == i) {
                    Triple triple = new Triple(triples[j].j, triples[j].i, triples[j].data);
                    newTrip[q++] = triple;
                }
            }
        }
        rs.triples = newTrip;
        return rs;
    }

    /**
     * 第二种方法：
     * 将转置后的矩阵进行排序
     * 比如三元组结构矩阵M
     * i  j  v
     * 1  3  9
     * 1  5  -2
     * 2  3  1
     * 2  4  2
     * 4  1  3
     * 转置后达到矩阵N==>
     * i  j  v
     * 1  4  3
     * 3  1  9
     * 3  2  1
     * 4  2  2
     * 5  1  -2
     * 如果能够事先计算出矩阵M第j列的元素个数和第一个元素的位置
     * 就能直接将他放到矩阵N中。
     * 比如矩阵M
     * j   1  2  3  4  5
     * num    1  0  2  1  1
     * cols   0  0  1  3  4
     *
     * @param tsMatrix
     * @return
     */
    public TSMatrix FastTransposeSMatrix(TSMatrix tsMatrix) {
        TSMatrix rs = new TSMatrix();
        rs.mu = tsMatrix.nu;
        rs.nu = tsMatrix.mu;
        rs.tu = tsMatrix.tu;
        Triple[] triples = tsMatrix.triples;
        Triple[] newTrip = new Triple[rs.tu];

        int[] num = new int[tsMatrix.nu];
        for (int i = 0; i < tsMatrix.tu; i++) {
            num[triples[i].j]++;
        }

        int[] colIndex = new int[tsMatrix.nu];
        colIndex[0] = 0;
        for (int i = 1; i < tsMatrix.nu; i++) {
            colIndex[i] = colIndex[i - 1] + num[i - 1];
        }
        for (int i = 0; i < tsMatrix.tu; i++) {
            Triple triple = triples[i];
            int startIndex = colIndex[triple.j];
            while (newTrip[startIndex] != null) startIndex++;
            newTrip[startIndex] = new Triple(triple.j, triple.i, triple.data);
        }
        rs.triples = newTrip;
        return rs;
    }


    /**
     * 一般矩阵的乘法
     * M矩阵的列数必须等于N矩阵的行数才能相乘
     *
     * @param M
     * @param N
     * @return
     */
    public int[][] MultSMatrix(int[][] M, int[][] N) {
        if (M.length <= 0 || N.length <= 0 || M[0].length != N.length) {
            return null;
        }

        int[][] rs = new int[M.length][N[0].length];

        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < N[0].length; j++) {
                for (int k = 0; k < N.length; k++) {
                    rs[i][j] += M[i][k] * N[k][j];
                }
            }
        }
        return rs;
    }

    /**
     * 上面的矩阵乘法算法中，rs[i][j] += M[i][k] * N[k][j];只要M和N其中一个元素是0，那么结果就是0
     * 所以对于非零元，如果能够不去计算，那么就能减少矩阵乘法所以操作的次数
     * 选用三元组的存储结构，算法如下
     */
    public RLSMatrix MultSMatrix(RLSMatrix M, RLSMatrix N) {
        RLSMatrix Q = new RLSMatrix();
        Q.mu = M.mu;
        Q.nu = N.nu;
        Q.triples = new Triple[Q.mu * Q.nu];
        if (M.tu * N.tu == 0) return null;

        for (int row = 0; row < M.mu; row++) {
            //找到M中行号为row的元素
            int start = M.rpos[row];
            int end = M.tu;
            if (row < M.mu - 1) {
                end = M.rpos[row + 1];
            }
            int[] ctemp = new int[Q.nu];

            for (int i = start; i < end; i++) {
                Triple mTriple = M.triples[i];
                int nStart = N.rpos[mTriple.j];
                int nEnd = N.tu;
                if (mTriple.j < N.mu - 1) {
                    nEnd = N.rpos[mTriple.j + 1];
                }
                for (int j = nStart; j < nEnd; j++) {
                    Triple nTriple = N.triples[j];
                    ctemp[nTriple.j] += mTriple.data * nTriple.data;
                }
            }

            for (int i = 0; i < Q.nu; i++) {
                if (ctemp[i] != 0) {
                    Q.triples[Q.tu++] = new Triple(row, i, ctemp[i]);
                }
            }
        }
        return Q;

    }


    public static void main(String[] args) {
        TSMatrix matrix = new TSMatrix();
        matrix.mu = 7;
        matrix.nu = 7;
        matrix.tu = 8;
        Triple[] triples = new Triple[8];
        triples[0] = new Triple(1, 2, 12);
        triples[1] = new Triple(1, 3, 9);
        triples[2] = new Triple(3, 1, -3);
        triples[3] = new Triple(3, 6, 14);
        triples[4] = new Triple(4, 3, 24);
        triples[5] = new Triple(5, 2, 18);
        triples[6] = new Triple(6, 1, 15);
        triples[7] = new Triple(6, 4, -7);
        matrix.triples = triples;
        MatrixImpl matrixImpl = new MatrixImpl();

        int[][] matr = new int[7][7];
        matr[1][2] = 12;
        matr[1][3] = 9;
        matr[3][1] = -3;
        matr[3][6] = 14;
        matr[4][3] = 24;
        matr[5][2] = 18;
        matr[6][1] = 15;
        matr[6][4] = -7;

        matrixImpl.TransposeSMatrix(matr);

        matrixImpl.TransposeSMatrix(matrix);
        matrixImpl.FastTransposeSMatrix(matrix);


        int[][] M = {{3, 0, 0, 5}, {0, -1, 0, 0}, {2, 0, 0, 0}};
        int[][] N = {{0, 2}, {1, 0}, {-2, 4}, {0, 0}};

        matrixImpl.MultSMatrix(M, N);


        Triple[] triples1 = new Triple[4];
        triples1[0] = new Triple(0, 0, 3);
        triples1[1] = new Triple(0, 3, 5);
        triples1[2] = new Triple(1, 1, -1);
        triples1[3] = new Triple(2, 0, 2);

        RLSMatrix rlsMatrix1 = new RLSMatrix(triples1, 3, 4, 4);


        Triple[] triples2 = new Triple[4];
        triples2[0] = new Triple(0, 1, 2);
        triples2[1] = new Triple(1, 0, 1);
        triples2[2] = new Triple(2, 0, -2);
        triples2[3] = new Triple(2, 1, 4);
        RLSMatrix rlsMatrix2 = new RLSMatrix(triples2, 4, 2, 4);


        RLSMatrix rlsMatrix = matrixImpl.MultSMatrix(rlsMatrix1, rlsMatrix2);
    }

}
