package matrix.impl;

/**
 * 行逻辑链接的顺序表
 */
public class RLSMatrix {
    Triple[] triples;
    int[] rpos;
    int mu, nu, tu;

    public RLSMatrix() {
    }

    public RLSMatrix(Triple[] triples, int mu, int nu, int tu) {
        this.triples = triples;
        this.mu = mu;
        this.nu = nu;
        this.tu = tu;
        initRpos();
    }

    private void initRpos() {
        int[] nums = new int[mu];
        for (int i = 0; i < tu; i++) {
            nums[triples[i].i]++;
        }

        rpos = new int[mu];
        for (int i = 1; i < mu; i++) {
            rpos[i] = rpos[i - 1] + nums[i - 1];
        }
    }

    public void setTriples(Triple[] triples) {
        this.triples = triples;
        initRpos();
    }
}
