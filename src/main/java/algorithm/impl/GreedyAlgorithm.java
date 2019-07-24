package algorithm.impl;

/**
 * @Description: 贪心算法
 * @Author: woneway
 * @Date: 2019/7/24 14:16
 */

import java.util.*;

/**
 * 一、基本思想
 * 找出整体当中每个小的局部的最优解，并且将所有的这些局部最优解整合起来形成一个整体的最优解。
 *
 * 二、贪心算法解决什么问题
 * 1.整体的最优解可以通过局部的最优解求出
 * 2.一个整体能够被分为多个局部，并且这些局部能够求出最优解。
 *
 * 贪心算法的核心在于贪心策略的设计
 *
 * 三、实现该算法的过程
 *
 * 从问题的某一初始解出发;
 * while 能朝给定总目标前进一步 do
 * 求出可行解的一个解元素;
 * 由所有解元素组合成问题的一个可行解。
 *
 *
 * 四、基本要素
 * 性质：贪心选择性质（每次选择局部最优解）和最优子结构性质
 *
 * 四、经典问题：背包问题、活动安排问题
 */
public class GreedyAlgorithm {
    /**
     * -----------------------
     * ---- 1.活动安排问题 -----
     * -----------------------
     * 多个活动需要共用同一个事务，比如上不同的课需要用到同一间教室，这个时候就需要保证不同活动
     * 的活动时间不存在交集。
     *
     * 每个活动i都有一个要求使用该资源的起始时间si和一个结束时间fi，且si＜fi。
     *
     * 如果选择了活动i，则它在半开时间区间[si ，fi )内占用资源。
     *
     * 若区间[si ，fi )与区间[sj，fj )不相交，则称活动i与活动j是相容的。当 si ≥ fj 或 sj ≥ fi 时，活动i与活动j相容。
     *
     * 活动安排问题就是在所给的活动集合中选出最大的相容活动子集合
     *
     */
    class Activity{
        /**
         * 定义一个活动的数据结构Activity
         * 比如start:0 end:1,index:1表示活动1从第0小时开始到第1小时结束。
         * 比如start:1 end:3,index:2表示活动2从第1小时开始到第3小时结束。
         * 比如start:2 end:3,index:3表示活动1从第2小时开始到第3小时结束。
         * 则活动1和活动2是相容的，活动1和活动3是相容的，活动2和活动3是不相容的
         */
        int start;//活动开始时间/h
        int end;//活动结束时间/h
        int index;

        public Activity(int start, int end, int index) {
            this.start = start;
            this.end = end;
            this.index = index;
        }
    }


    /**
     * 自动生成activityNum个活动
     * 开始时间范围是[0-13]
     * 结束时间范围是[1-14]
     * 单个活动时间范围不超过7小时
     * @param activityNum
     * @return
     */
    public List<Activity> randomInitActivity(int activityNum) {
        List<Activity> list = new ArrayList<>();
        if (activityNum<=0) return list;
        Random random = new Random();
        for (int i = 0; i < activityNum; i++) {
            int start = random.nextInt(15) - 1;//[0-13]
            int end = random.nextInt(15);//[1-14]
            while (end <= start || end - start > 7 || start < 0) {
                start = random.nextInt(15) - 1;
                end = random.nextInt(15);
            }
            list.add(new Activity(start, end, i));

        }
        return list;
    }


    /**
     * 获取最大的相容活动子集合
     * 思路：
     * 将活动按照结束时间进行排序，结束时间早的排在前面
     * 将第一个活动入栈，然后依结束时间选择下一个活动并且与前一个活动时间没有交集。
     * 此处利用栈的特性，每次入栈与最后放进去的活动比较
     * @return
     */
    public LinkedList<Activity> getMaxActivitySet(int activityNum) {
        //1.初始化一些活动
        List<Activity> activityList = randomInitActivity(activityNum);
        //2.对这些活动进行排序，结束时间早的排在结束时间晚的前面
        activityList.sort((o1, o2) -> o1.end - o2.end);
        LinkedList<Activity> stack = new LinkedList<>();
        stack.addLast(activityList.get(0));
        for (int i = 1; i < activityNum; i++) {
            Activity preActivity = stack.peekLast();//上一个放入的活动
            Activity curActivity = activityList.get(i);//当前结束时间最早的活动
            if (curActivity.start >= preActivity.end) {
                stack.addLast(curActivity);
            }
        }
        return stack;
    }





    public static void main(String[] args) {
        GreedyAlgorithm greedyAlgorithm = new GreedyAlgorithm();
        greedyAlgorithm.getMaxActivitySet(8);
    }



}
