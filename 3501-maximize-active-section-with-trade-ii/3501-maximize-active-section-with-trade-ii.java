import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        int[] pref = new int[n + 1];
        List<int[]> z = new ArrayList<>();
        
        int start = -1;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') {
                pref[i + 1] = pref[i] + 1;
                if (start != -1) {
                    z.add(new int[]{start, i - 1});
                    start = -1;
                }
            } else {
                pref[i + 1] = pref[i];
                if (start == -1) start = i;
            }
        }
        if (start != -1) {
            z.add(new int[]{start, n - 1});
        }
        
        int m = z.size();
        int[] v = new int[Math.max(0, m - 1)];
        for (int i = 0; i < m - 1; i++) {
            v[i] = (z.get(i)[1] - z.get(i)[0] + 1) + (z.get(i + 1)[1] - z.get(i + 1)[0] + 1);
        }
        
        ST st = new ST(v);
        List<Integer> res = new ArrayList<>();
        
        int totalOnes = pref[n];
        
        for (int[] qPair : queries) {
            int L = qPair[0];
            int R = qPair[1];
            
            int p = -1;
            int l = 0, r = m - 1;
            while (l <= r) {
                int mid = l + (r - l) / 2;
                if (z.get(mid)[1] >= L) {
                    p = mid;
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
            if (p != -1 && z.get(p)[0] > R) p = -1;
            
            int q = -1;
            l = 0; r = m - 1;
            while (l <= r) {
                int mid = l + (r - l) / 2;
                if (z.get(mid)[0] <= R) {
                    q = mid;
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
            if (q != -1 && z.get(q)[1] < L) q = -1;
            
            if (p == -1 || q == -1 || p >= q) {
                res.add(totalOnes);
                continue;
            }
            
            int wp = Math.min(R, z.get(p)[1]) - Math.max(L, z.get(p)[0]) + 1;
            int wq = Math.min(R, z.get(q)[1]) - Math.max(L, z.get(q)[0]) + 1;
            
            int mx = 0;
            if (p == q - 1) {
                mx = wp + wq;
            } else {
                int p1 = z.get(p + 1)[1] - z.get(p + 1)[0] + 1;
                int qm1 = z.get(q - 1)[1] - z.get(q - 1)[0] + 1;
                mx = Math.max(wp + p1, qm1 + wq);
                
                if (q - 2 >= p + 1) {
                    mx = Math.max(mx, st.query(p + 1, q - 2));
                }
            }
            
            res.add(totalOnes + mx);
        }
        
        return res;
    }
    
    class ST {
        int[] tree;
        int n;
        
        public ST(int[] a) {
            n = a.length;
            if (n > 0) {
                tree = new int[4 * n];
                build(a, 0, 0, n - 1);
            }
        }
        
        void build(int[] a, int node, int s, int e) {
            if (s == e) {
                tree[node] = a[s];
                return;
            }
            int mid = s + (e - s) / 2;
            build(a, 2 * node + 1, s, mid);
            build(a, 2 * node + 2, mid + 1, e);
            tree[node] = Math.max(tree[2 * node + 1], tree[2 * node + 2]);
        }
        
        int query(int l, int r) {
            if (n == 0) return 0;
            return query(0, 0, n - 1, l, r);
        }
        
        int query(int node, int s, int e, int l, int r) {
            if (r < s || e < l) return 0;
            if (l <= s && e <= r) return tree[node];
            int mid = s + (e - s) / 2;
            return Math.max(query(2 * node + 1, s, mid, l, r), 
                            query(2 * node + 2, mid + 1, e, l, r));
        }
    }
}