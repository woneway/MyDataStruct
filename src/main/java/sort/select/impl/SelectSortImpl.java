package sort.select.impl;

import sort.SortBasic;

/**
 * @Description: 选择排序
 * @Author: woneway
 * @Date: 2019/7/29 16:57
 */
public class SelectSortImpl extends SortBasic {
    /**
     * 选择排序有两种,简单选择排序和堆排序
     * <p>
     * 先来看看简单选择排序，与插入排序相同,简单选择排序将一段序列，分为左边的有序序列，和右边的无序序列两部分。
     * 初识状态，长度为n的数组，无序序列从0到n-1，从这段无序序列中选择一个最小值，然后需第一个元素进行交换，这样
     * 就变成了两段序列，[0,0]的有序序列和[1,n-1]的无序序列。并且无序序列中所有元素都比有序序列要大
     * 假设现在已经有了[0,i-1]的有序序列，那么现在从[i,n-1]中选择一个最小值与i+1个元素交换位置。其中  0<=i<=n-2
     */
    public void simpleSelectSort(int[] arr) {
        int i = 0;
        while (i < arr.length - 1) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min]) min = j;
            }
            if (i != min) {
                swap(arr, i, min);
            }
            i++;
        }
    }


    /**
     * 堆排序是一个性能更好的选择排序方式。
     *
     * 什么是堆？
     * 1)堆可以看做是一个完全二叉树
     * 2)堆中结点的值总是大于或者小于其孩子结点的值
     *
     * 堆排序的核心思想在于最大堆或者最小堆的创建,然后再将最值与待交换的值进行交换。
     *
     * 它将序列分为两部分，左边是无序序列，右边是有序序列。我们以最大堆为例子，将序列排序为从小到大。
     *
     * 如何构建？
     * 以序列 {12,23,11,1,8,17}为例
     * 可以看做是这样一棵树,长度为N = 7
     *          12
     *        /    \
     *       23    11
     *      / \    /
     *     1  8  17
     * 从第 i = N/2 = 2个元素开始,11不大于其孩子结点的值,则交换11和17
     *          12
     *        /    \
     *       23    17
     *      / \    /
     *     1  8  11
     * 然后比较第i = 1个元素,23大于孩子结点的值,不需要交换。
     * 接着比较第i = 0个元素,23和17的最大值是23，大于12,23和12进行交换。
     *          22
     *        /    \
     *       12    11
     *      / \    /
     *     1  8  17
     * 构建完成第一棵最大堆,最大值在i = 0的位置,与无序序列的最后一个元素进行交换。
     *          17
     *        /    \
     *       12    11
     *      / \    /
     *     1  8  22
     * 此时无序序列[0,n-2],有序序列[n-1,n-1]
     * 然后我们从第一个元素向下比较,如果孩子结点的最大值大于当前结点的值,则交换最大值和当前结点的位置 todo
     * @param arr
     */
    public void heapSort(int[] arr) {
        int n = arr.length - 1;//无序序列的最后一个元素下标或者无序序列的长度-1
        while (n > 0) {
            heapSort(arr, 0, n);
            swap(arr, 0, n);
            n--;
        }
    }

    private void heapSort(int[] arr, int i, int n) {
        for (int k = n / 2; k >= 0; k--) {
            int key = arr[k];
            int children = 2 * k;//指向左孩子
            if (children < n && arr[children] < arr[children + 1]) children++;//存在右孩子并且值大于左孩子
            if (arr[k] < arr[children]) {
                swap(arr, k, children);
            }
        }
    }


    public static void main(String[] args) {
        SelectSortImpl sort = new SelectSortImpl();
        int[] random = sort.random(100);
        int[] copy = sort.copy(random);

        sort.simpleSelectSort(random);
        sort.print(random);
        sort.heapSort(copy);
        sort.print(copy);
        System.out.println(sort.judgeArrSort(random, true));
        System.out.println(sort.isSame(random, copy));
    }
}
