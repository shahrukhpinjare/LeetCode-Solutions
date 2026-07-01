1import java.util.*;
2
3class Solution {
4
5    int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
6
7    public int maximumSafenessFactor(List<List<Integer>> grid) {
8
9        int n = grid.size();
10        int[][] dist = new int[n][n];
11
12        for (int[] row : dist)
13            Arrays.fill(row, -1);
14
15        Queue<int[]> q = new LinkedList<>();
16
17        // Multi-source BFS from all thieves
18        for (int i = 0; i < n; i++) {
19            for (int j = 0; j < n; j++) {
20                if (grid.get(i).get(j) == 1) {
21                    dist[i][j] = 0;
22                    q.offer(new int[]{i, j});
23                }
24            }
25        }
26
27        while (!q.isEmpty()) {
28            int[] cur = q.poll();
29
30            for (int[] d : dirs) {
31                int nr = cur[0] + d[0];
32                int nc = cur[1] + d[1];
33
34                if (nr >= 0 && nr < n && nc >= 0 && nc < n &&
35                        dist[nr][nc] == -1) {
36
37                    dist[nr][nc] = dist[cur[0]][cur[1]] + 1;
38                    q.offer(new int[]{nr, nc});
39                }
40            }
41        }
42
43        int low = 0;
44        int high = 2 * n;
45        int ans = 0;
46
47        while (low <= high) {
48            int mid = low + (high - low) / 2;
49
50            if (canReach(dist, mid)) {
51                ans = mid;
52                low = mid + 1;
53            } else {
54                high = mid - 1;
55            }
56        }
57
58        return ans;
59    }
60
61    private boolean canReach(int[][] dist, int safe) {
62
63        int n = dist.length;
64
65        if (dist[0][0] < safe || dist[n - 1][n - 1] < safe)
66            return false;
67
68        boolean[][] vis = new boolean[n][n];
69        Queue<int[]> q = new LinkedList<>();
70
71        q.offer(new int[]{0, 0});
72        vis[0][0] = true;
73
74        while (!q.isEmpty()) {
75
76            int[] cur = q.poll();
77
78            if (cur[0] == n - 1 && cur[1] == n - 1)
79                return true;
80
81            for (int[] d : dirs) {
82
83                int nr = cur[0] + d[0];
84                int nc = cur[1] + d[1];
85
86                if (nr >= 0 && nr < n && nc >= 0 && nc < n &&
87                        !vis[nr][nc] &&
88                        dist[nr][nc] >= safe) {
89
90                    vis[nr][nc] = true;
91                    q.offer(new int[]{nr, nc});
92                }
93            }
94        }
95
96        return false;
97    }
98}