1class Solution {
2
3    public int countMajoritySubarrays(int[] nums, int target) {
4
5        int n = nums.length;
6
7        int[] prefix = new int[n + 1];
8
9        for (int i = 0; i < n; i++) {
10            prefix[i + 1] = prefix[i] +
11                    (nums[i] == target ? 1 : -1);
12        }
13
14        int ans = 0;
15
16        for (int l = 0; l < n; l++) {
17            for (int r = l; r < n; r++) {
18
19                int sum = prefix[r + 1] - prefix[l];
20
21                if (sum > 0) {
22                    ans++;
23                }
24            }
25        }
26
27        return ans;
28    }
29}