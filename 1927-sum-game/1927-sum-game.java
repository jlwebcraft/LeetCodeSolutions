class Solution {
    public boolean sumGame(String num) {
        int n = num.length();
        int sumL = 0, countL = 0;
        int sumR = 0, countR = 0;
        
        for (int i = 0; i < n / 2; i++) {
            char c = num.charAt(i);
            if (c == '?') {
                countL++;
            } else {
                sumL += c - '0';
            }
        }

        for (int i = n / 2; i < n; i++) {
            char c = num.charAt(i);
            if (c == '?') {
                countR++;
            } else {
                sumR += c - '0';
            }
        }
        
        if ((countL + countR) % 2 != 0) {
            return true;
        }
        
        return (sumL - sumR) * 2 != 9 * (countR - countL);
    }
}