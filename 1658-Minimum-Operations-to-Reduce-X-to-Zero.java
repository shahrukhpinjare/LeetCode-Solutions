class Solution {
    public int minOperations(int[] nums, int x) {
        
        int n = nums.length;
        long totalSum = 0;

        // Calculate total sum
        for (int num : nums) {
            totalSum += num;
        }

        long target = totalSum - x;

        // If target is negative, impossible
        if (target < 0) {
            return -1;
        }

        // If target is 0, we need to remove all elements
        if (target == 0) {
            return n;
        }

        int left = 0;
        long windowSum = 0;
        int maxLength = -1;

        for (int right = 0; right < n; right++) {

            windowSum += nums[right];

            // Shrink window if sum becomes greater than target
            while (windowSum > target && left <= right) {
                windowSum -= nums[left];
                left++;
            }

            // Found subarray with target sum
            if (windowSum == target) {
                maxLength = Math.max(maxLength, right - left + 1);
            }
        }

        // No valid subarray found
        if (maxLength == -1) {
            return -1;
        }

        return n - maxLength;
    }
}