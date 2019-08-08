package leetcode.impl;

/**
 * @Description: 最小路径和
 * @Author: woneway
 * @Date: 2019/8/8 14:11
 */
public class Code64最小路径和 {
    /**
     * 给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
     *
     * 说明：每次只能向下或者向右移动一步。
     *
     * 示例:
     *
     * 输入:
     * [
     *  [1,3,1],
     *  [1,5,1],
     *  [4,2,1]
     * ]
     * 输出: 7
     * 解释: 因为路径 1→3→1→1→1 的总和最小。
     *
     *
     *
     * 思路分析:
     * 该题用动态规划算法可以求解
     *
     * 因为只能往下和往右走，所以端点(0,0)到达端点(i,j)的最小路径和，为端点(i,j-1)(若存在上端点,即j>0)和端点(i-1,j)(若存在左端点,即i>0)的最小路径和中的较小值
     * 加上当前端点的值。
     *
     * minSum(i,j) = {
     *      min(minSum(i-1,j),minSum(i,j-1))+v(i,j)   i>0&&j>0
     *      minSum(i-1,j)+v(i,j)   i>0&&j==0
     *      minSum(i,j-1)+v(i,j)   i==0&&j>0
     *      v(i,j)                 i==0&&j==0
     * }
     */

    public int minPathSum(int[][] grid) {
        int row = grid.length, col = grid[0].length;
        int[][] minSum = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (i > 0 && j > 0) {
                    minSum[i][j] = Math.min(minSum[i][j - 1], minSum[i - 1][j]) + grid[i][j];
                } else if (j > 0) {
                    minSum[i][j] = minSum[i][j - 1] + grid[i][j];
                } else if (i > 0) {
                    minSum[i][j] = minSum[i - 1][j] + grid[i][j];
                } else {
                    minSum[i][j] = grid[i][j];
                }
            }
        }
        return minSum[row - 1][col - 1];
    }

}
