class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();

        int[] freq = new int[26];
        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        // Match target from left to right.
        for (int i = 0; i < n; i++) {
            int cur = target.charAt(i) - 'a';

            // If target[i] is unavailable, try making this position larger.
            if (freq[cur] == 0) {
                return makeGreater(target, i, freq);
            }

            freq[cur]--;
        }

        // target itself is a permutation of s.
        // Therefore, we need to backtrack and make an earlier
        // position larger.
        return backtrack(target, n - 1, freq);
    }

    private String makeGreater(String target, int pos, int[] freq) {
        int cur = target.charAt(pos) - 'a';

        // Try the smallest character greater than target[pos].
        for (int c = cur + 1; c < 26; c++) {
            if (freq[c] > 0) {
                StringBuilder ans = new StringBuilder();

                // Keep prefix equal to target.
                ans.append(target, 0, pos);

                // Make this position greater.
                ans.append((char) ('a' + c));
                freq[c]--;

                // Fill remaining positions with smallest characters.
                appendSorted(ans, freq);

                return ans.toString();
            }
        }

        // Can't make current position greater.
        // Backtrack to an earlier position.
        return backtrack(target, pos - 1, freq);
    }

    private String backtrack(String target, int pos, int[] freq) {
        for (int i = pos; i >= 0; i--) {

            // Restore target[i], because we are changing this position.
            int cur = target.charAt(i) - 'a';
            freq[cur]++;

            // Find smallest character greater than target[i].
            for (int c = cur + 1; c < 26; c++) {
                if (freq[c] > 0) {
                    StringBuilder ans = new StringBuilder();

                    // Keep everything before i equal.
                    ans.append(target, 0, i);

                    // Make position i greater.
                    ans.append((char) ('a' + c));
                    freq[c]--;

                    // Smallest possible suffix.
                    appendSorted(ans, freq);

                    return ans.toString();
                }
            }
        }

        return "";
    }

    private void appendSorted(StringBuilder ans, int[] freq) {
        for (int c = 0; c < 26; c++) {
            while (freq[c] > 0) {
                ans.append((char) ('a' + c));
                freq[c]--;
            }
        }
    }
}
