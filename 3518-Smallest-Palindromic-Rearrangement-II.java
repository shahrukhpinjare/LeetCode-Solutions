class Solution {
    static final int LIMIT = 1_000_001;

    public String smallestPalindrome(String s, int k) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) freq[c - 'a']++;

        int[] half = new int[26];
        char mid = 0;
        int halfLen = 0;

        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
            halfLen += half[i];
            if ((freq[i] & 1) == 1) mid = (char) ('a' + i);
        }

        // Pascal triangle for combinations (capped)
        int[][] C = new int[halfLen + 1][];
        for (int i = 0; i <= halfLen; i++) {
            C[i] = new int[i + 1];
            C[i][0] = C[i][i] = 1;
            for (int j = 1; j < i; j++) {
                long val = (long) C[i - 1][j - 1] + C[i - 1][j];
                C[i][j] = (int) Math.min(val, LIMIT);
            }
        }

        if (countWays(half, C) < k) return "";

        StringBuilder first = new StringBuilder();

        for (int pos = 0; pos < halfLen; pos++) {
            for (int ch = 0; ch < 26; ch++) {
                if (half[ch] == 0) continue;

                half[ch]--;

                int ways = countWays(half, C);

                if (ways >= k) {
                    first.append((char) ('a' + ch));
                    break;
                }

                k -= ways;
                half[ch]++;
            }
        }

        StringBuilder ans = new StringBuilder(first);
        if (mid != 0) ans.append(mid);
        ans.append(new StringBuilder(first).reverse());

        return ans.toString();
    }

    private int countWays(int[] cnt, int[][] C) {
        int total = 0;
        for (int x : cnt) total += x;

        long ways = 1;
        int rem = total;

        for (int x : cnt) {
            if (x == 0) continue;
            ways *= C[rem][x];
            if (ways >= LIMIT) return LIMIT;
            rem -= x;
        }

        return (int) ways;
    }
}