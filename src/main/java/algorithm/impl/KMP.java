package algorithm.impl;

/**
 * @Description: KMP算法
 * @Author: woneway
 * @Date: 2019/7/30 16:44
 */
public class KMP {
    /**
     * Kmp算法是一种模式匹配算法。
     *
     * 它通过对模式串的分析，解决了简单字符串匹配算法中需要回溯指针的问题。
     */

    /**
     * 求模式串的next数组
     * next[j] = {
     * -1   ,j =0
     * 前缀后缀相等的最大长度
     * 0    ,其他
     * }
     *
     * @param pattern
     * @return
     */
    public int[] getNext(String pattern) {
        int[] next = new int[pattern.length()];
        next[0] = -1;
        int i = 1, j = 0;
        while (i < pattern.length() - 1) {
            if (j == -1 || pattern.charAt(i) == pattern.charAt(j)) {
                ++i;
                ++j;
                next[i] = j;
            } else {
                j = next[j];
            }
        }
        return next;
    }


    /**
     * 假设模式串a a a a b,原串aaabaaac
     * next为： 0 1 2 3 1
     * 所以当匹配到 a a a b a a a c
     * a a a a
     * 的时候，j = next[j] = next[3] = 3
     * 所以模式串向右移动一位，此时第三个a与第四个a相等，所以显然不等于b
     * 而这么显然的步骤，我们通过改进next函数来优化掉
     * <p>
     * 当我们需要移动指针的时候，如果现在所匹配的字符和接下来移动到这个位置的字符相等，则在向右移动
     */
    public int[] getNext_optim(String pattern) {
        int[] next = new int[pattern.length()];
        next[0] = -1;
        int i = 1, j = 0;
        while (i < pattern.length() - 1) {
            if (j == -1 || pattern.charAt(i) == pattern.charAt(j)) {
                ++i;
                ++j;
                next[i] = j;
                if (pattern.charAt(j) == pattern.charAt(i)) {
                    next[i] = next[j];
                } else {
                    next[i] = j;
                }
            } else {
                j = next[j];
            }
        }
        return next;
    }


    /**
     * 匹配字符串
     *
     * @param source
     * @param pattern
     * @return
     */
    public int indexOf(String source, String pattern) {
        int i = 0, j = 0;
//        int[] next = getNext(pattern);
        int[] next = getNext_optim(pattern);

        while (i < source.length()) {
            if (j == -1 || source.charAt(i) == pattern.charAt(j)) {
                ++i;
                ++j;
                if (j == pattern.length()) return i - j;
            } else {
                j = next[j];
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        KMP kmp = new KMP();
        int index = kmp.indexOf("abaababac", "abaa");
        System.out.println(index);
    }
}
