import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> findMissingElements(int[] nums) {
        boolean[] seen = new boolean[101];
        int min = 101;
        int max = 0;
        
        for (int n : nums) {
            seen[n] = true;
            if (n < min) min = n;
            if (n > max) max = n;
        }
        
        List<Integer> res = new ArrayList<>();
        for (int i = min + 1; i < max; i++) {
            if (!seen[i]) {
                res.add(i);
            }
        }
        
        return res;
    }
}