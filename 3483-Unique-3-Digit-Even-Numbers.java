class Solution {
    public int totalNumbers(int[] digits) {
        int[] freq = new int[10];

        // Count occurrences of each digit
        for (int digit : digits) {
            freq[digit]++;
        }

        int count = 0;

        for (int num = 100; num <= 999; num++) {
            // Number must be even
            if (num % 2 != 0) {
                continue;
            }

            int a = num / 100;
            int b = (num / 10) % 10;
            int c = num % 10;

            // Check if required digits are available
            int[] need = new int[10];
            need[a]++;
            need[b]++;
            need[c]++;

            boolean possible = true;

            for (int d = 0; d < 10; d++) {
                if (need[d] > freq[d]) {
                    possible = false;
                    break;
                }
            }

            if (possible) {
                count++;
            }
        }

        return count;
    }
}
