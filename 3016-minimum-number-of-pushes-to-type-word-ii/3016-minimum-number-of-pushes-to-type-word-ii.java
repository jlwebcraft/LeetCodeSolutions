import java.util.Arrays;

class Solution {
    public int minimumPushes(String word) {
        int[] count = new int[26];
        for (char c : word.toCharArray()) {
            count[c - 'a']++;
        }
        
        Arrays.sort(count);
        
        int ans = 0;
        for (int i = 25; i >= 0; i--) {
            if (count[i] == 0) break;
            ans += count[i] * (((25 - i) / 8) + 1);
        }
        
        return ans;
    }
}