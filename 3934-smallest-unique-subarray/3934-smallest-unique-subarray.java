
import java.util.*;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;

        Integer[] sa = new Integer[n];
        int[] rank = new int[n];

        for (int i = 0; i < n; i++) {
            sa[i] = i;
            rank[i] = nums[i];
        }

        for (int k = 1; k < n; k *= 2) {
            final int len = k;
            final int[] r = rank;

            Arrays.sort(sa, (a, b) -> {
                if (r[a] != r[b])
                    return Integer.compare(r[a], r[b]);

                int x = a + len < n ? r[a + len] : -1;
                int y = b + len < n ? r[b + len] : -1;

                return Integer.compare(x, y);
            });

            int[] newRank = new int[n];
            newRank[sa[0]] = 0;

            for (int i = 1; i < n; i++) {
                int a = sa[i - 1];
                int b = sa[i];

                boolean different =
                    rank[a] != rank[b] ||
                    (a + len < n ? rank[a + len] : -1) !=
                    (b + len < n ? rank[b + len] : -1);

                newRank[b] = newRank[a] + (different ? 1 : 0);
            }

            rank = newRank;

            if (rank[sa[n - 1]] == n - 1)
                break;
        }

        int[] lcp = new int[n];
        int[] pos = new int[n];

        for (int i = 0; i < n; i++)
            pos[sa[i]] = i;

        int h = 0;

        for (int i = 0; i < n; i++) {
            int r = pos[i];

            if (r == 0) continue;

            int j = sa[r - 1];

            while (i + h < n && j + h < n &&
                   nums[i + h] == nums[j + h]) {
                h++;
            }

            lcp[r] = h;

            if (h > 0) h--;
        }

        int ans = n;

        for (int i = 0; i < n; i++) {
            int r = pos[i];

            int maxLcp = lcp[r];

            if (r + 1 < n)
                maxLcp = Math.max(maxLcp, lcp[r + 1]);

            if (maxLcp < n - i) {
                ans = Math.min(ans, maxLcp + 1);
            }
        }

        return ans;
    }
}
