class Solution {
    public int uniqueXorTriplets(int[] nums) {
        final int MAX = 2048;

        boolean[][] dp = new boolean[4][MAX];
        dp[0][0] = true;

        for (int v : nums) {
            boolean[][] ndp = new boolean[4][MAX];

            // keep old states (choose this index 0 times)
            for (int c = 0; c <= 3; c++) {
                System.arraycopy(dp[c], 0, ndp[c], 0, MAX);
            }

            for (int c = 0; c <= 3; c++) {
                for (int x = 0; x < MAX; x++) {
                    if (!dp[c][x]) continue;

                    // choose this index once
                    if (c + 1 <= 3)
                        ndp[c + 1][x ^ v] = true;

                    // choose this index twice
                    if (c + 2 <= 3)
                        ndp[c + 2][x] = true;

                    // choose this index three times
                    if (c + 3 <= 3)
                        ndp[c + 3][x ^ v] = true;
                }
            }

            dp = ndp;
        }

        int ans = 0;
        for (boolean ok : dp[3]) {
            if (ok) ans++;
        }
        return ans;
    }
}