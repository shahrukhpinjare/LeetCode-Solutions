import java.util.*;

class Solution {
    public int[] pathExistenceQueries(int n, int[] nums, int maxDiff, int[][] queries) {
        // Store {value, originalIndex}
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; i++) {
            arr[i][0] = nums[i];
            arr[i][1] = i;
        }

        Arrays.sort(arr, (a, b) -> Integer.compare(a[0], b[0]));

        // pos[originalIndex] = position in sorted array
        int[] pos = new int[n];

        // component id for each position in sorted order
        int[] comp = new int[n];

        int component = 0;
        pos[arr[0][1]] = 0;
        comp[0] = 0;

        for (int i = 1; i < n; i++) {
            pos[arr[i][1]] = i;
            if (arr[i][0] - arr[i - 1][0] > maxDiff) {
                component++;
            }
            comp[i] = component;
        }

        // next[i] = farthest sorted position reachable in one edge
        int[] next = new int[n];
        int j = 0;
        for (int i = 0; i < n; i++) {
            while (j + 1 < n && arr[j + 1][0] - arr[i][0] <= maxDiff) {
                j++;
            }
            next[i] = j;
        }

        int LOG = 18; // since n <= 100000

        int[][] up = new int[LOG][n];
        for (int i = 0; i < n; i++) {
            up[0][i] = next[i];
        }

        for (int k = 1; k < LOG; k++) {
            for (int i = 0; i < n; i++) {
                up[k][i] = up[k - 1][up[k - 1][i]];
            }
        }

        int[] ans = new int[queries.length];

        for (int idx = 0; idx < queries.length; idx++) {
            int u = pos[queries[idx][0]];
            int v = pos[queries[idx][1]];

            if (comp[u] != comp[v]) {
                ans[idx] = -1;
                continue;
            }

            if (u == v) {
                ans[idx] = 0;
                continue;
            }

            if (u > v) {
                int temp = u;
                u = v;
                v = temp;
            }

            int cur = u;
            int steps = 0;

            for (int k = LOG - 1; k >= 0; k--) {
                if (up[k][cur] < v) {
                    cur = up[k][cur];
                    steps += 1 << k;
                }
            }

            ans[idx] = steps + 1;
        }

        return ans;
    }
}