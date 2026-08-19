import java.util.HashMap;
import java.util.Map;

class Solution {
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        Map<Integer, Integer> rowReservations = new HashMap<>();
        
        for (int[] seat : reservedSeats) {
            int row = seat[0];
            int col = seat[1];
            rowReservations.put(row, rowReservations.getOrDefault(row, 0) | (1 << col));
        }
        
        int maxFamilies = (n - rowReservations.size()) * 2;
        
        for (int mask : rowReservations.values()) {
            boolean leftFree = (mask & 60) == 0;
            
            boolean rightFree = (mask & 960) == 0;
            
            boolean middleFree = (mask & 240) == 0;
            
            if (leftFree && rightFree) {
                maxFamilies += 2;
            } else if (leftFree || rightFree || middleFree) {
                maxFamilies += 1;
            }
        }
        
        return maxFamilies;
    }
}