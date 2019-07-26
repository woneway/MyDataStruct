package tree.impl;

/**
 * @Description: 二叉平衡树
 * @Author: woneway
 * @Date: 2019/7/26 9:19
 */
public class BinaryBalanceTree {

    /**
     * 二叉平衡树在插入时通过旋转达到平衡，分为四种情况
     * 1.LL型(右旋)
     *       O  <-T,指向插入结点往上，左右子树深度差绝对值大于1的第一个根节点
     *      /
     *     O  <-lc= T.lChild  lc指向T的左孩子
     *    /
     *   O
     *  举个例子，比如下面这颗二叉查找树(1)，现在是平衡二叉树(插入顺序：9,10,11,8,6)
     *        10
     *       /  \
     *      8    11
     *     / \
     *    6   9
     *        (1)
     *  此时插入值3，结点10左子树深度3，右子树深度1，左右子树深度差的绝对值为2>1,失去平衡。
     * root->  10
     *        /  \
     *  lc-> 8    11
     *      / \
     *     6   9
     *    /
     *   3
     *        (2)
     * 由二叉查找树的特点，根节点左边的结点都小于它的值，右边的结点都大于它的值。然后利用右旋法调整上面这颗失衡的二叉查找树.
     * 最终我们会用lc作为根节点，lc的右孩子作为root的右孩子，root作为lc的右孩子
     * 即  root.lChild = lc.rChild;
     *     lc.rChild = root;
     *     root = lc;
     * 最终调整为
     * root->  8
     *        /  \
     *  lc-> 6    10
     *      /     / \
     *     3     9  11
     *      (3)
     *
     *
     * 同样，还是拿二叉查找树(1作为例子)
     * 插入值7，则结点10左子树深度3，右子树深度1，左右子树深度差的绝对值为2>1,失去平衡。
     * root->  10
     *        /  \
     *  lc-> 8    11
     *      / \
     *     6   9
     *      \
     *      7
     *  这其实也是一个LL型的例子
     * 最终转换为平衡二叉树
     *          8
     *         / \
     *        6   10
     *         \  /\
     *         7 9 11
     * 2.RR型(左旋),同理1
     * 3.LR型(先左旋，再右旋)
     *        O
     *       /
     *      O
     *      \
     *       O
     *  在二叉平衡树(3)的基础上，插入结点4
     *          8
     *        /  \
     * root->6    10
     *      /     / \
     * lc->3     9  11
     *      \
     *      4
     * 明显的，这是LR型，所以我们先左旋，在右旋
     *          8
     *        /  \
     *       6    10
     *      /     / \
     *     4     9  11
     *      \
     *      3
     *          8
     *        /  \
     *       4    10
     *      /\    / \
     *     3  6  9  11
     *
     *
     *
     * 4.RL型(先右旋，再左旋),同理3
     *
     *
     */

    protected Integer data;

    protected BinaryBalanceTree lChild;

    protected BinaryBalanceTree rChild;

    protected int bf;//平衡因子

    private boolean taller = false;

    private boolean shorter = false;

    private final static int LH = 1;
    private final static int RH = -1;
    private final static int EH = 0;

    public BinaryBalanceTree() {
    }

    public BinaryBalanceTree(Integer data) {
        this.data = data;
        this.bf = 0;
        this.lChild = this.rChild = null;
    }

    public BinaryBalanceTree(Integer data, int bf) {
        this.data = data;
        this.bf = bf;
        this.lChild = this.rChild = null;
    }

    public BinaryBalanceTree(Integer data, BinaryBalanceTree lChild, BinaryBalanceTree rChild, int bf) {
        this.data = data;
        this.lChild = lChild;
        this.rChild = rChild;
        this.bf = bf;
    }

