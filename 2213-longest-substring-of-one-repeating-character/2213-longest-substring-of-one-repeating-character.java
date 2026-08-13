class Solution {
    private int[] maxLen;
    private int[] prefLen;
    private int[] suffLen;
    private char[] leftChar;
    private char[] rightChar;

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        int n = s.length();
        maxLen = new int[4 * n + 1];
        prefLen = new int[4 * n + 1];
        suffLen = new int[4 * n + 1];
        leftChar = new char[4 * n + 1];
        rightChar = new char[4 * n + 1];
        
        build(1, 0, n - 1, s.toCharArray());
        
        int k = queryIndices.length;
        int[] ans = new int[k];
        
        for (int i = 0; i < k; i++) {
            update(1, 0, n - 1, queryIndices[i], queryCharacters.charAt(i));
            ans[i] = maxLen[1];
        }
        
        return ans;
    }
    
    private void build(int node, int L, int R, char[] s) {
        if (L == R) {
            maxLen[node] = prefLen[node] = suffLen[node] = 1;
            leftChar[node] = rightChar[node] = s[L];
            return;
        }
        
        int mid = L + (R - L) / 2;
        int leftNode = 2 * node;
        int rightNode = 2 * node + 1;
        
        build(leftNode, L, mid, s);
        build(rightNode, mid + 1, R, s);
        
        merge(node, L, mid, R);
    }
    
    private void update(int node, int L, int R, int idx, char ch) {
        if (L == R) {
            leftChar[node] = rightChar[node] = ch;
            return;
        }
        
        int mid = L + (R - L) / 2;
        int leftNode = 2 * node;
        int rightNode = 2 * node + 1;
        
        if (idx <= mid) {
            update(leftNode, L, mid, idx, ch);
        } else {
            update(rightNode, mid + 1, R, idx, ch);
        }
        
        merge(node, L, mid, R);
    }
    
    private void merge(int node, int L, int mid, int R) {
        int leftNode = 2 * node;
        int rightNode = 2 * node + 1;
        
        int leftSize = mid - L + 1;
        int rightSize = R - mid;
        
        leftChar[node] = leftChar[leftNode];
        rightChar[node] = rightChar[rightNode];
        
        prefLen[node] = prefLen[leftNode];
        if (leftChar[leftNode] == leftChar[rightNode] && prefLen[leftNode] == leftSize) {
            prefLen[node] += prefLen[rightNode];
        }
        
        suffLen[node] = suffLen[rightNode];
        if (rightChar[leftNode] == rightChar[rightNode] && suffLen[rightNode] == rightSize) {
            suffLen[node] += suffLen[leftNode];
        }
        
        maxLen[node] = Math.max(maxLen[leftNode], maxLen[rightNode]);
        if (rightChar[leftNode] == leftChar[rightNode]) {
            maxLen[node] = Math.max(maxLen[node], suffLen[leftNode] + prefLen[rightNode]);
        }
    }
}