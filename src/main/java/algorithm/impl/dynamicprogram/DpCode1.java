package algorithm.impl.dynamicprogram;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: dp相关编码题集1
 * @Author: woneway
 * @Date: 2019/8/1 9:33
 */
public class DpCode1 {
    /**
     * 一、最长回文子序列
     *
     * 给定一个字符串s，找到其中最长的回文子序列。可以假设s的最大长度为1000。
     *
     * 示例 1:
     * 输入:
     *
     * "bbbab"
     * 输出:
     *
     * 4
     * 一个可能的最长回文子序列为 "bbbb"。
     *
     * 示例 2:
     * 输入:
     *
     * "cbbd"
     * 输出:
     *
     * 2
     * 一个可能的最长回文子序列为 "bb"。
     *
     *
     *
     */


    /**
     * 思路一，根据动态规划的思想，我们用max记录当前最大长度，用dList数组存储长度为n的回文状态
     * <p>
     * 显然d1[i][i] = true;
     * <p>
     * 长度为2时d2[i][j] = si == sj
     * <p>
     * 长度为3时d3[i][j] = si == sj && d1[k][m]   i<k<=m<j
     * <p>
     * 长度为n时,dn[i][j] = si == sj && dn-1[k][m] i<k<=m<j
     * <p>
     * 但是这种算法时间复杂度太高
     *
     * @param s
     * @return
     */
    @Deprecated
    public int longestPalindromeSubseq_deprecated(String s) {
        int s_len = s.length();
        if (s_len <= 1) return s_len;

        List<boolean[][]> dList = new ArrayList();
        int max = 1;
        boolean[][] d1 = new boolean[s_len][s_len];
        boolean[][] d2 = new boolean[s_len][s_len];
        for (int i = 0; i < s_len; i++) {
            for (int j = i; j < s_len; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    if (i == j) {
                        d1[i][j] = true;
                    } else {
                        d2[i][j] = true;
                        max = 2;
                    }
                }
            }
        }

        dList.add(d1);
        dList.add(d2);

        int len = 3;
        boolean flag;
        boolean[][] dLen;
        while (len <= s_len) {
            dLen = new boolean[s_len][s_len];
            boolean[][] dLen_1 = dList.get(len - 3);
            for (int i = 0; i < s_len; i++) {
                for (int j = len + i - 1; j < s_len; j++) {
                    if (s.charAt(i) == s.charAt(j)) {
                        flag = true;
                        for (int k = i + 1; k < j && flag; k++) {
                            for (int m = len + k - 3; m < j; m++) {
                                if (dLen_1[k][m]) {
                                    dLen[i][j] = true;
                                    max = len;
                                    flag = false;
                                    if (max == s_len) return s_len;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            dList.add(dLen);
            len++;
        }
        return max;
    }


    /**
     * 换种思路，将长度存储下来
     * 显然d[i][i] = 1
     *
     * 若si == sj, d[i][j] = d[i+1][j-1]+2;
     * 若si <> sj, d[i][j] = max(d[i+1][j],d[i][j-1])
     *
     */

    public int longestPalindromeSubseq(String s) {
        int s_len = s.length();
        int[][] d = new int[s_len][s_len];

        for (int i = s_len - 1; i >= 0; i++) {
            d[i][i] = 1;
            for (int j = i + 1; j < s_len; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    d[i][j] = d[i + 1][j - 1] + 2;
                } else {
                    d[i][j] = Math.max(d[i + 1][j], d[i][j - 1]);
                }
            }
        }
        return d[0][s_len - 1];
    }


}
