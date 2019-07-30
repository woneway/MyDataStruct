package sort.exchange.impl;

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
     * @param arr
     */

    public void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int part = partition(arr, low, high);
            quickSort(arr, low, part - 1);
            quickSort(arr, part + 1, high);
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


    private int midIndex(int[] arr, int low, int high) {
        int mid = (low + high) / 2;
        if (arr[low] <= arr[mid] && arr[mid] <= arr[high]
                || arr[high] <= arr[mid] && arr[mid] <= arr[low]) {
            return mid;
        }

        if (arr[low] <= arr[high] && arr[high] <= arr[mid]
                || arr[mid] <= arr[high] && arr[high] <= arr[low]) {
            return high;
        }
        return low;
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

        ExchangeSortImpl sort = new ExchangeSortImpl();
        sort.bubbleSort(test);
        sort.print(test);
        sort.quickSort(test2);
        sort.print(test2);

    }

}
