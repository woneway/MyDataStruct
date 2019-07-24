package algorithm.impl;

/**
 * @Description: 分治法
 * @Author: woneway
 * @Date: 2019/7/24 9:30
 */


/**
 * 一、基本思想：
 * 将规模为n的大问题分解成多个规模k的小问题，然后依次求解小问题并合并结果解决大问题
 *
 * 二、基本特征：
 * 1.问题缩小到一定规模容易解决
 *
 * 2.分解成的子问题是相同种类的子问题，即该问题具有最优子结构性质(问题的最优解包含的子问题的解也是最优的)
 *
 * 3.分解而成的小问题在解决之后要可以合并
 *
 * 4.子问题是相互独立的，即子问题之间没有公共的子问题
 *
 * 三、时间复杂度分析：
 * 分治法解决问题所消耗的时间 = 解决子问题所需的工作总量（由 子问题的个数、解决每个子问题的工作量 决定）+ 合并所有子问题所需的工作量
 *
 * 四、分治的具体过程大致如下：
 *
 * 　　 begin 　｛分治过程开始｝
 * 　　　 if ①问题不可分 then ②返回问题解 　
 * 　　　　 else begin
 * 　　　　　　 　　 ③从原问题中划出含一半运算对象的子问题1；
 * 　　　　　　 　　 ④递归调用分治法过程，求出解1；
 * 　　　　　　 　　 ⑤从原问题中划出含另一半运算对象的子问题2；
 * 　　　　　　 　　 ⑥递归调用分治法过程，求出解2；
 * 　　　　　　 　　 ⑦将解1、解2组合成整修问题的解； 　
 * 　　　　　　　 end;
 * 　　 end; {结束}
 */
public class DivideAndConquer {

    /**
     * -----------------------
     * ------- 1.求众数 -------
     * -----------------------
     * 给定一个大小为 n 的数组，找到其中的众数。众数是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。
     *
     * 你可以假设数组是非空的，并且给定的数组总是存在众数。
     *
     * 示例 1:
     *
     * 输入: [3,2,3]
     * 输出: 3
     * 示例 2:
     *
     * 输入: [2,2,1,1,1,2,2]
     * 输出: 2
     *
     *
     * 思路分析：
     * 求解长度为n的数组中的众数，可以分解为求解左半边数组的众数a和右半边数组中的众数b，如果a==b,则返回a或者b，即a或者b为最后的众数，
     * 如果a!=b,则比较a和b出现的次数，即最后的众数 = count(a)>count(b)?a:b;
     * 当问题分解到数组中只有一个元素的时候，明显有该元素为众数的结论。
     */

    public int majorityElement(int[] nums) {
        return nums[majorityElement(nums,0,nums.length-1)];
    }

    /**
     * 求众数的下标
     * @param nums 数组
     * @param low 最左边的元素
     * @param high 最右边的元素
     * @return
     */
    private int majorityElement(int[] nums, int low, int high) {
        if (low == high) return low;//求解问题
        int mid = (low + high) / 2;
        //拆分问题
        int max1 = majorityElement(nums, low, mid);
        int max2 = majorityElement(nums, mid + 1, high);
        //子问题解合并
        if (max1 == max2) return max1;
        int count = 0;
        for (int i = low; i <= high; i++) {
            if (nums[i] == nums[max1]) count++;
            else if (nums[i] == nums[max2]) count--;
        }
        return count > 0 ? max1 : max2;
    }
}
