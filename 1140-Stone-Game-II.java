class Solution {
    public int stoneGameII(int[] piles) {
        int n = piles.length;

        // suffix[i] = sum of piles[i ... n-1]
        int[] suffix = new int[n + 1];

        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1] + piles[i];
        }

        // dp[i][m] = maximum stones current player can get
        // starting from index i with M = m
        int[][] dp = new int[n + 1][n + 1];

        for (int i = n - 1; i >= 0; i--) {
            for (int m = 1; m <= n; m++) {

                // Can take all remaining piles
                if (i + 2 * m >= n) {
                    dp[i][m] = suffix[i];
                    continue;
                }

                int best = 0;

                for (int x = 1; x <= 2 * m && i + x <= n; x++) {
                    int nextM = Math.max(m, x);

                    // Current player gets all remaining stones
                    // minus what the opponent can get.
                    int current = suffix[i] - dp[i + x][nextM];

                    best = Math.max(best, current);
                }

                dp[i][m] = best;
            }
        }

        return dp[0][1];
    }
}