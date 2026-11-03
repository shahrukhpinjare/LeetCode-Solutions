
class Solution {
    public int minMoves(int[] nums) {
        int max = nums[0];
        int sum = 0;

        // Find maximum element
        for (int num : nums) {
            max = Math.max(max, num);
        }

        // Calculate total moves
        for (int num : nums) {
            sum += max - num;
        }

        return sum;
    }
}