    /**
     * 左旋
     * O1  <-T  RH
     *  \
     *   O2  <-lc RH
     *    \
     *     O3     EH
     * 左旋完:
     *      O2   <-T  RH
     *    /    \
     *  O1 RH  O3  EH
     * 所以需要将T结点和T-lchild的bf设置为EH
     *
     * @param T
     */
    private BinaryBalanceTree L_Rotate(BinaryBalanceTree T) {
        BinaryBalanceTree lc = T.rChild;
        T.rChild = lc.lChild;
        lc.lChild = T;
        T = lc;
        T.bf = T.lChild.bf = 0;
        return T;
    }

    /**
     * 右旋
     *     O <-T
     *    /
     *   O <-lc
     *  /
     * O
     * 同理，需要将最终的T结点和T-rchild的bf设置为0
     *
     * @param T
     */
    private BinaryBalanceTree R_Rotate(BinaryBalanceTree T) {
        BinaryBalanceTree lc = T.lChild;
        T.lChild = lc.rChild;
        lc.rChild = T;
        T = lc;
        T.bf = T.rChild.bf = 0;
        return T;
    }

    /**
     * 调整左子树
     * @param T
     * @return
     */
    private BinaryBalanceTree leftBalance(BinaryBalanceTree T) {
        BinaryBalanceTree lc = T.lChild;
        switch (lc.bf) {
            case LH:
                T = R_Rotate(T);
                break;
            case RH:
                lc = L_Rotate(lc);
                T.lChild = lc;
                T = R_Rotate(T);
                break;
        }
        return T;
    }


    /**
     * 调整右子树
     * @param T
     * @return
     */
    private BinaryBalanceTree rightBalance(BinaryBalanceTree T) {
        BinaryBalanceTree lc = T.rChild;
        switch (lc.bf) {
            case LH:
                lc = R_Rotate(lc);
                T.rChild = lc;
                T = L_Rotate(T);
                break;
            case RH:
                T = L_Rotate(T);
                break;
        }
        return T;
    }


    /**
     * 插入一个值
     * @param p
     * @param value
     * @return
     */
    public BinaryBalanceTree insertAVL(BinaryBalanceTree p, int value) {
        if (p == null) {
            //说明没有找到元素
            taller = true;
            return new BinaryBalanceTree(value);
        }

        if (p.data == value) {
            //找到元素
            return null;
        } else if (value < p.data) {
            //查询左子树
            BinaryBalanceTree balanceTree = insertAVL(p.lChild, value);
            if (balanceTree != null) {
                //p.lChild为null，没有找到value元素，插入到p的左孩子
                p.lChild = balanceTree;
                if (taller) {
                    switch (p.bf) {
                        case LH:
                            //调整
                            taller = false;
                            p = leftBalance(p);
                            break;
                        case EH:
                            p.bf = LH;
                            break;
                        case RH:
                            p.bf = EH;
                            break;
                    }
                }
            }
        } else {
            //查询右子树
            BinaryBalanceTree balanceTree = insertAVL(p.rChild, value);
            if (balanceTree != null) {
                //p.lChild为null，没有找到value元素，插入到p的左孩子
                p.rChild = balanceTree;
                if (taller) {
                    switch (p.bf) {
                        case LH:
                            p.bf = EH;
                            break;
                        case EH:
                            p.bf = RH;
                            break;
                        case RH:
                            p = rightBalance(p);
                            taller = false;
                            break;
                    }
                }
            }
        }
        return p;
    }


