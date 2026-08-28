class Solution {

    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();

        int[] cnt = new int[26];

        for (char c : s.toCharArray()) {
            cnt[c - 'a']++;
        }

        // A palindrome can have at most one odd-frequency character.
        int middle = -1;

        for (int i = 0; i < 26; i++) {
            if ((cnt[i] & 1) == 1) {
                if (middle != -1) {
                    return "";
                }
                middle = i;
            }
        }

        int halfLen = n / 2;

        int[] half = new int[26];
        for (int i = 0; i < 26; i++) {
            half[i] = cnt[i] / 2;
        }

        char[] left = new char[halfLen];

        /*
         * Match target from left to right as long as possible.
         */
        for (int i = 0; i < halfLen; i++) {
            int t = target.charAt(i) - 'a';

            if (half[t] > 0) {
                left[i] = (char) ('a' + t);
                half[t]--;
                continue;
            }

            /*
             * target[i] isn't available.
             *
             * We can immediately make the answer greater by choosing
             * the smallest available character > target[i].
             */
            for (int c = t + 1; c < 26; c++) {
                if (half[c] > 0) {
                    left[i] = (char) ('a' + c);
                    half[c]--;

                    fillSmallest(left, i + 1, half);

                    return build(left, middle);
                }
            }

            /*
             * No larger character is available here.
             *
             * Go backwards and increase an earlier position.
             */
            for (int j = i - 1; j >= 0; j--) {
                int old = left[j] - 'a';
                half[old]++;

                int t2 = target.charAt(j) - 'a';

                for (int c = t2 + 1; c < 26; c++) {
                    if (half[c] > 0) {
                        left[j] = (char) ('a' + c);
                        half[c]--;

                        fillSmallest(left, j + 1, half);

                        return build(left, middle);
                    }
                }
            }

            return "";
        }

        /*
         * The entire left half matches target's first half.
         */
        String candidate = build(left, middle);

        if (candidate.compareTo(target) > 0) {
            return candidate;
        }

        /*
         * Candidate <= target.
         *
         * Find the next larger possible left-half permutation.
         */
        for (int i = halfLen - 1; i >= 0; i--) {
            int old = left[i] - 'a';
            half[old]++;

            int t = target.charAt(i) - 'a';

            for (int c = t + 1; c < 26; c++) {
                if (half[c] > 0) {
                    left[i] = (char) ('a' + c);
                    half[c]--;

                    fillSmallest(left, i + 1, half);

                    return build(left, middle);
                }
            }
        }

        return "";
    }

    private void fillSmallest(char[] left, int start, int[] half) {
        int index = start;

        for (int c = 0; c < 26; c++) {
            while (half[c] > 0) {
                left[index++] = (char) ('a' + c);
                half[c]--;
            }
        }
    }

    private String build(char[] left, int middle) {
        StringBuilder sb = new StringBuilder();

        // Left half.
        for (char c : left) {
            sb.append(c);
        }

        // Middle character for odd length.
        if (middle != -1) {
            sb.append((char) ('a' + middle));
        }

        // Right half.
        for (int i = left.length - 1; i >= 0; i--) {
            sb.append(left[i]);
        }

        return sb.toString();
    }
}
