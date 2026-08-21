import java.util.*;

class Solution {

    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

    private long lcm(long a, long b, long limit) {
        long g = gcd(a, b);

        // Prevent overflow
        if (a / g > limit / b) {
            return limit + 1;
        }

        return (a / g) * b;
    }

    // Number of distinct achievable amounts <= x
    private long count(long x, int[] coins) {
        int n = coins.length;
        long total = 0;

        for (int mask = 1; mask < (1 << n); mask++) {
            long currentLcm = 1;
            int bits = 0;
            boolean tooLarge = false;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    bits++;

                    currentLcm = lcm(currentLcm, coins[i], x);

                    if (currentLcm > x) {
                        tooLarge = true;
                        break;
                    }
                }
            }

            if (tooLarge) {
                continue;
            }

            long multiples = x / currentLcm;

            if ((bits & 1) == 1) {
                total += multiples;
            } else {
                total -= multiples;
            }
        }

        return total;
    }

    public long findKthSmallest(int[] coins, long k) {
        int minCoin = Arrays.stream(coins).min().getAsInt();

        long left = 1;
        long right = (long) minCoin * k;

        while (left < right) {
            long mid = left + (right - left) / 2;

            if (count(mid, coins) >= k) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }
}
