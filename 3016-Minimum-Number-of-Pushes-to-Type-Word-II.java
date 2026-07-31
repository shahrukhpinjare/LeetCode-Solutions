import java.util.*;

class Solution {
    public int minimumPushes(String word) {
        int[] freq = new int[26];

        // Count frequency of each letter
        for (char ch : word.toCharArray()) {
            freq[ch - 'a']++;
        }

        // Sort frequencies
        Arrays.sort(freq);

        int ans = 0;
        int idx = 0;

        // Traverse from largest frequency to smallest
        for (int i = 25; i >= 0 && freq[i] > 0; i--) {
            int pushes = (idx / 8) + 1;
            ans += freq[i] * pushes;
            idx++;
        }

        return ans;
    }
}