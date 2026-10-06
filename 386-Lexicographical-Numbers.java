import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> lexicalOrder(int n) {

        List<Integer> result = new ArrayList<>();
        int curr = 1;

        for(int i = 0; i < n; i++){
            result.add(curr);

            // Go in Tree (1 -> 10 -> 100 ->)
            if(curr * 10 <= n){
                curr *= 10;
            } else {
                // if reach bound || end with 9 then backtrack
                while(curr % 10 == 9 || curr >= n){
                    curr /= 10;
                }
                // Move to next sibling
                curr++;
            }
        }

        return result;
    }
}