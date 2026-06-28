1import java.util.Arrays;
2
3class Solution {
4    public int maximumElementAfterDecrementingAndRearranging(int[] arr) {
5        Arrays.sort(arr);
6
7        arr[0] = 1;
8
9        for (int i = 1; i < arr.length; i++) {
10            arr[i] = Math.min(arr[i], arr[i - 1] + 1);
11        }
12
13        return arr[arr.length - 1];
14    }
15}