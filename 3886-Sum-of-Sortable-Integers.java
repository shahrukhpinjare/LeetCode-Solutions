import java.util.*;

class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int ans = 0;

        for (int k = 1; k <= n; k++) {
            if (n % k != 0) continue;

            boolean valid = true;

            for (int start = 0; start < n && valid; start += k) {
                // Check whether nums[start..start+k-1] is a rotation
                // of sorted[start..start+k-1].
                if (!isRotation(nums, sorted, start, k)) {
                    valid = false;
                }
            }

            if (valid) ans += k;
        }

        return ans;
    }

    private boolean isRotation(int[] nums, int[] sorted, int start, int k) {
        // Search the block in the doubled sorted block using KMP.
        int[] pattern = new int[k];
        for (int i = 0; i < k; i++) {
            pattern[i] = sorted[start + i];
        }

        int[] lps = new int[k];
        for (int i = 1, len = 0; i < k;) {
            if (pattern[i] == pattern[len]) {
                lps[i++] = ++len;
            } else if (len > 0) {
                len = lps[len - 1];
            } else {
                lps[i++] = 0;
            }
        }

        int j = 0;
        for (int i = 0; i < 2 * k - 1; i++) {
            int value = nums[start + i % k];

            while (j > 0 && value != pattern[j]) {
                j = lps[j - 1];
            }

            if (value == pattern[j]) j++;

            if (j == k) return true;
        }

        return false;
    }
}