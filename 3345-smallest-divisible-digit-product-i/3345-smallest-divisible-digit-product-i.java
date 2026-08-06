class Solution {
    public int smallestNumber(int n, int t) {
        while (true) {
            if (productOfDigits(n) % t == 0) {
                return n;
            }
            n++;
        }
    }
    
    private int productOfDigits(int num) {
        int prod = 1;
        if (num == 0) return 0;
        while (num > 0) {
            prod *= num % 10;
            num /= 10;
        }
        return prod;
    }
}