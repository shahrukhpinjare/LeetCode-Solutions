import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {
        int m = classroom.length;
        int n = classroom[0].length();

        int startR = -1;
        int startC = -1;

        List<int[]> litter = new ArrayList<>();

        // Find starting position and litter
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                char ch = classroom[r].charAt(c);

                if (ch == 'S') {
                    startR = r;
                    startC = c;
                } else if (ch == 'L') {
                    litter.add(new int[]{r, c});
                }
            }
        }

        int k = litter.size();

        // No litter to collect
        if (k == 0) {
            return 0;
        }

        // Give each litter cell a bit number
        int[][] litterId = new int[m][n];

        for (int r = 0; r < m; r++) {
            Arrays.fill(litterId[r], -1);
        }

        for (int i = 0; i < k; i++) {
            int r = litter.get(i)[0];
            int c = litter.get(i)[1];

            litterId[r][c] = i;
        }

        // All litter collected
        int allCollected = (1 << k) - 1;

        /*
         * visited[r][c][mask][remainingEnergy]
         *
         * mask tells us which litter has been collected.
         */
        boolean[][][][] visited =
                new boolean[m][n][1 << k][energy + 1];

        /*
         * State:
         * [0] = row
         * [1] = column
         * [2] = collected litter mask
         * [3] = remaining energy
         */
        Queue<int[]> queue = new ArrayDeque<>();

        visited[startR][startC][0][energy] = true;

        queue.offer(new int[]{
                startR,
                startC,
                0,
                energy
        });

        int[][] directions = {
                {1, 0},   // down
                {-1, 0},  // up
                {0, 1},   // right
                {0, -1}   // left
        };

        int moves = 0;

        // BFS
        while (!queue.isEmpty()) {

            int size = queue.size();

            // Every level represents one move
            while (size-- > 0) {

                int[] current = queue.poll();

                int r = current[0];
                int c = current[1];
                int mask = current[2];
                int remainingEnergy = current[3];

                // All litter collected
                if (mask == allCollected) {
                    return moves;
                }

                // Try all 4 directions
                for (int[] dir : directions) {

                    int nr = r + dir[0];
                    int nc = c + dir[1];

                    // Outside grid
                    if (nr < 0 || nr >= m || nc < 0 || nc >= n) {
                        continue;
                    }

                    // Obstacle
                    if (classroom[nr].charAt(nc) == 'X') {
                        continue;
                    }

                    // Cannot move with zero energy
                    if (remainingEnergy == 0) {
                        continue;
                    }

                    // Moving costs 1 energy
                    int newEnergy = remainingEnergy - 1;

                    int newMask = mask;

                    // If we move onto litter, collect it
                    if (litterId[nr][nc] != -1) {
                        int litterIndex = litterId[nr][nc];

                        newMask |= (1 << litterIndex);
                    }

                    // Reset area restores energy
                    if (classroom[nr].charAt(nc) == 'R') {
                        newEnergy = energy;
                    }

                    // If this state hasn't been visited
                    if (!visited[nr][nc][newMask][newEnergy]) {

                        visited[nr][nc][newMask][newEnergy] = true;

                        queue.offer(new int[]{
                                nr,
                                nc,
                                newMask,
                                newEnergy
                        });
                    }
                }
            }

            moves++;
        }

        // Impossible to collect all litter
        return -1;
    }
}
