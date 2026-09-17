class Solution {
    public int minSumOfLengths(int[] arr, int target) {

        int n = arr.length;
        int INF = Integer.MAX_VALUE / 2;

        // best[i] = minimum length of a valid subarray
        // completely inside arr[0...i]
        int[] best = new int[n];

        for (int i = 0; i < n; i++) {
            best[i] = INF;
        }

        int left = 0;
        int sum = 0;
        int ans = INF;

        for (int right = 0; right < n; right++) {

            sum += arr[right];

            // Since all elements are positive,
            // we can safely shrink the window.
            while (sum > target) {
                sum -= arr[left];
                left++;
            }

            // Current subarray has sum = target
            if (sum == target) {

                int currentLength = right - left + 1;

                // Check if a previous non-overlapping
                // subarray exists.
                if (left > 0 && best[left - 1] != INF) {
                    ans = Math.min(
                        ans,
                        best[left - 1] + currentLength
                    );
                }

                // Store the minimum valid length
                // ending at or before 'right'.
                if (right == 0) {
                    best[right] = currentLength;
                } else {
                    best[right] = Math.min(
                        best[right - 1],
                        currentLength
                    );
                }

            } else {
                // No valid subarray ending at right.
                if (right > 0) {
                    best[right] = best[right - 1];
                }
            }
        }

        return ans == INF ? -1 : ans;
    }
}