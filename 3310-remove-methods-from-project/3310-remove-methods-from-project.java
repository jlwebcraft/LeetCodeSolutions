import java.util.*;

class Solution {
    public List<Integer> remainingMethods(int n, int k, int[][] invocations) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int[] inv : invocations) {
            graph.get(inv[0]).add(inv[1]);
        }
        
        boolean[] suspicious = new boolean[n];
        Queue<Integer> q = new LinkedList<>();
        q.offer(k);
        suspicious[k] = true;
        
        while (!q.isEmpty()) {
            int curr = q.poll();
            for (int next : graph.get(curr)) {
                if (!suspicious[next]) {
                    suspicious[next] = true;
                    q.offer(next);
                }
            }
        }
        
        boolean canRemove = true;
        for (int[] inv : invocations) {
            if (!suspicious[inv[0]] && suspicious[inv[1]]) {
                canRemove = false;
                break;
            }
        }
        
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (!canRemove || !suspicious[i]) {
                res.add(i);
            }
        }
        
        return res;
    }
}