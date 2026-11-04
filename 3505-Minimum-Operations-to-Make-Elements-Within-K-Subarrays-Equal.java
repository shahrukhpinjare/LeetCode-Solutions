
import java.util.*;

class Solution {
    TreeMap<Integer, Integer> left = new TreeMap<>();
    TreeMap<Integer, Integer> right = new TreeMap<>();

    long sumLeft = 0, sumRight = 0;
    int sizeLeft = 0, sizeRight = 0;

    void add(TreeMap<Integer, Integer> map, int val) {
        map.put(val, map.getOrDefault(val, 0) + 1);
    }

    void remove(TreeMap<Integer, Integer> map, int val) {
        int count = map.get(val);
        if (count == 1) map.remove(val);
        else map.put(val, count - 1);
    }

    void balance() {
        while (sizeLeft > sizeRight + 1) {
            int val = left.lastKey();
            remove(left, val);
            sumLeft -= val;
            sizeLeft--;

            add(right, val);
            sumRight += val;
            sizeRight++;
        }

        while (sizeLeft < sizeRight) {
            int val = right.firstKey();
            remove(right, val);
            sumRight -= val;
            sizeRight--;

            add(left, val);
            sumLeft += val;
            sizeLeft++;
        }
    }

    void insert(int val) {
        if (left.isEmpty() || val <= left.lastKey()) {
            add(left, val);
            sumLeft += val;
            sizeLeft++;
        } else {
            add(right, val);
            sumRight += val;
            sizeRight++;
        }
        balance();
    }

    void erase(int val) {
        if (left.containsKey(val)) {
            remove(left, val);
            sumLeft -= val;
            sizeLeft--;
        } else {
            remove(right, val);
            sumRight -= val;
            sizeRight--;
        }
        balance();
    }

    long getCost() {
        long median = left.lastKey();
        return median * sizeLeft - sumLeft
             + sumRight - median * sizeRight;
    }

    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;

        long[] costs = new long[n - x + 1];

        for (int i = 0; i < n; i++) {
            insert(nums[i]);

            if (i >= x) {
                erase(nums[i - x]);
            }

            if (i >= x - 1) {
                costs[i - x + 1] = getCost();
            }
        }

        long INF = 1L << 50;

        long[][] dp = new long[n + 1][k + 1];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], INF);
        }

        dp[0][0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= k; j++) {
                dp[i][j] = dp[i - 1][j];

                if (j > 0 && i >= x) {
                    long prev = dp[i - x][j - 1];

                    if (prev != INF) {
                        dp[i][j] = Math.min(
                            dp[i][j],
                            prev + costs[i - x]
                        );
                    }
                }
            }
        }

        return dp[n][k];
    }
}
