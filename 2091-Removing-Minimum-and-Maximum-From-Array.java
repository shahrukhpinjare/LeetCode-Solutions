class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;

        int minPos = 0;
        int maxPos = 0;

        // Find positions of minimum and maximum
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[minPos]) {
                minPos = i;
            }
            if (nums[i] > nums[maxPos]) {
                maxPos = i;
            }
        }

        int left = Math.min(minPos, maxPos);
        int right = Math.max(minPos, maxPos);

        // Option 1: Remove both from the front
        int removeFront = right + 1;

        // Option 2: Remove both from the back
        int removeBack = n - left;

        // Option 3: Remove one from front and one from back
        int removeBoth = (left + 1) + (n - right);

        return Math.min(removeFront, Math.min(removeBack, removeBoth));
    }
}
