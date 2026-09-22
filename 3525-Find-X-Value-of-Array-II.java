class Solution {
    static class Node {
        int prod;
        long[] pref;
        long[] suff;
        long[] cnt;

        Node(int k) {
            pref = new long[k];
            suff = new long[k];
            cnt = new long[k];
        }
    }

    int n, k;
    int[] nums;
    Node[] tree;

    private int mod(long x) {
        return (int)(x % k);
    }

    private Node makeNode(int value) {
        Node node = new Node(k);

        int p = value % k;
        node.prod = p;

        // The only non-empty prefix/suffix/subarray is the element itself.
        node.pref[p] = 1;
        node.suff[p] = 1;
        node.cnt[p] = 1;

        return node;
    }

    private Node merge(Node a, Node b) {
        Node res = new Node(k);

        // Product of the whole segment.
        res.prod = (a.prod * b.prod) % k;

        // Prefixes:
        // 1. Prefix lies completely in a.
        // 2. Entire a + a prefix of b.
        for (int r = 0; r < k; r++) {
            res.pref[r] += a.pref[r];

            for (int q = 0; q < k; q++) {
                if ((a.prod * q) % k == r) {
                    res.pref[r] += b.pref[q];
                }
            }
        }

        // Suffixes:
        // 1. Suffix lies completely in b.
        // 2. A suffix of a + entire b.
        for (int r = 0; r < k; r++) {
            res.suff[r] += b.suff[r];

            for (int q = 0; q < k; q++) {
                if ((q * b.prod) % k == r) {
                    res.suff[r] += a.suff[q];
                }
            }
        }

        // Subarrays completely inside either side.
        for (int r = 0; r < k; r++) {
            res.cnt[r] = a.cnt[r] + b.cnt[r];
        }

        // Subarrays crossing the boundary:
        // suffix of a + prefix of b.
        for (int x = 0; x < k; x++) {
            if (a.suff[x] == 0) continue;

            for (int y = 0; y < k; y++) {
                if (b.pref[y] == 0) continue;

                int r = (x * y) % k;
                res.cnt[r] += a.suff[x] * b.pref[y];
            }
        }

        return res;
    }

    private void build(int idx, int l, int r) {
        if (l == r) {
            tree[idx] = makeNode(nums[l]);
            return;
        }

        int mid = (l + r) >>> 1;

        build(idx << 1, l, mid);
        build(idx << 1 | 1, mid + 1, r);

        tree[idx] = merge(tree[idx << 1], tree[idx << 1 | 1]);
    }

    private void update(int idx, int l, int r, int pos, int value) {
        if (l == r) {
            tree[idx] = makeNode(value);
            return;
        }

        int mid = (l + r) >>> 1;

        if (pos <= mid) {
            update(idx << 1, l, mid, pos, value);
        } else {
            update(idx << 1 | 1, mid + 1, r, pos, value);
        }

        tree[idx] = merge(tree[idx << 1], tree[idx << 1 | 1]);
    }

    private Node query(int idx, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr) {
            return tree[idx];
        }

        int mid = (l + r) >>> 1;

        if (qr <= mid) {
            return query(idx << 1, l, mid, ql, qr);
        }

        if (ql > mid) {
            return query(idx << 1 | 1, mid + 1, r, ql, qr);
        }

        Node left = query(idx << 1, l, mid, ql, qr);
        Node right = query(idx << 1 | 1, mid + 1, r, ql, qr);

        return merge(left, right);
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.nums = nums;
        this.k = k;
        this.n = nums.length;

        tree = new Node[4 * n];
        build(1, 0, n - 1);

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            // Persistent update for subsequent queries.
            nums[index] = value;
            update(1, 0, n - 1, index, value);

            // We need non-empty prefixes of [start, n-1].
            Node res = query(1, 0, n - 1, start, n - 1);

            ans[i] = (int) res.pref[x];
        }

        return ans;
    }
}
