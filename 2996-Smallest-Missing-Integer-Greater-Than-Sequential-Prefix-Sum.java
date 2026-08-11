class Solution {
    public int missingInteger(int[] nums) {
        int sum = nums[0];

        // Find sum of the longest sequential prefix
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1] + 1) {
                sum += nums[i];
            } else {
                break;
            }
        }

        // Find the smallest missing integer >= sum
        int x = sum;

        while (contains(nums, x)) {
            x++;
        }

        return x;
    }

    private boolean contains(int[] nums, int target) {
        for (int num : nums) {
            if (num == target) {
                return true;
            }
        }
        return false;
    }
}