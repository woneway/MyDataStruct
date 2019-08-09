package leetcode.impl.backtracking;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description: N皇后
 * @Author: woneway
 * @Date: 2019/8/9 14:33
 */
public class Code51N皇后 {
    /**
     * n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
     * <p>
     * 给定一个整数 n，返回所有不同的 n 皇后问题的解决方案。
     * <p>
     * 每一种解法包含一个明确的 n 皇后问题的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
     * <p>
     * 示例:
     * <p>
     * 输入: 4
     * 输出: [
     * [".Q..",  // 解法 1
     * "...Q",
     * "Q...",
     * "..Q."],
     * <p>
     * ["..Q.",  // 解法 2
     * "Q...",
     * "...Q",
     * ".Q.."]
     * ]
     * 解释: 4 皇后问题存在两个不同的解法。
     */

    /**
     * 思路分析：
     * 通过剪枝，删除掉那些不符合条件的情况
     * 假设当前放一个皇后到<i,j>的位置，
     * 对任意的<k,l>(0<=k<i和0<=l<n),有i!=k && l!=j && i-k!=|l-j|
     *
     * @param n
     * @return
     */
    public List<List<String>> solveNQueens(int n) {
        char[][] chessboard = new char[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                chessboard[i][j] = '.';

        List<List<String>> rs = new ArrayList<>();
        LinkedList<Position> prePosition = new LinkedList<Position>();

        solveNQueens(chessboard, prePosition, 0, n, rs);
        return rs;
    }


    class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private void solveNQueens(char[][] chessboard, LinkedList<Position> prePosition, int i, int n, List<List<String>> rs) {
        if (i == n) {
            //写List
            List<String> list = new ArrayList<>();
            for (int k = 0; k < n; k++) {
                list.add(new String(chessboard[k]));
            }
            rs.add(list);
            return;
        }
        boolean flag;
        for (int j = 0; j < n; j++) {
            flag = true;
            if (!prePosition.isEmpty()) {
                for (Position position : prePosition) {
                    if (position.x == i || position.y == j || i - position.x == Math.abs(j - position.y)) {
                        flag = false;
                        break;
                    }
                }
            }
            if (flag) {
                chessboard[i][j] = 'Q';
                prePosition.addLast(new Position(i, j));
                solveNQueens(chessboard, prePosition, i + 1, n, rs);
                prePosition.pollLast();
                chessboard[i][j] = '.';
            }
        }
    }
}
