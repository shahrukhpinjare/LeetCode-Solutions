class Solution {
    public int maxSubarrayLength(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();

        int left = 0;
        int ans = 0;

        for (int right = 0; right < nums.length; right++) {
            int x = nums[right];
            freq.put(x, freq.getOrDefault(x, 0) + 1);

            while (freq.get(x) > k) {
                int y = nums[left];
                freq.put(y, freq.get(y) - 1);
                left++;
            }

            ans = Math.max(ans, right - left + 1);
        }

        return ans;
    }
}