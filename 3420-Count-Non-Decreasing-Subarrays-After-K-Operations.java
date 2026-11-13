class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;

        int[] stack = new int[n];
        int top = -1;

        long[] tree = new long[4 * n];
        long[] lazy = new long[4 * n];

        long ans = 0;
        int left = 0;

        for (int right = 0; right < n; right++) {

            // Maintain the non-increasing stack
            while (top >= 0 && nums[stack[top]] <= nums[right]) {
                top--;
            }

            stack[++top] = right;

            // Calculate valid window using binary search
            int low = left, high = right;

            while (low <= high) {
                int mid = low + (high - low) / 2;

                if (cost(nums, mid, right) <= k) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }

            left = low;
            ans += right - left + 1;
        }

        return ans;
    }

    private long cost(int[] nums, int l, int r) {
        long operations = 0;
        int max = nums[l];

        for (int i = l + 1; i <= r; i++) {
            max = Math.max(max, nums[i]);
            operations += max - nums[i];
        }

        return operations;
    }
}