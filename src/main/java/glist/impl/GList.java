package glist.impl;

/**
 * @Description: 广义表
 * @Author: woneway
 * @Date: 2019/8/6 14:22
 */
public class GList<T> {

    GListType elemTag;//默认是表结点

    T atom;//值域,仅原子节点有值

    GList hp;//指向表头的指针，仅表结点有值,上下级关系

    GList tp;//指向表尾的指针，同级关系

    public GList() {
        this.elemTag = GListType.List;
    }

    public GList(T atom, GList tp) {
        this.elemTag = GListType.ATOM;
        this.atom = atom;
        this.tp = tp;
    }

    public GList(GList hp, GList tp) {
        this.elemTag = GListType.List;
        this.hp = hp;
        this.tp = tp;
    }

    public GListType getElemTag() {
        return elemTag;
    }

    public void setElemTag(GListType elemTag) {
        this.elemTag = elemTag;
    }

    public T getAtom() {
        return atom;
    }

    public void setAtom(T atom) {
        this.atom = atom;
    }

    public GList getHp() {
        return hp;
    }

    public void setHp(GList hp) {
        this.hp = hp;
    }

    public GList getTp() {
        return tp;
    }

    public void setTp(GList tp) {
        this.tp = tp;
    }
}
