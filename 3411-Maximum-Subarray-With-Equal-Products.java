
class Solution {
    public int maxLength(int[] nums) {
        int n = nums.length;
        int ans = 0;

        for (int i = 0; i < n; i++) {
            long product = 1;
            long lcm = 1;
            int gcd = 0;

            for (int j = i; j < n; j++) {
                product *= nums[j];

                gcd = findGCD(gcd, nums[j]);
                lcm = findLCM(lcm, nums[j]);

                if (product == lcm * gcd) {
                    ans = Math.max(ans, j - i + 1);
                }
            }
        }

        return ans;
    }

    private int findGCD(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private long findLCM(long a, int b) {
        return (a / findGCD((int) a, b)) * b;
    }
}
