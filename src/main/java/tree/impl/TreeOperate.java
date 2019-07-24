package tree.impl;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Description: 二叉树的相关应用
 * @Author: woneway
 * @Date: 2019/7/23 16:51
 */
public class TreeOperate {


    /**
     * 获取二叉树的深度
     * 思路：
     * 根节点空，则深度为0；
     * 根节点没有左右子树，则深度为1
     * 根节点有左右子树，则深度为左右子树的深度较大的加1
     *
     * @param tree
     */
    public int getDepth(BinaryTree tree, boolean recursion) {
        if (recursion) return getDepth_Recursion(tree);
        return getDepth_Interation(tree);
    }

    public int getDepth_Recursion(BinaryTree tree) {
        if (tree == null) return 0;
        return Math.max(getDepth_Recursion(tree.left), getDepth_Recursion(tree.right)) + 1;
    }

    public int getDepth_Interation(BinaryTree tree) {
        if(tree == null) return 0;


        return 0;
    }

    /**
     * 判断tree1和tree2是否镜像
     *       1                1
     *      / \              / \
     *     2   3            3   2
     *    / \ / \          / \ / \
     *   4  5 6  7        7  6 5  4
     *
     * @param tree1
     * @param tree2
     * @return
     */
    public boolean isMirror(BinaryTree tree1, BinaryTree tree2) {
        Queue<BinaryTree> queue = new LinkedList<>();
        queue.add(tree1);
        queue.add(tree2);
        while (!queue.isEmpty()) {
            tree1 = queue.remove();
            tree2 = queue.remove();
            if (tree1==null&&tree2==null) continue;
            if (tree1 == null || tree2 == null || !tree1.data.equals(tree2.data))return false;
            queue.add(tree1.left);
            queue.add(tree2.right);
            queue.add(tree1.right);
            queue.add(tree2.left);
        }
        return true;
    }



    /**
     * 获取tree的镜像树
     * @param tree
     * @return
     */
    public BinaryTree getMirror(BinaryTree tree) {
        if (tree == null) return null;
        BinaryTree root = new BinaryTree(tree.data);
        root.left = getMirror(tree.right);
        root.right = getMirror(tree.left);
        return root;
    }

    public static void main(String[] args) {
        TreeOperate treeOperate = new TreeOperate();
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.scanfCreateTree(String.class,3);
        System.out.println(treeOperate.getDepth(binaryTree, true));

        BinaryTree binaryTree1 = new BinaryTree();
        binaryTree1.scanfCreateTree(String.class, 4);
        System.out.println(treeOperate.isMirror(binaryTree, treeOperate.getMirror(binaryTree)));
    }

}
