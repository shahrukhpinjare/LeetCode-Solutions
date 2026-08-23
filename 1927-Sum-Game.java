class Solution {
    public boolean sumGame(String num) {
        int n = num.length();
        int half = n / 2;

        int diff = 0;
        int qLeft = 0;
        int qRight = 0;

        for (int i = 0; i < half; i++) {
            char c = num.charAt(i);

            if (c == '?') {
                qLeft++;
            } else {
                diff += c - '0';
            }
        }

        for (int i = half; i < n; i++) {
            char c = num.charAt(i);

            if (c == '?') {
                qRight++;
            } else {
                diff -= c - '0';
            }
        }

        int qDiff = qLeft - qRight;

        // An odd difference in the number of '?' means
        // Alice can always force the sums to be different.
        if (qDiff % 2 != 0) {
            return true;
        }

        // Bob can force equality only in this exact situation.
        return diff != -9 * qDiff / 2;
    }
}
