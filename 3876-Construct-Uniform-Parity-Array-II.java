class Solution {
    public boolean uniformArray(int[] nums1) {
        int minOdd = Integer.MAX_VALUE;
        int minEven = Integer.MAX_VALUE;

        for (int x : nums1) {
            if ((x & 1) == 0) {
                minEven = Math.min(minEven, x);
            } else {
                minOdd = Math.min(minOdd, x);
            }
        }

        // Already uniform
        if (minOdd == Integer.MAX_VALUE || minEven == Integer.MAX_VALUE) {
            return true;
        }

        /*
         * Try making everything odd.
         * Even x needs a smaller odd number.
         */
        boolean makeOdd = true;
        for (int x : nums1) {
            if ((x & 1) == 0 && minOdd >= x) {
                makeOdd = false;
                break;
            }
        }

        if (makeOdd) {
            return true;
        }

        /*
         * Try making everything even.
         * Odd x needs a smaller odd number, which gives even difference.
         *
         * Since nums1 contains distinct integers, the smallest odd
         * number cannot subtract from itself. Therefore, for every odd
         * number, we need another smaller odd number.
         */
        boolean makeEven = true;
        int smallestOdd = minOdd;
        int secondSmallestOdd = Integer.MAX_VALUE;

        for (int x : nums1) {
            if ((x & 1) == 1 && x != smallestOdd) {
                secondSmallestOdd = Math.min(secondSmallestOdd, x);
            }
        }

        for (int x : nums1) {
            if ((x & 1) == 1 && secondSmallestOdd >= x) {
                makeEven = false;
                break;
            }
        }

        return makeEven;
    }
}
