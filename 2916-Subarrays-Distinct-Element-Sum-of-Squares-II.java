import java.util.Arrays;

class Solution {
    private static final long MOD = 1_000_000_007L;

    private long[] sum;
    private long[] sq;
    private long[] lazy;
    private int n;

    public int sumCounts(int[] nums) {
        n = nums.length;

        sum = new long[4 * n];
        sq = new long[4 * n];
        lazy = new long[4 * n];

        int maxVal = 100_000;
        int[] last = new int[maxVal + 1];
        Arrays.fill(last, -1);

        long ans = 0;

        for (int i = 0; i < n; i++) {
            int prev = last[nums[i]];

            // Distinct counts increase for starts in [prev + 1, i].
            update(1, 0, n - 1, prev + 1, i);

            // Sum of squares for all subarrays ending at i.
            ans = (ans + sq[1]) % MOD;

            last[nums[i]] = i;
        }

        return (int) ans;
    }

    private void apply(int node, int left, int right) {
        long len = right - left + 1L;

        // sq' = sq + 2 * sum + len
        sq[node] = (sq[node] + 2L * sum[node] + len) % MOD;

        // sum' = sum + len
        sum[node] = (sum[node] + len) % MOD;

        lazy[node] = (lazy[node] + 1) % MOD;
    }

    private void push(int node, int left, int right) {
        if (lazy[node] == 0 || left == right) {
            return;
        }

        int mid = left + (right - left) / 2;
        long add = lazy[node];

        applyLazy(node * 2, left, mid, add);
        applyLazy(node * 2 + 1, mid + 1, right, add);

        lazy[node] = 0;
    }

    private void applyLazy(
            int node, int left, int right, long add) {

        long len = right - left + 1L;

        // For an increment of add:
        // (x + add)^2 = x^2 + 2*add*x + add^2
        sq[node] = (
            sq[node]
            + 2L * add % MOD * sum[node] % MOD
            + add * add % MOD * len % MOD
        ) % MOD;

        sum[node] = (sum[node] + add * len) % MOD;
        lazy[node] = (lazy[node] + add) % MOD;
    }

    private void update(
            int node, int left, int right,
            int ql, int qr) {

        if (ql > right || qr < left) {
            return;
        }

        if (ql <= left && right <= qr) {
            apply(node, left, right);
            return;
        }

        push(node, left, right);

        int mid = left + (right - left) / 2;

        update(node * 2, left, mid, ql, qr);
        update(node * 2 + 1, mid + 1, right, ql, qr);

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;
        sq[node] = (sq[node * 2] + sq[node * 2 + 1]) % MOD;
    }
}