package sort.exchange.impl;

import java.util.Random;

/**
 * @Description: 交换排序
 * @Author: woneway
 * @Date: 2019/7/29 17:01
 */
public class ExchangeSortImpl {
    /**
     * 交换排序，顾名思义，就是通过元素之间位置的交换完成排序
     * 通常有两种，冒泡排序和快速排序。
     */

    /**
     * 先来看看第一种，冒泡排序。
     * 它的思想比较简单，冒泡排序将整个序列分为左边无序序列和右边有序序列。
     * 以非递减排序(元素从左到右依次非递减)为例。
     * 将无序序列每两个元素进行比较，若前者大于后者，则交换，依次向后比较，最后一次元素，即无序序列中的
     * 最大值，放在有序序列的第一个位置。
     */
    public void bubbleSort(int[] arr) {
        int i = arr.length;//上界
        while (i > 1) {
            int lastExchangeIndex = 1;//最后一次交换的位置的上界<j-1,j>
            for (int j = 1; j < i; j++) {
                if (arr[j - 1] > arr[j]) {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    lastExchangeIndex = j;
                }
            }
            i = lastExchangeIndex;//如果后续没有进行过交换，则表示序列已经是有序的，i = 1,结束循环
        }
    }

    /**
     * 快速排序的思想，就是选择一个中枢元素，将比它小的值交换到左边，比它大的元素交换到右边
     * 采用分治法的思想，依次对左边的序列和右边的序列进行上述操作。
     *
     *
     * type = 1,中枢值每次选取的是分表的第一个元素,这种情况下，如果序列本身就是有序的
     * 比如 1,2,3,4,5,6,7,8,9,10。这个时候我们发现，这种算法成了单支树,即，每次part成了中枢本身，
     * 中枢的左边没有元素，所有元素都大于它。其时间复杂度为O(n^2)。退化成了冒泡排序
     *
     * 对其进行改进，type = 2时，每次选择的时候low high mid三个位置的元素的中间值作为中枢，这样起码能够降低递归树的深度
     * 提升效率。
     *
     * 对于快速排序的时间分析，分为两个部分，第一个是对当前这段序列左右交换所花费的时间，第二个是对中枢元素左右序列排序所花费的时间
     * T(N) = Tex(N)+Tl(N)+Tr(N)
     *
     *
     * 最好的情况，递归树是一个满二叉树，对于N个值，树的深度是log(N+1),而每次操作交换需要的时间可看做是O(N)
     * 所以O(N) = N*logN
     *
     * @param arr
     */

    public void quickSort(int[] arr, int type) {
        if (type == 1) {
            quickSort(arr, 0, arr.length - 1);
        } else {
            quickSort2(arr, 0, arr.length - 1);
        }
    }

    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int part = partition(arr, low, high);
            quickSort(arr, low, part - 1);
            quickSort(arr, part + 1, high);
        }

    }


    private void quickSort2(int[] arr, int low, int high) {
        if (low < high) {
            int part = partition2(arr, low, high);
            quickSort2(arr, low, part - 1);
            quickSort2(arr, part + 1, high);
        }

    }

    private int partition(int[] arr, int low, int high) {
        int key = arr[low];
        while (low < high) {
            while (low < high && arr[high] >= key) high--;
            arr[low] = arr[high];
            while (low < high && arr[low] <= key) low++;
            arr[high] = arr[low];
        }
        arr[low] = key;
        return low;
    }

    private int partition2(int[] arr, int low, int high) {
        midIndex(arr, low, high);
        int key = arr[low];

        while (low < high) {
            while (low < high && arr[high] >= key) high--;
            arr[low] = arr[high];
            while (low < high && arr[low] <= key) low++;
            arr[high] = arr[low];
        }
        arr[low] = key;
        return low;
    }


    private void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }


    /**
     * low存放中间值
     * @param arr
     * @param low
     * @param high
     */
    private void midIndex(int[] arr, int low, int high) {
        int mid = (low + high) / 2;
        if (arr[low] <= arr[mid] && arr[mid] <= arr[high]
                || arr[high] <= arr[mid] && arr[mid] <= arr[low]) {
            swap(arr, mid, low);
        }

        if (arr[low] <= arr[high] && arr[high] <= arr[mid]
                || arr[mid] <= arr[high] && arr[high] <= arr[low]) {
            swap(arr, low, high);
        }
    }


    public void print(int[] arr) {
        System.out.println("输出结果:");
        for (int anArr : arr) {
            System.out.print(anArr + " ");
        }
        System.out.println();
    }

    public int[] random(int n) {
        int[] arr = new int[n];
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(1000);
        }
        return arr;
    }

    public int[] copy(int[] arr) {
        int[] another = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            another[i] = arr[i];
        }
        return another;
    }

    /**
     * 判断arr排序
     *
     * @param arr
     * @param desc true 非递增  false 非递减
     * @return
     */
    public boolean judgeArrSort(int[] arr, boolean desc) {
        if (desc) {
            for (int i = 1; i < arr.length; i++) {
                if (arr[i] < arr[i - 1]) {
                    return false;
                }
            }
        } else {
            for (int i = 1; i < arr.length; i++) {
                if (arr[i] > arr[i - 1]) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isSame(int[] arr1, int[] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        int test[] = {11, 2, 34, 7, 1, 9, 12, 11, 56, -2, 4};

        ExchangeSortImpl sort = new ExchangeSortImpl();
        sort.bubbleSort(test);
        sort.print(test);


        int[] testArr = sort.random(100000);
        int[] testCpy = sort.copy(testArr);

        sort.quickSort(testArr, 1);
        sort.quickSort(testCpy, 2);

        boolean b = sort.judgeArrSort(testArr, true);
        boolean b1 = sort.judgeArrSort(testCpy, true);
        boolean same = sort.isSame(testArr, testCpy);
        System.out.println(b + " " + b1 + " " + same);
    }

}
