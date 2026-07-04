import java.util.HashMap;

class Solution {
    public int lengthOfLongestSubstring(String s) {
        HashMap<Character, Integer> hashMap = new HashMap<>();
        int left = 0;
        int maxLength = 0;
        
        for (int i = 0; i < s.length(); i++) {
            char currentChar = s.charAt(i);
            
            if (hashMap.containsKey(currentChar) && hashMap.get(currentChar) >= left) {
                left = hashMap.get(currentChar) + 1;
            }
            
            hashMap.put(currentChar, i);
            
            maxLength = Math.max(maxLength, i - left + 1);
        }
        
        return maxLength;
    }
}