    /**
     * 删除一个结点分为四种情况
     * 1.结点不存在
     * 2.结点是叶子结点   ---  直接删除结点
     * 3.结点有且仅有一个孩子   ---  让孩子代替被删除结点
     * 4.结点有左孩子和右孩子   ---
     * 两种方法：(1)中序遍历的前一个结点s代替被删除结点，删除结点s
     * (2)中序遍历的后一个结点s代替被删除结点，删除结点s
     * <p>
     * <p>
     * 需要判定是否平衡
     *
     * @param value
     */
    public BinaryBalanceTree deleteVal(BinaryBalanceTree tree, int value) {
        if (tree == null) {
            return null;//没有找到}
        }
        if (tree.data == value) {
            shorter = true;
            if (tree.lChild == null && tree.rChild == null) return null;//叶子结点
            if (tree.lChild == null || tree.rChild == null) return tree.rChild != null ? tree.rChild : tree.lChild;//只有一个孩子
            BinaryBalanceTree p = tree.lChild;
            while(p.rChild!=null) p = p.rChild;
            tree.data = p.data;
            tree.lChild = deleteVal(tree.lChild, p.data);
            return tree;//中序遍历的前一个结点
        } else if (value > tree.data) {
            //向右子树探索
            BinaryBalanceTree balanceTree = deleteVal(tree.rChild, value);
            if (balanceTree == null && !shorter) {
                return null;
            }
            tree.rChild = balanceTree;//更新右子树
            if (shorter) {
                //删除了结点
                switch (tree.bf) {
                    case LH:
                        //失衡
                        tree = leftBalance(tree);
                        break;
                    case EH:
                        tree.bf = LH;
                        shorter = false;
                        break;
                    case RH:
                        tree.bf = EH;
                        break;
                }
            }

        } else {
            //向左子树探索
            BinaryBalanceTree balanceTree = deleteVal(tree.lChild, value);
            if (balanceTree == null && !shorter) {
                return null;
            }
            tree.lChild = balanceTree;//更新左子树
            if (shorter) {
                //删除了结点
                switch (tree.bf) {
                    case LH:
                        tree.bf = EH;
                        break;
                    case EH:
                        tree.bf = RH;
                        shorter = false;
                        break;
                    case RH:
                        //失衡
                        tree = rightBalance(tree);
                        break;
                }
            }
        }
        return tree;
    }


    /**
     * 查找值为value的结点
     * 若存在则返回，否则返回前一个结点
     * @param value
     * @return
     */
    public BinaryBalanceTree findNode(int value) {
        if (this.data == value) {
            return this;
        } else if (this.data < value) {
            if (this.rChild == null) return this;
            return this.rChild.findNode(value);
        } else {
            if (this.lChild == null) return this;
            return this.lChild.findNode(value);
        }
    }


    /**
     * 是否是二叉平衡树
     * 思路分析:
     * 二叉平衡树的左右子树也是二叉平衡树
     *
     * @return
     */
    public int isBalance(BinaryBalanceTree p) {
        if (p == null) return 0;
        int left = isBalance(p.lChild);
        int right = isBalance(p.rChild);
        if (left == -1||right == -1) return -1;
        if (Math.abs(left - right)>1) return -1;
        return Math.max(left, right) + 1;
    }

    public static void main(String[] args) {
        BinaryBalanceTree operate = new BinaryBalanceTree();
        BinaryBalanceTree p = null;
        p = operate.insertAVL(p, 16);
        p = operate.insertAVL(p, 14);
        p = operate.insertAVL(p, 12);//结点16(2)，14(1)不平衡,LL型,右旋
        p = operate.insertAVL(p, 18);
        p = operate.insertAVL(p, 20);//结点16(-2),18(-1)不平衡,RR型,左旋
        p = operate.insertAVL(p, 10);
        p = operate.insertAVL(p, 11);//结点12(2)10(-1)不平衡,LR型，先左旋再右旋
        p = operate.insertAVL(p, 22);
        p = operate.insertAVL(p, 21);//结点20(-2)22(1)不平衡,RL型，先右旋再左旋

        operate.deleteVal(p, 100);//删除一个不存在的值
        operate.deleteVal(p, 20);//删除叶子节点
        operate.deleteVal(p, 16);//删除叶子结点并且失衡调整
        operate.deleteVal(p, 21);//两个孩子
        operate.deleteVal(p, 18);//一个孩子

        BinaryBalanceTree node = p.findNode(18);
        BinaryBalanceTree tmp = p;
        while (tmp.lChild != null) {
            tmp = tmp.lChild;
        }
        tmp.lChild = new BinaryBalanceTree(8);
        int balance = operate.isBalance(p);
        System.out.println(balance);
    }
}
