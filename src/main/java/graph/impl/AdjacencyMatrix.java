package graph.impl;

import java.util.*;

/**
 * @Description: 邻接矩阵实现
 * @Author: woneway
 * @Date: 2019/7/17 14:48
 */
public class AdjacencyMatrix<E> {

    private final static int DEFAULT_CAPACITY = 10;//最大顶点数

    private ArrayList<E> nodeList;//顶点集

    transient int[][] matrix;//邻接矩阵

    private int nodeSize;//当前顶点数量

    private int size;//可存放顶点最大数量

    private int edgeSize;//当前边数量

    private boolean flag;//false  无向图  true有向图

    private boolean[] visited;//是否已经访问记录

    private final static float THRESHOLD = 0.75f;

    /**
     * initialCapacity为当前最大顶点数
     *
     * @param initialCapacity
     */
    public AdjacencyMatrix(int initialCapacity) {
        if (initialCapacity > 0) {
            this.matrix = new int[initialCapacity][initialCapacity];
        } else if (initialCapacity == 0) {
            this.matrix = new int[0][0];
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        }
        this.size = matrix.length;
        this.nodeList = new ArrayList<>(initialCapacity);
        this.edgeSize = 0;
        this.nodeSize = 0;
        this.flag = false;
    }

    /**
     * @param initialCapacity
     * @param flag
     */
    public AdjacencyMatrix(int initialCapacity, boolean flag) {
        this(initialCapacity);
        this.flag = flag;
    }

    /**
     * 得到结点的个数
     *
     * @return
     */
    public int getNodeSize() {
        return nodeSize;
    }

    /**
     * 得到边的数目
     *
     * @return
     */
    public int getEdgeSize() {
        return edgeSize;
    }

    /**
     * 返回第i个订点的数据
     *
     * @param index
     * @return
     */
    public E getNodeByIndex(int index) {
        return nodeList.get(index);
    }

    /**
     * 返回边<e1,e2>的权值
     * 若边<e1,e2>不存在，则返回-1
     *
     * @param e1
     * @param e2
     * @return
     */
    public int getWeight(E e1, E e2) {
        if (!nodeList.contains(e1)) return -1;
        if (!nodeList.contains(e2)) return -1;
        return matrix[nodeList.indexOf(e1)][nodeList.indexOf(e2)];
    }

    /**
     * 插入结点
     *
     * @param e
     * @return
     */
    public void insertNode(E e) {
        int oldCapacity = matrix.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (nodeSize >= oldCapacity * THRESHOLD) {
            int[][] newMatrix = new int[newCapacity][newCapacity];
            for (int i = 0; i < oldCapacity; i++) {
                newMatrix[i] = Arrays.copyOf(matrix[i], newCapacity);
            }
            matrix = newMatrix;
            this.size = newCapacity;
        }
        ++nodeSize;
        nodeList.add(e);
    }

    /**
     * 插入边
     *
     * @param e1
     * @param e2
     * @param weight
     * @return
     */
    public void insertEdge(E e1, E e2, int weight) {
        if (!nodeList.contains(e1)) insertNode(e1);
        if (!nodeList.contains(e2)) insertNode(e2);
        int i = nodeList.indexOf(e1);
        int j = nodeList.indexOf(e2);
        if (matrix[i][j] == 0) {
            ++edgeSize;
        }
        if (flag) {
            matrix[i][j] = weight;
        } else {
            matrix[i][j] = weight;
            matrix[j][i] = weight;
        }
    }

    /**
     * 插入边 无权重
     *
     * @param e1
     * @param e2
     * @return
     */
    public void insertEdge(E e1, E e2) {
        if (!nodeList.contains(e1)) insertNode(e1);
        if (!nodeList.contains(e2)) insertNode(e2);
        int i = nodeList.indexOf(e1);
        int j = nodeList.indexOf(e2);
        if (matrix[i][j] == 0) {
            ++edgeSize;
        }
        if (flag) {
            matrix[i][j] = 1;
        } else {
            matrix[i][j] = 1;
            matrix[j][i] = 1;
        }
    }

