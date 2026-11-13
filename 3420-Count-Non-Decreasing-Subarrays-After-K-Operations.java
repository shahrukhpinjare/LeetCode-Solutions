class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;

        int LOG = 18;
        while ((1 << LOG) <= n) LOG++;

        long[] prefix = new long[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        // Next greater element
        int[] nge = new int[n];
        int[] stack = new int[n];
        int top = -1;

        for (int i = n - 1; i >= 0; i--) {
            while (top >= 0 && nums[stack[top]] <= nums[i]) {
                top--;
            }

            nge[i] = top == -1 ? n : stack[top];
            stack[++top] = i;
        }

        // Binary lifting tables
        int[][] jump = new int[LOG][n + 1];
        long[][] cost = new long[LOG][n + 1];

        // Base level
        for (int i = 0; i < n; i++) {
            jump[0][i] = nge[i];

            int j = nge[i];

            cost[0][i] =
                (long) nums[i] * (j - i)
                - (prefix[j] - prefix[i]);
        }

        jump[0][n] = n;

        // Build binary lifting
        for (int p = 1; p < LOG; p++) {
            for (int i = 0; i <= n; i++) {
                int mid = jump[p - 1][i];

                jump[p][i] = jump[p - 1][mid];

                cost[p][i] =
                    cost[p - 1][i] + cost[p - 1][mid];
            }
        }

        long ans = 0;

        // Sliding window using binary search
        for (int right = 0; right < n; right++) {
            int low = 0;
            int high = right;

            while (low < high) {
                int mid = low + (high - low) / 2;

                if (getCost(mid, right, nums, prefix,
                            jump, cost, LOG) <= k) {
                    high = mid;
                } else {
                    low = mid + 1;
                }
            }

            ans += right - low + 1;
        }

        return ans;
    }

    private long getCost(
        int left,
        int right,
        int[] nums,
        long[] prefix,
        int[][] jump,
        long[][] cost,
        int LOG
    ) {
        long operations = 0;
        int cur = left;
        int limit = right + 1;
        int n = nums.length;

        // Skip complete maximum-value blocks
        for (int p = LOG - 1; p >= 0; p--) {
            int next = jump[p][cur];

            if (next <= limit) {
                operations += cost[p][cur];
                cur = next;
            }
        }

        // Remaining partial block
        if (cur <= right) {
            operations +=
                (long) nums[cur] * (right - cur + 1)
                - (prefix[right + 1] - prefix[cur]);
        }

        return operations;
    }
}