class Solution {
    public int minOperations(String s) {
        int n = s.length();

        boolean sorted = true;
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) < s.charAt(i - 1)) {
                sorted = false;
                break;
            }
        }

        if (sorted) return 0;
        if (n == 2) return -1;

        char first = s.charAt(0);
        char last = s.charAt(n - 1);

        char min = 'z';
        char max = 'a';

        for (int i = 0; i < n; i++) {
            min = (char) Math.min(min, s.charAt(i));
            max = (char) Math.max(max, s.charAt(i));
        }

        if (first == min || last == max) return 1;

        if (first == max && last == min) {
            boolean hasMiddleMinOrMax = false;

            for (int i = 1; i < n - 1; i++) {
                if (s.charAt(i) == min || s.charAt(i) == max) {
                    hasMiddleMinOrMax = true;
                    break;
                }
            }

            if (hasMiddleMinOrMax) return 2;
            return 3;
        }

        return 2;
    }
}