    /**
     * 根据下标插入边
     *
     * @param from
     * @param to
     */
    public void insertEdge(int from, int to) {
        if (matrix[from][to] == 0) {
            ++edgeSize;
        }
        if (flag) {
            matrix[from][to] = 1;
        } else {
            matrix[from][to] = 1;
            matrix[to][from] = 1;
        }
    }

    /**
     * 根据下标插入边 权重
     *
     * @param from
     * @param to
     * @param weight
     */
    public void insertEdge(int from, int to, int weight) {
        if (matrix[from][to] == 0) {
            ++edgeSize;
        }
        if (flag) {
            matrix[from][to] = weight;
        } else {
            matrix[from][to] = weight;
            matrix[to][from] = weight;
        }
    }

    /**
     * 删除结点
     *
     * @param t
     * @return
     */
    public void deleteNode(E t) {
        int index = nodeList.indexOf(t);
        nodeList.remove(t);
        for (int i = 0; i < nodeSize; i++) {
            if (flag) {
                if (matrix[i][index] != 0) --edgeSize;
                if (matrix[index][i] != 0) --edgeSize;
            } else {
                if (matrix[i][index] != 0) --edgeSize;
            }
        }
        for (int i = index; i < nodeSize - 1; i++) {
            for (int j = 0; j < nodeSize; j++) {
                matrix[i][j] = matrix[i + 1][j];
            }
        }
        for (int j = index; j < nodeSize - 1; j++) {
            for (int i = 0; i < nodeSize - 1; i++) {
                matrix[i][j] = matrix[i][j + 1];
            }
        }

        for (int i = 0; i < nodeSize; i++) {
            matrix[i][nodeSize - 1] = matrix[nodeSize - 1][i] = 0;
        }
        --nodeSize;
    }

    /**
     * 删除边
     *
     * @param e1
     * @param e2
     * @return
     */
    public void deleteEdge(E e1, E e2) {
        int i = nodeList.indexOf(e1);
        int j = nodeList.indexOf(e2);
        if (flag) {
            matrix[i][j] = 0;
        } else {
            matrix[i][j] = 0;
            matrix[j][i] = 0;
        }
        --edgeSize;
    }

