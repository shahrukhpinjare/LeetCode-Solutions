
import java.util.*;

class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {

        int n = servers.length;
        int m = tasks.length;

        // Free servers: [weight, index]
        PriorityQueue<int[]> free = new PriorityQueue<>(
            (a, b) -> {
                if (a[0] != b[0])
                    return a[0] - b[0];
                return a[1] - b[1];
            }
        );

        // Busy servers: [freeTime, weight, index]
        PriorityQueue<int[]> busy = new PriorityQueue<>(
            (a, b) -> {
                if (a[0] != b[0])
                    return Integer.compare(a[0], b[0]);
                if (a[1] != b[1])
                    return Integer.compare(a[1], b[1]);
                return Integer.compare(a[2], b[2]);
            }
        );

        // Initially all servers are free
        for (int i = 0; i < n; i++) {
            free.offer(new int[]{servers[i], i});
        }

        int[] ans = new int[m];

        for (int j = 0; j < m; j++) {

            // Release all servers that are free at time j
            while (!busy.isEmpty() && busy.peek()[0] <= j) {
                int[] server = busy.poll();

                free.offer(new int[]{server[1], server[2]});
            }

            // If no server is free, jump to the earliest free time
            if (free.isEmpty()) {

                int time = busy.peek()[0];

                // Release all servers available at this time
                while (!busy.isEmpty() && busy.peek()[0] <= time) {
                    int[] server = busy.poll();

                    free.offer(new int[]{server[1], server[2]});
                }
            }

            // Assign the task to the best available server
            int[] server = free.poll();

            int weight = server[0];
            int index = server[1];

            ans[j] = index;

            // Add server to busy queue
            busy.offer(new int[]{
                j + tasks[j],
                weight,
                index
            });
        }

        return ans;
    }
}
