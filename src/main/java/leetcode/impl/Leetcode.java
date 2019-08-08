package leetcode.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: woneway
 * @Date: 2019/8/8 10:25
 */
public class Leetcode {
    public static void main(String[] args) {
        Leetcode leetcode = new Leetcode();
//        List<String> list = leetcode.letterCasePermutation("a1b2");
        int ch = (int) '0';
        System.out.println(ch);
    }

    public List<String> letterCasePermutation(String S) {
        List<String> ans = new ArrayList<String>();
        dg(S.toCharArray(), 0, ans);
        return ans;
    }

    public void dg(char[] s, int i, List<String> ans) {
        if (i == s.length) {
            ans.add(String.valueOf(s));
            return;
        }
        dg(s, i + 1, ans);
        if (s[i] < '0' || s[i] > '9') {
            s[i] ^= (1 << 5);
            dg(s, i + 1, ans);
        }

    }
}
