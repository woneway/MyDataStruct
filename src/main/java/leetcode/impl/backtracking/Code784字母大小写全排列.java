package leetcode.impl.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 字母大小写全排列
 * @Author: woneway
 * @Date: 2019/8/8 15:31
 */
public class Code784字母大小写全排列 {
    /**
     * 给定一个字符串S，通过将字符串S中的每个字母转变大小写，我们可以获得一个新的字符串。返回所有可能得到的字符串集合。
     * <p>
     * 示例:
     * 输入: S = "a1b2"
     * 输出: ["a1b2", "a1B2", "A1b2", "A1B2"]
     * <p>
     * 输入: S = "3z4"
     * 输出: ["3z4", "3Z4"]
     * <p>
     * 输入: S = "12345"
     * 输出: ["12345"]
     * 注意：
     * <p>
     * S 的长度不超过12。
     * S 仅由数字和字母组成。
     */
    /**
     * 思路分析：
     * 依次遍历每个字符，如果是字母，则分为两个串，并继续处理下一个字符，直到没有字符处理
     * 然后回溯，将当前字符之后的串处理得到的list，拼接到当前字符后，直到第一个字符
     *
     * @param S
     * @return
     */
    public List<String> letterCasePermutation(String S) {
        List<String> list = new ArrayList<>();
        if (S.length() == 0) return list;
        char head = S.charAt(0);
        boolean letter = Character.isLetter(head);

        if (letter) {
            list.add(String.valueOf(Character.toLowerCase(head)));
            list.add(String.valueOf(Character.toUpperCase(head)));
        } else {
            list.add(String.valueOf(head));
        }

        if (S.length() >= 2) {
            List<String> childList = letterCasePermutation(S.substring(1));
            List<String> rs = new ArrayList<>();
            for (String h : list) {
                for (String t : childList) {
                    rs.add(h + t);
                }
            }
            list = rs;
        }
        return list;
    }


    /**
     * 思路分析：
     * 第二种方式，将串转换成一个char数组，如果不是字母，则继续处理下一个字符，如果是字母，则一分为二，递归大写字母和小写字母
     * 最终chars数组会在每一个递归函数的结束生成，然后加入到list中
     * <p>
     * chars[i] ^= (1 << 5) 这句是很有意思的一个地方,我们来看下
     * 'a'  97  对应的ascii码是 1100001
     * 'A'  65  对应的ascii码是 1000001
     * 两者相差32 = 1<<5 = 100000
     *
     * 字符char采用的是Unicode编码的16位字符类型，其表示范围是0-65536。
     * 标准的8位ASCII字符集是Unicode的子集，其取值范围为0-127。也就是000 0000 ~ 111 1111
     *
     * 1100001 ^ 0100000 = 1000001 = 'A'
     * 1000001 ^ 0100000 = 1100001 = 'a'
     * 所以字符ch ^ (1<<5)会将大小写进行互换
     * @param S
     * @return
     */
    public List<String> letterCasePermutation_2(String S) {
        List<String> list = new ArrayList<>();
        dfs(S.toCharArray(), 0, list);
        return list;
    }


    public void dfs(char[] chars, int i, List<String> list) {
        if (i == chars.length) {
            list.add(new String(chars));
            return;
        }
        dfs(chars, i + 1, list);
        if (chars[i] < '0' || chars[i] > '9') {
            chars[i] ^= (1 << 5);
            dfs(chars, i + 1, list);
        }
    }

}
