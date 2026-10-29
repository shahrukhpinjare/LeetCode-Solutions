import java.util.*;

class Solution {
    private int n, timer;
    private List<Integer>[] adj;
    private int[] parent, depth, size, heavy, head, pos;
    private char[] chars;
    private Fenwick bit;

    public List<Boolean> palindromePath(
            int n, int[][] edges, String s, String[] queries) {

        this.n = n;
        chars = s.toCharArray();

        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        parent = new int[n];
        depth = new int[n];
        size = new int[n];
        heavy = new int[n];
        head = new int[n];
        pos = new int[n];

        Arrays.fill(heavy, -1);

        dfsSize(0, -1);
        timer = 0;
        decompose(0, 0);

        bit = new Fenwick(n);
        for (int u = 0; u < n; u++) {
            bit.update(pos[u], 1 << (chars[u] - 'a'));
        }

        List<Boolean> ans = new ArrayList<>();

        for (String q : queries) {
            String[] p = q.split(" ");

            if (p[0].equals("update")) {
                int u = Integer.parseInt(p[1]);
                char c = p[2].charAt(0);

                if (chars[u] != c) {
                    bit.update(pos[u],
                            (1 << (chars[u] - 'a')) ^
                            (1 << (c - 'a')));
                    chars[u] = c;
                }
            } else {
                int u = Integer.parseInt(p[1]);
                int v = Integer.parseInt(p[2]);

                int mask = 0;

                while (head[u] != head[v]) {
                    if (depth[head[u]] < depth[head[v]]) {
                        int t = u;
                        u = v;
                        v = t;
                    }

                    mask ^= bit.query(pos[head[u]], pos[u] + 1);
                    u = parent[head[u]];
                }

                if (depth[u] > depth[v]) {
                    int t = u;
                    u = v;
                    v = t;
                }

                mask ^= bit.query(pos[u], pos[v] + 1);

                ans.add(Integer.bitCount(mask) <= 1);
            }
        }

        return ans;
    }

    private int dfsSize(int u, int p) {
        parent[u] = p;
        size[u] = 1;
        int maxSize = 0;

        for (int v : adj[u]) {
            if (v == p) continue;

            depth[v] = depth[u] + 1;
            int sub = dfsSize(v, u);
            size[u] += sub;

            if (sub > maxSize) {
                maxSize = sub;
                heavy[u] = v;
            }
        }

        return size[u];
    }

    private void decompose(int u, int h) {
        head[u] = h;
        pos[u] = timer++;

        if (heavy[u] != -1) {
            decompose(heavy[u], h);
        }

        for (int v : adj[u]) {
            if (v == parent[u] || v == heavy[u]) continue;
            decompose(v, v);
        }
    }

    private static class Fenwick {
        int[] tree;
        int n;

        Fenwick(int n) {
            this.n = n;
            tree = new int[n + 1];
        }

        void update(int index, int delta) {
            for (int i = index + 1; i <= n; i += i & -i) {
                tree[i] ^= delta;
            }
        }

        int prefix(int end) {
            int mask = 0;
            for (int i = end; i > 0; i -= i & -i) {
                mask ^= tree[i];
            }
            return mask;
        }

        int query(int left, int right) {
            return prefix(right) ^ prefix(left);
        }
    }
}