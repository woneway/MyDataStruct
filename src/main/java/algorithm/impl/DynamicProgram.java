package algorithm.impl;

/**
 * @Description: 动态规划算法
 * @Author: woneway
 * @Date: 2019/7/25 13:57
 */
public class DynamicProgram {
    /**
     * 一、基本思想
     * 将问题分解成多个子问题，自底向上通过对子问题的求解的合并逐步推出最终问题的解，其子问题之间不要求相互独立。
     * 动态规划算法的核心在于去减少子问题的重复计算。
     *
     *
     * 二、基本步骤
     * 找出最优解的性质，并刻画其结构特征（寻找最优解的子问题结构）
     * 递归的定义最优值（根据子问题结构建立问题的递归解式求解最优值）
     * 以自底向上的方式计算出最优值（动态规划思想）
     * 根据计算最优值时得到的信息，构造最优解
     *
     */

    /**
     * -----------------------------
     * ------- 1.最长回文子串 -------
     * -----------------------------
     *
     * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
     *
     * 示例 1：
     *
     * 输入: "babad"
     * 输出: "bab"
     * 注意: "aba" 也是一个有效答案。
     * 示例 2：
     *
     * 输入: "cbbd"
     * 输出: "bb"
     */
    /**
     * 首先我们用暴力解法解决这个问题
     *
     * @param s
     * @return
     */
    public String longestPalindrome_force(String s) {
        if (s == null || "".equals(s)) return s;
        char[] chars = s.toCharArray();
        String rs = chars[0] + "";
        for (int i = 0; i < chars.length - 1; i++) {
            for (int j = i + 1; j < chars.length; j++) {
                //判断从i到j是否是回文串
                int start = i, end = j;
                while (start < end) {
                    if (chars[start] == chars[end]) {
                        start++;
                        end--;
                    } else break;
                }
                if (start >= end && j - i + 1 > rs.length()) {
                    //是回文并且更长
                    rs = new String(chars, i, j - i + 1);
                }
            }
        }
        return rs;
    }


    /**
     * 分析可知，暴力解法的时间复杂度为O(n^3)
     * 以"babad"为例，假使我们知道了ba不是回文，自然也就知道了abad不是回文，像这种就不需要重复去计算了
     * 我们发现在暴力解法中，我们用动态规划的思想来解决这个问题
     * <p>
     * P(i,j) = { true  ai,ai+1,...aj是回文
     * |
     * { false ai,ai+1,...aj不是回文
     * <p>
     * 显然 P(i,i) = true;  P(i,i+1) = (ai == ai+1);由此，长度为1和长度为2的串是否是回文就已经判断出来了
     * 继续判断长度为3的串，p(i-1,i+1) = (ai-1 == ai+1) && p(i,i);
     * 推得 p(i,j) = p(i+1,j-1) && ai == aj
     */
    public String longestPalindrome_dp(String s) {
        if (s == null || "".equals(s)) return s;
        char[] chars = s.toCharArray();
        int length = chars.length;
        boolean[][] p = new boolean[length][length];
        int start = 0, count = 1;
        for (int i = 0; i < length; i++) {
            p[i][i] = true;//len = 1
            if (i < length - 1) {
                p[i][i + 1] = chars[i] == chars[i + 1];//len=2
                if (p[i][i + 1]) {
                    start = i;
                    count = 2;
                }
            }
        }
        int len = 3;
        while (len <= length) {
            for (int i = 0; i < length - len + 1; i++) {
                p[i][i + len - 1] = p[i + 1][i + len - 2] && chars[i] == chars[i + len - 1];
                if (p[i][i + len - 1]) {
                    start = i;
                    count = len;
                }
            }
            len++;
        }
        return new String(chars, start, count);
    }


    /**
     * 对dp算法进行分析，第一个遍历执行n次，while语句执行n-2次，for语句执行了n-len+1次
     * 所以最终时间复杂度O(n) = n^2
     * 该题还有中心扩展算法等解法。
     */

    /**
     * 下面这种算法是中心扩展算法
     * 中心存在两种情况1个中心或者两个元素构成一个中心
     * 即  ... A ... 或 ... A A ...
     * 长度为n的字符串，中心共有n + n-1 = 2n -1个
     */
    public String longestPalindrome_center(String s) {
        if (s == null || "".equals(s)) return s;
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            int len1 = expandAroundCenter(s, i, i);
            int len2 = expandAroundCenter(s, i, i + 1);
            int len = Math.max(len1, len2);
            if (len > end - start) {
                /**
                 * --------------- |  |  ------------
                 * start           i  j          end
                 * j = i 或 j = i+1
                 * 则有
                 * end - start = len
                 * i - start = end - j
                 * 求得
                 * start = (i+j-len)/2
                 * 假设i = k,
                 * 当j = k + 1,则一定有len = 长度 - 1 = 奇数
                 *  (1) start = (2k + 1 - len)/2
                 * 当j = k,则一定有len = 长度 - 1 = 偶数
                 *  (2) start = (2k - len) / 2
                 * 举例：12345是回文的下标
                 * 则i = 3,len = 5-1 = 4,start1 = 1,start2 =1都满足
                 * 123456是回文的下标
                 * 则i = 3,len = 6-1 = 5,start1 = 1,start2 =0,后者不满足
                 * 综上，式(1)为通用式
                 * start = k - (len - 1)/2
                 * end = len + start
                 */
                start = i - (len - 1) / 2;
                end = len + start;
            }
        }
        return s.substring(start, end + 1);
    }

    //获取从left,right扩展的回文长度-1
    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }

    public static void main(String[] args) {
        DynamicProgram dynamicProgram = new DynamicProgram();
        //dynamicProgram.longestPalindrome_force("babad");
        //dynamicProgram.longestPalindrome_dp("bb");
        dynamicProgram.longestPalindrome_center("bb");
    }

}
