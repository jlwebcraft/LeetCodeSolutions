class Solution {
    public int missingInteger(int[] nums) {
        int sum = nums[0];
        
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1] + 1) {
                sum += nums[i];
            } else {
                break;
            }
        }
        
        boolean[] seen = new boolean[51];
        for (int num : nums) {
            seen[num] = true;
        }
        
        while (sum <= 50 && seen[sum]) {
            sum++;
        }
        
        return sum;
    }
}