    /**
     * 随机生成一个图
     */
    public void randomInitGraph() {
        for (int i = 1; i <= size; i++) {
            E e = (E) new Integer(i);
            nodeList.add(e);
            ++nodeSize;
        }

        Random random = new Random();

        if (flag) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    int val = random.nextInt(100);
                    if (val % 2 == 0 && i != j) {
                        matrix[i][j] = val;
                        ++edgeSize;
                    }
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                for (int j = i + 1; j < size; j++) {
                    int val = random.nextInt(100);
                    if (val % 2 == 0) {
                        matrix[i][j] = 1;
                        matrix[j][i] = 1;
                        ++edgeSize;
                    }
                }
            }
        }
    }


    /**
     * 递归的方式进行深度优先遍历
     */
    public void dfs() {
        System.out.println("深度优先遍历所有顶点:");
        if (nodeSize == 0) return;
        visited = new boolean[nodeSize];
        for (int i = 0; i < nodeSize; i++) {
            if (!visited[i]) {
                dfs(i);
            }
        }
        System.out.println();
    }

    /**
     * 利用队列进行广度优先遍历
     */
    public void bfs() {
        System.out.println("广度优先遍历所有顶点:");
        if (nodeSize == 0) return;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        visited = new boolean[nodeSize];
        visited[0] = true;
        while (!queue.isEmpty()) {
            Integer head = queue.remove();
            toVisit(head);
            for (int i = 0; i < nodeSize; i++) {
                if (!visited[i] && matrix[head][i] != 0) {
                    queue.add(i);
                    visited[i] = true;
                }
            }
        }
        System.out.println();
    }


    /**
     * 获取从t1到t2的简单路径（即无环路径）
     * 利用深度优先遍历改造
     *
     * @param e1
     * @param e2
     */
    public void getEasyPath(E e1, E e2) {
        int head = nodeList.indexOf(e1);
        int tail = nodeList.indexOf(e2);
        if (head == -1 || tail == -1) return;
        visited = new boolean[nodeSize];
        LinkedList<Integer> path = new LinkedList<Integer>();
        System.out.println("找到顶点" + e1 + "到顶点" + e2 + "的所有简单路径:");
        getEasyPath(path, head, tail);
    }

    /**
     * 单源最短路径  Dijkstra算法
     * 将顶点集分为两部分（已加入判断最小路径的顶点集，未加入判断最小路径的顶点集）
     */
    public void getSingleSourceShortestPaths(E source) {
        int sourceIndex = nodeList.indexOf(source);
        if (sourceIndex == -1) return;
        Set<Integer> V = new HashSet<>();//已加入顶点
        Set<Integer> U_V = new HashSet<>();//已加入顶点

        V.add(sourceIndex);
        int[] lowCost = new int[nodeSize];
        for (int i = 0; i < nodeSize; i++) {
            lowCost[i] = Integer.MAX_VALUE;
            if (i != sourceIndex) {
                U_V.add(i);
            }
        }
        lowCost[sourceIndex] = 0;
        int cur = sourceIndex;
        String[] lowPath = new String[nodeSize];
        lowPath[sourceIndex] = nodeList.get(sourceIndex).toString();
        int prev = -1;

        while (!U_V.isEmpty()) {
            if (prev == cur) {
                System.out.println("存在不可达到的顶点:" + nodeList.get(cur).toString());
                return;
            }
            prev = cur;
            U_V.remove(cur);
            V.add(cur);
            for (int i = 0; i < nodeSize; i++) {
                if (matrix[cur][i] != 0 && i != sourceIndex && U_V.contains(i)) {
//                    lowCost[i] = Math.min(lowCost[i], lowCost[cur] + matrix[cur][i]);
                    if (lowCost[i] > lowCost[cur] + matrix[cur][i]) {
                        lowCost[i] = lowCost[cur] + matrix[cur][i];
                        lowPath[i] = lowPath[cur] + "->" + nodeList.get(i).toString();
                    }
                }
            }

            int minCost = Integer.MAX_VALUE;
            for (int i = 0; i < nodeSize; i++) {
                if (i != sourceIndex && U_V.contains(i) && minCost > lowCost[i]) {
                    cur = i;
                    minCost = lowCost[i];
                }
            }
        }


        System.out.println("从源点" + nodeList.get(sourceIndex).toString() + "到各点的最短路径：");
        for (int i = 0; i < nodeSize; i++) {
            System.out.println(nodeList.get(i).toString() + ":" + lowPath[i] + ";len:" + lowCost[i]);
        }
    }


    /**
     * 最小生成树 prim算法
     */
    public void minSpanningTreeByPrim() {
        int[][] minMatrix = new int[nodeSize][nodeSize];
        Set<Integer> inSet = new HashSet<>();
        inSet.add(0);
        while (inSet.size() < nodeSize) {
            int min = Integer.MAX_VALUE;//最小权重临界边的头部顶点
            int head = -1;
            for (Integer cur : inSet) {
                for (int i = 0; i < nodeSize; i++) {
                    if (!inSet.contains(i) && matrix[cur][i] != 0 && (min == Integer.MAX_VALUE || matrix[head][min] > matrix[cur][i])) {
                        head = cur;
                        min = i;
                    }
                }
            }
            inSet.add(min);
            minMatrix[head][min] = matrix[head][min];
        }
        printMatrix(minMatrix);
    }


    /**
     * 最小生成树   克鲁斯卡尔算法  考虑有向图
     */
    public void minSpanningTreeByKrus() {
        List<Edge<Integer>> edgeList = new ArrayList<>();

        for (int i = 0; i < nodeSize; i++) {
            for (int j = 0; j < nodeSize; j++) {
                if (matrix[i][j] != 0) {
                    edgeList.add(new Edge<>(i, j, matrix[i][j]));
                }
            }
        }
        edgeList.sort(Comparator.comparingInt(o -> o.weight));
        int count = 1;
        int[][] newMatrix = new int[nodeSize][nodeSize];
        while (edgeList.size() > 0 && count < nodeSize) {
            Edge<Integer> minEdge = edgeList.remove(0);
            //如果加入不产生环路则加入
            if (isCircle(newMatrix, minEdge.head, minEdge.tail, true)) {
                count++;
                newMatrix[minEdge.tail][minEdge.head] = matrix[minEdge.tail][minEdge.head];
            }
        }

        if (count == nodeSize) {
            printMatrix(newMatrix);
        }
    }


    /**
     * 关键路径
     */
    public void criticalPath() {
        int[] ve = new int[nodeSize];
        int[] vl = new int[nodeSize];
        for (int i = 1; i < nodeSize; i++) {
            ve[i] = Integer.MIN_VALUE;
        }
        for (int i = 0; i < nodeSize; i++) {
            vl[i] = Integer.MAX_VALUE;
        }
        //1.根据拓扑排序求ve
        LinkedList<Integer> topoPath = topologicalOrder(ve);
        //2.根据逆拓扑排序求vl
        vl[nodeSize - 1] = ve[nodeSize - 1];
        topoPath.pollLast();
        while (!topoPath.isEmpty()) {
            Integer lastIndex = topoPath.pollLast();
            for (int i = 0; i < nodeSize; i++) {
                if (matrix[lastIndex][i] != 0) {
                    vl[lastIndex] = Math.min(vl[lastIndex], vl[i] - matrix[lastIndex][i]);
                }
            }
        }

        List<EdgeTopo<Integer>> edgeList = new ArrayList<>();

        //根据事件的最早发生时间ve和最迟发生时间vl求活动的最早发生时间ee和最迟发生时间el
        //活动（i->eIdx）最早开始时间：事件（顶点） i最早开始时间
        //活动（i->eIdx）最迟开始时间：事件（顶点） eIdx 最迟开始时间 减去 活动持续时间
        //也就是ee(i,j) = ve(i),   el(i,j) = vl(j) - <i,j>.weight;


        //活动的最早发生时间是开始事件的最早发生时间，即开始事件发生之后即可展开相应的活动
        //活动的最晚发生时间是活动结束事件的最晚发生时间减去活动所消耗的时间

        for (int i = 0; i < nodeSize; i++) {
            for (int j = 0; j < nodeSize; j++) {
                if (i != j && matrix[i][j] != 0) {
                    edgeList.add(new EdgeTopo(i, j, ve[i], vl[j] - matrix[i][j]));
                }
            }
        }
        for (int i = 0; i < edgeList.size(); i++) {
            EdgeTopo<Integer> edgeTopo = edgeList.get(i);
            if (edgeTopo.ee == edgeTopo.el) {
                System.out.print(nodeList.get(edgeTopo.tail) + "->" + nodeList.get(edgeTopo.head) + ";");
            }
        }
    }

    private LinkedList<Integer> topologicalOrder(int[] ve) {
        ve[0] = 0;//源点设为0

        //ve(k) = Math.max(ve(j)+dut(<j,k>));
        //1.利用队列存储入度为0的点
        int[] indegree = new int[nodeSize];
        LinkedList<Integer> noneIndegree = new LinkedList<>();
        LinkedList<Integer> path = new LinkedList<>();

        for (int col = 0; col < nodeSize; col++) {
            boolean flag = false;
            for (int row = 0; row < nodeSize; row++) {
                if (matrix[row][col] != 0) {
                    ++indegree[col];
                    flag = true;
                }
            }
            if (!flag) {
                noneIndegree.addLast(col);
            }
        }
        while (!noneIndegree.isEmpty()) {
            Integer noneNode = noneIndegree.pollFirst();
            indegree[noneNode] = -1;
            path.addLast(noneNode);
            for (int i = 0; i < nodeSize; i++) {
                if (matrix[noneNode][i] != 0) {
                    ve[i] = Math.max(ve[i], ve[noneNode] + matrix[noneNode][i]);
//                    matrix[noneNode][i] = 0;
                    --indegree[i];
                }
            }
            if (noneIndegree.isEmpty()) {
                for (int i = 0; i < nodeSize; i++) {
                    if (indegree[i] == 0) {
                        noneIndegree.addLast(i);
                    }
                }

            }
        }
        printTopologicalOrder(path);
        return path;
    }


    /**
     * 拓扑排序
     * 对有向图来说，每次选择入度为0的顶点删除并移除关联的边
     * 重复此操作，直至没有入度为0的边或者没有顶点
     */
    public LinkedList<Integer> topologicalOrder() {
        //1.利用队列存储入度为0的点
        int[] indegree = new int[nodeSize];
        LinkedList<Integer> noneIndegree = new LinkedList<>();
        LinkedList<Integer> path = new LinkedList<>();

        for (int col = 0; col < nodeSize; col++) {
            boolean flag = false;
            for (int row = 0; row < nodeSize; row++) {
                if (matrix[row][col] != 0) {
                    ++indegree[col];
                    flag = true;
                }
            }
            if (!flag) {
                noneIndegree.addLast(col);
            }
        }
        while (!noneIndegree.isEmpty()) {
            Integer noneNode = noneIndegree.pollFirst();
            indegree[noneNode] = -1;
            path.addLast(noneNode);
            for (int i = 0; i < nodeSize; i++) {
                if (matrix[noneNode][i] != 0) {
//                    matrix[noneNode][i] = 0;
                    --indegree[i];
                }
            }
            if (noneIndegree.isEmpty()) {
                for (int i = 0; i < nodeSize; i++) {
                    if (indegree[i] == 0) {
                        noneIndegree.addLast(i);
                    }
                }

            }
        }
        printTopologicalOrder(path);
        return path;
    }


    /**
     * 打印邻接矩阵
     */
    public void printMatrix() {
        System.out.println("打印" + (flag ? "有向图" : "无向图") + "邻接矩阵:");
        for (int i = 0; i < nodeSize; i++) {
            for (int j = 0; j < nodeSize; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void printMatrix(int[][] matrix) {
        System.out.println("打印最小生成树矩阵:");
        for (int i = 0; i < nodeSize; i++) {
            for (int j = 0; j < nodeSize; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void dfs(int i) {
        toVisit(i);
        visited[i] = true;
        for (int j = 0; j < nodeSize; j++) {
            if (!visited[j] && matrix[i][j] != 0) {
                dfs(j);
            }
        }
    }

    private void toVisit(int i) {
        System.out.print(nodeList.get(i).toString() + " ");
    }

    private void getEasyPath(LinkedList<Integer> path, int head, int tail) {
        visited[head] = true;
        path.addLast(head);
        for (int i = 0; i < nodeSize; i++) {
            if (!visited[i] && matrix[head][i] != 0) {
                if (i == tail) {
                    path.addLast(i);
                    printPath(path);
                } else {
                    getEasyPath(path, i, tail);//遍历完成，将i顶点出栈，并访问标志置为假
                    visited[i] = false;
                    path.pollLast();
                }
            }
        }
    }

    private void printPath(LinkedList<Integer> path) {
        for (Integer aPath : path) {
            System.out.print(nodeList.get(aPath) + " ");
        }
        path.pollLast();//将终点移除
        System.out.println();
    }

    private boolean isCircle(int[][] newMatrix, int tail, int target, boolean first) {
        if (!first && tail == target) {
            return false;
        }
        for (int i = 0; i < nodeSize; i++) {
            if (newMatrix[tail][i] != 0) {
                return isCircle(newMatrix, i, target, false);
            }
        }
        //tail作为弧尾没有边则返回true
        return true;
    }

    private void printTopologicalOrder(LinkedList<Integer> path) {
        System.out.println("拓扑序列:");
        StringBuilder builder = new StringBuilder();

        for (Integer node : path) {
            builder.append(nodeList.get(node).toString()).append("->");
        }
        int i = builder.lastIndexOf("->");
        builder.delete(i, i + 2);
        System.out.println(builder.toString());
    }


    public static void main(String[] args) {
        AdjacencyMatrix<String> undirectedGraph = new AdjacencyMatrix<>(4);
        undirectedGraph.insertNode("A");
        undirectedGraph.insertNode("B");
        undirectedGraph.insertNode("C");
        undirectedGraph.insertEdge("A", "B");
        undirectedGraph.insertEdge("A", "C");
        undirectedGraph.insertEdge("B", "C");
        undirectedGraph.insertNode("D");

        undirectedGraph.insertEdge("B", "D");
        undirectedGraph.insertEdge("D", "C");
        undirectedGraph.insertEdge("C", "A");
        undirectedGraph.insertEdge("C", "B");
        undirectedGraph.insertNode("E");
        undirectedGraph.insertEdge("D", "E");
        undirectedGraph.deleteNode("A");

        AdjacencyMatrix<String> directedGraph = new AdjacencyMatrix<>(4, true);
        directedGraph.insertNode("A");
        directedGraph.insertNode("B");
        directedGraph.insertNode("C");
        directedGraph.insertNode("D");
        directedGraph.insertNode("E");
        directedGraph.insertNode("F");

        directedGraph.insertEdge("A", "B", 12);
        directedGraph.insertEdge("A", "C", 5);
        directedGraph.insertEdge("A", "D", 13);
        directedGraph.insertEdge("B", "E", 4);
        directedGraph.insertEdge("B", "D", 6);
        directedGraph.insertEdge("C", "D", 5);
        directedGraph.insertEdge("D", "B", 1);
        directedGraph.insertEdge("D", "E", 9);
        directedGraph.insertEdge("D", "F", 10);

        directedGraph.deleteNode("F");


        AdjacencyMatrix<String> randomUnDirectedGraph = new AdjacencyMatrix<>(12);
        AdjacencyMatrix<Integer> randomDirectedGraph = new AdjacencyMatrix<>(12, true);
        randomUnDirectedGraph.randomInitGraph();//随机生成无向图
        randomDirectedGraph.randomInitGraph();//随机生成有向图


//        directedGraph.printMatrix();
//
//        directedGraph.dfs();
//        directedGraph.bfs();
//        directedGraph.getEasyPath("A", "E");
//        directedGraph.getSingleSourceShortestPaths("A");

//        randomDirectedGraph.printMatrix();
//        randomDirectedGraph.getSingleSourceShortestPaths(1);

        AdjacencyMatrix<String> directedGraph2 = new AdjacencyMatrix<>(4, true);
        directedGraph2.insertNode("A");
        directedGraph2.insertNode("B");
        directedGraph2.insertNode("C");
        directedGraph2.insertNode("D");
        directedGraph2.insertNode("E");
        directedGraph2.insertNode("F");
        directedGraph2.insertNode("G");
        directedGraph2.insertNode("H");
        directedGraph2.insertNode("I");

        directedGraph2.insertEdge("A", "B", 6);
        directedGraph2.insertEdge("A", "C", 4);
        directedGraph2.insertEdge("A", "D", 5);
        directedGraph2.insertEdge("B", "E", 1);
        directedGraph2.insertEdge("C", "E", 1);
        directedGraph2.insertEdge("D", "F", 2);
        directedGraph2.insertEdge("E", "G", 8);
        directedGraph2.insertEdge("E", "H", 7);
        directedGraph2.insertEdge("F", "H", 4);
        directedGraph2.insertEdge("G", "I", 2);
        directedGraph2.insertEdge("H", "I", 4);

        directedGraph2.printMatrix();
//        directedGraph2.getSingleSourceShortestPaths("A");
//        directedGraph2.topologicalOrder();

        directedGraph2.criticalPath();


//        directedGraph.printMatrix();
//        directedGraph.minSpanningTreeByPrim();
//        undirectedGraph.printMatrix();
//        undirectedGraph.minSpanningTreeByKrus();

    }
}

class EdgeTopo<E> {
    E tail;
    E head;
    int ee;
    int el;

    public EdgeTopo(E tail, E head, int ee, int el) {
        this.tail = tail;
        this.head = head;
        this.ee = ee;
        this.el = el;
    }
}

class Edge<E> {
    E tail;
    E head;
    int weight;

    public Edge(E tail, E head, int weight) {
        this.tail = tail;
        this.head = head;
        this.weight = weight;
    }
}