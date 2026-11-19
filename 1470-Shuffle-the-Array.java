class Solution {
    public int[] shuffle(int[] nums, int n) {
        int ans[] = new int[2*n];
        int start = 0;
        int end = n;
        for(int i = 0; i < ans.length; i++){
            if( i % 2 == 0){
                ans[i] = nums[start];
                start++;
            } else {
                ans[i] = nums[end];
                end++;
            }
        }

        return ans;
    }
}