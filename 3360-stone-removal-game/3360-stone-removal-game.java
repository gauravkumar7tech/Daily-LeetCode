class Solution {
    public boolean canAliceWin(int n) {
        int need = 10;
        boolean alice = true;
        while(need > 0 && n >= need){
            n -= need;
            need--;
            alice = !alice;
        }
        return !alice;
    }
}