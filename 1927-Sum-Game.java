class Solution {
    public boolean sumGame(String num) {
        int n = num.length();
        int half = n / 2;

        int diff = 0;
        int qLeft = 0;
        int qRight = 0;

        for (int i = 0; i < half; i++) {
            if (num.charAt(i) == '?') {
                qLeft++;
            } else {
                diff += num.charAt(i) - '0';
            }
        }

        for (int i = half; i < n; i++) {
            if (num.charAt(i) == '?') {
                qRight++;
            } else {
                diff -= num.charAt(i) - '0';
            }
        }

        int qDiff = qLeft - qRight;

        // If the number of '?' differs, Bob can win only when
        // the existing sum difference is exactly compensable.
        if (qDiff != 0) {
            return diff != -9 * qDiff / 2;
        }

        // Same number of '?' on both sides:
        // Alice wins if the current sums are already different.
        return diff != 0;
    }
}
