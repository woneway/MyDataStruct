package leetcode.impl;

/**
 * @Description:
 * @Author: woneway
 * @Date: 2019/8/8 11:26
 */
public class Code53最大子序和 {

    /**
     * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * <p>
     * 示例:
     * <p>
     * 输入: [-2,1,-3,4,-1,2,1,-5,4],
     * 输出: 6
     * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
     * <p>
     * <p>
     * 思路分析：
     * 采用逐步试探的方式,sum：当前最大值,trySum：试探子序和
     * 当trySum试探到<=0的情况，若当前元素值大于已试探值，则更新试探值为当前值
     * 当trySum>0，则继续试探
     * 当trySum>sum时,sum = trySum
     *
     * trySum = {
     *     trySum + num[i]   if trySum>0
     *     num[i]            if trySum<=0 && num[i]>trySum
     *     trySum            其他情况
     * }
     * 时间复杂度O(n)
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        int i = 1, trySum = nums[0];
        int sum = trySum;//最大子序和
        while (i < nums.length) {
            if (trySum <= 0) {
                if (nums[i] > trySum) {
                    trySum = nums[i];
                }
            } else {
                trySum += nums[i];
            }
            if (trySum > sum) {
                sum = trySum;
            }
            i++;
        }
        return sum;
    }
}
