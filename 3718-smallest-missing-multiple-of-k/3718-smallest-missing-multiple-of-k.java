class Solution {
    public int missingMultiple(int[] nums, int k) {
        boolean[] seen = new boolean[101];
        
        for (int num : nums) {
            if (num <= 100) {
                seen[num] = true;
            }
        }
        
        int multiple = k;
        while (multiple <= 100 && seen[multiple]) {
            multiple += k;
        }
        
        return multiple;
    }
}