class Solution {
    public boolean stoneGameIX(int[] stones) {
        int[] cnt = new int[3];

        for (int stone : stones) {
            cnt[stone % 3]++;
        }

        // Alice needs both types of non-zero remainders.
        if (cnt[1] == 0 || cnt[2] == 0) {
            return false;
        }

        // If there are no multiples of 3, Alice can win unless
        // the two groups are too unbalanced.
        if (cnt[0] == 0) {
            return Math.abs(cnt[1] - cnt[2]) <= 2;
        }

        // With multiples of 3, Alice needs a sufficiently large
        // advantage in one of the two non-zero remainder groups.
        return cnt[1] > cnt[2] + 2 || cnt[2] > cnt[1] + 2;
    }
}