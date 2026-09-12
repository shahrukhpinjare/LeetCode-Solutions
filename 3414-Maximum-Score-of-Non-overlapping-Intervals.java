import java.util.*;

class Solution {

    static class Interval {
        int l, r, w, idx;

        Interval(int l, int r, int w, int idx) {
            this.l = l;
            this.r = r;
            this.w = w;
            this.idx = idx;
        }
    }

    static class State {
        long score;
        int[] indices;

        State(long score, int[] indices) {
            this.score = score;
            this.indices = indices;
        }
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();

        Interval[] arr = new Interval[n];

        for (int i = 0; i < n; i++) {
            arr[i] = new Interval(
                intervals.get(i).get(0),
                intervals.get(i).get(1),
                intervals.get(i).get(2),
                i
            );
        }

        // Sort by starting position.
        Arrays.sort(arr, (a, b) -> {
            if (a.l != b.l)
                return Integer.compare(a.l, b.l);

            if (a.r != b.r)
                return Integer.compare(a.r, b.r);

            return Integer.compare(a.idx, b.idx);
        });

        int[] starts = new int[n];

        for (int i = 0; i < n; i++) {
            starts[i] = arr[i].l;
        }

        /*
         * dp[i][k] =
         * best answer using intervals i..n-1
         * with at most k intervals.
         */
        State[][] dp = new State[n + 1][5];

        // No intervals left.
        for (int k = 0; k <= 4; k++) {
            dp[n][k] = new State(0, new int[0]);
        }

        // Cannot select anything.
        for (int i = 0; i <= n; i++) {
            dp[i][0] = new State(0, new int[0]);
        }

        for (int i = n - 1; i >= 0; i--) {

            for (int k = 1; k <= 4; k++) {

                // Option 1: skip this interval.
                State skip = dp[i + 1][k];

                // Option 2: take this interval.
                //
                // Need next.l > arr[i].r.
                int next = upperBound(starts, arr[i].r);

                State rest = dp[next][k - 1];

                int[] chosen = new int[rest.indices.length + 1];

                chosen[0] = arr[i].idx;

                System.arraycopy(
                    rest.indices,
                    0,
                    chosen,
                    1,
                    rest.indices.length
                );

                // IMPORTANT:
                // The answer must be sorted by ORIGINAL index.
                Arrays.sort(chosen);

                State take = new State(
                    arr[i].w + rest.score,
                    chosen
                );

                dp[i][k] = better(skip, take);
            }
        }

        return dp[0][4].indices;
    }

    /*
     * First position whose start > target.
     */
    private int upperBound(int[] starts, int target) {
        int lo = 0;
        int hi = starts.length;

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;

            if (starts[mid] <= target) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }

        return lo;
    }

    /*
     * Prefer:
     * 1. Higher score
     * 2. Lexicographically smaller sorted index array
     */
    private State better(State a, State b) {

        if (a.score != b.score) {
            return a.score > b.score ? a : b;
        }

        return compareLexicographically(a.indices, b.indices) <= 0
            ? a
            : b;
    }

    private int compareLexicographically(int[] a, int[] b) {

        int len = Math.min(a.length, b.length);

        for (int i = 0; i < len; i++) {
            if (a[i] != b[i]) {
                return Integer.compare(a[i], b[i]);
            }
        }

        return Integer.compare(a.length, b.length);
    }
}
