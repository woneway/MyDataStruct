package sort.merge.impl;

import sort.SortBasic;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Description:
 * @Author: woneway
 * @Date: 2019/7/30 15:25
 */
public class MergeSort extends SortBasic {

    /**
     * 归并排序是一种自底向上的排序过程，我们把序列分为多个长度为1的序列，则每个序列都是有序的，
     * 然后两个相邻的有序序列合并成一个更大集合的有序序列，以此类推，最终合并成长度为N的序列
     *
     * @param arr
     */
    public void mergeSort(int[] arr, boolean needSpace) {
        mergeSort(arr, 0, arr.length - 1, needSpace);
    }

    private void mergeSort(int[] arr, int low, int high, boolean needSpace) {
        if (low < high) {
            int mid = (low + high) / 2;
            mergeSort(arr, low, mid, needSpace);
            mergeSort(arr, mid + 1, high, needSpace);
            if (needSpace) {
                merge(arr, low, mid, high);
            } else {
                merge2(arr, low, mid, high);
            }
        }
    }

    /**
     * 利用队列归并
     *
     * @param arr
     * @param low
     * @param mid
     * @param high
     */
    private void merge(int[] arr, int low, int mid, int high) {
        //合并两个序列low--mid   mid+1--high
        int i = low, j = mid + 1;
        Queue<Integer> queue = new LinkedList();
        while (i <= mid && j <= high) {
            if (arr[i] < arr[j]) {
                queue.add(arr[i++]);
            } else if (arr[i] > arr[j]) {
                queue.add(arr[j++]);
            } else {
                queue.add(arr[i++]);
                queue.add(arr[j++]);
            }
        }
        while (i <= mid) {
            queue.add(arr[i++]);
        }

        while (j <= high) {
            queue.add(arr[j++]);
        }

        for (int k = low; k <= high && !queue.isEmpty(); k++) {
            arr[k] = queue.remove();
        }
    }


    /**
     * 不利用其他的空间归并
     *
     * @param arr
     * @param low
     * @param mid
     * @param high
     */
    private void merge2(int[] arr, int low, int mid, int high) {
        int i = mid + 1;
        while (i <= high) {
            int cur = arr[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (arr[j] > cur) arr[j + 1] = arr[j];
                else break;
            }
            arr[j + 1] = cur;
            i++;
        }
    }


    public static void main(String[] args) {
        MergeSort sort = new MergeSort();
        int[] random = sort.random(7);
        int[] copy = sort.copy(random);
        sort.print(random);
        sort.mergeSort(random, true);
        sort.mergeSort(copy, false);
        sort.print(random);
        sort.print(copy);
    }

}
