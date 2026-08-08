class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        char[] w1 = word1.toCharArray();
        char[] w2 = word2.toCharArray();
        
        int[] suf = new int[n + 1];
        int j = m - 1;
        for (int i = n - 1; i >= 0; i--) {
            if (j >= 0 && w1[i] == w2[j]) {
                j--;
            }
            suf[i] = (m - 1) - j;
        }
        
        int[] ans = new int[m];
        int ansIdx = 0;
        boolean changed = false;
        j = 0;
        
        for (int i = 0; i < n && j < m; i++) {
            if (w1[i] == w2[j]) {
                ans[ansIdx++] = i;
                j++;
            } else if (!changed && suf[i + 1] >= m - 1 - j) {
                changed = true;
                ans[ansIdx++] = i;
                j++;
            }
        }
        
        if (j == m) {
            return ans;
        } else {
            return new int[0];
        }
    }
}