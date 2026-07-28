class Solution {
    public String smallestPalindrome(String s) {
        int[] count = new int[26];
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }
        
        StringBuilder left = new StringBuilder();
        String mid = "";
        
        for (int i = 0; i < 26; i++) {
            if (count[i] % 2 != 0) {
                mid = String.valueOf((char) ('a' + i));
            }
            for (int j = 0; j < count[i] / 2; j++) {
                left.append((char) ('a' + i));
            }
        }
        
        String right = new StringBuilder(left).reverse().toString();
        return left.append(mid).append(right).toString();
    }
}