class Solution {
    private static final long MAX_VAL = 2000000000L;
    private long[][] c;

    public String smallestPalindrome(String s, int k) {
        int[] count = new int[26];
        for (char ch : s.toCharArray()) {
            count[ch - 'a']++;
        }
        
        int n = s.length();
        int halfLen = n / 2;
        int[] halfCount = new int[26];
        String mid = "";
        
        for (int i = 0; i < 26; i++) {
            if (count[i] % 2 != 0) {
                mid = String.valueOf((char) ('a' + i));
            }
            halfCount[i] = count[i] / 2;
        }
        
        buildComb(halfLen);
        
        long totalPerms = countPermutations(halfCount, halfLen);
        if (k > totalPerms) {
            return "";
        }
        
        StringBuilder left = new StringBuilder();
        for (int pos = 0; pos < halfLen; pos++) {
            for (int i = 0; i < 26; i++) {
                if (halfCount[i] == 0) continue;
                
                halfCount[i]--;
                long ways = countPermutations(halfCount, halfLen - pos - 1);
                
                if (k <= ways) {
                    left.append((char) ('a' + i));
                    break;
                } else {
                    k -= (int) ways;
                    halfCount[i]++;
                }
            }
        }
        
        String right = new StringBuilder(left).reverse().toString();
        return left.append(mid).append(right).toString();
    }
    
    private void buildComb(int maxLen) {
        c = new long[maxLen + 1][maxLen + 1];
        for (int i = 0; i <= maxLen; i++) {
            c[i][0] = 1;
            for (int j = 1; j <= i; j++) {
                c[i][j] = c[i - 1][j - 1] + c[i - 1][j];
                if (c[i][j] > MAX_VAL) c[i][j] = MAX_VAL;
            }
        }
    }
    
    private long countPermutations(int[] counts, int totalLen) {
        if (totalLen == 0) return 1;
        long ways = 1;
        int rem = totalLen;
        for (int count : counts) {
            if (count > 0) {
                ways = multiply(ways, c[rem][count]);
                rem -= count;
            }
        }
        return ways;
    }
    
    private long multiply(long a, long b) {
        if (a == 0 || b == 0) return 0;
        if (a > MAX_VAL / b) return MAX_VAL;
        return a * b;
    }
}