class Solution {
    public String smallestNumber(String num, long t) {
        long temp = t;
        int[] req = new int[8];
        while (temp % 2 == 0) { req[2]++; temp /= 2; }
        while (temp % 3 == 0) { req[3]++; temp /= 3; }
        while (temp % 5 == 0) { req[5]++; temp /= 5; }
        while (temp % 7 == 0) { req[7]++; temp /= 7; }
        
        // If t has any prime factors other than 2, 3, 5, or 7, it's impossible.
        if (temp > 1) {
            return "-1";
        }

        // dp[i][j] will store the minimum number of digits needed to get at least i twos and j threes.
        int[][] dp = new int[60][40];
        for (int[] r : dp) {
            java.util.Arrays.fill(r, 999999);
        }
        dp[0][0] = 0;
        
        for (int i = 0; i < 60; i++) {
            for (int j = 0; j < 40; j++) {
                if (i == 0 && j == 0) continue;
                int min = 999999;
                min = Math.min(min, dp[Math.max(0, i - 1)][j] + 1); // Using '2'
                min = Math.min(min, dp[i][Math.max(0, j - 1)] + 1); // Using '3'
                min = Math.min(min, dp[Math.max(0, i - 2)][j] + 1); // Using '4'
                min = Math.min(min, dp[Math.max(0, i - 1)][Math.max(0, j - 1)] + 1); // Using '6'
                min = Math.min(min, dp[Math.max(0, i - 3)][j] + 1); // Using '8'
                min = Math.min(min, dp[i][Math.max(0, j - 2)] + 1); // Using '9'
                dp[i][j] = min;
            }
        }

        int[][] count = new int[10][8];
        count[2][2] = 1;
        count[3][3] = 1;
        count[4][2] = 2;
        count[5][5] = 1;
        count[6][2] = 1; count[6][3] = 1;
        count[7][7] = 1;
        count[8][2] = 3;
        count[9][3] = 2;

        int n = num.length();
        int[][] pref = new int[n + 1][8];
        int zero_idx = n;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 8; j++) pref[i + 1][j] = pref[i][j];
            int d = num.charAt(i) - '0';
            if (d == 0) {
                if (zero_idx == n) zero_idx = i; // Record the first occurrence of '0'
            } else {
                pref[i + 1][2] += count[d][2];
                pref[i + 1][3] += count[d][3];
                pref[i + 1][5] += count[d][5];
                pref[i + 1][7] += count[d][7];
            }
        }

        // Check if the original number itself satisfies the condition
        if (zero_idx == n) {
            if (pref[n][2] >= req[2] && pref[n][3] >= req[3] && 
                pref[n][5] >= req[5] && pref[n][7] >= req[7]) {
                return num;
            }
        }

        // Try to replace a digit with a larger one and fill the rest to satisfy requirements
        for (int i = Math.min(n - 1, zero_idx); i >= 0; i--) {
            int orig_d = num.charAt(i) - '0';
            for (int d = Math.max(1, orig_d + 1); d <= 9; d++) {
                int nr2 = Math.max(0, req[2] - pref[i][2] - count[d][2]);
                int nr3 = Math.max(0, req[3] - pref[i][3] - count[d][3]);
                int nr5 = Math.max(0, req[5] - pref[i][5] - count[d][5]);
                int nr7 = Math.max(0, req[7] - pref[i][7] - count[d][7]);

                // Check if we can satisfy the remaining prime factors within the remaining length
                if (nr5 + nr7 + dp[nr2][nr3] <= n - 1 - i) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(num.substring(0, i));
                    sb.append(d);
                    sb.append(buildSuffix(n - 1 - i, nr2, nr3, nr5, nr7, dp, count));
                    return sb.toString();
                }
            }
        }

        // If no number of the same length works, we need to append digits.
        int target_len = n + 1;
        while (true) {
            if (req[5] + req[7] + dp[req[2]][req[3]] <= target_len) {
                return buildSuffix(target_len, req[2], req[3], req[5], req[7], dp, count);
            }
            target_len++;
        }
    }

    private String buildSuffix(int L, int r2, int r3, int r5, int r7, int[][] dp, int[][] count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < L; i++) {
            for (int d = 1; d <= 9; d++) {
                int nr2 = Math.max(0, r2 - count[d][2]);
                int nr3 = Math.max(0, r3 - count[d][3]);
                int nr5 = Math.max(0, r5 - count[d][5]);
                int nr7 = Math.max(0, r7 - count[d][7]);
                
                if (nr5 + nr7 + dp[nr2][nr3] <= L - 1 - i) {
                    sb.append(d);
                    r2 = nr2; 
                    r3 = nr3; 
                    r5 = nr5; 
                    r7 = nr7;
                    break;
                }
            }
        }
        return sb.toString();
    }
}