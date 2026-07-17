import java.util.*;

class Solution {
    public int[] gcdValues(int[] nums, long[] queries) {
        int max = 0;
        for (int x : nums) max = Math.max(max, x);

        int[] freq = new int[max + 1];
        for (int x : nums) freq[x]++;

        // cntDiv[d] = count of numbers divisible by d
        int[] cntDiv = new int[max + 1];
        for (int d = 1; d <= max; d++) {
            for (int multiple = d; multiple <= max; multiple += d) {
                cntDiv[d] += freq[multiple];
            }
        }

        long[] exact = new long[max + 1];

        // Inclusion-Exclusion
        for (int d = max; d >= 1; d--) {
            long c = cntDiv[d];
            long pairs = c * (c - 1) / 2;

            for (int multiple = d + d; multiple <= max; multiple += d) {
                pairs -= exact[multiple];
            }

            exact[d] = pairs;
        }

        // Prefix count of sorted gcd values
        long[] pref = new long[max + 1];
        for (int i = 1; i <= max; i++) {
            pref[i] = pref[i - 1] + exact[i];
        }

        int[] ans = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {
            long k = queries[i];

            int l = 1, r = max;
            while (l < r) {
                int mid = (l + r) >>> 1;
                if (pref[mid] > k)
                    r = mid;
                else
                    l = mid + 1;
            }
            ans[i] = l;
        }

        return ans;
    }
}