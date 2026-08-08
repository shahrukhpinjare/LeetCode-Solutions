class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        // dp[i] = maximum length of a suffix of word2
        // that can be matched exactly in word1[i...].
        int[] dp = new int[n + 1];

        int j = m - 1;

        for (int i = n - 1; i >= 0; i--) {
            dp[i] = dp[i + 1];

            if (j >= 0 && word1.charAt(i) == word2.charAt(j)) {
                dp[i]++;
                j--;
            }
        }

        int[] ans = new int[m];

        int i = 0;
        j = 0;

        // Before using the one allowed mismatch.
        while (i < n && j < m) {

            // Best possible choice: exact match.
            if (word1.charAt(i) == word2.charAt(j)) {
                ans[j] = i;
                j++;
                i++;
                continue;
            }

            // Use the one allowed modification at i.
            // The remaining m-j-1 characters must match exactly.
            int remaining = m - j - 1;

            if (dp[i + 1] >= remaining) {
                ans[j] = i;
                j++;
                i++;
                break;
            }

            // This mismatch cannot be used here.
            i++;
        }

        // After using the mismatch, everything must match exactly.
        while (i < n && j < m) {
            if (word1.charAt(i) == word2.charAt(j)) {
                ans[j] = i;
                j++;
            }
            i++;
        }

        // Could not construct the complete sequence.
        if (j != m) {
            return new int[0];
        }

        return ans;
    }
}