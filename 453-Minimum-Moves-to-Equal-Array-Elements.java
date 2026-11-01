
class Solution {
    public int minMoves(int[] nums) {
        int min = nums[0];

        // Find minimum element
        for (int num : nums) {
            min = Math.min(min, num);
        }

        int moves = 0;

        // Calculate total moves
        for (int num : nums) {
            moves += num - min;
        }

        return moves;
    }
}
