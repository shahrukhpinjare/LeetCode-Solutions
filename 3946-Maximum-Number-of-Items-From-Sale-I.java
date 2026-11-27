
import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int maxPrice = 1500;

        // Step 1: Calculate free items for each item
        int[] free = new int[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && items[j][0] % items[i][0] == 0) {
                    free[i]++;
                }
            }
        }

        // Step 2: Group items by price
        List<Integer>[] groups = new ArrayList[maxPrice + 1];

        for (int i = 1; i <= maxPrice; i++) {
            groups[i] = new ArrayList<>();
        }

        for (int i = 0; i < n; i++) {
            groups[items[i][1]].add(i);
        }

        // dp[b] = maximum value using exact budget b
        // considering only items with price greater than current price
        int[] dp = new int[budget + 1];
        Arrays.fill(dp, -1000000);
        dp[0] = 0;

        int ans = 0;

        // Step 3: Process prices in descending order
        for (int p = maxPrice; p >= 1; p--) {

            if (groups[p].isEmpty()) {
                continue;
            }

            // At least one item of current price must be selected
            int[] dp1 = new int[budget + 1];
            Arrays.fill(dp1, -1000000);

            for (int idx : groups[p]) {
                int cost = items[idx][1];
                int value = 1 + free[idx];

                for (int b = budget; b >= cost; b--) {
                    if (dp[b - cost] != -1000000) {
                        dp1[b] = Math.max(
                            dp1[b],
                            dp[b - cost] + value
                        );
                    }

                    if (dp1[b - cost] != -1000000) {
                        dp1[b] = Math.max(
                            dp1[b],
                            dp1[b - cost] + value
                        );
                    }
                }
            }

            // Step 4: Add extra copies of the cheapest
            // selected item (price p)
            for (int b = 0; b <= budget; b++) {
                if (dp1[b] != -1000000) {
                    int extra = (budget - b) / p;
                    ans = Math.max(ans, dp1[b] + extra);
                }
            }

            // Step 5: Add current price group to normal DP
            for (int idx : groups[p]) {
                int cost = items[idx][1];
                int value = 1 + free[idx];

                for (int b = budget; b >= cost; b--) {
                    if (dp[b - cost] != -1000000) {
                        dp[b] = Math.max(
                            dp[b],
                            dp[b - cost] + value
                        );
                    }
                }
            }
        }

        return ans;
    }
}
