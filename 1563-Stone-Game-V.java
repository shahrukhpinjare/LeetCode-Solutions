class Solution {
    public int stoneGameV(int[] stoneValue) {
        int n = stoneValue.length;

        // Prefix sums for O(1) range-sum queries.
        long[] prefix = new long[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + stoneValue[i];
        }

        // dp[l][r] = maximum score for subarray [l, r].
        int[][] dp = new int[n][n];

        // Length 2, 3, ..., n
        for (int len = 2; len <= n; len++) {
            for (int l = 0; l + len <= n; l++) {
                int r = l + len - 1;

                int best = 0;

                for (int k = l; k < r; k++) {
                    long leftSum = prefix[k + 1] - prefix[l];
                    long rightSum = prefix[r + 1] - prefix[k + 1];

                    if (leftSum < rightSum) {
                        best = Math.max(
                            best,
                            (int) leftSum + dp[l][k]
                        );
                    } else if (leftSum > rightSum) {
                        best = Math.max(
                            best,
                            (int) rightSum + dp[k + 1][r]
                        );
                    } else {
                        best = Math.max(
                            best,
                            (int) leftSum
                                + Math.max(dp[l][k], dp[k + 1][r])
                        );
                    }
                }

                dp[l][r] = best;
            }
        }

        return dp[0][n - 1];
    }
}