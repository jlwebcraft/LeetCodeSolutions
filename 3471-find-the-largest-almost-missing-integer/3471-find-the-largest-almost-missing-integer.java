class Solution {
    public int largestInteger(int[] nums, int k) {
        int n = nums.length;
        int[] count = new int[51]; 
        
        for (int i = 0; i <= n - k; i++) {
            boolean[] seenInSubarray = new boolean[51];
            for (int j = i; j < i + k; j++) {
                seenInSubarray[nums[j]] = true;
            }
            
            for (int val = 0; val <= 50; val++) {
                if (seenInSubarray[val]) {
                    count[val]++;
                }
            }
        }
        
        int maxVal = -1;
        for (int val = 0; val <= 50; val++) {
            if (count[val] == 1) {
                maxVal = Math.max(maxVal, val);
            }
        }
        
        return maxVal;
    }
}