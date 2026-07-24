class Solution {
    public int uniqueXorTriplets(int[] nums) {
        int max = 0;
        for (int n : nums) {
            if (n > max) max = n;
        }
        
        int limit = 1;
        while (limit <= max) {
            limit *= 2;
        }
        
        boolean[] seen = new boolean[limit];
        int[] uniq = new int[nums.length];
        int uSize = 0;
        for (int n : nums) {
            if (!seen[n]) {
                seen[n] = true;
                uniq[uSize++] = n;
            }
        }
        
        boolean[] pairSeen = new boolean[limit];
        int[] pairs = new int[limit];
        int pSize = 0;
        for (int i = 0; i < uSize; i++) {
            for (int j = i; j < uSize; j++) {
                int val = uniq[i] ^ uniq[j];
                if (!pairSeen[val]) {
                    pairSeen[val] = true;
                    pairs[pSize++] = val;
                }
            }
        }
        
        boolean[] tripletSeen = new boolean[limit];
        int ans = 0;
        for (int i = 0; i < pSize; i++) {
            for (int j = 0; j < uSize; j++) {
                int val = pairs[i] ^ uniq[j];
                if (!tripletSeen[val]) {
                    tripletSeen[val] = true;
                    ans++;
                }
            }
        }
        
        return ans;
    }
}