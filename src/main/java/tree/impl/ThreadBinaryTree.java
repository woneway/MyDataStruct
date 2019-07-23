package tree.impl;

/**
 * @Description: 线索二叉树
 * @Author: woneway
 * @Date: 2019/7/23 15:08
 */
public class ThreadBinaryTree<E> {
    /**
     * 线索二叉树的数据结构：
     * --------------------------------------------------
     * |lchild | lTag | data | rTag | rchild|
     * --------------------------------------------------
     * 可以通过先中后序遍历建立线索二叉树，采用中序为例：
     * 记ptr指向二叉树中的一个结点，则建立线索二叉树的规则如下：
     * 1.若ptr->lchild == null，则存放指向中序遍历序列中该结点的前驱结点。该结点称为ptr的中序前驱。
     * 2.若ptr->rchild == null，则存放指向中序遍历序列中该结点的后继结点。该结点称为ptr的中序后继。
     * ltag和rtag用来区分是线索还是孩子
     * 0表示孩子，1表示线索
     */
    protected int lTag;
    protected int rTag;
    protected E data;
    protected ThreadBinaryTree lchild;
    protected ThreadBinaryTree rchild;
    private ThreadBinaryTree prev = null;

    private final static int THREAD = 1;
    private final static int LINK = 0;

    public ThreadBinaryTree() {
    }

    public ThreadBinaryTree(E data) {
        this.data = data;
    }

    /**
     * 线索化二叉树
     *
     * @param tree
     * @return
     */
    public ThreadBinaryTree threadBinaryTree(BinaryTree tree) {
        //1.复制二叉树
        ThreadBinaryTree threadBinaryTree = copyBinaryTree(tree);
        inThreading(threadBinaryTree);
        return threadBinaryTree;
    }

    /**
     * 线索化二叉树  含有头结点
     *
     * @param tree
     * @return
     */
    public ThreadBinaryTree threadBinaryTreeWithHead(BinaryTree tree) {
        ThreadBinaryTree root = new ThreadBinaryTree();
        //1.复制二叉树
        ThreadBinaryTree threadBinaryTree = copyBinaryTree(tree);
        prev = root;//prev指向头结点
        inThreading(threadBinaryTree);
        //头结点左指针指向二叉树的根节点，ltag = LINK
        root.lchild = threadBinaryTree;
        root.lTag = LINK;
        //头结点右指针指向二叉树的最后一个结点，即prev，rtag = THREAD
        root.rchild = prev;
        root.rTag = THREAD;
        //最后一个结点的右孩子指向头结点，rtag=THREAD
        prev.rchild = root;
        prev.rTag = THREAD;
        return root;
    }

    /**
     * 中序线索二叉树
     * 几点需要注意：
     * 1.prev用来线索化结点右孩子为空的情况，因为是中序遍历，利用prev保存右孩子的双亲，线索完成后更新prev为当前访问的结点，然后访问下一个结点
     * 2.完成线索化时最后一个结点的右孩子为空的情况没有线索，默认为null，如果是头结点的线索二叉树，需要将他指向头结点
     *
     * @param threadBinaryTree
     */
    public void inThreading(ThreadBinaryTree threadBinaryTree) {
        if (threadBinaryTree != null) {
            inThreading(threadBinaryTree.lchild);
            if (threadBinaryTree.lchild == null) {
                threadBinaryTree.lchild = prev;
                threadBinaryTree.lTag = THREAD;
            }
            if (prev != null && prev.rchild == null) {
                prev.rchild = threadBinaryTree;
                prev.rTag = THREAD;
            }
            prev = threadBinaryTree;
            inThreading(threadBinaryTree.rchild);
        }
    }

    public ThreadBinaryTree copyBinaryTree(BinaryTree tree) {
        if (tree == null) return null;
        ThreadBinaryTree root = new ThreadBinaryTree(tree.data);
        root.lchild = copyBinaryTree(tree.left);
        root.rchild = copyBinaryTree(tree.right);
        return root;
    }


    /**
     * 中序遍历线索二叉树
     */
    public void inOrderTraverse() {
        ThreadBinaryTree root = this.lchild;
        while (root != this) {

            while (root.lTag == LINK) {
                root = root.lchild;
            }
            visitNode(root);
            while (root.rTag == THREAD && root.rchild != this) {
                root = root.rchild;
                visitNode(root);
            }
            root = root.rchild;
        }
    }

    private void visitNode(ThreadBinaryTree node) {
        System.out.print(node.data + " ");
    }


    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.scanfCreateTree(String.class);
        ThreadBinaryTree threadBinaryTree = new ThreadBinaryTree();
        threadBinaryTree = threadBinaryTree.threadBinaryTreeWithHead(binaryTree);
        threadBinaryTree.inOrderTraverse();
    }
}
