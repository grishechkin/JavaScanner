import java.util.Arrays;

public class IntList {
    private int lastInd = 0;
    private int[] list = new int[1];

    private void rebuild() {
        list = Arrays.copyOf(list, list.length * 2);
    } 

    public void add(int value) {
        if (lastInd == list.length) {
            rebuild();
        }

        list[lastInd] = value;
        lastInd++;
    }

    public int back() {
        return list[lastInd - 1];
    }

    public int pop() {
        lastInd--;
        return list[lastInd];
    }

    public int get(int ind) {
        return list[ind];
    }

    public void set(int ind, int value) {
        list[ind] = value;
    }

    public int size() {
        return lastInd;
    }

    public boolean isEmpty() {
        return lastInd == 0;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("[");
        if (lastInd != 0) {
            ret.append(list[0]);
        }
        for (int i = 1; i < lastInd; i++) {
            ret.append(", " + list[i]);
        }
        ret.append("]");
        return new String(ret);
    }
}