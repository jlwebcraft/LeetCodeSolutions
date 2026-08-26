class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int left = 0;
        int count = 0;
        String minStr = "";
        int minLen = Integer.MAX_VALUE;

        for (int right = 0; right < s.length(); right++) {
            if (s.charAt(right) == '1') {
                count++;
            }

            while (count == k) {
                String curr = s.substring(left, right + 1);
                
                if (curr.length() < minLen) {
                    minLen = curr.length();
                    minStr = curr;
                } else if (curr.length() == minLen && curr.compareTo(minStr) < 0) {
                    minStr = curr;
                }

                if (s.charAt(left) == '1') {
                    count--;
                }
                left++;
            }
        }
        
        return minStr;
    }
}