package sort.insert.impl;

/**
 * @Description:
 * @Author: woneway
 * @Date: 2019/7/28 17:10
 */
public class InsertSort {
    /**
     * 插入排序是指从未排序的序列中选择一个最小值或者最大值插入到已排序的序列中
     * 常见的插入类排序有：
     * 1.直接插入法
     * 2.折半插入法
     * 3.表插入法
     * 4.希尔排序(缩小增量排序)
     * 下面来依次实现以下这四种插入类排序算法
     */

    /**
     * 直接插入法
     * 将无序数组排序为不递减的数组
     *
     * @param arr
     */
    public void straightInsertionSort(int[] arr) {
        int i = 1, j = 0;
        for (i = 1; i < arr.length; i++) {
            int cur = arr[i];
            for (j = i - 1; j >= 0; j--) {
                if (cur < arr[j]) {
                    arr[j + 1] = arr[j];
                } else break;
            }
            arr[j + 1] = cur;
        }
    }

    /**
     * 折半插入，对直接插入算法比较部分进行了优化
     *
     * @param arr
     */
    public void binaryInsertionSort(int[] arr) {
        int i = 1, j = 0;
        for (i = 1; i < arr.length; i++) {
            int cur = arr[i];
            //寻找cur需要插入的位置
            int low = 0, high = i - 1;

            while (low <= high) {
                int mid = (low + high) / 2;
                if (cur < arr[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            //插入位置为low
            for (j = i - 1; j >= low; j--) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = cur;
        }
    }


    public void print(int[] arr) {
        System.out.println("输出结果:");
        for (int anArr : arr) {
            System.out.print(anArr + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int test[] = {11, 2, 34, 7, 1, 9, 12, 11, 56, -2, 4};
        int test2[] = {11, 2, 34, 7, 1, 9, 12, 11, 56, -2, 4};

        InsertSort insertSort = new InsertSort();
        insertSort.straightInsertionSort(test);
        insertSort.binaryInsertionSort(test2);
        insertSort.print(test);
        insertSort.print(test2);
    }


}
