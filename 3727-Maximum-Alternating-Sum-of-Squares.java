import java.util.Arrays;

class Solution {
    public long maxAlternatingSum(int[] nums) {
        int n = nums.length;
        long[] squares = new long[n];

        for (int i = 0; i < n; i++) {
            squares[i] = (long) nums[i] * nums[i];
        }

        Arrays.sort(squares);

        long ans = 0;
        int negativeCount = n / 2;

        for (int i = 0; i < n; i++) {
            if (i < negativeCount) {
                ans -= squares[i];
            } else {
                ans += squares[i];
            }
        }

        return ans;
    }
}