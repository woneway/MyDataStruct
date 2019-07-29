package sort.insert.impl;

/**
 * @Description:
 * @Author: woneway
 * @Date: 2019/7/28 17:10
 */
public class InsertSortImpl {
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
     * 折半插入，对直接插入算法比较部分进行了优化,但是移动元素的部分是一样的
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


    /**
     * 表插入算法
     * 利用静态链表的存储结构，优化插入元素时需要移动元素的缺点
     * @param arr
     */
    public void tableInsertSort(int[] arr) {
        //init table
        Table[] tables = new Table[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            tables[i + 1] = new Table(arr[i]);
        }
        tables[0] = new Table(Integer.MAX_VALUE, 1);//头结点
        tables[1].next = 0;

        //start sort
        for (int i = 2; i < arr.length + 1; i++) {
            //找到插入位置
            Integer p = tables[0].next;
            Integer prev = 0;
            int cur = tables[i].data;
            while (tables[p].next != 0 && tables[p].data <= cur) {
                prev = p;
                p = tables[p].next;
            }

            if (cur >= tables[p].data) {
                //更新链尾部
                tables[p].next = i;
                tables[i].next = 0;
            } else if (tables[0].next == p) {
                //说明当前cur是最小的值,更新链头部
                tables[i].next = tables[0].next;
                tables[0].next = i;
            } else {
                //插入到中间某个位置
                tables[prev].next = i;
                tables[i].next = p;
            }
        }

        Integer p = 0, q = 0;
        for (int i = 1; i < arr.length; i++) {
            while (p < i) p = tables[p].next;//如果p<i，说明下一个被交换到后面去了
            q = tables[p].next;

            if (p != i) {
                //1.交换data
                int temData = tables[p].data;
                tables[p].data = tables[i].data;
                tables[i].data = temData;
                //2.更新指针p的next指向i的next，i的next指向p的位置
                tables[p].next = tables[i].next;
                tables[i].next = p;
            }
            p = q;
        }
    }

    class Table {
        Integer data;
        Integer next;

        public Table() {
        }

        public Table(Integer data) {
            this.data = data;
        }

        public Table(Integer data, Integer next) {
            this.data = data;
            this.next = next;
        }
    }


    /**
     * 希尔排序(增量递减排序)
     * @param arr
     */
    public void shellSort(int[] arr) {
        int d = 6;//每次d/2
        int i, j;
        while (d > 0) {
            for (i = d; i < arr.length; i++) {
                int cur = arr[i];
                for (j = i - d; j >= 0; j -= d) {
                    if (cur < arr[j]) {
                        arr[j + d] = arr[j];
                    } else break;
                }
                arr[j + d] = cur;
            }
            d /= 2;
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
        int test3[] = {11, 2, 34, 7, 1, 9, 12, 11, 56, -2, 4};

        int table[] = {49, 38, 65, 97, 76, 13, 27, 49};
        InsertSortImpl insertSort = new InsertSortImpl();
        insertSort.straightInsertionSort(test);
        insertSort.binaryInsertionSort(test2);
        insertSort.shellSort(test3);
        insertSort.print(test);
        insertSort.print(test2);
        insertSort.print(test3);

        insertSort.tableInsertSort(table);

    }


}
