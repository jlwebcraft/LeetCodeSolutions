class Solution {
    public long findKthSmallest(int[] coins, int k) {
        int n = coins.length;
        int numSubsets = 1 << n;
        long[] lcms = new long[numSubsets];
        int[] signs = new int[numSubsets];
        
        for (int i = 1; i < numSubsets; i++) {
            int lowestBit = Integer.lowestOneBit(i);
            int prev = i ^ lowestBit;
            int coinIdx = Integer.numberOfTrailingZeros(lowestBit);
            
            if (prev == 0) {
                lcms[i] = coins[coinIdx];
                signs[i] = 1;
            } else {
                lcms[i] = lcm(lcms[prev], coins[coinIdx]);
                signs[i] = -signs[prev];
            }
        }
        
        long low = 1;
        long high = 50_000_000_000L; 
        
        while (low < high) {
            long mid = low + (high - low) / 2;
            long count = 0;
            
            for (int i = 1; i < numSubsets; i++) {
                count += signs[i] * (mid / lcms[i]);
            }
            
            if (count >= k) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        
        return low;
    }
    
    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    
    private long lcm(long a, long b) {
        return (a / gcd(a, b)) * b;
    }
}