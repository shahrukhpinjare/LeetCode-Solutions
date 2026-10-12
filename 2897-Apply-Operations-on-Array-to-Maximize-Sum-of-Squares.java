import java.util.*;

class Solution {
    public int maxSum(List<Integer> nums, int k) {
        final long MOD = 1_000_000_007L;
        int[] bitCount = new int[32];

        // Count set bits at each position
        for (int num : nums) {
            for (int b = 0; b < 32; b++) {
                if ((num & (1 << b)) != 0) {
                    bitCount[b]++;
                }
            }
        }

        long ans = 0;

        // Build k numbers with the maximum possible values
        for (int i = 0; i < k; i++) {
            long num = 0;

            for (int b = 0; b < 32; b++) {
                if (bitCount[b] > 0) {
                    num |= (1L << b);
                    bitCount[b]--;
                }
            }

            ans = (ans + num * num) % MOD;
        }

        return (int) ans;
    }
}