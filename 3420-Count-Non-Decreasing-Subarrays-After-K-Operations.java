class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;

        long[] prefix = new long[n + 1];

        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }

        int[] stack = new int[n];
        int top = -1;

        int[] nextGreater = new int[n];

        for (int i = n - 1; i >= 0; i--) {
            while (top >= 0 && nums[stack[top]] <= nums[i]) {
                top--;
            }

            nextGreater[i] = top == -1 ? n : stack[top];
            stack[++top] = i;
        }

        long ans = 0;

        // Sliding window with binary search
        for (int r = 0; r < n; r++) {
            int low = 0, high = r;

            while (low < high) {
                int mid = low + (high - low) / 2;

                if (getCost(nums, prefix, nextGreater, mid, r) <= k) {
                    high = mid;
                } else {
                    low = mid + 1;
                }
            }

            ans += r - low + 1;
        }

        return ans;
    }

    private long getCost(
        int[] nums,
        long[] prefix,
        int[] nextGreater,
        int l,
        int r
    ) {
        long cost = 0;
        int i = l;

        while (i <= r) {
            int j = Math.min(r, nextGreater[i] - 1);

            long sum = prefix[j + 1] - prefix[i];

            cost += (long) nums[i] * (j - i + 1) - sum;

            i = j + 1;
        }

        return cost;
    }
}