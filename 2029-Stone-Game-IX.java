class Solution {
    public boolean stoneGameIX(int[] stones) {
        int[] cnt = new int[3];

        // Count stones based on remainder when divided by 3
        for (int stone : stones) {
            cnt[stone % 3]++;
        }

        // If count of remainder-0 stones is even
        if (cnt[0] % 2 == 0) {
            return cnt[1] > 0 && cnt[2] > 0;
        }

        // If count of remainder-0 stones is odd
        return Math.abs(cnt[1] - cnt[2]) > 2;
    }
}