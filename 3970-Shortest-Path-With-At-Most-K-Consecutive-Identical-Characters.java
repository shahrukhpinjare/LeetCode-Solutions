
import java.util.*;

class Solution {
    public int shortestPath(int n, int[][] edges, String labels, int k) {

        // Build adjacency list
        List<int[]>[] adj = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            adj[e[0]].add(new int[]{e[1], e[2]});
        }

        // dist[node][consecutive count]
        int[][] dist = new int[n][k + 1];

        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }

        // PriorityQueue: {distance, node, consecutive count}
        PriorityQueue<int[]> pq = new PriorityQueue<>(
            (a, b) -> Integer.compare(a[0], b[0])
        );

        // Start from node 0
        dist[0][1] = 0;
        pq.offer(new int[]{0, 0, 1});

        while (!pq.isEmpty()) {

            int[] curr = pq.poll();

            int d = curr[0];
            int u = curr[1];
            int count = curr[2];

            // Skip outdated states
            if (d != dist[u][count]) {
                continue;
            }

            // Destination reached
            if (u == n - 1) {
                return d;
            }

            // Explore neighbors
            for (int[] edge : adj[u]) {

                int v = edge[0];
                int w = edge[1];

                int newCount;

                // Compare current and next node labels
                if (labels.charAt(u) == labels.charAt(v)) {
                    newCount = count + 1;
                } else {
                    newCount = 1;
                }

                // Check consecutive character constraint
                if (newCount > k) {
                    continue;
                }

                int newDist = d + w;

                // Relaxation
                if (newDist < dist[v][newCount]) {

                    dist[v][newCount] = newDist;

                    pq.offer(new int[]{
                        newDist,
                        v,
                        newCount
                    });
                }
            }
        }

        // No valid path exists
        return -1;
    }
}
