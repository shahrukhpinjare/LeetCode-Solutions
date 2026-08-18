class Solution {
    public int largestInteger(int[] nums, int k) {
        int[] count = new int[51];

        // Process every subarray of size k
        for (int start = 0; start <= nums.length - k; start++) {
            boolean[] seen = new boolean[51];

            for (int i = start; i < start + k; i++) {
                seen[nums[i]] = true;
            }

            // Count each number only once per subarray
            for (int x = 0; x <= 50; x++) {
                if (seen[x]) {
                    count[x]++;
                }
            }
        }

        // Find the largest number appearing in exactly one subarray
        for (int x = 50; x >= 0; x--) {
            if (count[x] == 1) {
                return x;
            }
        }

        return -1;
    }
}