package tree.impl;

/**
 * @Description: 二叉树的相关应用
 * @Author: woneway
 * @Date: 2019/7/23 16:51
 */
public class TreeOperate {


    /**
     * 获取二叉树的深度
     *
     * @param tree
     */
    public int getDepth(BinaryTree tree) {
        if (tree == null) return 0;
        return Math.max(getDepth(tree.left), getDepth(tree.right)) + 1;
    }

    public static void main(String[] args) {
        TreeOperate treeOperate = new TreeOperate();
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.scanfCreateTree(String.class);
        System.out.println(treeOperate.getDepth(binaryTree));
    }

}
