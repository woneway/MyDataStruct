package tree.impl;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

/**
 * @Description: 二叉树相关实现
 * @Author: woneway
 * @Date: 2019/7/23 9:14
 */
public class BinaryTree {
    private BinaryTree left;
    private BinaryTree right;
    private Object data;

    public BinaryTree() {
    }

    public BinaryTree(Object data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    public BinaryTree(BinaryTree left, BinaryTree right, Object data) {
        this.left = left;
        this.right = right;
        this.data = data;
    }
    
    /**
     * 先序遍历构造二叉树 深度为3
     * 此二叉树为满二叉树
     */
    public void randomCreateTree() {
        if (data == null) data = 1;
        int curData = Integer.valueOf(data.toString());
        if (2 * curData + 1 > 1 << 4) {
            return;
        }
        this.left = new BinaryTree(2 * curData);
        this.left.randomCreateTree();
        this.right = new BinaryTree(2 * curData + 1);
        this.right.randomCreateTree();
    }

    /**
     * 通过输入完成先序构建二叉树
     * 支持String和整型
     */
    public void scanfCreateTree(Object type) {
//        Scanner scanner = new Scanner(System.in);
        Scanner scanner;
        if (type == String.class) {
            scanner = new Scanner(this.getClass().getResourceAsStream("/wordTreeInput.txt"));
            String next = scanner.next();
            if (next.equals("#")) return;
            data = next;
            left = createTree(scanner, type);
            right = createTree(scanner, type);
        } else if (type == Integer.class) {
            scanner = new Scanner(this.getClass().getResourceAsStream("/numTreeInput.txt"));
            int next = scanner.nextInt();
            if (next == -1) return;
            data = next;
            left = createTree(scanner, type);
            right = createTree(scanner, type);
        }
    }


    private BinaryTree createTree(Scanner scanner, Object type) {
        BinaryTree tree;
        Object next = null;
        if (type == String.class) {
            next = scanner.next();
            if (next.equals("#")) {
                return null;
            }
        } else if (type == Integer.class) {
            next = scanner.nextInt();
            if (next.equals(-1)) return null;
        }
        tree = new BinaryTree(next);
        tree.left = createTree(scanner, type);
        tree.right = createTree(scanner, type);
        return tree;
    }


    /**
     * 先序遍历二叉树
     */
    public void preOrderTraverse(boolean recursion) {
        System.out.println("先序遍历二叉树--" + (recursion ? "递归版本" : "迭代版本"));
        if (recursion) {
            preOrder_Recursion(this);
        } else {
            preOrder_Iteration(this);
        }
        System.out.println();
    }

    /**
     * 先序遍历迭代版本
     */
    private void preOrder_Iteration(BinaryTree tree) {
        Stack<BinaryTree> stack = new Stack<>();
        stack.push(tree);
        while (!stack.isEmpty()) {
            BinaryTree pop = stack.pop();
            visitNode(pop);
            if (pop.right != null) stack.push(pop.right);
            if (pop.left != null) stack.push(pop.left);
        }
    }

    /**
     * 先序遍历递归版本
     */
    private void preOrder_Recursion(BinaryTree tree) {
        if (tree == null) return;
        visitNode(tree);
        preOrder_Recursion(tree.left);
        preOrder_Recursion(tree.right);
    }

    /**
     * 中序遍历二叉树
     */
    public void inOrderTraverse(boolean recursion) {
        System.out.println("中序遍历二叉树--" + (recursion ? "递归版本" : "迭代版本"));
        if (recursion) {
            inOrder_Recursion(this);
        } else {
            inOrder_Iteration(this);
        }
        System.out.println();
    }

    /**
     * 中序遍历迭代版本
     *
     * @param tree
     */
    private void inOrder_Iteration(BinaryTree tree) {
        if (tree == null) return;
        Stack<BinaryTree> stack = new Stack<>();
        stack.push(tree);
        while (tree.left != null) {
            stack.push(tree.left);
            tree = tree.left;
        }

        while (!stack.isEmpty()) {
            BinaryTree pop = stack.pop();
            visitNode(pop);
            if (pop.right != null) {
                BinaryTree cur = pop.right;
                stack.push(cur);
                while (cur.left != null) {
                    stack.push(cur.left);
                    cur = cur.left;
                }
            }
        }
    }

    /**
     * 中序遍历递归版本
     *
     * @param tree
     */
    private void inOrder_Recursion(BinaryTree tree) {
        if (tree == null) return;
        inOrder_Recursion(tree.left);
        visitNode(tree);
        inOrder_Recursion(tree.right);
    }

    /**
     * 后序遍历二叉树
     */
    public void afterOrderTraverse(boolean recursion) {
        System.out.println("后序遍历二叉树--" + (recursion ? "递归版本" : "迭代版本"));
        if (recursion) {
            afterOrder_Recursion(this);
        } else {
            afterOrder_Iteration(this);
        }
        System.out.println();
    }

    /**
     * 后序遍历迭代版本
     *
     * @param tree
     */
    private void afterOrder_Iteration(BinaryTree tree) {
        if (tree == null) return;
        Stack<BinaryTree> stack = new Stack<>();
        putLeftRight(stack, tree);
        BinaryTree prev = null;
        while (!stack.isEmpty()) {
            BinaryTree peek = stack.peek();
            if (peek.right == null || peek.right == prev) {
                visitNode(prev = stack.pop());
            } else {
                putLeftRight(stack, peek.right);
            }
        }

    }

    private void putLeftRight(Stack<BinaryTree> stack, BinaryTree tree) {
        while (tree != null) {
            stack.push(tree);
            while (tree.left != null) {
                stack.push(tree.left);
                tree = tree.left;
            }
            tree = tree.right;
        }
    }

    /**
     * 后序遍历递归版本
     *
     * @param tree
     */
    private void afterOrder_Recursion(BinaryTree tree) {
        if (tree == null) return;
        afterOrder_Recursion(tree.left);
        afterOrder_Recursion(tree.right);
        visitNode(tree);
    }


    /**
     * 层次遍历
     */
    public void levelTraverse() {
        System.out.println("层次遍历二叉树");
        Queue<BinaryTree> queue = new LinkedList<>();
        queue.add(this);
        while (!queue.isEmpty()) {
            BinaryTree first = queue.remove();
            visitNode(first);
            if (first.left != null) queue.add(first.left);
            if (first.right != null) queue.add(first.right);
        }
    }

    private void visitNode(BinaryTree node) {
        System.out.print(node.data + " ");
    }


    public static void main(String[] args) throws FileNotFoundException {
        BinaryTree root = new BinaryTree();
//        root.randomCreateTree();
        root.scanfCreateTree(Integer.class);

        root.preOrderTraverse(true);
        root.preOrderTraverse(false);
        root.inOrderTraverse(true);
        root.inOrderTraverse(false);
        root.afterOrderTraverse(true);
        root.afterOrderTraverse(false);
        root.levelTraverse();

    }
}



