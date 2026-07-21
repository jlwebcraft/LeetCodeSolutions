import java.util.ArrayList;
import java.util.List;

class Solution {
    public int maxActiveSectionsAfterTrade(String s) {
        int n = s.length();
        int totalOnes = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') totalOnes++;
        }

        List<Integer> zeros = new ArrayList<>();
        List<Integer> ones = new ArrayList<>();

        int i = 0;
        while (i < n) {
            char c = s.charAt(i);
            int count = 0;
            while (i < n && s.charAt(i) == c) {
                count++;
                i++;
            }
            if (c == '0') {
                zeros.add(count);
            } else if (!zeros.isEmpty()) {
                ones.add(count);
            }
        }

        if (ones.size() >= zeros.size() && !ones.isEmpty()) {
            ones.remove(ones.size() - 1);
        }

        if (ones.isEmpty()) {
            return totalOnes;
        }

        int[] top = {-1, -1, -1};
        for (int j = 0; j < zeros.size(); j++) {
            int val = zeros.get(j);
            if (top[0] == -1 || val > zeros.get(top[0])) {
                top[2] = top[1];
                top[1] = top[0];
                top[0] = j;
            } else if (top[1] == -1 || val > zeros.get(top[1])) {
                top[2] = top[1];
                top[1] = j;
            } else if (top[2] == -1 || val > zeros.get(top[2])) {
                top[2] = j;
            }
        }

        int ans = totalOnes;
        for (int j = 0; j < ones.size(); j++) {
            int z1 = zeros.get(j);
            int z2 = zeros.get(j + 1);
            int o = ones.get(j);

            int bestGain = z1 + z2;

            for (int idx : top) {
                if (idx != -1 && idx != j && idx != j + 1) {
                    bestGain = Math.max(bestGain, zeros.get(idx) - o);
                    break;
                }
            }
            ans = Math.max(ans, totalOnes + bestGain);
        }

        return ans;
    }
}