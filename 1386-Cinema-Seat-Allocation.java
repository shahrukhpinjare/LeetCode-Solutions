import java.util.*;

class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        Map<Integer, Integer> map = new HashMap<>();

        // Store reserved seats as a bitmask for each row.
        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int col = seat[1];

            map.put(row, map.getOrDefault(row, 0) | (1 << col));
        }

        // Seats 2,3,4,5
        int left = (1 << 2) | (1 << 3) | (1 << 4) | (1 << 5);

        // Seats 4,5,6,7
        int middle = (1 << 4) | (1 << 5) | (1 << 6) | (1 << 7);

        // Seats 6,7,8,9
        int right = (1 << 6) | (1 << 7) | (1 << 8) | (1 << 9);

        int answer = 2 * n;

        for (int mask : map.values()) {
            boolean leftFree = (mask & left) == 0;
            boolean middleFree = (mask & middle) == 0;
            boolean rightFree = (mask & right) == 0;

            if (leftFree && rightFree) {
                // Two groups can be seated.
                // Already counted in 2 * n.
            } else if (leftFree || middleFree || rightFree) {
                // Only one group can be seated.
                answer--;
            } else {
                // No group can be seated.
                answer -= 2;
            }
        }

        return answer;
    }
}
