package glist.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 广义表相关实现
 * @Author: woneway
 * @Date: 2019/8/6 14:23
 */
public class GListOperate {

    /**
     * 求广义表的深度
     *
     * @param gList
     * @return
     */
    public int maxDepth(GList gList) {
        if (gList.elemTag.equals(GListType.ATOM) && gList.tp == null) {
            return 0;
        }

        GList hp = gList.hp;
        if (gList.elemTag.equals(GListType.List) && hp == null && gList.tp == null) {
            return 1;
        }

        int max = 0; //max为其hp的深度和同一层结点的深度的最大值
        if (gList.elemTag.equals(GListType.List) && hp != null) {
            max = maxDepth(hp);
        }

        while (hp != null && hp.tp != null) {
            max = Math.max(max, maxDepth(hp.tp));
            hp = hp.tp;
        }


        if (gList.elemTag.equals(GListType.List)) {
            return max + 1;
        } else {
            return max;
        }
    }

    /**
     * 创建一个广义表
     * 基本操作：若str = () 返回空表
     * 若str = a,a为一个元素 返回一个原子
     *
     * @param glistStr
     * @return
     */
    public GList<String> createGList(String glistStr) {
        if (glistStr.length() < 2) {
            return new GList<>(glistStr, null);
        }
        GList<String> gList = new GList<>();//空表
        if ("()".equals(glistStr)) return gList;

        int splitIndex = findFirstIndex(glistStr);//第一个子串的后一个位置

        String hSub = glistStr.substring(1, splitIndex); //第一个子串

        GList<String> hpList = createGList(hSub);
        gList.hp = hpList;

        List<String> subs = getSubs(glistStr.substring(splitIndex + 1));

        int i = 0;
        while (i < subs.size()) {
            hpList.tp = createGList(subs.get(i));
            hpList = hpList.tp;
            ++i;
        }

        return gList;
    }

    /**
     * 获取广义表的长度
     * 例如()长度为0，(a)长度为1，((),(),())长度为3
     * 广义表的长度即最外层元素的个数
     */
    public int lengthOfGList(GList gList) {
        int length = 0;
        if (gList.elemTag.equals(GListType.List) && gList.hp == null) return 0;

        GList hp = gList.hp;

        while (hp != null) {
            ++length;
            hp = hp.tp;
        }
        return length;
    }


    public String head(String glist) {
        if (glist.length() <= 2) return "";
        return glist.substring(1, findFirstIndex(glist));
    }


    public String tail(String glist) {
        if (glist.length() <= 2) return "";
        int firstIndex = findFirstIndex(glist);
        if (firstIndex >= glist.length() - 1) return "";
        return "(" + glist.substring(firstIndex + 1);
    }

    /**
     * 获取剩余的所有子串
     *
     * @param sub
     * @return
     */
    private List<String> getSubs(String sub) {
        List<String> subs = new ArrayList<>();
        if ("".equals(sub)) return subs;
        int end = 0;
        int count = 0;
        int start = 0;
        while (end < sub.length()) {
            char ch = sub.charAt(end);
            if (ch == ',' && count == 0) {
                subs.add(sub.substring(start, end));
                start = end + 1;
            } else if (ch == '(') ++count;
            else if (ch == ')') --count;
            end++;
        }

        if (count == 0) subs.add(sub.substring(start, end));
        else subs.add(sub.substring(start, end - 1));
        return subs;
    }

    /**
     * 显然，glistStr长度大于2,并且不等于()
     * glistStr 形式为 (a,....) 或((),....) 或 ((a),....) 或((a,b,c...),....) 等
     *
     * @param glistStr
     * @return
     */
    public int findFirstIndex(String glistStr) {
        int i = 1, count = 0;
        char ch;
        do {
            ch = glistStr.charAt(i);
            if (ch == '(') ++count;
            else if (ch == ')') --count;
        } while (i++ < glistStr.length() - 1 && (ch != ',' || count != 0));

        return i - 1;
    }

    public static void main(String[] args) {
        //（a，（b，c，d））元素

        GListOperate operate = new GListOperate();
        String A = "()";
        String B = "(a)";
        String C = "(())";
        String D = "(a,b,c,d)";
        String E = "((),(a,d,c))";
        String F = "((a),(b,c,d))";
        String G = "((a,b,c),(d,e,f))";
        String H = "((a,b,c),d,(e,f,g))";
        String I = "((a),(b,c,()))";
        String J = "(((a,(a,(())))),())";
        String K = "((a,(d,(e,(f)))),(b,c,()))";

        List<String> list = new ArrayList<>();
        list.add(A);
        list.add(B);
        list.add(C);
        list.add(D);
        list.add(E);
        list.add(F);
        list.add(G);
        list.add(H);
        list.add(I);
        list.add(J);
        list.add(K);

        for (String str : list) {
//            int firstIndex = operate.findFirstIndex(str);
            GList<String> gList = operate.createGList(str);
            int depth = operate.maxDepth(gList);
            int len = operate.lengthOfGList(gList);
            System.out.println(str + "  depth is: " + depth + "  len is:" + len);

            System.out.println("head is:" + operate.head(str));
            System.out.println("tail is:" + operate.tail(str));
            System.out.println();

        }
    }
}
