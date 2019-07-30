package sort;

import java.util.Random;

/**
 * @Description:
 * @Author: woneway
 * @Date: 2019/7/30 14:15
 */
public class SortBasic {
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

    public void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
}
