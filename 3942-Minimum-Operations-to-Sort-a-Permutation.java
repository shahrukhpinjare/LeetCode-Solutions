class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return 0;
        }

        int best = Integer.MAX_VALUE;

        // Try every left rotation without reversing
        for (int shift = 0; shift < n; shift++) {
            if (isSortedAfterLeftRotation(nums, shift)) {
                best = Math.min(best, Math.min(shift, n - shift));
            }
        }

        // Try reversing, then every left rotation
        int[] reversed = new int[n];
        for (int i = 0; i < n; i++) {
            reversed[i] = nums[n - 1 - i];
        }

        for (int shift = 0; shift < n; shift++) {
            if (isSortedAfterLeftRotation(reversed, shift)) {
                best = Math.min(best, 1 + Math.min(shift, n - shift));
            }
        }

        return best == Integer.MAX_VALUE ? -1 : best;
    }

    private boolean isSortedAfterLeftRotation(int[] nums, int shift) {
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            if (nums[(shift + i) % n] != i) {
                return false;
            }
        }

        return true;
    }
}
