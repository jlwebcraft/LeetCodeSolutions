class Solution {
    public int[] gcdValues(int[] nums, long[] queries) {
        int max = 0;
        for (int n : nums) {
            if (n > max) max = n;
        }

        long[] count = new long[max + 1];
        for (int n : nums) {
            count[n]++;
        }

        long[] mul = new long[max + 1];
        for (int i = 1; i <= max; i++) {
            for (int j = i; j <= max; j += i) {
                mul[i] += count[j];
            }
        }

        long[] gcd = new long[max + 1];
        for (int i = max; i >= 1; i--) {
            long pairs = mul[i] * (mul[i] - 1) / 2;
            for (int j = 2 * i; j <= max; j += i) {
                pairs -= gcd[j];
            }
            gcd[i] = pairs;
        }

        long[] pref = new long[max + 1];
        for (int i = 1; i <= max; i++) {
            pref[i] = pref[i - 1] + gcd[i];
        }

        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            long val = queries[i];
            int l = 1, r = max, ans = max;
            
            while (l <= r) {
                int mid = l + (r - l) / 2;
                if (pref[mid] > val) {
                    ans = mid;
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
            res[i] = ans;
        }

        return res;
    }
}